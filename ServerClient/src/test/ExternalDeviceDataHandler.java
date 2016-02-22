package test;

import java.util.List;
import connections.ConnenctionController;
import types.DataPackerTypes;

public class ExternalDeviceDataHandler implements ActionDataHanlder{
	
	private static ExternalDeviceDataHandler externalDeviceDataHandler;
	
	public static ExternalDeviceDataHandler getInstance(){
		if(externalDeviceDataHandler == null){
			externalDeviceDataHandler = new ExternalDeviceDataHandler();
		}
		return externalDeviceDataHandler;
	}
	
	@Override
	public ActionResult handleActionData(List<Variable> testVariables, Action action) {
		return ConnenctionController.getInstatnce().handleTestData(action, testVariables, DataPackerTypes.JSON_DATA_PACKER);
	}

}
