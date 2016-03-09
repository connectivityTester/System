package test.actions;

import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;

import buffers.matchers.AnswerPattern;
import common.DeviceSource;
import common.SystemConstants;
import connections.devicesources.protocol.DeviceSourceMessage;
import connections.devicesources.protocol.DeviceSourceParameter;
import types.ActionResultTypes;
import types.CommandTypes;
import types.DataPackerTypes;
import types.FailReaction;
import types.IncomingMessageType;
import types.MessageLogTypes;
import utils.Logger;
import utils.Utils;

public class Action {
	private Command command;
	private CommandTypes commandType;
	private List<Parameter> commandParametes;
	private List<Action> thenBlock;
	private List<Action> elseBlock;
	private List<Action> loopBodyBlock;
	private ActionResult result;
	private FailReaction reaction;
	private int retry;

	public Action(Command command, List<Parameter> commandParametes, 
					List<Action> thenBlock, List<Action> elseBlock, List<Action> loopBodyBlock, 
					FailReaction failReaction, int retry) 
	{
		this.command = command;
		this.commandType = this.defineCommandType(command.getDeviceSource().getId());
		this.commandParametes = commandParametes;
		this.thenBlock = thenBlock;
		this.elseBlock = elseBlock;
		this.loopBodyBlock = loopBodyBlock;
		this.result = new ActionResult(ActionResultTypes.NOT_EXECUTED, null);
		this.reaction = failReaction;
		this.retry = retry;
	}
	
	public String getParamValue(String paramName){
		for(Parameter param : this.commandParametes){
			if(param.getName() != null && param.getName().equals(paramName)){
				return param.getValue();
			}
		}
		return null;
	}
	
	public List<AnswerPattern> getAnswerPatternsByType(IncomingMessageType type){
		List<AnswerPattern> answerPatterns = null;
		for(Parameter parameter : this.commandParametes){
			if(parameter.getMessageType() == type){
				if(answerPatterns == null){
					answerPatterns = new LinkedList<>();
				}
				AnswerPattern answerPattern = parameter.getAnswerPattern();
				if(answerPattern != null){
					answerPatterns.add(answerPattern);
				}
			}
		}
		return answerPatterns;
	}
	
 	public Command getCommand() 						{	return this.command;			}
	public List<Parameter> getCommandParametes()		{	return this.commandParametes;	}
	public List<Action> getThenBlock() 					{	return this.thenBlock;			}
	public List<Action> getElseBlock()					{ 	return this.elseBlock;			}
	public List<Action> getLoopBodyBlock() 				{	return this.loopBodyBlock;		}
	public ActionResult getActionResult()				{	return this.result;				}
	public FailReaction getFailReaction()				{	return this.reaction;			}	
	public int getReTry()								{	return this.retry;				}
	public void setActionResult(ActionResult result)	{ 	this.result = result;			}
	
	public String toString(){
		StringBuilder result = new StringBuilder(this.command.toString()+"\r");
		if(this.commandParametes != null){
			result.append("Parameters:\r");
			for(Parameter param : this.commandParametes){
				result.append("Parameter: "+param.toString()+"\r");
			}
		}
		if(this.thenBlock != null){
			result.append("ThenBlock:\r");
			for(Action action : this.thenBlock){
				result.append("Action: "+action.toString()+"\r");
			}
		}
		if(this.elseBlock != null){
			result.append("ElseBlock:\r");
			for(Action action : this.elseBlock){
				result.append("Action: "+action.toString()+"\r");
			}
		}
		if(this.loopBodyBlock != null){
			result.append("LoopBodyBlock:\r");
			for(Action action : this.loopBodyBlock){
				result.append("Action: "+action.toString()+"\r");
			}
		}
		return result.toString();
	}
	
	private CommandTypes defineCommandType(int devicesourceId){
		return devicesourceId != SystemConstants.systemDeviceSourceId ? CommandTypes.EXTERNAL_COMMAND : CommandTypes.SYSTEM_COMMAND;
	}
	
	public CommandTypes getCommandType() {	return commandType;	}
	
	public String packActionToString(DeviceSource devSource, DataPackerTypes dataPackerType, List<Variable> testVariables){
		DeviceSourceMessage deviceSourceMessage = null;
		if(this.commandParametes != null){
			List<DeviceSourceParameter> deviceSourceParameters = new LinkedList<>();
			for(Parameter parameter : this.commandParametes){
				if(!parameter.isVariable()){
					deviceSourceParameters.add(new DeviceSourceParameter(parameter.getName(), parameter.getValue()));
				}
				else{
					deviceSourceParameters.add(new DeviceSourceParameter(parameter.getName(), Utils.prepareSingleParameterValue(parameter, testVariables)));
				}
			}
			deviceSourceMessage = new DeviceSourceMessage(this.command.getCommandName(), this.command.getDeviceSource().getId(), deviceSourceParameters);
		}
		else{
			deviceSourceMessage = new DeviceSourceMessage(this.command.getCommandName(), this.command.getDeviceSource().getId(), null);
		}
		String commandString = new Gson().toJson(deviceSourceMessage, DeviceSourceMessage.class);
		Logger.logToUser("Command \"" + commandString + "\" will be sent", this, MessageLogTypes.INFO);
		return commandString;
	}
}
