package test.actions;

import types.ActionResultTypes;

public class ActionResult {
	private ActionResultTypes resultType = ActionResultTypes.NOT_EXECUTED;
	private String reason;
	
	public ActionResult (ActionResultTypes actionResult, String reason){
		this.resultType = actionResult;
		this.reason = reason;
	}

	public ActionResultTypes getResultType(){	return resultType;	}
	public String getReason() 				{	return reason;		}
}
