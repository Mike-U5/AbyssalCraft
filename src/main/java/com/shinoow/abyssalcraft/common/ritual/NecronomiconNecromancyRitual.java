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

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconSummonRitual;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class NecronomiconNecromancyRitual extends NecronomiconSummonRitual {
	
	private NBTTagCompound petData;

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
		System.out.println(nbtItem);
		return ItemStack.loadItemStackFromNBT(nbtItem);
	}
	
	private void reviveEntity(NBTTagCompound data, World world, int x, int y, int z) {
		Entity entity = EntityList.createEntityFromNBT(data, world);
		if (entity instanceof EntityLivingBase) {
			final EntityLivingBase revivedEntity = (EntityLivingBase)entity;
			revivedEntity.setLocationAndAngles(x, y, z, 0, 0);
			revivedEntity.motionX = 0;
			revivedEntity.motionY = 0;
			revivedEntity.motionZ = 0;
			revivedEntity.fallDistance = 0;
			revivedEntity.setHealth(revivedEntity.getMaxHealth());
			revivedEntity.clearActivePotions();
			world.spawnEntityInWorld(revivedEntity);
		}
	}

	@Override
	public boolean canCompleteRitual(World world, int x, int y, int z, EntityPlayer player) {
		final ItemStack mementoStack = getItemStackFromPedestal(world, x, y, z);
		
		if (mementoStack.getItem() == AbyssalCraft.memento) {
			this.petData = mementoStack.getTagCompound();
			return true;
		}

		return false;
	}

	@Override
	protected void completeRitualServer(World world, int x, int y, int z, EntityPlayer player) {
		if (this.petData != null) {
			System.out.println(this.petData);
			this.reviveEntity(this.petData, world, x, y + 2, z);
			this.petData = null;
		} else {
			System.err.println("Fuck mementoStack is undefined.");
		}
	}
}
