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

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.ThaumcraftApiHelper;

public class PotionAuditory extends Potion {

	public PotionAuditory(int par1, boolean par2, int par3) {
		super(par1, par2, par3);
	}

	@Override
	public Potion setIconIndex(int par1, int par2) {
		super.setIconIndex(par1, par2);
		return this;
	}

	@Override
	public void performEffect(EntityLivingBase entity, int amp) {
		if (entity instanceof EntityPlayer) {
			final EntityPlayer player = (EntityPlayer)entity;
			if (player.dimension == AbyssalCraft.configDimId3 || player.dimension == AbyssalCraft.configDimId4) {
				if (player.worldObj.isRemote) {
					player.playSound("abyssalcraft:jzahar.speak", 2F, 1F);
				} else {
					ThaumcraftApiHelper.addWarpToPlayer(player, 1, true);
				}
			}
		}
	}

	@Override
	public boolean isReady(int ticksLeft, int amp) {
		return ticksLeft == 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getStatusIconIndex() {
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("abyssalcraft:textures/misc/potionFX.png"));
		return 3;
	}
}