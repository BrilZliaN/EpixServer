package org.Epixcrafted.EpixServer.engine.player;

import java.net.InetSocketAddress;

import org.Epixcrafted.EpixServer.chat.CommandSender;
import org.Epixcrafted.EpixServer.engine.Server;
import org.Epixcrafted.EpixServer.mc.entity.EntityPlayer;
import org.Epixcrafted.EpixServer.protocol.Packet3Chat;

public class Player extends EntityPlayer implements CommandSender {
	
	private final Session session;
	
	private boolean isSwinging;

	public Player(Session session, int entityId, String name) {
		super(entityId, name);
		this.session = session;
	}

	public Player(Session session, int entityId, String name, double x, double y, double z) {
		super(entityId, name, x, y, z);
		this.session = session;
	}
	
	public Player(Session session, int entityId, String name, double x, double y, double z,
			double stance, float yaw, float pitch, boolean onGround) {
		super(entityId, name, x, y, z, stance, yaw, pitch, onGround);
		this.session = session;
	}
	
	public Player(Session session, int entityId, String name, double x, double y, double z,
			double stance, float yaw, float pitch, boolean onGround,
			short health, short food, float saturation) {
		super(entityId, name, x, y, z, stance, yaw, pitch, onGround, health, food,
				saturation);
		this.session = session;
	}
	
	public boolean isSwinging() {
		return isSwinging;
	}
	
	public InetSocketAddress getAddress() {
		return session.getAddress();
	}
	
	public void setSwinging(boolean isSwinging) {
		this.isSwinging = isSwinging;
	}
	
	@Override
	public Server getServer() {
		return session.getServer();
	}

	@Override
	public void sendMessage(String message) {
		session.send(new Packet3Chat(message));
	}
}
