package org.Epixcrafted.EpixServer.chat.commands;

import org.Epixcrafted.EpixServer.chat.CommandSender;

public class CommandList implements Command {

	@Override
	public String getCommandName() {
		return "list";
	}
	
	@Override
	public String[] getAliases() {
		return new String[] {"l"};
	}

	@Override
	public boolean executeCommand(CommandSender sender, String[] args) {
		String onlinePlayers = "";
		for (String player : sender.getServer().getOnlinePlayers()) {
			if (onlinePlayers == "") {
				onlinePlayers += player;
			} else {
				onlinePlayers += " " + player;
			}
		}
		sender.sendMessage("There are " + sender.getServer().getOnlinePlayerCount() + "/" + sender.getServer().getMaximumPlayers() + " players online:");
		sender.sendMessage(onlinePlayers);
		return true;
	}

}
