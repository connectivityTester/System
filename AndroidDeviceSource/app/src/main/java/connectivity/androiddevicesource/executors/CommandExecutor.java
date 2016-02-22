package connectivity.androiddevicesource.executors;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import connectivity.androiddevicesource.commands.Command;
import connectivity.androiddevicesource.commands.CommandResult;
import connectivity.androiddevicesource.commands.Parameter;
import connectivity.androiddevicesource.types.CommandResultTypes;
import connectivity.androiddevicesource.types.CommandTypes;

import android.app.Activity;
import android.util.Log;

public class CommandExecutor {

	private static CommandExecutor commandExecutor;
	private PhoneCommandExecutor phoneCommandExecutor;
	private BluetoothCommandExecutor bluetoothCommandExecutor;
	
	private CommandExecutor(Activity activity){
		Log.i("CommandExecutor::", "Constructor started");
		this.phoneCommandExecutor = new PhoneCommandExecutor(activity);
		this.bluetoothCommandExecutor = new BluetoothCommandExecutor(activity);
		Log.i("CommandExecutor::", "Constructor finished");
	}
	
	public static CommandExecutor getInstance(Activity activity){
		if(commandExecutor == null){
			commandExecutor = new CommandExecutor(activity);
		}
		return commandExecutor;
	}
	
	public CommandResult executeCommand(String command){
		Log.i("CommandExecutor::", "Function EXECUTECOMMAND started");
		Command newCommand = null;
		newCommand = this.createCommand(command);
		iExecutor executor = this.defineExecutor(newCommand.getCommandType());
		CommandResult result = executor.executeCommand(newCommand);
		Log.i("CommandExecutor::", "Function executeCommand, finished with result::" + result.getType().toString() );
		if(result.getType() != CommandResultTypes.OK){
			Log.i("CommandExecutor::", "Function executeCommand, Reason::" + result.getErrorReason() );
		}
		return result;
	}
	
	private Command createCommand(String command){
		Log.i("CommandExecutor::", "Function createCommand started");
		JSONObject reader = null;
		try {
			reader = new JSONObject(command);
		} catch (JSONException e) {
			Log.i("CommandExecutor::", "Function createCommand, problem with parsing JSON");
		}
		CommandTypes commandType = null;
		try {
			commandType = this.defineCommandType(reader.getString("commandName"));
		} catch (JSONException e) {
			Log.i("CommandExecutor::", "Function createCommand, problem with getting string from JSON");
		}
		List<Parameter> parameters = new ArrayList<Parameter>();
		JSONArray params = null;
		try {
			params = reader.getJSONArray("params");
		} catch (JSONException e) {
			params = null;
		}
		int localDeviceSourceId = -1;
		try{
			localDeviceSourceId = reader.getInt("id");
		}
		catch (JSONException e){
			Log.i("CommandExecutor::", "Function createCommand, problem with getting ID from JSON");
		}
		if(params != null) {
			for (int i = 0; i < params.length(); ++i) {
				try {
					parameters.add(new Parameter(params.getJSONObject(i).getString("name"), params.getJSONObject(i).getString("value")));
				} catch (JSONException e) {
					Log.i("CommandExecutor::", "Function createCommand, problem with getting parameters from JSON");
				}
			}
		}
		Log.i("CommandExecutor::", "Function createCommand finished");
		return new Command(localDeviceSourceId, commandType, parameters);
	}
	
	private CommandTypes defineCommandType(String type){
		Log.i("CommandExecutor::", "Function defineCommandType started");
		CommandTypes commandType = CommandTypes.UNKNOWN_COMMAND;
		switch(type){
//			case "MAKE_CALL"		: commandType = CommandTypes.MAKE_CALL; 			break;
//			case "ACCEPT_CALL"		: commandType = CommandTypes.ACCEPT_CALL;		break;
//			case "END_CALL"			: commandType = CommandTypes.END_CALL;			break;
//			case "MAKE_CONFERENCE"	: commandType = CommandTypes.MAKE_CONFERENCE;	break;
//			case "PUT_ON_HOLD"		: commandType = CommandTypes.PUT_ON_HOLD;		break;
//			case "REJECT_CALL"		: commandType = CommandTypes.REJECT_CALL;		break;
//			case "SWAP_CALLS"		: commandType = CommandTypes.SWAP_CALLS;			break;
			case "connect_to_target"	: commandType = CommandTypes.CONNECT_TARGET; 	break;
			case "confirm_connection"	: commandType = CommandTypes.CONFIRM_CONNECTION;break;
			case "bt_on"				: commandType = CommandTypes.BT_ON;				break;
			case "bt_off"				: commandType = CommandTypes.BT_OFF;			break;
			case "search_device"		: commandType = CommandTypes.SEARCH_DEVICE; 	break;
			case "pair_to_target"		: commandType = CommandTypes.PAIR_TO_TARGET;	break;
			case "disconnect_device"	: commandType = CommandTypes.DISCONNECT_DEVICE; break;
			case "unpair_device"		: commandType = CommandTypes.UNPAIR_DEVICE;		break;
			case "activate_profile"		: commandType = CommandTypes.ACTIVATE_PROFILE;	break;
			case "deactivate_profile"	: commandType = CommandTypes.DEACTIVATE_PROFILE;break;
		}
		Log.i("CommandExecutor::", "Function defineCommandType finished");
		return commandType;
	}
	
	private iExecutor defineExecutor(CommandTypes commandType){
		Log.i("CommandExecutor::", "Function defineExecutor started");
		iExecutor executor = null;
		switch(commandType){
//			case ACCEPT_CALL		: 	executor = this.phoneCommandExecutor;		break;
//			case END_CALL			: 	executor = this.phoneCommandExecutor;		break;
//			case MAKE_CALL			: 	executor = this.phoneCommandExecutor;		break;
//			case MAKE_CONFERENCE	:	executor = this.phoneCommandExecutor;		break;
//			case PUT_ON_HOLD		:	executor = this.phoneCommandExecutor; 		break;
//			case REJECT_CALL		: 	executor = this.phoneCommandExecutor;		break;
//			case SWAP_CALLS			:	executor = this.phoneCommandExecutor;		break;
			case PAIR_TO_TARGET		: 	executor = this.bluetoothCommandExecutor; 	break;
			case CONFIRM_CONNECTION	:	executor = this.bluetoothCommandExecutor;	break;
			case SET_PIN			: 	executor = this.bluetoothCommandExecutor;	break;
			case BT_ON				: 	executor = this.bluetoothCommandExecutor; 	break;
			case BT_OFF				: 	executor = this.bluetoothCommandExecutor;	break;
			case SEARCH_DEVICE		: 	executor = this.bluetoothCommandExecutor;	break;
			case CONNECT_TARGET		:	executor = this.bluetoothCommandExecutor;	break;
			case DISCONNECT_DEVICE	:	executor = this.bluetoothCommandExecutor;	break;
			case UNPAIR_DEVICE		:	executor = this.bluetoothCommandExecutor;	break;
			case ACTIVATE_PROFILE	:	executor = this.bluetoothCommandExecutor;	break;
			case DEACTIVATE_PROFILE	:	executor = this.bluetoothCommandExecutor;	break;
			case UNKNOWN_COMMAND	:
				Log.i("CommandExecutor::", "Function defineExecutor, UNKNOWN_COMMAND was received!!!!");
				executor = this.phoneCommandExecutor;
				break;
		}
		Log.i("CommandExecutor::", "Function defineExecutor finished");
		return executor;
	}
	
}
