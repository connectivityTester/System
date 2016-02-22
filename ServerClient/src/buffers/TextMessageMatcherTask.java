package buffers;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import types.IncomingMessageType;

public class TextMessageMatcherTask extends MessageMatcherTask {

	public TextMessageMatcherTask (String pattern, int deviceSourceId, ConcurrentLinkedQueue<ParsedIncomingMessage> queue) {
		super(pattern, deviceSourceId, queue);
	}

	@Override
	public ParsedIncomingMessage call() {
		ParsedIncomingMessage message = null;
		Pattern compiledPattern = Pattern.compile(".*" + this.messagePattern + ".*");
		Matcher matcher = null;
		ParsedIncomingMessage answer = null;
		Iterator<ParsedIncomingMessage> iterator = this.queue.iterator();
		while (iterator.hasNext()){
			message = iterator.next();
			if(this.deviceSourceId == message.getId() &&
					message.getConvertedType() == IncomingMessageType.TEXT)
			{
				matcher = compiledPattern.matcher(message.getData().toLowerCase());
				if(matcher.matches()){
					answer = message;
					break;
				}
			}
		}
		return answer;
	}

}
