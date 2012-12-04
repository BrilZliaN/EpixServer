package org.Epixcrafted.EpixServer.engine;

import org.Epixcrafted.EpixServer.EpixServer;
import org.Epixcrafted.EpixServer.chat.Colour;
import org.Epixcrafted.EpixServer.mc.entity.EntityPlayer;
import org.Epixcrafted.EpixServer.mc.entity.Player;
import org.Epixcrafted.EpixServer.mc.entity.metadata.EntityMetadata;
import org.Epixcrafted.EpixServer.protocol.Packet20NamedEntity;
import org.Epixcrafted.EpixServer.protocol.Packet29DestroyEntity;
import org.Epixcrafted.EpixServer.protocol.Packet31Move;
import org.Epixcrafted.EpixServer.protocol.Packet3Chat;

public class PlayerActionLogger {
	
	/**
	 * Should be fired after receiving Packet2Handshake
	 * @param player
	 */
	public static void playerPreLogin(Player player) {
		EpixServer.info(player.getName() + " ["+ player.getAddress().getAddress().getHostAddress() + ":" + player.getAddress().getPort() + "] is trying to login...");
	}
	
	/**
	 * Should be fired after receiving Packet204Settings and after sending map chunks
	 * @param player
	 */
	public static void playerLogin(Player player) {
		EpixServer.info(player.getName() + " logged in. EID: " + player.getEntityId() + "; Location: x=" + player.getX() + ", y=" + player.getY() + ", z=" + player.getZ() + ";");
		synchronized(EpixServer.players) {
			for (EntityPlayer pl : EpixServer.players) {
				Player p = (Player) pl;
				p.sendMessage(Colour.YELLOW + player.getName() + " joined the game.");
				player.sendPacket(new Packet20NamedEntity(p.getEntityId(), p.getName(), (int)p.getX(), (int)p.getY(), (int)p.getZ(), (byte)0, (byte)0, (short)0, new EntityMetadata()));
			}
 		}
	}
	
	/**
	 * Should be fired after receiving Packet11Pos, Packet12Look, Packet13PosLook
	 * @param player
	 */
	public static void playerMove(Player player, Player old, boolean isPacket11, boolean isAll) {
		if (isAll) {
			//not currently supported so copy&paste if(isPacket11) code
		} else if (isPacket11) {
			byte x = (byte) (-((int)old.getX() - (int)player.getX()) * 32);
			byte y = (byte) (-((int)old.getY() - (int)player.getY()) * 32);
			byte z = (byte) (-((int)old.getZ() - (int)player.getZ()) * 32);
			synchronized (EpixServer.players) {
				for (EntityPlayer pl : EpixServer.players) {
					Player p = (Player) pl;
					if (!p.equals(player)) p.sendPacket(new Packet31Move(player.getEntityId(), x, y, z));
				}
			}
		} else {
			//not currently supported
		}
	}
	
	/**
	 * Should be fired after receiving Packet3Chat
	 * @param player
	 * @param message
	 */
	public static void playerChat(Player player, String message) {
		EpixServer.info("<" + player.getName() + "> " + message);
		String formattedMessage = "<" + player.getName() + "> " + message;
		synchronized(EpixServer.players) {
			for (EntityPlayer pl : EpixServer.players) {
				Player p = (Player) pl;
				p.sendPacket(new Packet3Chat(formattedMessage));
			}
 		}
	}
	
	/**
	 * Should be fired after receiving Packet3Chat if message starts with "/"
	 * @param player
	 * @param command
	 */
	public static void playerCommand(Player player, String command) {
		EpixServer.info(player.getName() + " issued server command: " + command);
	}
	
	/**
	 * Should be fired after receiving Packet255Disconnect
	 * @param player
	 */
	public static void playerDisconnect(Player player) {
		EpixServer.info(player.getName() + " lost connection. (safe quit)");
		synchronized (EpixServer.players) {
			for (EntityPlayer pl : EpixServer.players) {
				Player p = (Player) pl;
				if (!p.equals(player)) p.sendPacket(new Packet29DestroyEntity((byte)1, new int[] { player.getEntityId() }));
				if (!p.equals(player)) p.sendMessage(Colour.YELLOW + player.getName() + " left the game.");
			}
		}
	}
	
	/**
	 * Should be fired on special occasions, such as receiving invalid packets or kicking player
	 * @param player
	 */
	public static void playerKick(Player player) {
		EpixServer.info(player.getName() + " lost connection. (kick)");
		synchronized (EpixServer.players) {
			for (EntityPlayer pl : EpixServer.players) {
				Player p = (Player) pl;
				if (!p.equals(player)) p.sendPacket(new Packet29DestroyEntity((byte)1, new int[] { player.getEntityId() }));
				if (!p.equals(player)) p.sendMessage(Colour.YELLOW + player.getName() + " was kicked from the game.");
			}
		}
	}

}
