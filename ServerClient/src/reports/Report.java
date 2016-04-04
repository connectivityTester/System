package reports;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import test.Test;
import types.LogLevels;
import utils.Logger;

public abstract class Report {
	
	protected final static File reportFilesDirectory;
	
	static{
		reportFilesDirectory = new File(System.getProperty("user.dir")+"/Reports");
		if(!reportFilesDirectory.exists()){
			boolean result = reportFilesDirectory.mkdirs();
			Logger.log(LogLevels.TRACE, "", "Report directory was create with result: " + result);
		}
	}
	
	public abstract void setDataToFile(List<Test> executedTest);
	public abstract void saveDataToFile(String pathTofile) throws FileNotFoundException, Exception;
	public abstract boolean openReportFile(String pathTofile);
}
