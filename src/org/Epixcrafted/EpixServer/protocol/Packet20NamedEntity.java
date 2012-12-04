package org.Epixcrafted.EpixServer.protocol;

import org.Epixcrafted.EpixServer.mc.entity.metadata.EntityMetadata;
import org.Epixcrafted.EpixServer.misc.NotSupportedOperationException;
import org.jboss.netty.buffer.ChannelBuffer;

public class Packet20NamedEntity extends Packet {
	
	public int entityId;
	public String name;
	public int x;
	public int y;
	public int z;
	public byte yaw;
	public byte pitch;
	public short heldItem;
	public EntityMetadata metadata;
	
	public Packet20NamedEntity(int entityId, String name, int x, int y, int z, byte yaw, byte pitch, short heldItem, EntityMetadata metadata) { 
		
	}

	@Override
	public int getPacketId() {
		return 0x14;
	}

	@Override
	public void get(ChannelBuffer buf) throws NotSupportedOperationException {
		throw new NotSupportedOperationException();
	}

	@Override
	public ChannelBuffer send(ChannelBuffer buf) {
		buf.writeInt(entityId);
		buf.writeShort(name.length());
		for (int i = 0; i < name.length(); ++i) {
			buf.writeChar(name.charAt(i));
		}
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeByte(yaw);
		buf.writeByte(pitch);
		buf.writeShort(heldItem);
		return metadata.writeMetadata(buf);
	}

}
