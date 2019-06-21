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

import java.util.List;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.entity.IAntiEntity;
import com.shinoow.abyssalcraft.api.entity.ICoraliumEntity;
import com.shinoow.abyssalcraft.api.entity.IDreadEntity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

enum BarfStatus {COOLDOWN, GURGLING, BARFING};

public class EntityShadowBeast extends ACMob implements IAntiEntity, ICoraliumEntity, IDreadEntity {
	
	private BarfStatus barfStatus = BarfStatus.COOLDOWN;
	private int breathTimer = 100;
	
	public EntityShadowBeast(World world) {
		super(world);
		setSize(1.0F, 2.8F);
		setDrop(AbyssalCraft.shadowgem, 1.0F);
		setPushResist(0.2);
		tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 0.35D, true));
		tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 0.35D));
		tasks.addTask(4, new EntityAIWander(this, 0.35D));
		tasks.addTask(5, new EntityAIFleeSun(this, 0.35D));
		tasks.addTask(6, new EntityAILookIdle(this));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		
		if(AbyssalCraft.hardcoreMode){
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(200.0D);
			getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(20.0D);
		} else {
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0D);
			getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(10.0D);
		}
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
	
	private int breathTimer() {
		return (ticksExisted % 400) - 300;
	}

	@Override
	public void onLivingUpdate() {
		// Run twice per second
		if (this.ticksExisted % 10 == 0) {
			// Apply blindness
			List<EntityPlayer> list = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.boundingBox.expand(4D, 4D, 4D));
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					Entity entity = (Entity)list.get(i);
					if ((Entity)list.get(i) instanceof EntityPlayer && !entity.isDead && this.canEntityBeSeen(entity)) {
						((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.blindness.getId(), 30));
					}
				}
			}
		}
		/*if (getAttackTarget() != null && getDistanceSqToEntity(getAttackTarget()) <= 64D && breathTimer <= -300) {
			breathTimer = 100;
		}

		if (breathTimer > 0) {
			motionX *= 0.05D;
			motionZ *= 0.05D;
			worldObj.setEntityState(this, (byte)23);
			if (ticksExisted % 5 == 0) {
				worldObj.playSound(posX + 0.5D, posY + getEyeHeight(), posZ + 0.5D, "mob.ghast.fireball", 0.9F, getRNG().nextFloat() * 0.7F + 0.3F, true);
			}
			Entity target = this.getAttackTarget();
			if (target != null) {
				barfStatus = BarfStatus.BARFING;
				if(target instanceof EntityLivingBase) {
					addScalingDebuff((EntityLivingBase)target, Potion.moveSlowdown, 1, 4, 300);
				}
			}
		} else {
			barfStatus = BarfStatus.COOLDOWN;
		}

		--breathTimer;*/
		super.onLivingUpdate();
	}
	
	@Override
	public void onUpdate() {
        if (barfStatus == BarfStatus.BARFING) {
        	addMouthParticles();
        }
        super.onUpdate();
    }
	
	@SideOnly(Side.CLIENT)
	public void handleStatusUpdate(byte id) {
		if (id == 23) {
			addMouthParticles();
		}
	}
	
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
	
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("BreathTimer", breathTimer);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		breathTimer = nbt.getInteger("BreathTimer");
	}
}
