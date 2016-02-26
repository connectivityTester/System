package hmc_dis2.androidtracesource.commands;

import android.util.Log;

import java.util.List;

import hmc_dis2.androidtracesource.types.CommandTypes;

public class Command {
	
	private CommandTypes commandType;
	private List<Parameter> parameters;
    private int localDeviceSourceId;

    public Command( int localDeviceSourceId, CommandTypes commandType, List<Parameter> parameters){
		Log.i("Command::", "Constructor started");
		this.commandType = commandType;
		this.parameters = parameters;
        this.localDeviceSourceId = localDeviceSourceId;
		Log.i("Command::", "Created command: " + this.toString() );
		Log.i("Command::", "Constructor finished");
	}

	public CommandTypes getCommandType() {
		return this.commandType;
	}

	public List<Parameter> getParameters() {
		return this.parameters;
	}

	public String toString(){
		String result = this.commandType+" ";
		for(Parameter parameter : this.parameters){
			result+= parameter.getName()+" "+parameter.getValue();
		}
		return result;
	}
	
	public String getParameterValue(String parameterName){
		Log.i("Command::", "Function getParameterValue, name: " + parameterName + " ---- " + this.parameters.size());
		for(Parameter parameter : this.parameters){
			Log.i("Command::", "Function getParameterValue, parameter: " + parameter);
			Log.i("Command::", "Function getParameterValue, parameter.getName(): " + parameter.getName());
			Log.i("Command::", "Function getParameterValue, parameterName: " + parameterName);
			if(parameter.getName().equals(parameterName)){
				Log.i("Command::", "Function getParameterValue, parameter was found");
				return parameter.getValue();
			}
		}
		return null;
	}

    public int getDeviceSourceId() {
        return this.localDeviceSourceId;
    }
}
