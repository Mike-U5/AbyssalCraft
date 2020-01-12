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
import com.shinoow.abyssalcraft.api.entity.IDreadEntity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class PotionDplagueX extends Potion {

	public PotionDplagueX(int par1, boolean par2, int par3) {
		super(par1, par2, par3);
	}

	@Override
	public Potion setIconIndex(int par1, int par2) {
		super.setIconIndex(par1, par2);
		return this;
	}

	@Override
	public void performEffect(EntityLivingBase entity, int power) {
		// Dread Entities are immune
		if(entity instanceof IDreadEntity) {
			entity.removePotionEffect(AbyssalCraft.Dplague.id);
			return;
		}
		
		// Perform Effect
		if (entity.dimension == AbyssalCraft.configDimId2) {
			// In the Dreadlands, don't tick down unless on a dreadlog
			int duration = entity.getActivePotionEffect(AbyssalCraft.Dplague).getDuration();
			if (!this.isOnDreadDrainBlock(entity)) { 
				PotionEffect newPlague = new PotionEffect(AbyssalCraft.Dplague.id, duration + 20);
				entity.addPotionEffect(newPlague);
				// Occasionally harm the affected entity
				if (entity.ticksExisted % 1200 == 0) {
					entity.attackEntityFrom(AbyssalCraftAPI.dread, 1);
				}
			}
		} else {
			// Outside, drain but hurt the player
			entity.attackEntityFrom(AbyssalCraftAPI.dread, 1);
		}

		// Add some exhaustion to the player
		if(entity instanceof EntityPlayer) {
			((EntityPlayer)entity).addExhaustion(0.025F);
		}
	}

	@Override
	public boolean isReady(int ticksLeft, int amplifier) {
		return ticksLeft % 20 == 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getStatusIconIndex() {
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("abyssalcraft:textures/misc/potionFX.png"));
		return 1;
	}
	
	private boolean isOnDreadDrainBlock(EntityLivingBase entity) {
		int xPos = (int)Math.floor(entity.posX);
		int yPos = (int)Math.floor(entity.posY) - 1;
		int zPos = (int)Math.floor(entity.posZ);
		Block block = entity.worldObj.getBlock(xPos, yPos, zPos);
		if (block == AbyssalCraft.dreadlog) {
			return true;
		}
		return false;
	}
}
