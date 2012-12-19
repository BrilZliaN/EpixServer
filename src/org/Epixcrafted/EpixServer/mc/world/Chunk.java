package org.Epixcrafted.EpixServer.mc.world;

import org.Epixcrafted.EpixServer.mc.block.Block;
import org.Epixcrafted.EpixServer.mc.entity.Entity;

public class Chunk {

	public World getWorld() {
		return null;
	}
	
	public int getX() {
		return 0;
	}
	
	public int getZ() {
		return 0;
	}
	
	public Block getBlock(int x, int y, int z) {
		return getWorld().getBlockAt(x, y, z);
	}
	
	public Entity[] getEntities() {
		return null;
	}
}
