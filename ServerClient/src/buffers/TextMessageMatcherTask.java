package buffers;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.google.gson.Gson;
import types.IncomingMessageType;
import types.LogLevels;
import utils.Logger;

public class TextMessageMatcherTask extends MessageMatcher {

	private final Gson jsonParser = new Gson();
	
	public TextMessageMatcherTask ( int deviceSourceId, ConcurrentLinkedQueue<String> queue) {
		super(deviceSourceId, queue);
	}

	@Override
	public String match(String messagePattern) {
		Pattern compiledPattern = Pattern.compile(".*" + messagePattern.toLowerCase() + ".*");
		String answer = null;
		Iterator<String> iterator = this.queue.iterator();
		while(iterator.hasNext()){
			String curMessage = iterator.next();
			//Logger.log(LogLevels.TRACE, this, curMessage);
			ParsedIncomingMessage currentMessage = this.jsonParser.fromJson(curMessage, ParsedIncomingMessage.class);
			if(currentMessage != null && 
					this.deviceSourceId == currentMessage.getId() &&
					currentMessage.getConvertedType() == IncomingMessageType.TEXT)
			{
				Matcher matcher = compiledPattern.matcher(currentMessage.getData().toLowerCase());
				if(matcher.matches()){
					answer = currentMessage.getData();
					break;
				}
			}
		}
		return answer;
	}

}
