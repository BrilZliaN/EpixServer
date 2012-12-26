package org.Epixcrafted.EpixServer.protocol;

import java.util.List;

import org.Epixcrafted.EpixServer.misc.NotSupportedOperationException;
import org.jboss.netty.buffer.ChannelBuffer;

public class Packet56MapChunks extends Packet {
	private int[] chunkPostX;
    private int[] chunkPosZ;
    public int[] field_73590_a;
    public int[] field_73588_b;

    /** The compressed chunk data buffer */
    private byte[] chunkDataBuffer;
    private byte[][] field_73584_f;

    /** total size of the compressed data */
    private int dataLength;
    private static byte[] chunkDataNotCompressed = new byte[0];

	public Packet56MapChunks(List chunks) {
		int var2 = par1List.size();
        this.chunkPostX = new int[var2];
        this.chunkPosZ = new int[var2];
        this.field_73590_a = new int[var2];
        this.field_73588_b = new int[var2];
        this.field_73584_f = new byte[var2][];
        int var3 = 0;

        for (int var4 = 0; var4 < var2; ++var4)
        {
            Chunk var5 = (Chunk)par1List.get(var4);
            Packet51MapChunkData var6 = Packet51MapChunk.getMapChunkData(var5, true, 65535);

            if (chunkDataNotCompressed.length < var3 + var6.compressedData.length)
            {
                byte[] var7 = new byte[var3 + var6.compressedData.length];
                System.arraycopy(chunkDataNotCompressed, 0, var7, 0, chunkDataNotCompressed.length);
                chunkDataNotCompressed = var7;
            }

            System.arraycopy(var6.compressedData, 0, chunkDataNotCompressed, var3, var6.compressedData.length);
            var3 += var6.compressedData.length;
            this.chunkPostX[var4] = var5.xPosition;
            this.chunkPosZ[var4] = var5.zPosition;
            this.field_73590_a[var4] = var6.chunkExistFlag;
            this.field_73588_b[var4] = var6.chunkHasAddSectionFlag;
            this.field_73584_f[var4] = var6.compressedData;
        }

        Deflater var11 = new Deflater(-1);

        try
        {
            var11.setInput(chunkDataNotCompressed, 0, var3);
            var11.finish();
            this.chunkDataBuffer = new byte[var3];
            this.dataLength = var11.deflate(this.chunkDataBuffer);
        }
        finally
        {
            var11.end();
        }
	}
	
	@Override
	public int getPacketId() {
		// TODO Auto-generated method stub
		return 0x38;
	}

	@Override
	public void get(ChannelBuffer buf) throws NotSupportedOperationException {
		throw new NotSupportedOperationException();
	}

	@Override
	public ChannelBuffer send(ChannelBuffer buf)
			throws NotSupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

}
