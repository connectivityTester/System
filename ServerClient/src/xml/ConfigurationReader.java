package xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import common.DeviceSource;
import common.SystemConstants;
import exceptions.ContentException;
import types.LogLevels;
import types.SourceTypes;
import utils.Logger;

public class ConfigurationReader extends AbstractReader{
	
	private final Pattern PATTERN = Pattern.compile("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
	
	public ConfigurationReader(String shemaPath) {
		super(shemaPath);
	}
	
	@Override
	protected SystemConfig readContext(String filePath) throws FileNotFoundException, Exception {
		File file = new File(filePath);
		Document xmlDoc = null;
		if (file.exists() && file.isFile()) {
			xmlDoc = new SAXBuilder().build(new FileInputStream(file));
		}
		else{
			Logger.log(LogLevels.EXCEPTION, this, "File "+ filePath +" was not found or it is directory");
			throw new FileNotFoundException("File "+ filePath +" was not found or it is directory");
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
						deviceSources.add(new DeviceSource(name, id, autoStartUp, address, startParameters, shutdownParameters));
					}
					break;
			}
		}
		return new SystemConfig(testRootDirectory, messagePort, deviceSources);
	}

	@Override
	protected Context validateReadContext(Context context) throws ContentException {
		SystemConfig systemConfig = (SystemConfig)context;
		// check test directory is exists
		File testDirectory = new File(systemConfig.getTestRootDirectory());
		if(!testDirectory.exists()){
			testDirectory = new File(SystemConstants.userDir + systemConfig.getTestRootDirectory());
			if(!testDirectory.exists()){
				String message = "Mentioned test directory " + systemConfig.getTestRootDirectory() + "does not exist";
				throw new ContentException(message);
			}
		}
		//check the availability message port
	    try( ServerSocket socket = new ServerSocket(systemConfig.getMessagePort())){
	    } catch (IOException e) {
	        throw new ContentException("Mentioned message port is incorrect or busy. Please use another port");
	    }
	    Set<Integer> deviceSourceIdSet = new HashSet<>();
		for(DeviceSource deviceSource : systemConfig.getDeviceSources()){
			//check the availability id for current device source
			if(!deviceSourceIdSet.add(deviceSource.getId())){
				Logger.log(LogLevels.EXCEPTION, this, "Device id for " + deviceSource.getName() + " has been already used. Please use another");
				throw new ContentException("Device id for " + deviceSource.getName() + " has been already used. Please use another");
			}
			//check the correctness of device source ip address 
			if(deviceSource.getSourceType() != SourceTypes.SYSTEM_SOURCE &&
					!this.PATTERN.matcher(deviceSource.getAddress()).matches())
			{
				Logger.log(LogLevels.EXCEPTION, this, "Device source \"" + deviceSource.getName() + " has incorrect ip address. Please correct it");
				throw new ContentException("Device source \"" + deviceSource.getName() + " has incorrect ip address. Please correct it");
			}
		}
		return systemConfig;
	}
}
