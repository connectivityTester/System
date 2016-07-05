package gui;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import controllers.DataController;
import Constants.Constants;
import Listeners.ClearListener;
import Listeners.CloseListener;
import Listeners.GetDataListener;
import Listeners.SortListener;

public class MainFrame extends JFrame{
	
	private JTextArea input;
	private JTextArea missingInterfaces;
	private JButton sortData;
	private JButton clearData;
	private JButton getData;
	private JLabel summary;
	private JComboBox<String> targetIPfield;
	private DataController controller;
	private ResultInterfaceTable table;
	private SortListener sorterListener;
	private GetDataListener getDataListener;
	private WarningWindow warningWindow;
	private ResultBinariesView resultBinariesView;
	private JScrollPane binariesScroll;
	
	public MainFrame(DataController controller){
		super("InterfaceSorter for DIS2");
		this.controller = controller;
		this.input = new JTextArea(10,45);
		this.missingInterfaces = new JTextArea(19,5);
		this.missingInterfaces.setEditable(false);
		this.input.setLineWrap(true);
		this.input.setWrapStyleWord(true);
		this.getData = new JButton("Get data");
		this.sortData = new JButton("Sort data");
		this.clearData = new JButton("Clear all");
		this.summary = new JLabel(new ImageIcon(Constants.getWaitingImage()));
		this.targetIPfield = new JComboBox<String>(this.controller.getAllAddresses());
		this.targetIPfield.setEditable(true);
		this.warningWindow = new WarningWindow(this);
		this.resultBinariesView = new ResultBinariesView(this.controller.getBinaries(this.targetIPfield.getSelectedItem().toString()));
		this.binariesScroll = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.initComponents();
		this.setMinimumSize(new Dimension(944, 872));
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void setData(List<String> data){
		for(String line : data){
			this.input.append(line+'\n');			
		}
	}
	
	private void makePretty() { 
		this.targetIPfield.setFont(Constants.getViewFont());
	}
	public void sortData(){ 
		this.sorterListener.sortInputData();
	}
	
	private void initComponents(){
		this.composeComponents();
		this.makePretty();
		this.addListeners();
	}

	private void composeComponents(){
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		this.targetIPfield.setBorder(BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder(),"Target IP", TitledBorder.CENTER, TitledBorder.TOP, Constants.getViewFont(), Color.blue));
		JPanel buffer = new JPanel();
		buffer.setLayout(new BoxLayout(buffer, BoxLayout.X_AXIS));
		buffer.add(Box.createHorizontalGlue());
		buffer.add(this.targetIPfield);
		buffer.add(Box.createHorizontalGlue());
		this.add(buffer);
		buffer = new JPanel();
		buffer.setLayout(new BoxLayout(buffer, BoxLayout.X_AXIS));
		JScrollPane scroll = new JScrollPane(input,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setBorder(BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder(),"Input", TitledBorder.CENTER, TitledBorder.TOP, Constants.getViewFont(), Color.blue));
		buffer.add(scroll);
		this.binariesScroll.setViewportView(this.resultBinariesView);
		this.binariesScroll.setPreferredSize(new Dimension(190, 300));
		this.binariesScroll.setBorder(BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder(),"Necessary binaries", TitledBorder.CENTER, TitledBorder.TOP, Constants.getViewFont(), Color.blue));
		buffer.add(this.binariesScroll);
		this.add(buffer);			
		this.initButtons();		
		buffer = new JPanel();
		buffer.setLayout(new BoxLayout(buffer, BoxLayout.X_AXIS));
		buffer.setBorder(BorderFactory.createTitledBorder(  BorderFactory.createEtchedBorder(), "Output", TitledBorder.CENTER, TitledBorder.TOP, Constants.getViewFont(), Color.blue));
		this.initResultTable();		
		buffer.add(new JScrollPane(table));
		JPanel resultInfoPanel = new JPanel();
		resultInfoPanel.setLayout(new BoxLayout(resultInfoPanel, BoxLayout.Y_AXIS));
		JPanel missingInterfacesPanel = new JPanel();
		missingInterfacesPanel.setLayout(new BoxLayout(missingInterfacesPanel, BoxLayout.X_AXIS));
		JScrollPane scr = new JScrollPane(this.missingInterfaces, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scr.setPreferredSize(new Dimension(150, 200));
		missingInterfacesPanel.add(scr);
		missingInterfacesPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"Missing interfaces", TitledBorder.CENTER, TitledBorder.TOP, Constants.getViewFont(), Color.blue));
		resultInfoPanel.add(missingInterfacesPanel);
		JPanel summaryPanel = new JPanel();
		summaryPanel.setLayout(new BoxLayout(summaryPanel, BoxLayout.X_AXIS));
		summaryPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"Summary", TitledBorder.CENTER, TitledBorder.TOP, Constants.getViewFont(), Color.blue));
		summary.setAlignmentX(summary.CENTER_ALIGNMENT);
		summaryPanel.add(Box.createHorizontalStrut(15));
		summaryPanel.add(summary);
		summaryPanel.add(Box.createHorizontalStrut(15));
		resultInfoPanel.add(summaryPanel);
		buffer.add(resultInfoPanel);
		this.add(buffer);		
	}

	private void initButtons() {
		JPanel buffer = new JPanel();
		buffer.setLayout( new BoxLayout(buffer, BoxLayout.X_AXIS));
		buffer.add(this.getData);
		buffer.add(Box.createHorizontalStrut(75));
		buffer.add(this.sortData);
		buffer.add(Box.createHorizontalStrut(75));
		buffer.add(this.clearData);
		this.add(buffer);		
	}

	
	private void initResultTable(){
		Vector<String> rowNames = new Vector<String>();
		rowNames.addElement("Result");
		rowNames.addElement("Interface name");
		rowNames.addElement("Version");
		rowNames.addElement("Application");
		this.table = new ResultInterfaceTable(rowNames, 0, this.missingInterfaces);
	}
		
	private void addListeners() {
		this.clearData.addActionListener(new ClearListener(this.input, this.table, this.summary, this.controller));
		this.sorterListener = new SortListener(this.input, this.table, this.summary, this.controller, this.missingInterfaces);
		this.sortData.addActionListener(this.sorterListener);
		this.getDataListener = new GetDataListener(this.input, this.table, this.summary, this.controller, this.targetIPfield);
		this.getDataListener.setController(this.controller);
		this.getData.addActionListener(getDataListener);
		this.addWindowListener(new CloseListener(this.controller, this.targetIPfield));
	}

	
	public void startShowWarning(String warningMessage, Image iconURL){
		this.warningWindow.setMessage(warningMessage);
		this.warningWindow.setGifIcon(new ImageIcon(iconURL));
		this.warningWindow.setVisible(true);
		this.warningWindow.repaint();
		this.input.setEnabled(false);
		this.targetIPfield.setEnabled(false);
		this.table.setEnabled(false);
		this.getData.setEnabled(false);
		this.sortData.setEnabled(false);
		this.clearData.setEnabled(false);
	}
	
	public void finishShowWarning(){
		this.warningWindow.setVisible(false);
		this.input.setEnabled(true);
		this.targetIPfield.setEnabled(true);
		this.table.setEnabled(true);
		this.getData.setEnabled(true);
		this.sortData.setEnabled(true);
		this.clearData.setEnabled(true);
	}

	public void updateBinaries(List<BinaryLabel> binaries) {
		this.resultBinariesView.setData(binaries);
		this.binariesScroll.updateUI();
	}

	public void clearData() {
		this.missingInterfaces.setText("");
		this.resultBinariesView.clearData();
		this.binariesScroll.updateUI();		
	}

	public void setTargetIP(String ipAddress) {
		this.targetIPfield.setSelectedItem(ipAddress);		
	}

	public void updateComboBox(String address) {
		for(int i = 0; i < this.targetIPfield.getItemCount(); ++i){
			if(this.targetIPfield.getItemAt(i).toString().equals(address)){
				return;
			}
		}
		this.targetIPfield.addItem(address);
		this.targetIPfield.updateUI();
	}
}
