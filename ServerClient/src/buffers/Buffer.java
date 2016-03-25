package buffers;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
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
	
	Buffer(final int bufferCapacity){
		this.queue = new ConcurrentLinkedQueue<>();
		this.bufferCapacity = bufferCapacity;
	}
	
	void addToBuffer(final List<String> messages){
		Objects.requireNonNull(messages);
		
		if(this.bufferCapacity <= this.queue.size()){
			Logger.log(LogLevels.TRACE, this, "Method addToBuffer, buffer is full, oldest message will be deleted");
			this.queue.poll();
		}
		messages.parallelStream().forEach((message) -> this.queue.offer(message));
	}
	
	void clearBuffer(){	queue.clear();	}

	public Object findPattern(final AnswerPattern pattern, final IncomingMessageType type, final int timeout) throws UnknownMessageTypeException {
		Objects.requireNonNull(pattern);
		
		if(type == IncomingMessageType.UNKNOWN_TYPE){
			throw new UnknownMessageTypeException("Unknown message type: " + type.toString());
		}
		final MessageMatcher messageMatcher = this.messageMatcherFactory.createMessageMatcher(pattern, timeout, type);
		LocalTime startTime = LocalTime.now();
		final Object foundAnswer = messageMatcher.match();
		pattern.setSpentTime(Duration.between(startTime, LocalTime.now()).getNano()/1_000_000, TimeUnit.MILLISECONDS);
		return foundAnswer;
	}
	
	private class MessageMatcherFactory {
		
		private final Buffer buffer;

		MessageMatcherFactory(final Buffer buffer){
			this.buffer = buffer;
		}
		
		public MessageMatcher createMessageMatcher(final AnswerPattern answerPattern, 
													final int timeout, final IncomingMessageType type) 
																	throws UnknownMessageTypeException
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
