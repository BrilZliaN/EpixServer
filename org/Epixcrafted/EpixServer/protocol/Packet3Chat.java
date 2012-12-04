package org.Epixcrafted.EpixServer.protocol;

import org.jboss.netty.buffer.ChannelBuffer;

public class Packet3Chat extends Packet {
	
	public String message;
	
	public Packet3Chat() {
		
	}
	
	public Packet3Chat(String message) {
		this.message = message;
	}

	@Override
	public int getPacketId() {
		return 0x3;
	}

	@Override
	public void get(ChannelBuffer buf) {
		int length = buf.readShort();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) sb.append(buf.readChar());
		message = sb.toString();
	}

	@Override
	public ChannelBuffer send(ChannelBuffer buf) {
        buf.writeShort(message.length());
        for(int i = 0; i < message.length(); ++i) {
            buf.writeChar(message.charAt(i));
        }
		return buf;
	}

}
