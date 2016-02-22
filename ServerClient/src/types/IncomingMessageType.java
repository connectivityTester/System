package types;

public enum IncomingMessageType {
	TEXT("TEXT"),
	AUDIO("AUDIO"),
	PICTURE("PICTURE"),
	UNKNOWN_TYPE("UNKNOWN_TYPE");
	
	private String type;
	
	private IncomingMessageType(String typeName){
		this.type = typeName;
	}
	
	public static IncomingMessageType defineIncomingMessageType(String messageTypeName){
		for(IncomingMessageType messageType : IncomingMessageType.values()){
			if(messageType.type.equals(messageTypeName.toUpperCase())){
				return messageType;
			}
		}
		return UNKNOWN_TYPE;
	}
}
