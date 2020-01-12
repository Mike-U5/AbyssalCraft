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
/**
 * AbyssalCraft
 * Copyright 2012-2015 Shinoow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

import net.minecraft.init.Blocks;

public class BiomeGenDarklandsPlains extends BiomeGenDarklands implements IDarklandsBiome {
	@SuppressWarnings("unchecked")
	public BiomeGenDarklandsPlains(int par1) {
		super(par1);
		topBlock = AbyssalCraft.Darkgrass;
		fillerBlock = Blocks.dirt;
		setTemperatureRainfall(1.8F, 0F);
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