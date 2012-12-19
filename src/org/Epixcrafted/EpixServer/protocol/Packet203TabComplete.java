package org.Epixcrafted.EpixServer.protocol;

import org.Epixcrafted.EpixServer.misc.NotSupportedOperationException;
import org.jboss.netty.buffer.ChannelBuffer;

public class Packet203TabComplete extends Packet {
	
	public String client;
	public String server;

	@Override
	public int getPacketId() {
		return 0xCB;
	}

	@Override
	public void get(ChannelBuffer buf) throws NotSupportedOperationException {
	       int length = buf.readShort();
	       StringBuilder builder = new StringBuilder();
	       for(int i = 0; i < length; i++) builder.append(buf.readChar());
	       client = builder.toString();
	}

	@Override
	public ChannelBuffer send(ChannelBuffer buf)
			throws NotSupportedOperationException {
        buf.writeShort(server.length());
        for(int i = 0; i < server.length(); ++i) {
            buf.writeChar(server.charAt(i));
        }
        return buf;
	}

}
