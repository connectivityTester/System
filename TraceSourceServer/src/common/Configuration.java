package common;

import java.io.IOException;

import org.jdom2.JDOMException;

import types.LogLevels;
import utils.Logger;

public class Configuration {
	
	private final static String configFilePath = "tsconfig.xml";
	private static Configuration config;
	
	private final String commandServerIPAddress;
	private final int commandServerPort;
	private final int deviceId;
	
	private final String targetIPAddress;
	private final int targetComunicationPort;
	private final int targetComunicationProtocol;
	private final String cnlFilePath;
	private final String textLogFilePath;
	private final int defaultCallbackValue;
	
	Configuration(String targetIPAddress, int targetComunicationPort, int targetComunicationProtocol,
					String cnlFilePath, String textLogFilePath,	int defaultCallbackValue,
					String commandServerIPAddress, int commandServerPort, int deviceId) 
	{
		this.targetIPAddress = targetIPAddress;
		this.targetComunicationPort = targetComunicationPort;
		this.targetComunicationProtocol = targetComunicationProtocol;
		this.cnlFilePath = cnlFilePath;
		this.textLogFilePath = textLogFilePath;
		this.defaultCallbackValue = defaultCallbackValue;
		this.commandServerIPAddress = commandServerIPAddress;
		this.commandServerPort = commandServerPort;
		this.deviceId = deviceId;
		Logger.log(LogLevels.TRACE, new Object(), toString());
	}
	
	public static Configuration getInstance(){
		if(config == null){
			try {
				config = XMLReader.readConfiguration(configFilePath);
			} catch (NumberFormatException | JDOMException | IOException e) {
				e.printStackTrace();
			}
		}
		return config;
	}

	public String getConfigFilePath() 			{	return configFilePath;				}
	public String getTargetIPAddress() 			{	return targetIPAddress;				}
	public int getTargetComunicationPort() 		{	return targetComunicationPort;		}
	public int getTargetComunicationProtocol() 	{	return targetComunicationProtocol;	}
	public String getCnlFilePath() 				{	return cnlFilePath;					}
	public String getTextLogFilePath() 			{	return textLogFilePath;				}
	public int getDefaultCallbackValue() 		{	return defaultCallbackValue;		}
	public String getCommandServerIPAddress() 	{	return commandServerIPAddress;		}
	public int getCommandServerPort() 			{	return commandServerPort;			}
	public int getDeviceId() 					{	return deviceId;					}

	public String toString() {
		return "Configuration [commandServerIPAddress=" + commandServerIPAddress + ", commandServerPort="
				+ commandServerPort + ", deviceId=" + deviceId + ", targetIPAddress=" + targetIPAddress
				+ ", targetComunicationPort=" + targetComunicationPort + ", targetComunicationProtocol="
				+ targetComunicationProtocol + ", cnlFilePath=" + cnlFilePath + ", textLogFilePath=" + textLogFilePath
				+ ", defaultCallbackValue=" + defaultCallbackValue + "]";
	}
}
