package starters;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import gui.WorkSpace;
import types.DeviceSourceStatus;
import types.MessageLogTypes;
import utils.Logger;

public class DeviceSourceProcessChecker implements Runnable{
	
	private final CopyOnWriteArrayList<DeviceSourceProcess> startedProcesses;
	private final WorkSpace workSpace;
	
	DeviceSourceProcessChecker(CopyOnWriteArrayList<DeviceSourceProcess> startedProcesses, WorkSpace workSpace) {
		this.workSpace = workSpace;
		this.startedProcesses = startedProcesses;
	}
	
	@Override
	public void run() {
		DeviceSourceProcess process = null;
		Iterator<DeviceSourceProcess> iterator = null;
		while(true){
			iterator = this.startedProcesses.iterator();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			while(iterator.hasNext()){
				process = iterator.next();
				if(process.getProcess() != null && !process.getProcess().isAlive()){
					this.workSpace.updateDeviceStatus(process.getDeviceSource(), DeviceSourceStatus.STOPED);
					StringBuffer logMessage = new StringBuffer("Device source \"");
					logMessage.append(process.getDeviceSource().getName());
					if(process.getDeviceSource().getAddress() != null){
						logMessage.append("\" with address ");
						logMessage.append(process.getDeviceSource().getAddress());
					}
					logMessage.append(" was stoped");
					Logger.logToUser(logMessage.toString(), this, MessageLogTypes.INFO);
					this.startedProcesses.remove(process);
				}
			}
			
		}
	}
}
