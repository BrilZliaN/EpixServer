package org.Epixcrafted.EpixServer.protocol;

import java.io.IOException;
import org.jboss.netty.buffer.ChannelBuffer;

public abstract class Packet {
	
	public static Packet read(ChannelBuffer buf) throws IOException {
		int id = buf.readByte();
		if (id < 0) id = 256 + id;
		Packet packet = getPacket(id);
		if (packet == null) throw new IOException("Bad packet id: " + id);
		packet.get(buf);
		return packet;
	}
	
	public static ChannelBuffer write(Packet packet, ChannelBuffer buf) {
		buf.writeByte((byte)packet.getPacketId());
		return packet.send(buf);
	}
	
	public static Packet getPacket(int id) {
		if (id == 0x0) return new Packet0KeepAlive();
		if (id == 0x2) return new Packet2Handshake();
		if (id == 0x3) return new Packet3Chat();
		if (id == 0x7) return new Packet7UseEntity();
		if (id == 0xA) return new Packet10Fly();
		if (id == 0xB) return new Packet11Pos();
		if (id == 0xC) return new Packet12Look();
		if (id == 0xD) return new Packet13PosLook();
		if (id == 0xE) return new Packet14Dig();
		if (id == 0xF) return new Packet15Place();
		if (id == 0x10) return new Packet16HeldChange();
		if (id == 0x11) return new Packet17UseBed();
		if (id == 0x12) return new Packet18Animation();
		if (id == 0x13) return new Packet19Action();
		if (id == 0xCA) return new Packet202Abilities();
		if (id == 0xCC) return new Packet204Settings();
		if (id == 0xFE) return new Packet254Ping();
		if (id == 0xFF) return new Packet255Disconnect();
		
		return null;
	}
	
	public abstract int getPacketId();
	public abstract void get(ChannelBuffer buf);
	public abstract ChannelBuffer send(ChannelBuffer buf);

}
