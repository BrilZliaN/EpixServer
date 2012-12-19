package org.Epixcrafted.EpixServer.mc.threads;

import org.Epixcrafted.EpixServer.EpixServer;

public class TickCounter extends Thread implements Runnable {
	
	private volatile static long currentTick = 0;
	private volatile static long startMillis = 0;
	private volatile static long sysMillis = 0;
	private volatile static float tps = 0;
	private EpixServer server;
	
	public TickCounter(EpixServer server) {
		this.server = server;
		this.setName("Tick counter thread");
		this.setDaemon(true);
	}
	
	@Override
	public void run() {
		while (!this.isInterrupted()) {
			if (sysMillis == 0) sysMillis = System.currentTimeMillis();
			if (startMillis == 0) startMillis = sysMillis;
			long currTimeMillis = System.currentTimeMillis();
			if (currTimeMillis-sysMillis >= 50) {
				tps = currentTick / ((currTimeMillis - startMillis)/1000F);
				currentTick++;
				new UpdateExecutor(server).start();
				sysMillis = currTimeMillis;
			}
			try {
				sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public synchronized static long getCurrentTick() {
		return currentTick;
	}
	
	public synchronized static float getTicksPerSecond() {
		return ((int)(tps*100)/100F);
	}

	public synchronized static long getUptime() {
		return System.currentTimeMillis() - startMillis;
	}

}
