package connections.devicesources.protocol;

import java.util.List;
import utils.Utils;

public class DeviceSourceMessage {
	
	private String commandName;
	private int id;
	private List<DeviceSourceParameter> parameters;
	
	public DeviceSourceMessage(final String commandName, final int id, final List<DeviceSourceParameter> parameters) {
		Utils.requireNonNull(commandName, parameters);
		
		this.commandName = commandName;
		this.id = id;
		this.parameters = parameters;
	}

	public String getCommandName() 						{	return this.commandName;		}
	public int getId() 									{	return id;						}
	public List<DeviceSourceParameter> getParameters() 	{	return parameters;				}
	
	public void setCommandName(final String commandName) 				{	this.commandName = commandName;	}
	public void setId(final int id) 									{	this.id = id;					}
	public void setParameters(final List<DeviceSourceParameter> params) {	this.parameters = params;		}
}	
