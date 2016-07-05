package logic;

import utils.P4Utils;
import gui.MessageType;

import com.perforce.p4java.server.IServer;
import com.perforce.p4java.server.ServerFactory;
import static controller.Controller.controller;

/**
 * 
 * Handle connections to servers
 * 
 * @author dkuschevoy
 *
 */
public class ConnectionController {
	
	private final static String URL3500 = "172.30.137.2:3500";
	private final static String URL3501 = "172.30.137.2:3501";
	private final static String URL3510 = "172.30.137.2:3510";
	
	private final IServer server3500;
	private final IServer server3501;
		
	public ConnectionController() {
		controller.showLogMessage("\nConnecting to servers...", MessageType.OK);
		server3500 = connectToServer(URL3500);
		server3501 = connectToServer(URL3501);
		 IServer server3510 = connectToServer(URL3510);
		if(server3500 != null && server3501 != null && server3510 !=null){
			controller.showLogMessage("Connections to servers were created", MessageType.OK);
			controller.showLogMessage("Please start integration", MessageType.OK);
		}
		else{
			controller.showLogMessage("\nSomeone connection was failed!", MessageType.ERROR);
			controller.showLogMessage("Do not start integration!", MessageType.ERROR);
			controller.showLogMessage("Please check user name, password and try again!", MessageType.ERROR);
		}
	}
	/**
	 * Make connection to server
	 * 	
	 * @param URL - server address
	 * @return - instance of connected server or null if connection was failed
	 */
	private static IServer connectToServer(final String URL){
		IServer server = null;
		try {
			server = ServerFactory.getServer("p4java://" + URL, null);
			server.connect();
			server.setUserName(Properties.USER.getData());
			server.login(Properties.PASSWORD.toString());	
			controller.showLogMessage("Connected to " + URL + " server", MessageType.OK);
		} catch (Exception e) {
			server = null;
			controller.showLogMessage("Server " + URL + " was not connected", MessageType.ERROR);
			e.printStackTrace();
		}
		return server;
	}
	
	public IServer getServer(final ServerType type){
		switch(type){
			case SERVER_3500: 	return server3500;
			case SERVER_3501: 	return server3501;
			default:			return null;
		}
	}
	/**
	 * 
	 * @param server - necessary server 
	 * @param workspace - workspace to switch mentioned server
	 * @return - instance of connected server or null if connection was failed
	 */
	public IServer getServer(final ServerType server, final String workspace){
		return P4Utils.switchWorkspace(getServer(server), workspace);
	}
	
	public enum ServerType{
		SERVER_3500,
		SERVER_3501,
	}
}
