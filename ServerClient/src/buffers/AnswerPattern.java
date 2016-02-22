package buffers;

public class AnswerPattern {
	
	private final int deviceSourceId;
	private final String pattern;
	
	public AnswerPattern(int deviceSourceId, String pattern) {
		this.deviceSourceId = deviceSourceId;
		this.pattern = pattern;
	}
	
	public int getDeviceSourceId() 	{	return this.deviceSourceId;	}
	public String getPattern() 		{	return this.pattern;		}	

}
