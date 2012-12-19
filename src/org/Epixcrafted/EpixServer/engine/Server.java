package org.Epixcrafted.EpixServer.engine;

public interface Server {

	public void start();
	
	public void shutdown();
	
	public String getMinecraftVersion();
	
	public int getOnlinePlayers();
	
	public int getMaximumPlayers();

}
