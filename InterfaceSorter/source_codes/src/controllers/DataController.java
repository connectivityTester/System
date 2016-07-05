package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;

import Constants.Constants;
import io.Configuration;
import io.Reader;
import io.XMLWriter;
import logic.ConnectionManager;
import logic.OutputStreamParser;
import gui.BinaryLabel;
import gui.MainFrame;

public class DataController {
	
	private ConnectionManager connectionManager;
	private MainFrame mainFrame;
	private Configuration configuration;
	private OutputStreamParser outputStream;
	
	
	public void startView()	 					{ this.mainFrame = new MainFrame(this);	}	
	public void showTargetIP()					{ this.mainFrame.setTargetIP(this.configuration.getIpAddress());}
	public void finishShowWarning() 			{ this.mainFrame.finishShowWarning();	}
	public void startShowWarning() 				{ this.mainFrame.startShowWarning(Constants.getWarningMessage(), Constants.getWaitingImage());	}

	public DataController(Reader reader){
		try {
			this.configuration = reader.readConfiguration();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.connectionManager = new ConnectionManager(this);
		this.mainFrame = new MainFrame(this);
		this.outputStream = new OutputStreamParser();
	}
	
	
	public void connectToTheTarget(String newTargetIP){
		this.mainFrame.startShowWarning(Constants.getWarningMessage(), Constants.getWaitingImage());
		this.configuration.setIPAddress(newTargetIP);
		this.connectionManager.connectToTarget(newTargetIP, Integer.parseInt(this.configuration.getPort()), this.configuration.getUserName(), this.configuration.getPassword(), this.configuration.getEcho());
		this.mainFrame.finishShowWarning();
	}
	
	public String getConcoleOutPut(){
		String returnStr = this.connectionManager.getInterfaces(this.configuration.getInterfaces(), this.configuration.getProcesses(), this.configuration.getEcho());
		return returnStr.substring(4, returnStr.length());
	}
	

	public void setData(List<String> list){
		this.mainFrame.setData(list);
		this.mainFrame.finishShowWarning();	
	}

	public void sortData() {
		this.mainFrame.sortData();	

	}
	
	public String getIpAddress(){
		return this.configuration.getIpAddress();
	}
	
	public List<String> getNeededInterfaces(String output){
		return this.outputStream.parseOutputStream(output, this.configuration.getProcesses(), this.configuration.getInterfaces());
	}
	
	public List<String> getInterfacesFromConfig(){
		return this.configuration.getInterfaces();
	}
	
	public List<BinaryLabel> getBinaries(String ipAddress){
		this.connectionManager.connectToTarget(ipAddress, Integer.parseInt(this.configuration.getPort()), this.configuration.getUserName(), this.configuration.getPassword(), this.configuration.getEcho());
		List<BinaryLabel> binaries = new ArrayList<>();
		for(String binary : this.configuration.getBinaries()){
			//System.out.println("----"+this.connectionManager.sendCommand("pidin | grep "+binary, configuration.getEcho())+"000000");
			if(this.connectionManager.sendCommand("pidin | grep "+binary, configuration.getEcho()).length()>4){
				binaries.add(new BinaryLabel(binary, Constants.getStartedImage()));
			}
			else{
				binaries.add(new BinaryLabel(binary, Constants.getNotStartedImage()));
			}
		}		
		return binaries;
	}
	
	public void updateBinaries(String ipAddress) {
		this.mainFrame.updateBinaries(this.getBinaries(ipAddress));
		
	}
	
	public void clearData() {
		this.mainFrame.clearData();		
	}
	
	public String[] getAllAddresses() {
		return this.configuration.getAllAddresses();
	}
	
	public void updateComboBox(String address) {
		this.mainFrame.updateComboBox(address);		
	}
	public void writeConfiguration() {
		XMLWriter writer = new XMLWriter("configuration.xml");
		writer.writeConfiguration(this.configuration);		
	}
	public void setLastConnected(String newTargetIP) {
		this.configuration.setLastConnected(newTargetIP);		
	}

}
