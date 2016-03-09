package gui.constructor.listeners;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import common.SystemConstants;
import types.ControlButtonsTypes;
import types.FunctionButtonType;

@SuppressWarnings("serial")
public class ContentPanel extends JPanel {
	
	private final ButtonPanel addDeleteButtons;
	private final ButtonPanel upDownButtons;
	private final ListPanel listPanel;
	
	public ContentPanel(String titleName, FunctionButtonType functionType){
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), titleName, TitledBorder.CENTER, TitledBorder.TOP, SystemConstants.font, Color.blue));
		this.setLayout(new BorderLayout());
		this.listPanel = new ListPanel();
		this.addDeleteButtons = new ButtonPanel(BoxLayout.X_AXIS,
				ControlButtonsTypes.ADD_DELETE, functionType,
				" Add ", "Delete", 	this.listPanel);
		this.upDownButtons = new ButtonPanel(BoxLayout.Y_AXIS, 
				ControlButtonsTypes.UP_DOWN, functionType,
				" Up ", "Down", this.listPanel);
		this.listPanel.setUpDownButtonPanel(this.upDownButtons);
		JScrollPane scrollPane = new JScrollPane(this.listPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(addDeleteButtons, BorderLayout.SOUTH);
		this.add(this.upDownButtons, BorderLayout.EAST);
		this.upDownButtons.enableFirstButton(false);
		this.upDownButtons.enableSecondButton(false);
	}

	public void removeAllItems() {
		this.listPanel.removeAllItems();
	}
}
