package common;

import java.io.IOException;
import java.util.List;

import org.jdom2.JDOMException;

import types.LogLevels;
import utils.Logger;

public class SystemConfig {
	
	private final int MAX_CAPACITY = Integer.MAX_VALUE;
	private final DeviceSource systemDeviceSource = new DeviceSource("System", SystemConstants.systemDeviceSourceId, false, null, -1, null, null);

	private final String testRootDirectory;
	private final int messagePort;
	private final List<DeviceSource> deviceSources;
	private static SystemConfig systemConfig;
	
	SystemConfig(String testRootDirectory, int messagePort, List<DeviceSource> deviceSources) {
		this.testRootDirectory = testRootDirectory;
		this.messagePort = messagePort;
		this.deviceSources = deviceSources;
		this.deviceSources.add(this.systemDeviceSource);
		Logger.log(LogLevels.TRACE, this, this.toString());
	}
	
	public static SystemConfig getInstance()	{	
		if(systemConfig == null){
			try {
				systemConfig = ConfigXMLReader.readConfiguration(SystemConstants.fileConfigPath);
			} catch (JDOMException | IOException e) {
				e.printStackTrace();
			}
		}
		return systemConfig;			
	}

	public int getMAX_CAPACITY() 				{	return this.MAX_CAPACITY;		}
	public List<DeviceSource> getDeviceSources(){	return this.deviceSources;		}
	public String getTestRootDirectory() 		{	return this.testRootDirectory;	}	
	public int getMessagePort() 				{	return this.messagePort;		}	
	public DeviceSource getSystemDeviceSource()	{	return this.systemDeviceSource;	}

	@Override
	public String toString() {
		return "\nSystemConfig [testRootDirectory=" + testRootDirectory + ", messagePort=" + messagePort
				+ ", \ndeviceSources=" + deviceSources + "]";
	}
}
