package connections.devicesources;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import common.DeviceSource;
import gui.WorkSpace;
import types.DeviceSourceStatus;
import types.LogLevels;
import types.MessageLogTypes;
import utils.Logger;
import xml.SystemConfig;

public class DeviceSourceConnectionController extends Thread{
	
	private final ServerSocket serverSocket;
	private final WorkSpace workSpace; 
	private final List<DeviceSourceConnection> connectionList = new ArrayList<>();;
	private final DeviceSourceConnectionBuilder connectionFactory = new DeviceSourceConnectionBuilder(this);
	
	public DeviceSourceConnectionController(WorkSpace workSpace){
		this.workSpace = workSpace;
		ServerSocket socket = null;
		try {
			socket = new ServerSocket(SystemConfig.getInstance().getMessagePort());
		} catch (IOException e) {
			Logger.log(LogLevels.EXCEPTION, this, "Socket for listening answers was not created");
			Logger.log(LogLevels.EXCEPTION, this, e.getLocalizedMessage());
			Logger.logToUser("System could not create socket for listening answers.", this, MessageLogTypes.ERROR);
			Logger.logToUser("Please you another port or analyze sysytem logs", this, MessageLogTypes.ERROR);
		}
		this.serverSocket = socket;
	}
	
	@Override
	public void run() {
		if(this.serverSocket != null){
			while(true){
				Socket socket = null;
				try {
					socket = this.serverSocket.accept();
				} catch (IOException e) {
					Logger.log(LogLevels.EXCEPTION, this, "Could not make socket from incoming device connection");
				}
				if(socket != null){
					DeviceSourceConnection newConnection = this.connectionFactory.createDeviceSourceConnection(socket);
					if(newConnection != null ){
						this.connectionList.add(newConnection);
						Thread thread = new Thread(newConnection);
						thread.setPriority(Thread.MIN_PRIORITY);
						thread.start();
						this.workSpace.updateDeviceStatus(newConnection.getDeviceSource(), DeviceSourceStatus.CONNECTED);
					}
				}
			}
		}
	}
	
	public boolean sendTestDataToDeviceSource(DeviceSource deviceSource, String testDataString){
		boolean result = true;
		boolean isDeviceConnected = false;
		for(DeviceSourceConnection deviceSourceConnection : this.connectionList){
			if(deviceSourceConnection.getDeviceSource().getId() == deviceSource.getId()){
				try {
					deviceSourceConnection.sendDataToDeviceSource(testDataString);
				} catch (IOException e) {
					Logger.log(LogLevels.EXCEPTION, this, e.getMessage());
					Logger.logToUser("Test data was not sent successfully to " + deviceSource.getName(), this, MessageLogTypes.ERROR);
					Logger.logToUser("Please analyze system logs", this, MessageLogTypes.ERROR);
					e.printStackTrace();
				}
				Logger.log(LogLevels.INFO, this, "Method sendTestDataToDeviceSource, data was sent with result: " + result);
				isDeviceConnected = true;
				break;
			}
		}
		if(!isDeviceConnected){
			StringBuilder logMessage = new StringBuilder("Device \""+deviceSource.getName() + "\"");
			if(deviceSource.getAddress() != null){
				logMessage.append(" with address " + deviceSource.getAddress());
				
			}
			logMessage.append(" is not connected");
			Logger.logToUser(logMessage.toString(), this, MessageLogTypes.ERROR);
		}
		return result;
	}
	
	void removeDisconnectedConnection(DeviceSource deviceSource){
		for(DeviceSourceConnection connection : this.connectionList){
			if(deviceSource.getId() == connection.getDeviceSource().getId()){
				this.connectionList.remove(connection);
				this.workSpace.updateDeviceStatus(deviceSource, DeviceSourceStatus.DISCONNECTED);
				break;
			}
		}
	}
}
