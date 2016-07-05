package editors;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.perforce.p4java.server.IServer;

/**
 * Generate content of SyncComposition.p4sm for TCFG
 *  
 *  Increment "pkg_conn.p4inc" file revision
 *  
 * @author dkuschevoy
 *
 */

public class TSyncCompositionEditor extends FileEditor{
	
	private static final Pattern PATTERN = Pattern.compile(".*pkg_conn.*#([0-9]+)");

	public TSyncCompositionEditor(final IServer server, final String fileName) {
		super(server, fileName);
	}

	@Override
	/**
	 * Increment "pkg_conn.p4inc" file revision
	 * 
	 * @param filePath - path to file
	 * @return - edited file content
	 */
	protected String getFileContent(final String filePath){
		String line;
		StringBuilder resultString = new StringBuilder();
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) 
		{
			while ((line = bufferedReader.readLine()) != null) {
				if(line.contains("pkg_conn")){
					Matcher matcher = PATTERN.matcher(line);
					matcher.find();
					int pkgConnRevision = Integer.parseInt(matcher.group(1))+1;
					resultString.append(line.substring(0, line.indexOf("#") + 1) + pkgConnRevision + "\n");
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
