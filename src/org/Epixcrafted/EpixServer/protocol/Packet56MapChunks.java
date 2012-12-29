package org.Epixcrafted.EpixServer.protocol;

import java.util.List;
import java.util.zip.Deflater;

import org.Epixcrafted.EpixServer.mc.world.Chunk;
import org.Epixcrafted.EpixServer.misc.NotSupportedOperationException;
import org.jboss.netty.buffer.ChannelBuffer;

public class Packet56MapChunks extends Packet {
	
	private int[] chunkPosX;
    private int[] chunkPosZ;
    public int[] chunkExistFlag;
    public int[] chunkHasAddSectionFlag;
    private byte[] chunkDataBuffer;
    private byte[][] compressedData;
    private int dataLength;
    private static byte[] chunkDataNotCompressed = new byte[0];

	public Packet56MapChunks(List chunks) {
		int c_size = chunks.size();
        this.chunkPosX = new int[c_size];
        this.chunkPosZ = new int[c_size];
        this.chunkExistFlag = new int[c_size];
        this.chunkHasAddSectionFlag = new int[c_size];
        this.compressedData = new byte[c_size][];
        int dataLength = 0;

        for (int numChunk = 0; numChunk < c_size; ++numChunk)
        {
            Chunk chunk = (Chunk)chunks.get(numChunk);
            Packet51MapChunkData chunkData = Packet51MapChunk.getMapChunkData(chunk, true, 65535);

            if (chunkDataNotCompressed.length < dataLength + chunkData.compressedData.length)
            {
                byte[] DataNotCompressedLength = new byte[dataLength + chunkData.compressedData.length];
                System.arraycopy(chunkDataNotCompressed, 0, DataNotCompressedLength, 0, chunkDataNotCompressed.length);
                chunkDataNotCompressed = DataNotCompressedLength;
            }

            System.arraycopy(chunkData.compressedData, 0, chunkDataNotCompressed, dataLength, chunkData.compressedData.length);
            dataLength += chunkData.compressedData.length;
            this.chunkPosX[numChunk] = chunk.x;
            this.chunkPosZ[numChunk] = chunk.z;
            this.chunkExistFlag[numChunk] = chunkData.chunkExistFlag;
            this.chunkHasAddSectionFlag[numChunk] = chunkData.chunkHasAddSectionFlag;
            this.compressedData[numChunk] = chunkData.compressedData;
        }

        Deflater deflater = new Deflater(-1);

        try
        {
        	deflater.setInput(chunkDataNotCompressed, 0, dataLength);
        	deflater.finish();
            this.chunkDataBuffer = new byte[dataLength];
            this.dataLength = deflater.deflate(this.chunkDataBuffer);
        }
        finally
        {
        	deflater.end();
        }
	}
	
	@Override
	public int getPacketId() {
		return 0x38;
	}

	@Override
	public void get(ChannelBuffer buf) throws NotSupportedOperationException {
		throw new NotSupportedOperationException();
	}

	@Override
	public ChannelBuffer send(ChannelBuffer buf)
			throws NotSupportedOperationException {
		buf.writeShort(this.chunkPosX.length);
        buf.writeInt(this.dataLength);
        buf.writeBytes(this.chunkDataBuffer);
        for (int count = 0; count < this.chunkPosX.length; ++count)
        {
            buf.writeInt(this.chunkPosX[count]);
            buf.writeInt(this.chunkPosZ[count]);
            buf.writeShort((short)(this.chunkExistFlag[count] & 65535));
            buf.writeShort((short)(this.chunkHasAddSectionFlag[count] & 65535));
        }
        return buf;
	}

}
