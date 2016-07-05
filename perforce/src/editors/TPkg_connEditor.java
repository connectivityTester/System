package editors;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

import com.perforce.p4java.server.IServer;

/**
 * Generate content of pkg_conn.p4inc
 *  
 * @author dkuschevoy
 *
 */
public class TPkg_connEditor extends FileEditor{
	
	private final static String apiRegPath = ".*api/\\.\\.\\..*@[0-9]+";
	private final static String binRegPath = ".*bin/\\.\\.\\..*@[0-9]+";
	private final static String etcRegPath = ".*etc/\\.\\.\\..*@[0-9]+";
	private final static String libRegPath = ".*lib/\\.\\.\\..*@[0-9]+";
	private final static String wicomePath = ".*@[a-zA-Z][a-zA-Z_0-9]*";
	
	private final String [] args;

	public TPkg_connEditor(final IServer server, final String fileName, final String ... args){
		super(server, fileName);
		this.args = args;
	}

	@Override
	/**
	 * This method reads the file line by line
	 *	and replaces old CL for "api", "bin", "etc", "lib" depots 
	 *  and wicome label on new values  
	 *  
	 *  @param filePath - path to file
	 *  @return - edited file content
	 */
	protected String getFileContent(final String filePath) {
		String apiCL = args[0];
		String binCL = args[1];
		String etcCL = args[2];
		String libCL = args[3];
		String wicomeLabel = args[4];		
		
		String line;
		StringBuilder resultString = new StringBuilder();
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))){
			while ((line = bufferedReader.readLine()) != null) {
				if(Pattern.matches(wicomePath, line)){
					resultString.append(line.substring(0, line.indexOf("@")+1) + wicomeLabel + "\n");
					continue;
				}
				if(Pattern.matches(apiRegPath, line)){
					resultString.append(line.substring(0, line.indexOf("@")+1) + apiCL + "\n");
					continue;
				}
				if(Pattern.matches(binRegPath, line)){
					resultString.append(line.substring(0, line.indexOf("@")+1) + binCL + "\n");
					continue;
				}
				if(Pattern.matches(etcRegPath, line)){
					resultString.append(line.substring(0, line.indexOf("@")+1) + etcCL + "\n");
					continue;
				}
				if(Pattern.matches(libRegPath, line)){
					resultString.append(line.substring(0, line.indexOf("@")+1) + libCL + "\n");
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
