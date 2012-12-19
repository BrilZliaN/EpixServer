package org.Epixcrafted.EpixServer.chat.commands;

import org.Epixcrafted.EpixServer.chat.Colour;
import org.Epixcrafted.EpixServer.chat.CommandSender;
import org.Epixcrafted.EpixServer.mc.threads.TickCounter;

public class CommandTPS implements Command {

	@Override
	public String getCommandName() {
		return "tps";
	}

	@Override
	public String[] getAliases() {
		return new String[] {"tick", "speed", "lag"};
	}

	@Override
	public boolean executeCommand(CommandSender sender, String[] args) {
		float tps = TickCounter.getTicksPerSecond();
		long ticks = TickCounter.getCurrentTick();
		long uptime = TickCounter.getUptime();
		String status = tps < 19F ? (tps < 15F ? Colour.DARK_RED + "BAD" : Colour.YELLOW + "NORMAL") : Colour.DARK_GREEN + "GOOD";
		sender.sendMessage(Colour.PURPLE + "[EpixServer] " + Colour.LIGHT_GREEN + "TPS: " + tps + "/20 " + Colour.CYAN + "[" + status + Colour.CYAN + "]");
		sender.sendMessage(Colour.PURPLE + "[EpixServer] " + Colour.LIGHT_GREEN + "Current tick/Current uptime: " + Colour.CYAN + "[" + Colour.GOLD + ticks + " ticks" + Colour.CYAN + "/" + Colour.GOLD + uptime + " ms" + Colour.CYAN + "]");
		return true;
	}

}
