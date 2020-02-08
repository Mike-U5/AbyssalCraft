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

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.biome.IDarklandsBiome;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

public class NecronomiconPurificationRitual extends NecronomiconRitual {

	public NecronomiconPurificationRitual() {
		super("purification", 4, 10000F, new Object[] {
			Item.getItemFromBlock(AbyssalCraft.relic), new ItemStack(Items.dye, 1, 15), 
			Item.getItemFromBlock(AbyssalCraft.relic), new ItemStack(Items.dye, 1, 15), 
			Item.getItemFromBlock(AbyssalCraft.relic), new ItemStack(Items.dye, 1, 15), 
			Item.getItemFromBlock(AbyssalCraft.relic), new ItemStack(Items.dye, 1, 15)
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
			int range = 48;
			byte biomeID = (byte)1;
			
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

                    byteArray[moduZ * 16 + moduX] = biomeID;
                    chunk.setBiomeArray(byteArray);
                    
                    // Change the blocks
                    transformBlocks(world, curX, curZ);
                }
            }
		}
	}
	
	private void transformBlocks(World world, int coordX, int coordZ) {
		for(int iY = 1; iY <= 160; iY++) {
			// Block info
			Block block = world.getBlock(coordX, iY, coordZ);
			int meta = block.getDamageValue(world, coordX, iY, coordZ);
			
			// Transmute Stone
			if (block == AbyssalCraft.Darkstone || block == AbyssalCraft.Darkstoneslab2) {
				block = Blocks.stone; 
			} else if (block == AbyssalCraft.Darkstone_cobble || block == AbyssalCraft.Darkcobbleslab2) {
				block = Blocks.cobblestone; 
			} else if (block == AbyssalCraft.Darkstone_brick) {
				block = Blocks.stonebrick; 
			} else if (block == AbyssalCraft.Darkcobbleslab1) {
				block = Blocks.stone_slab; 
				meta = 3;
			} else if (block == AbyssalCraft.Darkstoneslab1) {
				block = Blocks.stone_slab; 
				meta = 0;
			} else if (block == AbyssalCraft.Darkbrickslab1) {
				block = Blocks.stone_slab; 
				meta = 5;
			} else if (block == AbyssalCraft.DBstairs) {
				block = Blocks.stone_brick_stairs; 
			} else if (block == AbyssalCraft.DCstairs) {
				block = Blocks.stone_stairs; 
			} else if (block == AbyssalCraft.ritualpedestal && meta == 1) {
				meta = 0;
			} else if (block == AbyssalCraft.ritualaltar && meta == 1) {
				meta = 0;
			// Transmute Plants
			} else if (block == AbyssalCraft.Darkgrass) {
				block = Blocks.grass; 
			} else if (block == AbyssalCraft.DLTLeaves) {
				block = Blocks.leaves;
			} else if (block == AbyssalCraft.DLTSapling) {
				block = Blocks.sapling;
				meta = 0;
			// Transmute Wood
			} else if (block == AbyssalCraft.DLTLog) {
				block = Blocks.log; 
			} else if (block == AbyssalCraft.DLTplank || block == AbyssalCraft.DLTslab2) {
				block = Blocks.planks; 
				meta = 0;
			} else if (block == AbyssalCraft.DLTpplate) {
				block = Blocks.wooden_pressure_plate; 
			} else if (block == AbyssalCraft.DLTslab1) {
				block = Blocks.wooden_slab; 
				meta  = 0;
			} else if (block == AbyssalCraft.DLTstairs) {
				block = Blocks.oak_stairs; 
			} else if (block == AbyssalCraft.DLTbutton) {
				block = Blocks.wooden_button; 
			} else if (block == AbyssalCraft.DLTfence) {
				block = Blocks.fence; 
			// Misc
			} else if (block == AbyssalCraft.DSGlow) {
				block = AbyssalCraft.SGlow; 
			} else if (block == AbyssalCraft.shoggothBlock) {
				block = Blocks.dirt; 
			} else {
				continue;
			}

			//Transform Blocks and/or metadata
			world.setBlock(coordX, iY, coordZ, block, 0, 3);
			world.setBlockMetadataWithNotify(coordX, iY, coordZ, meta, 2);
		}
	}

	@Override
	protected void completeRitualClient(World world, int x, int y, int z, EntityPlayer player){}
}
