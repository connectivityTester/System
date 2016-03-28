package test;

import java.util.Collections;
import java.util.List;
import buffers.BufferManager;
import common.DeviceSource;
import test.actions.Action;
import test.actions.ActionResult;
import test.actions.Variable;
import test.datahandlers.ActionDataHanlder;
import test.datahandlers.ExternalDeviceDataHandler;
import test.datahandlers.SystemDataHandler;
import types.ActionResultTypes;
import types.FailReaction;
import types.LogLevels;
import types.MessageLogTypes;
import utils.Logger;
import xml.Context;

public class Test implements Context{

	private String testName;	
	private List<Action> testActions;
	private List<Variable> testVariables;
	
	public Test(final List<Variable> testVariables, final List<Action> testActions) {
		utils.Utils.requireNonNull(testActions, testVariables);
		
		this.testVariables = testVariables;
		this.testActions = testActions; 
	}
	
	public void executeTest(){
		Logger.logToUser( "----- Test \""+this.testName+"\" was started -----", this, MessageLogTypes.HEADER);
		for(int actionIndex = 0; actionIndex < this.testActions.size(); ++actionIndex){
			Logger.logToUser( "-------- ACTION ¹" + (actionIndex + 1) +
					"\"" + this.testActions.get(actionIndex).getCommand().getCommandName() + "\" --------", this, MessageLogTypes.HEADER);
			ActionDataHanlder dataHanlder = this.defineDataHandler(this.testActions.get(actionIndex).getCommand().getDeviceSource());
			ActionResult result = dataHanlder.handleActionData(this.testVariables, this.testActions.get(actionIndex));
			Logger.log(LogLevels.TRACE, this, "Method executeTest, action result is " + result.getResultType());
			this.testActions.get(actionIndex).setActionResult(result);
			if(result.getResultType() == ActionResultTypes.NOK){
				Logger.logToUser( "Action result is NOK", this, MessageLogTypes.HEADER);
				if(this.testActions.get(actionIndex).getReTry()>0){
					Logger.logToUser( "Action execution will be repeated " + this.testActions.get(actionIndex).getReTry() + " times" , this, MessageLogTypes.HEADER);
					for(int i = 0; i < this.testActions.get(actionIndex).getReTry(); ++i){
						Logger.logToUser( "Iteration ¹" + (i+1), this, MessageLogTypes.HEADER);
						result = dataHanlder.handleActionData(this.testVariables, this.testActions.get(actionIndex));
						if(result.getResultType() == ActionResultTypes.OK){
							break;
						}
					}
					if(result.getResultType() == ActionResultTypes.OK){
						Logger.logToUser( "Action result is OK", this, MessageLogTypes.HEADER);
						this.testActions.get(actionIndex).setActionResult(result);
						continue;
					}
				}
				this.testActions.get(actionIndex).setActionResult(result);
				if(result.getResultType() == ActionResultTypes.NOK){
					if(this.testActions.get(actionIndex).getFailReaction() == FailReaction.SKIP){
						Logger.logToUser( "Action result was skiped!", this, MessageLogTypes.SKIP);
					}
					else{
						break;
					}
				}	
			}
			Logger.logToUser( "------------------------------", this, MessageLogTypes.HEADER);
		}
		BufferManager.getInstance().clearBuffers();
		Logger.logToUser( "----- Test results -----", this, MessageLogTypes.HEADER);
		
		this.showTestResults();
		
		Logger.logToUser( "----------------------------", this, MessageLogTypes.HEADER);
		Logger.logToUser( "----- Test \""+this.testName+"\" was finished -----", this, MessageLogTypes.HEADER);
	}
	
	private ActionDataHanlder defineDataHandler(final DeviceSource deviceSource) {
		utils.Utils.requireNonNull(deviceSource);
		
		ActionDataHanlder dataHanlder = null;
		switch(deviceSource.getSourceType()){
			case EXTERNAL_SOURCE: 
				dataHanlder = ExternalDeviceDataHandler.getInstance();
				break;
			case SYSTEM_SOURCE:
				dataHanlder = SystemDataHandler.getInstance();
				break;
		}
		return dataHanlder;
	}

	private void showTestResults() {
		for(Action action : this.testActions){
			StringBuffer message = new StringBuffer("Command \""+action.getCommand().getCommandName()+"\". Result: "+action.getActionResult().getResultType()+".");
			if(action.getActionResult().getResultType() == ActionResultTypes.NOK || 
					action.getActionResult().getResultType() == ActionResultTypes.USER_ERROR)
			{
				message.append(" Reasone: "+action.getActionResult().getReason());
			}
			if(action.getActionResult().getResultType() != ActionResultTypes.OK){
				switch(action.getFailReaction()){
					case SKIP	:	Logger.logToUser( message.toString(), this, MessageLogTypes.SKIP);	break;
					case STOP	:	Logger.logToUser( message.toString(), this, MessageLogTypes.ERROR);	break;
				}
			}
			else{
				Logger.logToUser( message.toString(), this, MessageLogTypes.INFO);
			}
		}		
	}
	
	public String toString(){
		StringBuffer result = new StringBuffer("Test:\rVariables:\r");
		for(Variable var : this.testVariables){
			result.append("Variable:\r"+var.toString()+"\r");
		}
		result.append("\rActions:\r");
		for(Action action : this.testActions){
			result.append("Action:\r"+action.toString()+"\r");
		}
		return result.toString();
	}

	public List<Action> getTestActions() 	{	return this.testActions;	}
	public List<Variable> getTestVariables(){	return this.testVariables;	}	
	public String getTestName() 			{	return this.testName;		}
	public void setTestName(String testName){	this.testName = testName;	}

	public List<Variable> getSortedTestVariables(){
		Collections.sort(this.testVariables);
		return (List<Variable>) Collections.unmodifiableCollection(this.testVariables);
	}
	
	public Variable getVariableByName(final String variableName){
		utils.Utils.requireNonNull(variableName);
		
		for(Variable variable : this.testVariables){
			if(variable.getName().equals(variableName)){
				return variable;
			}
		}
		return null;
	}

}
