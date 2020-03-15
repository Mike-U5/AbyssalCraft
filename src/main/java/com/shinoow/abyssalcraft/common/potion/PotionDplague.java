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

import java.util.List;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.entity.IDreadEntity;
import com.shinoow.abyssalcraft.common.util.EntityUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
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
		
		// Get how long this effect still applies
		final int ticksLeft = entity.getActivePotionEffect(this).getDuration();
		
		// Remove if in flowing water... at a cost
		if (entity.ticksExisted % 5 == 0) {
			if (EntityUtil.inFlowingWater(entity)) {
				entity.attackEntityFrom(AbyssalCraftAPI.dread, ticksLeft / 100);
				if (entity instanceof EntityPlayer) {
					((EntityPlayer)entity).addExhaustion(ticksLeft / 10000);
					entity.worldObj.playSoundAtEntity(entity, "event.dreadwail", 1F, 1F);
				}
				entity.removePotionEffect(AbyssalCraft.Dplague.id);
				return;
			}
		}
		
		// Inflict Damage
		if (entity.ticksExisted % 200 == 0) {
			entity.attackEntityFrom(AbyssalCraftAPI.dread, 1);
		}

		// Add some exhaustion to the player
		if(entity instanceof EntityPlayer && entity.ticksExisted % 4 == 0) {
			((EntityPlayer)entity).addExhaustion(0.02F);
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
		
		// Do not tick down in Dreadlands
		if (entity.dimension == AbyssalCraft.configDimId2) {
			EntityUtil.applyDreadPlague(entity, ticksLeft + 1);
		}
		
		// Play Sound when it vanishes
		if (ticksLeft == 0) {
			entity.worldObj.playSoundAtEntity(entity, "event.dreadwail", 1F, 1F);
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
