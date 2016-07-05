package editors;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.perforce.p4java.server.IServer;

/**
 * Edit SyncComposition_Wicome.p4sm file
 * SyncComposition_Wicome.p4sm synchronizes latest Wicome label and features
 *  
 * @author dkuschevoy
 *
 */

public class DSyncCompositionWicomeEditor extends FileEditor {

	private final String wicomeLabel;

	public DSyncCompositionWicomeEditor( final IServer server, final String fileName, final String... args){
		super(server, fileName);
		wicomeLabel = args[0];
	}

	@Override
	/**
	 * This method reads the file line by line,
	 *  if line contains of Wicome label - replace it on new label,
	 *  new label is provided from GUI by user
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
					resultString.append(line.substring(0, line.indexOf('@') + 1) + wicomeLabel + "\n");
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
