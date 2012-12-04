package org.Epixcrafted.EpixServer.protocol;

import org.jboss.netty.buffer.ChannelBuffer;

public class Packet9Respawn extends Packet {
	
	public int dimension;
	public byte difficulty;
	public byte gameMode;
	public short height;
	public String levelType;
	
	public Packet9Respawn(int dimension, byte difficulty, byte gameMode, short height, String levelType) {
		this.dimension = dimension;
		this.difficulty = difficulty;
		this.gameMode = gameMode;
		this.height = height;
		this.levelType = levelType;
	}

	@Override
	public int getPacketId() {
		return 0x9;
	}

	@Override
	public void get(ChannelBuffer buf) {
		throw new RuntimeException();
	}

	@Override
	public ChannelBuffer send(ChannelBuffer buf) {
		buf.writeInt(dimension);
		buf.writeByte(difficulty);
		buf.writeByte(gameMode);
		buf.writeShort(height);
		buf.writeShort(levelType.length());
        for(int i = 0; i < levelType.length(); ++i) {
            buf.writeChar(levelType.charAt(i));
        }
		return buf;
	}

}
