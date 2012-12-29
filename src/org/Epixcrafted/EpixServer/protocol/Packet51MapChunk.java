package org.Epixcrafted.EpixServer.protocol;

import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.Epixcrafted.EpixServer.mc.world.Chunk;
import org.Epixcrafted.EpixServer.mc.world.ExtendedBlockStorage;
import org.Epixcrafted.EpixServer.mc.world.NibbleArray;
import org.Epixcrafted.EpixServer.misc.NotSupportedOperationException;
import org.jboss.netty.buffer.ChannelBuffer;

public class Packet51MapChunk extends Packet {
	
    public int xCh;
    public int zCh;
    public int yChMin;
    public int yChMax;
    private byte[] chunkData;
    private byte[] compressedChunkData;
    public boolean includeInitialize;
    private int tempLength;
    private static byte[] temp = new byte[196864];

    public Packet51MapChunk(Chunk chunk, boolean includeInitialize, int par3)
    {
        this.xCh = chunk.getX();
        this.zCh = chunk.getZ();
        this.includeInitialize = includeInitialize;
        Packet51MapChunkData chunkData = getMapChunkData(chunk, includeInitialize, par3);
        Deflater deflater = new Deflater(-1);
        this.yChMax = chunkData.chunkHasAddSectionFlag;
        this.yChMin = chunkData.chunkExistFlag;

        try
        {
            this.compressedChunkData = chunkData.compressedData;
            deflater.setInput(chunkData.compressedData, 0, chunkData.compressedData.length);
            deflater.finish();
            this.chunkData = new byte[chunkData.compressedData.length];
            this.tempLength = deflater.deflate(this.chunkData);
        }
        finally
        {
        	deflater.end();
        }
    }


    public static Packet51MapChunkData getMapChunkData(Chunk chunk, boolean includeInitialize, int par2)
    {
        int compressedDataLength = 0;
        ExtendedBlockStorage[] blockStorage = chunk.getBlockStorageArray();
        int var5 = 0;
        Packet51MapChunkData mapChunkData = new Packet51MapChunkData();
        byte[] tmp = temp;

        if (includeInitialize)
        {
            chunk.deferRender = true;
        }

        int count;

        for (count = 0; count < blockStorage.length; ++count)
        {
            if (blockStorage[count] != null && (!includeInitialize || !blockStorage[count].isEmpty()) && (par2 & 1 << count) != 0)
            {
                mapChunkData.chunkExistFlag |= 1 << count;

                if (blockStorage[count].getBlockMSBArray() != null)
                {
                	mapChunkData.chunkHasAddSectionFlag |= 1 << count;
                    ++var5;
                }
            }
        }

        for (count = 0; count < blockStorage.length; ++count)
        {
            if (blockStorage[count] != null && (!includeInitialize || !blockStorage[count].isEmpty()) && (par2 & 1 << count) != 0)
            {
                byte[] blockLSBArray = blockStorage[count].getBlockLSBArray();
                System.arraycopy(blockLSBArray, 0, tmp, compressedDataLength, blockLSBArray.length);
                compressedDataLength += blockLSBArray.length;
            }
        }

        NibbleArray metadataArray;

        for (count = 0; count < blockStorage.length; ++count)
        {
            if (blockStorage[count] != null && (!includeInitialize || !blockStorage[count].isEmpty()) && (par2 & 1 << count) != 0)
            {
                metadataArray = blockStorage[count].getMetadataArray();
                System.arraycopy(metadataArray.data, 0, tmp, compressedDataLength, metadataArray.data.length);
                compressedDataLength += metadataArray.data.length;
            }
        }

        for (count = 0; count < blockStorage.length; ++count)
        {
            if (blockStorage[count] != null && (!includeInitialize || !blockStorage[count].isEmpty()) && (par2 & 1 << count) != 0)
            {
                metadataArray = blockStorage[count].getBlocklightArray();
                System.arraycopy(metadataArray.data, 0, tmp, compressedDataLength, metadataArray.data.length);
                compressedDataLength += metadataArray.data.length;
            }
        }

        for (count = 0; count < blockStorage.length; ++count)
        {
            if (blockStorage[count] != null && (!includeInitialize || !blockStorage[count].isEmpty()) && (par2 & 1 << count) != 0)
            {
                metadataArray = blockStorage[count].getSkylightArray();
                System.arraycopy(metadataArray.data, 0, tmp, compressedDataLength, metadataArray.data.length);
                compressedDataLength += metadataArray.data.length;
            }
        }

        if (var5 > 0)
        {
            for (count = 0; count < blockStorage.length; ++count)
            {
                if (blockStorage[count] != null && (!includeInitialize || !blockStorage[count].isEmpty()) && blockStorage[count].getBlockMSBArray() != null && (par2 & 1 << count) != 0)
                {
                    metadataArray = blockStorage[count].getBlockMSBArray();
                    System.arraycopy(metadataArray.data, 0, tmp, compressedDataLength, metadataArray.data.length);
                    compressedDataLength += metadataArray.data.length;
                }
            }
        }

        if (includeInitialize)
        {
            byte[] biomeArray = chunk.getBiomeArray();
            System.arraycopy(biomeArray, 0, tmp, compressedDataLength, biomeArray.length);
            compressedDataLength += biomeArray.length;
        }

        mapChunkData.compressedData = new byte[compressedDataLength];
        System.arraycopy(tmp, 0, mapChunkData.compressedData, 0, compressedDataLength);
        return mapChunkData;
    }
	
    @Override
	public int getPacketId() {
		return 0x33;
	}

	@Override
	public void get(ChannelBuffer buf) throws NotSupportedOperationException {
		throw new NotSupportedOperationException();
	}

	@Override
	public ChannelBuffer send(ChannelBuffer buf)
			throws NotSupportedOperationException {
		buf.writeInt(this.xCh);
		buf.writeInt(this.zCh);
		buf.writeByte(includeInitialize ? 1 : 0);
		buf.writeShort((short)(this.yChMin & 65535));
		buf.writeShort((short)(this.yChMax & 65535));
		buf.writeInt(this.tempLength);
		buf.writeBytes(this.chunkData, 0, this.tempLength);
		return buf;
	}

}
