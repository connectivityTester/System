package hmc_dis2.androidtracesource.connections;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import android.util.Log;

import hmc_dis2.androidtracesource.MainActivity;
import hmc_dis2.androidtracesource.commands.CommandResult;
import hmc_dis2.androidtracesource.executors.CommandExecutor;

public class WiFiConnection implements Runnable {
	private DataInputStream input;
	private DataOutputStream output;
	private MainActivity activity;
	private WiFiConnectionManager wiFiConnectionManager;

	public WiFiConnection (Socket socket, MainActivity activity, WiFiConnectionManager wiFiConnectionManager ){
		Log.i("WiFiConnection::", "Constructor started");
		this.activity = activity;
		this.wiFiConnectionManager = wiFiConnectionManager;
		try {
			this.input = new DataInputStream(socket.getInputStream());
			this.output = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		Log.i("WiFiConnection::", "Constructor finished");
	}

	@Override
	public void run() {
		Log.i("WiFiConnection::", "Function RUN started");
		try {
			Log.i("WiFiConnection:: ", "waiting for data....");
			while (true){
				String receivedData = this.input.readUTF();
				Log.i("WiFiConnection:: ", "Data was received!");
				Log.i("WiFiConnection:: ", "Received data is: " + receivedData);
				CommandResult result = CommandExecutor.getInstance(this.activity).executeCommand(receivedData);
				Log.i("WiFiConnection:: ", "Answer to write: " + result.getJSONString());
				StringBuilder builder = new StringBuilder(result.getJSONString());
//				for(int i = 0; i < 99999; ++i)
//				{
//					builder.append(result.getJSONString());
//				}
				this.output.writeBytes(builder.toString());
				this.output.flush();
				Log.i("WiFiConnection:: ", "answer is written");
				Log.i("WiFiConnection:: ", "waiting for new data....");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		final boolean result = this.wiFiConnectionManager.stopServerConnection();
		Log.i("WiFiConnection::", "Function RUN, server connection was stopped with result: " + !result);
		this.activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				activity.updateConnectionStatus(result);
			}
		});
		Log.i("WiFiConnection::", "Function RUN, connection status was updated");
		Log.i("WiFiConnection::", "Function RUN finished");
	}

}
