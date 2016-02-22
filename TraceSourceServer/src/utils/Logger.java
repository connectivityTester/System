package utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import types.LogLevels;

public class Logger {
	
	private final static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss ");
	private final static int maxLogFileLines = 100000; 
	private static PrintWriter logFileWriter;
	private static File logFile;
	private static int currentLogFileLinesCounter;
	private static File logFilesDirectory;
	
	static{
		logFilesDirectory = new File(System.getProperty("user.dir")+"/TraceServerLogs");
		if(!logFilesDirectory.exists()){
			logFilesDirectory.mkdir();
		}
	}
	public static void log(LogLevels logLevel, Object className, String message){
		StringBuilder messageLog = new StringBuilder(logLevel.toString());
		messageLog.append(": ");
		messageLog.append(className.getClass().getName());
		messageLog.append(": ");
		messageLog.append(message);
		try {
			logToFile(messageLog.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(dateFormat.format(new Date()) + ": "+logLevel+": "+className.getClass().getName()+": "+message);
	}
	
	private static void logToFile(String logMessage) throws IOException{
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
			logFileWriter.print(dateFormat.format(new Date()));
			logFileWriter.println(logMessage);
			currentLogFileLinesCounter++;
			logFileWriter.flush();
		}
	}
}
