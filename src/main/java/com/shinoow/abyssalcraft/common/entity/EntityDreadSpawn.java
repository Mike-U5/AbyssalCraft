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
import java.lang.Math;

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
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.entity.IDreadEntity;

public class EntityDreadSpawn extends EntityMob implements IDreadEntity {
	private static boolean hasMerged;

	public EntityDreadSpawn(World par1World) {
		super(par1World);
		setSize(0.6F, 0.6F);
		tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 0.35D, true));
		tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 0.35D));
		tasks.addTask(4, new EntityAIWander(this, 0.35D));
		tasks.addTask(5, new EntityAILookIdle(this));
		tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		isImmuneToFire = true;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);

		if(AbyssalCraft.hardcoreMode){
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0D);
			getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(12.0D);
		} else getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0D);
	}

	@Override
	protected boolean isAIEnabled() {
		return true;
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity) {
		if (super.attackEntityAsMob(par1Entity))
			if (par1Entity instanceof EntityLivingBase)
				((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(AbyssalCraft.Dplague.id, 100));
		return super.attackEntityAsMob(par1Entity);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObject(18, Byte.valueOf((byte)0));
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if (!worldObj.isRemote)
			setBesideClimbableBlock(isCollidedHorizontally);
	}

	@Override
	protected String getLivingSound() {
		return "abyssalcraft:dreadspawn.idle";
	}

	@Override
	protected String getHurtSound() {
		return "abyssalcraft:dreadspawn.hurt";
	}

	@Override
	protected String getDeathSound() {
		return "abyssalcraft:dreadspawn.death";
	}

	@Override
	protected void func_145780_a(int par1, int par2, int par3, Block par4) {
		worldObj.playSoundAtEntity(this, "mob.zombie.step", 0.15F, 1.0F);
	}

	@Override
	public boolean isOnLadder() {
		return isBesideClimbableBlock();
	}

	/**
	 * Returns true if the WatchableObject (Byte) is 0x01 otherwise returns false. The WatchableObject is updated using
	 * setBesideClimableBlock.
	 */
	public boolean isBesideClimbableBlock() {
		return (dataWatcher.getWatchableObjectByte(18) & 1) != 0;
	}

	/**
	 * Updates the WatchableObject (Byte) created in entityInit(), setting it to 0x01 if par1 is true or 0x00 if it is
	 * false.
	 */
	public void setBesideClimbableBlock(boolean par1) {
		byte b0 = dataWatcher.getWatchableObjectByte(18);

		if (par1)
			b0 = (byte)(b0 | 1);
		else
			b0 &= -2;

		dataWatcher.updateObject(18, Byte.valueOf(b0));
	}

	@Override
	protected void fall(float par1) {}

	/*@Override
	protected Item getDropItem() {
		return AbyssalCraft.dreadfragment;
	}*/
	
	@Override
	protected void dropFewItems(boolean para, int lootLvl) {
		float drp = 0.5F + (lootLvl*0.25F);
		int finalAmt = (drp % 1 > Math.random()) ? (int)Math.ceil(drp) : (int)Math.floor(drp);
		
		entityDropItem(new ItemStack(AbyssalCraft.dreadfragment, finalAmt), 0);
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.ARTHROPOD;
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();

		List<EntityDreadSpawn> dreadspawns = worldObj.getEntitiesWithinAABB(getClass(), boundingBox.expand(2D, 2D, 2D));

		if(!worldObj.isRemote)
			if(!dreadspawns.isEmpty())
				if(dreadspawns.size() >= 5 && !hasMerged){
					hasMerged = true;
					for(int i = 0; i < 5; i++)
						worldObj.removeEntity(dreadspawns.get(i));
					EntityGreaterDreadSpawn greaterspawn = new EntityGreaterDreadSpawn(worldObj);
					greaterspawn.copyLocationAndAnglesFrom(this);
					worldObj.removeEntity(this);
					worldObj.spawnEntityInWorld(greaterspawn);
					hasMerged = false;
				}
	}
}
