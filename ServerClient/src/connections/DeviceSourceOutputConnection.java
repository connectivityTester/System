package connections;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import common.DeviceSource;
import types.LogLevels;
import utils.Logger;

public class DeviceSourceOutputConnection{
	
    private final DataOutputStream out;
    private final DeviceSource deviceSource;
    
	public DeviceSourceOutputConnection(Socket incomingSocketConnection, DeviceSource deviceSource) {
		this.deviceSource = deviceSource;
		DataOutputStream outputStream = null;
		try {
			outputStream = new DataOutputStream(incomingSocketConnection.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.out = outputStream;
	}
	
	
	
	public boolean sendDataToDeviceSource(String testDataString){
		Logger.log(LogLevels.TRACE, this, "Method sendDataToDeviceSource, message to send: " + testDataString);
		try {
			this.out.writeUTF(testDataString);
			this.out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	public DeviceSource getDeviceSource() {	return this.deviceSource;}	
	
}
