package org.Epixcrafted.EpixServer.mc.material.block;

import org.Epixcrafted.EpixServer.mc.Location;
import org.Epixcrafted.EpixServer.mc.material.Material;
import org.Epixcrafted.EpixServer.mc.world.Biome;
import org.Epixcrafted.EpixServer.mc.world.Chunk;
import org.Epixcrafted.EpixServer.mc.world.World;

public class EpixBlock implements Block {
	
	private int id;
	private byte data;
	private int x;
	private int y;
	private int z;
	private boolean needsRandomTick;

	public EpixBlock(int id) {
		this.id = id;
	}
	
	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public Material getMaterial() {
		return Material.getMaterial(this.id);
	}

	@Override
	public byte getData() {
		return this.data;
	}

	@Override
	public int getX() {
		return this.x;
	}

	@Override
	public int getY() {
		return this.y;
	}

	@Override
	public int getZ() {
		return this.z;
	}

	@Override
	public Location getLocation() {
		return new Location(x, y, z);
	}

	@Override
	public Biome getBiome() {
		return null;
	}

	@Override
	public Chunk getChunk() {
		return null;
	}

	@Override
	public World getWorld() {
		return null;
	}

	@Override
	public boolean isLiquid() {
		return getMaterial() == Material.WATER ||
				    getMaterial() == Material.STATIONARY_WATER ||
				    getMaterial() == Material.LAVA ||
				    getMaterial() == Material.STATIONARY_LAVA;
	}

	@Override
	public boolean isSolid() {
		return getMaterial().isSolid();
	}

	@Override
	public boolean isTransparent() {
		return getMaterial().isTransparent();
	}
	
	@Override
	public boolean isEdible() {
		return getMaterial().isEdible();
	}

	@Override
	public boolean isPowered() {
		return false;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public void setMaterial(Material material) {
		this.id = material.getId();
		
	}

	@Override
	public void setData(byte data) {
		this.data = data;
	}

	@Override
	public void setX(int x) {
		this.x = x;
	}

	@Override
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public void setZ(int z) {
		this.z = z;
	}

	@Override
	public void setLocation(Location location) {
		this.x = (int) location.getX();
		this.y = (int) location.getY();
		this.z = (int) location.getZ();
	}

	@Override
	public void setBiome(Biome biome) {
		//TODO
	}

	@Override
	public void setWorld(World world) {
		// TODO
	}
	
	public void setTickRandomly(boolean need)
    {
        this.needsRandomTick = need;
    }

    /**
     * Returns whether or not this block is of a type that needs random ticking. Called for ref-counting purposes by
     * ExtendedBlockStorage in order to broadly cull a chunk from the random chunk update list for efficiency's sake.
     */
    public boolean getTickRandomly()
    {
        return this.needsRandomTick;
    }

}
