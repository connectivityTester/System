package connections.devicesources;

import java.io.IOException;
import java.net.Socket;

import common.DeviceSource;
import types.LogLevels;
import types.MessageLogTypes;
import utils.Logger;
import xml.SystemConfig;

public class DeviceSourceConnectionBuilder {
	
	private final DeviceSourceConnectionController deviceSourceConnectionController;
	
	public DeviceSourceConnectionBuilder(DeviceSourceConnectionController controller) {
		this.deviceSourceConnectionController = controller;
	}
	
	public DeviceSourceConnection createDeviceSourceConnection(Socket clietnSocket){
		DeviceSource incDeviceSource = null;
		Logger.log(LogLevels.INFO, this, "Method run, incoming device source ip address: " + clietnSocket.getInetAddress().getHostAddress());
		for(DeviceSource deviceSource : SystemConfig.getInstance().getDeviceSources()){
			if(deviceSource.getAddress() != null &&
					deviceSource.getAddress().equals(clietnSocket.getInetAddress().getHostAddress()))
			{	
				
				incDeviceSource = deviceSource;
				break;
			}
		}
		DeviceSourceConnection newDevSourceConnection = null;
		if(incDeviceSource != null){
			Logger.logToUser("Incoming socket connection from \"" + incDeviceSource.getName() 
						+ "\"(with IP address " + clietnSocket.getLocalAddress().toString().substring(1) 
						+ ")...", this, MessageLogTypes.INFO);
			Logger.logToUser("System is tring to connect with " + incDeviceSource.getName() +  "...", this, MessageLogTypes.INFO);
			
			try {
				newDevSourceConnection = new DeviceSourceConnection(clietnSocket, incDeviceSource, this.deviceSourceConnectionController);
				StringBuilder logMessage = new StringBuilder("Device source \"");
				logMessage.append(incDeviceSource.getName());
				logMessage.append("\" with IP address ");
				logMessage.append(incDeviceSource.getAddress());
				logMessage.append(" was connected successfully");
				Logger.logToUser(logMessage.toString(), this, MessageLogTypes.INFO);
			} catch (IOException e) {
				Logger.logToUser("System could not make connection with " + incDeviceSource.getName(), this, MessageLogTypes.ERROR);
				Logger.logToUser("Please analyze system log", this, MessageLogTypes.ERROR);
			}
		}
		else{
			Logger.logToUser("Incoming IP address is not present in system configuration", this, MessageLogTypes.ERROR);
			Logger.logToUser("Connection will be rejected", this, MessageLogTypes.ERROR);
			Logger.logToUser("If you want to listening answers from this inc. connection, "
					+ "please add IP address in system configuration", this, MessageLogTypes.ERROR);
		}
		return newDevSourceConnection;
	}

}
