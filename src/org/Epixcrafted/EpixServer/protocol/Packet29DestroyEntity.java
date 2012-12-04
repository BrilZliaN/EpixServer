package org.Epixcrafted.EpixServer.protocol;

import org.Epixcrafted.EpixServer.misc.NotSupportedOperationException;
import org.jboss.netty.buffer.ChannelBuffer;

public class Packet29DestroyEntity extends Packet {
	
	public byte count;
	public int[] entityIds;
	
	public Packet29DestroyEntity(byte count, int[] entityIds) {
		this.count = count;
		this.entityIds = entityIds;
	}

	@Override
	public int getPacketId() {
		return 0x1D;
	}

	@Override
	public void get(ChannelBuffer buf) throws NotSupportedOperationException {
		throw new NotSupportedOperationException();
	}

	@Override
	public ChannelBuffer send(ChannelBuffer buf) {
		buf.writeByte(count);
		for (int entityId : entityIds) {
			buf.writeInt(entityId);
		}
		return buf;
	}

}
