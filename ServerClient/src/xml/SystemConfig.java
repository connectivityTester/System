package xml;
import java.util.List;

import javax.swing.JOptionPane;

import common.DeviceSource;
import common.SystemConstants;
import types.LogLevels;
import utils.Logger;

public class SystemConfig implements Context{
	
	private final DeviceSource systemDeviceSource = new DeviceSource("System", SystemConstants.systemDeviceSourceId, false, null, null, null);

	private final String testRootDirectory;
	private final int messagePort;
	private final List<DeviceSource> deviceSources;
	private static SystemConfig systemConfig;
	private static final AbstractReader reader = new ConfigurationReader(SystemConstants.configShemaPath);
	
	SystemConfig(final String testRootDirectory, final int messagePort, final List<DeviceSource> deviceSources) {
		utils.Utils.requireNonNull(testRootDirectory, messagePort, deviceSources);
		this.testRootDirectory = testRootDirectory;
		this.messagePort = messagePort;
		this.deviceSources = deviceSources;
		this.deviceSources.add(this.systemDeviceSource);
		Logger.log(LogLevels.TRACE, this, this.toString());
	}
	
	public static SystemConfig getInstance()	{	
		if(systemConfig == null){
			try {
				systemConfig = (SystemConfig) reader.getContext(SystemConstants.fileConfigPath);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(
						null, 
						"<html><body><p style='width: 150px;'>File: " + SystemConstants.fileConfigPath +
						".</p><p> "	+ e.getMessage()+
						". </p><p>System starting up was interrupted</p></body></html>", 
						"Error during system config validation", 
						JOptionPane.ERROR_MESSAGE);
				Logger.log(LogLevels.EXCEPTION, systemConfig, e.getMessage());
				System.exit(-1);
			}
		}
		return systemConfig;			
	}

	public List<DeviceSource> getDeviceSources(){	return this.deviceSources;		}
	public String getTestRootDirectory() 		{	return this.testRootDirectory;	}	
	public int getMessagePort() 				{	return this.messagePort;		}	
	public DeviceSource getSystemDeviceSource()	{	return this.systemDeviceSource;	}

	public boolean isDeviceSourceRegistered(int deviceSourceId){
		for(DeviceSource deviceSource : this.deviceSources){
			if(deviceSource.getId() == deviceSourceId){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "\nSystemConfig [testRootDirectory=" + testRootDirectory + ", messagePort=" + messagePort
				+ ", \ndeviceSources=" + deviceSources + "]";
	}
}
