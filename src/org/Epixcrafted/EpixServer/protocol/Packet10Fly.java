package org.Epixcrafted.EpixServer.protocol;

import org.jboss.netty.buffer.ChannelBuffer;

public class Packet10Fly extends Packet {
	
	public boolean onGround;

	@Override
	public int getPacketId() {
		return 0xA;
	}

	@Override
	public void get(ChannelBuffer buf) {
		onGround = buf.readByte() == 0x1 ? true : false;
	}

	@Override
	public ChannelBuffer send(ChannelBuffer buf) {
		throw new RuntimeException();
	}

}
