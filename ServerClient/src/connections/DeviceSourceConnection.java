package connections;

import java.net.Socket;
import common.DeviceSource;

public class DeviceSourceConnection implements Runnable{
	
	private final DeviceSourceInputConnection inputConnection;
	private final DeviceSourceOutputConnection outputConnection;
	private final DeviceSource deviceSource;
	private final Socket deviceSourceSocket;
	
	DeviceSourceConnection(Socket socketConnection, DeviceSource deviceSource, DeviceSourceConnectionController controller){
		this.deviceSource = deviceSource;
		this.inputConnection = new DeviceSourceInputConnection(this.deviceSource, socketConnection, controller);
		if(deviceSource != null){
			this.outputConnection = new DeviceSourceOutputConnection(socketConnection, deviceSource);
		}
		else{
			this.outputConnection = null;
		}
		this.deviceSourceSocket = socketConnection;
	}
	
	@Override
	public void run() {
		Thread inputConnectionThread = new Thread(this.inputConnection);
		inputConnectionThread.setPriority(Thread.MIN_PRIORITY);
		inputConnectionThread.start();
	}

	public DeviceSource getDeviceSource() {
		return deviceSource;
	}
	
	public boolean sendDataToDeviceSource(String dataString){
		return this.outputConnection.sendDataToDeviceSource(dataString);
	}
	
	public boolean isDeviceSourceConnected(){
		return this.deviceSourceSocket.isConnected();
	}

}
