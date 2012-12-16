package org.Epixcrafted.EpixServer.chat;

import org.Epixcrafted.EpixServer.engine.Server;

public class ConsoleSender implements CommandSender {

	@Override
	public String getName() {
		return "CONSOLE";
	}
	
	@Override
	public Server getServer() {
		throw new RuntimeException();
	}

	@Override
	public void sendMessage(String message) {
		throw new RuntimeException();
	}

}
