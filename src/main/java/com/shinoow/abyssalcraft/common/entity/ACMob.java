package com.shinoow.abyssalcraft.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class ACMob extends EntityMob {
	protected Item dropItem;
	protected float dropRate = 0;
	protected double pushForce = 0.4D;
	
	public ACMob(World world) {
		super(world);
	}
	
	public void setDrop(Item item, float rate) {
		this.dropItem = item;
		this.dropRate = rate;
	}
	
	public void setPushResist(double pushResist) {
		this.pushForce = 0.4D - (pushResist * 0.4);
	}
	
	@Override
	protected void dropFewItems(boolean par1, int lootLvl) {
		float rate = dropRate + ((dropRate/2) * lootLvl);
		int amount = (rate % 1 > rand.nextFloat()) ? (int)Math.ceil(rate) : (int)Math.floor(rate);
		
		ItemStack items = new ItemStack(dropItem, amount);

		if (items != null) {
			entityDropItem(items, 0);
		}
	}
	
	@Override
    public void knockBack(Entity p_70653_1_, float p_70653_2_, double pushX, double pushZ) {
    	this.isAirBorne = true;
        double base = MathHelper.sqrt_double(pushX * pushX + pushZ * pushZ);
        double motionDiv = 2.0D;
        this.motionX /= motionDiv;
        this.motionY /= motionDiv;
        this.motionZ /= motionDiv;
        this.motionX -= (pushX / base) * pushForce;
        this.motionY += pushForce;
        this.motionZ -= (pushZ / base) * pushForce;
        if (this.motionY > 0.4000000059604645D) {
            this.motionY = 0.4000000059604645D;
        }
    }
	
	@Override
	public void heal(float amount) {}
}
