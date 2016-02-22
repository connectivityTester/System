package gui;

import java.awt.event.ActionEvent;

import types.ControlButtonsTypes;

public class DownListener extends ButtonActionListener{

	public DownListener(ListPanel listPanel, ControlButtonsTypes type) {
		super(listPanel, type);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.listPanel.moveDownItem();
	}

}
