package org.Epixcrafted.EpixServer.mc.threads;

import org.Epixcrafted.EpixServer.EpixServer;

public class TickCounter extends Thread implements Runnable {
	
	private volatile static long currentTick = 0;
	private volatile long sysMillis = 0;
	private EpixServer server;
	
	public TickCounter(EpixServer server) {
		this.server = server;
	}
	
	@Override
	public void run() {
		while (!this.isInterrupted()) {
			if (sysMillis == 0) sysMillis = System.currentTimeMillis();
			long currTimeMillis = System.currentTimeMillis();
			if (currTimeMillis-sysMillis >= 50) {
				currentTick += 1;
				update();
				sysMillis = currTimeMillis;
			}
		}
	}
	
	public void update() {
		synchronized(server.getSessionListClass()) {
			server.getSessionListClass().update();
		}
	}
	
	public synchronized static long getCurrentTick() {
		return currentTick;
	}

}
