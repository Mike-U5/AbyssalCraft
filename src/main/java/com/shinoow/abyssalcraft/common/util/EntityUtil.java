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
package com.shinoow.abyssalcraft.common.util;

import java.util.ArrayList;
import java.util.UUID;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.entity.IAntiEntity;
import com.shinoow.abyssalcraft.api.entity.ICoraliumEntity;
import com.shinoow.abyssalcraft.api.entity.IDreadEntity;
import com.shinoow.abyssalcraft.common.entity.EntityGatekeeperMinion;
import com.shinoow.abyssalcraft.common.entity.EntityOmotholGhoul;
import com.shinoow.abyssalcraft.common.entity.EntityRemnant;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAbomination;
import com.shinoow.abyssalcraft.common.items.ItemCrozier;

import baubles.api.BaublesApi;
import net.minecraft.block.material.Material;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public final class EntityUtil {

	private EntityUtil(){}

	/**
	 * Checks if the Entity is immune to the Coralium Plague
	 * @param par1 The Entity to check
	 * @return True if the Entity is immune, otherwise false
	 */
	public static boolean isEntityCoralium(EntityLivingBase par1){
		return par1 instanceof ICoraliumEntity || par1 instanceof EntityPlayer && isPlayerCoralium((EntityPlayer)par1);
	}

	/**
	 * Checks if a Player has a certain name, and nulls the Coralium Plague if they do
	 * @param par1 The Player to check
	 * @return True if the Player has a certain name, otherwise false
	 */
	public static final boolean isPlayerCoralium(EntityPlayer par1){
		if(Vars.dev)
			return par1.getCommandSenderName().equals("shinoow") || par1.getCommandSenderName().equals("Oblivionaire");
		else return par1.getUniqueID().equals(Vars.uuid1) || par1.getUniqueID().equals(Vars.uuid2);
	}

	/**
	 * Checks if the Entity is immune to the Dread Plague
	 * @param par1 The Entity to check
	 * @return True if the Entity is immune, otherwise false
	 */
	public static boolean isEntityDread(EntityLivingBase entity) {
		return entity instanceof IDreadEntity;
	}

	/**
	 * Checks if the Entity is immune to Antimatter
	 * @param par1 The Entity to check
	 * @return True if the Entity is immune, otherwise false
	 */
	public static boolean isEntityAnti(EntityLivingBase par1){
		return par1 instanceof IAntiEntity;
	}
	
	public static boolean isEntityOmothol(EntityLivingBase entity) {
		return (
			entity instanceof EntityRemnant || 
			entity instanceof EntityGatekeeperMinion || 
			entity instanceof EntityOmotholGhoul
		);
	}

	/**
	 * Checks if a Player has a Necronomicon
	 * @param player The Player to check
	 * @return True if the Player has a Necronomicon, otherwise false
	 */
	public static boolean hasNecronomicon(EntityPlayer player) {
		return player.inventory.hasItem(AbyssalCraft.necronomicon) || player.inventory.hasItem(AbyssalCraft.necronomicon_cor) ||
				player.inventory.hasItem(AbyssalCraft.necronomicon_dre) || player.inventory.hasItem(AbyssalCraft.necronomicon_omt) ||
				player.inventory.hasItem(AbyssalCraft.abyssalnomicon);
	}
	
	/**
	 * You must have a Omothol Necronomicon to talk to Remnants.
	 */
	public static boolean hasEldritchTongue(EntityPlayer player) {
		if (EntityUtil.isPlayerWearingPendant(player)) {
			return true;
		}
		return player.inventory.hasItem(AbyssalCraft.necronomicon_omt) || player.inventory.hasItem(AbyssalCraft.abyssalnomicon);
	}

	static class Vars{
		static boolean dev = (Boolean)Launch.blackboard.get("fml.deobfuscatedEnvironment");
		static UUID uuid1 = UUID.fromString("a5d8abca-0979-4bb0-825a-f1ccda0b350b");
		static UUID uuid2 = UUID.fromString("08f3211c-d425-47fd-afd8-f0e7f94152c4");
	}
	
	/**
	 * Get a vector between entities
	 */
	public static Vec3 getVectorBetweenEntities(Entity e, Entity target) {
		Vec3 fromPosition = Vec3.createVectorHelper(e.posX, e.posY, e.posZ);
		Vec3 toPosition = Vec3.createVectorHelper(target.posX, target.posY, target.posZ);
		Vec3 dist = fromPosition.subtract(toPosition);
		dist.normalize();
		return dist;
	}
	
	/**
	 * This selector will target players unless they hold a Crozier
	 */
	public static IEntitySelector selectorCrozier = new IEntitySelector() {
		@Override
		public boolean isEntityApplicable(Entity e) {
			if (e instanceof EntityLivingBase) {
				final ItemStack stack = ((EntityLivingBase)e).getHeldItem();
				if (stack != null && stack.getItem() instanceof ItemCrozier) {
					return false;
				}
			}
			return true;
		}
	};
	
	public static boolean isPlayerWearingPendant(EntityPlayer player) {
		final ItemStack amulet = BaublesApi.getBaubles(player).getStackInSlot(0);
		if (amulet != null && amulet.getItem() == AbyssalCraft.trapezohedron) {
			return true;
		}
		return false;
	}
	
	public static boolean isSubmergedInWater(EntityLivingBase entity) {
		return (entity.isInsideOfMaterial(Material.water) && entity.isInWater());
	}
	
	public static void meltEyes(EntityPlayer player) {
		// Check if player is wearing pendant
		if (!EntityUtil.isPlayerWearingPendant(player)) {
			// Apply BAD THINGS
			player.addPotionEffect(new PotionEffect(AbyssalCraft.blurred.id, 20));
			if (!player.isPotionActive(Potion.confusion) || player.getActivePotionEffect(Potion.confusion).getDuration() > 25) {
				player.addPotionEffect(new PotionEffect(Potion.confusion.id, 115));
			}
		}
	}
	
	// Adds duration to the victim's Dread Plague if it's not immune
	public static void increaseDreadPlague(EntityLivingBase entity, int timeToAdd) {
		if (!EntityUtil.isEntityDread(entity) && !EntityUtil.isSubmergedInWater(entity)) {
			// Add Duration to Plague
			int newDuration = timeToAdd;
			if (entity.isPotionActive(AbyssalCraftAPI.potionId2)) {
				newDuration += entity.getActivePotionEffect(Potion.potionTypes[AbyssalCraftAPI.potionId2]).getDuration();
			}
			
			// Cap at 12000
			if (newDuration > 12000) {
				newDuration = 12000;
			}
			
			final PotionEffect plague = new PotionEffect(AbyssalCraft.Dplague.id, newDuration);
			plague.setCurativeItems(new ArrayList<ItemStack>());
			entity.addPotionEffect(plague);
		}
	}
	
	// Add Dread Plague to a victim if it's not immune
	public static void applyDreadPlague(EntityLivingBase entity, int duration) {
		if (!EntityUtil.isEntityDread(entity) && !EntityUtil.isSubmergedInWater(entity)) {
			// Apply the Dread Plague with the set duration
			final PotionEffect plague = new PotionEffect(AbyssalCraft.Dplague.id, duration);
			plague.setCurativeItems(new ArrayList<ItemStack>());
			entity.addPotionEffect(plague);
		}
	}
	
	private static Entity getReviveEntity(NBTTagCompound nbt, String name, World world) {
		if (Math.random() < 0.3F) {
			final EntityAbomination abo = new EntityAbomination(world);
			abo.setCustomNameTag(name);
			return abo;
		}
		
		return EntityList.createEntityFromNBT(nbt, world);
	}
	
	/** Check Necromancy Capability of the player **/
	public static void getNecroCapacility(EntityPlayer player) {
		if (player.worldObj.isRemote) {
			return;
		}
		
		final NecroPetList necroList = new NecroPetList(player);
		if (necroList.size() > 0) {
			final NBTTagCompound petData = necroList.shift();
			if (petData.hasKey("CustomName")) {
				final Entity e = EntityUtil.getReviveEntity(petData, petData.getString("CustomName"), player.worldObj);
				if (e instanceof EntityLivingBase) {
					final EntityLivingBase revivedEntity = (EntityLivingBase)e;
					revivedEntity.copyLocationAndAnglesFrom(player);
					revivedEntity.motionX = 0;
					revivedEntity.motionY = 0;
					revivedEntity.motionZ = 0;
					revivedEntity.fallDistance = 0;
					revivedEntity.setHealth(revivedEntity.getMaxHealth());
					necroList.writeToPlayer();
					player.worldObj.spawnEntityInWorld(revivedEntity);
				}
			}
		}
	}
}
