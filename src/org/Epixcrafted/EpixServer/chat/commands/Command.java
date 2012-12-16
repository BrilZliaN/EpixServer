package org.Epixcrafted.EpixServer.chat.commands;

import org.Epixcrafted.EpixServer.chat.CommandSender;

public interface Command {
	
	public String getCommandName();
	
	public String[] getAliases();
	
	public boolean executeCommand(CommandSender sender, String[] args);

}
