package org.Epixcrafted.EpixServer.protocol;

import org.jboss.netty.buffer.ChannelBuffer;

public class Packet8Health extends Packet {
	
	public short health;
	public short food;
	public float saturation;
	
	public Packet8Health(short health, short food, short saturation) {
		this.health = health;
		this.food = food;
		this.saturation = saturation;
	}

	@Override
	public int getPacketId() {
		return 0x8;
	}

	@Override
	public void get(ChannelBuffer buf) {
		throw new RuntimeException();
	}

	@Override
	public ChannelBuffer send(ChannelBuffer buf) {
		buf.writeShort(health);
		buf.writeShort(food);
		buf.writeFloat(saturation);
		return buf;
	}

}
