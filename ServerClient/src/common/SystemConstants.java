package common;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;


public class SystemConstants {

	public static int systemDeviceSourceId = -1;
	public static int defaultTimeout = 4000; // 4000ms
	public static String fileConfigPath = "scconfig.xml";
	public static Font font = new Font("Arial", Font.PLAIN, 15); 
	public static Font logFont = new Font ("Arial", Font.PLAIN, 10);
	
	public final static String userDir = System.getProperty("user.dir");
	public final static String configShemaPath = "/shemas/configuration_shema.xsd";
	public final static String testShemaPath = "/shemas/test_shema.xsd";
	
	private final static Toolkit toolkit = Toolkit.getDefaultToolkit(); 

	public static Image getAddImage()		{	return toolkit.createImage( userDir + "/images/add.png");	}		
	public static Image getDeleteImage()	{	return toolkit.createImage( userDir + "/images/delete.png");}
	public static Image getUpImage()		{	return toolkit.createImage( userDir + "/images/up.png");	}
	public static Image getDownImage()		{	return toolkit.createImage( userDir + "/images/down.png");	}
	public static Image getSaveImage()		{	return toolkit.createImage( userDir + "/images/save.png");	}
	public static Image getRunImage()		{	return toolkit.createImage( userDir + "/images/run.png");	}
	public static Image getClearImage()		{	return toolkit.createImage( userDir + "/images/clear.png");	}
}
