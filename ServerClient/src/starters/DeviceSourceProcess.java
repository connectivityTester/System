package starters;

import common.DeviceSource;

public class DeviceSourceProcess {
	
	private final Process process;
	private final DeviceSource deviceSource;
	
	public DeviceSourceProcess(Process process, DeviceSource deviceSource) {
		super();
		this.process = process;
		this.deviceSource = deviceSource;
	}

	public Process getProcess() 			{	return this.process;		}
	public DeviceSource getDeviceSource() 	{	return this.deviceSource;	}
}
