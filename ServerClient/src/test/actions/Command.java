package test.actions;

import common.DeviceSource;
import xml.SystemConfig;

public class Command {
	
	private String commandName;
	private DeviceSource deviceSource;

	public Command(String command, int deviceSourceId) {
		this.commandName = command;
		this.deviceSource = SystemConfig.getInstance().getSystemDeviceSource();
		for(DeviceSource deviceSource : SystemConfig.getInstance().getDeviceSources()){
			if(deviceSource.getId() == deviceSourceId){
				this.deviceSource = deviceSource;
			}
		}
	}

	@Override
	public String toString() 			 {	return "Command [commandName=" + commandName + ", deviceSource=" + deviceSource + "]";	}
	public String getCommandName() 		 {	return this.commandName;																}	
	public DeviceSource getDeviceSource(){	return this.deviceSource;																}
}
