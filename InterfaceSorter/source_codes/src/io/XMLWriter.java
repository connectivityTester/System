package io;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class XMLWriter {
	private XMLOutputter outputter;
	private  FileWriter fw;
	private Document doc;
	
	public XMLWriter(String file){
		try {
            this.outputter = new XMLOutputter();
            outputter.setFormat(Format.getPrettyFormat());
            this.fw = new FileWriter(file);
            this.doc = new Document();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
	}

	public void writeConfiguration(Configuration configuration) {
		Element rootElement = new Element("configuration");
		this.doc.setRootElement(rootElement);
		Element addressesItems = new Element("targets");
		for(String address : configuration.getAllAddresses()){
			addressesItems.addContent(new Element("ipaddress").addContent(address));
		}
		rootElement.addContent(addressesItems);
		rootElement.addContent(new Element("port").addContent(configuration.getPort()));
		rootElement.addContent(new Element("user").addContent(configuration.getUserName()));
		rootElement.addContent(new Element("password").addContent(configuration.getPassword()));
		rootElement.addContent(new Element("echo_string").addContent(configuration.getEcho()));
		Element intefaces = new Element("interfaces");
		for(String configInterface : configuration.getInterfaces()){
			intefaces.addContent(new Element("interface").addContent(configInterface));
		}
		rootElement.addContent(intefaces);
		Element processes = new Element("processes");
		for(String proccess : configuration.getProcesses()){
			processes.addContent(new Element("process").addContent(proccess));
		}
		rootElement.addContent(processes);
		Element binaries = new Element("binaries");
		for(String binary : configuration.getBinaries()){
			binaries.addContent(new Element("process").addContent(binary));
		}
		rootElement.addContent(binaries);
		try {
			outputter.output(doc, fw);
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		;
	}

}
