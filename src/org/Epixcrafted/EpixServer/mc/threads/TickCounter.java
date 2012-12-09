package org.Epixcrafted.EpixServer.mc.threads;

import org.Epixcrafted.EpixServer.Main;

public class TickCounter extends Thread implements Runnable {
	
	private static long currentTick = 0;
	private long sysMillis = 0;
	
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
			try {
				sleep(50);
			} catch (InterruptedException e) {
				this.interrupt();
			}
		}
	}
	
	public void update() {
		Main.getServer().getSessionListClass().update();
	}
	
	public synchronized static long getCurrentTick() {
		return currentTick;
	}

}
