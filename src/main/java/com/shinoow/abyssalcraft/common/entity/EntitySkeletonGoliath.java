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
import com.shinoow.abyssalcraft.api.entity.ICoraliumEntity;

import net.minecraft.block.Block;
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
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntitySkeletonGoliath extends ACMob implements ICoraliumEntity {

	public EntitySkeletonGoliath(World world) {
		super(world);
		setSize(1.0F, 4.5F);
		tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, true));
		tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 1.0D));
		tasks.addTask(4, new EntityAIWander(this, 1.0D));
		tasks.addTask(5, new EntityAIFleeSun(this, 1.0D));
		tasks.addTask(7, new EntityAILookIdle(this));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityAbyssalZombie.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityZombie.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityDepthsGhoul.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntitySkeleton.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntitySkeletonGoliath.class, 8.0F));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		this.setCurrentItemOrArmor(0, new ItemStack(AbyssalCraft.cudgel));
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.3D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);

		if(AbyssalCraft.hardcoreMode){
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(200.0D);
			getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(40.0D);
		} else {
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0D);
			getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(20.0D);
		}
	}

	@Override
	protected boolean isAIEnabled()
	{
		return true;
	}

	@Override
	protected float getSoundPitch()
	{
		return rand.nextFloat() - rand.nextFloat() * 0.2F + 0.6F;
	}

	@Override
	protected String getLivingSound()
	{
		return "mob.skeleton.say";
	}

	@Override
	protected String getHurtSound()
	{
		return "mob.skeleton.hurt";
	}

	@Override
	protected String getDeathSound()
	{
		return "mob.skeleton.death";
	}

	@Override
	protected void func_145780_a(int par1, int par2, int par3, Block par4)
	{
		playSound("mob.skeleton.step", 0.15F, 1.0F);
	}

	@Override
	protected Item getDropItem()
	{
		return Items.bone;
	}

	@Override
	protected void dropRareDrop(int par1)
	{
		dropItem(AbyssalCraft.cudgel, 1);
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.UNDEAD;
	}

	@Override
	public void onLivingUpdate()
	{
		if (worldObj.isDaytime() && !worldObj.isRemote && worldObj.provider.dimensionId != AbyssalCraft.configDimId1)
		{
			float f = getBrightness(1.0F);

			if (f > 0.5F && rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && worldObj.canBlockSeeTheSky(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ)))
			{
				boolean flag = true;

				if (flag)
					setFire(8);
			}
		}
		super.onLivingUpdate();
	}
}
