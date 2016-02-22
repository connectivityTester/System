package connectivity.androiddevicesource.io;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import connectivity.androiddevicesource.connections.ConnectionConfig;

public class Writer {

    private File fileToRead;

    public Writer(){
        Log.i("Writer::", "Constructor started");
        File file = new File(IOConstants.configDirectoryName, IOConstants.configFileName);
        if(file.exists()){
            this.fileToRead = file;
            Log.i("Writer::", "Constructor, configuration file exists");
        }
        else{
            Log.i("Writer::", "Constructor, configuration file does not exist");
            try {
                File dir = new File(IOConstants.configDirectoryName.getPath());
                if(dir.mkdirs()){
                    Log.i("Writer::", "Constructor, configuration file directory was created");
                }
                else{
                    Log.i("Writer::", "Constructor, configuration file directory was not created!");
                }
                file = new File(dir, IOConstants.configFileName);
                if(file.createNewFile()){
                    Log.i("Writer::", "Constructor, configuration file was created");
                }
                else {
                    Log.i("Writer::", "Constructor, configuration file was not created!");
                }
                this.fileToRead = file;
            } catch (IOException e) {
                Log.i("Writer::", "Constructor, configuration file can not be created!");
                Log.i("Writer::", "Constructor, " + e.getLocalizedMessage());
            }
        }
        Log.i("Writer::", "Constructor finished");
    }

    public void writeConnectionConfig(ConnectionConfig connectionConfig){
        Log.i("Writer::", "Function writeIPAddress started");
        Log.i("Writer::", "Function writeIPAddress, IP address: " + connectionConfig.getIpAddress());
        Log.i("Writer::", "Function writeIPAddress, port: " + connectionConfig.getPort());
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter( new FileOutputStream(this.fileToRead)));
            Log.i("Writer::", "Function writeIPAddress, file and stream was opened");
            bw.write(connectionConfig.getIpAddress());
            bw.write("\n");
            bw.write(connectionConfig.getPort()+"");
            Log.i("Writer::", "Function writeIPAddress, connection config was written");
            bw.close();
            Log.i("Writer::", "Function writeIPAddress, file and stream was closed");
        } catch (FileNotFoundException e) {
            Log.i("Writer::", "Function writeIPAddress, configuration was not found");
            Log.i("Writer::", "Function writeIPAddress, " + e.getLocalizedMessage());
        } catch (IOException e) {
            Log.i("Writer::", "Function writeIPAddress, I/O problem during reading");
            Log.i("Writer::", "Function writeIPAddress, " + e.getLocalizedMessage());
        }
        Log.i("Writer::", "Function writeIPAddress finished");
    }
}
