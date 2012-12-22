package org.Epixcrafted.EpixServer.mc.material.block;

import org.Epixcrafted.EpixServer.mc.Location;
import org.Epixcrafted.EpixServer.mc.material.Material;
import org.Epixcrafted.EpixServer.mc.world.Biome;
import org.Epixcrafted.EpixServer.mc.world.Chunk;
import org.Epixcrafted.EpixServer.mc.world.World;

public interface Block {
	
	public int getId();
	
	public Material getMaterial();
	
	public byte getData();
	
	public int getX();
	
	public int getY();
	
	public int getZ();

	public Location getLocation();
	
	public Biome getBiome();
	
	public Chunk getChunk();
	
	public World getWorld();
	
	public boolean isLiquid();
	
	public boolean isSolid();
	
	public boolean isTransparent();
	
	public boolean isEdible();
	
	public boolean isPowered();
	
	public void setId(int id);
	
	public void setMaterial(Material material);
	
	public void setData(byte data);
	
	public void setX(int x);
	
	public void setY(int y);
	
	public void setZ(int z);
	
	public void setLocation(Location location);
	
	public void setBiome(Biome biome);
	
	public void setWorld(World world);
}
