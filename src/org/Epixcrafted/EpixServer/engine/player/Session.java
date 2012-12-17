package org.Epixcrafted.EpixServer.engine.player;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;

import org.Epixcrafted.EpixServer.engine.PacketWorker;
import org.Epixcrafted.EpixServer.engine.PlayerActionLogger;
import org.Epixcrafted.EpixServer.misc.NotSupportedOperationException;
import org.Epixcrafted.EpixServer.protocol.Packet;
import org.Epixcrafted.EpixServer.protocol.Packet0KeepAlive;
import org.Epixcrafted.EpixServer.protocol.Packet255Disconnect;
import org.Epixcrafted.EpixServer.EpixServer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFutureListener;

public class Session {

    private static final int TIMEOUT_TICKS = 300;

    private final EpixServer server;
    private final Channel channel;
    private final Queue<Packet> packetQueue = new ArrayDeque<Packet>();
    private PacketWorker worker;
    
    private int timeoutCounter = 0;
    private Connection state = Connection.CONNECTING;
    private Player player;
    private int keepAliveId;

    public Session(EpixServer server, Channel channel) {
        this.server = server;
        this.channel = channel;
    }

    public Connection getConnectionState() {
        return state;
    }
    
    public void setConnectionState(Connection state) {
        this.state = state;
    }
    
    public PacketWorker getWorker() {
    	return worker;
    }
    
    public void setWorker(PacketWorker worker) {
    	if (this.worker != null) throw new IllegalStateException();
    	this.worker = worker;
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public void setPlayer(Player player) {
        if (this.player != null) throw new IllegalStateException();
        this.player = player;
    }

    public void update() {
        timeoutCounter++;
        
        if (channel.isOpen()) {
            Packet packet;
            while ((packet = packetQueue.poll()) != null) {
                if (packet.getPacketId() != 2 && packet.getPacketId() != 254) {
                	if (player != null) worker.acceptPacket(packet);
                } else {
                    worker.acceptPacket(packet);
                }
            }

            if (timeoutCounter >= TIMEOUT_TICKS) {
            	if (state == Connection.CONNECTING) {
            		disconnect("Took too long to log in");
            		return;
            	} else if (state == Connection.CONNECTED){
                    disconnect("Connection timed out");
                    return;
            	}
                keepAliveId = new Random().nextInt();
                send(new Packet0KeepAlive(keepAliveId));
                timeoutCounter = 0;
            }
        }
    }
    
    public void setTimeoutTicks(int ticks) {
    	timeoutCounter = ticks;
    }

    public void send(Packet packet) {
        try {
			channel.write(Packet.write(packet, ChannelBuffers.dynamicBuffer()));
		} catch (NotSupportedOperationException e) {
			e.printStackTrace();
		}
    }
    
    public void disconnect(String reason) {
        getServer().getSessionListClass().remove(this);
        if (channel.isOpen()) {
        	if (reason.equalsIgnoreCase(reason)) {
        		PlayerActionLogger.playerDisconnect(this);
        	} else {
        		PlayerActionLogger.playerKick(this);
        	}
        	channel.write(new Packet255Disconnect(reason)).addListener(ChannelFutureListener.CLOSE);
        }
        dispose();
        state = Connection.DISCONNECTED;
        channel.close();
    }

    public EpixServer getServer() {
        return server;
    }
    
    public InetSocketAddress getAddress() {
        SocketAddress addr = channel.getRemoteAddress();
        if (addr instanceof InetSocketAddress) {
            return (InetSocketAddress) addr;
        } else {
            return null;
        }
    }

    public void packetReceived(Packet packet) {
        packetQueue.add(packet);
    }

    public void dispose() {
        if (player != null) {            
            player = null;
        }
    }

    public int getKeepAliveId() {
        return keepAliveId;
    }
    
    public enum Connection {
        CONNECTING,
        CONNECTED,
        PLAYING,
        WAITING,
        DISCONNECTED,
        UNKNOWN
    }
}
