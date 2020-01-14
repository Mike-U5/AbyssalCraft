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

import java.util.Iterator;
import java.util.List;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.entity.IOmotholEntity;
import com.shinoow.abyssalcraft.api.item.ACItems;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
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
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityGatekeeperMinion extends ACMob implements IOmotholEntity {

	public EntityGatekeeperMinion(World world) {
		super(world);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityLivingBase.class, 0.35D, false));
		tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 0.35D));
		tasks.addTask(4, new EntityAIWander(this, 0.35D));
		tasks.addTask(7, new EntityAILookIdle(this));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityGatekeeperMinion.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityRemnant.class, 8.0F));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		setSize(1.4F, 2.8F);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(64.0D);
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.2D);

		if(AbyssalCraft.hardcoreMode){
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(500.0D);
			getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(36.0D);
		} else {
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(250.0D);
			getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(18.0D);
		}
	}

	@Override
	protected boolean isAIEnabled() {
		return true;
	}

	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}

	@Override
	public boolean attackEntityAsMob(Entity target) {
		swingItem();
		return super.attackEntityAsMob(target);
	}

	@Override
	protected String getDeathSound() {
		return "abyssalcraft:shadow.death";
	}

	@Override
	protected void func_145780_a(int par1, int par2, int par3, Block par4) {
		playSound("mob.spider.step", 0.15F, 1.0F);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean attackEntityFrom(DamageSource dmgSrc, float amount) {
		if (dmgSrc == DamageSource.inWall) {
			return false;
		}
		
		EntityLivingBase enemy = null;
		if(dmgSrc.getSourceOfDamage() != null && dmgSrc.getSourceOfDamage() instanceof EntityLivingBase) {
			enemy = (EntityLivingBase) dmgSrc.getSourceOfDamage();
		}
		if(rand.nextInt(10) == 0) {
			List<EntityRemnant> remnants = worldObj.getEntitiesWithinAABB(EntityRemnant.class, boundingBox.expand(16D, 16D, 16D));
			if(remnants != null) {
				if(enemy != null) {
					Iterator<EntityRemnant> iter = remnants.iterator();
					while(iter.hasNext()) {
						iter.next().enrage(false, enemy);
					}
				}
			}
			worldObj.playSoundAtEntity(this, "abyssalcraft:remnant.scream", 3F, 1F);
		}
		return super.attackEntityFrom(dmgSrc, amount);
	}

	@Override
	protected void dropFewItems(boolean par1, int lootLvl) {
		float drp = 1.0F + (lootLvl*0.5F);
		int finalAmt = (drp % 1 > Math.random()) ? (int)Math.ceil(drp) : (int)Math.floor(drp);
		
		ItemStack items = new ItemStack(AbyssalCraft.eldritchScale, finalAmt);
		if(rand.nextInt(10) == 0 && worldObj.provider.dimensionId == AbyssalCraft.configDimId3) {
			items = new ItemStack(AbyssalCraft.ethaxiumIngot, finalAmt);
		}

		if (items != null) {
			entityDropItem(items, 0);
		}
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return AbyssalCraftAPI.SHADOW;
	}
	
	@Override
	protected void dropRareDrop(int par1) {
		if (worldObj.provider.dimensionId == AbyssalCraft.configDimId3) {
			dropItem(ACItems.ethaxium_ingot, 1);
		}
	}
}
