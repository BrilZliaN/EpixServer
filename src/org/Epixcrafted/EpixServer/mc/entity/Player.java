package org.Epixcrafted.EpixServer.mc.entity;

import java.net.InetSocketAddress;

import org.Epixcrafted.EpixServer.chat.CommandSender;
import org.Epixcrafted.EpixServer.engine.PlayerWorkerThread;
import org.Epixcrafted.EpixServer.misc.NotSupportedOperationException;
import org.Epixcrafted.EpixServer.protocol.Packet;
import org.Epixcrafted.EpixServer.protocol.Packet3Chat;
import org.Epixcrafted.EpixServer.protocol.Packet4Time;

public class Player extends EntityPlayer implements CommandSender {
	
	public boolean isSwinging;

	public Player(int entityId, String name, PlayerWorkerThread playerWorker) {
		super(entityId, name, playerWorker);
	}

	public Player(int entityId, String name, double x, double y, double z,
			PlayerWorkerThread playerWorker) {
		super(entityId, name, x, y, z, playerWorker);
	}
	
	public Player(int entityId, String name, double x, double y, double z,
			double stance, float yaw, float pitch, boolean onGround,
			PlayerWorkerThread playerWorker) {
		super(entityId, name, x, y, z, stance, yaw, pitch, onGround, playerWorker);
	}
	
	public Player(int entityId, String name, double x, double y, double z,
			double stance, float yaw, float pitch, boolean onGround,
			short health, short food, float saturation,
			PlayerWorkerThread playerWorker) {
		super(entityId, name, x, y, z, stance, yaw, pitch, onGround, health, food,
				saturation, playerWorker);
	}
	
	public boolean isSwinging() {
		return isSwinging;
	}
	
	public InetSocketAddress getAddress() {
		return this.playerWorker.getAddress();
	}
	
	public void setSwinging(boolean isSwinging) {
		this.isSwinging = isSwinging;
	}
	
	@Override
	public void sendMessage(String message) {
		try {
			playerWorker.sendPacket(new Packet3Chat(message));
		} catch (NotSupportedOperationException e) {
			//nsoe
		}
	}
	
	public void synchronizeWorldTime(int time) {
		try {
			playerWorker.sendPacket(new Packet4Time(time));
		} catch (NotSupportedOperationException e) {
			//nsoe
		}
	}
	
	public void sendPacket(Packet packet) {
		try {
			playerWorker.sendPacket(packet);
		} catch (NotSupportedOperationException e) {
			//nsoe
		}
	}

}
