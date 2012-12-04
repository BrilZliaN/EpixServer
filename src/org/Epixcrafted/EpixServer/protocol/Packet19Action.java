package org.Epixcrafted.EpixServer.protocol;

import org.Epixcrafted.EpixServer.misc.NotSupportedOperationException;
import org.jboss.netty.buffer.ChannelBuffer;

public class Packet19Action extends Packet {
	
	public int entityId;
	public byte actionId;
	
	@Override
	public int getPacketId() {
		return 0x13;
	}

	@Override
	public void get(ChannelBuffer buf) {
		entityId = buf.readInt();
		actionId = buf.readByte();
	}

	@Override
	public ChannelBuffer send(ChannelBuffer buf) throws NotSupportedOperationException {
		throw new NotSupportedOperationException();
	}

}
