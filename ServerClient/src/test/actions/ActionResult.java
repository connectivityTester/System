package test.actions;

import types.ActionResultTypes;

public class ActionResult {
	private ActionResultTypes resultType = ActionResultTypes.NOT_EXECUTED;
	private final String reason;
	
	public ActionResult (final ActionResultTypes actionResult, final String reason){
		utils.Utils.requireNonNull(actionResult);
		
		this.resultType = actionResult;
		this.reason = reason;
	}

	public ActionResultTypes getResultType(){	return resultType;	}
	public String getReason() 				{	return reason;		}
}
