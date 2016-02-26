package hmc_dis2.androidtracesource.io;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import hmc_dis2.androidtracesource.connections.ConnectionConfig;

public class Reader {
    private File fileToRead;

    public Reader(){
        Log.i("Reader::", "Constructor started");
        if(!IOConstants.configDirectoryName.exists()){
            Log.i("Reader::", "Constructor, Directory does not exist");
            try {
                boolean result = IOConstants.configDirectoryName.createNewFile();
                Log.i("Reader::", "Constructor, Directory was created with result: " + result);
            } catch (IOException e) {
                Log.i("Reader::", "Constructor, Error during creation directory: " + e.getLocalizedMessage());
            }
        }
        File file = new File(IOConstants.configDirectoryName, IOConstants.configFileName);
        if(file.exists()){
            this.fileToRead = file;
            Log.i("Reader::", "Constructor, configuration file exists");
        }
        else{
            this.fileToRead = null;
            Log.i("Reader::", "Constructor, configuration file does not exist");
        }
        Log.i("Reader::", "Constructor finished");
    }

    public ConnectionConfig readConnectionConfig(){
        Log.i("Reader::", "Function readIPAddress started");
        ConnectionConfig connectionConfig = null;
        if(this.fileToRead != null) {
            try {
                BufferedReader bw = new BufferedReader( new InputStreamReader( new FileInputStream(this.fileToRead)));
                String ipAddress = bw.readLine();
                int port = Integer.parseInt(bw.readLine());
                connectionConfig = new ConnectionConfig(ipAddress, port);
                bw.close();
            } catch (FileNotFoundException e) {
                Log.i("Reader::", "Function readIPAddress, configuration was not found");
                Log.i("Reader::", "Function readIPAddress, " + e.getLocalizedMessage());
            } catch (IOException e) {
                Log.i("Reader::", "Function readIPAddress, I/O problem during reading");
                Log.i("Reader::", "Function readIPAddress, " + e.getLocalizedMessage());
            }
        }
        else{
            connectionConfig = new ConnectionConfig(IOConstants.defaultIPAddress, IOConstants.port);
        }
        Log.i("Reader::", "Function readIPAddress, Connection config: " + connectionConfig.toString());
        Log.i("Reader::", "Function readIPAddress finished");
        return connectionConfig;
    }
}
