package starters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import utils.Logger;
import utils.Utils;

public class DeviceSourceProcessTraceLogger implements Runnable {
	
	private final Reader processTraceReader;
	private final DeviceSourceProcess devicesourceProcess;  
	private final String deviceSourcePrefix;
	
	DeviceSourceProcessTraceLogger(final DeviceSourceProcess deviceSourceProcess) {
		Utils.requireNonNull(deviceSourceProcess);
		
		InputStream inputStream = deviceSourceProcess.getProcess().getInputStream();
		this.processTraceReader = new BufferedReader(new InputStreamReader(inputStream));
		this.devicesourceProcess = deviceSourceProcess;
		this.deviceSourcePrefix = "Device source \"" 
				+ deviceSourceProcess.getDeviceSource().getName() 
				+ "\"("  + deviceSourceProcess.getDeviceSource().getId() +"): ";
	}
	
	@Override
	public void run() {
		char [] traceData = new char[1000]; 
		int readChars = 0;
		while(devicesourceProcess.isAlive()){
			try {
				readChars = this.processTraceReader.read(traceData);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			if(readChars > 0){
				Logger.logFromDeviceSource((this.deviceSourcePrefix  + new String(traceData, 0, readChars)).replace("\n", ""));
				traceData = new char[1000];
			}
		}
	}

}
