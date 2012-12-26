package org.Epixcrafted.EpixServer.protocol;

import java.util.zip.Deflater;

import org.Epixcrafted.EpixServer.mc.world.Chunk;
import org.Epixcrafted.EpixServer.misc.NotSupportedOperationException;
import org.jboss.netty.buffer.ChannelBuffer;

public class Packet51MapChunk extends Packet {
	
    public int xCh;
    public int zCh;
    public int yChMin;
    public int yChMax;
    private byte[] chunkData;
    /** The compressed chunk data */
    private byte[] compressedChunkData;
    public boolean includeInitialize;
    private int tempLength;
    private static byte[] temp = new byte[196864];

    public Packet51MapChunk(Chunk chunk, boolean par2, int par3)
    {
        this.xCh = chunk.getX();
        this.zCh = chunk.getZ();
        this.includeInitialize = par2;
        Packet51MapChunkData var4 = getMapChunkData(ñhunk, par2, par3);
        Deflater var5 = new Deflater(-1);
        this.yChMax = var4.chunkHasAddSectionFlag;
        this.yChMin = var4.chunkExistFlag;

        try
        {
            this.compressedChunkData = var4.compressedData;
            var5.setInput(var4.compressedData, 0, var4.compressedData.length);
            var5.finish();
            this.chunkData = new byte[var4.compressedData.length];
            this.tempLength = var5.deflate(this.chunkData);
        }
        finally
        {
            var5.end();
        }
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        this.xCh = par1DataInputStream.readInt();
        this.zCh = par1DataInputStream.readInt();
        this.includeInitialize = par1DataInputStream.readBoolean();
        this.yChMin = par1DataInputStream.readShort();
        this.yChMax = par1DataInputStream.readShort();
        this.tempLength = par1DataInputStream.readInt();

        if (temp.length < this.tempLength)
        {
            temp = new byte[this.tempLength];
        }

        par1DataInputStream.readFully(temp, 0, this.tempLength);
        int var2 = 0;
        int var3;

        for (var3 = 0; var3 < 16; ++var3)
        {
            var2 += this.yChMin >> var3 & 1;
        }

        var3 = 12288 * var2;

        if (this.includeInitialize)
        {
            var3 += 256;
        }

        this.compressedChunkData = new byte[var3];
        Inflater var4 = new Inflater();
        var4.setInput(temp, 0, this.tempLength);

        try
        {
            var4.inflate(this.compressedChunkData);
        }
        catch (DataFormatException var9)
        {
            throw new IOException("Bad compressed data format");
        }
        finally
        {
            var4.end();
        }
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(this.xCh);
        par1DataOutputStream.writeInt(this.zCh);
        par1DataOutputStream.writeBoolean(this.includeInitialize);
        par1DataOutputStream.writeShort((short)(this.yChMin & 65535));
        par1DataOutputStream.writeShort((short)(this.yChMax & 65535));
        par1DataOutputStream.writeInt(this.tempLength);
        par1DataOutputStream.write(this.chunkData, 0, this.tempLength);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleMapChunk(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 17 + this.tempLength;
    }

    public static Packet51MapChunkData getMapChunkData(Chunk par0Chunk, boolean par1, int par2)
    {
        int var3 = 0;
        ExtendedBlockStorage[] var4 = par0Chunk.getBlockStorageArray();
        int var5 = 0;
        Packet51MapChunkData var6 = new Packet51MapChunkData();
        byte[] var7 = temp;

        if (par1)
        {
            par0Chunk.deferRender = true;
        }

        int var8;

        for (var8 = 0; var8 < var4.length; ++var8)
        {
            if (var4[var8] != null && (!par1 || !var4[var8].isEmpty()) && (par2 & 1 << var8) != 0)
            {
                var6.chunkExistFlag |= 1 << var8;

                if (var4[var8].getBlockMSBArray() != null)
                {
                    var6.chunkHasAddSectionFlag |= 1 << var8;
                    ++var5;
                }
            }
        }

        for (var8 = 0; var8 < var4.length; ++var8)
        {
            if (var4[var8] != null && (!par1 || !var4[var8].isEmpty()) && (par2 & 1 << var8) != 0)
            {
                byte[] var9 = var4[var8].getBlockLSBArray();
                System.arraycopy(var9, 0, var7, var3, var9.length);
                var3 += var9.length;
            }
        }

        NibbleArray var10;

        for (var8 = 0; var8 < var4.length; ++var8)
        {
            if (var4[var8] != null && (!par1 || !var4[var8].isEmpty()) && (par2 & 1 << var8) != 0)
            {
                var10 = var4[var8].getMetadataArray();
                System.arraycopy(var10.data, 0, var7, var3, var10.data.length);
                var3 += var10.data.length;
            }
        }

        for (var8 = 0; var8 < var4.length; ++var8)
        {
            if (var4[var8] != null && (!par1 || !var4[var8].isEmpty()) && (par2 & 1 << var8) != 0)
            {
                var10 = var4[var8].getBlocklightArray();
                System.arraycopy(var10.data, 0, var7, var3, var10.data.length);
                var3 += var10.data.length;
            }
        }

        for (var8 = 0; var8 < var4.length; ++var8)
        {
            if (var4[var8] != null && (!par1 || !var4[var8].isEmpty()) && (par2 & 1 << var8) != 0)
            {
                var10 = var4[var8].getSkylightArray();
                System.arraycopy(var10.data, 0, var7, var3, var10.data.length);
                var3 += var10.data.length;
            }
        }

        if (var5 > 0)
        {
            for (var8 = 0; var8 < var4.length; ++var8)
            {
                if (var4[var8] != null && (!par1 || !var4[var8].isEmpty()) && var4[var8].getBlockMSBArray() != null && (par2 & 1 << var8) != 0)
                {
                    var10 = var4[var8].getBlockMSBArray();
                    System.arraycopy(var10.data, 0, var7, var3, var10.data.length);
                    var3 += var10.data.length;
                }
            }
        }

        if (par1)
        {
            byte[] var11 = par0Chunk.getBiomeArray();
            System.arraycopy(var11, 0, var7, var3, var11.length);
            var3 += var11.length;
        }

        var6.compressedData = new byte[var3];
        System.arraycopy(var7, 0, var6.compressedData, 0, var3);
        return var6;
    }
	@Override
	public int getPacketId() {
		// TODO Auto-generated method stub
		return 0x33;
	}

	@Override
	public void get(ChannelBuffer buf) throws NotSupportedOperationException {
		// TODO Auto-generated method stub

	}

	@Override
	public ChannelBuffer send(ChannelBuffer buf)
			throws NotSupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

}
