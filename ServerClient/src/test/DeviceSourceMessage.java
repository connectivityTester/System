package test;

import java.util.List;

public class DeviceSourceMessage {
	
	private String commandName;
	private int id;
	private List<DeviceSourceParameter> parameters;
	
	public DeviceSourceMessage(String commandName, int id, List<DeviceSourceParameter> parameters) {
		super();
		this.commandName = commandName;
		this.id = id;
		this.parameters = parameters;
	}

	public String getCommandName() 						{	return this.commandName;		}
	public int getId() 									{	return id;						}
	public List<DeviceSourceParameter> getParameters() 	{	return parameters;				}
	
	public void setCommandName(String commandName) 					{	this.commandName = commandName;	}
	public void setId(int id) 										{	this.id = id;					}
	public void setParameters(List<DeviceSourceParameter> params) 	{	this.parameters = params;		}
}	
