package connectivity.androiddevicesource.io;

import android.os.Environment;

import java.io.File;

/**
 * Created by Администратор on 16.01.2016.
 */
public class IOConstants {
    static final String configFileName = "/system_config.txt";
    static final String defaultIPAddress = "192.168.0.5";
    static final int port = 65789;
    static final File configDirectoryName = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
}
