package starters;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import utils.Logger;

public class DeviceSourceProcessTraceLogger implements Runnable {
	
	private final Reader processTraceReader;
	private final DeviceSourceProcess devicesourceProcess;  
	private final String deviceSourcePrefix;
	
	DeviceSourceProcessTraceLogger(DeviceSourceProcess deviceSourceProcess) {
		this.processTraceReader = new BufferedReader(new InputStreamReader(deviceSourceProcess.getProcess().getInputStream()));
		this.devicesourceProcess = deviceSourceProcess;
		this.deviceSourcePrefix = "Device source \"" 
				+ deviceSourceProcess.getDeviceSource().getName() 
				+ "\"("  + deviceSourceProcess.getDeviceSource().getId() +"): ";
	}
	
	@Override
	public void run() {
		while(this.devicesourceProcess.getProcess().isAlive()){
			char [] traceData = new char[1000]; 
			int readChars = 0;
			try {
				readChars = this.processTraceReader.read(traceData);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			if(readChars > 0){
				Logger.logFromDeviceSource((this.deviceSourcePrefix  + new String(traceData, 0, readChars)).replace("\n", ""));
			}
		}
	}

}
