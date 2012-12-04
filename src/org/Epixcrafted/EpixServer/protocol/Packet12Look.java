package org.Epixcrafted.EpixServer.protocol;

import org.Epixcrafted.EpixServer.misc.NotSupportedOperationException;
import org.jboss.netty.buffer.ChannelBuffer;

public class Packet12Look extends Packet {
	
	public float yaw;
	public float pitch;
	public boolean onGround;
	
	@Override
	public int getPacketId() {
		return 0xC;
	}
	
	@Override
	public void get(ChannelBuffer buf) {
		yaw = buf.readFloat();
		pitch = buf.readFloat();
		onGround = buf.readByte() == 0x1 ? true : false;
	}
	
	@Override
	public ChannelBuffer send(ChannelBuffer buf) throws NotSupportedOperationException {
		throw new NotSupportedOperationException();
	}

}
