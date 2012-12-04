package org.Epixcrafted.EpixServer.protocol;

import org.jboss.netty.buffer.ChannelBuffer;

public class Packet254Ping extends Packet {

	@Override
	public int getPacketId() {
		return (int) 0xFE;
	}
	
	@Override
	public void get(ChannelBuffer buf) {
		buf.readByte();
	}
	
	@Override
	public ChannelBuffer send(ChannelBuffer buf) {
		throw new RuntimeException();
	}
}
