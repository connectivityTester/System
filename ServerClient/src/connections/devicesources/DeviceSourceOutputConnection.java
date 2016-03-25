package connections.devicesources;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

import common.DeviceSource;
import types.LogLevels;
import utils.Logger;

public class DeviceSourceOutputConnection{
	
    private final OutputStream outputStream;
    private final DeviceSource deviceSource;
    
	public DeviceSourceOutputConnection(final OutputStream outputStream, final DeviceSource deviceSource) {
		Objects.requireNonNull(outputStream);
		Objects.requireNonNull(deviceSource);
		
		this.deviceSource = deviceSource;
		this.outputStream = outputStream;
	}
	
	public void sendDataToDeviceSource(String testDataString) throws IOException{
		Objects.requireNonNull(testDataString);
		
		Logger.log(LogLevels.TRACE, this, "Method sendDataToDeviceSource, message to send: " + testDataString);
		this.outputStream.write(testDataString.getBytes(), 0, testDataString.length());
		this.outputStream.flush();
	}

	public DeviceSource getDeviceSource() {	return this.deviceSource;}	
	
}
