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
import com.shinoow.abyssalcraft.api.entity.IAntiEntity;
import com.shinoow.abyssalcraft.api.entity.ICoraliumEntity;
import com.shinoow.abyssalcraft.api.entity.IDreadEntity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.DimensionManager;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemDrainStaff extends Item {

	public ItemDrainStaff(){
		super();
		setUnlocalizedName("drainStaff");
		setCreativeTab(AbyssalCraft.tabTools);
		setTextureName("abyssalcraft:DrainStaff");
		setMaxStackSize(1);
	}

	@Override
	public boolean isFull3D(){
		return true;
	}

	public void increaseEnergy(int amount, ItemStack stack, String type) {
		if(!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		stack.stackTagCompound.setInteger("energy"+type, getEnergy(stack, type) + amount);
	}
	
	private boolean tryDrainEnergy(World world, EntityLivingBase target, ItemStack stack, String type) {
		float drainStr = 0.1f * (30 - target.getTotalArmorValue());
		if (target.getHealth() <= drainStr) {
			int lifeForce = (int)target.getMaxHealth();
			double tX = target.posX;
			double tY = target.posY;
			double tZ = target.posZ;
			double tWidth = target.width;
			double tHeight = target.height;
			target.attackEntityFrom(DamageSource.generic, 10000);
			increaseEnergy(lifeForce, stack, type);
			return true;
		}
		return false;
	}

	public void setEnergy(int amount, ItemStack stack, String type){
		stack.stackTagCompound.setInteger("energy"+type, amount);
	}

	public int getEnergy(ItemStack par1ItemStack, String type) {
		return par1ItemStack.hasTagCompound() && par1ItemStack.stackTagCompound.hasKey("energy"+type) ? (int)par1ItemStack.stackTagCompound.getInteger("energy"+type) : 0;
	}
	
	@Override
	public boolean hitEntity(ItemStack item, EntityLivingBase target, EntityLivingBase user) {
		drain(item, target, user);
		return false;
	}

	public void drain(ItemStack stack, EntityLivingBase target, EntityLivingBase entity) {
		if (!(entity instanceof EntityPlayer)) {
			return;
		}
		EntityPlayer player = (EntityPlayer)entity;
		World world = DimensionManager.getWorld(player.dimension);
		if (world == null) {
			return;
		}

		//Decide what energy one will receive
		if(world.provider.dimensionId == AbyssalCraft.configDimId1 && target instanceof ICoraliumEntity && !(target instanceof IBossDisplayData)) {
			// Attempting draining energy
			if (tryDrainEnergy(world, target, stack, "Abyssal")) {
				// Get energy is drain succeeded
				if(getEnergy(stack, "Abyssal") >= 200) {
					setEnergy(0, stack, "Abyssal");
					world.playSoundEffect(player.posX, player.posY, player.posZ, "abyssalcraft:event.essence", 1.0F, 1.0F);
					player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.essence, 1, 0));
				} else {
					world.playSoundEffect(player.posX, player.posY, player.posZ, "abyssalcraft:event.essence", 0.8F, 1.5F);
				}
			}
		} else if(world.provider.dimensionId == AbyssalCraft.configDimId2 && target instanceof IDreadEntity && !(target instanceof IBossDisplayData)){
			// Attempting draining energy
			if (tryDrainEnergy(world, target, stack, "Dread")) {
				// Get energy is drain succeeded
				if(getEnergy(stack, "Dread") >= 200) {
					setEnergy(0, stack, "Dread");
					world.playSoundEffect(player.posX, player.posY, player.posZ, "abyssalcraft:event.essence", 1.0F, 1.0F);
					player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.essence, 1, 1));
				} else {
					world.playSoundEffect(player.posX, player.posY, player.posZ, "abyssalcraft:event.essence", 0.8F, 1.5F);
				}
			}
		} else if(world.provider.dimensionId == AbyssalCraft.configDimId3 && target instanceof ICoraliumEntity && target instanceof IDreadEntity && target instanceof IAntiEntity && target.getCreatureAttribute() != AbyssalCraftAPI.SHADOW && !(target instanceof IBossDisplayData)) {
			// Attempting draining energy
			if (tryDrainEnergy(world, target, stack, "Omothol")) {
				// Get energy is drain succeeded
				if(getEnergy(stack, "Omothol") >= 200) {
					setEnergy(0, stack, "Omothol");
					world.playSoundEffect(player.posX, player.posY, player.posZ, "abyssalcraft:event.essence", 1.0F, 1.0F);
					player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.essence, 1, 2));
				} else {
					world.playSoundEffect(player.posX, player.posY, player.posZ, "abyssalcraft:event.essence", 0.8F, 1.5F);
				}
			}
		} else if(target.getCreatureAttribute() == AbyssalCraftAPI.SHADOW && !(target instanceof IBossDisplayData)) {
			// Attempting draining energy
			if (tryDrainEnergy(world, target, stack, "Shadow")) {
				// Get energy is drain succeeded
				if(getEnergy(stack, "Shadow") >= 500) {
					setEnergy(0, stack, "Shadow");
					world.playSoundEffect(player.posX, player.posY, player.posZ, "abyssalcraft:event.essence", 1.0F, 1.0F);
					player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.shadowgem));
				} else {
					world.playSoundEffect(player.posX, player.posY, player.posZ, "abyssalcraft:event.essence", 0.8F, 1.5F);
				}
			}
		}
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addInformation(ItemStack is, EntityPlayer player, List l, boolean B){
		int abyssal = getEnergy(is, "Abyssal");
		int dread = getEnergy(is, "Dread");
		int omothol = getEnergy(is, "Omothol");
		int shadow = getEnergy(is, "Shadow");
		l.add(StatCollector.translateToLocal("tooltip.drainstaff.energy.1")+": " + abyssal + "/200");
		l.add(StatCollector.translateToLocal("tooltip.drainstaff.energy.2")+": " + dread + "/200");
		l.add(StatCollector.translateToLocal("tooltip.drainstaff.energy.3")+": " + omothol + "/200");
		l.add(StatCollector.translateToLocal("tooltip.drainstaff.energy.4")+": " + shadow + "/500");
	}
}
