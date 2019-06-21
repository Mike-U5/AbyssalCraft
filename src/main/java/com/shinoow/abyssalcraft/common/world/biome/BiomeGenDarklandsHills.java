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
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenTrees;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.biome.IDarklandsBiome;
import com.shinoow.abyssalcraft.common.entity.EntityAbyssalZombie;
import com.shinoow.abyssalcraft.common.entity.EntityDepthsGhoul;
import com.shinoow.abyssalcraft.common.entity.EntityShadowBeast;
import com.shinoow.abyssalcraft.common.entity.EntityShadowCreature;
import com.shinoow.abyssalcraft.common.entity.EntityShadowMonster;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiPlayer;
import com.shinoow.abyssalcraft.common.world.gen.WorldGenDLT;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BiomeGenDarklandsHills extends BiomeGenBase implements IDarklandsBiome {

	private WorldGenTrees WorldGenDarkTrees;

	@SuppressWarnings("unchecked")
	public BiomeGenDarklandsHills(int par1)
	{
		super(par1);
		rootHeight = 1.1F;
		heightVariation = 0.5F;
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

	@Override
	public void decorate(World world, Random rand, int par3, int par4)
	{
		super.decorate(world, rand, par3, par4);

		if(AbyssalCraft.generateAbyssalniteOre)
			for (int rarity = 0; rarity < 6; ++rarity) {
				int veinSize = 1 + rand.nextInt(3);
				int x = par3 + rand.nextInt(16);
				int y = rand.nextInt(28) + 4;
				int z = par4 + rand.nextInt(16);

				new WorldGenMinable(AbyssalCraft.abyore, veinSize).generate(world, rand, x, y, z);
			}

		for (int rarity = 0; rarity < 7; ++rarity) {
			int x = par3 + rand.nextInt(16);
			int y = rand.nextInt(64);
			int z = par4 + rand.nextInt(16);
			new WorldGenMinable(AbyssalCraft.Darkstone, 64).generate(world, rand, x, y, z);
		}

		for (int rarity = 0; rarity < 7; ++rarity) {
			int x = par3 + rand.nextInt(16);
			int y = rand.nextInt(64);
			int z = par4 + rand.nextInt(16);
			new WorldGenMinable(AbyssalCraft.abydreadstone, 1).generate(world, rand, x, y, z);
		}

		for (int rarity = 0; rarity < 7; ++rarity) {
			int x = par3 + rand.nextInt(16);
			int y = rand.nextInt(64);
			int z = par4 + rand.nextInt(16);
			new WorldGenMinable(AbyssalCraft.Darkstone, 32).generate(world, rand, x, y, z);
		}
		
		//Coralium Ore
		for (int i = 0; i < 6; ++i) {
			int var7 = par3 + rand.nextInt(16);
			int var8 = rand.nextInt(28) + 4;
			int var9 = par4 + rand.nextInt(16);
			Block block = world.getBlock(var7, var8, var9);

			if (block != null && block.isReplaceableOreGen(world, var7, var8, var9, Blocks.stone) || block == Blocks.iron_ore || block == Blocks.coal_ore)
				world.setBlock(var7, var8, var9, AbyssalCraft.Coraliumore, 0, 2);
		}
		for(int rarity = 0; rarity < 9; rarity++) {
			int veinSize = 4;
			int x = par3 + rand.nextInt(16);
			int y = rand.nextInt(63);
			int z = par4 + rand.nextInt(16);

			new WorldGenMinable(AbyssalCraft.Coraliumore, veinSize).generate(world, rand, x, y, z);
		}
	}

	@Override
	public WorldGenAbstractTree func_150567_a(Random par1Random) {
		return par1Random.nextInt(5) == 0 ? worldGeneratorTrees : par1Random.nextInt(10) == 0 ? WorldGenDarkTrees : worldGeneratorTrees;
	}

	@Override
	@SideOnly(Side.CLIENT)

	public int getSkyColorByTemp(float par1) {
		return 0;
	}
}