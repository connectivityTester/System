package connections;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import buffers.IncomingMessageReceiver;
import common.DeviceSource;
import types.MessageLogTypes;
import utils.Logger;

public class DeviceSourceInputConnection implements Runnable{
	
	private final DataInputStream dataInputStream;
	private final DeviceSource deviceSource;
	private final DeviceSourceConnectionController deviceSourceConnectionController;
	
	public DeviceSourceInputConnection(DeviceSource deviceSource, Socket incomingConnection, DeviceSourceConnectionController controller) {
		this.deviceSourceConnectionController = controller;
		this.deviceSource = deviceSource;
		DataInputStream inputStream = null;
		try {
			inputStream = new DataInputStream(incomingConnection.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.dataInputStream = inputStream;
	}

	@Override
	public void run() {
		StringBuilder builder = new StringBuilder();
		while(true){
			byte []buffer = new byte[1000];
			List<String> messages = null; 
			try {
				int readBytes = this.dataInputStream.read(buffer);
				if(readBytes > 0 ){
					builder.append(new String(buffer, 0, readBytes));					
					String [] parts = builder.toString().split("\\}\n\\{");
					if(parts.length > 2){
						messages = new LinkedList<>();
						for(int i = 0; i < parts.length-2; ++i){
							messages.add("{" + parts[i] + "}");
						}					
					}
					else{
						messages = null;
					}
					builder = new StringBuilder(parts[parts.length-1]);
					//System.out.println(messages);
				}
				else{
					StringBuilder logMessage = new StringBuilder("Device source \"");
					logMessage.append(this.deviceSource.getName());
					logMessage.append("\" with address ");
					logMessage.append(this.deviceSource.getAddress());
					logMessage.append(" was disconnected");
					Logger.logToUser(logMessage.toString(), this, MessageLogTypes.INFO);
					this.deviceSourceConnectionController.removeDisconnectedConnection(this.deviceSource);
					break;
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
			if(messages != null){
				IncomingMessageReceiver.getInstance().addMessageToBuffer(messages);
			}
		}		
	}
}
