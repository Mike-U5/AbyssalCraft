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
package com.shinoow.abyssalcraft.common.disruptions;

import java.util.List;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.energy.disruption.DisruptionEntry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class DisruptionOoze extends DisruptionEntry {
	
	private final int oozeRange = 6; // In all directions

	public DisruptionOoze() {
		super("ooze", null);
	}
	
	private void deepOoze(World world, int x, int y, int z) {
		for(int iY = 0; iY <= oozeRange*8; iY++) {
			if(AbyssalCraft.shoggothBlock.canPlaceBlockAt(world, x, y - iY, z)) {
				world.setBlock(x, y - iY, z, ACBlocks.shoggoth_ooze);
				return;
			}
		}
	}

	@Override
	public void disrupt(World world, int x, int y, int z, List<EntityPlayer> players) {
		if(world.isRemote) {
			return;
		}

		final int oX = x + oozeRange;
		final int oY = y + (oozeRange*4);
		final int oZ = z + oozeRange;
		
		for(int iX = 0; iX <= oozeRange*2; iX++) {
			for(int iZ = 0; iZ <= oozeRange*2; iZ++) {
				int dist = (Math.abs(oozeRange - iX) + Math.abs(oozeRange - iZ));
				if (world.rand.nextInt(dist + 1) == 0) {
					deepOoze(world, oX - iX, oY, oZ - iZ);
				}
			}
		}
	}
}