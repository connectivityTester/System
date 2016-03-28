package buffers.matchers;
import java.util.concurrent.TimeUnit;

import utils.Utils;

public class AnswerPattern {
	
	private final int deviceSourceId;
	private final String pattern;
	private TimeUnit timeUnit;
	private long spentTime;
	
	public AnswerPattern(final int deviceSourceId, final String pattern) {
		Utils.requireNonNull(pattern);
		
		this.deviceSourceId = deviceSourceId;
		this.pattern = pattern;
	}
	
	public int getDeviceSourceId() 	{	return this.deviceSourceId;						}
	public String getPattern() 		{	return this.pattern;							}
	public String getTimeUnit() 	{	return this.timeUnit.toString().toLowerCase();	}
	public long getSpentTime() 		{	return this.spentTime;							}

	public void setSpentTime(final long spentTime, final TimeUnit timeUnit) {	
		Utils.requireNonNull(timeUnit);
		
		this.spentTime = spentTime;	
		this.timeUnit = timeUnit;
	}	

}
