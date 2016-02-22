package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.DataConversionException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import exceptions.TestContextException;
import exceptions.XMLException;
import test.Action;
import test.Command;
import test.Parameter;
import test.Test;
import test.Variable;
import types.FailReaction;
import types.IncomingMessageType;
import types.LogLevels;
import utils.Logger;

public class ConfigXMLReader {

	private final static ConfigXMLReader configXMLReader = new ConfigXMLReader();

	public static Test readTest(String testFilePath) throws FileNotFoundException, JDOMException, IOException, Exception {		
		File file = new File(testFilePath);
		Document xmlDoc = null;
		if (file.exists() && file.isFile()) {
			xmlDoc = new SAXBuilder().build(new FileInputStream(file));
		}
		else{
			Logger.log(LogLevels.EXCEPTION, configXMLReader, "File "+testFilePath+" was not found or it is directory");
			throw new FileNotFoundException("File "+testFilePath+" was not found or it is directory");
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
		Test test = new Test(testVariables, testActions);
		return test;
	}

	public Test readLibrary(String libraryFilePath) throws JDOMException, IOException, Exception {
		return readTest(libraryFilePath);
	}

	public static SystemConfig readConfiguration(String configurationFilePath)	throws FileNotFoundException, JDOMException, IOException {
		File file = new File(configurationFilePath);
		Document xmlDoc = null;
		if (file.exists() && file.isFile()) {
			xmlDoc = new SAXBuilder().build(new FileInputStream(file));
		}
		else{
			Logger.log(LogLevels.EXCEPTION, configXMLReader, "File "+configurationFilePath+" was not found or it is directory");
			throw new FileNotFoundException("File "+configurationFilePath+" was not found or it is directory");
		}
		List<Element> elements = xmlDoc.getRootElement().getChildren();
		String testRootDirectory = null;
		int messagePort = 0;
		List<DeviceSource> deviceSources = new ArrayList<>();
		for(Element element : elements){
			switch(element.getName()){
				case "testRootDirectory": 
					testRootDirectory = element.getAttributeValue("value");
					break;
				case "messagePort":
					messagePort = Integer.parseInt(element.getValue());
					break;
				case "device_sources":
					for(Element deviceSourceElement : element.getChildren()){
						String name = null;
						int id = 0;
						boolean autoStartUp = false;
						String address = null;
						int port = 0;
						List<String> startParameters = null;
						List<String> shutdownParameters = null;
						for(Element deviceSourceItem : deviceSourceElement.getChildren()){
							switch(deviceSourceItem.getName()){
							case "name":
								name = deviceSourceItem.getValue();
								break;
							case "id":
								id = Integer.parseInt(deviceSourceItem.getValue());
								autoStartUp = Boolean.parseBoolean(deviceSourceItem.getAttributeValue("autostart"));
								break;
							case "address":
								address = deviceSourceItem.getValue();
								break;
							case "port":
								port = Integer.parseInt(deviceSourceItem.getValue());
								break;
							case "start_up_parameters":
								startParameters = new ArrayList<String>(deviceSourceItem.getChildren().size());
								for(Element parameterItem: deviceSourceItem.getChildren()){
									startParameters.add(parameterItem.getValue());
								}
								break;
							case "kill_parameters":
								shutdownParameters = new ArrayList<String>(deviceSourceItem.getChildren().size());
								for(Element parameterItem: deviceSourceItem.getChildren()){
									shutdownParameters.add(parameterItem.getValue());
								}
								break;
							}
						}
						deviceSources.add(new DeviceSource(name, id, autoStartUp, address, port, startParameters, shutdownParameters));
					}
					break;
			}
		}
		return new SystemConfig(testRootDirectory, messagePort, deviceSources);
	}
	
	private static Variable parseElemToVariable(Element varElem) throws XMLException {
		Attribute nameAtrib = varElem.getAttribute("name");
		if(nameAtrib == null){
			Logger.log(LogLevels.EXCEPTION, configXMLReader,"Variable has to have \"name\" attribute");
			throw new XMLException("Variable has to have \"name\" attribute");
		}
		Attribute valueAtrib = varElem.getAttribute("value");
		if(valueAtrib == null){
			return new Variable(nameAtrib.getValue(), null);
		}
		return new Variable(nameAtrib.getValue().toString(), valueAtrib.getValue().toString());
	}

	private static Action parseElemToAction (Element actionElem) throws XMLException, TestContextException{		
		Element commandElem = actionElem.getChild("command");
		if(commandElem == null){
			Logger.log(LogLevels.EXCEPTION, configXMLReader,"Action should contains \"command\" child\r"+actionElem.toString());
			throw new XMLException("Action should contains \"command\" child\r"+actionElem.toString());
		}
		Command command = null;
		Attribute deviceAttr = commandElem.getAttribute("device");
		if(deviceAttr != null){
			try {
				command = new Command(commandElem.getText(), deviceAttr.getIntValue());
			} catch (DataConversionException e) {
				e.printStackTrace();
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
				Logger.log(LogLevels.EXCEPTION, configXMLReader,"Action \"if\" should contains \"then\" block\r");
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
				Logger.log(LogLevels.EXCEPTION, configXMLReader, "Action \"loop\" should contains \"loop_body\" block\r");
				throw new XMLException("Action \"loop\" should contains \"loop_body\" block\r");
			}
		}
		Element typeElem = actionElem.getChild("type");
		String commandType = null;
		if(typeElem != null){
			commandType = typeElem.getValue();
		}
		return new Action (command, commandType, commandParametes,thenBlock,elseBlock,loopBodyBlock, reaction, retry);
	}

	private static Parameter parseElemToParameter(Element paramElem) throws XMLException {
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
			Logger.log(LogLevels.EXCEPTION, configXMLReader, "Parameter has to have \"value\" attribute");
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
}
