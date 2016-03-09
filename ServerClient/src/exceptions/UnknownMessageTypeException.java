package exceptions;

@SuppressWarnings("serial")
public class UnknownMessageTypeException extends Exception{
	
	public UnknownMessageTypeException(String message){
		super(message);
	}
}
