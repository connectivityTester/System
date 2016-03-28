package buffers.matchers;
import java.util.Queue;

import types.IncomingMessageType;
import utils.Utils;

public abstract class MessageMatcher {
	
	protected final Queue<String> queue;
	protected final int deviceSourceId;
	protected final String pattern;
	protected final int timeout;
	protected IncomingMessageType  messageType = IncomingMessageType.UNKNOWN_TYPE;
	
	MessageMatcher(final String pattern, final int deviceSourceDeviceId, final Queue<String> queue, final int timeout) {
		Utils.requireNonNull(pattern, queue);
		
		this.pattern = pattern;
		this.queue = queue;
		this.deviceSourceId = deviceSourceDeviceId;
		this.timeout = timeout;
	}

	public abstract Object match();	
}
