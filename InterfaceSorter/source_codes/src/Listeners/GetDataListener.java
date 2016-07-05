package Listeners;

import java.awt.event.ActionEvent;
import java.util.List;

import gui.ResultInterfaceTable;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Constants.Constants;
import controllers.DataController;

public class GetDataListener extends Listener{

	private DataController controller;
	private JComboBox<String> targetField;

	public GetDataListener(JTextArea input, ResultInterfaceTable table,JLabel summary, DataController controller, JComboBox<String> targetfield) {
		super(input, table, summary,controller);
		this.targetField = targetfield;
	}
	
	public void setController(DataController controller) { this.controller = controller; }

	@Override
	public void actionPerformed(ActionEvent e) {
		this.input.setText("");
		this.tableView.removeData();
		this.summary.setIcon(new ImageIcon(Constants.getWaitingImage()));
		this.controller.connectToTheTarget(this.targetField.getSelectedItem().toString());
		List<String> lines = controller.getNeededInterfaces(controller.getConcoleOutPut());
		for(String line : lines){
			this.input.append(line+'\n');
		}
		this.controller.sortData();
		this.controller.updateBinaries(this.targetField.getSelectedItem().toString());
		this.controller.updateComboBox(this.targetField.getSelectedItem().toString());
	}

}
