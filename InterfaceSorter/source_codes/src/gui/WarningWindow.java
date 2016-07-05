package gui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import Constants.Constants;


public class WarningWindow extends javax.swing.JWindow {
	
	private JLabel messageLabel;
	private JLabel gifLabel;
	
	public WarningWindow(JFrame parentFrame){
		super(parentFrame);
		this.messageLabel = new JLabel();
		this.gifLabel = new JLabel();
		this.layoutComponent();
		this.setLocationRelativeTo(parentFrame);
	}
	
	private void layoutComponent(){
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder(),"", TitledBorder.CENTER, TitledBorder.TOP, Constants.getViewFont(), Color.blue));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		this.messageLabel.setForeground(Color.BLUE);
		this.messageLabel.setFont(Constants.getViewFont());
		this.messageLabel.setAlignmentX(this.messageLabel.CENTER_ALIGNMENT);
		this.gifLabel.setAlignmentX(this.gifLabel.CENTER_ALIGNMENT);
		panel.add(messageLabel);
		panel.add(Box.createVerticalStrut(10));
		panel.add(gifLabel);
		this.add(panel);
		this.setAlwaysOnTop(true);
		this.setSize(300,210);
	}


	public void setMessage(String messageLabel) 	{ this.messageLabel.setText(messageLabel); }
	public void setGifIcon(ImageIcon gifLabel) 		{ this.gifLabel.setIcon(gifLabel);         }

}
