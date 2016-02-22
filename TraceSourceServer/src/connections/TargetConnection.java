package connections;

import java.util.ArrayList;
import java.util.List;
import common.Configuration;
import connections.TargetComunicationLibrary;
import types.LogLevels;
import utils.Logger;

public class TargetConnection {
	private static TargetConnection targetConnection ; 
	private final TargetComunicationLibrary library = TargetComunicationLibrary.INSTANCE;
	
	private final List<String> processList;
	private boolean isTargetConnected = false;
	
	private TargetConnection(){	
		this.processList = new ArrayList<String>();
		this.isTargetConnected = false;
		Logger.log(LogLevels.TRACE, this, "Library was init with result: " + this.library.tcl_init());
		int res = this.setCNLogFilePath(Configuration.getInstance().getCnlFilePath());
		Logger.log(LogLevels.TRACE, this, "CNL file was set with result: "+res);
		res = this.setTextLogFilePath(Configuration.getInstance().getTextLogFilePath());
		Logger.log(LogLevels.TRACE, this, "Text file was set with result: "+res);	
	}
	
	private int setCNLogFilePath(String fileName)	{	return this.library.tcl_setCNLogFilePath(fileName);		}	
	private int setTextLogFilePath(String filePath)	{	return this.library.tcl_setTextLogFilePath(filePath);	}	
	public static TargetConnection getInstance()	{	return targetConnection == null ? targetConnection = new TargetConnection() : targetConnection;}		
	
	public boolean connectToTarget(){
		if(this.isTargetConnected == true){
			Logger.log(LogLevels.INFO, this, "Target has been already connected");
		}
		else{
			Logger.log(LogLevels.TRACE, this, "Connecting to target \"" + Configuration.getInstance().getTargetIPAddress() + "\" ...");
			int res = this.library.tcl_connect_to_trace_consumer(
												Configuration.getInstance().getCommandServerIPAddress(),
												Configuration.getInstance().getCommandServerPort(), 
												Configuration.getInstance().getDeviceId(), 
												Configuration.getInstance().getTargetIPAddress(), 
												Configuration.getInstance().getTargetComunicationPort(), 
												Configuration.getInstance().getTargetComunicationProtocol());
			switch(res){
				case 0:
					this.isTargetConnected = true;	
					Logger.log(LogLevels.TRACE, this, "Target was successfully connected.");
					break;
				case 1:
					this.isTargetConnected = false;			
					Logger.log(LogLevels.ERROR, this, "Connection timeout. Target was not successfully connected.");
					break;
				case 2:
					this.isTargetConnected = false;			
					Logger.log(LogLevels.ERROR, this, "Internal error. Target was not successfully connected. Please analize text logs");
					break;
			}
		}
		return this.isTargetConnected;
	}
	
	
	public boolean executeCallback(List<String> callbackParameters) {
		Logger.log(LogLevels.INFO, this, "Function executeCallback was started");
		boolean executionResult = true;
		boolean connectionResult = false;
		if(!this.isTargetConnected){
			connectionResult = this.connectToTarget();
		}
		if(!connectionResult){
			Logger.log(LogLevels.INFO, this, "Function executeCallback will not be executed "
					+ "because target was not connected successfully");
			executionResult = false;
		}
		else
		{			
			if(!this.processList.contains(callbackParameters.get(0))){
				Logger.log(LogLevels.TRACE, this, "Loading process - " + callbackParameters.get(0));
				int execRes = this.library.tcl_loadProcessInfo(callbackParameters.get(0));
				Logger.log(LogLevels.TRACE, this, "Process was loaded with result: " + execRes);
				if( execRes != 0)
				{
					Logger.log(LogLevels.ERROR, this, "Problems with loading process info!");
					Logger.log(LogLevels.ERROR, this, "TCL_loadProcessInfo returned:" + execRes);
					executionResult = false;
				}				
				this.processList.add(callbackParameters.get(0));
			}
			if(executionResult){
				List<String> paramsList = callbackParameters.subList(2, callbackParameters.size());
				String [] paramsArray = new String[paramsList.size()];
				Logger.log(LogLevels.INFO, this, "Callback parameters:");
				Logger.log(LogLevels.INFO, this, "Process name: " + callbackParameters.get(0));
				Logger.log(LogLevels.INFO, this, "Callback name: " + callbackParameters.get(1));		
				for(int i = 0; i < paramsArray.length; i++){
					paramsArray[i] = paramsList.get(i);
					Logger.log(LogLevels.INFO, this, "Callback parameter: " + paramsArray[i]);
				}
				int execRes = this.library.tcl_executeCallback(callbackParameters.get(0), callbackParameters.get(1), 
						Configuration.getInstance().getDefaultCallbackValue(), 
						paramsArray, paramsArray.length);		
				if (execRes!=0){
					executionResult = false;
					Logger.log(LogLevels.ERROR, this, "Callback was executed with result: " + execRes);
				}
				else{
					Logger.log(LogLevels.TRACE, this, "Callback was executed successfully");
				}						
			}			
		}		
		Logger.log(LogLevels.INFO, this, "Function executeCallback was finished");
		return executionResult;
	}
}
