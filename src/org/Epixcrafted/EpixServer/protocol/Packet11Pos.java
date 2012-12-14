package org.Epixcrafted.EpixServer.protocol;

import org.Epixcrafted.EpixServer.misc.NotSupportedOperationException;
import org.jboss.netty.buffer.ChannelBuffer;

public class Packet11Pos extends Packet {

	public double x;
	public double y;
	public double stance;
	public double z;
	public boolean onGround;
	
	@Override
	public int getPacketId() {
		return 0xB;
	}

	@Override
	public void get(ChannelBuffer buf) {
		x = buf.readDouble();
		y = buf.readDouble();
		stance = buf.readDouble();
		z = buf.readDouble();
		onGround = buf.readByte() == 0x1 ? true : false;
	}

	@Override
	public ChannelBuffer send(ChannelBuffer buf) throws NotSupportedOperationException {
		throw new NotSupportedOperationException();
	}

}
