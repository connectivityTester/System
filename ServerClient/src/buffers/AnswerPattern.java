package buffers;

import java.util.concurrent.TimeUnit;

public class AnswerPattern {
	
	private final int deviceSourceId;
	private final String pattern;
	private TimeUnit timeUnit;
	private long spentTime;
	
	public AnswerPattern(int deviceSourceId, String pattern) {
		this.deviceSourceId = deviceSourceId;
		this.pattern = pattern;
	}
	
	public int getDeviceSourceId() 	{	return this.deviceSourceId;						}
	public String getPattern() 		{	return this.pattern;							}
	public String getTimeUnit() 	{	return this.timeUnit.toString().toLowerCase();	}
	public long getSpentTime() 		{	return this.spentTime;							}

	public void setTimeUnit(TimeUnit timeUnit) 	{	this.timeUnit = timeUnit;	}
	public void setSpentTime(long spentTime) 	{	this.spentTime = spentTime;	}	

}
