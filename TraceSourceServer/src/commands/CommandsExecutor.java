package commands;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import connections.TargetConnection;
import types.LogLevels;
import utils.Logger;

public class CommandsExecutor {
	
	private final static CommandsExecutor executor = new CommandsExecutor();
	
	public static CommandsExecutor getInstance()	{	return executor;	}
	
	public void executeCommand(Command command){
		switch(command.getCommandType()){
			case EXECUTE_CALLBACK:
				//Parameters order:
				//process name
				//callback name
				//other parameters
				List<String> parameters = new LinkedList<>();
				Parameter parameter = command.getParameterByName("process");
				if(parameter == null){
					Logger.log(LogLevels.ERROR, this, "Callback parameters does not contain \"process\" parameter");
					return;
				}
				else{
					parameters.add(parameter.getValue());
				}
				parameter = command.getParameterByName("callback_name");
				if(parameter == null){
					Logger.log(LogLevels.ERROR, this, "Callback parameters does not contain \"callback_name\" parameter");
					return;
				}
				else{
					parameters.add(parameter.getValue());
				}
				List<String> otherParameters = 
						command.getParametersValuesWithoutNames(Arrays.asList(new String[]{"process","callback_name"}));
				parameters.addAll(otherParameters);
				Logger.log(LogLevels.TRACE, executor, parameters.toString());
				TargetConnection.getInstance().executeCallback(parameters);
				break;
			case UNKOWN_COMMAND:
				Logger.log(LogLevels.ERROR, this, "Unknown command was received");
				break;
		}
	}
}
