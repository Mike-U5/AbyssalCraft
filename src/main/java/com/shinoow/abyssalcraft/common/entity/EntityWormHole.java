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

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.entity.IOmotholEntity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityWormHole extends EntityMob {

	public int deathTicks;
	
	public EntityWormHole(World world) {
		super(world);
		setSize(1.5F, 5.7F);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 0.35D, true));
		tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 0.35D));
		tasks.addTask(5, new EntityAIWander(this, 0.35D));
		tasks.addTask(6, new EntityAILookIdle(this));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		isImmuneToFire = true;
	}

	@Override
	public String getCommandSenderName() {
		return EnumChatFormatting.BLUE + super.getCommandSenderName();
	}
	
	@Override
	public void onLivingUpdate() {
		motionX = motionY = motionZ = 0;
		if (isEntityAlive() && getHealth() > 0) {
			setHealth(0);
		}
		super.onLivingUpdate();
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1.0D);
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(0.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0D);
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
	}

	@Override
	protected boolean isAIEnabled() {
		return true;
	}

	@Override
	protected float getSoundVolume() {
		return 3.0F;
	}

	@Override
	protected boolean canDespawn() {
		return false;
	}

	@Override
	public boolean attackEntityAsMob(Entity target) {
		return false;
	}

	@Override
	public boolean attackEntityFrom(DamageSource dmgSrc, float amount) {
		return false;
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("DeathTicks", deathTicks);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		deathTicks = nbt.getInteger("DeathTicks");
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onDeathUpdate() {
		motionX = motionY = motionZ = 0;
		deathTicks += 1;

		if(deathTicks <= 500) {
			if(deathTicks == 110) {
				worldObj.playSoundAtEntity(this, "abyssalcraft:jzahar.charge", 1, 1);
			}
			/*if(deathTicks < 100) {
				worldObj.spawnParticle("largesmoke", posX, posY + 2.5D, posZ, 0, 0, 0);
			}*/
			float f = (rand.nextFloat() - 0.5F) * 3.0F;
			float f1 = (rand.nextFloat() - 0.5F) * 2.0F;
			float f2 = (rand.nextFloat() - 0.5F) * 3.0F;
			if(deathTicks >= 0 && deathTicks < 480) {
				worldObj.spawnParticle("depthSuspend ", posX + f, posY + f1, posZ + f2, 0, 0, 0);
			}
			if(deathTicks >= 0 && deathTicks < 480) {
				worldObj.spawnParticle("depthSuspend ", posX + f, posY + f1, posZ + f2, 0.0D, 0.0D, 0.0D);
			}
			worldObj.spawnParticle("enchantmenttable", posX, posY + 2.5D, posZ, 0, 0, 0);
			if (deathTicks >= 490 && deathTicks <= 500) {
				worldObj.spawnParticle("hugeexplosion", posX, posY + 1.5D, posZ, 0.0D, 0.0D, 0.0D);
				worldObj.playSoundAtEntity(this, "random.explode", 4, (1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.2F) * 0.7F);
			}
			
			if(deathTicks > 50 && deathTicks < 500) {
				pullEntities();
			}
		}

		if(deathTicks == 490 && !worldObj.isRemote) {
			if(!worldObj.getEntitiesWithinAABB(Entity.class, boundingBox.expand(3,3,3)).isEmpty()) {
				List<Entity> entities = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(3,3,3));
				for(Entity entity: entities) {
					if(entity instanceof EntityLivingBase) {
						entity.attackEntityFrom(AbyssalCraftAPI.antimatter, 50F);
					}
				}
			}

			final int pX = (int)posX;
			final int pY = (int)posY;
			final int pZ = (int)posZ;
			final int radius = 6;
			for(int x = -radius; x < radius; x++) {
				if (Math.abs(x) == radius && Math.random() > 0.25) {
					continue;
				}
				for(int y = -radius; y < radius; y++) {
					if (Math.abs(y) == radius && Math.random() > 0.25) {
						continue;
					}
					for(int z = -radius; z < radius; z++) {
						if (Math.abs(z) == radius && Math.random() > 0.25) {
							continue;
						}
						
						if(!worldObj.isAirBlock(pX + x, pY + y, pZ + z)) {
							final Block block = worldObj.getBlock((int)posX + x, (int)posY + y, (int)posZ + z);
							if(block != Blocks.bedrock) {
								if (block.getBlockHardness(worldObj, pX + x, pY + y, pZ + z) < 150F) {
									worldObj.setBlockToAir(pX + x, pY + y, pZ + z);
								}
							}
						}
					}
				}
			}	
		}
		
		// Remove Self
		if (deathTicks >= 491) {
			this.setDead();
		}
	}
	
	@Override
	public boolean isPushedByWater() {
		return false;
	}
	
	@Override
	public boolean canBePushed() {
		return false;
	}
	
	@SuppressWarnings("unchecked")
	private void pullEntities() {
		if (deathTicks > 500) {
			return;
		}
		final float dt = (float)deathTicks;
		final float power = dt / 5000;
		final float size = dt / 12;
		System.out.println("TICKS: " + dt + "| POWER:"  + power + "| SIZE: " + size);

		List<Entity> list = worldObj.getEntitiesWithinAABB(Entity.class, boundingBox.expand(size, size, size));

		for(Entity entity : list) {
			if (entity instanceof IOmotholEntity) {
				continue;
			}
			float velocity = -power;
			if (!entity.onGround) {
				velocity *= 2;
			}
			
			double scale = (size - entity.getDistance(posX, posY, posZ)) / size;

			Vec3 dir = Vec3.createVectorHelper(entity.posX - posX, entity.posY - posY, entity.posZ - posZ);
			dir = dir.normalize();
			entity.addVelocity(dir.xCoord * velocity * scale, dir.yCoord * velocity * scale, dir.zCoord * velocity * scale);
		}
	}
	
	@Override
	public void onStruckByLightning(EntityLightningBolt bolt) {
		return;
	}
}
