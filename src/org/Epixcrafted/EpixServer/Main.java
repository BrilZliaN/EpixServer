package org.Epixcrafted.EpixServer;

public class Main {
	
	private EpixServer server;
	
	public static void main(String[] args) {
		new Main().startServer(args);
	}
	
	public boolean startServer(String[] args) {
		if (server != null) return false;
		(server = new EpixServer()).start();
		return true;
	}

}