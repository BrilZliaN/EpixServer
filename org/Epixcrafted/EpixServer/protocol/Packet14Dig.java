package org.Epixcrafted.EpixServer.protocol;

import org.jboss.netty.buffer.ChannelBuffer;

public class Packet14Dig extends Packet {

	public byte status;
	public int x;
	public byte y;
	public int z;
	public byte face;
	
	@Override
	public int getPacketId() {
		return 0xE;
	}

	@Override
	public void get(ChannelBuffer buf) {
		status = buf.readByte();
		x = buf.readInt();
		y = buf.readByte();
		z = buf.readInt();
		face = buf.readByte();
	}

	@Override
	public ChannelBuffer send(ChannelBuffer buf) {
		throw new RuntimeException();
	}

}
