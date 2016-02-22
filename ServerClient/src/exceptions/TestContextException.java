package exceptions;

import types.LogLevels;
import utils.Logger;

@SuppressWarnings("serial")
public class TestContextException extends Exception{
	
	public TestContextException (String message){
		super(message);
		Logger.log(LogLevels.INFO, this, "Contructor was started");
		Logger.log(LogLevels.INFO, this, "Contructor was finished");
	}

}
