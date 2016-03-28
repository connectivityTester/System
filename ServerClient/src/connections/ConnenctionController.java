package connections;

import java.util.List;

import common.DeviceSource;
import connections.devicesources.DeviceSourceConnectionController;
import gui.WorkSpace;
import test.actions.Action;
import test.actions.ActionResult;
import test.actions.Variable;
import types.ActionResultTypes;
import types.DataPackerTypes;
import types.LogLevels;
import types.MessageLogTypes;
import utils.Logger;
import utils.Utils;

public class ConnenctionController {
	
	private DeviceSourceConnectionController deviceSourceConnectionController;
	private final static ConnenctionController connenctionController = new ConnenctionController();
		
	public static ConnenctionController getInstatnce()	{	return connenctionController;	}
	
	public void startAllConnections(final WorkSpace workSpace){
		Utils.requireNonNull(workSpace);
		
		if(this.deviceSourceConnectionController == null){
			this.deviceSourceConnectionController = new DeviceSourceConnectionController(workSpace);
			this.deviceSourceConnectionController.start();
		}
	}
	
	public ActionResult handleTestData(final Action action, final List<Variable> testVariables, final DataPackerTypes dataPackerType){
		Utils.requireNonNull(action, dataPackerType);
		
		ActionResult result = new ActionResult(ActionResultTypes.OK, null);
		switch(action.getCommandType()){
			case EXTERNAL_COMMAND:
				final StringBuilder logMessage = new StringBuilder("Command \"");
				logMessage.append(action.getCommand().getCommandName());
				if(action.getCommandParametes() != null){
					logMessage.append("\" with parameters: ");
					String parameters = action.getCommandParametes().toString();
					logMessage.append(parameters.substring(1, parameters.length()-1));
				}
				else{
					logMessage.append("\" without parameters");
				}
				Logger.logToUser(logMessage.toString(), connenctionController, MessageLogTypes.INFO);
				final DeviceSource devSource = action.getCommand().getDeviceSource();
				final String testDataString = action.packActionToString(devSource, dataPackerType, testVariables);
				boolean sendResult = this.deviceSourceConnectionController.sendTestDataToDeviceSource(devSource, testDataString);
				if(!sendResult){
					result = new ActionResult(ActionResultTypes.NOK, "Action data was not successfully sent to device source");
					logMessage.append("\nwas sent to ");
					logMessage.append(action.getCommand().getDeviceSource().getName());
					logMessage.append(" not successufully");
					Logger.logToUser(logMessage.toString(), connenctionController, MessageLogTypes.ERROR);
				}	
				else{
					logMessage.append("\nwas sent to ");   
					logMessage.append(action.getCommand().getDeviceSource().getName());
					logMessage.append(" successufully");
					Logger.logToUser(logMessage.toString(), connenctionController, MessageLogTypes.INFO);
				}
				break;
			case SYSTEM_COMMAND:
				result = new ActionResult(ActionResultTypes.NOK, null);
				Logger.log(LogLevels.ERROR, connenctionController, "Method handleTestData, connection controller received system command");
				break;
		}
		return result;
	}
	
	
}
