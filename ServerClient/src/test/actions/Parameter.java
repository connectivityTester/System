package test.actions;

import buffers.matchers.AnswerPattern;
import common.DeviceSource;
import common.SystemConstants;
import types.IncomingMessageType;
import xml.SystemConfig;

public class Parameter implements Comparable<Parameter>{
	
	private final String name;
	private final String value;
	private final  boolean isVariable;
	private final DeviceSource deviceSource;
	private final IncomingMessageType messageType;
	
	public Parameter(final String name, final String device, final IncomingMessageType type, final String value) {
		utils.Utils.requireNonNull(name, type, value);
		
		this.name = name;
		this.isVariable = this.checkIsVariable(value);
		if(this.isVariable){
			this.value = value.substring(1, value.length()-1);
		}
		else{
			this.value = value;
		}
		this.messageType = type;
		this.deviceSource = this.defineDeviceSource(device);
	}
	
	private DeviceSource defineDeviceSource(final String deviceId) {
		if(deviceId == null){
			return SystemConfig.getInstance().getSystemDeviceSource();
		}
		return SystemConfig.getInstance().getDeviceSources().stream()
				.filter(device -> device.equalsId(Integer.parseInt(deviceId)))
				.findFirst()
				.orElse(SystemConfig.getInstance().getSystemDeviceSource());
	}

	private boolean checkIsVariable(final String value){
		utils.Utils.requireNonNull(value);
		
		if(
			value.indexOf('~') == 0
			&&
			value.lastIndexOf('~') == value.length()-1
		)
		{
			return true;
		}
		return false;
	}
	
	public String getName() 						{ return this.name;			}
	public String getValue()						{ return this.value;		}
	public DeviceSource getDeviceSource()			{ return this.deviceSource;	}
	public IncomingMessageType getMessageType() 	{ return this.messageType;	}
	public boolean isVariable()						{ return this.isVariable;	}
	
	public AnswerPattern getAnswerPattern(){
		AnswerPattern answerPattern = null;
		if(!deviceSource.equalsId(SystemConstants.systemDeviceSourceId)){
			answerPattern = new AnswerPattern(this.deviceSource.getId(), this.value); 
		}
		return answerPattern;
	}
	
	@Override
	public String toString() {
		return "\nParameter [name=" + name + ", value=" + value + "]";
	}

	@Override
	public int compareTo(final Parameter otherParameter) {
		utils.Utils.requireNonNull(otherParameter);
		
		return this.name.compareTo(otherParameter.getName());
	}
	
}
