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
import com.shinoow.abyssalcraft.common.entity.anti.EntityAbomination;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.entities.ITaintedMob;

public class PotionAntimatter extends Potion{

	public PotionAntimatter(int par1, boolean par2, int par3) {
		super(par1, par2, par3);
	}

	@Override
	public Potion setIconIndex(int par1, int par2) {
		super.setIconIndex(par1, par2);
		return this;
	}
	
	private boolean isImmune(EntityLivingBase entity) {
		if (entity instanceof EntityAbomination) {
			return true;
		}
		if (entity instanceof IBossDisplayData && (entity.isImmuneToFire() || entity instanceof ITaintedMob)) {
			return true;
		}
		return false;
	}
	
	@Override
	public void performEffect(EntityLivingBase entity, int par2) {
		if (isImmune(entity)) {
			entity.removePotionEffect(AbyssalCraft.antiMatter.id);
		} else {
			final int dmg = 3;
			final float health = entity.getHealth();
			entity.setHealth(health - dmg);
            entity.func_110142_aN().func_94547_a(AbyssalCraftAPI.antimatter, health, dmg);
            entity.setAbsorptionAmount(entity.getAbsorptionAmount() - dmg);
		}
	}

	@Override
	public boolean isReady(int ticksLeft, int amplifier) {
		return ticksLeft % 6 == 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getStatusIconIndex() {
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("abyssalcraft:textures/misc/potionFX.png"));
		return 2;
	}
}
