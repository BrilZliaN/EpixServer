package org.Epixcrafted.EpixServer.protocol;

import java.util.Random;

import org.jboss.netty.buffer.ChannelBuffer;

public class Packet0KeepAlive extends Packet {

	public int keepaliveid = 0;
	
	@Override
	public int getPacketId() {
		return (int) 0x0;
	}
	
	@Override
	public void get(ChannelBuffer buf) {
		keepaliveid = buf.readInt();
	}
	
	@Override
	public ChannelBuffer send(ChannelBuffer buf) {
		buf.writeInt(keepaliveid = new Random().nextInt(Integer.MAX_VALUE));
		return buf;
	}
}
