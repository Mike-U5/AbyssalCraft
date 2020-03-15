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
import com.shinoow.abyssalcraft.api.entity.IDreadEntity;
import com.shinoow.abyssalcraft.common.util.EntityUtil;
import com.shinoow.abyssalcraft.common.util.ItemUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.FoodStats;
import net.minecraft.util.ResourceLocation;

public class PotionDplague extends Potion {

	public PotionDplague(int par1, boolean par2, int par3) {
		super(par1, par2, par3);
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
		if(entity instanceof IDreadEntity) {
			entity.removePotionEffect(AbyssalCraft.Dplague.id);
			return;
		}
		
		// Get how long this effect still applies and what the power is
		final int ticksLeft = entity.getActivePotionEffect(this).getDuration();
		final int intensity = 340 - (ticksLeft / 50); 
		
		// Happens Every 10 Ticks
		if (entity.ticksExisted % 10 == 0) {
			// Dreadify a random inventory item
			if (entity instanceof EntityPlayer) {
				final EntityPlayer player = (EntityPlayer)entity;
				// Target Random Inventory Item
				final int invSlot = player.worldObj.rand.nextInt(player.inventory.getSizeInventory());
				ItemUtil.dreadifyFood(player.inventory.getStackInSlot(invSlot));
			}
			
			// Remove when in Flowing Water
			if (EntityUtil.inFlowingWater(entity)) {
				// Harm Entity for forcefully expelling the plague
				entity.attackEntityFrom(AbyssalCraftAPI.dread, 2);
				addHunger(entity);
				
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
		}
		
		// Inflict Damage and exhaustion
		if (entity.ticksExisted % intensity == 0) {
			entity.attackEntityFrom(AbyssalCraftAPI.dread, 1);
			addHunger(entity);
		}
		
		// Afflict nearby entities
		if (!entity.worldObj.isRemote) {
			final List<EntityLivingBase> entities = entity.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, entity.boundingBox.expand(2D, 2D, 2D));
			for (EntityLivingBase e : entities) {
				if (e != entity) {
					EntityUtil.applyDreadPlague(e, ticksLeft);
				}
			}
		}
		
		// While in the Dreadlands, tick up rather than down
		if (entity.dimension == AbyssalCraft.configDimId2 && ticksLeft < 12000) {
			EntityUtil.applyDreadPlague(entity, ticksLeft + 1);
		}
	}

	// Make the player hungrier
	private void addHunger(EntityLivingBase entity) {
		if (entity instanceof EntityPlayer) {
			FoodStats foodstats = ((EntityPlayer)entity).getFoodStats();
			if (foodstats.getSaturationLevel() > 0) {
				foodstats.setFoodSaturationLevel(foodstats.getSaturationLevel() - 0.25F);
			} else if (foodstats.getFoodLevel() > 0) {
				foodstats.setFoodLevel(foodstats.getFoodLevel() - 1);
			}
		}
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
