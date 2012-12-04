package org.Epixcrafted.EpixServer.engine;

import java.util.Iterator;
import java.util.logging.Level;

import org.Epixcrafted.EpixServer.EpixServer;
import org.Epixcrafted.EpixServer.chat.Colour;
import org.Epixcrafted.EpixServer.mc.entity.EntityPlayer;
import org.Epixcrafted.EpixServer.mc.entity.Player;
import org.Epixcrafted.EpixServer.protocol.Packet;
import org.Epixcrafted.EpixServer.protocol.Packet29DestroyEntity;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

@SuppressWarnings("unused")
public class PlayerHandler extends SimpleChannelUpstreamHandler {

	private PlayerWorkerThread worker;
	private PacketFrameDecoder decoder;
	private PacketFrameEncoder encoder;
	
    public PlayerHandler(PacketFrameDecoder decoder, PacketFrameEncoder encoder) {
		this.decoder = decoder;
		this.encoder = encoder;
	}
    
	@Override
    public void channelConnected(final ChannelHandlerContext ctx, final ChannelStateEvent e) throws Exception {
        worker = new PlayerWorkerThread(this, e.getChannel());
    }
	
    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        worker.disconnectedFromChannel();
    }
    
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
        if (e.getChannel().isOpen()) worker.acceptPacket((Packet) e.getMessage());
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
    	try {
        	if (e.getCause().getMessage().contains("Bad packet")) {
        		EpixServer.info(worker.getPlayer().getName() + " generated an exception: \"" + e.getCause().getMessage() + "\"");
        		worker.sendPacket(new org.Epixcrafted.EpixServer.protocol.Packet255Disconnect(e.getCause().getMessage()));
        		synchronized (EpixServer.players) {
        			for (Iterator<EntityPlayer> iterator = EpixServer.players.iterator(); iterator.hasNext();) {
        				Player p = (Player) iterator.next();
        				if (!p.equals(worker.getPlayer())) p.sendPacket(new Packet29DestroyEntity((byte)1, new int[] { worker.getPlayer().getEntityId() }));
        				if (!p.equals(worker.getPlayer())) p.sendMessage(Colour.YELLOW + worker.getPlayer().getName() + " was kicked from the game.");
        			}
        		}
        	} else {
                EpixServer.warning("Caught exception in a stream: \n" + e.getCause());
        		synchronized (EpixServer.players) {
        			for (Iterator<EntityPlayer> iterator = EpixServer.players.iterator(); iterator.hasNext();) {
        				Player p = (Player) iterator.next();
        				if (!p.equals(worker.getPlayer())) p.sendPacket(new Packet29DestroyEntity((byte)1, new int[] { worker.getPlayer().getEntityId() }));
        				if (!p.equals(worker.getPlayer())) p.sendMessage(Colour.YELLOW + worker.getPlayer().getName() + " was kicked from the game.");
        			}
        		}
        	}
    	} catch (NullPointerException npe) {
    		//handle NPEs
    	}
        ctx.getChannel().close();
    }
}
