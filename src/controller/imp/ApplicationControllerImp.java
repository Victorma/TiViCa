package controller.imp;

import controller.ApplicationController;
import controller.ControllerParams;
import controller.ControllerResponse;
import controller.commands.Command;
import controller.commands.factory.CommandFactory;

public class ApplicationControllerImp extends ApplicationController {	
	
	@Override
	public ControllerResponse execute(String action, ControllerParams params) {
		Command command = CommandFactory.getInstance().createCommand(action);
		return command.execute(params);
	}

}
