package buffers;

import com.google.gson.Gson;

import types.LogLevels;
import utils.Logger;

public class IncomingMessageParser extends Thread{
	
	private final String messageToParse;
		
	public IncomingMessageParser(String messageToParse) {
		this.messageToParse = messageToParse;
		this.setPriority(MIN_PRIORITY);
	}

	@Override
	public void run() {
		Logger.log(LogLevels.INFO, this, "Method run, message to parse: " + this.messageToParse);
		if(this.messageToParse.startsWith("[") && this.messageToParse.endsWith("]")){
			ParsedIncomingMessage[] messages = new Gson().fromJson(this.messageToParse, ParsedIncomingMessage[].class);
			for(ParsedIncomingMessage message : messages){
				BufferManager.getInstance().addMessageToBuffer(message);
			}
		}
		else{
			BufferManager.getInstance().addMessageToBuffer(new Gson().fromJson(this.messageToParse, ParsedIncomingMessage.class));
		}
	}
}
