package org.Epixcrafted.EpixServer.protocol;

import org.Epixcrafted.EpixServer.mc.ItemStack;
import org.Epixcrafted.EpixServer.misc.NotSupportedOperationException;
import org.jboss.netty.buffer.ChannelBuffer;

public class Packet15Place extends Packet {
	
	public int x;
	public short y;
	public int z;
	public byte direction;
	public ItemStack heldItem;
	public byte cx;
	public byte cy;
	public byte cz;

	@Override
	public int getPacketId() {
		return 0xF;
	}

	@Override
	public void get(ChannelBuffer buf) {
		x = buf.readInt();
		y = buf.readUnsignedByte();
		z = buf.readInt();
		direction = buf.readByte();
		heldItem = ItemStack.readSlot(buf);
		cx = buf.readByte();
		cy = buf.readByte();
		cz = buf.readByte();
	}

	@Override
	public ChannelBuffer send(ChannelBuffer buf) throws NotSupportedOperationException {
		throw new NotSupportedOperationException();
	}

}
