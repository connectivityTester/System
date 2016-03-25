package buffers.matchers;

import java.util.Objects;

import types.IncomingMessageType;

public class ParsedIncomingMessage 
{
	private int id;
	private String type;
	private String data;

	public void setId(int id) 		{	this.id = id;	}
	public void setType(String type){	this.type = type;	}
	public void setData(String data){	this.data = data;	}
	public int getId() 				{	return id;	}
	public String getType() 		{	return type;	}
	public String getData() 		{	return data;	}
	
	public IncomingMessageType getConvertedType() {
		return IncomingMessageType.defineIncomingMessageType(this.type);
	}
	
	public boolean equalsType(final IncomingMessageType type){
		Objects.requireNonNull(type);
		
		return type == IncomingMessageType.defineIncomingMessageType(this.type);
	}

	@Override
	public String toString() {
		return "ParsedIncomingMessage [id=" + id + ", type=" + type
				+ ", data=" + data + "]";
	}	
}
