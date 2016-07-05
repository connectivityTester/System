package io;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class XMLReader extends Reader{
	
	private Element  rootElement;
	private Document xmlDoc;

	public XMLReader(String filePath) {
		super(filePath);
		xmlDoc = null;
		try {
			xmlDoc = new SAXBuilder().build(new File(this.filePath));
		} catch (IOException e) {
			System.out.println("Some problems with input files");
			e.printStackTrace();
		} catch (JDOMException e) {
			System.out.println("Some problems with XML or JDOM");
			e.printStackTrace();
		}
	}

	@Override
	public Configuration readConfiguration() throws IOException {		
		this.rootElement = xmlDoc.getRootElement(); 
		Configuration configuration = new Configuration();
		List<Element> addresses = rootElement.getChild("targets").getChildren();
		for(Element address : addresses){
			configuration.setIPAddress(address.getText());
		}
		configuration.setPort(rootElement.getChild("port").getText());
		configuration.setUserName(rootElement.getChild("user").getText());
		configuration.setPassword(rootElement.getChild("password").getText());
		configuration.setEcho(rootElement.getChild("echo_string").getText());
		List<Element> interfaces = rootElement.getChild("interfaces").getChildren();
		for(Element interfaceElem : interfaces){
			configuration.addInterface(interfaceElem.getText());
		}
		List<Element> processes = rootElement.getChild("processes").getChildren();
		for(Element process : processes){
			configuration.addProcess(process.getText());
		}	
		List<Element> binaries = rootElement.getChild("binaries").getChildren();
		for(Element binary : binaries){
			configuration.addBinary(binary.getText());
		}
		return configuration;
	}

	@Override
	public String getTargetIP() { return rootElement.getChild("ipaddress").getText(); }

}
