package utils;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import gui.WorkSpace;
import types.LogLevels;
import types.MessageLogTypes;

public class Logger {
	
	private final static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss ");
	private final static int maxLogFileLines = 100000; 
	
	private static WorkSpace mWorkSpace;
	private static List<LogLevels> currentLogLevels = new ArrayList<>();
	private static PrintWriter logFileWriter;
	private static File logFile;
	private static int currentLogFileLinesCounter;
	private static File logFilesDirectory;
	
	static{
		logFilesDirectory = new File(System.getProperty("user.dir")+"/SystemLogs");
		if(!logFilesDirectory.exists()){
			logFilesDirectory.mkdir();
		}
	}
	
	public static void log(final LogLevels logLevel, final Object className, final String message){
		utils.Utils.requireNonNull(logLevel, className, message);
		
		StringBuilder messageLog = new StringBuilder(logLevel.toString());
		messageLog.append(": ");
		if(className !=null){
			messageLog.append(className.getClass().getName());
		}
		messageLog.append(": ");
		messageLog.append(message);
		if(currentLogLevels.contains(logLevel))	
		{
			System.out.print(dateFormat.format(new Date()));
			System.out.println(messageLog);
		}
		try {
			logToFile(messageLog.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void logToUser(final String messageLog, final Object className, final MessageLogTypes messageType){
		utils.Utils.requireNonNull(messageLog, className, messageType);
		
		if(mWorkSpace != null){
			mWorkSpace.showLog(messageLog, messageType);
		}
		StringBuilder log = new StringBuilder("USER: ");
		log.append(className.getClass().getName());
		log.append(": ");
		log.append(messageLog);
		try {
			logToFile(log.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.print(dateFormat.format(new Date()));
		System.out.println(log.toString());
	}
	
	public static void setWorkSpace (final WorkSpace workSpace)		{	mWorkSpace = workSpace;			}
	public static void setLogLevel(final List<LogLevels> newlogLevel) {	currentLogLevels = newlogLevel;	}
	
	private static void logToFile(final String logMessage) throws IOException{
		if(logFileWriter == null || maxLogFileLines < currentLogFileLinesCounter){
			if(logFileWriter != null){
				logFileWriter.flush();
				logFileWriter.close();
			}
			if(!logFilesDirectory.exists()){
				try {
					logFilesDirectory.createNewFile();
				} catch (IOException e) {}
			}
			logFile = new File(logFilesDirectory.getName()+ "/" + new SimpleDateFormat("yyMMdd_HHmmss").format(new Date()).toString()+".txt");
			if(!logFile.exists()){
				logFile.createNewFile();
			}
			logFileWriter = new PrintWriter(logFile);
			currentLogFileLinesCounter = 0;
		}
		else{
			logFileWriter.println(dateFormat.format(new Date()));
			logFileWriter.println(logMessage);
			currentLogFileLinesCounter++;
			//logFileWriter.flush();
		}
	}
	
	public static void closeLogger(){
		logFileWriter.flush();
		logFileWriter.close();
	}

	public static void logFromDeviceSource(final String logMessage) {
		//System.out.print(dateFormat.format(new Date()));
		System.out.println(logMessage);
		try {
			logToFile(logMessage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
