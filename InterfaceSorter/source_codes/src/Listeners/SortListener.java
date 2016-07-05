package Listeners;
import gui.ResultInterfaceTable;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import controllers.DataController;
import Constants.Constants;
import logic.ResultLine;

public class SortListener extends Listener {

	private JTextArea missingInterfaces;

	public SortListener(JTextArea input, ResultInterfaceTable table, JLabel summary, DataController controller, JTextArea missingInterfaces) {
		super(input, table, summary, controller);
		this.missingInterfaces = missingInterfaces;
	}
	
	public void actionPerformed(ActionEvent e) 			{ this.viewResults(devideInputText());	}
	public void sortInputData ()						{ this.viewResults(devideInputText()); 	}
	private boolean viewResults(List<ResultLine> lines)	{ return this.tableView.setData(lines, this.controller, this.missingInterfaces );	}
	
	private List<ResultLine> devideInputText(){
		List <ResultLine> lines = new ArrayList<ResultLine>(10);
		String[] splitStrings = this.input.getText().split("\\n");
		for(int i = 0; i<splitStrings.length;i=i+1){
			lines.add(new ResultLine(splitStrings[i]));
		}
		Collections.sort(lines);
		if(viewResults(lines)){
			this.summary.setIcon(new ImageIcon(Constants.getOkImage()));
		}
		else{
			this.summary.setIcon(new ImageIcon(Constants.getNotOkImage()));
		}	
		this.summary.updateUI();			
		for(ResultLine line : lines){
			//line.
		}
		return lines;
	}
	
		
}