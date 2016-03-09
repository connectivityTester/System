package buffers;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

import buffers.matchers.AnswerPattern;
import buffers.matchers.MessageMatcher;
import buffers.matchers.TextMessageMatcherTask;
import exceptions.UnknownMessageTypeException;
import types.IncomingMessageType;
import types.LogLevels;
import utils.Logger;

class Buffer{	
	
	private final ConcurrentLinkedQueue<String> queue;
	private final int bufferCapacity; 
	private final MessageMatcherFactory messageMatcherFactory = new MessageMatcherFactory(this);
	
	Buffer(int bufferCapacity){
		this.queue = new ConcurrentLinkedQueue<>();
		this.bufferCapacity = bufferCapacity;
	}
	
	void addToBuffer(List<String> messages){
		if(this.bufferCapacity <= this.queue.size()){
			Logger.log(LogLevels.TRACE, this, "Method addToBuffer, buffer is full, oldest message will be deleted");
			this.queue.poll();
		}
		messages.parallelStream().forEach((message) -> this.queue.offer(message));
	}
	
	void clearBuffer(){	this.queue.clear();	}

	public Object findPattern(AnswerPattern pattern, IncomingMessageType type, int timeout) throws UnknownMessageTypeException {
		MessageMatcher messageMatcher = this.messageMatcherFactory.createMessageMatcher(pattern, timeout, type);
		LocalTime startTime = LocalTime.now();
		Object foundAnswer = messageMatcher.match();
		pattern.setSpentTime(Duration.between(startTime, LocalTime.now()).getNano()/1_000_000, TimeUnit.MILLISECONDS);
		return foundAnswer;
	}
	
	class MessageMatcherFactory {
		
		private final Buffer buffer;

		MessageMatcherFactory(Buffer buffer){
			this.buffer = buffer;
		}
		
		public MessageMatcher createMessageMatcher(AnswerPattern answerPattern, 
													int timeout, IncomingMessageType type) throws UnknownMessageTypeException
		{
			MessageMatcher messageMatcher = null;
			switch (type) {
				case TEXT:	messageMatcher = new TextMessageMatcherTask(answerPattern.getPattern(), 
														answerPattern.getDeviceSourceId(), this.buffer.queue, timeout);	break;
				default:
					throw new UnknownMessageTypeException("Unknown message type: " + type.toString());				
			}
			return messageMatcher;
		}
	}
}
