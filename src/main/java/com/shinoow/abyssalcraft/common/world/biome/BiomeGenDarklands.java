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
import com.shinoow.abyssalcraft.api.biome.IDarklandsBiome;
import com.shinoow.abyssalcraft.common.entity.EntityAbyssalZombie;
import com.shinoow.abyssalcraft.common.entity.EntityDepthsGhoul;
import com.shinoow.abyssalcraft.common.entity.EntityShadowBeast;
import com.shinoow.abyssalcraft.common.entity.EntityShadowCreature;
import com.shinoow.abyssalcraft.common.entity.EntityShadowMonster;
import com.shinoow.abyssalcraft.common.entity.anti.EntityDaoloth;
import com.shinoow.abyssalcraft.common.world.gen.WorldGenDLT;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenTrees;

public class BiomeGenDarklands extends BiomeGenBase implements IDarklandsBiome {

	protected WorldGenTrees WorldGenDarkTrees;

	@SuppressWarnings("unchecked")
	public BiomeGenDarklands(int num) {
		super(num);
		topBlock = AbyssalCraft.Darkgrass;
		fillerBlock = Blocks.dirt;
		waterColorMultiplier = 14745518;
		WorldGenDarkTrees = new WorldGenDLT(false);
		theBiomeDecorator.treesPerChunk = 10;
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableMonsterList.clear();
		spawnableMonsterList.add(new SpawnListEntry(EntityDepthsGhoul.class, 55, 1, 5));
		spawnableMonsterList.add(new SpawnListEntry(EntityAbyssalZombie.class, 55, 1, 3));
		spawnableMonsterList.add(new SpawnListEntry(EntityDaoloth.class, 15, 1, 1));
		spawnableMonsterList.add(new SpawnListEntry(EntityShadowCreature.class, 35, 1, 1));
		spawnableMonsterList.add(new SpawnListEntry(EntityShadowMonster.class, 25, 1, 1));
		spawnableMonsterList.add(new SpawnListEntry(EntityShadowBeast.class, 15, 1, 1));
	}
	
	@Override
	public void decorate(World world, Random rand, int baseX, int baseZ) {
		super.decorate(world, rand, baseX, baseZ);

		// Darkstone
		for (int i = 0; i < 7; i++) {
			int x = baseX + rand.nextInt(16);
			int y = rand.nextInt(64);
			int z = baseZ + rand.nextInt(16);
			new WorldGenMinable(AbyssalCraft.Darkstone, 20).generate(world, rand, x, y, z);
		}

		//Coralium Ore
		for (int i = 0; i < 6; i++) {
			int x = baseX + rand.nextInt(16);
			int y = rand.nextInt(28) + 4;
			int z = baseZ + rand.nextInt(16);
			Block block = world.getBlock(x, y, z);

			if (block != null && block.isReplaceableOreGen(world, x, y, z, Blocks.stone) || block == Blocks.stone || block == Blocks.iron_ore || block == Blocks.coal_ore || block == AbyssalCraft.Darkstone) {
				world.setBlock(x, y, z, AbyssalCraft.Coraliumore, 0, 2);
			}
		}
		
		for(int i = 0; i < 9; i++) {
			int veinSize = 4;
			int x = baseX + rand.nextInt(16);
			int y = rand.nextInt(63);
			int z = baseZ + rand.nextInt(16);

			new WorldGenMinable(AbyssalCraft.Coraliumore, veinSize).generate(world, rand, x, y, z);
		}
	}

	@Override
	public WorldGenAbstractTree func_150567_a(Random rand) {
		return WorldGenDarkTrees;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getSkyColorByTemp(float par1) {
		return 0;
	}
}
