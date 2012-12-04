package org.Epixcrafted.EpixServer.mc.threads;

import org.Epixcrafted.EpixServer.EpixServer;
import org.Epixcrafted.EpixServer.mc.entity.EntityPlayer;
import org.Epixcrafted.EpixServer.mc.entity.Player;

public class TimeSenderThread extends Thread implements Runnable {
	
	@Override
	public void run() {
		while(!this.isInterrupted()) {
			synchronized(EpixServer.players) {
				for (EntityPlayer player : EpixServer.players) {
					((Player)player).synchronizeWorldTime(EpixServer.getTime());
				}
			}
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				this.interrupt();
			}
		}
	}

}
