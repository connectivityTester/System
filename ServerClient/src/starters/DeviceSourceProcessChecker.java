package starters;
import java.util.List;
import gui.WorkSpace;
import types.DeviceSourceStatus;
import types.MessageLogTypes;
import utils.Logger;
import utils.Utils;

public class DeviceSourceProcessChecker implements Runnable{
	
	private final List<DeviceSourceProcess> startedProcesses;
	private final WorkSpace workSpace;
	
	DeviceSourceProcessChecker(final List<DeviceSourceProcess> startedProcesses, final WorkSpace workSpace) {
		Utils.requireNonNull(startedProcesses, workSpace);
		
		this.workSpace = workSpace;
		this.startedProcesses = startedProcesses;
	}
	
	@Override
	public void run() {
		while(true){
			this.startedProcesses.parallelStream().forEach(process ->{
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(process.getProcess() != null && !process.isAlive()){
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
			});
		}
	}
}
