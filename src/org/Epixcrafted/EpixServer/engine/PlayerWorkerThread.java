package org.Epixcrafted.EpixServer.engine;

import java.net.InetSocketAddress;

import org.Epixcrafted.EpixServer.EpixServer;
import org.Epixcrafted.EpixServer.mc.entity.Player;
import org.Epixcrafted.EpixServer.protocol.Packet;
import org.Epixcrafted.EpixServer.protocol.Packet10Fly;
import org.Epixcrafted.EpixServer.protocol.Packet11Pos;
import org.Epixcrafted.EpixServer.protocol.Packet12Look;
import org.Epixcrafted.EpixServer.protocol.Packet13PosLook;
import org.Epixcrafted.EpixServer.protocol.Packet18Animation;
import org.Epixcrafted.EpixServer.protocol.Packet1Login;
import org.Epixcrafted.EpixServer.protocol.Packet255Disconnect;
import org.Epixcrafted.EpixServer.protocol.Packet2Handshake;
import org.Epixcrafted.EpixServer.protocol.Packet3Chat;
import org.Epixcrafted.EpixServer.protocol.Packet4Time;
import org.Epixcrafted.EpixServer.protocol.Packet6Spawn;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;

public class PlayerWorkerThread {
	
	private Channel channel;
	private Player player;

	public PlayerWorkerThread(PlayerHandler playerHandler, Channel channel) {
		this.channel = channel;
	}

	boolean wasAlreadyDisconnected = false;
	public void disconnectedFromChannel() {
		if (player != null) {
			synchronized (EpixServer.players){
				if (EpixServer.players.contains(player)) {
					wasAlreadyDisconnected = true;
					EpixServer.players.remove(player);
				}
			}
		}
		if (wasAlreadyDisconnected) EpixServer.info(player.getName() + " lost connection. (window closing/channel closing)");
	}

	public void acceptPacket(Packet packet) {
		if (packet.getPacketId() == 2) {
			player = new Player(++EpixServer.allEntitiesUsedIds, ((Packet2Handshake)packet).username, this);
			if (((Packet2Handshake)packet).protocol < EpixServer.PROTOCOL_VERSION) {
				channel.write(Packet.write(new Packet255Disconnect("Outdated client!"), ChannelBuffers.dynamicBuffer()));
			} else if (((Packet2Handshake)packet).protocol > EpixServer.PROTOCOL_VERSION) {
				channel.write(Packet.write(new Packet255Disconnect("Outdated server!"), ChannelBuffers.dynamicBuffer()));
			} else {
				channel.write(Packet.write(new Packet1Login(player.getEntityId(), "default", (byte)0x1, (byte)0x0, (byte)0x1, (byte)0x0, (byte)0x0), ChannelBuffers.dynamicBuffer()));
			}
			PlayerActionLogger.playerPreLogin(player);
			return;
		}
		if (packet.getPacketId() == 3) {
			if (((Packet3Chat)packet).message.length() > 100) {
				channel.write(Packet.write(new Packet255Disconnect("Recieved a string with >100 character length"), ChannelBuffers.dynamicBuffer()));
				channel.close();
				PlayerActionLogger.playerKick(player);
				return;
			}
			if (((Packet3Chat)packet).message.startsWith("/")) {
				EpixServer.clist.executeCommand(((Packet3Chat)packet).message.substring(1), player);
				PlayerActionLogger.playerCommand(player, ((Packet3Chat)packet).message);
			} else {
				PlayerActionLogger.playerChat(player, ((Packet3Chat)packet).message);
			}
		}
		if (packet.getPacketId() == 10) {
			player.setOnGround(((Packet10Fly)packet).onGround);
		}
		if (packet.getPacketId() == 11) {
			Packet11Pos p = (Packet11Pos) packet;
			if (p.stance - p.y > 1.65D || p.stance - p.y < 0.1D) {
				channel.write(Packet.write(new Packet255Disconnect("Invalid stance"), ChannelBuffers.dynamicBuffer()));
				return;
			}
			Player oldPlayer = player;
			player.setX(p.x);
			player.setY(p.y);
			player.setZ(p.z);
			player.setStance(p.stance);
			player.setOnGround(p.onGround);
			PlayerActionLogger.playerMove(player, oldPlayer, true, false);
		}
		if (packet.getPacketId() == 12) {
			player.setYaw(((Packet12Look)packet).yaw);
			player.setPitch(((Packet12Look)packet).pitch);
			player.setOnGround(((Packet12Look)packet).onGround);
		}
		if (packet.getPacketId() == 13) {
			Packet13PosLook p = (Packet13PosLook) packet;
			if (p.stance - p.y > 1.65D || p.stance - p.y < 0.1D) {
				channel.write(Packet.write(new Packet255Disconnect("Invalid stance"), ChannelBuffers.dynamicBuffer()));
				return;
			}
			Player oldPlayer = player;
			player.setX(p.x);
			player.setY(p.y);
			player.setZ(p.z);
			player.setStance(p.stance);
			player.setYaw(p.yaw);
			player.setPitch(p.pitch);
			player.setOnGround(p.onGround);
			PlayerActionLogger.playerMove(player, oldPlayer, true, false);
		}
		if (packet.getPacketId() == 18) {
			if (((Packet18Animation)packet).type == 0) {
				channel.write(Packet.write(packet, ChannelBuffers.dynamicBuffer()));
				player.setSwinging(false);
			} else if (((Packet18Animation)packet).type == 1) {
				if (player.isSwinging == true) channel.write(Packet.write(packet, ChannelBuffers.dynamicBuffer()));
			}
			if (((Packet18Animation)packet).type == 2) channel.write(Packet.write(packet, ChannelBuffers.dynamicBuffer()));
		}
		if (packet.getPacketId() == 202) {
			channel.write(Packet.write(packet, ChannelBuffers.dynamicBuffer()));
		}
		if (packet.getPacketId() == 204) {
			//send chunks here
			player.setX(0D);
			player.setY(128D);
			player.setZ(0D);
			player.setStance(128D + 1.65D);
			player.setYaw(0F);
			player.setPitch(0F);
			player.setOnGround(true);
			channel.write(Packet.write(new Packet6Spawn((int)player.getX(), (int)player.getY(), (int)player.getZ()), ChannelBuffers.dynamicBuffer()));
			channel.write(Packet.write(new Packet13PosLook(player.getX(), player.getStance(), player.getY(), player.getZ(), player.getYaw(), player.getPitch(), player.isOnGround()), ChannelBuffers.dynamicBuffer()));
			channel.write(Packet.write(new Packet4Time(EpixServer.getTime()), ChannelBuffers.dynamicBuffer()));
			PlayerActionLogger.playerLogin(player);
		}
		if (packet.getPacketId() == 254) {
			String s = "\u00A71\u0000" + EpixServer.PROTOCOL_VERSION + "\u0000" + EpixServer.MINECRAFT_VERSION + "\u0000EpixServer\u0000" + EpixServer.players.size() + "\u000065535";
			channel.write(Packet.write(new Packet255Disconnect(s), ChannelBuffers.dynamicBuffer()));
			return;
		}
		if (packet.getPacketId() == 255) {
			if (player != null) {
				synchronized (EpixServer.players){
					if (EpixServer.players.contains(player)) {
						EpixServer.players.remove(player);
					}
				}
			}
			PlayerActionLogger.playerDisconnect(player);
		}
		if (player != null) {
			synchronized (EpixServer.players){
				if (EpixServer.players.contains(player)) {
					EpixServer.players.remove(player);
					EpixServer.players.add(player);
				} else {
					EpixServer.players.add(player);
				}
			}
		}
	}
	
	public InetSocketAddress getAddress() {
		return (InetSocketAddress) this.channel.getRemoteAddress();
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void sendPacket(Packet packet) {
		channel.write(Packet.write(packet, ChannelBuffers.dynamicBuffer()));
	}
}
