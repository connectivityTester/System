package gui;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.TitledBorder;
import common.SystemConstants;
import types.FunctionButtonType;

@SuppressWarnings("serial")
public class ConstructorPanel extends JPanel {
	private final ContentPanel variablesPanel;
	private final ContentPanel actionsPanel;
	private final JButton saveButton = new JButton("Save");
	private final JButton runButton = new JButton("Run");
	private final JButton clearButton = new JButton("Clear all data");
	
	public ConstructorPanel(){
		this.setTitleBorderName("New test");
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		this.variablesPanel = new ContentPanel("Variables", FunctionButtonType.VARIABLES);
		this.actionsPanel = new ContentPanel("Actions", FunctionButtonType.ACTIONS);
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, this.variablesPanel, this.actionsPanel);
		splitPane.setAlignmentX(JSplitPane.CENTER_ALIGNMENT);
		this.add(splitPane);
		this.saveButton.setIcon(new ImageIcon(SystemConstants.getSaveImage()));
		this.runButton.setIcon(new ImageIcon(SystemConstants.getRunImage()));
		this.clearButton.setIcon(new ImageIcon(SystemConstants.getClearImage()));
		JPanel buttonPanel = new JPanel();
		buttonPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(this.saveButton);
		buttonPanel.add(Box.createHorizontalStrut(5));
		buttonPanel.add(this.runButton);
		buttonPanel.add(Box.createHorizontalStrut(5));
		buttonPanel.add(this.clearButton);
		buttonPanel.add(Box.createHorizontalGlue());
		this.add(buttonPanel);
		this.addListeners();
	}
	
	private void addListeners(){
		this.clearButton.addActionListener((event) -> {
			variablesPanel.removeAllItems();
			actionsPanel.removeAllItems();
		});
	}
	
	private void setTitleBorderName(String name){
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), name, TitledBorder.CENTER, TitledBorder.TOP, SystemConstants.font, Color.blue));
	}
}
