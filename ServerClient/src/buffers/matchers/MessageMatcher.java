package buffers.matchers;
import java.util.Queue;

public abstract class MessageMatcher {
	
	protected final Queue<String> queue;
	protected final int deviceSourceId;
	protected final String pattern;
	protected final int timeout;
	
	MessageMatcher(String pattern, int deviceSourceDeviceId, Queue<String> queue, int timeout) {
		this.pattern = pattern;
		this.queue = queue;
		this.deviceSourceId = deviceSourceDeviceId;
		this.timeout = timeout;
	}

	public abstract Object match();	
}
