package connections;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import buffers.IncomingMessageReceiver;
import common.DeviceSource;
import types.LogLevels;
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
			byte []buffer = new byte[60_000];
			List<String> messages = null; 
			try {
				int readBytes = this.dataInputStream.read(buffer);
				if(readBytes > 0 ){
					String receivedData = new String(buffer, 0, readBytes);
					Logger.log(LogLevels.TRACE, this, "Received data: " + receivedData);
					builder.append(receivedData);					
					String [] parts = builder.toString().split("\\}\n\\{");
					if(parts.length > 1){
						messages = new LinkedList<>();
						for(int i = 0; i < parts.length-1; ++i){
							if(!parts[i].startsWith("{")){
								messages.add("{" + parts[i] + "}");
							}
							else{
								messages.add( parts[i] + "}");
							}
						}					
					}
					else{
						messages = null;
					}
					if(parts[parts.length-1].endsWith("}\n")){
						if(parts[parts.length-1].startsWith("{")){
							messages = Arrays.asList(parts[parts.length-1].substring(0, parts[parts.length-1].length()-1));
						}
						else{
							messages = Arrays.asList("{" + parts[parts.length-1].substring(0, parts[parts.length-1].length()-1));
						}
						builder = new StringBuilder();
					}
					else{
						builder = new StringBuilder(parts[parts.length-1]);
					}
//					System.out.println("----- "+builder.toString());
//					System.out.println("++++++");
//					if(messages != null)
//						for(String mess : messages){
//							System.out.println(mess);
//						}
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
