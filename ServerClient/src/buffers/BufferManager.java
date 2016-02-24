package buffers;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import types.IncomingMessageType;

public class BufferManager {

	private final Buffer incomingMessagesBuffer = new Buffer(Integer.MAX_VALUE);
	private final static BufferManager bufferManager = new BufferManager();
	
	public static BufferManager getInstance() 	 	{	return bufferManager;								}	
	public void addMessageToBuffer(List<String> messages)	{	this.incomingMessagesBuffer.addToBuffer(messages);	}
	public void clearBuffers()						{	this.incomingMessagesBuffer.clearBuffer();			}
	
	public Map<AnswerPattern, Object> findAnswersInBuffer(List<AnswerPattern> patterns, IncomingMessageType messageType){
		Map<AnswerPattern, Object> foundAnswers = new HashMap<AnswerPattern, Object>(patterns.size());
		patterns.forEach((pattern) -> {
			foundAnswers.put(pattern, this.incomingMessagesBuffer.findPattern(pattern, messageType));
		});
		return foundAnswers;
	}
	
}
