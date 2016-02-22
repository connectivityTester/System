package connectivity.androiddevicesource.executors;
import android.app.Activity;
import android.util.Log;

import connectivity.androiddevicesource.commands.Command;
import connectivity.androiddevicesource.commands.CommandResult;
import connectivity.androiddevicesource.types.CommandResultTypes;

public class PhoneCommandExecutor implements iExecutor{
	private Activity activity;

	public PhoneCommandExecutor(Activity activity) {
		Log.i("PhoneCommandExecutor::", "Constructor started");
		this.activity = activity;
		Log.i("PhoneCommandExecutor::", "Constructor finished");
	}

	@Override
	public CommandResult executeCommand(Command command) {
		Log.i("PhoneCommandExecutor::", "Function  executeCommand started");
		CommandResult result = new CommandResult(CommandResultTypes.OK, null);
		Log.i("PhoneCommandExecutor::", "Function searchDevice, finished with result::" + result.getType().toString() );
		if(result.getType() != CommandResultTypes.OK){
			Log.i("PhoneCommandExecutor::", "Function searchDevice, Reason::" + result.getErrorReason() );
		}
		return result;
		
	}
}
