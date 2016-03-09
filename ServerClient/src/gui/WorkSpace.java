package gui;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import io.FileSystemManager;
import starters.DeviceSourceRunManager;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import common.DeviceSource;
import gui.constructor.listeners.ConstructorPanel;
import gui.runner.DeviceSourcePanel;
import gui.runner.LogPanel;
import gui.runner.TestDirectoryTree;
import types.DeviceSourceStatus;
import types.GuiModes;
import types.MessageLogTypes;
import utils.Logger;

@SuppressWarnings("serial")
public class WorkSpace extends JFrame{

	private final DeviceSourcePanel deviceSourcePanel;
	private final TestDirectoryTree testDirectoryTree;
	private JPanel workPanel;
	private GuiModes currentMode = GuiModes.EXECUTION_MODE;

	private JSplitPane splitPane;
	
	public WorkSpace(FileSystemManager fileSystemManager){
		super("Connectivity testing tool");
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		this.deviceSourcePanel = new DeviceSourcePanel();
		this.testDirectoryTree = new TestDirectoryTree(fileSystemManager);
		this.testDirectoryTree.setAlignmentX(LEFT_ALIGNMENT);
		
		this.layoutObjects();
		this.setVisible(true);
		this.setMinimumSize(new Dimension(800, 500));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setJMenuBar(new MenuBar(this));
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				DeviceSourceRunManager.getInstance().finishAllDeviceSources();
				Logger.closeLogger();
			}
		});
		Logger.setWorkSpace(this);
	}
	
	private void layoutObjects(){		
		JPanel horizontalPanel  = new JPanel();
		horizontalPanel.setLayout(new BoxLayout(horizontalPanel, BoxLayout.X_AXIS));
		JScrollPane leftComponent = new JScrollPane(this.testDirectoryTree, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		leftComponent.setMinimumSize(new Dimension(200, 200));
		this.splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		this.splitPane.setLeftComponent(leftComponent);
		this.setMode(this.currentMode);
		this.workPanel.setMinimumSize(new Dimension(400, 200));
		horizontalPanel.add(splitPane);
		this.add(horizontalPanel);
		this.add(this.deviceSourcePanel);
	}

	public void showLog(String messageLog, MessageLogTypes messageType){
		if(this.currentMode == GuiModes.EXECUTION_MODE){
			((LogPanel)this.workPanel).addLog(messageLog, messageType);
		}
	}

	public void setMode(GuiModes newMode) {
		this.currentMode = newMode;
		switch(newMode){
			case CONSTRUCTOR_MODE:	
				this.workPanel = new ConstructorPanel();
				this.deviceSourcePanel.setVisible(false);
				break;
			case EXECUTION_MODE:	
				this.workPanel = new LogPanel();
				this.deviceSourcePanel.setVisible(true);
				break;
		}
		this.splitPane.setRightComponent(this.workPanel);
	}

	public void updateDeviceStatus(DeviceSource deviceSource, DeviceSourceStatus status) {
		this.deviceSourcePanel.updateDeviceStatus(deviceSource, status);
	}
}
