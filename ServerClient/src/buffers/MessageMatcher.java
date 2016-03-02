package buffers;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class MessageMatcher {
	
	protected ConcurrentLinkedQueue<String> queue;
	protected final int deviceSourceId;
	
	MessageMatcher(int deviceSourceDeviceId, ConcurrentLinkedQueue<String> queue) {
		this.queue = queue;
		this.deviceSourceId = deviceSourceDeviceId;
	}

	public abstract Object match(String messagePattern, int timeout);	
}
