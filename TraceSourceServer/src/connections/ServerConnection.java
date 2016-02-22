package connections;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import commands.Command;
import commands.CommandsExecutor;
import common.Configuration;
import types.LogLevels;
import unpacker.JsonUnpacker;
import unpacker.iUnpacker;
import utils.Logger;

public class ServerConnection{
		
	public ServerConnection(){
		Thread thread = new Thread(()->{
			iUnpacker unpacker = new JsonUnpacker();
			try(Socket socket = new Socket(Configuration.getInstance().getCommandServerIPAddress(), 
					Configuration.getInstance().getCommandServerPort()))
			{
				try(DataInputStream inputStream  = new DataInputStream(socket.getInputStream())){
					String receivedMessage = null;
					while(true){
						try {
							receivedMessage = inputStream.readUTF();
						} catch (IOException e) {
							e.printStackTrace();
							break;
						}
						Command receivedCommand = unpacker.unpackMessageToCommand(receivedMessage);
						CommandsExecutor.getInstance().executeCommand(receivedCommand);
					}
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}	
		});
		thread.setPriority(Thread.MAX_PRIORITY);
		thread.start();
	}

}
