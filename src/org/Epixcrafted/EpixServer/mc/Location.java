package org.Epixcrafted.EpixServer.mc;

public class Location {
	
	private double x;
	private double y;
	private double z;
	private double yaw;
	private double pitch;
	
	public Location(double x, double y, double z) {
		this(x, y, z, 0, 0);
	}
	
	public Location(int x, int y, int z) {
		this(x, y, z, 0, 0);
	}
	
	public Location(double x, double y, double z, float yaw, float pitch) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public double getYaw() {
		return yaw;
	}

	public void setYaw(double yaw) {
		this.yaw = yaw;
	}

	public double getPitch() {
		return pitch;
	}

	public void setPitch(double pitch) {
		this.pitch = pitch;
	}

}
