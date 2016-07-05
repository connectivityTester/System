package editors;
import com.perforce.p4java.server.IServer;
import utils.Utils;

/**
 * Edit "TcfgVersion.txt" file
 *  
 * @author dkuschevoy
 *
 */
public class TConnectivityVersionEditor extends FileEditor{
	
	private final String [] args;
	
	public TConnectivityVersionEditor( final IServer server, final String fileName, final String ... args){
		super(server, fileName);
		this.args = args;
	}

	@Override
	/**
	 * @param filePath - path to file
	 * @return - content of "TcfgVersion.txt"	  
	 */
	protected String getFileContent(final String filePath) {
		String connectivityLabel 	= args[0];
		String wicomeLabel 			= args[1];
		String syncCompRevision		= args[2];
		String syncCompSpecRevision	= args[3];
		String syncCompWicomeRevis	= args[4];
		String dis2Common			= args[5];
		String dis2Tuner			= args[6];
		String dis2SCP				= args[7];
		String apiBinary			= args[8];
		String apiSource			= args[9];
		String binBinary			= args[10];
		String binSource			= args[11];
		String etcBinary			= args[12];
		String etcSource			= args[13];
		String libBinary			= args[14];
		String libSource			= args[15];
		
		return  "-= TCFG INFO =-\n" + 
				"Config Type:                       Development\n" +
				"Creation Date:                     " + Utils.getUsualDate() + " (" +  Utils.getHarmanDate() + "A)\n" +
				"\n" +
				"-= LABELS =-\n" +
				"Connectivity Label:                " + connectivityLabel + "\n" +
				"Wicome Label:                      " + wicomeLabel + "\n" +
				"\n" +
				"-= FILE REVISIONS =-\n" +
				"SyncComposition.p4sm:              " + syncCompRevision +"\n" +
				"SyncComposition_Specific.p4sm:     " + syncCompSpecRevision + "\n" +
				"SyncComposition_Wicome.p4sm:       " + syncCompWicomeRevis + "\n" +
				"DIS2_common.p4inc:                 " + dis2Common + "\n" +
				"DIS2_MM_Tuner.p4inc:               " + dis2Tuner + "\n" +
				"DIS2_SCP.p4inc:                    " + dis2SCP + "\n" +
				"\n" +
				"-= BINARY CHANGELISTS =-\n" +
				"Interfaces (api)                   " + apiBinary + " (" + apiSource + ")\n" +
				"Binaries   (bin)                   " + binBinary + " (" + binSource + ")\n" +
				"Used files (etc)                   " + etcBinary + " (" + etcSource + ")\n" +
				"Libraries  (lib)                   " + libBinary + " (" + libSource + ")";
	}

}
