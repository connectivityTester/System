package connections.devicesources;

import java.io.IOException;
import java.net.Socket;
import java.util.Objects;

import common.DeviceSource;

public class DeviceSourceConnection implements Runnable{
	
	private final DeviceSourceInputConnection inputConnection;
	private final DeviceSourceOutputConnection outputConnection;
	private final DeviceSource deviceSource;
	private final Socket deviceSourceSocket;
	
	DeviceSourceConnection(final Socket socket, final DeviceSource deviceSource,
							final DeviceSourceConnectionController controller) throws IOException
	{
		Objects.requireNonNull(socket);
		Objects.requireNonNull(controller);
		
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
		final Thread inputConnectionThread = new Thread(this.inputConnection);
		inputConnectionThread.setPriority(Thread.MIN_PRIORITY);
		inputConnectionThread.start();
	}

	public DeviceSource getDeviceSource() {
		return deviceSource;
	}
	
	public void sendDataToDeviceSource(final String dataString) throws IOException{
		Objects.requireNonNull(dataString);
		
		this.outputConnection.sendDataToDeviceSource(dataString);
	}
	
	public boolean isDeviceSourceConnected(){
		return this.deviceSourceSocket.isConnected();
	}
	
	public boolean equalsDeviceId(final int deviceId){
		return this.deviceSource.getId() == deviceId;
	}

}
