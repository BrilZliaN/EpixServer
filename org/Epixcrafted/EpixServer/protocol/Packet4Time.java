package org.Epixcrafted.EpixServer.protocol;

import org.jboss.netty.buffer.ChannelBuffer;

public class Packet4Time extends Packet {
	
	private long time;
	
	public Packet4Time(int time) {
		this.time = time;
	}

	@Override
	public int getPacketId() {
		return 0x4;
	}

	@Override
	public void get(ChannelBuffer buf) {
		throw new RuntimeException();
	}

	@Override
	public ChannelBuffer send(ChannelBuffer buf) {
		buf.writeLong(time);
		buf.writeLong(time);
		return buf;
	}

}
