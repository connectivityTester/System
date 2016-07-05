package logic;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import utils.P4Utils;
import utils.Utils;

import com.perforce.p4java.core.IChangelist;
import com.perforce.p4java.core.ILabel;
import com.perforce.p4java.core.file.FileSpecBuilder;
import com.perforce.p4java.core.file.IFileSpec;
import com.perforce.p4java.exception.P4JavaException;
import com.perforce.p4java.impl.generic.core.Label;
import com.perforce.p4java.option.client.LabelSyncOptions;
import com.perforce.p4java.server.IServer;

import editors.DSyncCompositionEditor;
import editors.DSyncCompositionExternalIfEditor;
import editors.DSyncCompositionSpecificEditor;
import editors.DSyncCompositionWicomeEditor;
import editors.FileEditor;
import editors.TConnectivityVersionEditor;
import editors.TPkg_connEditor;
import editors.TSyncCompositionEditor;
import editors.TVersionEditor;
import gui.MessageType;
import static controller.Controller.controller;

/**
 * Class-logic
 * 
 * It handles all sequences and algorithms of integration process
 * 
 * @author dkuschevoy
 *
 */

public class Logic{

	private static final String TCFG_CONNECTIVITY_PATH 	= "/version/TcfgConnectivityVersion.txt";
	private static final String TCFG_VERSION_PATH 		= "/version/TcfgVersion.txt";
		
	private static final String DIS2_SCP_PATH 			= "/Default/config/DIS2_SCP.p4inc";
	
	private static final String SYNC_REGEXP				= ".*finished\\swith\\s([0-9]+)\\s.*";
	private static final String DIS2_MM_TUNER_REGEXP 	= ".*DIS2_MM_Tuner.*#([0-9]+)";
	private static final String DIS2_COMMON_REGEXP 		= ".*DIS2_common.*#([0-9]+)";
	private static final String CL_REGEXP 				= ".*CL:([0-9]+)";
	
	private static final String API_PATH 				= "/dis2-scp-api-all-omap-trunk/api/...";
	private static final String BIN_PATH 				= "/dis2-scp-bin-omap-trunk/bin/...";
	private static final String ETC_PATH 				= "/dis2-scp-bin-omap-trunk/etc/...";
	private static final String LIB_PATH 				= "/dis2-scp-bin-omap-trunk/lib/...";
	
	private static final String DTCFG_CON_VERSION_PATH 	= "/Default/version/TcfgConnectivityVersion.txt";
	private static final String DPKG_CONN_PATH 			= "/Default/config/pkg/pkg_conn.p4inc";
	private static final String DTCFG_VERSION_PATH 		= "/Default/version/TcfgVersion.txt";

	private static final String CSYNC_COMP_EXT_PATH 	= "/config/SyncComposition_UsedExternalIf.p4sm";
	private static final String CSYNC_COMP_SPEC_PATH 	= "/config/SyncComposition_Specific.p4sm";
	private static final String CSYNC_COMP_WICOME_PATH 	= "/config/SyncComposition_Wicome.p4sm";
	private static final String CSYNC_COMP_HEAD_PATH	= "/config/SyncComposition_Head.p4sm";
	private static final String CSYNC_COMP_PATH			= "/config/SyncComposition.p4sm";
	private static final String CPKG_CONN_PATH 			= "/config/pkg/pkg_conn.p4inc";

	private static final String DIS2_MM_TUNER_PATH 		= "/Default/config/DIS2_MM_Tuner.p4inc";
	private static final String DIS2_COMMON_PATH 		= "/Default/config/DIS2_common.p4inc";
	
	private static final String DSYNC_COMP_EXTERN_PATH 	= "/Default/config/SyncComposition_UsedExternalIf.p4sm";
	private static final String DSYNC_COMP_SPEC_PATH 	= "/Default/config/SyncComposition_Specific.p4sm";
	private static final String DSYNC_COMP_WICOME_PATH 	= "/Default/config/SyncComposition_Wicome.p4sm";
	private static final String DSYNC_COMP_HEAD_PATH 	= "/Default/config/SyncComposition_Head.p4sm";
	private static final String DSYNC_COMP_PATH 		= "/Default/config/SyncComposition.p4sm";
	
	private static final String SERVER_URL 				= "172.30.137.2:3500";
	
	private final ConnectionController connectionController;
	private final String connectivityLabel;
	
	public Logic (){			
		controller.showLogMessage("Branch: " + Properties.BRANCH, MessageType.OK);
		controller.showLogMessage("Domain workspace: " + Properties.DOMAIN_WORKSPACE, MessageType.OK);
		controller.showLogMessage("TCFG workspace: " + Properties.TCFG_WORKSPACE, MessageType.OK);
		
		connectionController = new ConnectionController();
		char labelLetter = P4Utils.getLabelLetter(	connectionController.getServer(ConnectionController.ServerType.SERVER_3500),
													Properties.SCP_PATH.getData() + Properties.BRANCH +"/...");
		connectivityLabel = "DIS2_SCP_" + Properties.LABEL_PREFIX + "_" + Utils.getHarmanDate() + labelLetter;
	}

	public void getLatestDomainRevision() {
		controller.blockGUI(true);
		Thread syncThread = new Thread( new Runnable() {
			@Override
			public void run() {
				LocalTime startTime = LocalTime.now();
				controller.showLogMessage("\n\n================= SYNC TO LATEST REVISION STARTED =================\n", MessageType.INFO);
				
				//switch to domain workspace
				IServer server3500 = connectionController.getServer(ConnectionController.ServerType.SERVER_3500, Properties.DOMAIN_WORKSPACE.getData());

				//get latest revision for domain
				controller.showLogMessage("\nDomain '" + Properties.SCP_PATH + Properties.BRANCH + "/...' will be synced to latest revision. Please wait... ", MessageType.OK);
				P4Utils.syncDepot(server3500, Properties.SCP_PATH + Properties.BRANCH.getData() + "/...");
				
				controller.showLogMessage("Domain branch was synced successfully\n", MessageType.OK);
				controller.showLogMessage("\n\n================= LATEST DOMAIN REVISION WAS GOTTEN =================\n", MessageType.INFO);
				controller.showLogMessage("\nSpend time: " + Duration.between(startTime, LocalTime.now()).getSeconds() +" seconds", MessageType.OK);
				controller.blockGUI(false);
			}
		});
		syncThread.start();		
	}
	
	public void makeRebase(final String wicomeLabel, final boolean withSubmit) {
		controller.blockGUI(true);
		Thread rebaseThread = new Thread( new Runnable() {
			@Override
			public void run() {
				LocalTime startTime = LocalTime.now();
				controller.showLogMessage("\n\n================= REBASE STARTED =================\n", MessageType.INFO);

				IServer server3500 = connectionController.getServer(ConnectionController.ServerType.SERVER_3500, Properties.DOMAIN_WORKSPACE.getData());

				//select necessary files
				List<IFileSpec> editedFiles =  FileSpecBuilder.makeFileSpecList( Arrays.asList ( 
								 	Properties.SCP_PATH + Properties.BRANCH.getData() + DSYNC_COMP_PATH,
								 	Properties.SCP_PATH + Properties.BRANCH.getData() + DSYNC_COMP_HEAD_PATH,
									Properties.SCP_PATH + Properties.BRANCH.getData() + DSYNC_COMP_WICOME_PATH,
									Properties.SCP_PATH + Properties.BRANCH.getData() + DSYNC_COMP_EXTERN_PATH));
				 
				//check if files are opened
				String path = null;
				if((path = P4Utils.areFilesAvailable(server3500, editedFiles)) != null){
					controller.showLogMessage("File '" + path + "' has been already opened.\n Please release it and try again", MessageType.ERROR);
					controller.blockGUI(false);
					return;
				}
				 //create new change list
				String newSyncCompDescription = P4Utils.getCLDescription( server3500, 
									 			server3500.getCurrentClient().getRoot() + CSYNC_COMP_PATH, 
									 			"[IN] Rebase " + Utils.getHarmanDate());

				IChangelist pendingCL = P4Utils.createPendingCL(server3500, newSyncCompDescription);

				//add selected files to created change list
				P4Utils.addFilesToCL(server3500, editedFiles,pendingCL.getId());
	
				//get last revision of DIS2_common.p4inc and DIS2_MM_Tuner.p4inc
				int lastRevisionDIS2_common =
						P4Utils.getLastFileRevision(server3500, Properties.APPS_PATH + Properties.BRANCH.getData() + DIS2_COMMON_PATH);
				int lastRevisionDIS2_MM_Tuner = 
						P4Utils.getLastFileRevision(server3500, Properties.APPS_PATH + Properties.BRANCH.getData() + DIS2_MM_TUNER_PATH);
				 		
				//edit SyncComposition.p4sm
				FileEditor syncCompEditor = 
						 	new DSyncCompositionEditor(server3500, CSYNC_COMP_PATH, lastRevisionDIS2_common, lastRevisionDIS2_MM_Tuner);
				syncCompEditor.editFile();
				
				 //edit SyncComposition_Head.p4sm
				FileEditor syncCompHeadEditor =
							new DSyncCompositionEditor(server3500, CSYNC_COMP_HEAD_PATH, lastRevisionDIS2_common, lastRevisionDIS2_MM_Tuner);
				syncCompHeadEditor.editFile();
				
				 //edit SyncComposition_Wicome.p4sm
				if(wicomeLabel != null && !wicomeLabel.isEmpty()){
					FileEditor syncCompWicomeEditor = 
							new DSyncCompositionWicomeEditor(server3500, CSYNC_COMP_WICOME_PATH, wicomeLabel);
					syncCompWicomeEditor.editFile();
				}
				
				IServer server3501 = connectionController.getServer(ConnectionController.ServerType.SERVER_3501);
				//edit SyncComposition_UsedExternalIf.p4sm
				FileEditor syncCompExternalEditor =
							new DSyncCompositionExternalIfEditor(server3500, CSYNC_COMP_EXT_PATH, new IServer[]{server3501});
				syncCompExternalEditor.editFile();
				
				//revert unchanged files
				controller.showLogMessage("Reverting unchanged files...", MessageType.OK);
				List<IFileSpec> revertedFiles = P4Utils.revertedFiles(server3500, editedFiles, pendingCL.getId());
				if(revertedFiles.size() > 0){
					controller.showLogMessage("Reverted files:", MessageType.OK);
					for(IFileSpec file : revertedFiles){
						 controller.showLogMessage(file.toString(), MessageType.OK);
					}
					boolean isCLdeleted = false;
					if(revertedFiles.size() == editedFiles.size()){
						 controller.showLogMessage("All files were reverted!", MessageType.OK);
						 controller.showLogMessage("Deleting pending rebase change list....", MessageType.OK);
						 P4Utils.deleteChangeList(server3500, pendingCL.getId());	
						 isCLdeleted = true;
						 controller.showLogMessage("Pending rebase change list was successfully deleted", MessageType.OK);
					}
					else{
						controller.showLogMessage("Rebase change list (" + pendingCL.getId() + ") has been prepared", MessageType.OK);
					}
					if(!isCLdeleted){
						if(withSubmit){
							controller.showLogMessage("Submitting ...", MessageType.OK);
							P4Utils.submitCL(pendingCL);
							controller.showLogMessage("Rebase change list (" + pendingCL.getId() + ") was submitted successfully", MessageType.OK);
						}
						else{
							controller.showLogMessage("Please review it firstly and then submit", MessageType.OK);	
						}						
					}
				 }
				 else{
					 controller.showLogMessage("No files to revert", MessageType.OK);
				 }
				
				 controller.showLogMessage("\n================= REBASE FINISHED =================", MessageType.INFO);
				 controller.showLogMessage("\nSpend time: " + Duration.between(startTime, LocalTime.now()).getSeconds() +" seconds", MessageType.OK);
				 controller.blockGUI(false);
		}});
		rebaseThread.start();
	}
    /*
	public void buildRebaseCL() {
		controller.blockGUI(true);
		Thread buildThread = new Thread(new Runnable() {
			@Override
			public void run() {
				controller.showLogMessage("\n================= REBASE BUILD STARTED =================\n", MessageType.OK);
				LocalTime startTime = LocalTime.now();
				IServer server3500 = connectionController.getServer(ConnectionController.ServerType.SERVER_3500, domainWorkspace);
				controller.showLogMessage("\n============== SYNC TO HEAD FROM LOCAL DISK STARTED ==============\n", MessageType.OK);
				String syncResultStr = 
						P4Utils.syncFileByForceImport ( controller, 
														syncManagerPath, 
														server3500.getCurrentClient().getRoot() + "/config/SyncComposition_Head.p4sm",
														user,
														SERVER_URL,
														domainWorkspace
														);
				int errorsCount = Integer.parseInt(Utils.getData(syncResultStr, SYNC_REGEXP));
				if(errorsCount == 0){
					controller.showLogMessage("Rebase CL was synced successfully", MessageType.OK);
				}
				else{
					controller.showLogMessage("Rebase CL was not synced successfully. Please analyze logs and try again.", MessageType.ERROR);
				}
				controller.showLogMessage("\n============== SYNC TO HEAD FROM LOCAL DISK FINISHED ==============\n", MessageType.OK);
				
				//create temporary file				
				File buildStartSourceFile = new File(server3500.getCurrentClient().getRoot() + "/process_needed_for_BC.cmd");
				File buildStartDestFile = new File(server3500.getCurrentClient().getRoot() + "/process_needed_for_BC.bat");
				try {
					Files.copy(buildStartSourceFile, buildStartDestFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("*************");
				String buildResult = P4Utils.startProcess(controller, buildStartDestFile.getAbsolutePath(), "-b", "-s=1");
				System.out.println(buildResult);
				System.out.println("*************");
				int buildErrorCount = Integer.parseInt(Utils.getData(buildResult, ERROR_REGEXP));
				if(buildErrorCount == 0){
					controller.showLogMessage("Rebase was built successfully", MessageType.OK);
				}
				else{
					controller.showLogMessage("Rebase was built unsuccessfully. Please analize logs and try again", MessageType.ERROR);
				}
				//delete temporary file
				buildStartDestFile.delete();
				
				controller.showLogMessage("\n================= REBASE BUILD FINISHED =================\n", MessageType.OK);
				controller.showLogMessage("\nSpend time: " + Duration.between(startTime, LocalTime.now()).getSeconds() +" seconds", MessageType.OK);
				controller.blockGUI(false);
			}
		});
		buildThread.start();
	}*/

	public void createLabel(final boolean withSubmit) {
		controller.blockGUI(true);
		Thread labelThread = new Thread(new Runnable() {
			@Override
			public void run() {
				LocalTime startTime = LocalTime.now();
				controller.showLogMessage("\n============== LABEL CREATION STARTED ============\n", MessageType.INFO);
		 
				//switch to domain workspace
				IServer server3500 = connectionController.getServer(ConnectionController.ServerType.SERVER_3500, Properties.DOMAIN_WORKSPACE.getData());
		 
				//create label name
				controller.showLogMessage("New label name - '" + connectivityLabel + "'", MessageType.OK);
				
				//check if current label already exists
				try {
					if (server3500.getLabel(connectivityLabel) != null) {
						controller.showLogMessage("Can not create label '" + connectivityLabel +"' because it has been already created", MessageType.ERROR);
						controller.blockGUI(false);
						return;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
		
				//create mapping for new label
				controller.showLogMessage("\nGetting previous label...", MessageType.OK);
				String path = Properties.SCP_PATH + Properties.BRANCH.getData() +"/...";
				ILabel previousLabel = P4Utils.getLatestLabel(server3500, Properties.USER.getData(), path);
				
				controller.showLogMessage("Previous label was gotten\n", MessageType.OK);
		
				//create new label and add domain files to it
				controller.showLogMessage("Creating label on server. Please wait....", MessageType.OK);
				Label label = P4Utils.createLabel(server3500, connectivityLabel, previousLabel.getViewMapping());
				label.setServer(server3500);
				label.setAutoReload(true);
				controller.showLogMessage("Label was created", MessageType.OK);
				controller.showLogMessage("\n============== LABEL WAS CREATED==========", MessageType.INFO);
				controller.showLogMessage("\n========= CHANGING 'SyncComposition_Specific.p4sm' =========\n", MessageType.INFO);

				 // select necessary files
				List<IFileSpec> specificFile =
						 	FileSpecBuilder.makeFileSpecList(Properties.SCP_PATH + Properties.BRANCH.getData()+ DSYNC_COMP_SPEC_PATH);

				 // check if files are opened
				if ((path = P4Utils.areFilesAvailable(server3500, specificFile)) != null) {
					controller.showLogMessage("File '"	+ path + "' has been already opened.\nPlease release it and try again", MessageType.ERROR);
					return;
				 }

				 // create new change list
				 String newSpecificDescription = null;
					newSpecificDescription = P4Utils.getCLDescription(server3500, 
										server3500.getCurrentClient().getRoot() + CSYNC_COMP_SPEC_PATH, 
										"[IN] Label create " + Utils.getHarmanDate() + P4Utils.getLabelLetter(server3500, Properties.SCP_PATH + Properties.BRANCH.getData()));
				 IChangelist specificCL = P4Utils.createPendingCL(server3500,  newSpecificDescription);

				 // add selected files to created change list
				 P4Utils.addFilesToCL(server3500, specificFile, specificCL.getId());

				 FileEditor syncCompSpecificEditor 
							= new DSyncCompositionSpecificEditor(server3500, CSYNC_COMP_SPEC_PATH, connectivityLabel);
				 syncCompSpecificEditor.editFile();
		
				 controller.showLogMessage("Change list for 'SyncComposition_Specific.p4sm' - " + specificCL.getId(), MessageType.OK);
				 if(withSubmit){
					 P4Utils.submitCL(specificCL);
					 controller.showLogMessage("Change list (" + specificCL.getId() + ") was submitted successfully", MessageType.OK);	
				 }
				 else{
					 controller.showLogMessage("Please review it firstly and then submit\n", MessageType.OK);	
				 }
				 controller.showLogMessage("\n========= 'SyncComposition_Specific.p4sm' WAS CHANGED ========\n", MessageType.INFO);
				 controller.showLogMessage("\nSpend time: " + Duration.between(startTime, LocalTime.now()).getSeconds() +" seconds", MessageType.OK);
				 controller.blockGUI(false);
			}
		});
		labelThread.start();
	}

	public void addFilesToLabel() {
		controller.blockGUI(true);
		Thread addFilesThread = new Thread(new Runnable() {
			@Override
			public void run() {
				LocalTime startTime = LocalTime.now();
				controller.showLogMessage("\n================ ADDING FILES TO LABEL STARTED ================\n", MessageType.INFO);
				
				//switch to domain workspace
				IServer server3500 = connectionController.getServer(ConnectionController.ServerType.SERVER_3500, Properties.DOMAIN_WORKSPACE.getData());
						
				//add files to label and lock label				
				try {
					//unlock label
					server3500.getLabel(connectivityLabel).setLocked(false);					
					List<IFileSpec> addedFiles = server3500.getCurrentClient().labelSync(FileSpecBuilder.makeFileSpecList(Properties.SCP_PATH + Properties.BRANCH.getData() + "/..."), 
																						connectivityLabel, 
																						new LabelSyncOptions (false, true, false));
					controller.showLogMessage( addedFiles.size() + " files were added", MessageType.OK);
					
					//lock label
					server3500.getLabel(connectivityLabel).setLocked(true);
				} catch (P4JavaException e) {
					e.printStackTrace();
				}
				controller.showLogMessage("\n================ FILES WERE ADDED TO LABEL ================\n", MessageType.INFO);
				controller.showLogMessage("\nSpend time: " + Duration.between(startTime, LocalTime.now()).getSeconds() +" seconds", MessageType.OK);
				controller.blockGUI(false);
			}
		});
		addFilesThread.start();
	}
	
	public void syncTCFGToHead() {
		controller.blockGUI(true);
		Thread syncThead = new Thread( new Runnable() {			
			@Override
			public void run() {
				LocalTime startTime = LocalTime.now();
				controller.showLogMessage("\n=============== SYNC TCFG TO HEAD STARTED ===============\n", MessageType.INFO);
				
				//switch to TCFG workspace
				connectionController.getServer(ConnectionController.ServerType.SERVER_3500, Properties.TCFG_WORKSPACE.getData());
				
				String syncResultStr = P4Utils.syncFileToHeadRevision ( Properties.TCFG_PATH + Properties.BRANCH.getData() + DSYNC_COMP_PATH,
																		SERVER_URL,
																		Properties.TCFG_WORKSPACE.getData());
				int errorsCount = Integer.parseInt(Utils.getData(syncResultStr, SYNC_REGEXP));
				if(errorsCount == 0){
					controller.showLogMessage("TCFG was synced to head successfully", MessageType.OK);
				}
				else{
					controller.showLogMessage("TCFG was synced to head unsuccessfully. Please analyze logs and try again.", MessageType.ERROR);
				}
				controller.showLogMessage("\n=============== SYNC TCFG TO HEAD FINISHED =================\n", MessageType.INFO);
				controller.showLogMessage("\nSpend time: " + Duration.between(startTime, LocalTime.now()).getSeconds() +" seconds", MessageType.OK);
				controller.blockGUI(false);
			}
		});
		syncThead.start();
	}
	
	public void makeTCFG(final String wicomeLabel, final boolean withSubmit){
		controller.blockGUI(true);
		Thread tcfgThead = new Thread( new Runnable() {			
			@Override
			public void run() {
				LocalTime startTime = LocalTime.now();
				controller.showLogMessage("\n================ TCFG INTEGRATION STARTED ================\n", MessageType.INFO);
				
				//switch to TCFG workspace
				IServer server3500 = connectionController.getServer(ConnectionController.ServerType.SERVER_3500, Properties.TCFG_WORKSPACE.getData());
		
				List<IFileSpec> tcfgFiles = FileSpecBuilder.makeFileSpecList( Arrays.asList(
						Properties.TCFG_PATH + Properties.BRANCH.getData() + DSYNC_COMP_PATH,
						Properties.TCFG_PATH + Properties.BRANCH.getData() + DPKG_CONN_PATH,
						Properties.TCFG_PATH + Properties.BRANCH.getData() + DTCFG_VERSION_PATH,
						Properties.TCFG_PATH + Properties.BRANCH.getData() + DTCFG_CON_VERSION_PATH));
				
				//check if files are opened
				controller.showLogMessage("Checking availability of files...", MessageType.OK);
				String filePath;
				if((filePath = P4Utils.areFilesAvailable(server3500, tcfgFiles)) != null){
					 controller.showLogMessage("File '" + filePath + "' has been already opened.\nPlease release it and try again", MessageType.ERROR);
					 controller.blockGUI(false);
					 return;
				}
				else{
					controller.showLogMessage("All neccessary files are available", MessageType.OK);
				}
		
				//get new CL Description
				controller.showLogMessage("Creating new CL description...", MessageType.OK);
				String newCLDescription = null;
					newCLDescription = P4Utils.getCLDescription(server3500, Properties.TCFG_PATH + Properties.BRANCH.getData() + DTCFG_CON_VERSION_PATH, 
							 				"[P.AVN3][SCP][IN] Connectivity delivery " + Utils.getHarmanDate());
				controller.showLogMessage("CL description was created", MessageType.OK);
				
				//create new change list
				controller.showLogMessage("Creating new CL on server...", MessageType.OK);
				IChangelist tcfgCL = P4Utils.createPendingCL(server3500, newCLDescription);
				P4Utils.addFilesToCL(server3500, tcfgFiles, tcfgCL.getId());
				controller.showLogMessage("New CL was created", MessageType.OK);		
				
				controller.showLogMessage("\nGetting data for 'TcfgConnectivityVersion.txt'... ", MessageType.OK);
				IServer server3501 = connectionController.getServer(ConnectionController.ServerType.SERVER_3501);
				String apiCL = P4Utils.getLastChangeList(server3501, Properties.BINARIES_DEPOT + Properties.BRANCH.getData() + API_PATH);
				String binCL = P4Utils.getLastChangeList(server3501, Properties.BINARIES_DEPOT + Properties.BRANCH.getData() + BIN_PATH);
				String etcCL = P4Utils.getLastChangeList(server3501, Properties.BINARIES_DEPOT + Properties.BRANCH.getData() + ETC_PATH);
				String libCL = P4Utils.getLastChangeList(server3501, Properties.BINARIES_DEPOT + Properties.BRANCH.getData() + LIB_PATH);				
				String platformLabel = wicomeLabel;
				if(wicomeLabel.isEmpty()){
					platformLabel = P4Utils.getPreviousWicomeLabel(server3500.getCurrentClient().getRoot() + TCFG_CONNECTIVITY_PATH);
				}	
				String connectivityLabel = P4Utils.getServerFileDataByRegexp(server3500, Properties.SCP_PATH + Properties.BRANCH.getData() + DSYNC_COMP_SPEC_PATH, ".*@([a-zA-Z_0-9]+).*");
				String[] versionArgs={
							connectivityLabel,
							platformLabel,
							P4Utils.getLastFileRevision(server3500, Properties.SCP_PATH + Properties.BRANCH.getData() + DSYNC_COMP_PATH) + "",
							P4Utils.getLastFileRevision(server3500, Properties.SCP_PATH + Properties.BRANCH.getData() + DSYNC_COMP_SPEC_PATH) + "",
							P4Utils.getLastFileRevision(server3500, Properties.SCP_PATH + Properties.BRANCH.getData() + DSYNC_COMP_WICOME_PATH) + "",
							P4Utils.getServerFileDataByRegexp(server3500, Properties.SCP_PATH + Properties.BRANCH.getData() + DSYNC_COMP_PATH, DIS2_COMMON_REGEXP),
							P4Utils.getServerFileDataByRegexp(server3500, Properties.SCP_PATH + Properties.BRANCH.getData() + DSYNC_COMP_PATH, DIS2_MM_TUNER_REGEXP),
							P4Utils.getLastFileRevision(server3500, Properties.APPS_PATH + Properties.BRANCH.getData() + DIS2_SCP_PATH) + "",
							apiCL,
							P4Utils.getCLFromDescription(server3501, Properties.BINARIES_DEPOT + Properties.BRANCH.getData() + API_PATH, CL_REGEXP),
							binCL,
							P4Utils.getCLFromDescription(server3501, Properties.BINARIES_DEPOT + Properties.BRANCH.getData() + BIN_PATH, CL_REGEXP),
							etcCL,
							P4Utils.getCLFromDescription(server3501, Properties.BINARIES_DEPOT + Properties.BRANCH.getData() + ETC_PATH, CL_REGEXP),
							libCL,
							P4Utils.getCLFromDescription(server3501, Properties.BINARIES_DEPOT + Properties.BRANCH.getData() + LIB_PATH, CL_REGEXP),
						};
				FileEditor tcfgConnVersion = new TConnectivityVersionEditor(server3500, TCFG_CONNECTIVITY_PATH, versionArgs);
				tcfgConnVersion.editFile();		
						
				FileEditor pkg_conn = new TPkg_connEditor(server3500, CPKG_CONN_PATH, apiCL, binCL, etcCL, libCL, platformLabel);
				pkg_conn.editFile();
				
				FileEditor syncComposition = new TSyncCompositionEditor(server3500, CSYNC_COMP_PATH );
				syncComposition.editFile();
				
				String descriptionCL = P4Utils.getCLFromDescription(server3501, Properties.BINARIES_DEPOT + Properties.BRANCH.getData() +"/...", CL_REGEXP);
				String revision = P4Utils.getLastChangeList(server3501, Properties.BINARIES_DEPOT + Properties.BRANCH.getData() + "/...");
				
				//Connectivity:          4647134   1289549   20.05.2016   16205A
				String connectivityData = "Connectivity:          " 
											+ descriptionCL + "   " 
											+ revision + "   " 
											+ Utils.getUsualDate() + "   " 
											+ connectivityLabel.substring(connectivityLabel.indexOf('1'), connectivityLabel.length());
				FileEditor tcfgVersion = new TVersionEditor(server3500, TCFG_VERSION_PATH, connectivityData);
				tcfgVersion.editFile();
				
				if(withSubmit){
					P4Utils.submitCL(tcfgCL);
					controller.showLogMessage("\nCL#" + tcfgCL.getId() + " was submitted successfully", MessageType.OK);					
				}
				
				controller.showLogMessage("\n================ TCFG INTEGRATION FINISHED ================\n", MessageType.INFO);
				controller.showLogMessage("\nSpend time: " + Duration.between(startTime, LocalTime.now()).getSeconds() + " seconds", MessageType.OK);
				controller.blockGUI(false);
			}
		});
		tcfgThead.start();
	}
	/*
	public void buildTCFGCL() {
		controller.blockGUI(true);
		Thread buildThread = new Thread( new Runnable() {			
			@Override
			public void run() {
				LocalTime startTime = LocalTime.now();
				controller.showLogMessage("\n================ BUILD TCFG CL STARTED ===============\n", MessageType.OK);
				
				//switch to TCFG workspace
				IServer server3500 = connectionController.getServer(ConnectionController.ServerType.SERVER_3500, tcfgWorkspace);

				controller.showLogMessage("\n=============== SYNC TO HEAD FROM LOCAL DISK STARTED ================\n", MessageType.OK);
				String buildResultStr = 
						P4Utils.syncFileByForceImport ( controller, 
														syncManagerPath, 
														server3500.getCurrentClient().getRoot() + "/config/SyncComposition.p4sm",
														user,
														"172.30.137.2:3500",
														tcfgWorkspace);
				int syncErrorsCount = Integer.parseInt(Utils.getData(buildResultStr, SYNC_REGEXP));
				if(syncErrorsCount == 0){
					controller.showLogMessage("TCFG CL was synced successfully", MessageType.OK);
				}
				else{
					controller.showLogMessage("TCFG CL was not synced successfully. Please analyze logs and try again.", MessageType.ERROR);
				}
				controller.showLogMessage("\n================ SYNC TO HEAD FROM LOCAL DISK FINISHED ================\n", MessageType.OK);
				if(syncErrorsCount == 0){
					String buildCentralPath = server3500.getCurrentClient().getRoot() + "buildcentral.bat";
					String buildResult = P4Utils.startProcess(controller, buildCentralPath, "-b", "-s=1");
					int buildErrorCount = Integer.parseInt(Utils.getData(buildResult, ERROR_REGEXP));
					if(buildErrorCount == 0){
						controller.showLogMessage("TCFG was built successfully", MessageType.OK);
					}
					else{
						controller.showLogMessage("TCFG was built unsuccessfully. Please analize logs and try again", MessageType.ERROR);
					}
				}
				controller.showLogMessage("\n================= BUILD TCFG FINISHED ================\n", MessageType.OK);
				controller.showLogMessage("\nSpend time: " + Duration.between(startTime, LocalTime.now()).getSeconds() + " seconds", MessageType.OK);
				controller.blockGUI(false);
			}
		});
		buildThread.start();		
	}	*/
}
