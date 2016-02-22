package buffers;

import com.google.gson.Gson;
import types.LogLevels;
import utils.Logger;

public class JSONParser {
	private final static JSONParser parser = new JSONParser();
	
	public static ParsedIncomingMessage parseIncomingMessage(String data){
		Logger.log(LogLevels.TRACE, parser, "Method parseIncomingMessage, message to parse: " + data);
		return new Gson().fromJson(data, ParsedIncomingMessage.class);
	}
}
