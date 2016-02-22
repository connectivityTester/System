package types;

public enum SystemCommandTypes {
	SLEEP("sleep"),
	IF("if"),
	LOOP("loop"),
	ARITH_OPERATION("arithmetic_operation"),
	ANSWERS("answers"),
	UNKNOWN_COMMAND("unknown_command");
	
	private String commandName;
	
	SystemCommandTypes(String commandName){
		this.commandName = commandName;
	}
	
	public static SystemCommandTypes defineSystemCommandType(String commandName){
		for(SystemCommandTypes type : SystemCommandTypes.values()){
			if(commandName.toLowerCase().equals(type.commandName)){
				return type;
			}
		}
		return SystemCommandTypes.UNKNOWN_COMMAND;
	}
}
