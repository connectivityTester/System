package utils;
import gui.MessageType;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import logic.Properties;

import com.perforce.p4java.client.IClient;
import com.perforce.p4java.core.IChangelist;
import com.perforce.p4java.core.ILabel;
import com.perforce.p4java.core.ILabelMapping;
import com.perforce.p4java.core.ILabelSummary;
import com.perforce.p4java.core.ViewMap;
import com.perforce.p4java.core.file.FileSpecBuilder;
import com.perforce.p4java.core.file.IFileRevisionData;
import com.perforce.p4java.core.file.IFileSpec;
import com.perforce.p4java.exception.P4JavaException;
import com.perforce.p4java.impl.generic.core.Changelist;
import com.perforce.p4java.impl.generic.core.Label;
import com.perforce.p4java.option.server.OpenedFilesOptions;
import com.perforce.p4java.server.IServer;

import static controller.Controller.controller;

public class P4Utils {

	private static final String STOP_SYNC_EXP 	= "Operation finished with";
	private static final String alphabet 		= "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String LABEL_REGEXP 	= ".*Wicome Label:\\s+([A-Za-z_0-9]+).*";
	
	public static IChangelist createPendingCL(final IServer server, final String newDescription) {
		IChangelist pendingCL = new Changelist();
		pendingCL.setServer(server);
		pendingCL.setClientId(server.getCurrentClient().getName());
		pendingCL.setDescription(newDescription);
		try {
			pendingCL = server.getCurrentClient().createChangelist(pendingCL);
		} catch (Exception e) {
			pendingCL = null;
			e.printStackTrace();
		}
		return pendingCL;
	}
	
	public static IServer switchWorkspace(final IServer server, final String workspace) {
		IClient currentClient;
		try {
			currentClient = server.getClient(workspace);
			server.setCurrentClient(currentClient);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return server;
	}

	public static String areFilesAvailable(final IServer server, final List<IFileSpec> files) {
		String openedFilePath = null;
		for (IFileSpec file : files) {
			try {
				List<IFileSpec> openedFile = server.getCurrentClient()
						.openedFiles(Arrays.asList(file),	new OpenedFilesOptions());
				if (openedFile != null && openedFile.size() > 0) {
					openedFilePath = openedFile.get(0).toString();
					break;
				}
			} catch (P4JavaException e) {
				e.printStackTrace();
			}
		}
		return openedFilePath;
	}
	
	public static int getLastFileRevision(final IServer server, final String filePath)
	{
		int lastRevision = 0;
		List<IFileSpec> file = FileSpecBuilder.makeFileSpecList(filePath);
		Map<IFileSpec, List<IFileRevisionData>> revisionDataMap = null;
		try {
			revisionDataMap = server.getRevisionHistory(file, 1, false, true, false, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Entry<IFileSpec, List<IFileRevisionData>> entry : revisionDataMap.entrySet()) {
			lastRevision = entry.getValue().get(0).getRevision();
			break;
		}
		return lastRevision;
	}
	
	public static String getCLFromDescription(final IServer server, final String filePath, final String regexp){
		IChangelist changelist = null;
		try {
			changelist = server.getChangelist(Integer.parseInt(P4Utils.getLastChangeList(server, filePath)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String description = changelist.getDescription();
		Matcher matcher = Pattern.compile(regexp).matcher(description);
		matcher.find();
		return matcher.group(1);
	}

	public static String getLastChangeList(final IServer server, final String filePath)  {
		try {
			return server.getChangelists(1, FileSpecBuilder.makeFileSpecList(filePath), null, null, true, true, false, true).get(0).getId() + "";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getServerFileDataByRegexp(final IServer server, final String filePath, final String regexp){
		byte [] buffer = new byte[1_000_000];
		try {
			InputStream inputStream = server.getFileContents(FileSpecBuilder.makeFileSpecList(filePath), false, false);
			inputStream.read(buffer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Matcher matcher = Pattern.compile(regexp).matcher(new String(buffer));
		matcher.find();
		return matcher.group(1);
	}
	
	/*public static String syncFileByForceImport(final Controller controller, final String filePath, final String ... syncArgs) 
	{
		Process buildProcess = null;
		try {
			buildProcess = new ProcessBuilder(PropertyTypes.SYNC_MANAGER_PATH.getData(), 
					"-f", filePath, // filePath
					"-I", "f", 
					"-u", syncArgs[0], //user name
					"-p", syncArgs[1], // server URL
					"-c", syncArgs[2]) // workspace
					.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		StringBuilder resultString = new StringBuilder();
		DataInputStream input = new DataInputStream(buildProcess.getInputStream());
		while(buildProcess.isAlive()){
			byte [] buffer = new byte[5000];
			try {
				input.read(buffer);
			} catch (IOException e) {
				e.printStackTrace();
			}
			String part = new String(buffer);
			controller.showLogMessage(part, MessageType.OK);
			resultString.append(part);
		}
		return resultString.toString();
	}*/
	
	public static String syncFileToHeadRevision(final String filePath, final String ... syncArgs)
	{
		Process buildProcess = null;
		try {
			buildProcess = new ProcessBuilder(Properties.SYNC_MANAGER_PATH.getData(), 
					"-d", filePath, 					// filePath
					"-u", Properties.USER.getData(), //user name
					"-p", syncArgs[0],					// server URL
					"-c", syncArgs[1]) 					// workspace
					.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		StringBuilder resultString = new StringBuilder();
		DataInputStream input = new DataInputStream(buildProcess.getInputStream());
		while(buildProcess.isAlive()){
			byte [] buffer = new byte[2000];
			try {
				input.read(buffer);
			} catch (IOException e) {
				e.printStackTrace();
			}
			String part = new String(buffer);
			controller.showLogMessage(part, MessageType.DEBUG);
			resultString.append(part);
			if(resultString.toString().contains(STOP_SYNC_EXP)){
				break;
			}
		}
		return resultString.toString();
	}
	
	public static void syncDepot(final IServer server, final String depotPath){
		try {
			server.getCurrentClient().sync(FileSpecBuilder.makeFileSpecList(depotPath),
												true, 
												false, 
												false, 
												false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static char getLabelLetter(final IServer server, final String path){
		long labelsForCurDate = 0;
		try {
			List<ILabelSummary> labels = server.getLabels(Properties.USER.getData(), 10000, null, FileSpecBuilder.makeFileSpecList(path));
			labelsForCurDate = labels.stream().map(ILabelSummary::getName).filter( name -> name.contains(Utils.getHarmanDate())).count();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return alphabet.charAt((int)labelsForCurDate);
	}

	public static void addFilesToCL(final IServer server, final List<IFileSpec> files, final int changeListNumber) {
		try {
			server.getCurrentClient().editFiles(files, false, false, changeListNumber, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static List<IFileSpec> revertedFiles(IServer server, final List<IFileSpec> files, final int changeListNumber) {
		List<IFileSpec> revertedFiles = null;
		try {
			revertedFiles = server.getCurrentClient().revertFiles(files, false, changeListNumber, true, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return revertedFiles;
	}

	public static void deleteChangeList(final IServer server, final int changeListNumber) {
		try {
			server.deletePendingChangelist(changeListNumber);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void submitCL(final IChangelist pendingCL) {
		try {
			pendingCL.submit(false);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	public static ILabel getLatestLabel(final IServer server, final String user, final String path) {
		ILabel label = null;
		try {
			List<ILabelSummary> labels = server.getLabels(Properties.USER.getData(), 10000, null, FileSpecBuilder.makeFileSpecList(path));
			label = server.getLabel(labels.get(labels.size()-1).getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return label;
	}

	public static Label createLabel(final IServer server, final String labelName, final ViewMap<ILabelMapping> mapping) {
		Date now = new Date();
		Label label = new Label  (	labelName,
									server.getCurrentClient().getOwnerName(),
									 now,
									 now,
									 "Daily CW" + Utils.getHarmanWeek() + " integration.",
									 "",
									 false,
									 mapping);
		try {
			server.createLabel(label);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return label;
	}
	
	public static String getCLDescription(final IServer server, final String filePath, final String editedLine) {
		IChangelist changelist = null;
		try {
			changelist = server.getChangelist(Integer.parseInt(P4Utils.getLastChangeList(server, filePath)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String description = changelist.getDescription();
		return editedLine + description.substring(description.indexOf("\n"), description.length()-1);
	}
	
	public static String getPreviousWicomeLabel(final String filePath) {
		String fileContent = null;
		try {
			fileContent = String.join("", Files.readAllLines(Paths.get(filePath)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Matcher matcher = Pattern.compile(LABEL_REGEXP).matcher(fileContent);
		matcher.find();
		return matcher.group(1);
	}
}
