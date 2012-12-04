package org.Epixcrafted.EpixServer.protocol;

import org.Epixcrafted.EpixServer.mc.item.ItemStack;
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
	public void get(ChannelBuffer buf) {
		throw new RuntimeException();
	}

	@Override
	public ChannelBuffer send(ChannelBuffer buf) {
		throw new RuntimeException(); //not currently supported
	}

}
