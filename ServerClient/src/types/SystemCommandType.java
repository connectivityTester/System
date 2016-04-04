package types;

import java.util.Arrays;

public enum SystemCommandType {
	SLEEP("sleep"),
	IF("if"),
	LOOP("loop"),
	ARITH_OPERATION("arithmetic_operation"),
	ANSWERS("answers"),
	EXEC_LIB("exec_lib"),
	UNKNOWN_COMMAND("unknown_command");
	
	private String name;
	
	private SystemCommandType(String systemCommandName){
		this.name = systemCommandName;
	}
	
	public static SystemCommandType defineCommandType(String commandName){
		return Arrays.stream(values())
				.filter(type -> type.name.equalsIgnoreCase(commandName))
				.findFirst()
				.orElse(UNKNOWN_COMMAND);
	}
}
