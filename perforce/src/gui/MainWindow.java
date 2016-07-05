package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import static controller.Controller.controller;

/**
 * 
 * Display GUI to user
 * 
 * @author dkuschevoy
 *
 */

@SuppressWarnings("serial")
public class MainWindow extends JFrame{
	
	private final Font buttonFont 					= new Font("Tahoma", Font.PLAIN, 14);
	
	private final JButton 		syncDomainButton	= new JButton("Sync to latest revision");
	private final JButton 		rebaseButton 		= new JButton("Make rebase");
	//private final JButton 	buildRebaseButton	= new JButton("Build rebase CL");
	private final JButton 		labelButton			= new JButton(" Create label");
	private final JButton 		addFilesButton		= new JButton("Add files to label");
	private final JButton 		syncTCFGButton		= new JButton(" Sync to Head ");
	private final JButton 		tcfgButton			= new JButton("Make TCFG");
	//private final JButton 	buildTCFGButton		= new JButton("Build TCFG CL");
	private final JCheckBox 	submitRebaseBox		= new JCheckBox("Submit");
	private final JCheckBox 	submitLabelBox		= new JCheckBox("Submit");
	private final JCheckBox 	submitTCFGBox		= new JCheckBox("Submit");
	private final JTextField 	wicomeLabelField 	= new JTextField(24);
	private final JTextPane  	logsField			= new JTextPane();
	private JScrollPane scrollPane 					= new JScrollPane ( logsField, 
																	JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
																	JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	private final SimpleAttributeSet ok 			= new SimpleAttributeSet();
	private final SimpleAttributeSet info 			= new SimpleAttributeSet();
	private final SimpleAttributeSet debug 			= new SimpleAttributeSet();
	private final SimpleAttributeSet error 			= new SimpleAttributeSet();
	private final StyledDocument doc 				= logsField.getStyledDocument();
	
	public MainWindow(){
		super("Integration runner");
		addListeners();
		addProperties();
		layoutComponents();
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * This method transfers button clicks on GUI to 
	 * 	call appropriated methods in {@link controller.Controller}  
	 */
	private void addListeners() {
		syncDomainButton.addActionListener( event -> controller.getLatestDomainRevision());
		rebaseButton.addActionListener( event -> controller.makeRebase(wicomeLabelField.getText(), submitRebaseBox.isSelected()));
//		buildRebaseButton.addActionListener( event -> controller.buildRebaseCL()); 
		labelButton.addActionListener( event -> controller.createLabel(submitLabelBox.isSelected()));
		addFilesButton.addActionListener( event -> 	controller.addFilesToLabel());
		syncTCFGButton.addActionListener( event -> 	controller.syncTCFGToHead());
		tcfgButton.addActionListener( event -> controller.makeTCFG(wicomeLabelField.getText(), submitTCFGBox.isSelected()));
//		buildTCFGButton.addActionListener( event -> controller.buildTCFGCL());
	}

	/**
	 * Add properties to GUI items 
	 */
	private void addProperties() {		
		logsField.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), 
																	"Logs", 
																	TitledBorder.CENTER, 
																	TitledBorder.TOP, 
																	new Font("Arial", Font.PLAIN, 15),
																	Color.blue));
		wicomeLabelField.setHorizontalAlignment(JTextField.CENTER);
		this.setMinimumSize(new Dimension(900, 450));
		this.setLocationRelativeTo(null);
		
		StyleConstants.setForeground(this.ok, new Color(0, 168, 0));
		StyleConstants.setBold(this.ok, true);
		StyleConstants.setForeground(this.error, Color.RED);
		StyleConstants.setBold(this.error, true);
		StyleConstants.setForeground(this.info, Color.BLUE);
		StyleConstants.setBold(this.info, true);
		StyleConstants.setForeground(this.debug, Color.GRAY);
		StyleConstants.setBold(this.debug, true);
		
		logsField.setEditable(false);
		
		scrollPane.setAutoscrolls(true);
		
		syncDomainButton.setFont(buttonFont);
		syncTCFGButton.setFont(buttonFont);
//		buildRebaseButton.setFont(buttonFont);
//		buildTCFGButton.setFont(buttonFont);
		rebaseButton.setFont(buttonFont);
		tcfgButton.setFont(buttonFont);
		labelButton.setFont(buttonFont);
		addFilesButton.setFont(buttonFont);
	}

	/**
	 * 
	 * Layout components on GUI
	 * 
	 */
	private void layoutComponents() {
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));
		
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
		controlPanel.add(Box.createVerticalGlue());
		JPanel wicomePanel = addComponentsOnPanel(wicomeLabelField);
		wicomePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), 
																"WICOME Label", 
																TitledBorder.CENTER, 
																TitledBorder.TOP, 
																new Font("Arial", Font.PLAIN, 15),
																Color.blue));
		controlPanel.add(wicomePanel);
		controlPanel.add(Box.createVerticalGlue());
		
		JPanel rebaseButtons = prepareItems( addComponentsOnPanel(syncDomainButton), 
											 addComponentsOnPanel(rebaseButton, submitRebaseBox), 
											 addComponentsOnPanel(labelButton, submitLabelBox), 
											 addComponentsOnPanel(addFilesButton));
		
		rebaseButtons.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), 
																"Domain", 
																TitledBorder.CENTER, 
																TitledBorder.TOP, 
																new Font("Arial", Font.PLAIN, 15),
																Color.blue));
		controlPanel.add(rebaseButtons);
		controlPanel.add(Box.createVerticalGlue());
		JPanel tcfgButtons = prepareItems(	addComponentsOnPanel(syncTCFGButton), 
											addComponentsOnPanel(tcfgButton, submitTCFGBox));
		
		tcfgButtons.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), 
																"TCFG", 
																TitledBorder.CENTER, 
																TitledBorder.TOP, 
																new Font("Arial", Font.PLAIN, 15),
																Color.blue));
		controlPanel.add(tcfgButtons);
		controlPanel.setPreferredSize(new Dimension(10, 10));
		controlPanel.add(Box.createVerticalGlue());
		
		scrollPane.setMinimumSize(new Dimension(590, 450));
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, controlPanel);

		this.add(splitPane);
	}
	
	private JPanel addComponentsOnPanel(final JComponent ... items){
		JPanel panel = new JPanel();
		for(JComponent component : items){
			panel.add(component);
		}
		return panel;
	}
	
	private JPanel prepareItems(final JComponent ... items){
		JPanel panel = new JPanel();
		GridLayout gridLayout = new GridLayout(items.length, 0, 0, 0); 
		panel.setLayout(gridLayout);
		for(JComponent component : items){
			panel.add(component);
		}
		return panel;
	}
	
	public void showLogMessage(final String message, final MessageType messageType){
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {		
				switch(messageType){
				case OK		: 
					try {
						doc.insertString( doc.getLength(), message + "\n\r", ok );
					} catch (BadLocationException e1) {
						e1.printStackTrace();
					}
					break;
				case DEBUG	: 
					try {
						doc.insertString( doc.getLength(), message + "\n\r", debug );
					} catch (BadLocationException e1) {
						e1.printStackTrace();
					}
					break;
				case ERROR	: 
					try {
						doc.insertString( doc.getLength(), message + "\n\r", error );
					} catch (BadLocationException e1) {
						e1.printStackTrace();
					}
					break;
				case INFO	:
					try {
						doc.insertString( doc.getLength(), message + "\n\r", info );
					} catch (BadLocationException e1) {
						e1.printStackTrace();
					}
					break;
				default		:
					try {
						doc.insertString( doc.getLength(), message + "\n\r", error );
					} catch (BadLocationException e1) {
						e1.printStackTrace();
					}	
					break;
				}
				logsField.updateUI();
				JScrollBar vertScrol = scrollPane.getVerticalScrollBar();
				int max = 0;
				try{
					max = vertScrol.getMaximum();
				}
				catch (Exception e) {}
				if(max != 0){
					vertScrol.setValue(max);
				}
				logsField.setCaretPosition(logsField.getDocument().getLength());	
		}});
	}
	
	/**
	 * Block/unblock GUI
	 * 
	 * @param flag - if true  -> block,
	 * 					false -> unblock 				
	 */
	public void blockGUI(boolean flag){
		tcfgButton.setEnabled(!flag);
		rebaseButton.setEnabled(!flag);
		labelButton.setEnabled(!flag);
		addFilesButton.setEnabled(!flag);
		wicomeLabelField.setEnabled(!flag);
		syncDomainButton.setEnabled(!flag);
		syncTCFGButton.setEnabled(!flag);
		submitLabelBox.setEnabled(!flag);
		submitRebaseBox.setEnabled(!flag);
		submitTCFGBox.setEnabled(!flag);
//		buildRebaseButton.setEnabled(!flag);
//		buildTCFGButton.setEnabled(!flag);
	}
}
