package connections.devicesources;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import buffers.BufferManager;
import common.DeviceSource;
import types.MessageLogTypes;
import utils.Logger;
import utils.Utils;

public class DeviceSourceInputConnection implements Runnable{
	
	private final InputStream inputStream;
	private final DeviceSource deviceSource;
	private final DeviceSourceConnectionController deviceSourceConnectionController;
	
	public DeviceSourceInputConnection(final DeviceSource devSource, final InputStream inStream , 
										final DeviceSourceConnectionController controller) 
	{
		Utils.requireNonNull(devSource,inStream, controller);
		
		this.deviceSourceConnectionController = controller;
		this.deviceSource = devSource;
		this.inputStream = inStream;	
	}
	
	@Override
	public void run() {
		StringBuilder builder = new StringBuilder();
		while(true){
			byte []buffer = new byte [2_000_000];
			List<String> messages = null; 
			try {
				int readBytes = this.inputStream.read(buffer);
				if(readBytes > 0 ){
					String receivedData = new String(buffer, 0, readBytes);
					//Logger.log(LogLevels.INFO, this, "Received data: " + receivedData);
					builder.append(receivedData);	
					String [] parts =  builder.toString().split("\n");
					if(parts[parts.length-1].endsWith("}")){
						messages = Arrays.asList(parts);
						builder = new StringBuilder();
					}
					else{
						messages = Arrays.asList(parts).subList(0, parts.length-2);
						builder = new StringBuilder(parts[parts.length-1]);
					}
				}		
			} catch (IOException e) {
				StringBuilder logMessage = new StringBuilder("Device source \"");
				logMessage.append(this.deviceSource.getName());
				logMessage.append("\" with address ");
				logMessage.append(this.deviceSource.getAddress());
				logMessage.append(" was disconnected");
				Logger.logToUser(logMessage.toString(), this, MessageLogTypes.INFO);
				this.deviceSourceConnectionController.removeDisconnectedConnection(this.deviceSource);
				break;
			}
			if(messages!= null){
				BufferManager.getInstance().addMessageToBuffer(messages);
			}				
		}		
	}
}
