package org.Epixcrafted.EpixServer.engine;

import org.Epixcrafted.EpixServer.EpixServer;
import org.Epixcrafted.EpixServer.engine.player.Player;
import org.Epixcrafted.EpixServer.engine.player.Session;
import org.Epixcrafted.EpixServer.engine.player.Session.Connection;
import org.Epixcrafted.EpixServer.protocol.Packet;
import org.Epixcrafted.EpixServer.protocol.Packet10Fly;
import org.Epixcrafted.EpixServer.protocol.Packet11Pos;
import org.Epixcrafted.EpixServer.protocol.Packet12Look;
import org.Epixcrafted.EpixServer.protocol.Packet13PosLook;
import org.Epixcrafted.EpixServer.protocol.Packet18Animation;
import org.Epixcrafted.EpixServer.protocol.Packet1Login;
import org.Epixcrafted.EpixServer.protocol.Packet202Abilities;
import org.Epixcrafted.EpixServer.protocol.Packet203TabComplete;
import org.Epixcrafted.EpixServer.protocol.Packet204Settings;
import org.Epixcrafted.EpixServer.protocol.Packet205Status;
import org.Epixcrafted.EpixServer.protocol.Packet254Ping;
import org.Epixcrafted.EpixServer.protocol.Packet255Disconnect;
import org.Epixcrafted.EpixServer.protocol.Packet2Handshake;
import org.Epixcrafted.EpixServer.protocol.Packet3Chat;
import org.Epixcrafted.EpixServer.protocol.Packet6Spawn;

public class PacketWorker {

	private final EpixServer server;
	private final Session session;
	
	public PacketWorker(EpixServer server, Session session) {
		this.server = server;
		this.session = session;
	}

	public void acceptPacket(Packet packet) {
		if (packet.getPacketId() == 2) {
			onPacket2Handshake((Packet2Handshake) packet);
		} else
		if (packet.getPacketId() == 3) {
			onPacket3Chat((Packet3Chat) packet);
		} else
		if (packet.getPacketId() == 10) {
			onPacket10Fly((Packet10Fly) packet);
		} else
		if (packet.getPacketId() == 11) {
			onPacket11Pos((Packet11Pos) packet);
		} else
		if (packet.getPacketId() == 12) {
			onPacket12Look((Packet12Look) packet);
		} else
		if (packet.getPacketId() == 13) {
			onPacket13PosLook((Packet13PosLook) packet);
		} else
		if (packet.getPacketId() == 18) {
			onPacket18Animation((Packet18Animation) packet);
		} else
		if (packet.getPacketId() == 202) {
			onPacket202Abilities((Packet202Abilities) packet);
		} else
		if (packet.getPacketId() == 203) {
			onPacket203TabComplete((Packet203TabComplete) packet);
		} else
		if (packet.getPacketId() == 204) {
			onPacket204Settings((Packet204Settings) packet);
		} else
		if (packet.getPacketId() == 205) {
			onPacket205Status((Packet205Status) packet);
		}
		if (packet.getPacketId() == 254) {
			onPacket254Ping((Packet254Ping) packet);
		} else
		if (packet.getPacketId() == 255) {
			onPacket255Disconnect((Packet255Disconnect) packet);
		}
	}

	private void onPacket2Handshake(Packet2Handshake packet) {
		if (packet.protocol != -1) {
			session.disconnect("You should use EpixClient to enter this server");
			return;
		}
		session.setPlayer(new Player(session, ++EpixServer.lastEntityId, packet.username));
		if (server.getSessionListClass().isExists(session, packet.username)) {
			session.disconnect("This player is already online!");
			return;
		}
		session.send(new Packet1Login(session.getPlayer().getEntityId(), "default", (byte)1, (byte)0, (byte)1, (byte)0, (this.server.getMaximumPlayers())));
		PlayerActionLogger.playerPreLogin(session);
	}
	
	private void onPacket3Chat(Packet3Chat packet) {
		if (packet.message.startsWith("/")) {
			server.getCommandList().executeCommand(packet.message.substring(1), session.getPlayer());
			PlayerActionLogger.playerCommand(session, packet.message);
		} else {
			PlayerActionLogger.playerChat(session, packet.message);
		}
	}
	
	private void onPacket10Fly(Packet10Fly packet) {
		session.getPlayer().setOnGround(packet.onGround);
	}
	
	private void onPacket11Pos(Packet11Pos packet) {
		if (packet.stance - packet.y < 0D || packet.stance - packet.y > 1.65D) {
			session.disconnect("Invalid stance");
			return;
		}
		Player old = session.getPlayer();
		session.getPlayer().setX(packet.x);
		session.getPlayer().setY(packet.y);
		session.getPlayer().setZ(packet.z);
		session.getPlayer().setStance(packet.stance);
		session.getPlayer().setOnGround(packet.onGround);
		PlayerActionLogger.playerMove(session, old, true, false);
	}
	
	private void onPacket12Look(Packet12Look packet) {
		session.getPlayer().setYaw(packet.yaw);
		session.getPlayer().setPitch(packet.pitch);
		session.getPlayer().setOnGround(packet.onGround);
	}
	
	private void onPacket13PosLook(Packet13PosLook packet) {
		if (packet.stance < 0D || packet.stance > 1.65D) {
			session.disconnect("Invalid stance");
			return;
		}
		Player old = session.getPlayer();
		session.getPlayer().setX(packet.x);
		session.getPlayer().setY(packet.y);
		session.getPlayer().setZ(packet.z);
		session.getPlayer().setStance(packet.stance);
		session.getPlayer().setYaw(packet.yaw);
		session.getPlayer().setPitch(packet.pitch);
		session.getPlayer().setOnGround(packet.onGround);
		PlayerActionLogger.playerMove(session, old, false, true);
	}
	
	private void onPacket18Animation(Packet18Animation packet) {
		if (((Packet18Animation)packet).type == 0) {
			session.send(packet);
			session.getPlayer().setSwinging(false);
		}
	}
	
	private void onPacket202Abilities(Packet202Abilities packet) {
		session.send(packet);
	}
	
	private void onPacket203TabComplete(Packet203TabComplete packet) {
		packet.server = packet.client;
		session.send(packet);
	}
	
	private void onPacket204Settings(Packet204Settings packet) {
		session.setConnectionState(Connection.CONNECTED);
		//send chunks here
		session.getPlayer().setX(0D);
		session.getPlayer().setY(128D);
		session.getPlayer().setZ(0D);
		session.getPlayer().setStance(128D + 1.65D);
		session.getPlayer().setYaw(0F);
		session.getPlayer().setPitch(0F);
		session.getPlayer().setOnGround(true);
		session.send(new Packet6Spawn((int)session.getPlayer().getX(), (int)session.getPlayer().getY(), (int)session.getPlayer().getZ()));
		session.send(new Packet13PosLook(session.getPlayer().getX(), session.getPlayer().getStance(), session.getPlayer().getY(), session.getPlayer().getZ(), session.getPlayer().getYaw(), session.getPlayer().getPitch(), session.getPlayer().isOnGround()));
		session.setConnectionState(Connection.PLAYING);
		PlayerActionLogger.playerLogin(session);
	}
	
	private void onPacket205Status(Packet205Status packet) {
		
	}
	
	private void onPacket254Ping(Packet254Ping packet) {
		String answer = "";
		if (packet.magic == 3) {
			answer = "\u00A71\u0000-1\u0000Update needed\u0000EpixServer\u0000" + this.server.getOnlinePlayers() + "\u0000" + this.server.getMaximumPlayers();
		} else if (packet.magic == 1) {
			answer = "\u00A71\u0000127\u0000EpixClient needed\u0000EpixServer\u0000" + this.server.getOnlinePlayers() + "\u0000" + this.server.getMaximumPlayers();
		} else {
			answer = ""; //TODO: handle 1.3 answering
		}
		session.send(new Packet255Disconnect(answer));
	}
	
	private void onPacket255Disconnect(Packet255Disconnect packet) {
		session.disconnect("disconnect.quitting");
	}
}
