package editors;

import gui.MessageType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import com.perforce.p4java.server.IServer;

import static controller.Controller.controller; 

/**
 * Base class that describes a logic and sequence 
 * 		of applying changes in integration files.
 * Changes can be applied by method {@link #editFile()}
 * 
 * Inherited class should implement abstract and protected method {@link #getFileContent(String)}
 * 
 * @author dkuschevoy
 *
 */
public abstract class FileEditor {

	protected final IServer server;
	protected final String fileName;
	
	protected FileEditor(final IServer server, final String fileName) {
		this.server = server;
		this.fileName = fileName;
	}

	/**
	 * It is template method. It describes algorithm getting and applying changes:
	 * 	1. Get path to file
	 * 	2. Get edited or new file content by path
	 * 	3. Write new content to file and save changes
	 */
	public void editFile() {
		String filePath = server.getCurrentClient().getRoot()  + fileName;
		controller.showLogMessage("Checking '" + filePath + "'...", MessageType.OK);
		String newFileContent = getFileContent(filePath);
		writeNewContentToFile(filePath, newFileContent);
		controller.showLogMessage("'" + filePath + "' was checked\n", MessageType.OK);
	}

	/**
	 * Write new file content to file
	 * 
	 * @param filePath - path to appropriated file
	 * @param newFileContent - new file content 
	 */
	private void writeNewContentToFile(final String filePath, final String newFileContent) {
		try (Writer bufferedWriter = new BufferedWriter(new FileWriter(new File(filePath)))) {
			bufferedWriter.write(newFileContent);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method should return new or edited file content
	 *  
	 * @param filePath - file path
	 * @return - new or edited file content
	 */
	protected abstract String getFileContent(String filePath);
}
