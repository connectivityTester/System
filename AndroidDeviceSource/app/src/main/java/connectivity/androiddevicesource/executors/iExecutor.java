package connectivity.androiddevicesource.executors;

import connectivity.androiddevicesource.commands.Command;
import connectivity.androiddevicesource.commands.CommandResult;

public interface iExecutor {
	public CommandResult executeCommand(Command command);
}
