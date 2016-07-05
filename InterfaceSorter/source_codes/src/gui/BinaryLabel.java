package gui;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class BinaryLabel extends JLabel{
	
	public BinaryLabel(String binary, Image image){
		this.setIcon(new ImageIcon(image));
		this.setText(binary);
	}
	

}
