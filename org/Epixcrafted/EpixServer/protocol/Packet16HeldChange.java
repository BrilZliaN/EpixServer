package org.Epixcrafted.EpixServer.protocol;

import org.jboss.netty.buffer.ChannelBuffer;

public class Packet16HeldChange extends Packet {
	
	public short slotId;

	@Override
	public int getPacketId() {
		return 0x10;
	}

	@Override
	public void get(ChannelBuffer buf) {
		slotId = buf.readShort();
	}

	@Override
	public ChannelBuffer send(ChannelBuffer buf) {
		throw new RuntimeException();
	}

}
