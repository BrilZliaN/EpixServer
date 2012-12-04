package org.Epixcrafted.EpixServer.protocol;

import org.jboss.netty.buffer.ChannelBuffer;

public class Packet255Disconnect extends Packet {
	
	public String reason;
	
	public Packet255Disconnect() {
		reason = "";
	}
	
	public Packet255Disconnect(String reason) {
		this.reason = reason;
	}
	
	@Override
	public int getPacketId() {
		return (int) 0xFF;
	}
	
	@Override
	public void get(ChannelBuffer buf) {
	       int length = buf.readShort();
	       StringBuilder builder = new StringBuilder();
	       for(int i = 0; i < length; i++) builder.append(buf.readChar());
	       reason = builder.toString();
	}
	
	@Override
	public ChannelBuffer send(ChannelBuffer buf) {
        buf.writeShort(reason.length());
        for(int i = 0; i < reason.length(); ++i) {
            buf.writeChar(reason.charAt(i));
        }
        return buf;
	}
}
