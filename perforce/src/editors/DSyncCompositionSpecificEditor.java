package editors;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.perforce.p4java.server.IServer;

/**
 * Edit SyncComposition_Specific.p4sm file
 * SyncComposition_Specific.p4sm is used for label creation
 *  
 * @author dkuschevoy
 *
 */
public class DSyncCompositionSpecificEditor extends FileEditor {

	private final String connectivityLabel;

	public DSyncCompositionSpecificEditor( final IServer server, final String fileName, final String connectivityLabel){
		super(server, fileName);
		this.connectivityLabel = connectivityLabel;
	}

	@Override
	/**
	 * This method reads the file line by line,
	 *  if line contains of current label - replace it on new
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
				if (line.contains("@")) {
					resultString.append(line.substring(0, line.indexOf("@") + 1) + connectivityLabel + "\n");
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
