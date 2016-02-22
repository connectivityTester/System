package test;

public class DeviceSourceParameter {
	
	private String name;
	private String value;
	
	public DeviceSourceParameter(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}

	public String getName() 			{	return this.name;	}
	public String getValue() 			{	return this.value;	}
	public void setName(String name) 	{	this.name = name;	}
	public void setValue(String value) 	{	this.value = value;	}
}
