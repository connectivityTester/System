package connections.devicesources.protocol;

import utils.Utils;

public class DeviceSourceParameter {
	
	private String name;
	private String value;
	
	public DeviceSourceParameter(final String name, final String value) {
		Utils.requireNonNull(name, value);
		
		this.name = name;
		this.value = value;
	}

	public String getName() 			{	return this.name;	}
	public String getValue() 			{	return this.value;	}
	
	public void setName(final  String name) 	{	this.name = name;	}
	public void setValue(final String value) 	{	this.value = value;	}
}
