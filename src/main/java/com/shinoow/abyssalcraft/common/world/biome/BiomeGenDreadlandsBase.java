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

				new WorldGenMinable(AbyssalCraft.magicdreadstone, veinSize, AbyssalCraft.dreadstone).generate(world, rnd, x, y, z);
			}
		}

		for (int rarity = 0; rarity < 3; ++rarity) {
			int x = par3 + rnd.nextInt(16);
			int y = rnd.nextInt(55);
			int z = par4 + rnd.nextInt(16);
			new WorldGenMinable(AbyssalCraft.abydreadstone, 16, AbyssalCraft.dreadstone).generate(world, rnd, x, y, z);
		}
	}

	@Override
	public void genTerrainBlocks(World world, Random rand, Block[] blockArray, byte[] byteArray, int x, int z, double d) {
		genDreadlandsTerrain(world, rand, blockArray, byteArray, x, z, d);
	}

	public final void genDreadlandsTerrain(World world, Random rand, Block[] blockArray, byte[] byteArray, int x, int z, double d) {
		Block surfaceBlock = topBlock;
		Block stoneBlock = AbyssalCraft.dreadstone;
		byte bit = (byte)(field_150604_aj & 255);
		int numK = -1;
		final int seaLevel = 63;
		final int numL = (int)((d / 3.0D) + 3.0D + (rand.nextDouble() * 0.25D));
		final int posX = x & 15; // Up to 15
		final int posZ = z & 15; // Up to 15
		final int heightMult = blockArray.length / 256;

		for (int depth = 255; depth >= 0; --depth) {
			final int index = (posZ * 16 + posX) * heightMult + depth;

			if (depth <= 0 + rand.nextInt(5)) {
				blockArray[index] = Blocks.bedrock;
			} else {
				Block block = blockArray[index];

				if (block != null && block.getMaterial() != Material.air) {
					if (block == AbyssalCraft.dreadstone) {
						if (numK == -1) {
							if (numL <= 0) {
								surfaceBlock = null;
								bit = 0;
								stoneBlock = AbyssalCraft.dreadstone;
							} else if (depth >= 59 && depth <= 64) {
								surfaceBlock = topBlock;
								bit = (byte)(field_150604_aj & 255);
								stoneBlock = fillerBlock;
							}

							if (depth < seaLevel && (surfaceBlock == null || surfaceBlock.getMaterial() == Material.air)) {
								surfaceBlock = AbyssalCraft.dreadstone;
								bit = 0;
							}

							numK = numL;

							if (depth >= 62) {
								blockArray[index] = surfaceBlock;
								byteArray[index] = bit;
							} else if (depth < 56 - numL) {
								surfaceBlock = null;
								stoneBlock = AbyssalCraft.dreadstone;
								blockArray[index] = AbyssalCraft.dreadstone;
							} else {
								blockArray[index] = stoneBlock;
							}
						} else if (numK > 0) {
							--numK;
							blockArray[index] = stoneBlock;

							if (numK == 0 && stoneBlock == AbyssalCraft.dreadstone) {
								numK = rand.nextInt(4) + Math.max(0, depth - seaLevel);
								stoneBlock = AbyssalCraft.dreadstone;
							}
						}
					}
				} else {
					numK = -1;
				}
			}
		}
	}
}
