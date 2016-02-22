import java.util.regex.Pattern;
import connections.ServerConnection;

public class main {
	
	private static final Pattern ipAddressPattern = Pattern.compile(
	        "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

	public static void main(String ... args){
		new ServerConnection();
	}
}
