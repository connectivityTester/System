package common;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;


public interface SystemConstants {

	int systemDeviceSourceId = -1;
	int defaultTimeout = 4000; // 4000ms
	String fileConfigPath = "scconfig.xml";
	Font font = new Font("Arial", Font.PLAIN, 15); 
	Font logFont = new Font ("Arial", Font.PLAIN, 10);
	
	final static Toolkit toolkit = Toolkit.getDefaultToolkit(); 
	final static String userDir = System.getProperty("user.dir");
	final static String configShemaPath = "/shemas/configuration_shema.xsd";
	final static String testShemaPath = "/shemas/test_shema.xsd";
	
	static Image getAddImage()		{	return toolkit.createImage( userDir + "/images/add.png");	}		
	static Image getDeleteImage()	{	return toolkit.createImage( userDir + "/images/delete.png");}
	static Image getUpImage()		{	return toolkit.createImage( userDir + "/images/up.png");	}
	static Image getDownImage()		{	return toolkit.createImage( userDir + "/images/down.png");	}
	static Image getSaveImage()		{	return toolkit.createImage( userDir + "/images/save.png");	}
	static Image getRunImage()		{	return toolkit.createImage( userDir + "/images/run.png");	}
	static Image getClearImage()	{	return toolkit.createImage( userDir + "/images/clear.png");	}
}
