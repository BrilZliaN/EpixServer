package org.Epixcrafted.EpixServer.chat.commands;

import org.Epixcrafted.EpixServer.EpixServer;
import org.Epixcrafted.EpixServer.chat.CommandSender;
import org.Epixcrafted.EpixServer.mc.entity.EntityPlayer;

public class CommandList implements Command {

	@Override
	public String getCommandName() {
		return "list";
	}

	@Override
	public boolean executeCommand(CommandSender sender, String[] args) {
		int onlineCount = 0;
		String onlinePlayers = "";
		synchronized (EpixServer.players) {
			for (EntityPlayer p : EpixServer.players) {
				onlinePlayers += " " + p.getName();
				onlineCount += 1;
			}
		}
		sender.sendMessage("There are " + onlineCount + "/65535 players online:");
		sender.sendMessage(onlinePlayers.substring(1));
		return true;
	}

}
