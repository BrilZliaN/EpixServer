package org.Epixcrafted.EpixServer.engine;

public interface Server {

	public void start();
	
	public void shutdown();
	
	public String getMinecraftVersion();
	
	public int getOnlinePlayerCount();
	
	public String[] getOnlinePlayers();
	
	public int getMaximumPlayers();

}
