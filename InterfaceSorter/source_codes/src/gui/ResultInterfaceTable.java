package gui;

import java.awt.Font;
import java.util.List;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import controllers.DataController;
import logic.ResultLine;
import logic.Validator;

  public class ResultInterfaceTable  extends JTable{
	  
	 private DefaultTableModel tableModel;
	 private JTextArea missingsInterfaces;
	 
	 public ResultInterfaceTable(Vector<String> rowNames, int countRow, JTextArea missingsInterfaces) {
		tableModel = new DefaultTableModel(rowNames, countRow); 
		this.setModel(tableModel);
		this.missingsInterfaces = missingsInterfaces;
		
		this.getColumnModel().getColumn(0).setMaxWidth(60);
		this.getColumnModel().getColumn(2).setMaxWidth(80);
		this.getColumnModel().getColumn(3).setMaxWidth(120);
		this.getColumnModel().getColumn(3).setMinWidth(120);
			
		
		DefaultTableCellRenderer render2 = new DefaultTableCellRenderer();
		render2.setHorizontalAlignment(JLabel.RIGHT);
		this.getColumnModel().getColumn(2).setCellRenderer(render2);
		
		this.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 15));
	 }

	 public boolean setData (List <ResultLine> lines, DataController controller, JTextArea output){
		 this.tableModel.setRowCount(lines.size());
		 for(int i = 0;  i < lines.size(); ++i){
			 this.setValueAt(" "+lines.get(i).getInterfaceName(), i, 1);
			 this.setValueAt(lines.get(i).getVersion()+" ", i, 2);
			 this.setValueAt(" "+lines.get(i).getApplication()+" ", i, 3);
		 }
		 boolean result1 = Validator.validateVersions(this); 
		 List<String> interfaces = Validator.checkPresence(this, controller, output);
		 if(interfaces.size()!=0){
			 this.missingsInterfaces.setText("");
			 for(String item : interfaces){
				 this.missingsInterfaces.append(item+'\n');
			 }
			 return false;
		 }
		 else{
			 this.missingsInterfaces.setText("No missing interfaces!");
		 }
		 
		 return result1; 
	 }
	 
	 public void removeData(){	
		 this.tableModel.getDataVector().removeAllElements();
		 this.updateUI();
	 }

	 public boolean isCellEditable ( int row, int column )			{	return false;	}
	 
   }