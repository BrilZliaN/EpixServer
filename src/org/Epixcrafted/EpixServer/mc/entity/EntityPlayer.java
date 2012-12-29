package org.Epixcrafted.EpixServer.mc.entity;

import java.util.LinkedList;
import java.util.List;

public class EntityPlayer implements Entity {
	
	private int entityId;
	private String name;
	
	private double x;
	private double y;
	private double stance;
	private double z;
	private float yaw = 0F;
	private float pitch = 0F;
	private boolean onGround = true;
	
	private short health;
	private short food;
	private float saturation;
	
   public final List loadedChunks = new LinkedList();
        
	public EntityPlayer(int entityId, String name) {
		this(entityId, name, 0D, 0D, 0D, 0D, 0F, 0F, true, (short)20, (short)20, 5F);
	}
	
	public EntityPlayer(int entityId, String name, double x, double y, double z) {
		this(entityId, name, x, y, z, y + 1.65D, 0F, 0F, true, (short)20, (short)20, 5F);
	}
	
	public EntityPlayer(int entityId, String name, double x, double y, double z, double stance, float yaw, float pitch, boolean onGround) {
		this(entityId, name, x, y, z, stance, yaw, pitch, onGround, (short)20, (short)20, 5F);
	}
	
	public EntityPlayer(int entityId, String name, double x, double y, double z, double stance, float yaw, float pitch, boolean onGround, short health, short food, float saturation) {
		this.entityId = entityId;
		this.name = name;
		this.x = x;
		this.y = y;
		this.z = z;
		this.stance = stance;
		this.yaw = yaw;
		this.pitch = pitch;
		this.onGround = onGround;
		this.health = health;
		this.food = food;
		this.saturation = saturation;
	}
	
	@Override
	public boolean equals(Object player) {
		return player != null ? ((Entity)player).getEntityId() == this.getEntityId() && ((EntityPlayer)player).getName() == this.getName() : false;
	}

	@Override
	public int getEntityId() {
		return entityId;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

	@Override
	public double getZ() {
		return z;
	}
	
	public double getStance() {
		return stance;
	}
	
	public float getYaw() {
		return yaw;
	}
	
	public float getPitch() {
		return pitch;
	}
	
	@Override
	public short getHealth() {
		return health;
	}
	
	public short getFood() {
		return food;
	}
	
	public float getSaturation() {
		return saturation;
	}
	
	public boolean isOnGround() {
		return onGround;
	}

	@Override
	public void setX(double x) {
		this.x = x;
	}

	@Override
	public void setY(double y) {
		this.y = y;
	}

	@Override
	public void setZ(double z) {
		this.z = z;
	}
	
	public void setStance(double stance) {
		this.stance = stance;
	}
	
	public void setYaw(float yaw) {
		this.yaw = yaw;
	}
	
	public void setPitch(float pitch) {
		this.pitch = pitch;
	}
	
	public void setFood(short food) {
		this.food = food;
	}
	
	@Override
	public void setHealth(short health) {
		this.health = health;
	}
	
	public void setSaturation(short saturation) {
		this.saturation = saturation;
	}
	
	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}
	
	public void onUpdate() {
		
	}
}
