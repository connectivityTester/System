package gui;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import common.SystemConstants;
import types.ControlButtonsTypes;
import types.FunctionButtonType;

@SuppressWarnings("serial")
public class ButtonPanel extends JPanel{
	
	private final JButton firstButton;
	private final JButton secondButton;
	
	public ButtonPanel(int orientation,
					ControlButtonsTypes controlType, FunctionButtonType functionType, 
					String firstButtonName, String secondButtonName, 
					ListPanel listPanel)
	{
		this.setLayout(new BoxLayout(this, orientation));
		if(controlType == ControlButtonsTypes.ADD_DELETE){
			this.firstButton = new JButton(firstButtonName, new ImageIcon(SystemConstants.getAddImage()));
			this.secondButton = new JButton(secondButtonName, new ImageIcon(SystemConstants.getDeleteImage()));			
		}
		else{
			this.firstButton = new JButton(firstButtonName, new ImageIcon(SystemConstants.getUpImage()));
			this.secondButton = new JButton(secondButtonName, new ImageIcon(SystemConstants.getDownImage()));
		}
		if(orientation == BoxLayout.X_AXIS){
			this.add(Box.createHorizontalGlue());
		}
		else{
			this.firstButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
			this.secondButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
			this.setAlignmentX(ButtonPanel.CENTER_ALIGNMENT);
			this.add(Box.createVerticalGlue());
		}
		this.add(this.firstButton);
		if(orientation == BoxLayout.X_AXIS){
			this.add(Box.createHorizontalStrut(5));
		}
		else{
			this.add(Box.createVerticalStrut(5));
		}
		this.add(this.secondButton);
		if(orientation == BoxLayout.X_AXIS){
			this.add(Box.createHorizontalGlue());
		}
		else{
			this.add(Box.createVerticalGlue());
		}		
		if(controlType == ControlButtonsTypes.ADD_DELETE){
			if(functionType == FunctionButtonType.VARIABLES){
				this.firstButton.addActionListener(new AddVariableListener(listPanel, controlType));
			}
			else if(functionType == FunctionButtonType.ACTIONS){
				this.firstButton.addActionListener(new AddActionListener(listPanel, controlType));
			}
			this.secondButton.addActionListener(new DeleteListener(listPanel, controlType));
		}
		else if(controlType == ControlButtonsTypes.UP_DOWN){
			this.firstButton.addActionListener(new UpListener(listPanel, controlType));
			this.secondButton.addActionListener(new DownListener(listPanel, controlType));
		}
	}

	public void enableFirstButton(boolean flag) {
		this.firstButton.setEnabled(flag);
	}

	public void enableSecondButton(boolean flag) {
		this.secondButton.setEnabled(flag);
		
	}
}
