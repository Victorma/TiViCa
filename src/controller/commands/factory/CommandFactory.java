package controller.commands.factory;

import controller.commands.Command;
import controller.commands.factory.imp.CommandFactoryImp;

public abstract class CommandFactory {
	
	private static CommandFactory instance;
	
	public static CommandFactory getInstance(){
		if(instance == null)
			instance = new CommandFactoryImp();
		return instance;
	}
	
	public abstract Command createCommand(String action);

}
