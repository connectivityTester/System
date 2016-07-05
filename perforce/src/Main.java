import java.io.File;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import logic.Properties;
import static controller.Controller.controller;
/**
 * This class is application entry point.
 * It checks ".ini" file existing 
 * and create instance of {@link controller.Controller} 
 * 
 * @author dkuschevoy
 *
 */
public class Main {	
	
	public static void main(String ... args) throws Exception{			
		if (!new File(Properties.CONFIG_FILE).exists()){
			JOptionPane.showMessageDialog(
					null, 
					"Sorry, but application can not find \"config.ini\" file. Create it and try again", 
					"Loading erro", 
					JOptionPane.ERROR_MESSAGE);
			
		}
		else{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");		
			controller.init();
		}
	}
}
