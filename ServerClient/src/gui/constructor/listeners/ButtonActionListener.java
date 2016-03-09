package gui.constructor.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import types.ControlButtonsTypes;

abstract class ButtonActionListener implements ActionListener{
	protected final ListPanel listPanel;
	protected final ControlButtonsTypes type;

	public ButtonActionListener(ListPanel listPanel, ControlButtonsTypes type){
		this.listPanel = listPanel;
		this.type = type;
	}

	@Override
	public abstract void actionPerformed(ActionEvent e);
}
