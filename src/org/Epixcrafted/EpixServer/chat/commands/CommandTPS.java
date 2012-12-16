package org.Epixcrafted.EpixServer.chat.commands;

import org.Epixcrafted.EpixServer.EpixServer;
import org.Epixcrafted.EpixServer.chat.Colour;
import org.Epixcrafted.EpixServer.chat.CommandSender;
import org.Epixcrafted.EpixServer.mc.threads.TickCounter;

public class CommandTPS implements Command {

	@Override
	public String getCommandName() {
		return "tickspersecond";
	}

	@Override
	public String[] getAliases() {
		return new String[] {"tps", "tick", "speed", "lag"};
	}

	@Override
	public boolean executeCommand(CommandSender sender, String[] args) {
		float tps = TickCounter.getTicksPerSecond();
		long ticks = TickCounter.getCurrentTick();
		String status = tps < 15F ? (tps < 10F ? Colour.DARK_RED + "BAD" : Colour.YELLOW + "NORMAL") : Colour.DARK_GREEN + "GOOD";
		sender.sendMessage(Colour.PURPLE + "[EpixServer] " + Colour.LIGHT_GREEN + "TPS: " + tps + "/20 " + Colour.CYAN + "[" + status + Colour.CYAN + "]");
		((EpixServer)sender.getServer()).getLogger().info("TPS: " + tps + " (total ticks: " + ticks + ")");
		return true;
	}

}
