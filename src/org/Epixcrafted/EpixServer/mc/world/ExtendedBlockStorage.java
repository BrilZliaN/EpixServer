package org.Epixcrafted.EpixServer.mc.world;

import org.Epixcrafted.EpixServer.mc.block.Block;

public class ExtendedBlockStorage
{
    
    private int yBase;
    private int blockRefCount;
    private int tickRefCount;
    private byte[] blockLSBArray;
    private NibbleArray blockMSBArray;
    private NibbleArray blockMetadataArray;private NibbleArray blocklightArray;private NibbleArray skylightArray;

    public ExtendedBlockStorage(int par1)
    {
        this.yBase = par1;
        this.blockLSBArray = new byte[4096];
        this.blockMetadataArray = new NibbleArray(this.blockLSBArray.length, 4);
        this.skylightArray = new NibbleArray(this.blockLSBArray.length, 4);
        this.blocklightArray = new NibbleArray(this.blockLSBArray.length, 4);
    }

    
    public int getExtBlockID(int par1, int par2, int par3)
    {
        int var4 = this.blockLSBArray[par2 << 8 | par3 << 4 | par1] & 255;
        return this.blockMSBArray != null ? this.blockMSBArray.get(par1, par2, par3) << 8 | var4 : var4;
    }

    /**
     * Sets the extended block ID for a location in a chunk, splitting bits 11..8 into a NibbleArray and bits 7..0 into
     * a byte array. Also performs reference counting to determine whether or not to broadly cull this Chunk from the
     * random-update tick list.
     */
    public void setExtBlockID(int x, int y, int z, int id)
    {
        int var5 = this.blockLSBArray[y << 8 | z << 4 | x] & 255;

        if (this.blockMSBArray != null)
        {
            var5 |= this.blockMSBArray.get(x, y, z) << 8;
        }

        if (var5 == 0 && id != 0)
        {
            ++this.blockRefCount;

            if (Block.blocksList[id] != null && Block.blocksList[id].getTickRandomly())
            {
                ++this.tickRefCount;
            }
        }
        else if (var5 != 0 && id == 0)
        {
            --this.blockRefCount;

            if (Block.blocksList[var5] != null && Block.blocksList[var5].getTickRandomly())
            {
                --this.tickRefCount;
            }
        }
        else if (Block.blocksList[var5] != null && Block.blocksList[var5].getTickRandomly() && (Block.blocksList[id] == null || !Block.blocksList[id].getTickRandomly()))
        {
            --this.tickRefCount;
        }
        else if ((Block.blocksList[var5] == null || !Block.blocksList[var5].getTickRandomly()) && Block.blocksList[id] != null && Block.blocksList[id].getTickRandomly())
        {
            ++this.tickRefCount;
        }

        this.blockLSBArray[y << 8 | z << 4 | x] = (byte)(id & 255);

        if (id > 255)
        {
            if (this.blockMSBArray == null)
            {
                this.blockMSBArray = new NibbleArray(this.blockLSBArray.length, 4);
            }

            this.blockMSBArray.set(par1, par2, par3, (par4 & 3840) >> 8);
        }
        else if (this.blockMSBArray != null)
        {
            this.blockMSBArray.set(par1, par2, par3, 0);
        }
    }

    /**
     * Returns the metadata associated with the block at the given coordinates in this ExtendedBlockStorage.
     */
    public int getExtBlockMetadata(int x, int y, int z)
    {
        return this.blockMetadataArray.get(x, y, z);
    }

    
    public void setExtBlockMetadata(int x, int y, int z, int id)
    {
        this.blockMetadataArray.set(x, y, z, id);
    }

    /**
     * Returns whether or not this block storage's Chunk is fully empty, based on its internal reference count.
     */
    public boolean isEmpty()
    {
        return this.blockRefCount == 0;
    }

    /**
     * Returns whether or not this block storage's Chunk will require random ticking, used to avoid looping through
     * random block ticks when there are no blocks that would randomly tick.
     */
    public boolean getNeedsRandomTick()
    {
        return this.tickRefCount > 0;
    }

    /**
     * Returns the Y location of this ExtendedBlockStorage.
     */
    public int getYLocation()
    {
        return this.yBase;
    }

    /**
     * Sets the saved Sky-light value in the extended block storage structure.
     */
    public void setExtSkylightValue(int par1, int par2, int par3, int par4)
    {
        this.skylightArray.set(par1, par2, par3, par4);
    }

    /**
     * Gets the saved Sky-light value in the extended block storage structure.
     */
    public int getExtSkylightValue(int par1, int par2, int par3)
    {
        return this.skylightArray.get(par1, par2, par3);
    }

    /**
     * Sets the saved Block-light value in the extended block storage structure.
     */
    public void setExtBlocklightValue(int par1, int par2, int par3, int par4)
    {
        this.blocklightArray.set(par1, par2, par3, par4);
    }

    /**
     * Gets the saved Block-light value in the extended block storage structure.
     */
    public int getExtBlocklightValue(int par1, int par2, int par3)
    {
        return this.blocklightArray.get(par1, par2, par3);
    }

    public void removeInvalidBlocks()
    {
        this.blockRefCount = 0;
        this.tickRefCount = 0;

        for (int var1 = 0; var1 < 16; ++var1)
        {
            for (int var2 = 0; var2 < 16; ++var2)
            {
                for (int var3 = 0; var3 < 16; ++var3)
                {
                    int var4 = this.getExtBlockID(var1, var2, var3);

                    if (var4 > 0)
                    {
                        if (Block.blocksList[var4] == null)
                        {
                            this.blockLSBArray[var2 << 8 | var3 << 4 | var1] = 0;

                            if (this.blockMSBArray != null)
                            {
                                this.blockMSBArray.set(var1, var2, var3, 0);
                            }
                        }
                        else
                        {
                            ++this.blockRefCount;

                            if (Block.blocksList[var4].getTickRandomly())
                            {
                                ++this.tickRefCount;
                            }
                        }
                    }
                }
            }
        }
    }

    public byte[] getBlockLSBArray()
    {
        return this.blockLSBArray;
    }

    /**
     * Returns the block ID MSB (bits 11..8) array for this storage array's Chunk.
     */
    public NibbleArray getBlockMSBArray()
    {
        return this.blockMSBArray;
    }

    public NibbleArray getMetadataArray()
    {
        return this.blockMetadataArray;
    }

    /**
     * Returns the NibbleArray instance containing Block-light data.
     */
    public NibbleArray getBlocklightArray()
    {
        return this.blocklightArray;
    }

    /**
     * Returns the NibbleArray instance containing Sky-light data.
     */
    public NibbleArray getSkylightArray()
    {
        return this.skylightArray;
    }

    /**
     * Sets the array of block ID least significant bits for this ExtendedBlockStorage.
     */
    public void setBlockLSBArray(byte[] par1ArrayOfByte)
    {
        this.blockLSBArray = par1ArrayOfByte;
    }

    /**
     * Sets the array of blockID most significant bits (blockMSBArray) for this ExtendedBlockStorage.
     */
    public void setBlockMSBArray(NibbleArray par1NibbleArray)
    {
        this.blockMSBArray = par1NibbleArray;
    }

    /**
     * Sets the NibbleArray of block metadata (blockMetadataArray) for this ExtendedBlockStorage.
     */
    public void setBlockMetadataArray(NibbleArray par1NibbleArray)
    {
        this.blockMetadataArray = par1NibbleArray;
    }

    /**
     * Sets the NibbleArray instance used for Block-light values in this particular storage block.
     */
    public void setBlocklightArray(NibbleArray par1NibbleArray)
    {
        this.blocklightArray = par1NibbleArray;
    }

    /**
     * Sets the NibbleArray instance used for Sky-light values in this particular storage block.
     */
    public void setSkylightArray(NibbleArray par1NibbleArray)
    {
        this.skylightArray = par1NibbleArray;
    }
}
