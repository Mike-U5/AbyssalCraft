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
import com.shinoow.abyssalcraft.common.items.ItemPortalPlacer;
import com.shinoow.abyssalcraft.common.items.ItemPortalPlacerDL;
import com.shinoow.abyssalcraft.common.items.ItemPortalPlacerJzh;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.DimensionManager;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemStaff extends Item {

	public ItemStaff() {
		super();
		setMaxStackSize(1);
	}
	
	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer player, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
		int dimId = par3World.provider.dimensionId;
		if(player.dimension == 0 || dimId == AbyssalCraft.configDimId1 && player.isSneaking()) {
			ItemPortalPlacer pp = new ItemPortalPlacer();
			pp.onItemUse(par1ItemStack, player, par3World, par4, par5, par6, par7, par8, par9, par10);
		} else if(dimId == AbyssalCraft.configDimId1 || dimId == AbyssalCraft.configDimId2 && player.isSneaking()) {
			ItemPortalPlacerDL pp = new ItemPortalPlacerDL();
			pp.onItemUse(par1ItemStack, player, par3World, par4, par5, par6, par7, par8, par9, par10);
		} else if(dimId == AbyssalCraft.configDimId2 || dimId == AbyssalCraft.configDimId3 || dimId == AbyssalCraft.configDimId4) {
			ItemPortalPlacerJzh pp = new ItemPortalPlacerJzh();
			pp.onItemUse(par1ItemStack, player, par3World, par4, par5, par6, par7, par8, par9, par10);
		}
		return true;
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {
		return EnumChatFormatting.BLUE + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
	}

	public void increaseEnergy(ItemStack stack, String type){
		if(!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		stack.stackTagCompound.setInteger("energy"+type, getEnergy(stack, type) + 1);
	}

	public void setEnergy(int amount, ItemStack stack, String type){
		stack.stackTagCompound.setInteger("energy"+type, amount);
	}

	public int getEnergy(ItemStack par1ItemStack, String type) {
		return par1ItemStack.hasTagCompound() && par1ItemStack.stackTagCompound.hasKey("energy"+type) ? (int)par1ItemStack.stackTagCompound.getInteger("energy"+type) : 0;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List l, boolean B){
		l.add(StatCollector.translateToLocal("tooltip.staff"));
		int abyssal = getEnergy(is, "Abyssal");
		int dread = getEnergy(is, "Dread");
		int omothol = getEnergy(is, "Omothol");
		int shadow = getEnergy(is, "Shadow");
		l.add(StatCollector.translateToLocal("tooltip.drainstaff.energy.1")+": " + abyssal + "/25");
		l.add(StatCollector.translateToLocal("tooltip.drainstaff.energy.2")+": " + dread + "/25");
		l.add(StatCollector.translateToLocal("tooltip.drainstaff.energy.3")+": " + omothol + "/25");
		l.add(StatCollector.translateToLocal("tooltip.drainstaff.energy.4")+": " + shadow + "/50");
	}

	@Override
	public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase) {
		drain(par1ItemStack, par2EntityLivingBase, par3EntityLivingBase);
		return true;
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
			increaseEnergy(stack, "Abyssal");
			if(getEnergy(stack, "Abyssal") >= 25) {
				setEnergy(0, stack, "Abyssal");
				player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.essence, 1, 0));
			}
		} else if(world.provider.dimensionId == AbyssalCraft.configDimId2 && target instanceof IDreadEntity && !(target instanceof IBossDisplayData)){
			increaseEnergy(stack, "Dread");
			if(getEnergy(stack, "Dread") >= 25) {
				setEnergy(0, stack, "Dread");
				player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.essence, 1, 1));
			}
		} else if(world.provider.dimensionId == AbyssalCraft.configDimId3 && target instanceof ICoraliumEntity && target instanceof IDreadEntity && target instanceof IAntiEntity && target.getCreatureAttribute() != AbyssalCraftAPI.SHADOW && !(target instanceof IBossDisplayData)) {
			increaseEnergy(stack, "Omothol");
			if(getEnergy(stack, "Omothol") >= 25) {
				setEnergy(0, stack, "Omothol");
				player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.essence, 1, 2));
			}
		} else if(target.getCreatureAttribute() == AbyssalCraftAPI.SHADOW && !(target instanceof IBossDisplayData)) {
			increaseEnergy(stack, "Shadow");
			if(getEnergy(stack, "Shadow") >= 50) {
				setEnergy(0, stack, "Shadow");
				player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.shadowgem));
			}
		}
	}
}
