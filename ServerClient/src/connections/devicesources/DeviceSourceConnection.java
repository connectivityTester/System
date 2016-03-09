package connections.devicesources;

import java.io.IOException;
import java.net.Socket;
import common.DeviceSource;

public class DeviceSourceConnection implements Runnable{
	
	private final DeviceSourceInputConnection inputConnection;
	private final DeviceSourceOutputConnection outputConnection;
	private final DeviceSource deviceSource;
	private final Socket deviceSourceSocket;
	
	DeviceSourceConnection(Socket socket, DeviceSource deviceSource, DeviceSourceConnectionController controller) throws IOException{
		this.deviceSource = deviceSource;
		this.deviceSourceSocket = socket;
		this.inputConnection = new DeviceSourceInputConnection(this.deviceSource, socket.getInputStream(), controller);
		if(deviceSource != null){
			this.outputConnection = new DeviceSourceOutputConnection(socket.getOutputStream(), deviceSource);
		}
		else{
			this.outputConnection = null;
		}
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
	
	public void sendDataToDeviceSource(String dataString) throws IOException{
		this.outputConnection.sendDataToDeviceSource(dataString);
	}
	
	public boolean isDeviceSourceConnected(){
		return this.deviceSourceSocket.isConnected();
	}

}
