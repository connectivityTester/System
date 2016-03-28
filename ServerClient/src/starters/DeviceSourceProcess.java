package starters;
import utils.Utils;
import common.DeviceSource;

public class DeviceSourceProcess {
	
	private final Process process;
	private final DeviceSource deviceSource;
	
	public DeviceSourceProcess(final Process process, final DeviceSource deviceSource) {
		Utils.requireNonNull(process, deviceSource);
		
		this.process = process;
		this.deviceSource = deviceSource;
	}

	public Process getProcess() 			{	return this.process;			}
	public DeviceSource getDeviceSource() 	{	return this.deviceSource;		}
	public boolean isAlive()				{	return this.process.isAlive();	}
}
