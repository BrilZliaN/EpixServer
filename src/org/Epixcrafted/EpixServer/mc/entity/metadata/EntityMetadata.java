package org.Epixcrafted.EpixServer.mc.entity.metadata;

import java.util.ArrayList;

import org.jboss.netty.buffer.ChannelBuffer;

@SuppressWarnings("rawtypes")
public class EntityMetadata {

	public ArrayList<MetadataEntry> entries;
	
	public EntityMetadata() {
		//entries = new ArrayList<MetadataEntry>();
	}
	
	public EntityMetadata(ArrayList<MetadataEntry> entries) {
		//this.entries = entries;
	}
	
	public void addEntry(MetadataEntry entry) {
		//this.entries.add(entry);
		throw new UnsupportedOperationException();
	}
	
	public void removeEntry(MetadataEntry entry) {
		//this.entries.remove(entry);
		throw new UnsupportedOperationException();
	}
	
	public ChannelBuffer writeMetadata(ChannelBuffer buf) {
		/*if (this.entries.size() != 0) {
			
		}*/
		buf.writeByte(((0 << 5) | 0));
		buf.writeByte(0);
		buf.writeByte(127);
		return buf;
	}
}
