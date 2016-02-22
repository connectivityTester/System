package gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import common.DeviceSource;
import common.SystemConstants;
import types.DeviceSourceStatus;

@SuppressWarnings("serial")
public class DeviceSourceView extends JPanel{
	
	private final DeviceSource deviceSource;
	private final JLabel statusLabel;
	private JLabel addressLabel;
	
	public DeviceSourceView(DeviceSource deviceSource) {
		this.deviceSource = deviceSource;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setAlignmentX(CENTER_ALIGNMENT);
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), deviceSource.getName(), TitledBorder.CENTER, TitledBorder.TOP, SystemConstants.font, Color.blue));		
		if(this.deviceSource.getAddress() != null){
			this.addressLabel = new JLabel("  " + this.deviceSource.getAddress() + "  ");
			this.addressLabel.setAlignmentX(CENTER_ALIGNMENT);
			this.addressLabel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Address", TitledBorder.CENTER, TitledBorder.TOP, SystemConstants.font, Color.blue));
			this.add(this.addressLabel);
		}
		this.statusLabel = new JLabel("Not started");
		this.statusLabel.setForeground(Color.RED);
		this.statusLabel.setAlignmentX(CENTER_ALIGNMENT);
		this.statusLabel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Status", TitledBorder.CENTER, TitledBorder.TOP, SystemConstants.font, Color.blue));
		this.statusLabel.setPreferredSize(new Dimension(130, 100));
		this.setPreferredSize(new Dimension(130, 110));
		this.add(this.statusLabel);
	}

	public DeviceSource getDeviceSource() {
		return this.deviceSource;
	}
	
	public void updateDeviceStatus(DeviceSourceStatus status){
		switch(status){
			case CONNECTED: 
				this.statusLabel.setText("Connected");
				this.statusLabel.setForeground(new Color(0, 168, 0));
				break;
			case DISCONNECTED:
				this.statusLabel.setText("Disconnected");
				this.statusLabel.setForeground(Color.RED);
				break;
			case NOT_STARTED:
				this.statusLabel.setText("Not started");
				this.statusLabel.setForeground(Color.RED);
				break;
			case STOPED:
				this.statusLabel.setText("   Stoped  ");
				this.statusLabel.setForeground(Color.RED);
				break;
			case STARTED: 
				this.statusLabel.setText("  Started  ");
				this.statusLabel.setForeground(new Color(0, 168, 0));
				break;
		}
	}
}
