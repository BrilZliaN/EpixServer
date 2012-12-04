package org.Epixcrafted.EpixServer.protocol;

import org.jboss.netty.buffer.ChannelBuffer;

public class Packet1Login extends Packet {
	
	public int entityId;
	public String levelType;
	public byte gameMode;
	public byte dimension;
	public byte difficulty;
	public byte worldHeight;
	public byte maxPlayers;
	
	public Packet1Login(int entityId, String levelType, byte gameMode, byte dimension, byte difficulty, byte worldHeight, byte maxPlayers) {
		this.entityId = entityId;
		this.levelType = levelType;
		this.gameMode = gameMode;
		this.dimension = dimension;
		this.difficulty = difficulty;
		this.worldHeight = worldHeight;
		this.maxPlayers = maxPlayers;
	}

	@Override
	public int getPacketId() {
		return 0x1;
	}

	@Override
	public void get(ChannelBuffer buf) {
		throw new RuntimeException();
	}

	@Override
	public ChannelBuffer send(ChannelBuffer buf) {
		buf.writeInt(entityId);
        buf.writeShort(levelType.length());
        for(int i = 0; i < levelType.length(); ++i) {
            buf.writeChar(levelType.charAt(i));
        }
        buf.writeByte(gameMode);
        buf.writeByte(dimension);
        buf.writeByte(difficulty);
        buf.writeByte(worldHeight);
        buf.writeByte(maxPlayers);
        return buf;
	}

}
