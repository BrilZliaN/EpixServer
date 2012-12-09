package org.Epixcrafted.EpixServer.protocol;

import org.jboss.netty.buffer.ChannelBuffer;

public class Packet0KeepAlive extends Packet {

	public int keepAliveId = 0;
	
	public Packet0KeepAlive() {
		
	}
	
	public Packet0KeepAlive(int keepAliveId) {
		this.keepAliveId = keepAliveId;
	}

	@Override
	public int getPacketId() {
		return 0x0;
	}
	
	@Override
	public void get(ChannelBuffer buf) {
		keepAliveId = buf.readInt();
	}
	
	@Override
	public ChannelBuffer send(ChannelBuffer buf) {
		buf.writeInt(keepAliveId);
		return buf;
	}
}
