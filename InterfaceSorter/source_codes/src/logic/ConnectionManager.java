package logic;
import io.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.commons.net.telnet.TelnetClient;

import controllers.DataController;

public class ConnectionManager {
	private TelnetClient telnetClient;
	private InputStream input;
	private PrintStream out;
	private DataController dataController;
	
	
	public ConnectionManager(DataController dataController) {
		this.telnetClient = new TelnetClient();
		this.dataController = dataController;
	}
	
	private void authontification(String user, String password, String echo) throws SocketException, IOException{
		readUntil("login: ");
		write(user);
		if(!password.equals("")){
			readUntil("Password: ");
			write(password);
		}
		readUntil(echo);
	}
	
	public String getInterfaces(List<String> interfaceList, List<String> processes, String echo){
		return sendCommand("cat /srv/servicebroker", echo);	
	}
	
	
	public String sendCommand(String command, String echo) {
		try {
			write(command);
			String retunrLine = readUntil(echo);			
			retunrLine=retunrLine.substring(command.length());
			retunrLine=retunrLine.substring(0, retunrLine.length()-echo.length());			
			return retunrLine;
		}
		catch (Exception e) {
			System.out.println("Some problems with sending command");
		}
		return null;
	}

	public void disconnect() {
		try {
			this.telnetClient.disconnect();
		}
		catch (Exception e) {
			System.out.println("Some problems with disconnection");
			e.printStackTrace();
		}
	}
	
	private String readUntil(String pattern) {
		try {
			char lastChar = pattern.charAt(pattern.length()-1);
			StringBuffer sb = new StringBuffer();
			char ch = (char) input.read();
			while (true) {
				sb.append(ch);
				if (ch == lastChar) {
					if (sb.toString().endsWith(pattern)) {
						return sb.toString();
					}
				}
				ch = (char) input.read();
			}
		}
		catch (Exception e) {
			System.out.println("Some problems with reading command");
			e.printStackTrace();
		}
		return null;
	}
	
	private void write(String value) {
		try {
			out.println(value);
			out.flush();
		}
		catch (Exception e) {
			System.out.println("Some problems with writing command");
			e.printStackTrace();
		}
	}
	

	public void connectToTarget(String newTargetIP, int port, String user, String password, String echo) {
		try {
			System.out.println("Connect to "+ newTargetIP);
			this.telnetClient.connect(newTargetIP, port);
			this.dataController.setLastConnected(newTargetIP);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			dataController.finishShowWarning();
			JOptionPane.showMessageDialog(null, "<html><p align=\"center\">Incorrect configuration format.</p><br><p align=\"center\">Please, correct it!</p>");
		} catch (SocketException e) {
			e.printStackTrace();
			dataController.finishShowWarning();
			JOptionPane.showMessageDialog(null, "<html><p align=\"center\">Connection timed out.</p><br><p align=\"center\">Check that target is power on and his IP address is correct!</p>");
		} catch (UnknownHostException e){
			e.printStackTrace();
			dataController.finishShowWarning();
			JOptionPane.showMessageDialog(null, "<html><p align=\"center\">Current target does not exist!</p><br><p align=\"center\">Please, correct IP address!</p>");
		} catch (IOException e) { 
			e.printStackTrace();
		}
		this.input = this.telnetClient.getInputStream();
		this.out = new PrintStream(this.telnetClient.getOutputStream());
		try {
			this.authontification(user, password, echo);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Connection can not be authontificated");
		}
		
	}
}
