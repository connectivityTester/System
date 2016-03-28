package xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.DataConversionException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import exceptions.ContentException;
import exceptions.XMLException;
import test.Test;
import test.actions.Action;
import test.actions.Command;
import test.actions.Parameter;
import test.actions.Variable;
import types.CommandTypes;
import types.FailReaction;
import types.IncomingMessageType;
import types.LogLevels;
import types.SystemCommandType;
import utils.Logger;

public class TestReader extends AbstractReader{

	public TestReader(final String shemaPath) {
		super(shemaPath);
	}

	protected Context readContext(final String filePath) throws FileNotFoundException, Exception {
		utils.Utils.requireNonNull(filePath);
		
		File file = new File(filePath);
		Document xmlDoc = null;
		if (file.exists() && file.isFile()) {
			xmlDoc = new SAXBuilder().build(new FileInputStream(file));
		}
		else{
			Logger.log(LogLevels.EXCEPTION, this, "File "+ filePath +" was not found or it is directory");
			throw new FileNotFoundException("File "+ filePath +" was not found or it is directory");
		}
		List<Variable> testVariables = new ArrayList<Variable>();
		Element variablesElem = xmlDoc.getRootElement().getChild("variables");
		if(variablesElem != null){
			List<Element> variablesList = variablesElem.getChildren();
			if(variablesList != null){
				for(Element varElem : variablesList){
					if(varElem.getName().equals("variable")){
						testVariables.add(parseElemToVariable(varElem));
					}
				}
			}
		}
		List<Element> actionsElem = xmlDoc.getRootElement().getChild("actions").getChildren();
		List<Action> testActions = new ArrayList<Action>();
		if(actionsElem != null){
			for(Element action : actionsElem){
				testActions.add(parseElemToAction(action));
			}
		}
		final Test test = new Test(testVariables, testActions);
		return test;
	}

	@Override
	protected Test validateReadContext(final Context context) throws ContentException {
		utils.Utils.requireNonNull(context);
		final Test test = (Test) context;
		for(Variable variable : test.getTestVariables()){
			if(variable.getName().isEmpty()){
				throw new ContentException("Variable name can't be empty. Please correct and rerun again");
			}
		}
		for(Action action : test.getTestActions()){
			this.validateAction(action);			
		}			
		return test;
	}

	private Variable parseElemToVariable(final Element varElem) throws XMLException {
		utils.Utils.requireNonNull(varElem);
		
		Attribute nameAtrib = varElem.getAttribute("name");
		if(nameAtrib == null){
			Logger.log(LogLevels.EXCEPTION, this,"Variable has to have \"name\" attribute");
			throw new XMLException("Variable has to have \"name\" attribute");
		}
		Attribute valueAtrib = varElem.getAttribute("value");
		if(valueAtrib == null){
			return new Variable(nameAtrib.getValue(), null);
		}
		return new Variable(nameAtrib.getValue().toString(), valueAtrib.getValue().toString());
	}
	
	private Action parseElemToAction(final Element actionElem) throws XMLException {
		utils.Utils.requireNonNull(actionElem);
		
		Element commandElem = actionElem.getChild("command");
		if(commandElem == null){
			Logger.log(LogLevels.EXCEPTION, this,"Action should contains \"command\" child\r"+actionElem.toString());
			throw new XMLException("Action should contains \"command\" child\r"+actionElem.toString());
		}
		Command command = null;
		Attribute deviceAttr = commandElem.getAttribute("device");
		if(deviceAttr != null){
			try {
				command = new Command(commandElem.getText(), deviceAttr.getIntValue());
			} catch (DataConversionException e) {
				Logger.log(LogLevels.EXCEPTION, this,"Command should contains integer device id\r" + actionElem.toString());
				throw new XMLException("Command should contains integer device id\r");
			}
		}
		else{
			command = new Command(commandElem.getText(), -1);
		}
		List<Parameter> commandParametes = null;
		Element paramsElem = actionElem.getChild("params");
		if(paramsElem != null){
			List<Element> paramsList = paramsElem.getChildren();
			if(paramsList != null){
				commandParametes = new ArrayList<Parameter>();
				for(Element paramElem : paramsList){
					if(paramElem.getName().equals("param")){
						commandParametes.add(parseElemToParameter(paramElem));
					}
				}
			}
		}
		int retry = 1;
		FailReaction reaction = FailReaction.STOP;
		Element failElem = actionElem.getChild("fail");
		if(failElem != null){
			reaction = FailReaction.defineFailReaction(failElem.getText());
			Attribute retryElem = failElem.getAttribute("retry");
			if(retryElem != null){
				try {
					retry = retryElem.getIntValue();
				} catch (DataConversionException e) {
					throw new XMLException("Attribute \"retry\" contains of not integer value");
				}
			}
		}
		List<Action> thenBlock = null;
		List<Action> elseBlock = null;
		List<Action> loopBodyBlock = null;
		if(commandElem.getText().equals("if")){
			Element thenBodyElem = actionElem.getChild("then");
			if(thenBodyElem != null){
				List<Element> thenActions = thenBodyElem.getChildren();
				if(thenActions != null){
					thenBlock = new ArrayList<Action>();
					for(Element action : thenActions){
						if(action.getName().equals("action")){
							thenBlock.add(parseElemToAction(action));
						}
					}
				}
			}
			else{
				Logger.log(LogLevels.EXCEPTION, this, "Action \"if\" should contains \"then\" block\r");
				throw new XMLException("Action \"if\" should contains \"then\" block\r");
			}
			Element elseBodyElem = actionElem.getChild("else");
			if(elseBodyElem != null){
				List<Element> elseActions = elseBodyElem.getChildren();
				if(elseActions != null){
					elseBlock = new ArrayList<Action>();
					for(Element action : elseActions){
						if(action.getName().equals("action")){
							elseBlock.add(parseElemToAction(action));
						}
					}
				}
			}
		}
		if(commandElem.getText().equals("loop")){
			Element loopBodyElem = actionElem.getChild("loop_body");
			if(loopBodyElem != null){
				loopBodyBlock = new ArrayList<Action>();
				List<Element> loopActions = loopBodyElem.getChildren();
				if(loopActions != null){
					for(Element action : loopActions){
						if(action.getName().equals("action")){
							loopBodyBlock.add(parseElemToAction(action));
						}
					}
				}
			}
			else{
				Logger.log(LogLevels.EXCEPTION, this, "Action \"loop\" should contains \"loop_body\" block\r");
				throw new XMLException("Action \"loop\" should contains \"loop_body\" block\r");
			}
		}
		return new Action (command, commandParametes, thenBlock, elseBlock, loopBodyBlock, reaction, retry);
	}

	private Parameter parseElemToParameter(final Element paramElem) throws XMLException {
		utils.Utils.requireNonNull(paramElem);
		
		String name = null;
		String device = null;
		String value = null;
		IncomingMessageType type = IncomingMessageType.UNKNOWN_TYPE;
		Attribute nameAttrib = paramElem.getAttribute("name");
		if(nameAttrib != null){
			name = nameAttrib.getValue();
		}
		Attribute deviceAttrib = paramElem.getAttribute("device");
		if(deviceAttrib != null){
			device = deviceAttrib.getValue();
		}
		else if(name == null){
			throw new XMLException("Parameter has to have \"name\" or \"device\" attribute");
		}
		Attribute valueAtrib = paramElem.getAttribute("value");
		if(valueAtrib == null){
			Logger.log(LogLevels.EXCEPTION, this, "Parameter has to have \"value\" attribute");
			throw new XMLException("Parameter has to have \"value\" attribute");
		}
		else{
			value = valueAtrib.getValue();
		}
		Attribute typeAttrib = paramElem.getAttribute("type");
		if(typeAttrib != null){
			type = IncomingMessageType.defineIncomingMessageType(typeAttrib.getValue());
			if(type == IncomingMessageType.UNKNOWN_TYPE){
				throw new XMLException("Unknown message type \"" +typeAttrib.getValue()+ "\"");
			}
		}
		else{
			type = IncomingMessageType.TEXT;
		}
		return new Parameter(name, device, type, value);
	}
	
	private void validateAction(final Action action) throws ContentException {
		utils.Utils.requireNonNull(action);
		
		if(action.getCommandType() == CommandTypes.SYSTEM_COMMAND){
			switch (SystemCommandType.defineCommandType(action.getCommand().getCommandName())) {
			case ANSWERS:
				this.checkAnswersCommand(action);
				break;
			case ARITH_OPERATION:
				this.checkArithmeticOperation(action, "arithmetic_operation");
				break;
			case IF:
				this.checkArithmeticOperation(action, "if");
				break;
			case LOOP:
				this.checkArithmeticOperation(action, "loop");
				if(action.getLoopBodyBlock().size() < 1){
					throw new ContentException("Loop body should contain at least one action");
				}
				for(Action loopAction : action.getLoopBodyBlock()){
					this.validateAction(loopAction);
				}
				break;
			case SLEEP:
				if(action.getParamValue("timeout") == null){
					throw new ContentException("Command \"sleep\" should contain \"timeout\" parameter");
				}
				break;
			default: break;
			}
		}
	}

	private void checkAnswersCommand(final Action action) throws ContentException {
		utils.Utils.requireNonNull(action);
		
		if(action.getParamValue("timeout") == null){
			throw new ContentException("Command \"answer\" should contain \"timeout\" parameter");
		}
		for(Parameter parameter : action.getCommandParametes()){
			if(parameter.getMessageType() == IncomingMessageType.UNKNOWN_TYPE){
				throw new ContentException("Unknown message type in command \"answers\"");
			}
			if(parameter.getValue().isEmpty()){
				throw new ContentException("Parameter value in command \"answers\" can not be empty");
			}
			if(!SystemConfig.getInstance().isDeviceSourceRegistered(parameter.getDeviceSource().getId())){
				throw new ContentException("Command \"answers\" refers on unregistered device source");
			}
		}
	}

	private void checkArithmeticOperation(final Action action, final String operation) throws ContentException {
		utils.Utils.requireNonNull(action, operation);
		
		if(action.getParamValue("operand1") == null){
			throw new ContentException("Command \"" + operation + "\" should contain \"operand1\" parameter");
		}
		if(action.getParamValue("operand2") == null){
			throw new ContentException("Command \"" + operation + "\" should contain \"operand2\" parameter");
		}
		if(action.getParamValue("operator") == null){
			throw new ContentException("Command \"" + operation + "\" should contain \"operator\" parameter");
		}
		for(Parameter parameter : action.getCommandParametes()){
			if(parameter.getValue().isEmpty()){
				throw new ContentException("Command \"" + operation + "\" can't contain empty parameter value");
			}
		}	
	}


}
