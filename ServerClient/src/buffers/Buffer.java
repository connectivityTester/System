package buffers;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

import types.IncomingMessageType;
import types.LogLevels;
import utils.Logger;

class Buffer{	
	
	private final ConcurrentLinkedQueue<String> queue;
	private final int bufferCapacity; 
		
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

	public Object findPattern(AnswerPattern pattern, IncomingMessageType type, int timeout) {
		MessageMatcher messageMatcher = null;
		switch (type) {
			case TEXT:	messageMatcher = new TextMessageMatcherTask(pattern.getDeviceSourceId(), this.queue);	break;
		}
		LocalTime startTime = LocalTime.now();
		Object foundAnswer = messageMatcher.match(pattern.getPattern(), timeout);
		pattern.setTimeUnit(TimeUnit.MILLISECONDS);
		pattern.setSpentTime(Duration.between(startTime, LocalTime.now()).getNano()/1_000_000);
		return foundAnswer;
	}
}
