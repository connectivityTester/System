package test.datahandlers;

import java.util.List;
import connections.ConnenctionController;
import test.actions.Action;
import test.actions.ActionResult;
import test.actions.Variable;
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
	public ActionResult handleActionData(final List<Variable> testVariables, final Action action) {
		return ConnenctionController.getInstatnce().handleTestData(action, testVariables, DataPackerTypes.JSON_DATA_PACKER);
	}

}
