package org.Epixcrafted.EpixServer.tools.nbt;

import java.io.DataInput;
import java.io.IOException;

import org.jboss.netty.buffer.ChannelBuffer;

public class NBTTagEnd extends NBTBase
{
    public NBTTagEnd()
    {
        super((String)null);
    }

    /**
     * Read the actual data contents of the tag, implemented in NBT extension classes
     */
    void load(DataInput par1DataInput) throws IOException {}

    /**
     * Write the actual data contents of the tag, implemented in NBT extension classes
     */
    ChannelBuffer write(ChannelBuffer par1DataOutput) {
    	return par1DataOutput;
    }

    /**
     * Gets the type byte for the tag.
     */
    public byte getId()
    {
        return (byte)0;
    }

    public String toString()
    {
        return "END";
    }

    /**
     * Creates a clone of the tag.
     */
    public NBTBase copy()
    {
        return new NBTTagEnd();
    }
}
