package org.Epixcrafted.EpixServer.chat;

import org.Epixcrafted.EpixServer.engine.Server;

public interface CommandSender {

	public String getName();
	
	public Server getServer();
	
	public void sendMessage(String message);
}
