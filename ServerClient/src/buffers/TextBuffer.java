package buffers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

class TextBuffer extends Buffer{	
	
	TextBuffer(int bufferCapacity, int threadPoolSize) {
		super(bufferCapacity, threadPoolSize);
	}

	@Override
	Map<AnswerPattern , Future<ParsedIncomingMessage>> findAnswers (List<AnswerPattern> answerPatterns){
		Map<AnswerPattern , Future<ParsedIncomingMessage>> answers = new HashMap<>(answerPatterns.size());
		for(AnswerPattern pattern : answerPatterns){
			Future<ParsedIncomingMessage> future = 
					this.threadPool.submit(new TextMessageMatcherTask(pattern.getPattern().toString(), pattern.getDeviceSourceId(), this.queue));
			answers.put(pattern, future);
		}
		return answers;
	}

}
