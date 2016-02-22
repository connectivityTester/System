package common;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;


public interface SystemConstants {
	
	public int maxThreadCount = Runtime.getRuntime().availableProcessors();
	public int systemDeviceSourceId = -1;
	public int defaultTimeout = 4000; // 4000ms
	public String fileConfigPath = "scconfig.xml";
	public Font font = new Font("Arial", Font.PLAIN, 15); 
	public Font logFont = new Font ("Arial", Font.PLAIN, 10);
	
	public static Image getAddImage()	{
		return Toolkit.getDefaultToolkit().createImage(System.getProperty("user.dir") + "/images/add.png");
	}	
	
	public static Image getDeleteImage()	{
		return Toolkit.getDefaultToolkit().createImage(System.getProperty("user.dir") + "/images/delete.png");
	}
	public static Image getUpImage()	{
		return Toolkit.getDefaultToolkit().createImage(System.getProperty("user.dir") + "/images/up.png");
	}
	public static Image getDownImage()	{
		return Toolkit.getDefaultToolkit().createImage(System.getProperty("user.dir") + "/images/down.png");
	}
	public static Image getSaveImage()	{
		return Toolkit.getDefaultToolkit().createImage(System.getProperty("user.dir") + "/images/save.png");
	}
	public static Image getRunImage()	{
		return Toolkit.getDefaultToolkit().createImage(System.getProperty("user.dir") + "/images/run.png");
	}
	public static Image getClearImage()	{
		return Toolkit.getDefaultToolkit().createImage(System.getProperty("user.dir") + "/images/clear.png");
	}
}
