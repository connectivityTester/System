package test;

import buffers.AnswerPattern;
import common.DeviceSource;
import common.SystemConfig;
import common.SystemConstants;
import types.IncomingMessageType;

public class Parameter implements Comparable<Parameter>{
	
	private String name;
	private String value;
	private boolean isVariable;
	private DeviceSource deviceSource;
	private IncomingMessageType messageType;
	
	public Parameter(String name, String device, IncomingMessageType type, String value) {
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
	
	private DeviceSource defineDeviceSource(String device) {
		DeviceSource result = SystemConfig.getInstance().getSystemDeviceSource();
		if(device != null){
			int deviceSourceId = Integer.parseInt(device);
			for(DeviceSource deviceSource : SystemConfig.getInstance().getDeviceSources()){
				if(deviceSource.getId() == deviceSourceId){
					result = deviceSource;
					break;
				}
			}
		}
		return result;
	}

	private boolean checkIsVariable(String value){
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
		if(this.deviceSource.getId() != SystemConstants.systemDeviceSourceId){
			answerPattern = new AnswerPattern(this.deviceSource.getId(), this.value); 
		}
		return answerPattern;
	}
	
	@Override
	public String toString() {
		return "\nParameter [name=" + name + ", value=" + value + "]";
	}

	@Override
	public int compareTo(Parameter otherParameter) {
		return this.name.compareTo(otherParameter.getName());
	}
	
}
