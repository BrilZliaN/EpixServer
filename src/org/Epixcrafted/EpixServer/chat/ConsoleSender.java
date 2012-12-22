package org.Epixcrafted.EpixServer.chat;

import org.Epixcrafted.EpixServer.EpixServer;
import org.Epixcrafted.EpixServer.engine.Server;

public class ConsoleSender implements CommandSender {
	
	private static ConsoleSender instance;
	
	private Server server;
	
	public ConsoleSender(Server server) {
		if (instance != null) {
			throw new RuntimeException();
		}
		instance = this;
		this.server = server;
	}

	@Override
	public String getName() {
		return "CONSOLE";
	}
	
	@Override
	public Server getServer() {
		return server;
	}

	@Override
	public void sendMessage(String message) {
        ((EpixServer)server).getLogger().info(cutColourCodes(message));
	}
	
	public String cutColourCodes(String string) {
		for (Colour c : Colour.values()) {
			string = string.replace(c.toString(), "");
		}
		return string;
	}

}
