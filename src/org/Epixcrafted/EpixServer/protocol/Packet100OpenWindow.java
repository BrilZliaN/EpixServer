package org.Epixcrafted.EpixServer.protocol;

import org.Epixcrafted.EpixServer.misc.NotSupportedOperationException;
import org.jboss.netty.buffer.ChannelBuffer;

public class Packet100OpenWindow extends Packet {

	public byte windowId;
	public byte inventoryType;
	public String windowTitle;
	public byte slotsCount;
	
	public Packet100OpenWindow() {
		
	}
	
	public Packet100OpenWindow(byte windowId, byte inventoryType, String windowTitle, byte slotsCount) {
		this.windowId = windowId;
		this.inventoryType = inventoryType;
		this.windowTitle = windowTitle;
		this.slotsCount = slotsCount;	
	}
	
	@Override
	public int getPacketId() {
		return 0x64;
	}

	@Override
	public void get(ChannelBuffer buf) throws NotSupportedOperationException {
		throw new NotSupportedOperationException();
	}

	@Override
	public ChannelBuffer send(ChannelBuffer buf) throws NotSupportedOperationException {
		buf.writeByte(windowId & 0xff);
        buf.writeByte(inventoryType & 0xff);
        buf.writeShort(windowTitle.length());
        for(int i = 0; i < windowTitle.length(); ++i) {
            buf.writeChar(windowTitle.charAt(i));
        }
        buf.writeByte(slotsCount & 0xff);
        return buf;
	}

}
