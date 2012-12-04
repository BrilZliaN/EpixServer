package org.Epixcrafted.EpixServer.engine;

import org.Epixcrafted.EpixServer.misc.NotSupportedOperationException;
import org.Epixcrafted.EpixServer.protocol.Packet;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

public class PacketFrameEncoder extends OneToOneEncoder {
	
	@Override
	protected Object encode(ChannelHandlerContext channelhandlercontext, Channel channel, Object obj) throws NotSupportedOperationException {
		if (!(obj instanceof Packet)) return obj;
		Packet packet = (Packet) obj;
		
		ChannelBuffer buf = ChannelBuffers.dynamicBuffer();
		Packet.write(packet, buf);
		return buf;
	}

}
