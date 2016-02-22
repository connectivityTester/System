package buffers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import common.SystemConfig;
import common.SystemConstants;
import types.IncomingMessageType;
import types.LogLevels;
import utils.Logger;

public class BufferManager {

	private final static BufferManager bufferManager = new BufferManager();
	private final int threadPoolSize = SystemConstants.maxThreadCount;
	
	private final AudioBuffer audioBuffer;
	private final PictureBuffer pictureBuffer;
	private final TextBuffer textBuffer;
	
	private BufferManager(){
		this.audioBuffer = new AudioBuffer(10, 1);
		this.pictureBuffer = new PictureBuffer(10, 1);
		this.textBuffer = new TextBuffer(SystemConfig.getInstance().getMAX_CAPACITY(), this.threadPoolSize);
	}
	
	public final static BufferManager getInstance(){
		return bufferManager;
	}
	
	public void addMessageToBuffer(ParsedIncomingMessage message){
		switch (message.getConvertedType()) {
			case TEXT:
				this.textBuffer.addToBuffer(message);
				break;
			case PICTURE:
				this.pictureBuffer.addToBuffer(message);
				break;
			case AUDIO:
				this.audioBuffer.addToBuffer(message);
				break;
			case UNKNOWN_TYPE:
				Logger.log(LogLevels.ERROR, "BufferManager", "UNKNOWN_TYPE message was received");
				Logger.log(LogLevels.ERROR, "BufferManager", "Message was not added to any buffers");
				break;
		}
	}
	
	public Map<AnswerPattern, Future<ParsedIncomingMessage>> findAnswersInBuffer(List<AnswerPattern> patterns, IncomingMessageType messageType){
		Map<AnswerPattern, Future<ParsedIncomingMessage>> foundAnswers = new LinkedHashMap<>(patterns.size());
		switch (messageType) {
			case TEXT:
				foundAnswers.putAll(this.textBuffer.findAnswers(patterns));
				break;
			case PICTURE:
				foundAnswers.putAll(this.pictureBuffer.findAnswers(patterns));
				break;
			case AUDIO:
				foundAnswers.putAll(this.audioBuffer.findAnswers(patterns));
				break;
			default:
				break;
		}
		return foundAnswers;
	}
	
	public void clearBuffers(){
		this.textBuffer.clearBuffer();
		this.pictureBuffer.clearBuffer();
		this.audioBuffer.clearBuffer();
	}
}
