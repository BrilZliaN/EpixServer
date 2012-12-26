package org.Epixcrafted.EpixServer.mc.block;

import java.util.ArrayList;

public class BlockList {
	
	public ArrayList<Block> list;
	
	public BlockList() {
		list.set(1, new EpixBlock());
	}
}
