package org.Epixcrafted.EpixServer.chat.commands;

import org.Epixcrafted.EpixServer.chat.CommandSender;

public class CommandStop implements Command {

	@Override
	public String getCommandName() {
		return "stop";
	}

	@Override
	public String[] getAliases() {
		return new String[0];
	}

	@Override
	public boolean executeCommand(CommandSender sender, String[] args) {
		sender.getServer().shutdown();
		return true;
	}

}
