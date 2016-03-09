package starters;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;
import common.DeviceSource;
import gui.WorkSpace;
import types.DeviceSourceStatus;
import types.LogLevels;
import types.MessageLogTypes;
import types.SourceTypes;
import utils.Logger;
import xml.SystemConfig;

public class DeviceSourceRunManager {

	private final CopyOnWriteArrayList<DeviceSourceProcess> startedProcesses =  new CopyOnWriteArrayList<>();
	
	private WorkSpace workSpace;
	private static DeviceSourceRunManager deviceSourceStarter;
	
	public static DeviceSourceRunManager getInstance(){
		return deviceSourceStarter == null ? deviceSourceStarter = new DeviceSourceRunManager() : deviceSourceStarter;
	}
	
	public void setWorkSpace(WorkSpace workSpace)	{	this.workSpace = workSpace;	 }
	
	public void finishAllDeviceSources(){
		for(DeviceSource deviceSource : SystemConfig.getInstance().getDeviceSources()){
			if(deviceSource.getSourceType() != SourceTypes.SYSTEM_SOURCE &&
					deviceSource.getShutdownParameters() != null)
			{
				StringBuffer shutdownParameters = new StringBuffer();
				for(String param : deviceSource.getShutdownParameters()){
					shutdownParameters.append(param +" ");
				}
				try {
					Runtime.getRuntime().exec(shutdownParameters.toString());
					Logger.log(LogLevels.TRACE, this, "Device source \"" + deviceSource.getName() + "\" was shutdown successfully");
				} catch (IOException e) {
					Logger.log(LogLevels.EXCEPTION, this, "Device source \"" + deviceSource.getName() + "\" was not shutdown successfully");
					Logger.log(LogLevels.EXCEPTION, this, e.getLocalizedMessage());
				}
			}
		}
	}
	
	private DeviceSourceProcess startDeviceSource(DeviceSource deviceSource){
		Logger.log(LogLevels.INFO, this, "Method startDeviceSource was started");
		StringBuffer startUpParameters = new StringBuffer();
		for(String param : deviceSource.getStartUpParameters()){
			startUpParameters.append(param +" ");
		}
		Process deviceSourceProcess = null;
		try {
			deviceSourceProcess = Runtime.getRuntime().exec(startUpParameters.toString());
			Logger.logToUser("Device source \"" + deviceSource.getName() + "\" was auto started successfully", this, MessageLogTypes.INFO);
		} catch (IOException e) {
			Logger.logToUser("Device source \"" + deviceSource.getName() + "\" was not auto started successfully", this, MessageLogTypes.ERROR);
			Logger.log(LogLevels.EXCEPTION, this, e.getLocalizedMessage());
		}
		if(deviceSourceProcess != null){
			this.workSpace.updateDeviceStatus(deviceSource, DeviceSourceStatus.STARTED);
		}
		Logger.log(LogLevels.INFO, this, "Method startDeviceSource was finished");
		return new DeviceSourceProcess(deviceSourceProcess, deviceSource);
	}
	
	public void startDeviceSources(){
		for(DeviceSource deviceSource : SystemConfig.getInstance().getDeviceSources()){
			if(deviceSource.isAutoStartUp()){
				DeviceSourceProcess deviceSourceProcess = this.startDeviceSource(deviceSource);
				if(deviceSourceProcess.getProcess() != null){
					Thread thread  = new Thread(new DeviceSourceProcessTraceLogger(deviceSourceProcess));
					thread.setPriority(Thread.MIN_PRIORITY);
					thread.start();
				}
				this.startedProcesses.add(deviceSourceProcess);
			}
		}
		Thread thread = new Thread(new DeviceSourceProcessChecker(this.startedProcesses, this.workSpace));
		thread.setPriority(Thread.MIN_PRIORITY);
		thread.start();
	}
}
