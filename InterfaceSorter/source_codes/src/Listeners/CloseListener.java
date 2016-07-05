package Listeners;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;

import controllers.DataController;

public class CloseListener implements WindowListener{
	
	private DataController controller;
	private JComboBox<String> targetField;
	
	public CloseListener(DataController controller, JComboBox<String> targetField){
		this.controller = controller;
		this.targetField = targetField;
	}
	public void windowClosing(WindowEvent e) {
		this.controller.writeConfiguration();
	}
	public void windowActivated(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}

}
