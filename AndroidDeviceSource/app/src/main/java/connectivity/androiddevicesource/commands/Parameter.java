package connectivity.androiddevicesource.commands;

import android.util.Log;

public class Parameter {
	
	private String name;
	private String value;
	
	public Parameter(String name, String value){
		Log.i("Parameter::", "Constructor started");
		this.name = name;
		this.value = value;
		Log.i("Parameter::", "Constructor finished");
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public String toString(){
		return "Parameter: name - " + this.name + ", value - " + this.value;
	}
}
