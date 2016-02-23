package buffers;

import java.util.List;
public class IncomingMessageReceiver {
	
	private final static IncomingMessageReceiver incomingMessageReceiver = new IncomingMessageReceiver();
	private boolean isAdditionToBufferStopted = true;
	
	public static IncomingMessageReceiver getInstance()	{	return incomingMessageReceiver;	}
	
	public void allowAdditionToBuffer(boolean allow)	{	this.isAdditionToBufferStopted = allow;	}

	public void addMessageToBuffer(List<String> newMessages){
		if(this.isAdditionToBufferStopted){
			BufferManager.getInstance().addMessageToBuffer(newMessages);
		}
	}
	
}
