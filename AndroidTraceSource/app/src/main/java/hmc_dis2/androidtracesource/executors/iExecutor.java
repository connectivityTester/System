package hmc_dis2.androidtracesource.executors;

import hmc_dis2.androidtracesource.commands.Command;
import hmc_dis2.androidtracesource.commands.CommandResult;

public interface iExecutor {
	public CommandResult executeCommand(Command command);
}
