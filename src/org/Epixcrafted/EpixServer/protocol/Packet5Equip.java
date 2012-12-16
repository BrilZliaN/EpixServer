package org.Epixcrafted.EpixServer.protocol;

import org.Epixcrafted.EpixServer.mc.ItemStack;
import org.Epixcrafted.EpixServer.misc.NotSupportedOperationException;
import org.jboss.netty.buffer.ChannelBuffer;

@SuppressWarnings("unused")
public class Packet5Equip extends Packet {
	
	private int entityId;
	private short slot;
	private ItemStack item;

	@Override
	public int getPacketId() {
		return 0x5;
	}

	@Override
	public void get(ChannelBuffer buf) throws NotSupportedOperationException {
		throw new NotSupportedOperationException();
	}

	@Override
	public ChannelBuffer send(ChannelBuffer buf) throws NotSupportedOperationException {
		throw new NotSupportedOperationException(); //not currently supported
	}

}
