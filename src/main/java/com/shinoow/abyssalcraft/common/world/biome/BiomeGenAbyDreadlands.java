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
import com.shinoow.abyssalcraft.common.entity.EntityAbygolem;
import com.shinoow.abyssalcraft.common.entity.EntityDreadguard;
import com.shinoow.abyssalcraft.common.world.gen.WorldGenDreadlandsStalagmite;

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class BiomeGenAbyDreadlands extends BiomeGenDreadlandsBase {

	@SuppressWarnings("unchecked")
	public BiomeGenAbyDreadlands(int par1) {
		super(par1);
		topBlock = AbyssalCraft.abydreadstone;
		fillerBlock = AbyssalCraft.abydreadstone;
		spawnableCreatureList.add(new SpawnListEntry(EntityAbygolem.class, 100, 3, 8));
		spawnableMonsterList.add(new SpawnListEntry(EntityDreadguard.class, 1, 1, 1));
	}

	@Override
	public void decorate(World par1World, Random par2Random, int par3, int par4)
	{
		super.decorate(par1World, par2Random, par3, par4);

		if(AbyssalCraft.generateDreadlandsAbyssalniteOre)
			for(int rarity = 0; rarity < 10; rarity++) {
				int veinSize =  8 + par2Random.nextInt(12);
				int x = par3 + par2Random.nextInt(16);
				int y = par2Random.nextInt(60);
				int z = par4 + par2Random.nextInt(16);

				new WorldGenMinable(AbyssalCraft.magicdreadstone, veinSize, AbyssalCraft.dreadstone).generate(par1World, par2Random, x, y, z);
			}
		for (int rarity = 0; rarity < 7; ++rarity)
		{
			int x = par3 + par2Random.nextInt(16);
			int y = par2Random.nextInt(64);
			int z = par4 + par2Random.nextInt(16);
			new WorldGenMinable(AbyssalCraft.abydreadstone, 64,
					AbyssalCraft.dreadstone).generate(par1World, par2Random, x, y, z);
		}
		for (int rarity = 0; rarity < 7; ++rarity)
		{
			int x = par3 + par2Random.nextInt(16);
			int y = par2Random.nextInt(64);
			int z = par4 + par2Random.nextInt(16);
			new WorldGenMinable(AbyssalCraft.abydreadstone, 48,
					AbyssalCraft.dreadstone).generate(par1World, par2Random, x, y, z);
		}
		for (int rarity = 0; rarity < 7; ++rarity)
		{
			int x = par3 + par2Random.nextInt(16);
			int y = par2Random.nextInt(64);
			int z = par4 + par2Random.nextInt(16);
			new WorldGenMinable(AbyssalCraft.abydreadstone, 32,
					AbyssalCraft.dreadstone).generate(par1World, par2Random, x, y, z);
		}
		if(AbyssalCraft.generateDreadlandsAbyssalniteOre) {
			for(int rarity = 0; rarity < 8; rarity++) {
				int veinSize =  2 + par2Random.nextInt(4);
				int x = par3 + par2Random.nextInt(16);
				int y = par2Random.nextInt(55);
				int z = par4 + par2Random.nextInt(16);

				new WorldGenMinable(AbyssalCraft.magicdreadstone, veinSize, AbyssalCraft.abydreadstone).generate(par1World, par2Random, x, y, z);
			}
		}
		if(AbyssalCraft.generateDreadlandsStalagmite)
			for(int i = 0; i < 1; i++){
				int xPos = par3 + par2Random.nextInt(16) + 8;
				int zPos = par4 + par2Random.nextInt(16) + 8;
				new WorldGenDreadlandsStalagmite().generate(par1World, par2Random, xPos, par1World.getHeightValue(xPos, zPos), zPos);
			}
	}
}
