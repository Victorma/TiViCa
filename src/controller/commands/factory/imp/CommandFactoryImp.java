package controller.commands.factory.imp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import controller.commands.Command;
import controller.commands.factory.CommandFactory;

public class CommandFactoryImp extends CommandFactory {

private final String commandFileName = "command.config";
	
	private HashMap<String,Class<? extends Command>> classMap = null;
	
	@SuppressWarnings({ "resource", "unchecked" })
	private Command parseCommand(String evento){
		
		Command com = null;
		
		if(classMap == null){
			try{
				HashMap<String,Class<? extends Command>> temporalClassMap = new HashMap<String,Class<? extends Command>>();
				String absolute = new File("plataforma").getAbsolutePath();
				File file = new File(absolute+"\\carniceria\\TiViCa\\"+commandFileName);
				System.out.println(file.getAbsolutePath());
				Scanner fileReader = new Scanner(file);
				String line;
				while(fileReader.hasNext()){
					line = fileReader.nextLine();
					line = line.trim();
					if(line.charAt(0) != '#' && line.length() != 0){
						String [] tokens = line.split(" "); 
						temporalClassMap.put(tokens[0], (Class<? extends Command>) Class.forName(tokens[tokens.length-1]));
					}
				}
				classMap = temporalClassMap;
			}catch(FileNotFoundException fnfe){
				fnfe.printStackTrace();
				System.err.println("No encontrado "+commandFileName+"!!!");
			} catch (ClassNotFoundException e) {
				System.err.println("Clase no encontrada: " + e.getMessage());
			}
		}
		
		if(classMap != null){
			try {
				com = classMap.get(evento.toString()).newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
		
		return com;
	}
	
	@Override
	public Command createCommand(String action) {
		return parseCommand(action);
	}

}
