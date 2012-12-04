package org.Epixcrafted.EpixServer.mc.entity.metadata;

public class MetadataEntry<T> {

	private T entry;
	
	public MetadataEntry() {
		
	}
	
	public MetadataEntry(T entry) {
		this.entry = entry;
	}
	
	public T getEntry() {
		return entry;
	}
	
	public void setEntry(T entry) {
		this.entry = entry;
	}
}
