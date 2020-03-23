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
package com.shinoow.abyssalcraft.common.potion;

import java.util.ArrayList;
import java.util.List;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.common.util.EntityUtil;
import com.shinoow.abyssalcraft.common.util.ItemUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.FoodStats;
import net.minecraft.util.ResourceLocation;

public class PotionDplague extends Potion {

	public PotionDplague(int id, boolean isBadEffect, int liquidColor) {
		super(id, isBadEffect, liquidColor);
	}

	
	@Override
	public Potion setIconIndex(int par1, int par2) {
		super.setIconIndex(par1, par2);
		return this;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public void performEffect(EntityLivingBase entity, int amp) {
		// Dread Entities are immune
		if (EntityUtil.isEntityDread(entity)) {
			entity.removePotionEffect(AbyssalCraft.Dplague.id);
			return;
		}

		// Get how long this effect still applies and what the power is
		final int ticksLeft = entity.getActivePotionEffect(this).getDuration();
		///final int intensity = Math.max(350 - (ticksLeft / 40), 50); // Up to 300 is subtracted

		// Happens Every 10 Ticks
		if (entity.ticksExisted % 10 == 0) {
			// Add dread to entity
			final boolean doActivate = this.addDreadCells(entity, 2000 + ticksLeft);
			
			// Dreadify a random inventory item
			if (entity instanceof EntityPlayer) {
				final EntityPlayer player = (EntityPlayer) entity;
				final int invSlot = player.worldObj.rand.nextInt(player.inventory.getSizeInventory());
				ItemUtil.dreadifyFood(player.inventory.getStackInSlot(invSlot));
			}

			// Remove when in Flowing Water
			if (EntityUtil.isSubmergedInWater(entity)) {
				// Harm Entity for forcefully expelling the plague
				entity.attackEntityFrom(AbyssalCraftAPI.dread, 2 * (amp + 1));
				exhaust(entity, amp);

				// Remove if ticks are low
				if (ticksLeft <= 1500) {
					entity.removePotionEffect(AbyssalCraft.Dplague.id);
				} else {
					entity.removePotionEffect(AbyssalCraft.Dplague.id);
					final PotionEffect plague = new PotionEffect(AbyssalCraft.Dplague.id, ticksLeft - 1500);
					plague.setCurativeItems(new ArrayList<ItemStack>());
					entity.addPotionEffect(plague);
				}

				entity.worldObj.playSoundAtEntity(entity, "abyssalcraft:event.dreadwail", 1F, 1F);
				return;
			}
			
			// Inflict Damage and exhaustion
			if (doActivate) {
				entity.attackEntityFrom(AbyssalCraftAPI.dread, 1 + amp);
				exhaust(entity, amp);
			}
		}

		// Afflict nearby entities
		if (!entity.worldObj.isRemote && ticksLeft > 1) {
			final List<EntityLivingBase> entities = entity.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, entity.boundingBox.expand(2D, 2D, 2D));
			for (EntityLivingBase e : entities) {
				if (e != entity) {
					EntityUtil.applyDreadPlague(e, ticksLeft - 1);
				}
			}
		}

		// While in the Dreadlands, tick up rather than down
		if (entity.dimension == AbyssalCraft.configDimId2 && ticksLeft <= 12000) {
			EntityUtil.applyDreadPlague(entity, ticksLeft + 1);
		}
	}

	
	// Make the player hungrier
	private void exhaust(EntityLivingBase entity, int amp) {
		if (entity instanceof EntityPlayer) {
			final EntityPlayer player = (EntityPlayer)entity;
			final FoodStats foodstats = player.getFoodStats();
			
			if (foodstats.getSaturationLevel() > 0) {
				final float saturationDrain = 0.05F * (1 + amp);
				foodstats.setFoodSaturationLevel(foodstats.getSaturationLevel() - saturationDrain);
			} else {
				final float exhaustionDrain = 0.1F * (1 + amp);
				player.addExhaustion(exhaustionDrain);
			}
		}
	}
	
	private boolean addDreadCells(EntityLivingBase entity, int amount) {
		boolean triggerEffect = false;
		final NBTTagCompound nbt = entity.getEntityData();
		int dreadcells = (nbt.hasKey("dreadcells")) ? nbt.getInteger("dreadcells") : 0;
		dreadcells += amount;
		if (dreadcells >= 70000) {
			triggerEffect = true;
			dreadcells -= 70000;
		}
		nbt.setInteger("dreadcells", dreadcells);
		entity.writeEntityToNBT(nbt);
		return triggerEffect;
	}

	
	@Override
	public boolean isReady(int ticksLeft, int amplifier) {
		return true;
	}

	
	@Override
	@SideOnly(Side.CLIENT)
	public int getStatusIconIndex() {
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("abyssalcraft:textures/misc/potionFX.png"));
		return 1;
	}
}
