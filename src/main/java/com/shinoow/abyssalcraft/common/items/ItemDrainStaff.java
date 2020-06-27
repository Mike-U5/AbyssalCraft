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
package com.shinoow.abyssalcraft.common.items;

import java.util.List;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.entity.ICoraliumEntity;
import com.shinoow.abyssalcraft.api.entity.IDreadEntity;
import com.shinoow.abyssalcraft.api.entity.IOmotholEntity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemDrainStaff extends Item {

	public ItemDrainStaff() {
		super();
		setUnlocalizedName("drainStaff");
		setCreativeTab(AbyssalCraft.tabTools);
		setTextureName("abyssalcraft:DrainStaff");
		setMaxStackSize(1);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int num, boolean flag) {
		if (stack.hasTagCompound() && stack.stackTagCompound.hasKey("cooldown")) {
			int cooldown = stack.stackTagCompound.getInteger("cooldown");
			if (cooldown <= 1) {
				stack.stackTagCompound.removeTag("cooldown");
			} else {
				stack.stackTagCompound.setInteger("cooldown", cooldown - 1);
			}
		}
	}

	@Override
	public boolean isFull3D() {
		return true;
	}

	public void increaseEnergy(ItemStack stack, String type, int amount) {
		if(!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		stack.stackTagCompound.setInteger("energy"+type, getEnergy(stack, type) + amount);
	}

	public void setEnergy(int amount, ItemStack stack, String type) {
		stack.stackTagCompound.setInteger("energy"+type, amount);
	}

	public int getEnergy(ItemStack stack, String type) {
		return stack.hasTagCompound() && stack.stackTagCompound.hasKey("energy"+type) ? (int)stack.stackTagCompound.getInteger("energy"+type) : 0;
	}
	
	@SuppressWarnings("unchecked")
	protected void drain(ItemStack stack, World world, EntityPlayer player, int drainPower) {
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		
		// Check if on cooldown
		if (stack.stackTagCompound.hasKey("cooldown")) {
			return;
		} else {
			stack.stackTagCompound.setInteger("cooldown", 10);
		}
		
		// Activate Drain
		world.playSoundAtEntity(player, "mob.silverfish.say", 0.2F, 2.5F);
		
		// Give essence
		if(getEnergy(stack, "Abyssal") >= 100) {
			world.playSoundAtEntity(player, "random.pop", 2.0F, 0.5F);
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.essence, 1, 0));
			setEnergy(0, stack, "Abyssal");
		}
		if(getEnergy(stack, "Dread") >= 100) {
			world.playSoundAtEntity(player, "random.pop", 2.0F, 0.5F);
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.essence, 1, 1));
			setEnergy(0, stack, "Dread");
		}
		if(getEnergy(stack, "Omothol") >= 100) {
			world.playSoundAtEntity(player, "random.pop", 2.0F, 0.5F);
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.essence, 1, 2));
			setEnergy(0, stack, "Omothol");
		}
		
		// Get Look Vector
		final Vec3 vec = player.getLookVec().normalize();
		final double cone = 1.1;
		final double pXa = player.posX - cone;
		final double pYa = player.posY - cone + player.getEyeHeight();
		final double pZa = player.posZ - cone;
		final double pXb = player.posX + cone;
		final double pYb = player.posY + cone + player.getEyeHeight();
		final double pZb = player.posZ + cone;

		// Drain Life
		for(int i = 1; i < 32; i++) {
			final AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(pXa + vec.xCoord * i, pYa + vec.yCoord * i, pZa + vec.zCoord * i, pXb + vec.xCoord * i, pYb + vec.yCoord * i, pZb + vec.zCoord * i);
			final List<EntityLiving> list = world.getEntitiesWithinAABB(EntityLiving.class, aabb);
			if(list.iterator().hasNext()) {
				for (int j = 0; j < list.size(); j++) {
					// Drain Energy
					final EntityLiving target = list.get(j);
					if (isDrainable(target) && player.canEntityBeSeen(target) && target.hurtResistantTime <= 0) {
						final String type = this.getEnemyType(world, target);
						if (type == null) {
							return;
						}
						float dealableDamage = Math.min(target.getHealth(), drainPower);
						final int trueDmg = (int)Math.floor(dealableDamage);
						
						// If target can be harmed, increase energy
						target.setHealth(target.getHealth() - trueDmg);
						target.performHurtAnimation();
						target.hurtResistantTime = target.maxHurtResistantTime;
						increaseEnergy(stack, type, trueDmg);
						player.addExhaustion(0.02F);
					}
				}
			}
		}
	}
	
	private boolean isDrainable(EntityLiving e) {
		if (!e.isEntityAlive() || e instanceof IBossDisplayData) {
			return false;
		}
		return (e instanceof ICoraliumEntity || e instanceof IDreadEntity || e instanceof IOmotholEntity || EntityList.getEntityString(e).equals("w_angels.EntityWeepingAngel"));
	}
	
	private String getEnemyType(World world, EntityLiving target) {
		if (target != null && !target.isDead && !(target instanceof IBossDisplayData)) {
			if(world.provider.dimensionId == AbyssalCraft.configDimId1 && target instanceof ICoraliumEntity) {
				return "Abyssal";
			} else if(world.provider.dimensionId == AbyssalCraft.configDimId2 && target instanceof IDreadEntity) {
				return "Dread";
			} else if((world.provider.dimensionId == AbyssalCraft.configDimId3 && target instanceof IOmotholEntity) || EntityList.getEntityString(target).equals("w_angels.EntityWeepingAngel")) {
				return "Omothol";
			}
		}
		return null;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {		
		if (!player.isSneaking()) {
			drain(stack, world, player, 1);
		}
		return stack;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addInformation(ItemStack is, EntityPlayer player, List l, boolean bool) {
		final int aE = getEnergy(is, "Abyssal");
		final int dE = getEnergy(is, "Dread");
		final int oE = getEnergy(is, "Omothol");
		l.add(EnumChatFormatting.DARK_GREEN + StatCollector.translateToLocal("tooltip.drainstaff.energy.1")+": " + EnumChatFormatting.WHITE + aE + "%");
		l.add(EnumChatFormatting.DARK_RED + StatCollector.translateToLocal("tooltip.drainstaff.energy.2")+": " + EnumChatFormatting.WHITE + dE + "%");
		l.add(EnumChatFormatting.DARK_AQUA + StatCollector.translateToLocal("tooltip.drainstaff.energy.3")+": " + EnumChatFormatting.WHITE + oE + "%");
	}
}