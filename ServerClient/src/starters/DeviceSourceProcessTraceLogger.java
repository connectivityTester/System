package starters;

import java.io.DataInputStream;
import java.io.IOException;
import utils.Logger;

public class DeviceSourceProcessTraceLogger implements Runnable {
	
	private final DataInputStream deviceSourceProcessInputStream;  

	DeviceSourceProcessTraceLogger(DeviceSourceProcess deviceSourceProcess) {
		this.deviceSourceProcessInputStream = new DataInputStream(deviceSourceProcess.getProcess().getInputStream());
	}
	
	@Override
	public void run() {
		while(true){
			String logMessage = null;
			try {
				logMessage = this.deviceSourceProcessInputStream.readUTF();
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
			if(logMessage != null){
				Logger.logFromDeviceSource(logMessage);
			}
		}
	}

}
