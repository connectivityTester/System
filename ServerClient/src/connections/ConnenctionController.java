package connections;

import java.util.List;

import common.DeviceSource;
import gui.WorkSpace;
import test.Action;
import test.ActionResult;
import test.Variable;
import types.ActionResultTypes;
import types.DataPackerTypes;
import types.LogLevels;
import types.MessageLogTypes;
import utils.Logger;

public class ConnenctionController {
	
	private DeviceSourceConnectionController deviceSourceConnectionController;
	private final static ConnenctionController connenctionController = new ConnenctionController();
		
	public static ConnenctionController getInstatnce(){
		return connenctionController;
	}
	
	public void startAllConnections(WorkSpace workSpace){
		if(this.deviceSourceConnectionController == null){
			this.deviceSourceConnectionController = new DeviceSourceConnectionController(workSpace);
			this.deviceSourceConnectionController.start();
		}
	}
	
	public ActionResult handleTestData(Action action, List<Variable> testVariables, DataPackerTypes dataPackerType){
		ActionResult result = new ActionResult(ActionResultTypes.OK, null);
		switch(action.getCommandType()){
			case EXTERNAL_COMMAND:
				StringBuilder stringBuilder = new StringBuilder("Command \"");
				stringBuilder.append(action.getCommand().getCommandName());
				stringBuilder.append("\" with parameters: ");
				String parameters = action.getCommandParametes().toString();
				stringBuilder.append(parameters.substring(1, parameters.length()-1));
				Logger.logToUser(stringBuilder.toString(), connenctionController, MessageLogTypes.INFO);
				DeviceSource devSource = action.getCommand().getDeviceSource();
				String testDataString = action.packActionToString(devSource, dataPackerType, testVariables);
				boolean res = this.deviceSourceConnectionController.sendTestDataToDeviceSource(devSource, testDataString);
				if(!res){
					result = new ActionResult(ActionResultTypes.NOK, "Action data was not successfully sent to device source");
					stringBuilder.append("\nwas sent to ");
					stringBuilder.append(action.getCommand().getDeviceSource().getName());
					stringBuilder.append(" not successufully");
					Logger.logToUser(stringBuilder.toString(), connenctionController, MessageLogTypes.ERROR);
				}	
				else{
					stringBuilder.append("\nwas sent to ");
					stringBuilder.append(action.getCommand().getDeviceSource().getName());
					stringBuilder.append(" successufully");
					Logger.logToUser(stringBuilder.toString(), connenctionController, MessageLogTypes.INFO);
				}
				break;
			case UNKNOWN_COMMAND:
				Logger.log(LogLevels.ERROR, connenctionController, "Method handleTestData, connection controller received unknown command");
				break;
			case SYSTEM_COMMAND:
				Logger.log(LogLevels.ERROR, connenctionController, "Method handleTestData, connection controller received system command");
				break;
		}
		return result;
	}
	
	
}
