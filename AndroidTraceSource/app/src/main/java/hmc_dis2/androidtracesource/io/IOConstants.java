package hmc_dis2.androidtracesource.io;

import android.os.Environment;
import java.io.File;

public class IOConstants {
    static final String configFileName = "/system_config.txt";
    static final String defaultIPAddress = "192.168.0.105";
    static final int port = 1268;
    static final File configDirectoryName = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
}
