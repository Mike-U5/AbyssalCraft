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

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.entity.IAntiEntity;

public class EntityAntiCow extends EntityAnimal implements IAntiEntity {

	public EntityAntiCow(World par1World) {
		super(par1World);
		setSize(0.9F, 1.3F);
		getNavigator().setAvoidsWater(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIPanic(this, 2.0D));
		tasks.addTask(2, new EntityAIMate(this, 1.0D));
		tasks.addTask(3, new EntityAITempt(this, 1.25D, Items.wheat, false));
		tasks.addTask(4, new EntityAIFollowParent(this, 1.25D));
		tasks.addTask(5, new EntityAIWander(this, 1.0D));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		tasks.addTask(7, new EntityAILookIdle(this));
	}

	@Override
	public boolean isAIEnabled() {
		return true;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.20000000298023224D);
	}

	@Override
	protected String getLivingSound() {
		return "mob.cow.say";
	}

	@Override
	protected String getHurtSound() {
		return "mob.cow.hurt";
	}

	@Override
	protected String getDeathSound() {
		return "mob.cow.hurt";
	}

	@Override
	protected void func_145780_a(int par1, int par2, int par3, Block par4Block) {
		playSound("mob.cow.step", 0.15F, 1.0F);
	}

	@Override
	protected float getSoundVolume() {
		return 0.4F;
	}

	@Override
	protected Item getDropItem() {
		return Items.leather;
	}

	@Override
	protected void dropFewItems(boolean par1, int par2) {
		int j = rand.nextInt(3) + rand.nextInt(1 + par2);
		int k;

		for (k = 0; k < j; ++k)
			dropItem(Items.leather, 1);
	}

	@Override
	protected void collideWithEntity(Entity par1Entity) {
		if (!worldObj.isRemote && par1Entity instanceof EntityCow) {
			boolean flag = worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
			worldObj.createExplosion(this, posX, posY, posZ, 5, flag);
			setDead();
		} else
			par1Entity.applyEntityCollision(this);
	}

	@Override
	public boolean interact(EntityPlayer par1EntityPlayer) {
		ItemStack itemstack = par1EntityPlayer.inventory.getCurrentItem();

		if (itemstack != null && itemstack.getItem() == Items.bucket && !par1EntityPlayer.capabilities.isCreativeMode) {
			if (itemstack.stackSize-- == 1)
				par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, new ItemStack(Items.milk_bucket));
			else if (!par1EntityPlayer.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.antibucket)))
				par1EntityPlayer.dropPlayerItemWithRandomChoice(new ItemStack(AbyssalCraft.antibucket, 1, 0), false);

			return true;
		} else
			return super.interact(par1EntityPlayer);
	}

	@Override
	public EntityAntiCow createChild(EntityAgeable par1EntityAgeable) {
		return new EntityAntiCow(worldObj);
	}
}
