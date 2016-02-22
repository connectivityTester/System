package gui;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultListModel;
import javax.swing.JList;

@SuppressWarnings({ "rawtypes", "serial" })
public class ListPanel extends JList{
	private final DefaultListModel model;
	private ButtonPanel upDownButtons;
	
	@SuppressWarnings("unchecked")
	public ListPanel(){
		this.model = new DefaultListModel();
		this.setModel(this.model);
		this.setMinimumSize(new Dimension(100, 100));
		this.setVisible(true);
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2 && getSelectedIndex() != -1){
					VariableItem variableItem = (VariableItem) model.getElementAt(getSelectedIndex());
					variableItem.showDialog();
				}
			}
		});
		
		this.addListSelectionListener((e) -> checkSelectedValue());
	}
	
	private void checkSelectedValue(){
		int selectedItems[] = getSelectedIndices();
		if(selectedItems.length != 0){
			if(selectedItems[0] == 0){
				upDownButtons.enableFirstButton(false);
			}
			else{
				upDownButtons.enableFirstButton(true);
			}
			if(selectedItems[selectedItems.length-1] == model.size()-1){
				upDownButtons.enableSecondButton(false);
			}
			else{
				upDownButtons.enableSecondButton(true);
			}
		}
	}
	
	public void setUpDownButtonPanel (ButtonPanel upDownButtons){
		this.upDownButtons = upDownButtons;
	}
	
	@SuppressWarnings("unchecked")
	public void addNewItem(Object item){
		this.model.addElement(item);
		if(this.model.getSize() > 1){
			this.upDownButtons.enableFirstButton(true);
		}
		this.setSelectedIndex(this.model.getSize()-1);
	}

	public void deleteSelectedItem() {
		int selectedItems[] = this.getSelectedIndices();
		if(selectedItems.length != -1){
			this.model.removeRange(selectedItems[0], selectedItems[selectedItems.length-1]);
		}
	}

	@SuppressWarnings("unchecked")
	public void moveUpItem() {
		int indexArray[] = this.getSelectedIndices();
		for(int index : indexArray){
			Object movedObject = this.model.getElementAt(index);
			this.model.setElementAt(this.model.getElementAt(index-1), index);
			this.model.setElementAt(movedObject, index-1);
		}
		for(int index = 0 ; index < indexArray.length; ++index){
			indexArray[index] = indexArray[index] - 1;
		}
		this.setSelectedIndices(indexArray);
	}

	@SuppressWarnings("unchecked")
	public void moveDownItem() {
		int indexArray[] = this.getSelectedIndices();
		for(int index : indexArray){
			Object movedObject = this.model.getElementAt(index);
			this.model.setElementAt(this.model.getElementAt(index+1), index);
			this.model.setElementAt(movedObject, index+1);
		}
		for(int index = 0 ; index < indexArray.length; ++index){
			indexArray[index] = indexArray[index] + 1;
		}
		this.setSelectedIndices(indexArray);
	}

	public void removeAllItems() {
		this.model.removeAllElements();
	}
}
