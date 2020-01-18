/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2016 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.entity;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.entity.IOmotholEntity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

enum BarfStatus {COOLDOWN, GURGLING, BARFING};

public class EntityShadowBeast extends ACMob implements IOmotholEntity {
	
	public EntityShadowBeast(World world) {
		super(world);
		setSize(1.0F, 2.8F);
		setDrop(AbyssalCraft.shadowgem, 1.0F);
		tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1D, true));
		tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 1D));
		tasks.addTask(4, new EntityAIWander(this, 1D));
		tasks.addTask(5, new EntityAIFleeSun(this, 1D));
		tasks.addTask(6, new EntityAILookIdle(this));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.3D);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0D);
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(10.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.35D);
	}

	@Override
	protected boolean isAIEnabled() {
		return true;
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity) {
		swingItem();
		boolean flag = super.attackEntityAsMob(par1Entity);

		return flag;
	}

	@Override
	protected float getSoundPitch() {
		return rand.nextFloat() - rand.nextFloat() * 0.2F + 0.5F;
	}

	@Override
	protected String getHurtSound() {
		return "abyssalcraft:shadow.hit";
	}

	@Override
	protected String getDeathSound() {
		return "abyssalcraft:shadow.death";
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return AbyssalCraftAPI.SHADOW;
	}

	/*
	@SideOnly(Side.CLIENT)
	public void handleStatusUpdate(byte id) {
		if (id == 23) {
			addMouthParticles();
		}
	}
	*/
	
	protected void addScalingDebuff(EntityLivingBase target, Potion potion, int amplifier, int increment, int maxDuration) {
		PotionEffect effect = target.getActivePotionEffect(potion);
		int duration = 0;
		if (effect != null) {
			duration += effect.getDuration();
			target.removePotionEffect(effect.getPotionID());
		}
		duration = Math.min(duration + increment, maxDuration);
		target.addPotionEffect(new PotionEffect(potion.getId(), duration, amplifier));
	}
	
	protected void addMouthParticles() {
		if (worldObj.isRemote) {
			Vec3 vector = getLookVec();

			double px = posX;
			double py = posY + getEyeHeight();
			double pz = posZ;


			for (int i = 0; i < 15; i++) {
				double dx = vector.xCoord;
				double dy = vector.yCoord;
				double dz = vector.zCoord;

				double spread = 5.0D + getRNG().nextDouble() * 2.5D;
				double velocity = 0.5D + getRNG().nextDouble() * 0.5D;

				dx += getRNG().nextGaussian() * 0.007499999832361937D * spread;
				dy += getRNG().nextGaussian() * 0.007499999832361937D * spread;
				dz += getRNG().nextGaussian() * 0.007499999832361937D * spread;
				dx *= velocity;
				dy *= velocity;
				dz *= velocity;

				worldObj.spawnParticle("largesmoke", px + getRNG().nextDouble() - 0.5D, py + getRNG().nextDouble() - 0.5D, pz + getRNG().nextDouble() - 0.5D, dx, dy, dz);
			}
		} else {
			worldObj.setEntityState(this, (byte)23);
		}
	}

	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}
	
	/*@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("BreathTimer", breathTimer);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		breathTimer = nbt.getInteger("BreathTimer");
	}*/
}
