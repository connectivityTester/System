package commands;

public class Parameter {
	
	private String name;
	private String value;
	
	public Parameter(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}

	@Override
	public String toString() 			{	return "Parameter [name=" + name + ", value=" + value + "]";	}
	public String getName() 			{	return this.name;	}
	public String getValue() 			{	return this.value;	}

	public void setName(String name)	{	this.name = name;	}
	public void setValue(String value) 	{	this.value = value;	}

}
