package test.actions;

import common.DeviceSource;
import xml.SystemConfig;

public class Command {
	
	private String commandName;
	private DeviceSource deviceSource;

	public Command(final String command, final int deviceSourceId) {
		utils.Utils.requireNonNull(command);
		
		this.commandName = command;
		this.deviceSource = SystemConfig.getInstance().getDeviceSources().stream()
			.filter(deviceSource -> deviceSource.equalsId(deviceSourceId))
			.findFirst()
			.orElse(SystemConfig.getInstance().getSystemDeviceSource());
	}

	@Override
	public String toString() 			 {	return "Command [commandName=" + commandName + ", deviceSource=" + deviceSource + "]";	}
	public String getCommandName() 		 {	return this.commandName;																}	
	public DeviceSource getDeviceSource(){	return this.deviceSource;																}
}
