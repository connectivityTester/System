package Constants;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

public class Constants {
	
	static Constants constants = new Constants();
	private Constants(){}
	public static String getConfigurationFilePath() { return "configuration.xml"; }	
	public static Font getViewFont() 				{ return new Font("Tahoma", Font.BOLD, 18);	}	
	public static Image getWaitingImage() 			{ return Toolkit.getDefaultToolkit().getImage(constants.getClass().getResource("images/waiting.png")); }
	public static Image getOkImage() 				{ return Toolkit.getDefaultToolkit().getImage(constants.getClass().getResource("images/big_ok.png")); }
	public static Image getNotOkImage() 			{ return Toolkit.getDefaultToolkit().getImage(constants.getClass().getResource("images/big_notOk.png")); }	
	public static Image getStartedImage() 				{ return Toolkit.getDefaultToolkit().getImage(constants.getClass().getResource("images/started.png")); }
	public static Image getNotStartedImage() 			{ return Toolkit.getDefaultToolkit().getImage(constants.getClass().getResource("images/notStarted.png")); }	
	public static String getWarningMessage()		{ return "<html><br><p align=\"center\">Connection to the target is in progress!</p><p align=\"center\">Please wait!</p>";}
	
}
