package connections;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import common.DeviceSource;
import common.SystemConfig;
import gui.WorkSpace;
import types.DeviceSourceStatus;
import types.LogLevels;
import types.MessageLogTypes;
import utils.Logger;

public class DeviceSourceConnectionController extends Thread{
	
	private final ServerSocket serverSocket;
	private final WorkSpace workSpace; 
	private final List<DeviceSourceConnection> connectionList = new ArrayList<>();;
	
	public DeviceSourceConnectionController(WorkSpace workSpace){
		this.workSpace = workSpace;
		ServerSocket socket = null;
		try {
			socket = new ServerSocket(SystemConfig.getInstance().getMessagePort());
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.serverSocket = socket;
	}
	
	@SuppressWarnings("null")
	@Override
	public void run() {
		while(true){
			Socket socket = null;
			try {
				socket = serverSocket.accept();
			} catch (IOException e) {
				e.printStackTrace();
			}
			DeviceSource incDeviceSource = null;
			Logger.log(LogLevels.INFO, this, "Method run, incoming device source ip address: " + socket.getInetAddress().getHostAddress());
			for(DeviceSource deviceSource : SystemConfig.getInstance().getDeviceSources()){
				if(deviceSource.getAddress() != null &&
						deviceSource.getAddress().equals(socket.getInetAddress().getHostAddress()))
				{	
					Logger.log(LogLevels.INFO, this, "Method run, incoming device is present in system config");
					incDeviceSource = deviceSource;
					break;
				}
			}
			DeviceSourceConnection newConnection = new DeviceSourceConnection(socket, incDeviceSource, this);
			this.connectionList.add(newConnection);
			Thread thread = new Thread(newConnection);
			thread.setPriority(Thread.MIN_PRIORITY);
			thread.start();
			if(incDeviceSource != null){
				StringBuilder logMessage = new StringBuilder("Device source \"");
				logMessage.append(incDeviceSource.getName());
				logMessage.append("\" with address ");
				logMessage.append(incDeviceSource.getAddress());
				logMessage.append(" was connected");
				Logger.logToUser(logMessage.toString(), this, MessageLogTypes.INFO);
				this.workSpace.updateDeviceStatus(incDeviceSource, DeviceSourceStatus.CONNECTED);
			}
			else{
				StringBuilder logMessage = new StringBuilder("Device source \"");
				logMessage.append(incDeviceSource.getAddress());
				logMessage.append("\" was connected");
				Logger.logToUser(logMessage.toString(), this, MessageLogTypes.INFO);
			}
		}
	}
	
	public boolean sendTestDataToDeviceSource(DeviceSource deviceSource, String testDataString){
		boolean result = false;
		boolean isDeviceConnected = false;
		for(DeviceSourceConnection deviceSourceConnection : this.connectionList){
			if(deviceSourceConnection.getDeviceSource().getId() == deviceSource.getId()){
				result = deviceSourceConnection.sendDataToDeviceSource(testDataString);
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
