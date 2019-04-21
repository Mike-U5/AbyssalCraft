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
import com.shinoow.abyssalcraft.common.blocks.tile.TEDirectional;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenShoggothMonolith extends WorldGenerator {

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z) {

		while(world.isAirBlock(x, y, z) && y > 2)
			--y;

		if(world.getBlock(x, y, z) != AbyssalCraft.shoggothBlock)
			return false;
		else {
			int max = rand.nextInt(8) + 5;
			for(int i = 0; i < max; i++){
				setBlockAndNotifyAdequately(world, x, y + i, z, AbyssalCraft.monolithStone, 0);
				setBlockAndNotifyAdequately(world, x + 1, y + i, z, AbyssalCraft.monolithStone, 0);
				setBlockAndNotifyAdequately(world, x - 1, y + i, z, AbyssalCraft.monolithStone, 0);
				setBlockAndNotifyAdequately(world, x, y + i, z + 1, AbyssalCraft.monolithStone, 0);
				setBlockAndNotifyAdequately(world, x, y + i, z - 1, AbyssalCraft.monolithStone, 0);
				setBlockAndNotifyAdequately(world, x + 1, y + i, z + 1, AbyssalCraft.monolithStone, 0);
				setBlockAndNotifyAdequately(world, x - 1, y + i, z - 1, AbyssalCraft.monolithStone, 0);
				setBlockAndNotifyAdequately(world, x + 1, y + i, z - 1, AbyssalCraft.monolithStone, 0);
				setBlockAndNotifyAdequately(world, x - 1, y + i, z + 1, AbyssalCraft.monolithStone, 0);
			}
			setBlockAndNotifyAdequately(world, x, y, z, AbyssalCraft.shoggothBiomass, 0);
			setBlockAndNotifyAdequately(world, x, y + max, z, getStatue(rand), 0);
			TileEntity te = world.getTileEntity(x, y + max, z);

			if(te != null && te instanceof TEDirectional)
				((TEDirectional) te).setDirection(rand.nextInt(3));

			world.playSoundEffect(x, y, z, "random.anvil_use", 2, world.rand.nextFloat() * 0.1F * 0.9F);

			return true;
		}
	}

	private Block getStatue(Random rand){
		switch(rand.nextInt(7)){
		case 0:
			return AbyssalCraft.cthulhuStatue;
		case 1:
			return AbyssalCraft.hasturStatue;
		case 2:
			return AbyssalCraft.jzaharStatue;
		case 3:
			return AbyssalCraft.azathothStatue;
		case 4:
			return AbyssalCraft.nyarlathotepStatue;
		case 5:
			return AbyssalCraft.yogsothothStatue;
		case 6:
			return AbyssalCraft.shubniggurathStatue;
		default:
			return getStatue(rand);
		}
	}
}
