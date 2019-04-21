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
package com.shinoow.abyssalcraft.common.ritual;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.World;
import net.minecraft.item.*;

import com.google.common.collect.Lists;
import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.biome.IDarklandsBiome;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;
import com.shinoow.abyssalcraft.common.entity.EntityLesserShoggoth;

public class NecronomiconPurificationRitual extends NecronomiconRitual {

	private boolean shoggothInfestation;

	public NecronomiconPurificationRitual() {
		super("purification", 0, 1000F, new Object[]{
			Item.getItemFromBlock(AbyssalCraft.ODBcore), Item.getItemFromBlock(AbyssalCraft.ODBcore), 
			Item.getItemFromBlock(AbyssalCraft.ODBcore), Item.getItemFromBlock(AbyssalCraft.ODBcore), 
			Item.getItemFromBlock(AbyssalCraft.ODBcore), Item.getItemFromBlock(AbyssalCraft.ODBcore), 
			Item.getItemFromBlock(AbyssalCraft.ODBcore), Item.getItemFromBlock(AbyssalCraft.ODBcore)
		});
	}

	@Override
	public boolean canCompleteRitual(World world, int x, int y, int z, EntityPlayer player) {
		if (!world.isRemote) {
			BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
			if (biome instanceof IDarklandsBiome) {
				world.playSoundEffect(x, y, z, "abyssalcraft:event.sin", 1.0F, 1.0F);
				return true;
			}
		}
		return false;
	}

	@Override
	protected void completeRitualServer(World world, int x, int y, int z, EntityPlayer player){
		if (!world.isRemote) {
			int range = 24;
			int biomeID = 1;
			
			for (int iX = 0; iX < (2 * range) + 1; iX++) {
                for (int iZ = 0; iZ < (2 * range) + 1; iZ++) {
                	int curX = x - range + iX;
                	int curZ = z - range + iZ;
                	// Check if this block is actually a Darklands Biome
                	BiomeGenBase biome = world.getBiomeGenForCoords(curX, curZ);
        			if (!(biome instanceof IDarklandsBiome)) {
        				continue;
        			}
                	// Change the biome
                    Chunk chunk = world.getChunkFromBlockCoords(curX, curZ);
                    byte[] byteArray = chunk.getBiomeArray();
                    int moduX = (curX) % 16;
                    int moduZ = (curZ) % 16;

                    if (moduX < 0) {
                        moduX = moduX + 16;
                    }

                    if (moduZ < 0) {
                        moduZ = moduZ + 16;
                    }

                    byteArray[moduZ * 16 + moduX] = (byte) biomeID;
                    chunk.setBiomeArray(byteArray);
                    
                    // Change the blocks
                    transformBlocks(world, curX, curZ);
                }
            }
		}
	}
	
	private void transformBlocks(World world, int coordX, int coordZ) {
		for(int iY = 1; iY <= 128; iY++){
			Block block = world.getBlock(coordX, iY, coordZ);
			//Transmute
			boolean transmute = false;
			if (block == AbyssalCraft.Darkstone) {
				block = Blocks.stone; 
				transmute = true;
			} else if (block == AbyssalCraft.Darkstone_cobble) {
				block = Blocks.cobblestone; 
				transmute = true;
			} else if (block == AbyssalCraft.Darkstone_brick) {
				block = Blocks.stonebrick; 
				transmute = true;
			} else if (block == AbyssalCraft.Darkgrass) {
				block = Blocks.grass; 
				transmute = true;
			} else if (block == AbyssalCraft.DLTLeaves) {
				block = Blocks.leaves; 
				transmute = true;
			} else if (block == AbyssalCraft.DLTLog) {
				block = Blocks.log; 
				transmute = true;
			}

			//Set
			if (transmute) {
				world.setBlock(coordX, iY, coordZ, block, 0, 3);
			}
		}
	}

	@Override
	protected void completeRitualClient(World world, int x, int y, int z, EntityPlayer player){}
}
