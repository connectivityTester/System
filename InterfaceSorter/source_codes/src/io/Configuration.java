package io;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Configuration {
	
	private List<String> targetAddresses;
	private List<String> interfaces;
	private List<String> processes;
	private List<String> binaries;
	private String port;
	private String userName;
	private String password;
	private String echo;
	
	public Configuration (){
		this.targetAddresses = new LinkedList();
		this.port = "";
		this.echo = "";
		this.userName = "";
		this.password = "";
		this.interfaces = new ArrayList<>();
		this.processes = new ArrayList<>();
		this.binaries = new ArrayList<>();
	}
	//return head of queue
	public String getIpAddress() 		{ return this.targetAddresses.get(0); }
	public String getPort() 			{ return port; }
	public String getUserName() 		{ return userName; }
	public String getPassword() 		{ return password; }
	public String getEcho() 			{ return echo; }
	public List<String> getInterfaces()	{ return interfaces;}
	public List<String> getProcesses()  { return processes;	}
	public List<String> getBinaries()	{ return this.binaries;}
	public void setEcho(String echo)				{ this.echo = echo;	}
	public void setPort(String port) 				{ this.port = port;	}
	public void setUserName(String userName) 		{ this.userName = userName; }
	public void setPassword(String password)		{ this.password = password; }
	public void addInterface(String interfaceName)	{ this.interfaces.add(interfaceName); }
	public void addProcess(String process) 			{ this.processes.add(process); }
	public void addBinary(String binary)			{ this.binaries.add(binary); }
	
	public void setIPAddress(String ipAddress){
		if(this.targetAddresses.contains(ipAddress) == true){
			LinkedList<String> newTargetList = new LinkedList<>();
			newTargetList.add(ipAddress);
			this.targetAddresses.remove(ipAddress);
			newTargetList.addAll(this.targetAddresses);
			this.targetAddresses = newTargetList;
		}
		else{
			this.targetAddresses.add(ipAddress);
		}
	}
	public String[] getAllAddresses() {
		String [] array = new String[this.targetAddresses.size()];
		for(int i = 0 ; i < this.targetAddresses.size(); ++i){
			array[i] = this.targetAddresses.get(i);
		}
		return array;
	}
	public void setLastConnected(String newTargetIP) {
		List<String> newAddressesList = new LinkedList<>();
		newAddressesList.add(newTargetIP);
		this.targetAddresses.remove(this.targetAddresses.indexOf(newTargetIP));
		newAddressesList.addAll(this.targetAddresses);
		this.targetAddresses = newAddressesList;
	}


}
