package buffers;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import buffers.matchers.AnswerPattern;
import exceptions.UnknownMessageTypeException;
import types.IncomingMessageType;

public class BufferManager {

	private final Buffer incomingMessagesBuffer = new Buffer(Integer.MAX_VALUE);
	private final static BufferManager bufferManager = new BufferManager();
	private boolean allowAddition = false;
	
	public static BufferManager getInstance() 		{	return bufferManager;						}	
	public void clearBuffers()						{	this.incomingMessagesBuffer.clearBuffer();	}
	public void setIsAllowAddition(boolean isAllow)	{	this.allowAddition = isAllow;				}
	
	public void addMessageToBuffer(final List<String> messages)	{
		Objects.requireNonNull(messages);
		
		if(this.allowAddition){
			this.incomingMessagesBuffer.addToBuffer(messages);	
		}
	}	
	
	public Map<AnswerPattern, Object> findAnswersInBuffer(final List<AnswerPattern> patterns,
								final IncomingMessageType messageType, final int timeout) throws UnknownMessageTypeException
	{
		final Map<AnswerPattern, Object> foundAnswers = new HashMap<AnswerPattern, Object>(patterns.size());
		for(AnswerPattern pattern : patterns){
			foundAnswers.put(pattern, this.incomingMessagesBuffer.findPattern(pattern, messageType, timeout));
		}
		return foundAnswers;
	}	
}
