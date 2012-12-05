package org.Epixcrafted.EpixServer.protocol;

import org.Epixcrafted.EpixServer.misc.NotSupportedOperationException;
import org.jboss.netty.buffer.ChannelBuffer;

public class Packet101CloseWindow extends Packet {

	public byte windowId;
	
	public Packet101CloseWindow() {
		
	}
	
	public Packet101CloseWindow(byte windowId) {
		this.windowId = windowId;
	}

	@Override
	public int getPacketId() {
		return 0x65;
	}

	@Override
	public void get(ChannelBuffer buf) throws NotSupportedOperationException {
		this.windowId = buf.readByte();
	}

	@Override
	public ChannelBuffer send(ChannelBuffer buf) throws NotSupportedOperationException {
		buf.writeByte(windowId);
		return buf;
	}

}
