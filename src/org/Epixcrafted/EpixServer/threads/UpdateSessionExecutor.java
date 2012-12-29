package org.Epixcrafted.EpixServer.threads;

import org.Epixcrafted.EpixServer.EpixServer;

public class UpdateSessionExecutor extends Thread implements Runnable {
	
	private static int threadNum = 0;
	private EpixServer server;

	public UpdateSessionExecutor(EpixServer server) {
		this.server = server;
		this.setName("UpdateSessionExecutor-#" + ++threadNum);
		this.setDaemon(false);
	}
	
	@Override
	public void run() {
		update();
		try {
			finalize();
			interrupt();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		synchronized(server.getSessionListClass()) {
			server.getSessionListClass().update();
		}
	}
}
