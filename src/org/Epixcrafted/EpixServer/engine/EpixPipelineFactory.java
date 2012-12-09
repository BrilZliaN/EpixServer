package org.Epixcrafted.EpixServer.engine;

import org.Epixcrafted.EpixServer.EpixServer;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

public class EpixPipelineFactory implements ChannelPipelineFactory {
	
	private final EpixServer server;
	
	public EpixPipelineFactory(EpixServer server) {
		this.server = server;
	}
	
	@Override
	public ChannelPipeline getPipeline() throws Exception {
		PacketFrameDecoder decoder = new PacketFrameDecoder();
		PacketFrameEncoder encoder = new PacketFrameEncoder();
		return Channels.pipeline(decoder, encoder, new PlayerHandler(server));
	}

}
