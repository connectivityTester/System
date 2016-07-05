package Listeners;
import gui.ResultInterfaceTable;

import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import controllers.DataController;


public abstract class Listener implements ActionListener {
	
	protected JTextArea input;
	protected ResultInterfaceTable tableView;
	protected JLabel summary;
	protected DataController controller;
	
	public Listener (JTextArea input, ResultInterfaceTable table, JLabel summary, DataController controller){
		this.input = input;
		this.tableView = table; 
		this.summary = summary;
		this.controller = controller;
	}
}
