package controller;

import controller.imp.ApplicationControllerImp;

public abstract class ApplicationController {
	
	/**
	 * singleton instance
	 */
	private static ApplicationController instance;

	/**
	 * Singleton instance request
	 * @return always the same instance for every getInstance call
	 */
	public static ApplicationController getInstance() {
		if(instance == null)
			instance = new ApplicationControllerImp();
		
		return instance;
	}
	
	/**
	 * The main method in the application used to make the requests from the view and
	 * update the application logic.
	 * @param action string with the action (preferible used from ControllerActions)
	 * @param params the params required described in ControllerActions
	 * @return a controller response described in ControllerActions
	 */
	public abstract ControllerResponse execute(String action, ControllerParams params);
}
