package unpacker;

import java.io.StringReader;

import com.google.gson.Gson;

import commands.Command;

public class JsonUnpacker implements iUnpacker{
	
	private final static Gson gson = new Gson(); 
	
	@Override
	public Command unpackMessageToCommand(String message) {
		return gson.fromJson(new StringReader(message), Command.class);
	}

}
