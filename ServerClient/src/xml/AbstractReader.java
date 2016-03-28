package xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;

import common.SystemConstants;
import exceptions.ContentException;
import types.LogLevels;
import utils.Logger;

public abstract class AbstractReader {
	
	protected final String shemaPath;
	
	protected AbstractReader (final String shemaPath){
		utils.Utils.requireNonNull(shemaPath);
		
		this.shemaPath = shemaPath;
	}
	
	public final Context getContext(String filePath) throws FileNotFoundException, ContentException, Exception{
		utils.Utils.requireNonNull(filePath);
		
		if(this.validateFileContext(filePath, this.shemaPath)){
			Logger.log(LogLevels.TRACE, this, "File \"" + filePath + " was validated successfully!");
			Context context = this.readContext(filePath);
			return this.validateReadContext(context);			
		}
		else{
			Logger.log(LogLevels.ERROR, this, "File \"" + filePath + " was not  validated successfully!");
			return null;
		}
	}
	
	protected abstract Context readContext(final String filePath) throws FileNotFoundException, Exception;
	protected abstract Context validateReadContext(final Context context) throws ContentException;
	
	protected boolean validateFileContext(final String filePath, final String shemaPath) throws ContentException{
		utils.Utils.requireNonNull(filePath, shemaPath);
		
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setValidating(false); 
		factory.setNamespaceAware(true);		
		SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		SAXParser parser = null;
		try {
			factory.setSchema(schemaFactory.newSchema(new File(SystemConstants.userDir + shemaPath)));
		}
		catch (SAXException se) {
			Logger.log(LogLevels.EXCEPTION, this, "System could not validate due to: " + se.getMessage());
			throw new ContentException("System could not validate due to " + se.getMessage());
		}
		try {
			parser = factory.newSAXParser();
		} catch (ParserConfigurationException | SAXException e2) {
			Logger.log(LogLevels.EXCEPTION, this, "System could not validate due to " + e2.getMessage());
			throw new ContentException("System could not validate due to " + e2.getMessage());
		}
		XMLReader reader = null;
		try {
			reader = parser.getXMLReader();
		} catch (SAXException e2) {
			Logger.log(LogLevels.EXCEPTION, this, "System could not validate due to " + e2.getMessage());
			throw new ContentException("System could not validate due to " + e2.getMessage());
		}
		reader.setErrorHandler( new ErrorHandler() {
			public void warning(SAXParseException e) throws SAXException {
				Logger.log(LogLevels.EXCEPTION, this, "Warning was occurred: " + e.getMessage());
				throw e;
			}
		
			public void error(SAXParseException e) throws SAXException {
				Logger.log(LogLevels.EXCEPTION, this, "Error was occurred: " + e.getMessage());
				throw e;
			}
		
			public void fatalError(SAXParseException e) throws SAXException {
				Logger.log(LogLevels.EXCEPTION, this, "FatalError was occurred: " + e.getMessage());
				throw e;
		    }
		});
		try {
			reader.parse(new InputSource(filePath));
		} catch (IOException | SAXException e1) {
			Logger.log(LogLevels.EXCEPTION, this, "Problem during parsing: " + e1.getMessage());
			throw new ContentException("Problem during parsing: " + e1.getMessage());
		}
		return true;
	}
}
