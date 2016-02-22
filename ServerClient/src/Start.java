import java.util.ArrayList;
import java.util.List;
import gui.WorkSpace;
import utils.Logger;
import javax.swing.UIManager;
import connections.ConnenctionController;
import io.*;
import starters.DeviceSourceRunManager;
import types.LogLevels;

public class Start {

	public static void main(String ... args) {
		List<LogLevels> levels = new ArrayList<>();
		levels.add(LogLevels.EXCEPTION);
		//levels.add(LogLevels.INFO);
		levels.add(LogLevels.TRACE);
		levels.add(LogLevels.ERROR);
		Logger.setLogLevel(levels);
		
		try{
	        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
	    } catch (Exception evt) {}
		
		WorkSpace workSpace = new WorkSpace(new FileSystemManager());				
		ConnenctionController.getInstatnce().startAllConnections(workSpace);
		DeviceSourceRunManager.getInstance().setWorkSpace(workSpace);
		DeviceSourceRunManager.getInstance().startDeviceSources();
	}
}