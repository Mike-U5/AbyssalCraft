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
import net.minecraft.world.World;

public class EntityChagarothFist extends DreadEntity implements IDreadEntity {

	public EntityChagarothFist(World world) {
		super(world);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, true));
		tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 1.0D));
		tasks.addTask(4, new EntityAIWander(this, 1.0D));
		tasks.addTask(5, new EntityAILookIdle(this));
		tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		isImmuneToFire = true;
		experienceValue = 0;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);

		if (AbyssalCraft.hardcoreMode) {
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0D);
			getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(15D);
		} else {
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50.0D);
			getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(7.5D);
		}
	}

	@Override
	protected boolean isAIEnabled() {
		return true;
	}

	@Override
	protected void func_145780_a(int par1, int par2, int par3, Block par4) {
		playSound("mob.spider.step", 0.15F, 1.0F);
	}
}
