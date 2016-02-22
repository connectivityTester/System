package connectivity.androiddevicesource.connections;

/**
 * Created by Администратор on 16.01.2016.
 */
public class ConnectionConfig {

    private String ipAddress;
    private int port;

    @Override
    public String toString() {
        return "ConnectionConfig{" +
                "ipAddress='" + ipAddress + '\'' +
                ", port=" + port +
                '}';
    }

    public int getPort() {
        return port;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public ConnectionConfig(String ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }

}
