package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import types.LogLevels;
import utils.Logger;

public class XMLReader{
	
	private final static XMLReader reader = new XMLReader();
	
	public static Configuration readConfiguration(String configurationFilePath) throws FileNotFoundException, NumberFormatException, JDOMException, IOException {
		Logger.log(LogLevels.INFO, reader, "Function readConfiguration was started");
		
		File file = new File(configurationFilePath);
		Document xmlDoc = null;
		if (file.exists() && file.isFile()) {
			xmlDoc = new SAXBuilder().build(new FileInputStream(file));
		}
		else{
			throw new FileNotFoundException("File "+configurationFilePath+" was not found or it is directory");
		}
		String targetIPAddress = null;
		int targetComunicationPort = 0;
		int targetComunicationProtocol = 0;
		String cnlFilePath = null;
		String textLogFilePath = null;
		int defaultCallbackValue = 0;
		int deviceId = 0;
		String commandServerIPAddress = null;
		int commandServerPort = 0;
		List<Element> configItems = xmlDoc.getRootElement().getChildren();
		for(Element item : configItems){
			switch(item.getName()){
				case "targetComunicationIPAddress"				: 
					targetIPAddress  = item.getAttributeValue("value");
					break;
				case "targetComunicationPort"		:
					targetComunicationPort = Integer.parseInt(item.getAttributeValue("value"));
					break;
				case "targetComunicationProtocol"	: 
					targetComunicationProtocol = Integer.parseInt(item.getAttributeValue("value"));
					break;
				case "defaultCNLFilePath"			: 
					cnlFilePath = item.getAttributeValue("value");
					break;
				case "defaultTextLogPath"			: 
					textLogFilePath = item.getAttributeValue("value");
					break;
				case "defaultCallbackValue"			:
					defaultCallbackValue = Integer.parseInt(item.getAttributeValue("value"));
					break;
				case "deviceId"						:
					deviceId = Integer.parseInt(item.getAttributeValue("value"));
					break;
				case "commandServerIPAddress"		:
					commandServerIPAddress =  item.getAttributeValue("value");
					break;
				case "commandServerPort"			:
					commandServerPort = Integer.parseInt(item.getAttributeValue("value"));
					break;
					
			}
		}
		Logger.log(LogLevels.INFO, reader, "Function readConfiguration was finished");
		return new Configuration(targetIPAddress, targetComunicationPort, targetComunicationProtocol,
										cnlFilePath, textLogFilePath, defaultCallbackValue,
										commandServerIPAddress, commandServerPort, deviceId);
	}
}
