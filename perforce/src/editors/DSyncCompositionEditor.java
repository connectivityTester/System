package editors;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;

import com.perforce.p4java.server.IServer;

/**
 * Edit SyncComposition.p4sm file for domain
 * 
 * Replace old revisions on new in DIS2_common.p4inc and DIS2_MM_Tuner.p4inc
 * 
 * @author dkuschevoy
 *
 */
public class DSyncCompositionEditor extends FileEditor {

	private final int lastRevisionDIS2_common;
	private final int lastRevisionDIS2_MM_Tuner;

	public DSyncCompositionEditor( final IServer server, final String filePath, 
									final int lastRevisionDIS2_common, final int lastRevisionDIS2_MM_Tuner) {
		super(server, filePath);
		this.lastRevisionDIS2_common =  lastRevisionDIS2_common;
		this.lastRevisionDIS2_MM_Tuner = lastRevisionDIS2_MM_Tuner;
	}
	
	@Override
	protected String getFileContent(final String filePath) {
		String line;
		StringBuilder resultString = new StringBuilder();
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) 
		{
			while ((line = bufferedReader.readLine()) != null) {
				// current line is not a comment
				if (!line.startsWith("#")) {
					// current line contains path to DIS2_common.p4inc
					if (line.contains("DIS2_common.p4inc")) {
						/*
						 * Example of changes
						 * 	............/config/DIS2_common.p4inc                 #199
						 * 	............/config/DIS2_common.p4inc                 #200
						 */
						resultString.append(line.substring(0, line.indexOf('#') + 1) + lastRevisionDIS2_common + "\n");
						continue;
					}
					// current line contains path to DIS2_MM_Tuner.p4inc
					if (line.contains("DIS2_MM_Tuner.p4inc")) {
						/*
						 * Example of changes
						 * 	............/config/DIS2_MM_Tuner.p4inc                 #23
						 * 	............/config/DIS2_MM_Tuner.p4inc                 #24
						 */
						resultString.append(line.substring(0, line.indexOf('#') + 1) + lastRevisionDIS2_MM_Tuner + "\n");
						continue;
					}
				}
				resultString.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultString.toString();
	}

}
