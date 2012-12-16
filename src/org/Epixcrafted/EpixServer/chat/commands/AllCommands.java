package org.Epixcrafted.EpixServer.chat.commands;

import java.util.ArrayList;
import java.util.Iterator;

import org.Epixcrafted.EpixServer.chat.CommandSender;

public class AllCommands {
	
	public ArrayList<Command> commands = new ArrayList<Command>();
	
	public AllCommands() {
		addCommand(new CommandVersion());
		addCommand(new CommandList());
		addCommand(new CommandSpawn());
	}
	
	public void addCommand(Command command) {
		commands.add(command);
	}
	
	public boolean executeCommand(String string, CommandSender sender) {
		if (string.contains(" ")) {
			Iterator<Command> ic = commands.iterator();
			while(ic.hasNext()) {
				Command c = ic.next();
				if (c.getCommandName().equalsIgnoreCase(string.split(" ")[0])) {
					String[] args = new String[string.split(" ").length-1];
					for (int i = 1; i < args.length; i++) {
						args[i-1] = string.split(" ")[i];
					}
					return c.executeCommand(sender, args);
				}
				for (String alias : c.getAliases()) {
					if (alias.equalsIgnoreCase(string.split(" ")[0])) {
						String[] args = new String[string.split(" ").length-1];
						for (int i = 1; i < args.length; i++) {
							args[i-1] = string.split(" ")[i];
						}
						return c.executeCommand(sender, args);
					}
				}
			}
		} else { 
			Iterator<Command> ic = commands.iterator();
			while(ic.hasNext()) {
				Command c = ic.next();
				if (c.getCommandName().equalsIgnoreCase(string)) {
					return c.executeCommand(sender, new String[0]);
				}
				for (String alias : c.getAliases()) {
					if (alias.equalsIgnoreCase(string)) {
						return c.executeCommand(sender, new String[0]);
					}
				}
			}
		}
		sender.sendMessage("Unknown command: " + string);
		return false;
	}

}
