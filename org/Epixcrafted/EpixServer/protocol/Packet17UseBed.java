package org.Epixcrafted.EpixServer.protocol;

import org.jboss.netty.buffer.ChannelBuffer;

public class Packet17UseBed extends Packet {
	
	public int entityId;
	public byte type;
	public int x;
	public byte y;
	public int z;

	@Override
	public int getPacketId() {
		return 0x11;
	}

	@Override
	public void get(ChannelBuffer buf) {
		throw new RuntimeException();
	}

	@Override
	public ChannelBuffer send(ChannelBuffer buf) {
		buf.writeInt(entityId);
		buf.writeByte(type);
		buf.writeInt(x);
		buf.writeByte(y);
		buf.writeInt(z);
		return buf;
	}

}
