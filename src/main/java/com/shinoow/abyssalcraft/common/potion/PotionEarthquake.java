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

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class PotionEarthquake extends Potion{

	public PotionEarthquake(int par1, boolean par2, int par3) {
		super(par1, par2, par3);
	}

	@Override
	public Potion setIconIndex(int par1, int par2) {
		super.setIconIndex(par1, par2);
		return this;
	}

	@Override
	public void performEffect(EntityLivingBase target, int amp) {
		if (target.onGround && target.hurtResistantTime == 0) {
			target.attackEntityFrom(AbyssalCraftAPI.earthquake, 1F);
		} else if (target.ticksExisted % 5 == 0 && target instanceof EntityPlayer) {
			((EntityPlayer) target).performHurtAnimation();
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
		return 3;
	}
}