package org.Epixcrafted.EpixServer.protocol;

import org.jboss.netty.buffer.ChannelBuffer;

public class Packet31Move extends Packet {
	
	public int entityId;
	public byte x;
	public byte y;
	public byte z;
	
	public Packet31Move(int entityId, byte x, byte y, byte z) {
		this.entityId = entityId;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public int getPacketId() {
		return 0x1F;
	}

	@Override
	public void get(ChannelBuffer buf) {
		throw new RuntimeException();
	}

	@Override
	public ChannelBuffer send(ChannelBuffer buf) {
		buf.writeInt(entityId);
		buf.writeByte(x);
		buf.writeByte(y);
		buf.writeByte(z);
		return buf;
	}

}
