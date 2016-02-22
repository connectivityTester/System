package gui;

import java.awt.event.ActionEvent;

import test.Variable;
import types.ControlButtonsTypes;

public class AddVariableListener extends ButtonActionListener{

	public AddVariableListener(ListPanel listPanel, ControlButtonsTypes type) {
		super(listPanel, type);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		VariableItem newVariableItem = new VariableItem();
		Variable variable = newVariableItem.getVariable();
		if(!variable.getName().isEmpty() && !variable.getValue().isEmpty())
		{
			this.listPanel.addNewItem(newVariableItem);
		}
	}

}
