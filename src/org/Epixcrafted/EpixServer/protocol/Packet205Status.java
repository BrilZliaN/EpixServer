package org.Epixcrafted.EpixServer.protocol;

import org.Epixcrafted.EpixServer.misc.NotSupportedOperationException;
import org.jboss.netty.buffer.ChannelBuffer;

public class Packet205Status extends Packet {
	
	public byte payload;

	@Override
	public int getPacketId() {
		return 0xCD;
	}

	@Override
	public void get(ChannelBuffer buf) throws NotSupportedOperationException {
		payload = buf.readByte();
	}

	@Override
	public ChannelBuffer send(ChannelBuffer buf) throws NotSupportedOperationException {
		throw new NotSupportedOperationException();
	}

}
