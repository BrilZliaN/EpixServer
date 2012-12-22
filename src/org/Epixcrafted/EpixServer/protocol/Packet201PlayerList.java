package org.Epixcrafted.EpixServer.protocol;

import org.Epixcrafted.EpixServer.misc.NotSupportedOperationException;
import org.jboss.netty.buffer.ChannelBuffer;

public class Packet201PlayerList extends Packet {

	@Override
	public int getPacketId() {
		return 0xC9;
	}

	@Override
	public void get(ChannelBuffer buf) throws NotSupportedOperationException {
		throw new NotSupportedOperationException();
	}

	@Override
	public ChannelBuffer send(ChannelBuffer buf) throws NotSupportedOperationException {
		return buf;
	}

}
