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
package com.shinoow.abyssalcraft.common.ritual;

import com.shinoow.abyssalcraft.api.ritual.NecronomiconSummonRitual;
import com.shinoow.abyssalcraft.common.util.EntityUtil;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class NecronomiconNecromancyRitual extends NecronomiconSummonRitual {
	
	private ItemStack ped1Stack;

	public NecronomiconNecromancyRitual(String unlocalizedName, int bookType, float requiredEnergy, Class<? extends EntityLivingBase> entity, Object...offerings) {
		super(unlocalizedName, bookType, requiredEnergy, entity, offerings);
	}
	
	protected NBTTagCompound getCompoundFromPedestal(World world, int x, int y, int z) {
		final TileEntity ped = world.getTileEntity(x - 3, y, z);
		final NBTTagCompound compound = new NBTTagCompound();
		ped.writeToNBT(compound);
		return compound;
	}
	
	protected ItemStack getItemStackFromPedestal(World world, int x, int y, int z) {
		final NBTTagCompound nbtPedestal = getCompoundFromPedestal(world, x, y, z);
		final NBTTagCompound nbtItem = nbtPedestal.getCompoundTag("Item");
		System.out.println(nbtItem.toString());
		return ItemStack.loadItemStackFromNBT(nbtItem);
	}

	@Override
	public boolean canCompleteRitual(World world, int x, int y, int z, EntityPlayer player) {
		this.ped1Stack = getItemStackFromPedestal(world, x, y, z);
		return true;
		// Get Sacrifice
		/*final ItemStack sacrificeStack = this.getAltarItemStack(world, x, y, z);
		
		// Check if player has necropets
		final NecroPetList necroList = new NecroPetList(player);
		System.out.print("Necrosize: " + necroList.size());
		if (necroList.size() > 0) {
			if (this.getSacrifice() instanceof ItemStack) {
				System.out.print(sacrificeStack.getDisplayName());
				if (sacrificeStack.getDisplayName().equals("Brian")) {
					System.out.println("Can do ritual!");
					return true;
				} else {
					System.out.println("Failure due to name not being Brian!");
				}
			} else {
				System.out.println("Failure due to sacrifice not being an ItemStack!");
			}
		} else {
			System.out.println("Failure due to lack of necropets!");
		}
		return false;*/
	}

	@Override
	protected void completeRitualServer(World world, int x, int y, int z, EntityPlayer player) {
		final ItemStack mementoStack = this.ped1Stack;
		if (mementoStack != null) {
			System.out.println(mementoStack.toString());
			MinecraftServer.getServer().addChatMessage(new ChatComponentText(mementoStack.toString()));
			EntityUtil.necroByName(player, "Brian", x, y, z);
		} else {
			System.err.println("Fuck mementoStack is undefined.");
		}
	}
}
