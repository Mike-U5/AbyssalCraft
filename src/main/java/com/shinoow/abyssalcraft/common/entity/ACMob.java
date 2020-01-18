package com.shinoow.abyssalcraft.common.entity;

import com.shinoow.abyssalcraft.api.entity.ICoraliumEntity;
import com.shinoow.abyssalcraft.api.entity.IDreadEntity;
import com.shinoow.abyssalcraft.api.entity.IOmotholEntity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class ACMob extends EntityMob {
	protected Item dropItem;
	protected float dropRate = 0;
	
	public ACMob(World world) {
		super(world);
	}
	
	public void setDrop(Item item, float rate) {
		this.dropItem = item;
		this.dropRate = rate;
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
	
	public double getKnockbackMult() {
		double modifier = (float)(1 - getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getAttributeValue());
		return Math.min(1, Math.max(0, modifier));
	}
	
	/**
     * knocks back this entity
     */
	@Override
    public void knockBack(Entity entity, float unused, double xRatio, double zRatio) {
		this.isAirBorne = true;
        final double f = MathHelper.sqrt_double(xRatio * xRatio + zRatio * zRatio);
        final double strength = 0.4F * getKnockbackMult();
        this.motionX /= 2.0D;
        this.motionY /= 2.0D;
        this.motionZ /= 2.0D;
        this.motionX -= xRatio / f * strength;
        this.motionY += strength;
        this.motionZ -= zRatio / f * strength;

        if (this.motionY > 0.4000000059604645D) {
            this.motionY = 0.4000000059604645D;
        }
    }
	
	@Override
	public void addVelocity(double plusX, double plusY, double plusZ) {
		final double mult = getKnockbackMult();
		this.motionX += plusX * mult;
        this.motionY += plusY * mult;
        this.motionZ += plusZ * mult;
        this.isAirBorne = true;
	}
	
	@Override
	public void heal(float amount) {
		if (!(this instanceof IBossDisplayData)) {
			if (this instanceof ICoraliumEntity) {
				return;
			}
			if (this instanceof IDreadEntity) {
				return;
			}
			if (this instanceof IOmotholEntity) {
				return;
			}
		}
		super.heal(amount);
	}
}
