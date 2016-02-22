package commands;

import java.util.LinkedList;
import java.util.List;

import types.CommandTypes;
import types.LogLevels;
import utils.Logger;

public class Command {
	
	private String commandName;
	private int id;
	private List<Parameter> parameters;

	public String getCommandName() 							{	return commandName;				}
	public int getId() 										{	return id;						}
	public List<Parameter> getParameters() 					{	return parameters;				}
	
	public void setCommandName(String commandName) 			{	this.commandName = commandName;	}
	public void setId(int id) 								{	this.id = id;					}
	public void setParameters(List<Parameter> parameters)	{	this.parameters = parameters;	}

	public CommandTypes getCommandType(){
		CommandTypes type = CommandTypes.UNKOWN_COMMAND;
		switch(this.commandName.toLowerCase()){
			case "execute_callback"	:	
				type = CommandTypes.EXECUTE_CALLBACK; 
				break;
			default: 
				Logger.log(LogLevels.ERROR, this, "Unknown command was received");
				break;
		}
		return type;
	}
	
	public Parameter getParameterByName(String name){
		Parameter parameter = null;
		for(Parameter param : this.parameters){
			if(param.getName().equals(name)){
				parameter = param;
			}
		}
		return parameter;
	}
	
	public List<String> getParametersValuesWithoutNames(List<String> names){
		List<String> values = new LinkedList<String>();
		for(Parameter parameter : this.parameters){
			if(!names.contains(parameter.getName())){
				values.add(parameter.getValue());
			}
		}
		return values;
	}

	@Override
	public String toString() {
		return "Command [commandName=" + commandName + ", id=" + id + ", parameters=" + parameters + "]";
	}
}
