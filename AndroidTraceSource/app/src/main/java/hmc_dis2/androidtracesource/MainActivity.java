package hmc_dis2.androidtracesource;
import android.os.StrictMode;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;

import android.text.format.Formatter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import hmc_dis2.androidtracesource.connections.ConnectionConfig;
import hmc_dis2.androidtracesource.connections.WiFiConnectionManager;
import hmc_dis2.androidtracesource.io.Reader;
import hmc_dis2.androidtracesource.io.Writer;

public class MainActivity extends AppCompatActivity {

    private TextView statusField;
    private WiFiConnectionManager wiFiConnectionManager;
    private Button connectionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_main);
        WifiManager wifiManager = (WifiManager)this.getSystemService(WIFI_SERVICE);
		String localIpAddress = Formatter.
				formatIpAddress(wifiManager.
                        getConnectionInfo().
                        getIpAddress());
		if(localIpAddress.equals("0.0.0.0")){
			this.showDialog();
		}
        this.wiFiConnectionManager = new WiFiConnectionManager(this);
        TextView localAddressView = (TextView) findViewById(R.id.localAddressField);
        localAddressView.setText(localIpAddress);
        Reader reader = new Reader();
        final ConnectionConfig connectionConfig = reader.readConnectionConfig();
        final EditText serverAddressField = (EditText) findViewById(R.id.serverAddressField);
        serverAddressField.setGravity(Gravity.CENTER);
        serverAddressField.setText(connectionConfig.getIpAddress());
        final EditText serverPortField = (EditText) findViewById(R.id.serverPortField);
        serverPortField.setGravity(Gravity.CENTER);
        serverPortField.setText(connectionConfig.getPort()+"");
        this.statusField = (TextView) findViewById(R.id.connectionStatusField);
        this.connectionButton = (Button) findViewById(R.id.connectionButton);
        this.connectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connectionButton.getText().equals("Connect")){
                    ConnectionConfig newConnectionConfig =
                            new ConnectionConfig(
                                    serverAddressField.getText().toString(),
                                    Integer.parseInt(serverPortField.getText().toString()));
                    Writer writer = new Writer();
                    writer.writeConnectionConfig(newConnectionConfig);
                    boolean result = wiFiConnectionManager.startServerConnection(newConnectionConfig);
                    Log.i("MainActivity::", "Function onCreate, server connection was started with result: " + result);
                    updateConnectionStatus(result);
                }
                else {
                    boolean result = wiFiConnectionManager.stopServerConnection();
                    Log.i("MainActivity::", "Function onCreate, server connection was stopped with result: " + !result);
                    updateConnectionStatus(result);
                }
            }
        });
    }

    public void showDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("WI-FI Connection");
        alertDialogBuilder
                .setMessage("Please connect to WI-FI network and lauch application again!")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.this.finish();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void updateConnectionStatus(boolean status){
        if(status) {
            this.statusField.setText("Connected");
            this.connectionButton.setText("Disconnect");
        }
        else{
            this.statusField.setText("Disconnected");
            this.connectionButton.setText("Connect");
        }
    }
}
