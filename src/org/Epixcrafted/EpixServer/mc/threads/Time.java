package org.Epixcrafted.EpixServer.mc.threads;

public class Time extends Thread implements Runnable {
	
	private int currentTime;
	private long sysMillis = 0;
	
	public Time() {
		//currentTime = EpixServer.worlds.get(0);
		currentTime = 6000;
	}
	
	public synchronized int getTime() {
		return currentTime;
	}
	
	@Override
	public void run() {
		while (!this.isInterrupted()) {
			if (sysMillis == 0) sysMillis = System.currentTimeMillis();
			if (currentTime >= 24000) currentTime = 0;
			long currTimeMillis = System.currentTimeMillis();
			if (currTimeMillis-sysMillis >= 1000) {
				currentTime += 20;
				sysMillis = currTimeMillis;
			}
			try {
				sleep(500);
			} catch (InterruptedException e) {
				this.interrupt();
			}
		}
	}

}
