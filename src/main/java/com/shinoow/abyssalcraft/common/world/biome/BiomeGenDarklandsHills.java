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
package com.shinoow.abyssalcraft.common.world.biome;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.biome.IDarklandsBiome;
import com.shinoow.abyssalcraft.common.entity.EntityAbyssalZombie;
import com.shinoow.abyssalcraft.common.entity.EntityDepthsGhoul;
import com.shinoow.abyssalcraft.common.entity.EntityShadowBeast;
import com.shinoow.abyssalcraft.common.entity.EntityShadowCreature;
import com.shinoow.abyssalcraft.common.entity.EntityShadowMonster;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiPlayer;
import com.shinoow.abyssalcraft.common.world.gen.WorldGenDLT;

public class BiomeGenDarklandsHills extends BiomeGenDarklands implements IDarklandsBiome {
	@SuppressWarnings("unchecked")
	public BiomeGenDarklandsHills(int par1) {
		super(par1);
		rootHeight = 1.1F;
		heightVariation = 0.5F;
		setTemperatureRainfall(0.85F, 0F);
		topBlock = AbyssalCraft.Darkgrass;
		fillerBlock = AbyssalCraft.Darkstone;
		waterColorMultiplier = 14745518;
		WorldGenDarkTrees = new WorldGenDLT(false);
		theBiomeDecorator.treesPerChunk = 1;
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableMonsterList.clear();
		spawnableMonsterList.add(new SpawnListEntry(EntityDepthsGhoul.class, 55, 1, 5));
		spawnableMonsterList.add(new SpawnListEntry(EntityAbyssalZombie.class, 55, 1, 3));
		spawnableMonsterList.add(new SpawnListEntry(EntityAntiPlayer.class, 15, 1, 1));
		spawnableMonsterList.add(new SpawnListEntry(EntityShadowCreature.class, 35, 1, 1));
		spawnableMonsterList.add(new SpawnListEntry(EntityShadowMonster.class, 25, 1, 1));
		spawnableMonsterList.add(new SpawnListEntry(EntityShadowBeast.class, 15, 1, 1));
	}
}