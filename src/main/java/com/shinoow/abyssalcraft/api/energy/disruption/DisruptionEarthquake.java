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
package com.shinoow.abyssalcraft.api.energy.disruption;

import java.util.List;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI.ACPotions;
import com.shinoow.abyssalcraft.api.energy.EnergyEnum.DeityType;
import com.shinoow.abyssalcraft.common.potion.CurseEffect;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

/**
 * A Potion Disruption Entry
 * @author shinoow
 *
 * @since 1.5
 */
public class DisruptionEarthquake extends DisruptionEntry {

	/**
	 * Earthquake Curse Disruption
	 * @param unlocalizedName A String representing the disruption name (mainly used for Necronomicon categorization)
	 * @param potion A Potion Effect
	 */
	public DisruptionEarthquake(String unlocalizedName, DeityType deity) {
		super(unlocalizedName, deity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void disrupt(World world, int x, int y, int z, List<EntityPlayer> players) {
		if(!world.isRemote) {
			final int duration = 500 + world.rand.nextInt(120);
			List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1).expand(16, 16, 16));

			for(EntityLivingBase entity : entities) {
				if(!(entity instanceof IBossDisplayData)) {
					entity.addPotionEffect(new CurseEffect(ACPotions.Earthquake.id, duration));
				}
			}
		}
	}
}
