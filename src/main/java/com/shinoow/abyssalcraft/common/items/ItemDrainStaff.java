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
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.entity.ICoraliumEntity;
import com.shinoow.abyssalcraft.api.entity.IDreadEntity;
import com.shinoow.abyssalcraft.api.entity.IOmotholEntity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
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
	public boolean isFull3D() {
		return true;
	}

	public void increaseEnergy(ItemStack stack, String type, int amount) {
		if(!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		stack.stackTagCompound.setInteger("energy"+type, getEnergy(stack, type) + 1);
	}

	public void setEnergy(int amount, ItemStack stack, String type) {
		stack.stackTagCompound.setInteger("energy"+type, amount);
	}

	public int getEnergy(ItemStack stack, String type) {
		return stack.hasTagCompound() && stack.stackTagCompound.hasKey("energy"+type) ? (int)stack.stackTagCompound.getInteger("energy"+type) : 0;
	}
	
	@SuppressWarnings("unchecked")
	protected void drain(ItemStack stack, World world, EntityPlayer player, int drainPower) {
		// Give essence
		if(getEnergy(stack, "Abyssal") >= 100) {
			world.playSoundAtEntity(player, "random.pop", 1.0F, 0.6F);
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.essence, 1, 0));
			setEnergy(0, stack, "Abyssal");
		}
		if(getEnergy(stack, "Dread") >= 100) {
			world.playSoundAtEntity(player, "random.pop", 1.0F, 0.6F);
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.essence, 1, 1));
			setEnergy(0, stack, "Dread");
		}
		if(getEnergy(stack, "Omothol") >= 100) {
			world.playSoundAtEntity(player, "random.pop", 1.0F, 0.6F);
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.essence, 1, 2));
			setEnergy(0, stack, "Omothol");
		}
		
		// Drain life
		Vec3 vec = player.getLookVec().normalize();
		for(int i = 1; i < 32; i++) {
			// Make the cone thicker at close range
			double cone = 1.4 - (i*0.04);
			double pXa = player.posX - cone;
			double pYa = player.posY - cone + player.getEyeHeight();
			double pZa = player.posZ - cone;
			double pXb = player.posX + cone;
			double pYb = player.posY + cone + player.getEyeHeight();
			double pZb = player.posZ + cone;
			AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(pXa + vec.xCoord * i, pYa + vec.yCoord * i, pZa + vec.zCoord * i, pXb + vec.xCoord * i, pYb + vec.yCoord * i, pZb + vec.zCoord * i);
			List<EntityLiving> list = world.getEntitiesWithinAABB(EntityLiving.class, aabb);
			if(list.iterator().hasNext()) {
				EntityLiving target = list.get(0);
				
				if (!target.isDead && !(target instanceof IBossDisplayData)) {
					int trueDmg = (int)Math.min(Math.ceil(target.getHealth()), drainPower);
					
					if(world.provider.dimensionId == AbyssalCraft.configDimId1 && target instanceof ICoraliumEntity) {
						if(target.attackEntityFrom(AbyssalCraftAPI.vajra, trueDmg)) {
							increaseEnergy(stack, "Abyssal", trueDmg);
						}
					} else if(world.provider.dimensionId == AbyssalCraft.configDimId2 && target instanceof IDreadEntity) {
						if(target.attackEntityFrom(AbyssalCraftAPI.vajra, trueDmg)) {
							increaseEnergy(stack, "Dread", trueDmg);
						}
					} else if(world.provider.dimensionId == AbyssalCraft.configDimId3 && target instanceof IOmotholEntity) {
						if(target.attackEntityFrom(AbyssalCraftAPI.vajra, trueDmg)) {
							increaseEnergy(stack, "Omothol", trueDmg);
						}
					} else if (world.provider.dimensionId == AbyssalCraft.configDimId3 || target.getMaxHealth() == 33) {
						trueDmg -= 2;
						if (trueDmg <= 0) {
							return;
						}
						if(target.attackEntityFrom(AbyssalCraftAPI.vajra, trueDmg)) {
							increaseEnergy(stack, "Omothol", trueDmg);
						}
					}
				}
			}
		}
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
	public void addInformation(ItemStack is, EntityPlayer player, List l, boolean B) {
		int abyssal = getEnergy(is, "Abyssal");
		int dread = getEnergy(is, "Dread");
		int omothol = getEnergy(is, "Omothol");
		l.add(StatCollector.translateToLocal("tooltip.drainstaff.energy.1")+": " + abyssal + "/100");
		l.add(StatCollector.translateToLocal("tooltip.drainstaff.energy.2")+": " + dread + "/100");
		l.add(StatCollector.translateToLocal("tooltip.drainstaff.energy.3")+": " + omothol + "/100");
	}
}