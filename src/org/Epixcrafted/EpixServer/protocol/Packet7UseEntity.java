package org.Epixcrafted.EpixServer.protocol;

import org.Epixcrafted.EpixServer.misc.NotSupportedOperationException;
import org.jboss.netty.buffer.ChannelBuffer;

public class Packet7UseEntity extends Packet {

	public int userEID;
	public int targetEID;
	public boolean button;
	
	@Override
	public int getPacketId() {
		return 0x7;
	}

	@Override
	public void get(ChannelBuffer buf) {
		userEID = buf.readInt();
		targetEID = buf.readInt();
		button = buf.readByte() == 0x1 ? true : false;
	}

	@Override
	public ChannelBuffer send(ChannelBuffer buf) throws NotSupportedOperationException {
		throw new NotSupportedOperationException();
	}

}
