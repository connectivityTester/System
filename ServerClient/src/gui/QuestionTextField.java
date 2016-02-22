package gui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import common.SystemConstants;

@SuppressWarnings("serial")
public class QuestionTextField extends JTextField{
	
	public QuestionTextField(String titleName){
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), titleName, TitledBorder.LEFT, TitledBorder.TOP, SystemConstants.font, Color.blue));
		this.setHorizontalAlignment(JTextField.CENTER);
	}
}
