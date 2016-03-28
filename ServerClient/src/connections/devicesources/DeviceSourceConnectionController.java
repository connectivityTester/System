package connections.devicesources;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import common.DeviceSource;
import gui.WorkSpace;
import types.DeviceSourceStatus;
import types.LogLevels;
import types.MessageLogTypes;
import utils.Logger;
import utils.Utils;
import xml.SystemConfig;

public class DeviceSourceConnectionController extends Thread{
	
	private final ServerSocket serverSocket;
	private final WorkSpace workSpace; 
	private final List<DeviceSourceConnection> connectionList = new ArrayList<>();;
	private final DeviceSourceConnectionBuilder connectionFactory = new DeviceSourceConnectionBuilder(this);
	
	public DeviceSourceConnectionController(final WorkSpace workSpace){
		Utils.requireNonNull(workSpace);
		
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
		Utils.requireNonNull(this.serverSocket); 
		
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
					final Thread thread = new Thread(newConnection);
					thread.setPriority(Thread.MIN_PRIORITY);
					thread.start();
					this.workSpace.updateDeviceStatus(newConnection.getDeviceSource(), DeviceSourceStatus.CONNECTED);
				}
			}
		}
	}
	
	public boolean sendTestDataToDeviceSource(final DeviceSource deviceSource, final String testDataString){
		Utils.requireNonNull(deviceSource, testDataString);
		
		boolean result = true;
		Optional<DeviceSourceConnection> deviceConnection = this.connectionList.stream()
			.filter(deviceSourceConnection -> deviceSourceConnection.equalsDeviceId(deviceSource.getId()))
			.findFirst();
		if(deviceConnection.isPresent()){
			try {
				deviceConnection.get().sendDataToDeviceSource(testDataString);
			} catch (IOException e) {
				Logger.log(LogLevels.EXCEPTION, this, e.getMessage());
				Logger.logToUser("Test data was not sent successfully to " + deviceSource.getName(), this, MessageLogTypes.ERROR);
				Logger.logToUser("Probably device (" + deviceSource.getName() 
						+ ")has not been connected ", this, MessageLogTypes.ERROR);
				Logger.logToUser("Please check it and try again", this, MessageLogTypes.ERROR);
				result = false;
			}
		}
		else{
			result = false;
			Logger.logToUser("Device (" + deviceSource.getName() 
						+ ") has not been registered", this, MessageLogTypes.ERROR);
		}		
		return result;
	}
	
	void removeDisconnectedConnection(final DeviceSource deviceSource){
		Utils.requireNonNull(deviceSource);
		
		for(DeviceSourceConnection connection : this.connectionList){
			if(connection.equalsDeviceId(deviceSource.getId())){
				this.connectionList.remove(connection);
				this.workSpace.updateDeviceStatus(deviceSource, DeviceSourceStatus.DISCONNECTED);
				break;
			}
		}
	}
}
