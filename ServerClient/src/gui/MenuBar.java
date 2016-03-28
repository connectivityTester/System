package gui;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JRadioButtonMenuItem;

import types.GuiModes;
import utils.Utils;

@SuppressWarnings("serial")
public class MenuBar extends JMenuBar{
	private final JRadioButtonMenuItem  constructorMode;
	private final JRadioButtonMenuItem  testRunnerMode;
	private final WorkSpace workSpace;
	
	public MenuBar(final WorkSpace workSpace)
	{
		Utils.requireNonNull(workSpace);
		
		this.workSpace = workSpace;
		this.constructorMode = new JRadioButtonMenuItem("Constuctor mode");
		this.testRunnerMode = new JRadioButtonMenuItem("Execution mode");
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(this.constructorMode);
		buttonGroup.add(this.testRunnerMode);
		this.testRunnerMode.setSelected(true);
		JMenu modeMenu = new JMenu("Mode");
		modeMenu.add(this.constructorMode);
		modeMenu.add(this.testRunnerMode);
		this.add(modeMenu);
		this.setListeners();
	}
	
	private void setListeners(){
		this.constructorMode.addActionListener((event) -> workSpace.setMode(GuiModes.CONSTRUCTOR_MODE));
		this.testRunnerMode.addActionListener((event) -> workSpace.setMode(GuiModes.EXECUTION_MODE));
	}
	
}
