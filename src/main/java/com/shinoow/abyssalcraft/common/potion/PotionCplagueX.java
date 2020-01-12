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

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.common.util.EntityUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PotionCplagueX extends Potion{

	public PotionCplagueX(int par1, boolean par2, int par3) {
		super(par1, par2, par3);
	}

	@Override
	public Potion setIconIndex(int par1, int par2) {
		super.setIconIndex(par1, par2);
		return this;
	}
	
	private void performOutsideEffect(EntityLivingBase entity, PotionEffect eff) {
		if (eff.getDuration() % 40 == 0) {
			entity.attackEntityFrom(AbyssalCraftAPI.coralium, 1);
		}
	}
	
	private void performAbyssEffect(EntityLivingBase entity, PotionEffect eff, int amp) {
		if (eff.getDuration() <= 5) {
			int newAmp = Math.min(eff.getAmplifier() + 1, 4);
			entity.removePotionEffect(AbyssalCraft.Cplague.id);
			entity.addPotionEffect(new PotionEffect(AbyssalCraft.Cplague.id, 1205 + (amp*600), newAmp));
		}
		
		if (eff.getDuration() % (600/(amp+1)) == 0) {
			entity.attackEntityFrom(AbyssalCraftAPI.coralium, 1);
		}
	}

	@Override
	public void performEffect(EntityLivingBase entity, int amp) {
		
		// Gather Info
		World world = entity.worldObj;
		Block block = world.getBlock((int)(Math.floor(entity.posX)), (int)(Math.floor(entity.posY)), (int)(Math.floor(entity.posZ)));
		boolean inFlowingWater = (entity.isInWater() && block == Blocks.flowing_water);
		
		// Remove effect if conditions are met
		if(inFlowingWater || EntityUtil.isEntityCoralium(entity)) {
			entity.removePotionEffect(AbyssalCraft.Cplague.id);
			return;
		}
		
		// Perform effect
		if (entity.isPotionActive(AbyssalCraft.Cplague)) {
			PotionEffect eff = entity.getActivePotionEffect(AbyssalCraft.Cplague);
			if (entity.dimension == AbyssalCraft.configDimId1) {
				this.performAbyssEffect(entity, eff, amp);
			} else {
				this.performOutsideEffect(entity, eff);
			}
		}
	}

	@Override
	public boolean isReady(int ticksLeft, int amplifier) {
		return ticksLeft % 5 == 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getStatusIconIndex() {
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("abyssalcraft:textures/misc/potionFX.png"));
		return 0;
	}
}