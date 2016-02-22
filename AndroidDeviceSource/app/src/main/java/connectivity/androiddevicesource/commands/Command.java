package connectivity.androiddevicesource.commands;

import android.util.Log;

import java.util.List;

import connectivity.androiddevicesource.types.CommandTypes;


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
		for(Parameter parameter : this.parameters){
			if(parameter.getName().equals(parameterName)){
				return parameter.getValue();
			}
		}
		return null;
	}

    public int getDeviceSourceId() {
        return this.localDeviceSourceId;
    }
}
