package Listeners;
import gui.ResultInterfaceTable;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import controllers.DataController;
import Constants.Constants;


public class ClearListener extends Listener {

	public ClearListener(JTextArea input, ResultInterfaceTable table, JLabel summary, DataController controller) {	
		super(input, table, summary ,controller);	
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.input.setText("");
		this.tableView.removeData();
		this.summary.setIcon(new ImageIcon(Constants.getWaitingImage()));
		this.controller.clearData();
	}

}
