package editors;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utils.P4Utils;
import com.perforce.p4java.server.IServer;

/**
 * Edit SyncComposition_UsedExternalIf.p4sm file
 * SyncComposition_UsedExternalIf.p4sm synchronizes described depots to latest revision
 *  
 * @author dkuschevoy
 *
 */
public class DSyncCompositionExternalIfEditor extends FileEditor {

	private final IServer server3501;
	private final Pattern pattern = Pattern.compile(".*(//.*)//.*");

	@SafeVarargs
	public DSyncCompositionExternalIfEditor( final IServer server, final String filePath, final IServer ... args){
		super(server, filePath);
		server3501 = args[0];
	}

	@Override
	/**
	 * This method reads the file line by line,
	 *  if line contains of path to depot - ask server for latest revision for this path,
	 *  gotten resivion is applied for current line
	 *  
	 *  @param filePath - path to file
	 *  @return - edited file content
	 */
	protected String getFileContent(final String filePath) {
		String line;
		StringBuilder resultString = new StringBuilder();
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) 
		{
			while ((line = bufferedReader.readLine()) != null) {
				//if line contains of path and CL
				if (line.contains("@")) {
					/**
					 * Example of changes
					 * 
					 * ......./dis2/carfunctions/timemgr/...            @1239185
					 * ......./dis2/carfunctions/timemgr/...            @1241123
					 * 
					 */
					Matcher matcher = pattern.matcher(line);
					matcher.find();
					String usedFilePath = matcher.group(1).trim();
					String lastChangeList = P4Utils.getLastChangeList(server3501, usedFilePath);
					resultString.append(line.substring(0, line.indexOf('@') + 1) + lastChangeList + "\n");
				} else {
					resultString.append(line + "\n");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultString.toString();
	}
}
