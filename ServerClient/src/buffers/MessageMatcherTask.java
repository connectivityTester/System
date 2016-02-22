package buffers;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class MessageMatcherTask implements Callable<ParsedIncomingMessage>{
	
	protected final String messagePattern; 
	protected ConcurrentLinkedQueue<ParsedIncomingMessage> queue;
	protected final int deviceSourceId;
	
	MessageMatcherTask(String messagePattern, int deviceSourceDeviceId, ConcurrentLinkedQueue<ParsedIncomingMessage> queue) {
		this.messagePattern = messagePattern.toLowerCase();
		this.queue = queue;
		this.deviceSourceId = deviceSourceDeviceId;
	}

	@Override
	public abstract ParsedIncomingMessage call();	

}
