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

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.common.entity.EntityAbyssalZombie;
import com.shinoow.abyssalcraft.common.entity.EntityDepthsGhoul;
import com.shinoow.abyssalcraft.common.entity.EntitySkeletonGoliath;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class BiomeGenAbywasteland extends BiomeGenBase {

	@SuppressWarnings("unchecked")
	public BiomeGenAbywasteland(int par1){
		super(par1);
		topBlock = AbyssalCraft.fusedabyssalsand;
		fillerBlock = AbyssalCraft.abyssalsand;
		waterColorMultiplier = 0x24FF83;
		spawnableMonsterList.clear();
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableCaveCreatureList.clear();
		spawnableMonsterList.add(new SpawnListEntry(EntityZombie.class, 50, 1, 4));
		spawnableMonsterList.add(new SpawnListEntry(EntitySkeleton.class, 50, 1, 4));
		spawnableMonsterList.add(new SpawnListEntry(EntityDepthsGhoul.class, 60, 1, 4));
		spawnableMonsterList.add(new SpawnListEntry(EntityAbyssalZombie.class, 60, 1, 4));
		spawnableMonsterList.add(new SpawnListEntry(EntitySkeletonGoliath.class, 15, 1, 1));
		///spawnableMonsterList.add(new SpawnListEntry(EntityDragonMinion.class, 1, 0, 1));
	}

	@Override
	public void decorate(World world, Random rand, int par3, int par4) {
		super.decorate(world, rand, par3, par4);

		if(AbyssalCraft.generateLiquifiedCoraliumOre)
			for (int rarity = 0; rarity < 8; rarity++){
				int veinSize = 1 + rand.nextInt(3);
				int x = par3 + rand.nextInt(16);
				int y = rand.nextInt(30);
				int z = par4 + rand.nextInt(16);

				new WorldGenMinable(AbyssalCraft.AbyLCorOre, veinSize, AbyssalCraft.abystone).generate(world, rand, x, y, z);
			}
		if(AbyssalCraft.generateAbyssalCoraliumOre)
			for(int rarity = 0; rarity < 12; rarity++) {
				int veinSize =  2 + rand.nextInt(6);
				int x = par3 + rand.nextInt(16);
				int y = rand.nextInt(75);
				int z = par4 + rand.nextInt(16);

				new WorldGenMinable(AbyssalCraft.AbyCorOre, veinSize, AbyssalCraft.abystone).generate(world, rand, x, y, z);
			}
		if(AbyssalCraft.generateAbyssalNitreOre)
			for(int rarity = 0; rarity < 8; rarity++) {
				int veinSize =  2 + rand.nextInt(6);
				int x = par3 + rand.nextInt(16);
				int y = rand.nextInt(60);
				int z = par4 + rand.nextInt(16);

				new WorldGenMinable(AbyssalCraft.AbyNitOre, veinSize, AbyssalCraft.abystone).generate(world, rand, x, y, z);
			}
		if(AbyssalCraft.generateAbyssalIronOre)
			for(int rarity = 0; rarity < 8; rarity++) {
				int veinSize = 2 + rand.nextInt(6);
				int x = par3 + rand.nextInt(16);
				int y = rand.nextInt(60);
				int z = par4 + rand.nextInt(16);

				new WorldGenMinable(AbyssalCraft.AbyIroOre, veinSize, AbyssalCraft.abystone).generate(world, rand, x, y, z);
			}
		if(AbyssalCraft.generateAbyssalCopperOre)
			for(int rarity = 0; rarity < 8; rarity++) {
				int veinSize = 2 + rand.nextInt(6);
				int x = par3 + rand.nextInt(16);
				int y = rand.nextInt(60);
				int z = par4 + rand.nextInt(16);

				new WorldGenMinable(AbyssalCraft.AbyCopOre, veinSize, AbyssalCraft.abystone).generate(world, rand, x, y, z);
			}
		if(AbyssalCraft.generateAbyssalGoldOre)
			for(int rarity = 0; rarity < 5; rarity++) {
				int veinSize = 2 + rand.nextInt(3);
				int x = par3 + rand.nextInt(16);
				int y = rand.nextInt(35);
				int z = par4 + rand.nextInt(16);

				new WorldGenMinable(AbyssalCraft.AbyGolOre, veinSize, AbyssalCraft.abystone).generate(world, rand, x, y, z);
			}
		if(AbyssalCraft.generateAbyssalDiamondOre)
			for(int rarity = 0; rarity < 5; rarity++) {
				int veinSize = 1 + rand.nextInt(7);
				int x = par3 + rand.nextInt(16);
				int y = rand.nextInt(20);
				int z = par4 + rand.nextInt(16);

				new WorldGenMinable(AbyssalCraft.AbyDiaOre, veinSize, AbyssalCraft.abystone).generate(world, rand, x, y, z);
			}
		if(AbyssalCraft.generatePearlescentCoraliumOre)
			for(int rarity = 0; rarity < 2; rarity++) {
				int veinSize = 1 + rand.nextInt(3);
				int x = par3 + rand.nextInt(16);
				int y = rand.nextInt(15);
				int z = par4 + rand.nextInt(16);

				new WorldGenMinable(AbyssalCraft.AbyPCorOre, veinSize, AbyssalCraft.abystone).generate(world, rand, x, y, z);
			}
		if(AbyssalCraft.generateAbyssalTinOre)
			for(int rarity = 0; rarity < 8; rarity++) {
				int veinSize = 2 + rand.nextInt(6);
				int x = par3 + rand.nextInt(16);
				int y = rand.nextInt(60);
				int z = par4 + rand.nextInt(16);

				new WorldGenMinable(AbyssalCraft.AbyTinOre, veinSize, AbyssalCraft.abystone).generate(world, rand, x, y, z);
			}
	}
	
	//@Override
	public void genTerrainBlocks(World world, Random rand, Block[] blockArray, byte[] byteArray, int x, int z, double d) {
		generateAbyssalWastelandTerrain(world, rand, blockArray, byteArray, x, z, d);
	}
	
	public final void generateAbyssalWastelandTerrain(World world, Random rand, Block[] blockArray, byte[] byteArray, int x, int z, double d) {
		Block surfaceBlock = topBlock;
		Block stoneBlock = fillerBlock;
		int numK = -1;
		final int seaLevel = 63;
		final int numL = (int)((d / 3.0D) + 3.0D + (rand.nextDouble() * 0.25D));
		final int posZ = x & 15;
		final int posX = z & 15;
		final int heightMult = blockArray.length / 256;

		for (int posY = 255; posY >= 0; --posY) {
			final int index = (posZ * 16 + posX) * heightMult + posY;
		
			if (posY <= rand.nextInt(5)) {
				blockArray[index] = Blocks.bedrock;
			} else {
				Block block = blockArray[index];

				if (block != null && block.getMaterial() == Material.air) {
					numK = -1;
				} else if (block == ACBlocks.abyssal_stone) {
					if (numK == -1) {
						if (numL <= 0) {
							surfaceBlock = null;
							stoneBlock = ACBlocks.abyssal_stone;
						} else if (posY >= seaLevel - 4 && posY <= seaLevel + 1) {
							surfaceBlock = topBlock;
							stoneBlock = fillerBlock;
						}

						numK = numL;

						if (posY >= 62) {
							
							blockArray[index] = surfaceBlock;
						} else if (posY < 56 - numL) {
							surfaceBlock = null;
							stoneBlock = ACBlocks.abyssal_stone;
							blockArray[index] = stoneBlock;
						} else {
							blockArray[index] = stoneBlock;
						}
					} else if (numK > 0) {
						--numK;
						blockArray[index] = stoneBlock;
					}
				}
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getSkyColorByTemp(float par1) {
		return 0;
	}
}