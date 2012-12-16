package org.Epixcrafted.EpixServer.mc;

public class MetadataEntry<T> {

	private int type;
	private int id;
	private T entry;
	
	public MetadataEntry(int type, int id, T entry) {
		this.type = type;
		this.id = id;
		this.entry = entry;
	}
	
	public int getType() {
		return type;
	}
	
	public int getId() {
		return id;
	}
	
	public T getEntry() {
		return entry;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public void setId(int id) {
		this.id = id;
	}
}
