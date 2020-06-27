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
import com.shinoow.abyssalcraft.common.entity.anti.EntityAbomination;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
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
		final Entity entity = EntityList.createEntityFromNBT(data, world);
		if (entity instanceof EntityLiving) {
			final EntityLiving reviveEntity = (EntityLiving)entity;
			
			// Roll for Abdomination
			if (Math.random() < 0.25F) {
				final EntityAbomination abomination = new EntityAbomination(world);
				abomination.setLocationAndAngles(x, y, z, 0, 0);
				abomination.onSpawnWithEgg((IEntityLivingData)null);
				abomination.setCustomNameTag(reviveEntity.getCustomNameTag());
				world.spawnEntityInWorld(abomination);
				world.playSoundEffect(x, y, z, "abyssalcraft:event.necromancy.failure", 1.0F, 1.0F);
				return;
			}
			
			// Safe Revival
			reviveEntity.setLocationAndAngles(x, y, z, 0, 0);
			reviveEntity.motionX = 0;
			reviveEntity.motionY = 0;
			reviveEntity.motionZ = 0;
			reviveEntity.fallDistance = 0;
			reviveEntity.setHealth(reviveEntity.getMaxHealth());
			reviveEntity.extinguish();
			reviveEntity.removePotionEffect(Potion.poison.id);
			reviveEntity.removePotionEffect(Potion.wither.id);
			
			final NBTTagCompound reviveNbt = reviveEntity.getEntityData();
			reviveNbt.setBoolean("wasRevivedFromAbyss", true);
			reviveEntity.writeEntityToNBT(reviveNbt);
			
			world.spawnEntityInWorld(reviveEntity);
			world.playSoundEffect(x, y, z, "abyssalcraft:event.necromancy.success", 1.0F, 1.0F);
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
		}
	}
}
