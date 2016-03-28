package buffers.matchers;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Queue;
import java.util.regex.Pattern;

import com.google.gson.Gson;

import types.IncomingMessageType;
import types.LogLevels;
import utils.Logger;
import utils.Utils;

public class TextMessageMatcherTask extends MessageMatcher {

	private final Gson jsonParser = new Gson();
	private final String patternString;
	
	public TextMessageMatcherTask (final String pattern, final int deviceSourceId,
									final Queue<String> queue, final int timeout) 
	{
		super(pattern, deviceSourceId, queue, timeout);
		
		Utils.requireNonNull(pattern);
		
		this.messageType = IncomingMessageType.TEXT;
		this.patternString = ".*" + this.pattern.toLowerCase() + ".*";
	}

	@Override
	public String match() {
		String answer = null;
		LocalTime startTime = LocalTime.now();
		long diffMiliSeconds = 0;
		TRACE_FOUND_LABEL:{
			do {
				for(String message : this.queue){
					//Logger.log(LogLevels.TRACE, this, curMessage);
					ParsedIncomingMessage currentMessage = this.jsonParser.fromJson(message, ParsedIncomingMessage.class);
					if(currentMessage != null && 
							currentMessage.equalsMessageId(deviceSourceId) &&
							currentMessage.equalsType(this.messageType))
					{
						String messageData = currentMessage.getData().toLowerCase();
						if(Pattern.matches(this.patternString, messageData)){
							answer = currentMessage.getData();
							Logger.log(LogLevels.TRACE, this, "Trace was found");
							break TRACE_FOUND_LABEL;
						}
					}
				}
//				Iterator<String> iterator = this.queue.iterator();
//				while(iterator.hasNext()){
//					String curMessage = iterator.next();
//					//Logger.log(LogLevels.TRACE, this, curMessage);
//					ParsedIncomingMessage currentMessage = this.jsonParser.fromJson(curMessage, ParsedIncomingMessage.class);
//					if(currentMessage != null && 
//							this.deviceSourceId == currentMessage.getId() &&
//							currentMessage.getConvertedType() == IncomingMessageType.TEXT)
//					{
//						Matcher matcher = compiledPattern.matcher(currentMessage.getData().toLowerCase());
//						if(matcher.matches()){
//							answer = currentMessage.getData();
//							//Logger.log(LogLevels.TRACE, this, "Trace was found");
//							break TRACE_FOUND_LABEL;
//						}
//					}
//				}
				diffMiliSeconds = Duration.between(startTime, LocalTime.now()).getSeconds()*1000;
				if(diffMiliSeconds > this.timeout){
					Logger.log(LogLevels.TRACE, this, "Search timed out");
					break;
				}
				else{
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {}
				}
			}
			while(true); 
		}
		//Logger.logToUser("Spent time: " + diffMiliSeconds + " miliseconds", this, MessageLogTypes.INFO);
		return answer;
	}
}
