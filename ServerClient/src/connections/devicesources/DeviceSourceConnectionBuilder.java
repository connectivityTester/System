package connections.devicesources;

import java.io.IOException;
import java.net.Socket;
import java.util.Optional;

import common.DeviceSource;
import types.LogLevels;
import types.MessageLogTypes;
import utils.Logger;
import utils.Utils;
import xml.SystemConfig;

public class DeviceSourceConnectionBuilder {
	
	private final DeviceSourceConnectionController deviceSourceConnectionController;
	
	public DeviceSourceConnectionBuilder(final DeviceSourceConnectionController controller) {
		this.deviceSourceConnectionController = controller;
	}
	
	public DeviceSourceConnection createDeviceSourceConnection(final Socket clientSocket){
		Utils.requireNonNull(clientSocket);
		
		Logger.log(LogLevels.INFO, this, "Method run, incoming device source ip address: " + clientSocket.getInetAddress().getHostAddress());
		Optional<DeviceSource> incDeviceSource = SystemConfig.getInstance().getDeviceSources().stream()
				.filter(deviceAddress -> deviceAddress!= null && 
						deviceAddress.equals(clientSocket.getInetAddress().getHostAddress()))
				.findFirst();
		DeviceSourceConnection newDevSourceConnection = null;
		if(incDeviceSource.isPresent()){
			Logger.logToUser("Incoming socket connection from \"" + incDeviceSource.get().getName() 
						+ "\"(with IP address " + clientSocket.getLocalAddress().toString().substring(1) 
						+ ")...", this, MessageLogTypes.INFO);
			Logger.logToUser("System is tring to connect with " + incDeviceSource.get().getName() +  "...", this, MessageLogTypes.INFO);
			
			try {
				newDevSourceConnection = new DeviceSourceConnection(clientSocket, incDeviceSource.get(), this.deviceSourceConnectionController);
				StringBuilder logMessage = new StringBuilder("Device source \"");
				logMessage.append(incDeviceSource.get().getName());
				logMessage.append("\" with IP address ");
				logMessage.append(incDeviceSource.get().getAddress());
				logMessage.append(" was connected successfully");
				Logger.logToUser(logMessage.toString(), this, MessageLogTypes.INFO);
			} catch (IOException e) {
				Logger.logToUser("System could not make connection with " + incDeviceSource.get().getName(), this, MessageLogTypes.ERROR);
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
