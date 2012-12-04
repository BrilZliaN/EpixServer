package org.Epixcrafted.EpixServer.protocol;

import org.jboss.netty.buffer.ChannelBuffer;

public class Packet204Settings extends Packet {
	
	public String locale;
	public byte distance;
	public byte chatFlags;
	public byte difficulty;
	public boolean showCape;

	@Override
	public int getPacketId() {
		return 0xCC;
	}

	@Override
	public void get(ChannelBuffer buf) {
		int length = buf.readShort();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) sb.append(buf.readChar());
		locale = sb.toString();
		distance = buf.readByte();
		chatFlags = buf.readByte();
		difficulty = buf.readByte();
		showCape = buf.readByte() == 0x1 ? true : false;
	}

	@Override
	public ChannelBuffer send(ChannelBuffer buf) {
		throw new RuntimeException();
	}

	
}
