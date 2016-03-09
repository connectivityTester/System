package common;

import java.util.List;
import types.SourceTypes;

public class DeviceSource {
	private final String name;
	private final SourceTypes sourceType;
	private final int id;
	private final boolean autoStartUp;
	private final String address;
	private final List<String> startUpParameters;	
	private final List<String> shutdownParameters;
	
	public DeviceSource(String name, int id, boolean autoStartUp, 
			String address, List<String> startUpParameters, List<String> shutdownParameters) 
	{
		this.name = name;
		this.id = id;
		if(this.id >= 0 ){
			this.sourceType = SourceTypes.EXTERNAL_SOURCE;
		}
		else{
			this.sourceType	= SourceTypes.SYSTEM_SOURCE;
		}
		this.autoStartUp = autoStartUp;
		this.address = address;
		this.startUpParameters = startUpParameters;
		this.shutdownParameters = shutdownParameters;
	}
	
	public List<String> getShutdownParameters() {	return shutdownParameters;	}
	public SourceTypes getSourceType() {	return sourceType;	}	
	public String getName() {		return name;	}
	public int getId() {		return id;	}
	public boolean isAutoStartUp() {		return autoStartUp;	}
	public String getAddress() {		return address;	}
	public List<String> getStartUpParameters() {
		return startUpParameters;
	}
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("\nDeviceSource [name=" + name + ", id=" + id + ", autoStartUp=" + autoStartUp + ", address=" + address
				 + ", startUpParameters=");
		if(this.startUpParameters != null){
			for(String param : this.startUpParameters){
				result.append(param + " ");
			}
		}
		result.append("]");
		return result.toString();
	}

	
}
