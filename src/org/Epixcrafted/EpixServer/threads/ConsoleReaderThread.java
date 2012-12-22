package org.Epixcrafted.EpixServer.threads;

import java.util.Scanner;

import org.Epixcrafted.EpixServer.EpixServer;
import org.Epixcrafted.EpixServer.chat.CommandSender;

public class ConsoleReaderThread extends Thread implements Runnable {
	
	private final CommandSender console;
	private final Scanner scanner = new Scanner(System.in);
	
	public ConsoleReaderThread(CommandSender console) {
		this.console = console;
		this.setDaemon(true);
		this.setName("Console reader thread");
	}
	
	@Override
	public void run() {
		while(true) {
			String read = scanner.nextLine();
			if (read.startsWith("/")) read = read.substring(1);
			try {
				((EpixServer)console.getServer()).getCommandList().executeCommand(read, console);
			} catch (Exception e) {
				((EpixServer)console.getServer()).getLogger().info("Caught exception while executing command \"/" + read + "\": " + e.getMessage());
			}
		}
	}

}