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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.shinoow.abyssalcraft.api.energy.EnergyEnum.DeityType;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;

/**
 * A Swarm Disruption Entry
 * @author shinoow
 *
 * @since 1.5
 */
public class DisruptionBiomeSwarm extends DisruptionEntry {

	private Class<? extends EntityLivingBase>[] entities;

	/**
	 * A Spawning Disruption Entry
	 * @param unlocalizedName A String representing the disruption name (mainly used for Necronomicon categorization)
	 * @param deity Deity whose image must be present for this to happen
	 * @param entities An array of entities to spawn
	 */
	public DisruptionBiomeSwarm(String unlocalizedName, DeityType deity, BiomeGenBase biome) {
		super(unlocalizedName, deity);
	}

	@Override
	public void disrupt(World world, int x, int y, int z, List<EntityPlayer> players) {
		if(world.isRemote) {
			return;
		}

		List<SpawnListEntry> l = BiomeGenBase.hell.getSpawnableList(EnumCreatureType.monster);
		if(!l.isEmpty()) {
			x = x - 10;
			z = z - 10;
			for(int i = 0; i < 4; i++)  {
				try {
					SpawnListEntry entry = (SpawnListEntry) WeightedRandom.getRandomItem(world.rand, l);
					EntityLiving entity = (EntityLiving) entry.entityClass.getConstructor(World.class).newInstance(world);
					x += 1 + world.rand.nextInt(6);
					z += 1 + world.rand.nextInt(6);
					entity.setLocationAndAngles(x, y + world.rand.nextInt(4), z, entity.rotationYaw, entity.rotationPitch);
					//entity.onInitialSpawn(world.getDifficultyForLocation(pos1), (IEntityLivingData)null);
					world.spawnEntityInWorld(entity);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
