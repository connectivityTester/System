package buffers;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import common.SystemConstants;
import types.LogLevels;
import utils.Logger;

public class IncomingMessageReceiver {
	
	private final int maxThreadCount = SystemConstants.maxThreadCount;
	private final ExecutorService threadPool = Executors.newFixedThreadPool(this.maxThreadCount);
	private final static IncomingMessageReceiver incomingMessageReceiver = new IncomingMessageReceiver();
	private boolean isAdditionToBufferStopted = true;
	
	public static IncomingMessageReceiver getInstance(){
		return incomingMessageReceiver;
	}
	
	public void addMessageToBuffer(List<String> newMessages){
		if(this.isAdditionToBufferStopted){
			Logger.log(LogLevels.INFO, this, "Messages to submit: " + newMessages);
			for(String newMessage : newMessages){
				Thread thread = new Thread(new IncomingMessageParser(newMessage));
				thread.setPriority(Thread.MIN_PRIORITY);
				this.threadPool.submit(thread);
			}
		}
	}
	
	public void allowAdditionToBuffer(boolean allow){
		this.isAdditionToBufferStopted = allow;
	}
}
