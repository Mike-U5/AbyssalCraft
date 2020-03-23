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

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.common.util.EntityUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class PotionCplague extends Potion{

	public PotionCplague(int par1, boolean par2, int par3) {
		super(par1, par2, par3);
	}

	@Override
	public Potion setIconIndex(int par1, int par2) {
		super.setIconIndex(par1, par2);
		return this;
	}

	@Override
	public void performEffect(EntityLivingBase entity, int amp) {
		// Remove effect if target is immune
		if(EntityUtil.isEntityCoralium(entity)) {
			entity.removePotionEffect(AbyssalCraft.Cplague.id);
			return;
		}
		
		// Perform Effect
		final int frequency = (amp > 0) ? 25 : 50;
		if (entity.ticksExisted % frequency == 0) {
			entity.attackEntityFrom(AbyssalCraftAPI.coralium, 1);
			if (entity instanceof EntityPlayer) {
				this.damagePlayerGear((EntityPlayer)entity);
			} else {
				this.damageMobGear(entity);
			}
		}
	}
	
	private void damagePlayerGear(EntityPlayer player) {
		// Decay Armor
		final int armorIndex = player.worldObj.rand.nextInt(4);
		final ItemStack armor = player.getCurrentArmor(armorIndex);
		if (armor != null && armor.isItemStackDamageable() && !armor.getHasSubtypes()) {
			armor.damageItem(1, player);
		}
		
		// Decay Inventory
		final int itemIndex = player.worldObj.rand.nextInt(player.inventory.getSizeInventory());
		final ItemStack item  = player.inventory.getStackInSlot(itemIndex);
		if (item != null && item.isItemStackDamageable() && !item.getHasSubtypes()) {
			item.damageItem(1, player);
		}
	}
	
	private void damageMobGear(EntityLivingBase entity) {
		// Decay Armor
		final int armorIndex = entity.worldObj.rand.nextInt(4) + 1;
		final ItemStack armor = entity.getEquipmentInSlot(armorIndex);
		if (armor != null && armor.isItemStackDamageable() && !armor.getHasSubtypes()) {
			armor.damageItem(1, entity);
		}
		
		// Decay Held Item
		if (entity.worldObj.rand.nextInt(36) == 0) {
			final ItemStack heldItem = entity.getEquipmentInSlot(0);
			if (heldItem != null && heldItem.isItemStackDamageable() && !heldItem.getHasSubtypes()) {
				heldItem.damageItem(1, entity);
			}
		}
	}

	@Override
	public boolean isReady(int ticksLeft, int amp) {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getStatusIconIndex() {
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("abyssalcraft:textures/misc/potionFX.png"));
		return 0;
	}
}