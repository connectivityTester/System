package test.actions;

import types.MessageLogTypes;
import utils.Logger;

public class Variable implements Comparable<Variable> {
	
	private String name;
	private String value;
	
	public Variable (String name, String value){
		this.name = name;
		this.value = value;
	}
	
	public String getName() 	{	return name;	}
	public String getValue()	{	return value;	}

	public void setValue(String value) {
		this.value = value;		
		Logger.logToUser("Variable "+this.name + " got new value value\""+this.value+"\"", this, MessageLogTypes.INFO);
	}
	
	public void setValue(Double value) {
		this.value = value.toString();		
		Logger.logToUser("Variable "+this.name + " got new value value\""+this.value+"\"", this, MessageLogTypes.INFO);
	}


	public String toString(){
		return "	Name: "+this.name+"\r	Value: "+this.value+" ";
	}

	@Override
	public int compareTo(Variable variable) {
		return this.name.compareTo(variable.getName());
	}

}
