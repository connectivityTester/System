package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;


public class ResultBinariesView extends JPanel{
	
	public ResultBinariesView (List<BinaryLabel> data){
		for(BinaryLabel label : data){
			this.add(label);
		}
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setVisible(true);
	}
	
	public void setData(List<BinaryLabel> data){
		this.clearData();
		for(BinaryLabel label : data){
			this.add(label);
		}
	}
	
	public void clearData(){
		for(Component component :this.getComponents()){
			this.remove(component);
		}
	}

	

}
