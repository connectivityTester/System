package buffers;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import types.LogLevels;
import utils.Logger;

public abstract class Buffer{	
	
	protected final ConcurrentLinkedQueue<ParsedIncomingMessage> queue;
	protected final int bufferCapacity; 
	protected Integer counter;
	protected final ExecutorService threadPool;
	
	
	Buffer(int bufferCapacity, int threadPoolSize){
		this.queue = new ConcurrentLinkedQueue<>();
		this.bufferCapacity = bufferCapacity;
		this.counter = 0;
		this.threadPool = Executors.newFixedThreadPool(threadPoolSize);
	}
	
	boolean addToBuffer(ParsedIncomingMessage bufferItem){
		if(this.bufferCapacity <= this.queue.size()){
			Logger.log(LogLevels.TRACE, this, "Method addToBuffer, buffer is full, oldest message will be deleted");
			this.queue.poll();
		}
		boolean result = this.queue.offer(bufferItem);
		synchronized (this.counter) {			
			this.counter++;
		}
		return result;
	}
	
	void clearBuffer(){
		this.queue.clear();
	}
	
	abstract Map<AnswerPattern , Future<ParsedIncomingMessage>> findAnswers(List<AnswerPattern> answerPatterns);
}
