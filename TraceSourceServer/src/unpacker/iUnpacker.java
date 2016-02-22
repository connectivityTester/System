package unpacker;

import commands.Command;

public interface iUnpacker {
	public Command unpackMessageToCommand(String message);
}
