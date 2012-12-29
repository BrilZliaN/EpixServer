package org.Epixcrafted.EpixServer.mc.entity;

public interface Entity {

	public int getEntityId();
	public double getX();
	public double getY();
	public double getZ();
	public short getHealth();
	public void setX(double x);
	public void setY(double y);
	public void setZ(double z);
	public void setHealth(short health);
	public void onUpdate();
}
