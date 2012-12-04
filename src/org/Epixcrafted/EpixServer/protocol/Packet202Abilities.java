package org.Epixcrafted.EpixServer.protocol;

import org.jboss.netty.buffer.ChannelBuffer;

public class Packet202Abilities extends Packet {

	public byte flags;
	public byte flyspeed;
	public byte walkspeed;
	
	public Packet202Abilities() {
		
	}
	
	public Packet202Abilities(byte flags, byte flyspeed, byte walkspeed) {
		this.flags = flags;
		this.flyspeed = flyspeed;
		this.walkspeed = walkspeed;
	}
	
	@Override
	public int getPacketId() {
		return 0xCA;
	}

	@Override
	public void get(ChannelBuffer buf) {
		flags = buf.readByte();
		flyspeed = buf.readByte();
		walkspeed = buf.readByte();
	}

	@Override
	public ChannelBuffer send(ChannelBuffer buf) {
		buf.writeByte(flags);
		buf.writeByte(flyspeed);
		buf.writeByte(walkspeed);
		return buf;
	}

}
