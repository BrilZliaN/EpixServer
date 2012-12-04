package org.Epixcrafted.EpixServer.chat;

import org.Epixcrafted.EpixServer.EpixServer;

public class ConsoleSender implements CommandSender {

	@Override
	public String getName() {
		return "CONSOLE";
	}

	@Override
	public void sendMessage(String message) {
		EpixServer.info(message);
	}

}
