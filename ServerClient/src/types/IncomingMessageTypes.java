package types;

public enum IncomingMessageTypes {
	TEXT("TEXT"),
	AUDIO("AUDIO"),
	PICTURE("PICTURE"),
	UNKNOWN_TYPE("UNKNOWN_TYPE");
	
	private String nativeName; 	
	
	IncomingMessageTypes(String nativeName){
		this.nativeName = nativeName;
	}
	
	public String getNativeName(){
		return this.nativeName;
	}
	
	public static IncomingMessageTypes defineMessageType(String messageTypeName){
		try{
			return IncomingMessageTypes.valueOf(messageTypeName);
		}catch(IllegalArgumentException e){			
			return IncomingMessageTypes.UNKNOWN_TYPE;
		}
	}
}
