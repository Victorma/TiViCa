package controller.commands;

import controller.ControllerParams;
import controller.ControllerResponse;

public interface Command {

	public ControllerResponse execute(ControllerParams params);
}
