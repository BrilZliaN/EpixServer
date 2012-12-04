package org.Epixcrafted.EpixServer.mc.entity.metadata;

import java.util.LinkedHashMap;
import java.util.LinkedList;

@SuppressWarnings("rawtypes")
public class IdTypeValueMapper {
	
	public LinkedList<Byte> idList;
	public LinkedHashMap typeValue;
	
	public IdTypeValueMapper() {
		idList = new LinkedList<Byte>();
		typeValue = new LinkedHashMap();
	}
	
	public void add(byte id, byte type, Object obj) {
		 
	}

}
