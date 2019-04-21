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
package com.shinoow.abyssalcraft.common.world.gen;

import java.util.Random;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.biome.ACBiomes;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenDreadlandsStalagmite extends WorldGenerator {
	
	private boolean purified = false;
	private boolean infusedCanGen = true;

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z) {

		while(world.isAirBlock(x, y, z))
			--y;

		if(world.getBlock(x, y, z) != AbyssalCraft.abydreadstone &&
				world.getBlock(x, y, z) != AbyssalCraft.dreadstone)
			return false;
		
		if(world.getBiomeGenForCoords(x, z) == ACBiomes.purified_dreadlands) {
			this.purified = true;
		}
		
		for(int i = 0; i < 7 + rand.nextInt(5); i++)
			world.setBlock(x, y + i, z, rollStalagBlock(rand));
		for(int i = 0; i < 5 + rand.nextInt(5); i++)
			world.setBlock(x + 1, y + i, z, rollStalagBlock(rand));
		for(int i = 0; i < 5 + rand.nextInt(5); i++)
			world.setBlock(x - 1, y + i, z, rollStalagBlock(rand));
		for(int i = 0; i < 5 + rand.nextInt(5); i++)
			world.setBlock(x, y + i, z + 1, rollStalagBlock(rand));
		for(int i = 0; i < 5 + rand.nextInt(5); i++)
			world.setBlock(x, y + i, z - 1, rollStalagBlock(rand));
		for(int i = 0; i < 3 + rand.nextInt(5); i++)
			world.setBlock(x + 1, y + i, z + 1, rollStalagBlock(rand));
		for(int i = 0; i < 3 + rand.nextInt(5); i++)
			world.setBlock(x - 1, y + i, z - 1, rollStalagBlock(rand));
		for(int i = 0; i < 3 + rand.nextInt(5); i++)
			world.setBlock(x - 1, y + i, z + 1, rollStalagBlock(rand));
		for(int i = 0; i < 3 + rand.nextInt(5); i++)
			world.setBlock(x + 1, y + i, z - 1, rollStalagBlock(rand));

		return true;
	}
	
	private Block rollStalagBlock(Random rand) {
		if(this.purified) {
			return AbyssalCraft.abydreadstone;
		} else if (this.infusedCanGen && rand.nextInt(200) == 0) {
			this.infusedCanGen = false;
			return AbyssalCraft.magicdreadstone;
		} else {
			return AbyssalCraft.dreadstone;
		}
	}
}
