package org.Epixcrafted.EpixServer.chat;

import org.Epixcrafted.EpixServer.Main;

public class ConsoleSender implements CommandSender {

	@Override
	public String getName() {
		return "CONSOLE";
	}

	@Override
	public void sendMessage(String message) {
		Main.getServer().getLogger().info(message);
	}

}
