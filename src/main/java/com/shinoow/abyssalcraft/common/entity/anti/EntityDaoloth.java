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
package com.shinoow.abyssalcraft.common.entity.anti;

import com.shinoow.abyssalcraft.api.biome.IDarklandsBiome;
import com.shinoow.abyssalcraft.api.entity.IAntiEntity;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class EntityDaoloth extends EntityMob implements IAntiEntity {
	
	public EntityDaoloth(World world) {
		super(world);
		// Custom Selector to not target those who hold the Gatekeeper's Crozier
		final IEntitySelector selector = new IEntitySelector() {
			@Override
			public boolean isEntityApplicable(Entity e) {
				return getEntityBiome(e) instanceof IDarklandsBiome;
			}
		};
		// AI
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1D, true));
		tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 1D));
		tasks.addTask(5, new EntityAILookIdle(this));
		tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true, false, selector));
	}
	
	private BiomeGenBase getEntityBiome(Entity e) {
		final int intX = MathHelper.floor_double(e.posX);
		final int intZ = MathHelper.floor_double(e.posZ);
		return worldObj.getBiomeGenForCoords(intX, intZ);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1.0D);
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(8.5D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.14D);
	}

	@Override
	protected boolean isAIEnabled() {
		return true;
	}

	@Override
	public boolean canDespawn() {
		return false;
	}
	
	@Override
	protected String getLivingSound() {
		return "abyssalcraft:antiplayer.idle";
	}
	
	@Override
	protected String getDeathSound() {
		return "abyssalcraft:antiplayer.death";
	}

	@Override
	public void onDeath(DamageSource src) {
		boolean flag = worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
		if(!worldObj.isRemote) {
			worldObj.createExplosion(this, posX, posY, posZ, 1, flag);
		}
		super.onDeath(src);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData entity) {
		entity = super.onSpawnWithEgg(entity);
		setCanPickUpLoot(false);
		return entity;
	}
	
	@Override
	public void onLivingUpdate() {
    	if(!isInDarklands(this)) {
    		attackEntityFrom(DamageSource.outOfWorld, 99999);
    	}
        super.onLivingUpdate();
	}
	
	public boolean isInDarklands(Entity e) {
		return getEntityBiome(e) instanceof IDarklandsBiome;
	}
	
	@Override
	public int getTotalArmorValue() {
		return 30;
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource src, float amount) {
		if (src == DamageSource.outOfWorld) {
			return super.attackEntityFrom(src, amount);
		} else if (src.getEntity() instanceof Entity || src.isExplosion()) {
			return super.attackEntityFrom(src, 0);
		}
		
		return false;
	}
}
