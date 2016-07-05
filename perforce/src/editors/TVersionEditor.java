package editors;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.perforce.p4java.server.IServer;

/**
 * Edit "TCFgVersion.txt" file 
 * 
 * @author dkuschevoy
 *
 */
public class TVersionEditor extends FileEditor{

	private final String connectivityData;
	
	public TVersionEditor(final IServer server, final String fileName, final String connectivityData) 
	{
		super(server, fileName);
		//Connectivity:          4647134   1289549   20.05.2016   16205A
		this.connectivityData = connectivityData;
	}

	@Override
	/**
	 * This method reads file line by line 
	 * 	and replace Connectivity data on new value 
	 * 
	 * 	@param filePath - path to file
	 *  @return - edited file content
	 */
	protected String getFileContent(final String filePath) {
		String line;
		StringBuilder resultString = new StringBuilder();
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) 
		{
			while ((line = bufferedReader.readLine()) != null) {
				if(line.toLowerCase().contains("Connectivity".toLowerCase())){
					resultString.append(connectivityData + "\n");
				}
				else{
					resultString.append(line + "\n");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultString.toString();
	}

}
