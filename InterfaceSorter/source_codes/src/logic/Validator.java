package logic;
import gui.ColumnRender;
import gui.ResultInterfaceTable;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.ControllerEventListener;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import controllers.DataController;

public class Validator {
	
	public static boolean validateVersions(ResultInterfaceTable table){
		boolean isIndentical = true;
		table.getColumnModel().getColumn(0).setCellRenderer(new ColumnRender(new Font("Tahoma", 1, 15), Color.RED, JLabel.CENTER));
		for(int i = 0; i < table.getRowCount()-1; ++i ){
			String interface1 = table.getValueAt(i, 1).toString();
			String interface2 = table.getValueAt(i+1, 1).toString();
			if(interface1.equals(interface2)){
				String version1 = table.getValueAt(i, 2).toString();
				String version2 = table.getValueAt(i+1, 2).toString();
				if(!version1.equals(version2)){
					isIndentical = false;
					boolean needToStop = false;
					for(int k = 0; k <table.getRowCount()-1;++k){
						if(table.getValueAt(k, 1).toString().equals(interface1)){
							table.setValueAt("X", k, 0);
							needToStop = true;
						}
						table.updateUI();	
					}
				}
				else{
					table.setValueAt(" ", i, 0);
					table.setValueAt(" ", i+1, 0);
				}
			}
		}
		return isIndentical;
	}
	
	public static List<String> checkPresence(ResultInterfaceTable table, DataController controller, JTextArea output){
		List<String> missingInterfaces = new ArrayList<>(controller.getInterfacesFromConfig());
		for(String interfaceItem : controller.getInterfacesFromConfig()){
			for(int i = 0; i < table.getRowCount();++i){
				if(table.getValueAt(i, 1).toString().contains(interfaceItem)){
					missingInterfaces.remove(interfaceItem);
					break;
				}
			}
		}
		return missingInterfaces;
	}

}
