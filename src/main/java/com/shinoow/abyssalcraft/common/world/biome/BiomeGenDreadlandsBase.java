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

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenMinable;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.biome.IDreadlandsBiome;
import com.shinoow.abyssalcraft.common.entity.EntityAbygolem;
import com.shinoow.abyssalcraft.common.entity.EntityChagarothFist;
import com.shinoow.abyssalcraft.common.entity.EntityDreadSpawn;
import com.shinoow.abyssalcraft.common.entity.EntityDreadgolem;
import com.shinoow.abyssalcraft.common.entity.EntityDreadguard;
import com.shinoow.abyssalcraft.common.entity.EntityDreadling;
import com.shinoow.abyssalcraft.common.entity.EntityGreaterDreadSpawn;
import com.shinoow.abyssalcraft.common.entity.EntityLesserDreadbeast;
import com.shinoow.abyssalcraft.common.entity.demon.EntityDemonPig;

public class BiomeGenDreadlandsBase extends BiomeGenBase implements IDreadlandsBiome {

	@SuppressWarnings("unchecked")
	public BiomeGenDreadlandsBase(int par1) {
		super(par1);
		topBlock = AbyssalCraft.dreadstone;
		fillerBlock = AbyssalCraft.dreadstone;
		spawnableMonsterList.clear();
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableCaveCreatureList.clear();
		theBiomeDecorator.treesPerChunk = -1;
		theBiomeDecorator.flowersPerChunk= -1;
		spawnableCreatureList.add(new SpawnListEntry(EntityAbygolem.class, 60, 1, 3));
		spawnableMonsterList.add(new SpawnListEntry(EntityDreadgolem.class, 60, 1, 3));
		spawnableMonsterList.add(new SpawnListEntry(EntityDreadSpawn.class, 30, 1, 2));
		spawnableMonsterList.add(new SpawnListEntry(EntityDreadling.class, 40, 1, 2));
		spawnableMonsterList.add(new SpawnListEntry(EntityChagarothFist.class, 2, 1, 1));
		spawnableMonsterList.add(new SpawnListEntry(EntityDemonPig.class, 5, 1, 2));
		spawnableMonsterList.add(new SpawnListEntry(EntityGreaterDreadSpawn.class, 5, 1, 1));
		spawnableMonsterList.add(new SpawnListEntry(EntityDreadguard.class, 8, 1, 1));
		spawnableMonsterList.add(new SpawnListEntry(EntityLesserDreadbeast.class, 1, 0, 1));
	}

	@Override
	public void decorate(World world, Random rnd, int par3, int par4) {
		super.decorate(world, rnd, par3, par4);

		if(AbyssalCraft.generateDreadedAbyssalniteOre) {
			for(int rarity = 0; rarity < 8; rarity++) {
				int veinSize =  4 + rnd.nextInt(12);
				int x = par3 + rnd.nextInt(16);
				int y = rnd.nextInt(60);
				int z = par4 + rnd.nextInt(16);

				new WorldGenMinable(AbyssalCraft.dreadore, veinSize, AbyssalCraft.dreadstone).generate(world, rnd, x, y, z);
			}
		}

		for (int rarity = 0; rarity < 3; ++rarity) {
			int x = par3 + rnd.nextInt(16);
			int y = rnd.nextInt(55);
			int z = par4 + rnd.nextInt(16);
			new WorldGenMinable(AbyssalCraft.abydreadstone, 16, AbyssalCraft.dreadstone).generate(world, rnd, x, y, z);
		}
		
		for (int rarity = 0; rarity < 32; ++rarity) {
			int x = par3 + rnd.nextInt(16);
			int y = rnd.nextInt(60);
			int z = par4 + rnd.nextInt(16);
			new WorldGenMinable(AbyssalCraft.magicdreadstone, 3, AbyssalCraft.dreadstone).generate(world, rnd, x, y, z);
		}
	}

	@Override
	public void genTerrainBlocks(World world, Random rand, Block[] blockArray, byte[] byteArray, int x, int z, double d) {
		genDreadlandsTerrain(world, rand, blockArray, byteArray, x, z, d);
	}

	public final void genDreadlandsTerrain(World world, Random rand, Block[] blockArray, byte[] byteArray, int x, int z, double d) {
		Block baseBlock = topBlock;
		byte b0 = (byte)(field_150604_aj & 255);
		Block stoneBlock = AbyssalCraft.dreadstone;
		int k = -1;
		int l = (int)(d / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
		int i1 = x & 15;
		int j1 = z & 15;
		int k1 = blockArray.length / 256;

		for (int l1 = 255; l1 >= 0; --l1) {
			int i2 = (j1 * 16 + i1) * k1 + l1;

			if (l1 <= 0 + rand.nextInt(5)) {
				blockArray[i2] = Blocks.bedrock;
			} else {
				Block block2 = blockArray[i2];

				if (block2 != null && block2.getMaterial() != Material.air) {
					if (block2 == AbyssalCraft.dreadstone) {
						if (k == -1) {
							if (l <= 0) {
								baseBlock = null;
								b0 = 0;
								stoneBlock = AbyssalCraft.dreadstone;
							} else if (l1 >= 59 && l1 <= 64) {
								baseBlock = topBlock;
								b0 = (byte)(field_150604_aj & 255);
								stoneBlock = fillerBlock;
							}

							if (l1 < 63 && (baseBlock == null || baseBlock.getMaterial() == Material.air)) {
								if (getFloatTemperature(x, l1, z) < 0.15F) {
									baseBlock = AbyssalCraft.dreadstone;
									b0 = 0;
								} else {
									baseBlock = AbyssalCraft.dreadstone;
									b0 = 0;
								}
							}

							k = l;

							if (l1 >= 62) {
								blockArray[i2] = baseBlock;
								byteArray[i2] = b0;
							} else if (l1 < 56 - l) {
								baseBlock = null;
								stoneBlock = AbyssalCraft.dreadstone;
								blockArray[i2] = AbyssalCraft.dreadstone;
							} else {
								blockArray[i2] = stoneBlock;
							}
						} else if (k > 0) {
							--k;
							blockArray[i2] = stoneBlock;

							if (k == 0 && stoneBlock == AbyssalCraft.dreadstone) {
								k = rand.nextInt(4) + Math.max(0, l1 - 63);
								stoneBlock = AbyssalCraft.dreadstone;
							}
						}
					}
				} else {
					k = -1;
				}
			}
		}
	}
}
