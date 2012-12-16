package org.Epixcrafted.EpixServer.mc;

import java.util.ArrayList;

import org.jboss.netty.buffer.ChannelBuffer;

@SuppressWarnings("rawtypes")
public class EntityMetadata {

	public ArrayList<MetadataEntry> entries;
	
	public EntityMetadata() {
		entries = new ArrayList<MetadataEntry>();
	}
	
	public EntityMetadata(ArrayList<MetadataEntry> entries) {
		this.entries = entries;
	}
	
	public void addEntry(MetadataEntry entry) {
		this.entries.add(entry);
	}
	
	public void removeEntry(MetadataEntry entry) {
		this.entries.remove(entry);
	}
	
	public ChannelBuffer writeMetadata(ChannelBuffer buf) {
		for (MetadataEntry<?> entry : entries) {
			int header = (entry.getType() << 5 | entry.getId() & 0x1f) & 0xff;
			buf.writeByte(header);
			switch(entry.getId()) {
			case 0:
				buf.writeByte((Integer)entry.getEntry());
				break;
			case 1:
				buf.writeShort((Short)entry.getEntry());
				break;
			case 2:
				buf.writeInt((Integer)entry.getEntry());
				break;
			case 3:
				buf.writeFloat((Float)entry.getEntry());
				break;
			case 4:
				String string = (String) entry.getEntry();
				buf.writeShort(string.length());
		        for(int i = 0; i < string.length(); ++i) {
		            buf.writeChar(string.charAt(i));
		        }
			case 5:
				((ItemStack)entry.getEntry()).write(buf);
				break;
			case 6:
				//TODO location x,y,z support
				break;
			}
		}
		buf.writeByte(127);
		return buf;
	}
}
