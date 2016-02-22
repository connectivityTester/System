package exceptions;

import types.LogLevels;
import utils.Logger;

@SuppressWarnings("serial")
public class TestExecutionExeption extends Exception{
	
	public TestExecutionExeption (String message){
		super(message);
		Logger.log(LogLevels.INFO, this, "Contructor was started");
		Logger.log(LogLevels.INFO, this, "Contructor was finished");
	}
}
