package org.Epixcrafted.EpixServer.mc.world;

import java.util.List;

import org.Epixcrafted.EpixServer.mc.Location;
import org.Epixcrafted.EpixServer.mc.block.Block;
import org.Epixcrafted.EpixServer.mc.entity.Entity;

public class World {
	
	public World() {
		
	}
	
	public String getWorldName() {
		return null;
	}
	
	public Location getSpawnLocation() {
		return new Location(0, 10, 0);
	}
	
	public void setSpawnLocation(Location location) {
		
	}
	
	public Chunk getChunkAt(int x, int z) {
		return null;
	}
	
	public Block getBlockAt(int x, int y, int z) {
		return null;
	}
	
	public boolean isChunkLoaded(int x, int z) {
		return false;
	}
	
	public Chunk[] getLoadedChunks() {
		return null;
	}
	
	public void loadChunk(int x, int y) {
		
	}
	
	public void unloadChunk(int x, int y) {
		
	}
	
	public int getTime() {
		return 0;
	}
	
	public void setTime(int time) {
		time = time > 24000 ? 24000 : time;
	}
	
	public Biome getBiome(int x, int z) {
		return null;
	}
	
	public void setBiome(int x, int y, Biome biome) {
		
	}
	
	public List<Entity> getEntities() {
		return null;
	}

	public void save() {
		
	}
	
	public long getSeed() {
		return 0;
	}
	
	public boolean getPVP() {
		return true;
	}
	
	public void setPVP(boolean pvp) {
		
	}
}
