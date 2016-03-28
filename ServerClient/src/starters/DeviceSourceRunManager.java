package starters;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import common.DeviceSource;
import gui.WorkSpace;
import types.DeviceSourceStatus;
import types.LogLevels;
import types.MessageLogTypes;
import utils.Logger;
import utils.Utils;
import xml.SystemConfig;

public class DeviceSourceRunManager {

	private final List<DeviceSourceProcess> startedProcesses =  new CopyOnWriteArrayList<>();
	
	private WorkSpace workSpace;
	private static DeviceSourceRunManager deviceSourceStarter;
	
	public static DeviceSourceRunManager getInstance(){
		return deviceSourceStarter == null ? deviceSourceStarter = new DeviceSourceRunManager() : deviceSourceStarter;
	}
	
	public void setWorkSpace(final WorkSpace workSpace)	{	this.workSpace = workSpace;	 }
	
	public void finishAllDeviceSources(){
		SystemConfig.getInstance().getDeviceSources().stream()
		.map(DeviceSource::getShutdownParameters)
		.filter(params -> params != null)
		.forEach(params -> {
			String shutdownParams = params.stream().reduce("", String::concat);
			try {
				Runtime.getRuntime().exec(shutdownParams);
				Logger.log(LogLevels.TRACE, this, "Device source with parameters \"" + shutdownParams + "\" was shutdown successfully");
			} catch (IOException e) {
				Logger.log(LogLevels.EXCEPTION, this, "Device source with parameters\"" + shutdownParams + "\" was not shutdown successfully");
				Logger.log(LogLevels.EXCEPTION, this, e.getLocalizedMessage());
			}
		});
	}
	
	private DeviceSourceProcess startDeviceSource(final DeviceSource deviceSource){
		Utils.requireNonNull(deviceSource);
		
		Logger.log(LogLevels.INFO, this, "Method startDeviceSource was started");
		String startUpParameters = deviceSource.getStartUpParameters().stream().reduce("", String::concat);
		Process deviceSourceProcess = null;
		try {
			deviceSourceProcess = Runtime.getRuntime().exec(startUpParameters.toString());
			this.workSpace.updateDeviceStatus(deviceSource, DeviceSourceStatus.STARTED);
			Logger.logToUser("Device source \"" + deviceSource.getName() + "\" was auto started successfully", this, MessageLogTypes.INFO);
		} catch (IOException e) {
			Logger.logToUser("Device source \"" + deviceSource.getName() + "\" was not auto started successfully", this, MessageLogTypes.ERROR);
			Logger.log(LogLevels.EXCEPTION, this, e.getLocalizedMessage());
		}
		Logger.log(LogLevels.INFO, this, "Method startDeviceSource was finished");
		return new DeviceSourceProcess(deviceSourceProcess, deviceSource);
	}
	
	public void startDeviceSources(){
		SystemConfig.getInstance().getDeviceSources().forEach(deviceSource -> {
			if(deviceSource.isAutoStartUp()){
				DeviceSourceProcess deviceSourceProcess = this.startDeviceSource(deviceSource);
				if(deviceSourceProcess.getProcess() != null){
					Thread thread  = new Thread(new DeviceSourceProcessTraceLogger(deviceSourceProcess));
					thread.setPriority(Thread.MIN_PRIORITY);
					thread.start();
				}
				this.startedProcesses.add(deviceSourceProcess);
			}
		});
		Thread thread = new Thread(new DeviceSourceProcessChecker(this.startedProcesses, this.workSpace));
		thread.setPriority(Thread.MIN_PRIORITY);
		thread.start();
	}
}
