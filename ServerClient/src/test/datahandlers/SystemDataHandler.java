package test.datahandlers;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import buffers.BufferManager;
import buffers.matchers.AnswerPattern;
import common.SystemConstants;
import exceptions.TestExecutionExeption;
import exceptions.UnknownMessageTypeException;
import test.actions.Action;
import test.actions.ActionResult;
import test.actions.Parameter;
import test.actions.Variable;
import types.ActionResultTypes;
import types.IncomingMessageType;
import types.LogLevels;
import types.MessageLogTypes;
import types.SourceTypes;
import types.SystemCommandType;
import utils.Logger;
import utils.Utils;

public class SystemDataHandler implements ActionDataHanlder{
	
	private static SystemDataHandler systemDataHandler = new SystemDataHandler();
	
	public static SystemDataHandler getInstance()	{	return systemDataHandler;	}

	@Override
	public ActionResult handleActionData(final List<Variable> testVariables, final Action action) {
		utils.Utils.requireNonNull(testVariables, action);
		
		ActionResult result = null;
		switch(SystemCommandType.defineCommandType(action.getCommand().getCommandName())){
			case SLEEP: 
				result = this.executeSleepAction(Utils.getSingleParameterByName(action.getCommandParametes(), "timeout"), testVariables ); 	
				break;
			case IF: 
				result = this.executeIfAction(testVariables, action);
				break;
			case LOOP 	:
				result = this.executeLoopAction(testVariables, action);
				break;
			case ARITH_OPERATION:
				result = this.executeArithmeticOperationAction(testVariables, action);
				break;
			case ANSWERS:
				result = this.searhAnswers(testVariables, action);
				break;
			default:
				result = new ActionResult(ActionResultTypes.USER_ERROR, 
						"Unknown system command \"" + action.getCommand().getCommandName() +"\".\r" +
						"Please check \"device\" attribute.");
				break;			
		}
		return result;
	}

	private ActionResult searhAnswers(List<Variable> testVariables, Action action) {
		utils.Utils.requireNonNull(testVariables, action);
		
		ActionResult actionResult = new ActionResult(ActionResultTypes.OK, "All patterns were found");
		List<AnswerPattern> textPatterns = action.getAnswerPatternsByType(IncomingMessageType.TEXT);
		int timeout = SystemConstants.defaultTimeout;
		String stringTimeout = action.getParamValue("timeout");
		if(stringTimeout != null){
			try{
				timeout = Integer.parseInt(stringTimeout);
			}
			catch (NumberFormatException e){
				Logger.logToUser("Parameter timeout (in \"" + action.getCommand().getCommandName() +
						"\" action) has wrong value\"" + stringTimeout + 
						"\". Default timeout (" + SystemConstants.defaultTimeout +
						" miliseconds will be used)", this, MessageLogTypes.ERROR);
			}
		}
		else{
			Logger.logToUser("Default timeout (" + SystemConstants.defaultTimeout +
					" miliseconds will be used)", this, MessageLogTypes.HEADER);
		}
		Logger.logToUser("Searching for answers....(" + timeout/1000 + " seconds)", systemDataHandler, MessageLogTypes.INFO);
		if(textPatterns != null){
			actionResult = this.analizeAnswers(textPatterns, IncomingMessageType.TEXT, timeout);
		}
		if(actionResult.getResultType() == ActionResultTypes.OK){
			BufferManager.getInstance().clearBuffers();
		}
		Logger.logToUser("Search was stoped.", systemDataHandler, MessageLogTypes.INFO);
		return actionResult;
	}

	private ActionResult executeArithmeticOperationAction(final List<Variable> testVariables, final Action action) {
		utils.Utils.requireNonNull(testVariables, action);
		Logger.logToUser( action.getCommand().getCommandName()+" action started", this, MessageLogTypes.INFO);		
		String 	operator = Utils.getSingleParameterByName(action.getCommandParametes(), "operator").getValue();
		Double firstOperandDouble = this.parseDoubleFromString(Utils.getVariableByName(Utils.getSingleParameterByName(action.getCommandParametes(), "operand1").getValue(),testVariables).getValue());
		String  firstOperandStr = Utils.getVariableByName(Utils.getSingleParameterByName(action.getCommandParametes(), "operand1").getValue(),testVariables).getValue();
		Double secondOperandDouble = null;
		String 	secondOperandStr = null;
		if(Utils.getSingleParameterByName(action.getCommandParametes(), "operand2").isVariable()){
			Logger.log(LogLevels.INFO, this, "Method executeIfAction: second operand is variable");
			secondOperandDouble = this.parseDoubleFromString(Utils.getVariableByName(Utils.getSingleParameterByName(action.getCommandParametes(), "operand2").getValue(),testVariables).getValue());
			secondOperandStr = Utils.getVariableByName(Utils.getSingleParameterByName(action.getCommandParametes(), "operand2").getValue(),testVariables).getValue();
		}
		else{
			Logger.log(LogLevels.INFO, this, "Method executeIfAction: second operand is not variable");
			secondOperandDouble = this.parseDoubleFromString(Utils.getSingleParameterByName(action.getCommandParametes(), "operand2").getValue());
			secondOperandStr = Utils.getSingleParameterByName(action.getCommandParametes(), "operand2").getValue();
		}				
		Logger.log(LogLevels.INFO, this, "Method executeIfAction: firstOperandInt "+firstOperandDouble);
		Logger.log(LogLevels.INFO, this, "Method executeIfAction: firstOperandStr "+firstOperandStr);
		Logger.log(LogLevels.INFO, this, "Method executeIfAction: operator "+operator);
		Logger.log(LogLevels.INFO, this, "Method executeIfAction: secondOperandInt "+secondOperandDouble);
		Logger.log(LogLevels.INFO, this, "Method executeIfAction: secondOperandStr "+secondOperandStr);
		Logger.log(LogLevels.INFO, this, "Method executeArithmeticOperation was finished");		
		//if both operands are numbers
		if(firstOperandDouble != null && secondOperandDouble != null){			
			Logger.logToUser( "Both operands are numbers", this, MessageLogTypes.INFO);
			Logger.logToUser( "Operand1: "+firstOperandDouble, this, MessageLogTypes.INFO);
			Logger.logToUser( "Operator: "+operator, this, MessageLogTypes.INFO);
			Logger.logToUser( "Operand2: "+secondOperandDouble, this, MessageLogTypes.INFO);			
			switch(operator){
				case "+"	: 
				case "+="	: 
					Utils.getVariableByName(Utils.getSingleParameterByName(action.getCommandParametes(), "operand1").getValue(),testVariables).setValue(firstOperandDouble + secondOperandDouble);
					break;
				case "-" 	: 
				case "-="	: 
					Utils.getVariableByName(Utils.getSingleParameterByName(action.getCommandParametes(), "operand1").getValue(),testVariables).setValue(firstOperandDouble - secondOperandDouble);
					break;
				case "*"	: 
				case "*="	: 
					Utils.getVariableByName(Utils.getSingleParameterByName(action.getCommandParametes(), "operand1").getValue(),testVariables).setValue(firstOperandDouble * secondOperandDouble);
					break;
				case "/"	: 
				case "/="	: 
					Utils.getVariableByName(Utils.getSingleParameterByName(action.getCommandParametes(), "operand1").getValue(),testVariables).setValue(firstOperandDouble / secondOperandDouble);
					break;
				case "="	: 
					Utils.getVariableByName(Utils.getSingleParameterByName(action.getCommandParametes(), "operand1").getValue(),testVariables).setValue(secondOperandDouble);
					break;
				default		:
					Logger.log(LogLevels.ERROR, this, "Both operands are numbers. Unknown operator" + operator +" "+ ActionResultTypes.USER_ERROR);
					return new ActionResult(ActionResultTypes.USER_ERROR, "Both operands are numbers. Unknown operator \"" + operator + "\"");
			}
		}
		//if both operands are strings
		else if(firstOperandDouble == null && secondOperandDouble == null){			
			Logger.logToUser( "Both operands are strings", this, MessageLogTypes.INFO);
			Logger.logToUser( "Operand1: "+firstOperandStr, this, MessageLogTypes.INFO);
			Logger.logToUser( "Operator: "+operator, this, MessageLogTypes.INFO);
			Logger.logToUser( "Operand2: "+secondOperandStr, this, MessageLogTypes.INFO);			
			switch(operator){
				case "+"	: 
				case "+="	: 
					Utils.getVariableByName(Utils.getSingleParameterByName(action.getCommandParametes(), "operand1").getValue(),testVariables).setValue(firstOperandStr + secondOperandStr);
					break;
				case "="	: 
					Utils.getVariableByName(Utils.getSingleParameterByName(action.getCommandParametes(), "operand1").getValue(),testVariables).setValue(secondOperandStr);
					break;
				default		:
					Logger.log(LogLevels.ERROR, this, "Both operands are strings. Unknown operator \"" + operator + "\" "+ ActionResultTypes.USER_ERROR);
					return new ActionResult(ActionResultTypes.USER_ERROR, "Both operands are strings. Unknown operator \"" + operator + "\"");
			}
		}
		//if second operand is string and first operand is number
		else{
			Logger.logToUser( "One of operands is string and other - number", this, MessageLogTypes.INFO);
			Logger.logToUser( "Operand1: "+firstOperandStr, this, MessageLogTypes.INFO);
			Logger.logToUser( "Operator: "+operator, this, MessageLogTypes.INFO);
			Logger.logToUser( "Operand2: "+secondOperandStr, this, MessageLogTypes.INFO);			
			switch(operator){
				case "+"	: 
				case "+="	: 
					Utils.getVariableByName(Utils.getSingleParameterByName(action.getCommandParametes(), "operand1").getValue(),testVariables).setValue(firstOperandStr + secondOperandStr);
					break;
				case "="	: 
					Utils.getVariableByName(Utils.getSingleParameterByName(action.getCommandParametes(), "operand1").getValue(),testVariables).setValue(secondOperandStr == null ? secondOperandDouble.toString() : secondOperandStr );
					break;
				default		:
					Logger.log(LogLevels.ERROR, this, "One of operands is string and other - number. Unknown operator \" "+ ActionResultTypes.USER_ERROR);
					return new ActionResult(ActionResultTypes.USER_ERROR, "One of operands is string and other - number. Unknown operator \"" + operator + "\"");
			}
		}
		final ActionResult result =  new ActionResult(ActionResultTypes.OK, null);		
		Logger.log(LogLevels.INFO, this, action.getCommand().getCommandName()+" was finished with result: "+result.getResultType());
		Logger.logToUser( action.getCommand().getCommandName()+" action finished", this, MessageLogTypes.INFO);
		return result;
	}

	private ActionResult executeLoopAction(final List<Variable> testVariables, final Action action) {
		utils.Utils.requireNonNull(testVariables, action);
		
		Logger.logToUser( action.getCommand().getCommandName()+" action started", this, MessageLogTypes.INFO);		
		ActionResult result = new ActionResult(ActionResultTypes.OK, null);
		try {
			while(!this.executeCondition(testVariables, action)){
				this.executeActionsBlock(action.getLoopBodyBlock(), testVariables);
			}
		} catch (TestExecutionExeption e) {
			Logger.log(LogLevels.EXCEPTION, this, "Method executeLoopAction, " + e.getMessage());
			result = new ActionResult(ActionResultTypes.USER_ERROR, e.getMessage());
		}
		Logger.logToUser( action.getCommand().getCommandName()+" action finished", this, MessageLogTypes.INFO);
		Logger.log(LogLevels.INFO, this, "Method executeAction, command was finished with result: "+ result.getResultType());
		return result;
	}

	private ActionResult executeIfAction(final List<Variable> testVariables, final Action action) {
		utils.Utils.requireNonNull(testVariables, action);
		
		Logger.logToUser( action.getCommand().getCommandName()+" action started", this, MessageLogTypes.INFO);		
		ActionResult result = new ActionResult(ActionResultTypes.OK, null);
		try {
			this.executeCondition(testVariables, action);
		} catch (TestExecutionExeption e) {
			Logger.log(LogLevels.EXCEPTION, this, "Method executeIfAction");
			Logger.log(LogLevels.EXCEPTION, this, e.getLocalizedMessage());			
			result = new ActionResult(ActionResultTypes.USER_ERROR, e.getMessage());
		}
		Logger.logToUser( action.getCommand().getCommandName()+" action finished", this, MessageLogTypes.INFO);
		Logger.log(LogLevels.TRACE, this, action.getCommand().getCommandName()+" action finished with result: "+ result.getResultType());
		if(result.getResultType() != ActionResultTypes.OK){
			Logger.log(LogLevels.TRACE, this, "Reason: "+ result.getReason());
		}
		return result;
	}

	private ActionResult executeSleepAction(final Parameter time, final List<Variable> testVariables) {
		utils.Utils.requireNonNull(time, testVariables);
		int timeout = 0;
		ActionResult result =  new ActionResult(ActionResultTypes.OK, null);
		try{
			if(time.isVariable()){
				String timeValue = Utils.getVariableByName(time.getValue(), testVariables).getValue();
				timeout = Integer.parseInt(timeValue);
				Logger.log(LogLevels.TRACE, this, "Method executeSleepAction: sleep "+timeout);
			}
			else{
				timeout = Integer.parseInt(time.getValue());
				Logger.log(LogLevels.TRACE, this, "Method executeSleepAction: sleep "+timeout);
			}
		}
		catch (NumberFormatException ex){
			Logger.log(LogLevels.EXCEPTION, this, "Command \"sleep\". Value of parameter \"timeout\" is not integer number");
			Logger.log(LogLevels.EXCEPTION, this, ex.getLocalizedMessage());
			result = new ActionResult(ActionResultTypes.USER_ERROR, "Command \"sleep\". Value of parameter \"timeout\" is not integer number");
		}
		if(result.getResultType() == ActionResultTypes.OK){
			Logger.logToUser( "SLEEP action started", this, MessageLogTypes.INFO);
			Logger.logToUser( "Timeout: " + timeout, this, MessageLogTypes.INFO);
			Logger.logToUser( "SLEEP action finished", this, MessageLogTypes.INFO);			
			try {
				Thread.sleep(timeout);
			} catch (InterruptedException e) {
				Logger.log(LogLevels.EXCEPTION, this,  "Command \"sleep\". Interrupted exception was happened");
				result =  new ActionResult(ActionResultTypes.SYSTEM_ERROR, "Command \"sleep\". Interrupted exception was happened");
			}
		}		
		if(result.getResultType() != ActionResultTypes.OK){
			Logger.log(LogLevels.TRACE, this, "Reason: "+ result.getReason());
		}
		return result;
	}

	private void executeActionsBlock(final List<Action> testActions, final List<Variable> testVariables) {
		utils.Utils.requireNonNull(testVariables);
		
		if(testActions != null){
			for(Action action : testActions){
				Logger.log(LogLevels.TRACE, this, "action command: " + action.getCommand().getCommandName() + "   " +action.getParamValue("timeout"));
				ActionDataHanlder handler = this.defineDataHandler(action.getCommand().getDeviceSource().getSourceType());
				handler.handleActionData(testVariables, action);				
			}
		}
	}
	
	private ActionDataHanlder defineDataHandler(final SourceTypes sourceType) {
		utils.Utils.requireNonNull(sourceType);
		
		ActionDataHanlder result = null;
		switch(sourceType){
			case EXTERNAL_SOURCE:
				result = ExternalDeviceDataHandler.getInstance();
				break;
			case SYSTEM_SOURCE:
				result = this;
				break;
		}
		return result;
	}

	private boolean executeCondition(final List<Variable> testVariables, final Action action) throws TestExecutionExeption{
		utils.Utils.requireNonNull(testVariables, action);
		
		boolean loopResult = false; // used for "loop" action and does not have meaning for "if" action		
		String 	operator = Utils.getSingleParameterByName(action.getCommandParametes(), "operator").getValue();
		Double firstOperandDouble = this.parseDoubleFromString(Utils.getVariableByName(Utils.getSingleParameterByName(action.getCommandParametes(), "operand1").getValue(),testVariables).getValue());
		String  firstOperandStr = Utils.getVariableByName(Utils.getSingleParameterByName(action.getCommandParametes(), "operand1").getValue(),testVariables).getValue();
		Double secondOperandDouble = null;
		String 	secondOperandStr = null;
		if(Utils.getSingleParameterByName(action.getCommandParametes(), "operand2").isVariable()){
			Logger.log(LogLevels.INFO, this, "Method executeIfAction: second operand is variable");
			secondOperandDouble = this.parseDoubleFromString(Utils.getVariableByName(Utils.getSingleParameterByName(action.getCommandParametes(), "operand2").getValue(),testVariables).getValue());
			secondOperandStr = Utils.getVariableByName(Utils.getSingleParameterByName(action.getCommandParametes(), "operand2").getValue(),testVariables).getValue();
		}
		else{
			Logger.log(LogLevels.INFO, this, "Method executeIfAction: second operand is not variable");
			secondOperandDouble = this.parseDoubleFromString(Utils.getSingleParameterByName(action.getCommandParametes(), "operand2").getValue());
			secondOperandStr = Utils.getSingleParameterByName(action.getCommandParametes(), "operand2").getValue();
		}				
		Logger.log(LogLevels.INFO, this, "Method executeIfAction: firstOperandInt "+firstOperandDouble);
		Logger.log(LogLevels.INFO, this, "Method executeIfAction: firstOperandStr "+firstOperandStr);
		Logger.log(LogLevels.INFO, this, "Method executeIfAction: operator "+operator);
		Logger.log(LogLevels.INFO, this, "Method executeIfAction: secondOperandInt "+secondOperandDouble);
		Logger.log(LogLevels.INFO, this, "Method executeIfAction: secondOperandStr "+secondOperandStr);		
		//if both operands are numbers
		if(firstOperandDouble != null && secondOperandDouble != null){
			Logger.logToUser( "Both operands are numbers", this, MessageLogTypes.INFO);
			Logger.logToUser( "Operand1: "+firstOperandDouble, this, MessageLogTypes.INFO);
			Logger.logToUser( "Operator: "+operator, this, MessageLogTypes.INFO);
			Logger.logToUser( "Operand2: "+secondOperandDouble, this, MessageLogTypes.INFO);
			Logger.log(LogLevels.INFO, this, "Method executeIfAction, case when both oprands are numbers");
			switch(operator){
				case ">" :
					if(firstOperandDouble.compareTo(secondOperandDouble) > 0){
						Logger.log(LogLevels.INFO, this, "Method executeIfAction, operator \">\": then block ");
						Logger.logToUser( "Execution \"then\" block was started", this, MessageLogTypes.INFO);
						this.executeActionsBlock(action.getThenBlock(),testVariables);
						Logger.logToUser( "Execution \"then\" block was finished", this, MessageLogTypes.INFO);
						loopResult = true;
					}
					else{
						Logger.log(LogLevels.INFO, this, "Method executeIfAction, operator \">\": else block ");
						Logger.logToUser( "Execution \"else\" block was started", this, MessageLogTypes.INFO);
						this.executeActionsBlock(action.getElseBlock(), testVariables);
						Logger.logToUser( "Execution \"else\" block was finished", this, MessageLogTypes.INFO);
						loopResult = false;
					}
					break;
				case "<" :
					if(firstOperandDouble.compareTo(secondOperandDouble) < 0){
						Logger.log(LogLevels.INFO, this, "Method executeIfAction, operator \"<\": then block ");
						Logger.logToUser( "Execution \"then\" block was started", this, MessageLogTypes.INFO);
						this.executeActionsBlock(action.getThenBlock(), testVariables);
						Logger.logToUser( "Execution \"then\" block was finished", this, MessageLogTypes.INFO);
						loopResult = true;
					}
					else{
						Logger.log(LogLevels.INFO, this, "Method executeIfAction, operator \"<\": else block ");
						Logger.logToUser( "Execution \"else\" block was started", this, MessageLogTypes.INFO);
						this.executeActionsBlock(action.getElseBlock(),testVariables);
						Logger.logToUser( "Execution \"else\" block was finished", this, MessageLogTypes.INFO);
						loopResult = false;
					}
					break;
				case ">=" : 
					if(firstOperandDouble.compareTo(secondOperandDouble) >= 0){
						Logger.log(LogLevels.INFO, this, "Method executeIfAction, operator \">=\": then block ");
						Logger.logToUser( "Execution \"then\" block was started", this, MessageLogTypes.INFO);
						this.executeActionsBlock(action.getThenBlock(), testVariables);
						Logger.logToUser( "Execution \"then\" block was finished", this, MessageLogTypes.INFO);
						loopResult = true;
					}
					else{
						Logger.log(LogLevels.INFO, this, "Method executeIfAction, operator \">=\": else block ");
						Logger.logToUser( "Execution \"else\" block was started", this, MessageLogTypes.INFO);
						this.executeActionsBlock(action.getElseBlock(), testVariables);
						Logger.logToUser( "Execution \"else\" block was finished", this, MessageLogTypes.INFO);
						loopResult = false;
					}
					break;
				case "<=" : 
					if(firstOperandDouble.compareTo(secondOperandDouble) <= 0 ){
						Logger.log(LogLevels.INFO, this, "Method executeIfAction, operator \"<=\": then block ");
						Logger.logToUser( "Execution \"then\" block was started", this, MessageLogTypes.INFO);
						this.executeActionsBlock(action.getThenBlock(), testVariables);
						Logger.logToUser( "Execution \"then\" block was finished", this, MessageLogTypes.INFO);
						loopResult = true;
					}
					else{
						Logger.log(LogLevels.INFO, this, "Method executeIfAction, operator \"<=\": else block ");
						Logger.logToUser( "Execution \"else\" block was started", this, MessageLogTypes.INFO);
						this.executeActionsBlock(action.getElseBlock(), testVariables);
						Logger.logToUser( "Execution \"else\" block was finished", this, MessageLogTypes.INFO);
						loopResult = false;
					}
					break;
				case "==" :
					if(firstOperandDouble.compareTo(secondOperandDouble) == 0){
						Logger.log(LogLevels.INFO, this, "Method executeIfAction, operator \"==\": then block ");
						Logger.logToUser( "Execution \"then\" block was started", this, MessageLogTypes.INFO);
						this.executeActionsBlock(action.getThenBlock(), testVariables);
						Logger.logToUser( "Execution \"then\" block was finished", this, MessageLogTypes.INFO);
						loopResult = true;
					}
					else{
						Logger.log(LogLevels.INFO, this, "Method executeIfAction, operator \"==\": else block ");
						Logger.logToUser( "Execution \"else\" block was started", this, MessageLogTypes.INFO);
						this.executeActionsBlock(action.getElseBlock(), testVariables);
						Logger.logToUser( "Execution \"else\" block was finished", this, MessageLogTypes.INFO);
						loopResult = false;
					}
					break;
				case "!=" :
					if(firstOperandDouble.compareTo(secondOperandDouble) != 0){
						Logger.log(LogLevels.INFO, this, "Method executeIfAction, operator \"!=\": then block ");
						Logger.logToUser( "Execution \"then\" block was started", this, MessageLogTypes.INFO);
						this.executeActionsBlock(action.getThenBlock(), testVariables);
						Logger.logToUser( "Execution \"then\" block was finished", this, MessageLogTypes.INFO);
						loopResult = true;
					}
					else{
						Logger.log(LogLevels.INFO, this, "Method executeIfAction, operator \"==\": else block ");
						Logger.logToUser( "Execution \"else\" block was started", this, MessageLogTypes.INFO);
						this.executeActionsBlock(action.getElseBlock(), testVariables);
						Logger.logToUser( "Execution \"else\" block was finished", this, MessageLogTypes.INFO);
						loopResult = false;
					}
					break;
			}
		}		
		//if both operands are strings
		else if(firstOperandDouble == null && secondOperandDouble == null){
			Logger.logToUser( "Both operands are strings", this, MessageLogTypes.INFO);
			Logger.logToUser( "Operand1: "+firstOperandStr, this, MessageLogTypes.INFO);
			Logger.logToUser( "Operator: "+operator, this, MessageLogTypes.INFO);
			Logger.logToUser( "Operand2: "+secondOperandStr, this, MessageLogTypes.INFO);
			switch(operator){
				case "==" : 
					{
						if(firstOperandStr.equals(secondOperandStr)){
							Logger.logToUser( "Execution \"then\" block was started", this, MessageLogTypes.INFO);
							this.executeActionsBlock(action.getThenBlock(), testVariables);
							Logger.logToUser( "Execution \"then\" block was finished", this, MessageLogTypes.INFO);
							loopResult = true;
						}
						else{
							Logger.logToUser( "Execution \"else\" block was started", this, MessageLogTypes.INFO);
							this.executeActionsBlock(action.getElseBlock(), testVariables);
							Logger.logToUser( "Execution \"else\" block was finished", this, MessageLogTypes.INFO);
							loopResult = false;
						}
						break;
					}
				case "!=" : 
					{
						if(!firstOperandStr.equals(secondOperandStr)){
							Logger.logToUser( "Execution \"then\" block was started", this, MessageLogTypes.INFO);
							this.executeActionsBlock(action.getThenBlock(), testVariables);
							Logger.logToUser( "Execution \"then\" block was finished", this, MessageLogTypes.INFO);
							loopResult = true;
						}
						else{
							Logger.logToUser( "Execution \"else\" block was started", this, MessageLogTypes.INFO);
							this.executeActionsBlock(action.getElseBlock(), testVariables);
							Logger.logToUser( "Execution \"else\" block was finished", this, MessageLogTypes.INFO);
							loopResult = false;
						}
						break;
					}
				default : {
					Logger.log(LogLevels.EXCEPTION, this, "Only operators \"==\" and \"!=\" are available for work with strings");
					throw new TestExecutionExeption("Only operators \"==\" and \"!=\" are available for work with strings");
				}
			}
		}
		//if one of operands is string 
		//Can't compare string and number
		else{
			Logger.log(LogLevels.EXCEPTION, this, "Can't compare string and number");
			throw new TestExecutionExeption("Can't compare string and number");
		}
		return loopResult;
	}
	
	private Double parseDoubleFromString (final String value){
		utils.Utils.requireNonNull(value);
		
		Double result = null;
		try {
			result =  Double.parseDouble(value);
		}
		catch(NumberFormatException ex){}
		return result;
	}

	private ActionResult analizeAnswers(final List<AnswerPattern> answerPatterns, final IncomingMessageType type, int timeout){
		utils.Utils.requireNonNull(answerPatterns, type);
		
		ActionResult actionResult = new ActionResult(ActionResultTypes.OK, "All pattern were found");
		Map<AnswerPattern, Object> textAnswers = null;
		try {
			textAnswers = BufferManager.getInstance().findAnswersInBuffer(answerPatterns, type, timeout);
		} catch (UnknownMessageTypeException e) {
			Logger.logToUser("System can not find answer because there is unknown answer type: " + type.toString(), this, MessageLogTypes.ERROR);
			Logger.logToUser("Please check you test case or anayze system logs", this, MessageLogTypes.ERROR);
			Logger.log(LogLevels.EXCEPTION, this, e.getMessage());
		}
		if(textAnswers != null){
			for(Entry<AnswerPattern, Object> entry : textAnswers.entrySet()){
				if(entry.getValue() == null){
					Logger.logToUser("Answer message for pattern \"" + 
								entry.getKey().getPattern().toString() +
								"\" from device source \"" + entry.getKey().getDeviceSourceId() +
								"\" was not found", this, MessageLogTypes.ERROR);
					if(actionResult.getResultType() == ActionResultTypes.OK){
						actionResult = new ActionResult(ActionResultTypes.NOK, "Not all patterns were found");
					}
				}
				else{
					Logger.logToUser("Answer message for pattern \"" + 
							entry.getKey().getPattern().toString() +
							"\" from device source \"" + entry.getKey().getDeviceSourceId() +
							"\" was found: " + entry.getValue(), this, MessageLogTypes.INFO);
				}
				Logger.logToUser("Spent time: " + entry.getKey().getSpentTime()
						+ " " + entry.getKey().getTimeUnit(), this, MessageLogTypes.INFO);
			}
		}
		else{
			actionResult = new ActionResult(ActionResultTypes.NOK, "System did not find answer due to unknown message answer type");
		}
		return actionResult;
	}
}
