package gui.runner;

import java.awt.Color;
import java.util.Objects;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import common.SystemConstants;
import types.MessageLogTypes;

@SuppressWarnings("serial")
public class LogPanel extends JPanel{
	private JTextPane logs;
	private JButton clearLogs;
	private JScrollPane scroll;
	private StyledDocument doc;
	private SimpleAttributeSet header;
	private SimpleAttributeSet info;
	private SimpleAttributeSet error;
	private SimpleAttributeSet skip;
	
	public LogPanel (){
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Execution logs", TitledBorder.CENTER, TitledBorder.TOP, SystemConstants.font, Color.BLUE));
		this.logs = new JTextPane();
		this.logs.setBackground(new Color(224, 224, 224));
		//this.logs.setFont(GUIConstants.logFont);
		this.logs.setAlignmentX(CENTER_ALIGNMENT);
		this.clearLogs = new JButton("Clear logs");
		this.clearLogs.setAlignmentX(CENTER_ALIGNMENT);
		this.clearLogs.addActionListener((event) ->	this.logs.setText(""));
		this.scroll = new JScrollPane(this.logs, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.scroll.setAutoscrolls(true);
		this.add(this.scroll);
		this.add(this.clearLogs);	
		this.doc = this.logs.getStyledDocument();
		this.createAttibutes();
		DefaultCaret caret = (DefaultCaret)this.logs.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
	}
	
	private void createAttibutes() {
		this.header = new SimpleAttributeSet();
		StyleConstants.setForeground(this.header, Color.BLUE);
		StyleConstants.setBold(this.header, true);
		
		this.info = new SimpleAttributeSet();
		StyleConstants.setForeground(this.info, new Color(0, 168, 0));
		StyleConstants.setBold(this.info, true);
		
		this.error = new SimpleAttributeSet();
		StyleConstants.setForeground(this.error, Color.RED);
		StyleConstants.setBold(this.error, true);
		
		this.skip = new SimpleAttributeSet();
		StyleConstants.setForeground(this.skip, new Color(105, 0, 210));
		StyleConstants.setBold(this.skip, true);
	}

	public void addLog(final String message, final MessageLogTypes type){
		Objects.requireNonNull(message);
		Objects.requireNonNull(type);
		
		try {
			switch(type){
				case INFO 	: 	doc.insertString( doc.getLength(), message + "\n\r", this.info );	break;
				case HEADER	:	doc.insertString( doc.getLength(), message + "\n\r", this.header );	break;
				case ERROR	:	doc.insertString( doc.getLength(), message + "\n\r", this.error );	break;
				case SKIP	:	doc.insertString( doc.getLength(), message + "\n\r", this.skip );	break;
			}
		} catch (BadLocationException e) { e.printStackTrace();	}
		JScrollBar vertScrol = this.scroll.getVerticalScrollBar();
		int max = 0;
		try{
			max = vertScrol.getMaximum();
		}
		catch (NullPointerException e) {}
		if(max != 0){
			vertScrol.setValue(max);
		}
		this.logs.setCaretPosition(this.logs.getDocument().getLength());		
	}
}
