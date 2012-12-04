package org.Epixcrafted.EpixServer.chat.commands;

import org.Epixcrafted.EpixServer.chat.Colour;
import org.Epixcrafted.EpixServer.chat.CommandSender;

public class CommandVersion implements Command {

	@Override
	public String getCommandName() {
		return "version";
	}

	@Override
	public boolean executeCommand(CommandSender sender, String[] args) {
		sender.sendMessage(Colour.PURPLE + "[EpixServer] " + Colour.LIGHT_GREEN + "This server runs on EpixServer DevTest (MC 1.4.2)");
		return true;
	}
	

}
