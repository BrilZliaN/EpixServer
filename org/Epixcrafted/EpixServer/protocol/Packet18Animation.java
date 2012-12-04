package org.Epixcrafted.EpixServer.protocol;

import org.jboss.netty.buffer.ChannelBuffer;

public class Packet18Animation extends Packet {
	
	public int entityId;
	public byte type;
	
	public Packet18Animation() {
		
	}
	
	public Packet18Animation(int entityId, byte type) {
		this.entityId = entityId;
		this.type = type;
	}

	@Override
	public int getPacketId() {
		return 0x12;
	}

	@Override
	public void get(ChannelBuffer buf) {
		entityId = buf.readInt();
		type = buf.readByte();
	}

	@Override
	public ChannelBuffer send(ChannelBuffer buf) {
		buf.writeInt(entityId);
		buf.writeByte(type);
		return buf;
	}

}
