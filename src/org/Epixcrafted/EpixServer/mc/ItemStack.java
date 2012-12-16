package org.Epixcrafted.EpixServer.mc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import org.Epixcrafted.EpixServer.tools.nbt.NBTBase;
import org.Epixcrafted.EpixServer.tools.nbt.NBTTagCompound;
import org.jboss.netty.buffer.ChannelBuffer;

public class ItemStack {
	
	private int id;
	private byte count;
	private short damage;
	private NBTTagCompound enchantment;
	
	public ItemStack(int id) {
		this(id, (byte)1, (short)0);
	}
	
	public ItemStack(int id, byte count, short damage) {
		this(id,  count, damage, null);
	}
	
	public ItemStack(int id, byte count, short damage, NBTTagCompound enchantment) {
		this.setId(id);
		this.setCount(count);
		this.setDamage(damage);
		this.setEnchantment(enchantment);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte getCount() {
		return count;
	}

	public void setCount(byte count) {
		this.count = count;
	}

	public short getDamage() {
		return damage;
	}

	public void setDamage(short damage) {
		this.damage = damage;
	}

	public NBTTagCompound getEnchantment() {
		return enchantment;
	}

	public void setEnchantment(NBTTagCompound enchantment) {
		this.enchantment = enchantment;
	}
	
	public ChannelBuffer write(ChannelBuffer buf) {
		buf.writeShort(this.getId());
		if (this.getId() != -1) {
			buf.writeByte(this.getCount());
			buf.writeShort(this.getDamage());
			if (this.getEnchantment() != null) {
				buf = NBTBase.writeNamedTag(this.getEnchantment(), buf);
			}
		}
		return buf;
	}
	
	public static ItemStack readSlot(ChannelBuffer buf) {
		short id = buf.readShort();
		if (id == -1) {
			return new ItemStack(id);
		}
		byte itemCount = buf.readByte();
		short itemDamage = buf.readShort();
		short length = buf.readShort();
		if (length == -1) {
			return new ItemStack(id, itemCount, itemDamage);
		}
		byte[] zippedTags = new byte[length];
		buf.readBytes(zippedTags, 0, length);
		Inflater decompressor = new Inflater();
		decompressor.setInput(zippedTags);
		ByteArrayOutputStream baos = new ByteArrayOutputStream(zippedTags.length);
		byte[] buffer = new byte[zippedTags.length*4];
		while (!decompressor.finished()) {
			int newLength = 0;
			try {
				newLength = decompressor.inflate(buffer);
			} catch (DataFormatException e) {
				e.printStackTrace();
			}
			baos.write(buffer, 0, newLength);
		}
		try {
			baos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		NBTTagCompound tags = null;
		try {
			tags = (NBTTagCompound) NBTTagCompound.readNamedTag((DataInput) new ByteArrayInputStream(baos.toByteArray()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ItemStack(id, itemCount, itemDamage, tags);
	}
}
