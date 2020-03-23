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
import com.shinoow.abyssalcraft.api.entity.IDreadEntity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityDreadgolem extends ACMob implements IDreadEntity {

	public EntityDreadgolem(World world) {
		super(world);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityAbygolem.class, 0.35D, true));
		tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityPlayer.class, 0.35D, false));
		tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 0.35D));
		tasks.addTask(5, new EntityAIWander(this, 0.35D));
		tasks.addTask(6, new EntityAILookIdle(this));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityAbygolem.class, 0, true));
		targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		isImmuneToFire = true;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();

		if(AbyssalCraft.hardcoreMode){
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0D);
			getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(8.0D);
		} else getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.5D);
	}
	
	@Override
	public boolean attackEntityAsMob(Entity target) {
	   this.swingItem();
	   return super.attackEntityAsMob(target);
	}
	
	@Override
	protected float getSoundPitch() {
		return (rand.nextFloat() * 0.15F) + 0.8F;
	}

	@Override
	protected boolean isAIEnabled() {
		return true;
	}

	@Override
	protected String getLivingSound() {
		return "abyssalcraft:golem.idle";
	}

	@Override
	protected String getHurtSound() {
		return "abyssalcraft:golem.hit";
	}

	@Override
	protected String getDeathSound() {
		return "abyssalcraft:golem.death";
	}

	@Override
	protected void dropFewItems(boolean playerKill, int lootLvl) {
		if (worldObj.rand.nextInt(10) == 0) {
			entityDropItem(new ItemStack(AbyssalCraft.magicdreadstone, 1), 0);
			entityDropItem(new ItemStack(AbyssalCraft.dreadstone, 2), 0);
		} else {
			entityDropItem(new ItemStack(AbyssalCraft.dreadstone, 3), 0);
		}
	}

	@Override
	protected void func_145780_a(int par1, int par2, int par3, Block par4) {
		worldObj.playSoundAtEntity(this, "mob.zombie.step", 0.15F, 1.0F);
	}
}
