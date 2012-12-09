package org.Epixcrafted.EpixServer.chat.commands;

import org.Epixcrafted.EpixServer.chat.Colour;
import org.Epixcrafted.EpixServer.chat.CommandSender;

public class CommandSpawn implements Command {

	@Override
	public String getCommandName() {
		return "spawn";
	}

	@Override
	public boolean executeCommand(CommandSender sender, String[] args) {
		sender.sendMessage(Colour.RED + "Command /" + getCommandName() + " is not currently properly supported.");
		return true;
	}

}
