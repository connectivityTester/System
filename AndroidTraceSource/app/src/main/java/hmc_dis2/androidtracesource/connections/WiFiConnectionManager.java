package hmc_dis2.androidtracesource.connections;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import android.util.Log;

import hmc_dis2.androidtracesource.MainActivity;


public class WiFiConnectionManager{

	private Thread activeConnection;
	private MainActivity activity;
    private Socket connectionSocket;

	public WiFiConnectionManager(MainActivity activity) {
		Log.i("WiFiConnectionManager::", "Constructor started");
		this.activity = activity;
		Log.i("WiFiConnectionManager::", "Constuctor finished");
	}
	public boolean startServerConnection(ConnectionConfig connectionConfig){
        Log.i("WiFiConnectionManager::", "Function startServerConnection started");
        boolean result = true;
		if(this.activeConnection == null){
            try {
                Log.i("WiFiConnectionManager::", "Function startServerConnection, " + connectionConfig.toString());
                this.connectionSocket = new Socket(connectionConfig.getIpAddress(), connectionConfig.getPort());
                Log.i("WiFiConnectionManager::", "Function startServerConnection, server socket was created");
                this.activeConnection = new Thread(new WiFiConnection(this.connectionSocket, this.activity, this));
                this.activeConnection.start();
                Log.i("WiFiConnectionManager::", "Function startServerConnection, wi-fi connection to server was started");
            }catch (UnknownHostException e) {
                Log.i("WiFiConnectionManager::", "Function startServerConnection, unknown host was requested");
                Log.i("WiFiConnectionManager::", "Function startServerConnection, " + e.getLocalizedMessage());
                result = false;
            }catch (IOException e) {
                Log.i("WiFiConnectionManager::", "Function startServerConnection, some I/O problems with socket");
                Log.i("WiFiConnectionManager::", "Function startServerConnection, " + e.getLocalizedMessage());
                result = false;
            }
        }
        Log.i("WiFiConnectionManager::", "Function startServerConnection finished");
        return  result;
	}

	public boolean stopServerConnection(){
        Log.i("WiFiConnectionManager::", "Function stopServerConnection started");
        boolean result = false; // connection status will be updated to "Disconnected"
        if(this.activeConnection != null){
            try {
                this.connectionSocket.close();
            } catch (IOException e) {
                Log.i("WiFiConnectionManager::", "Function stopServerConnection, i/o problem with closing socket");
                Log.i("WiFiConnectionManager::", "Function stopServerConnection, " + e.getLocalizedMessage());
            }
            this.activeConnection.interrupt();
            this.activeConnection = null;
        }
        Log.i("WiFiConnectionManager::", "Function stopServerConnection finished");
        return result;
	}
}
