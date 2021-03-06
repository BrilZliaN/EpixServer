package org.Epixcrafted.EpixServer.engine;

import java.util.Iterator;
import java.util.logging.Level;

import org.Epixcrafted.EpixServer.EpixServer;
import org.Epixcrafted.EpixServer.Main;
import org.Epixcrafted.EpixServer.chat.Colour;
import org.Epixcrafted.EpixServer.engine.player.Player;
import org.Epixcrafted.EpixServer.engine.player.Session;
import org.Epixcrafted.EpixServer.mc.entity.EntityPlayer;
import org.Epixcrafted.EpixServer.misc.NotSupportedOperationException;
import org.Epixcrafted.EpixServer.protocol.Packet;
import org.Epixcrafted.EpixServer.protocol.Packet29DestroyEntity;
import org.Epixcrafted.EpixServer.protocol.Packet3Chat;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

public class PlayerHandler extends SimpleChannelUpstreamHandler {

	private final EpixServer server;
	
    public PlayerHandler(final EpixServer server) {
		this.server = server;
	}
    
	@Override
    public void channelConnected(final ChannelHandlerContext ctx, final ChannelStateEvent e) throws Exception {
		org.jboss.netty.channel.Channel c = e.getChannel();
        Session session =  new Session(server, c);
		PacketWorker worker = new PacketWorker(server, session);
		session.setWorker(worker);
        ctx.setAttachment(session);
        server.addChannel(c, session);
    }
	
    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
    	org.jboss.netty.channel.Channel c = e.getChannel();
        Session session = (Session) ctx.getAttachment();
        server.removeChannel(c, session);
        session.dispose();
    }
    
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws NotSupportedOperationException {
        if (e.getChannel().isOpen()) {
            Session session = (Session) ctx.getAttachment();
            session.packetReceived((Packet) e.getMessage());
        }
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
    	try {
        	Session session = (Session) ctx.getAttachment();
        	if (session.getPlayer() != null) {
        		session.getServer().getLogger().warning("Player " + session.getPlayer().getName() + " generated an exception: " + e.getCause());
        	} else {
        		session.getServer().getLogger().warning("Caught exception in a stream: " + e.getCause());
        	}
			session.disconnect("Internal server error");
    	} catch (Exception exc) {
    		server.getLogger().severe("Caught exception: " + exc.getCause());
    	}
    	ctx.getChannel().close();
    }
}
