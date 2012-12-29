package org.Epixcrafted.EpixServer.mc.world;

import org.Epixcrafted.EpixServer.mc.entity.Entity;
import org.Epixcrafted.EpixServer.mc.material.block.Block;

public class Chunk {

	public boolean deferRender; //пока не особо разбрался зачем, но по сути связанно с обновлением блоков
	
	public int x;
	public int z;

	private ExtendedBlockStorage[] storageArrays; //массив блоков

	private byte[] blockBiomeArray; //насколько я понял, содержит массив (16х16) с айди биомов и блоков
	
	public Chunk() {
		this.deferRender = false;
	}
	
	public World getWorld() {
		return null;
	}
	
	public int getX() {
		return this.x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getZ() {
		return this.z;
	}
	
	public void setZ(int z) {
		this.z = z;
	}
	
	public Block getBlock(int x, int y, int z) {
		return getWorld().getBlockAt(x, y, z);
	}
	
	public byte[] getBiomeArray()
    {
        return this.blockBiomeArray;
    }
	
	public void setBiomeArray(byte[] biome)
	{
		this.blockBiomeArray = biome;
	}
	
	public Entity[] getEntities() {
		return null;
	}
	
	public ExtendedBlockStorage[] getBlockStorageArray()
    {
        return this.storageArrays;
    }
}
