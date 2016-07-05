package Loaders;

import io.Reader;
import io.XMLReader;

import java.io.IOException;
import java.net.SocketException;

import Constants.Constants;
import controllers.DataController;


public class Loader {

	public static void main(String[] args) {
		Reader reader = new XMLReader(Constants.getConfigurationFilePath());
		DataController controller = new DataController(reader);
		controller.connectToTheTarget(controller.getIpAddress());
		String concoleOutput = controller.getConcoleOutPut();
		controller.setData(controller.getNeededInterfaces(concoleOutput));
		controller.sortData();
	}

}
