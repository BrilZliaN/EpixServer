package org.Epixcrafted.EpixServer.protocol;

import org.jboss.netty.buffer.ChannelBuffer;

public class Packet13PosLook extends Packet {
	
	public double x;
	public double y;
	public double stance;
	public double z;
	public float yaw;
	public float pitch;
	public boolean onGround;
	
	public Packet13PosLook() {
		
	}
	
	public Packet13PosLook(double x, double stance, double y, double z, float yaw, float pitch, boolean onGround) {
		this.x = x;
		this.stance = stance;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
		this.onGround = onGround;
	}

	@Override
	public int getPacketId() {
		return 0xD;
	}

	@Override
	public void get(ChannelBuffer buf) {
		x = buf.readDouble();
		y = buf.readDouble();
		stance = buf.readDouble();
		z = buf.readDouble();
		yaw = buf.readFloat();
		pitch = buf.readFloat();
		onGround = buf.readByte() == 0x1 ? true : false;
	}

	@Override
	public ChannelBuffer send(ChannelBuffer buf) {
		buf.writeDouble(x);
		buf.writeDouble(stance);
		buf.writeDouble(y);
		buf.writeDouble(z);
		buf.writeFloat(yaw);
		buf.writeFloat(pitch);
		buf.writeByte(onGround ? 0x1 : 0x0);
		return buf;
	}
	
}
