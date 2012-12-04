package org.Epixcrafted.EpixServer.protocol;

import org.jboss.netty.buffer.ChannelBuffer;

public class Packet6Spawn extends Packet {
	
	public int x;
	public int y;
	public int z;
	
	public Packet6Spawn(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public int getPacketId() {
		return 0x6;
	}
	@Override
	public void get(ChannelBuffer buf) {
		throw new RuntimeException();
	}
	@Override
	public ChannelBuffer send(ChannelBuffer buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		return buf;
	}

}
