package org.Epixcrafted.EpixServer.protocol;

import org.jboss.netty.buffer.ChannelBuffer;

public class Packet32Look extends Packet {

	@Override
	public int getPacketId() {
		return 0x20;
	}

	@Override
	public void get(ChannelBuffer buf) {
		
	}

	@Override
	public ChannelBuffer send(ChannelBuffer buf) {
		// TODO Auto-generated method stub
		return null;
	}

}
