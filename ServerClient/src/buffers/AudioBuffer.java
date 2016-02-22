package buffers;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

class AudioBuffer extends Buffer{

	AudioBuffer(int bufferCapacity, int threadPoolSize) {
		super(bufferCapacity, threadPoolSize);
	}

	@Override
	Map<AnswerPattern , Future<ParsedIncomingMessage>> findAnswers (List<AnswerPattern> patterns) {
		return null;
	}
}
