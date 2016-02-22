package types;

public enum SystemCommandType {
	SLEEP("sleep"),
	IF("if"),
	LOOP("loop"),
	ARITH_OPERATION("arithmetic_operation"),
	ANSWERS("answers"),
	UNKNOWN_COMMAND("unknown_command");
	
	private String name;
	
	private SystemCommandType(String systemCommandName){
		this.name = systemCommandName;
	}
	
	public static SystemCommandType defineCommandType(String commandName){
		for(SystemCommandType type : values()){
			if(commandName.toLowerCase().equals(type.name)){
				return type;
			}
		}
		return SystemCommandType.UNKNOWN_COMMAND;
	}
}
