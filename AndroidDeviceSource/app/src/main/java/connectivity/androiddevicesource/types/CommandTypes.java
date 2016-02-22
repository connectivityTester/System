package connectivity.androiddevicesource.types;

public enum CommandTypes {
	//telephony
	MAKE_CALL,
	ACCEPT_CALL,
	END_CALL,
	REJECT_CALL,
	PUT_ON_HOLD,
	SWAP_CALLS,
	MAKE_CONFERENCE,
	//bluetooth
	PAIR_TO_TARGET,// target was not pair before
	CONFIRM_CONNECTION,
	SET_PIN,
	BT_ON,
	BT_OFF,
	SEARCH_DEVICE,
	CONNECT_TARGET,// target is already paired, but connection is not active
	DISCONNECT_DEVICE,
	UNPAIR_DEVICE,
	ACTIVATE_PROFILE,
	DEACTIVATE_PROFILE,
	//
	UNKNOWN_COMMAND
}
