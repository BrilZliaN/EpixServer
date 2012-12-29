package org.Epixcrafted.EpixServer.mc.material.block;

import java.util.ArrayList;

public class BlockList {
	
	public static ArrayList<Block> list;
	
	public BlockList() {
		this.add(new BlockStone(1));
	}
	
	public void add(EpixBlock block) {
		list.set(block.getId(), block);
	}
	
	public static Block get(int id) {
		return list.get(id);
	}
}
