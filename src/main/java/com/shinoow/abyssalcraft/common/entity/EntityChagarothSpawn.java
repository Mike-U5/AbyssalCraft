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
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityChagarothSpawn extends DreadEntity implements IDreadEntity {

	public EntityChagarothSpawn(World par1World) {
		super(par1World);
		setSize(0.6F, 0.6F);
		setDrop(AbyssalCraft.dreadfragment, 1.0F);
		tasks.addTask(1, new EntityAIAttackOnCollide(this, EntityPlayer.class, 0.35D, true));
		tasks.addTask(2, new EntityAIMoveTowardsRestriction(this, 0.35D));
		tasks.addTask(3, new EntityAIWander(this, 0.35D));
		tasks.addTask(4, new EntityAILookIdle(this));
		tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		isImmuneToFire = true;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.45D);

		if(AbyssalCraft.hardcoreMode){
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(80.0D);
			getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(16.0D);
		} else {
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0D);
			getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(8.0D);
		}
	}

	@Override
	protected boolean isAIEnabled() {
		return true;
	}

	@Override
	protected String getLivingSound() {
		return "abyssalcraft:dreadslug.idle";
	}

	@Override
	protected String getHurtSound() {
		return "abyssalcraft:dreadslug.hurt";
	}

	@Override
	protected String getDeathSound() {
		return "abyssalcraft:dreadslug.death";
	}

	@Override
	protected void func_145780_a(int par1, int par2, int par3, Block par4) {
		worldObj.playSoundAtEntity(this, "mob.zombie.step", 0.15F, 1.0F);
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.ARTHROPOD;
	}
}
