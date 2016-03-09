package gui.constructor.listeners;

import java.awt.event.ActionEvent;

import types.ControlButtonsTypes;

public class UpListener extends ButtonActionListener{

	public UpListener(ListPanel listPanel, ControlButtonsTypes type) {
		super(listPanel, type);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.listPanel.moveUpItem();
	}

}
