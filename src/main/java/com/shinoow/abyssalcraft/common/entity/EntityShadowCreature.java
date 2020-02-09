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
import net.minecraft.world.World;

public class EntityShadowCreature extends ACMob implements IOmotholEntity {

	public EntityShadowCreature(World world) {
		super(world);
		setSize(0.6F, 0.6F);
		setDrop(AbyssalCraft.shadowfragment, 2.0F);
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
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0D);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.15D);
	}

	@Override
	protected boolean isAIEnabled() {
		return true;
	}

	@Override
	protected float getSoundPitch() {
		return rand.nextFloat() - rand.nextFloat() * 0.2F + 1.5F;
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
	
	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}
}
