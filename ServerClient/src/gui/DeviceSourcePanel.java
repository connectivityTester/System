package gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import common.DeviceSource;
import common.SystemConfig;
import common.SystemConstants;
import types.DeviceSourceStatus;
import types.SourceTypes;

@SuppressWarnings("serial")
public class DeviceSourcePanel extends JPanel{
	private final List<DeviceSourceView> deviceSourceViews = new ArrayList<>();
	
	public DeviceSourcePanel(){
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Device connections", TitledBorder.CENTER, TitledBorder.TOP, SystemConstants.font, Color.blue));
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		for(DeviceSource deviceSource : SystemConfig.getInstance().getDeviceSources()){
			if(deviceSource.getSourceType() == SourceTypes.EXTERNAL_SOURCE){
				DeviceSourceView deviceSourceView = new DeviceSourceView(deviceSource);
				this.deviceSourceViews.add(deviceSourceView);
				this.add(deviceSourceView);
			}
		}
	}
	
	public void updateDeviceStatus(DeviceSource deviceSource, DeviceSourceStatus status){
		for(DeviceSourceView deviceSourceView : this.deviceSourceViews){
			if(deviceSourceView.getDeviceSource().getId() == deviceSource.getId()){
				deviceSourceView.updateDeviceStatus(status);
				break;
			}
		}
	}
}
