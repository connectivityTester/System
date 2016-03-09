package buffers.matchers;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;

import types.IncomingMessageType;
import types.LogLevels;
import utils.Logger;

public class TextMessageMatcherTask extends MessageMatcher {

	private final Gson jsonParser = new Gson();
	
	public TextMessageMatcherTask (String pattern, int deviceSourceId, Queue<String> queue, int timeout) {
		super(pattern, deviceSourceId, queue, timeout);
	}

	@Override
	public String match() {
		Pattern compiledPattern = Pattern.compile(".*" + this.pattern.toLowerCase() + ".*");
		String answer = null;
		LocalTime startTime = LocalTime.now();
		long diffMiliSeconds = 0;
		TRACE_FOUND_LABEL:{
			do {
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
							//Logger.log(LogLevels.TRACE, this, "Trace was found");
							break TRACE_FOUND_LABEL;
						}
					}
				}
				diffMiliSeconds = Duration.between(startTime, LocalTime.now()).getSeconds()*1000;
				if(diffMiliSeconds > this.timeout){
					Logger.log(LogLevels.TRACE, this, "Search timed out");
					break;
				}
				else{
					try {
					///	Logger.log(LogLevels.TRACE, this, "Waiting....");
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			while(true); 
		}
		//Logger.logToUser("Spent time: " + diffMiliSeconds + " miliseconds", this, MessageLogTypes.INFO);
		return answer;
	}
}
