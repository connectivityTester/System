package hmc_dis2.androidtracesource.types;

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
	PAIR_BY_NAME,// connect to target by name(which not pair before)
	PAIR_BY_ADDRESS,// connect to target by bluetooth mac address(which not pair before)
	CONFIRM_CONNECTION,
	SET_PIN,
	BT_ON,
	BT_OFF,
	SEARCH_DEVICE,
	CONNECT_TARGET,// target is already paired, but connection is not active
	DISCONNECT_DEVICE,
	UNPAIR_DEVICE,
	UNPAIP_ALL_DEVICES,
	ACTIVATE_PROFILE,
	DEACTIVATE_PROFILE,
	//
	UNKNOWN_COMMAND
}
