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
package com.shinoow.abyssalcraft.common.structures.abyss;

import static net.minecraftforge.common.ChestGenHooks.DUNGEON_CHEST;
import static net.minecraftforge.common.ChestGenHooks.STRONGHOLD_CORRIDOR;

import java.util.Random;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.structures.abyss.stronghold.StructureAbyStrongholdPieces.ChestCorridor;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.ChestGenHooks;

public class Abyruin extends WorldGenerator
{
	protected Block[] GetValidSpawnBlocks() {
		return new Block[] {
			AbyssalCraft.abystone, AbyssalCraft.abyssalsand, AbyssalCraft.fusedabyssalsand
		};
	}

	public boolean LocationIsValidSpawn(World world, int i, int j, int k){
		int distanceToAir = 0;
		int checkID = world.getBlockMetadata(i, j, k);

		while (checkID != 0){
			distanceToAir++;
			checkID = world.getBlockMetadata(i, j + distanceToAir, k);
		}

		if (distanceToAir > 3)
			return false;
		j += distanceToAir - 1;

		Block blockID = world.getBlock(i, j, k);
		Block blockIDAbove = world.getBlock(i, j+1, k);
		Block blockIDBelow = world.getBlock(i, j-1, k);
		for (Block x : GetValidSpawnBlocks()){
			if (blockIDAbove != Blocks.air)
				return false;
			if (blockID == x)
				return true;
			else if (blockID == Blocks.snow && blockIDBelow == x)
				return true;
		}
		return false;
	}

	public Abyruin() { }

	@Override
	public boolean generate(World world, Random rand, int i, int j, int k) {
		//check that each corner is one of the valid spawn blocks
		if(!LocationIsValidSpawn(world, i, j, k) || !LocationIsValidSpawn(world, i + 4, j, k) || !LocationIsValidSpawn(world, i + 4, j, k + 8) || !LocationIsValidSpawn(world, i, j, k + 8)) {
			return false;
		}

		world.setBlock(i - 4, j, k - 6, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 4, j, k - 5, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 4, j, k - 4, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 4, j, k - 3, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 4, j, k - 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 4, j, k - 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 4, j, k, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 4, j, k + 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 4, j, k + 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 4, j + 1, k - 6, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 4, j + 1, k - 5, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 4, j + 1, k - 4, AbyssalCraft.cstonebrickfence, 0, 0);
		world.setBlock(i - 4, j + 1, k - 3, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 4, j + 1, k - 2, AbyssalCraft.cstonebrickfence, 0, 0);
		world.setBlock(i - 4, j + 1, k - 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 4, j + 1, k, AbyssalCraft.cstonebrickfence, 0, 0);
		world.setBlock(i - 4, j + 1, k + 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 4, j + 1, k + 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 4, j + 2, k - 6, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 4, j + 2, k - 5, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 4, j + 2, k - 4, AbyssalCraft.cstonebrickfence, 0, 0);
		world.setBlock(i - 4, j + 2, k - 3, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 4, j + 2, k - 2, AbyssalCraft.cstonebrickfence, 0, 0);
		world.setBlock(i - 4, j + 2, k - 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 4, j + 2, k, AbyssalCraft.cstonebrickfence, 0, 0);
		world.setBlock(i - 4, j + 2, k + 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 4, j + 2, k + 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 4, j + 3, k - 6, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 4, j + 3, k - 5, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 4, j + 3, k - 4, AbyssalCraft.cstonebrickfence, 0, 0);
		world.setBlock(i - 4, j + 3, k - 3, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 4, j + 3, k - 2, AbyssalCraft.cstonebrickfence, 0, 0);
		world.setBlock(i - 4, j + 3, k - 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 4, j + 3, k, AbyssalCraft.cstonebrickfence, 0, 0);
		world.setBlock(i - 4, j + 4, k - 6, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 4, j + 4, k - 3, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 4, j + 4, k - 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 4, j + 4, k - 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 4, j + 5, k - 6, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 4, j + 5, k - 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 3, j, k - 6, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 3, j, k - 5, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 3, j, k - 4, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 3, j, k - 3, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 3, j, k - 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 3, j, k - 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 3, j, k, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 3, j, k + 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 3, j, k + 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 3, j + 1, k - 6, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 3, j + 1, k + 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 3, j + 2, k - 6, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 3, j + 2, k + 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 3, j + 3, k - 6, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 3, j + 3, k + 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 3, j + 4, k - 6, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 3, j + 5, k - 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 3, j + 5, k - 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 2, j - 7, k - 3, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 2, j - 7, k - 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 2, j - 7, k - 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 2, j - 7, k, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 2, j - 6, k - 3, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 2, j - 6, k - 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 2, j - 6, k - 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 2, j - 6, k, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 2, j - 5, k - 3, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 2, j - 5, k - 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 2, j - 5, k - 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 2, j - 5, k, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 2, j, k - 6, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 2, j, k - 5, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 2, j, k - 4, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 2, j, k - 3, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 2, j, k - 2, AbyssalCraft.abystone, 0, 0);
		world.setBlock(i - 2, j, k - 1, AbyssalCraft.abystone, 0, 0);
		world.setBlock(i - 2, j, k, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 2, j, k + 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 2, j, k + 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 2, j + 1, k - 6, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 2, j + 1, k - 3, AbyssalCraft.cstonebrickfence, 0, 0);
		world.setBlock(i - 2, j + 1, k, AbyssalCraft.cstonebrickfence, 0, 0);
		world.setBlock(i - 2, j + 1, k + 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 2, j + 2, k - 6, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 2, j + 2, k + 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 2, j + 3, k - 6, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 2, j + 3, k + 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 2, j + 4, k - 6, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 2, j + 4, k + 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 2, j + 5, k - 6, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 2, j + 5, k - 5, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 2, j + 5, k - 4, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 2, j + 5, k - 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 2, j + 5, k + 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 1, j - 8, k - 3, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 1, j - 8, k - 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 1, j - 8, k - 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 1, j - 8, k, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 1, j - 7, k - 4, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 1, j - 7, k + 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 1, j - 6, k - 4, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 1, j - 6, k + 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 1, j - 5, k - 4, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 1, j - 5, k + 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 1, j - 4, k - 3, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 1, j - 4, k - 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 1, j - 4, k - 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 1, j - 4, k, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 1, j - 4, k + 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 1, j - 3, k - 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 1, j - 3, k, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 1, j - 3, k + 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 1, j - 2, k - 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 1, j - 2, k, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 1, j - 2, k + 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 1, j - 1, k - 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 1, j - 1, k, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 1, j - 1, k + 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 1, j, k - 6, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 1, j, k - 5, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 1, j, k - 4, AbyssalCraft.abystone, 0, 0);
		world.setBlock(i - 1, j, k - 3, AbyssalCraft.abystone, 0, 0);
		world.setBlock(i - 1, j, k - 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 1, j, k - 1, AbyssalCraft.abystone, 0, 0);
		world.setBlock(i - 1, j, k, AbyssalCraft.abystone, 0, 0);
		world.setBlock(i - 1, j, k + 1, AbyssalCraft.abystone, 0, 0);
		world.setBlock(i - 1, j, k + 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 1, j + 1, k + 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 1, j + 2, k + 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 1, j + 3, k + 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 1, j + 4, k - 6, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 1, j + 4, k + 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 1, j + 5, k - 5, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 1, j + 5, k - 4, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 1, j + 5, k - 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 1, j + 5, k, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 1, j + 5, k + 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i, j - 8, k - 3, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i, j - 8, k - 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i, j - 8, k - 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i, j - 8, k, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i, j - 7, k - 4, AbyssalCraft.cstonebrick, 0, 0);
		this.setBasementFurniture(world, i, j, k);
		/*final int rollA = rand.nextInt(50);
		if (rollA == 0) {
			world.setBlock(i, j - 7, k - 3, Blocks.anvil, 8, 0);
		} else if (rollA == 1) {
			world.setBlock(i, j - 7, k - 3, Blocks.brewing_stand, 0, 0);
		} else if (rollA == 2) {
			world.setBlock(i, j - 7, k - 3, Blocks.cauldron, 0, 0);
		} else if (rollA == 3) {
			world.setBlock(i, j - 7, k - 3, Blocks.flower_pot, 0, 0);
		} else if (rollA == 4) {
			world.setBlock(i, j - 7, k - 3, Blocks.hay_block, 0, 0);
		} else if (rollA == 5) {
			world.setBlock(i, j - 7, k - 3, Blocks.furnace, 0, 0);
		} else if (rollA == 6) {
			world.setBlock(i, j - 7, k - 3, AbyssalCraft.ODBcore, 0, 0);
		} else {
			world.setBlock(i, j - 7, k - 3, Blocks.chest, 0, 0);
			TileEntityChest tileentitychest = (TileEntityChest)world.getTileEntity(i, j - 7, k - 3);
			if(tileentitychest != null) {
				WeightedRandomChestContent.generateChestContents(rand, ChestCorridor.strongholdChestContents, tileentitychest, ChestGenHooks.getCount(STRONGHOLD_CORRIDOR, rand));
			}
		}*/
		world.setBlock(i, j - 7, k + 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i, j - 6, k - 4, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i, j - 6, k + 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i, j - 5, k - 4, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i, j - 5, k + 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i, j - 4, k - 3, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i, j - 4, k - 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i, j - 4, k - 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i, j - 4, k + 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i, j - 3, k - 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i, j - 3, k + 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i, j - 2, k - 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i, j - 2, k + 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i, j - 1, k - 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i, j - 1, k + 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i, j, k - 6, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i, j, k - 5, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i, j, k - 4, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i, j, k - 3, AbyssalCraft.abystone, 0, 0);
		world.setBlock(i, j, k - 2, AbyssalCraft.abystone, 0, 0);
		world.setBlock(i, j, k - 1, AbyssalCraft.abystone, 0, 0);
		world.setBlock(i, j, k, AbyssalCraft.cstone, 0, 0);
		world.setBlock(i, j, k + 1, AbyssalCraft.abystone, 0, 0);
		world.setBlock(i, j, k + 2, AbyssalCraft.cstonebrick, 0, 0);
		int rollB = rand.nextInt(5);
		if (rollB == 0) {
			world.setBlock(i, j + 1, k + 1, Blocks.crafting_table, 0, 0);
		} else if (rollB == 1) {
			world.setBlock(i, j + 1, k + 1, Blocks.air, 0, 0);
		} else {
			world.setBlock(i, j + 1, k + 1, Blocks.chest, 3, 0);
			world.setBlockMetadataWithNotify(i, j + 1, k + 1, 1, 1);
			TileEntityChest tileentitychest2 = (TileEntityChest)world.getTileEntity(i, j + 1, k + 1);
			if(tileentitychest2 != null) {
				WeightedRandomChestContent.generateChestContents(rand, ChestGenHooks.getItems(DUNGEON_CHEST, rand), tileentitychest2, ChestGenHooks.getCount(DUNGEON_CHEST, rand));
			}
		}
		world.setBlock(i, j + 1, k + 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i, j + 2, k + 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i, j + 3, k + 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i, j + 4, k - 6, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i, j + 4, k + 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i, j + 5, k - 6, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i, j + 5, k - 4, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i, j + 5, k - 3, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i, j + 5, k - 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i, j + 5, k + 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 1, j - 8, k - 3, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 1, j - 8, k - 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 1, j - 8, k - 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 1, j - 8, k, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 1, j - 7, k - 4, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 1, j - 7, k + 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 1, j - 6, k - 4, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 1, j - 6, k + 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 1, j - 5, k - 4, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 1, j - 5, k + 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 1, j - 4, k - 3, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 1, j - 4, k - 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 1, j - 4, k - 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 1, j - 4, k, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 1, j - 4, k + 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 1, j - 3, k - 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 1, j - 3, k, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 1, j - 3, k + 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 1, j - 2, k - 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 1, j - 2, k, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 1, j - 2, k + 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 1, j - 1, k - 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 1, j - 1, k, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 1, j - 1, k + 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 1, j, k - 6, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 1, j, k - 5, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 1, j, k - 4, AbyssalCraft.abystone, 0, 0);
		world.setBlock(i + 1, j, k - 3, AbyssalCraft.abystone, 0, 0);
		world.setBlock(i + 1, j, k - 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 1, j, k - 1, AbyssalCraft.abystone, 0, 0);
		world.setBlock(i + 1, j, k, AbyssalCraft.abystone, 0, 0);
		world.setBlock(i + 1, j, k + 1, AbyssalCraft.abystone, 0, 0);
		world.setBlock(i + 1, j, k + 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 1, j + 1, k + 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 1, j + 2, k + 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 1, j + 3, k + 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 1, j + 4, k - 6, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 1, j + 4, k + 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 1, j + 5, k - 5, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 1, j + 5, k - 4, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 1, j + 5, k - 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 1, j + 5, k - 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 1, j + 5, k, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 1, j + 5, k + 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 2, j - 7, k - 3, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 2, j - 7, k - 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 2, j - 7, k - 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 2, j - 7, k, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 2, j - 6, k - 3, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 2, j - 6, k - 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 2, j - 6, k - 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 2, j - 6, k, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 2, j - 5, k - 3, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 2, j - 5, k - 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 2, j - 5, k - 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 2, j - 5, k, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 2, j, k - 6, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 2, j, k - 5, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 2, j, k - 4, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 2, j, k - 3, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 2, j, k - 2, AbyssalCraft.abystone, 0, 0);
		world.setBlock(i + 2, j, k - 1, AbyssalCraft.abystone, 0, 0);
		world.setBlock(i + 2, j, k, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 2, j, k + 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 2, j, k + 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 2, j + 1, k - 6, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 2, j + 1, k - 3, AbyssalCraft.cstonebrickfence, 0, 0);
		world.setBlock(i + 2, j + 1, k, AbyssalCraft.cstonebrickfence, 0, 0);
		world.setBlock(i + 2, j + 1, k + 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 2, j + 2, k - 6, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 2, j + 2, k + 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 2, j + 3, k - 6, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 2, j + 3, k + 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 2, j + 4, k - 6, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 2, j + 4, k + 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 2, j + 5, k - 5, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 2, j + 5, k - 4, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 2, j + 5, k - 3, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 2, j + 5, k - 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 2, j + 5, k, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 2, j + 5, k + 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 3, j, k - 6, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 3, j, k - 5, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 3, j, k - 4, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 3, j, k - 3, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 3, j, k - 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 3, j, k - 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 3, j, k, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 3, j, k + 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 3, j, k + 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 3, j + 1, k - 6, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 3, j + 1, k + 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 3, j + 2, k - 6, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 3, j + 2, k + 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 3, j + 3, k - 6, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 3, j + 3, k + 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 3, j + 4, k - 6, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 3, j + 4, k + 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 3, j + 5, k - 6, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 3, j + 5, k - 4, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 3, j + 5, k - 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 3, j + 5, k - 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 3, j + 5, k + 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 4, j, k - 6, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 4, j, k - 5, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 4, j, k - 4, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 4, j, k - 3, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 4, j, k - 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 4, j, k - 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 4, j, k, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 4, j, k + 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 4, j, k + 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 4, j + 1, k - 6, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 4, j + 1, k - 5, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 4, j + 1, k - 4, AbyssalCraft.cstonebrickfence, 0, 0);
		world.setBlock(i + 4, j + 1, k - 3, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 4, j + 1, k - 2, AbyssalCraft.cstonebrickfence, 0, 0);
		world.setBlock(i + 4, j + 1, k - 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 4, j + 1, k, AbyssalCraft.cstonebrickfence, 0, 0);
		world.setBlock(i + 4, j + 1, k + 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 4, j + 1, k + 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 4, j + 2, k - 6, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 4, j + 2, k - 5, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 4, j + 2, k - 4, AbyssalCraft.cstonebrickfence, 0, 0);
		world.setBlock(i + 4, j + 2, k - 3, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 4, j + 2, k - 2, AbyssalCraft.cstonebrickfence, 0, 0);
		world.setBlock(i + 4, j + 2, k - 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 4, j + 2, k, AbyssalCraft.cstonebrickfence, 0, 0);
		world.setBlock(i + 4, j + 2, k + 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 4, j + 2, k + 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 4, j + 3, k - 6, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 4, j + 3, k - 5, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 4, j + 3, k - 4, AbyssalCraft.cstonebrickfence, 0, 0);
		world.setBlock(i + 4, j + 3, k - 3, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 4, j + 3, k - 2, AbyssalCraft.cstonebrickfence, 0, 0);
		world.setBlock(i + 4, j + 3, k - 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 4, j + 3, k, AbyssalCraft.cstonebrickfence, 0, 0);
		world.setBlock(i + 4, j + 3, k + 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 4, j + 4, k - 6, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 4, j + 4, k - 5, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 4, j + 4, k - 4, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 4, j + 4, k - 3, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 4, j + 4, k - 2, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 4, j + 4, k - 1, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 4, j + 5, k - 5, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i + 4, j + 5, k - 3, AbyssalCraft.cstonebrick, 0, 0);
		world.setBlock(i - 3, j + 1, k - 5, Blocks.air, 0, 0);
		world.setBlock(i - 3, j + 1, k - 4, Blocks.air, 0, 0);
		world.setBlock(i - 3, j + 1, k - 3, Blocks.air, 0, 0);
		world.setBlock(i - 3, j + 1, k - 2, Blocks.air, 0, 0);
		world.setBlock(i - 3, j + 1, k - 1, Blocks.air, 0, 0);
		world.setBlock(i - 3, j + 1, k, Blocks.air, 0, 0);
		world.setBlock(i - 3, j + 1, k + 1, Blocks.air, 0, 0);
		world.setBlock(i - 3, j + 2, k - 5, Blocks.air, 0, 0);
		world.setBlock(i - 3, j + 2, k - 4, Blocks.air, 0, 0);
		world.setBlock(i - 3, j + 2, k - 3, Blocks.air, 0, 0);
		world.setBlock(i - 3, j + 2, k - 2, Blocks.air, 0, 0);
		world.setBlock(i - 3, j + 2, k - 1, Blocks.air, 0, 0);
		world.setBlock(i - 3, j + 2, k, Blocks.air, 0, 0);
		world.setBlock(i - 3, j + 2, k + 1, Blocks.air, 0, 0);
		world.setBlock(i - 3, j + 3, k - 5, Blocks.air, 0, 0);
		world.setBlock(i - 3, j + 3, k - 4, Blocks.air, 0, 0);
		world.setBlock(i - 3, j + 3, k - 3, Blocks.air, 0, 0);
		world.setBlock(i - 3, j + 3, k - 2, Blocks.air, 0, 0);
		world.setBlock(i - 3, j + 3, k - 1, Blocks.air, 0, 0);
		world.setBlock(i - 3, j + 3, k, Blocks.air, 0, 0);
		world.setBlock(i - 3, j + 3, k + 1, Blocks.air, 0, 0);
		world.setBlock(i - 3, j + 4, k - 5, Blocks.air, 0, 0);
		world.setBlock(i - 3, j + 4, k - 4, Blocks.air, 0, 0);
		world.setBlock(i - 3, j + 4, k - 3, Blocks.air, 0, 0);
		world.setBlock(i - 3, j + 4, k - 2, Blocks.air, 0, 0);
		world.setBlock(i - 3, j + 4, k - 1, Blocks.air, 0, 0);
		world.setBlock(i - 3, j + 4, k, Blocks.air, 0, 0);
		world.setBlock(i - 3, j + 4, k + 1, Blocks.air, 0, 0);
		world.setBlock(i - 2, j + 1, k - 5, Blocks.air, 0, 0);
		world.setBlock(i - 2, j + 1, k - 4, Blocks.air, 0, 0);
		world.setBlock(i - 2, j + 1, k - 2, Blocks.air, 0, 0);
		world.setBlock(i - 2, j + 1, k - 1, Blocks.air, 0, 0);
		world.setBlock(i - 2, j + 1, k + 1, Blocks.air, 0, 0);
		world.setBlock(i - 2, j + 2, k - 5, Blocks.air, 0, 0);
		world.setBlock(i - 2, j + 2, k - 4, Blocks.air, 0, 0);
		world.setBlock(i - 2, j + 2, k - 3, Blocks.torch, 5, 0);
		world.setBlock(i - 2, j + 2, k - 2, Blocks.air, 0, 0);
		world.setBlock(i - 2, j + 2, k - 1, Blocks.air, 0, 0);
		world.setBlock(i - 2, j + 2, k, Blocks.torch, 5, 0);
		world.setBlock(i - 2, j + 2, k + 1, Blocks.air, 0, 0);
		world.setBlock(i - 2, j + 3, k - 5, Blocks.air, 0, 0);
		world.setBlock(i - 2, j + 3, k - 4, Blocks.air, 0, 0);
		world.setBlock(i - 2, j + 3, k - 3, Blocks.air, 0, 0);
		world.setBlock(i - 2, j + 3, k - 2, Blocks.air, 0, 0);
		world.setBlock(i - 2, j + 3, k - 1, Blocks.air, 0, 0);
		world.setBlock(i - 2, j + 3, k, Blocks.air, 0, 0);
		world.setBlock(i - 2, j + 3, k + 1, Blocks.air, 0, 0);
		world.setBlock(i - 2, j + 4, k - 5, Blocks.air, 0, 0);
		world.setBlock(i - 2, j + 4, k - 4, Blocks.air, 0, 0);
		world.setBlock(i - 2, j + 4, k - 3, Blocks.air, 0, 0);
		world.setBlock(i - 2, j + 4, k - 2, Blocks.air, 0, 0);
		world.setBlock(i - 2, j + 4, k - 1, Blocks.air, 0, 0);
		world.setBlock(i - 2, j + 4, k, Blocks.air, 0, 0);
		world.setBlock(i - 2, j + 4, k + 1, Blocks.air, 0, 0);
		world.setBlock(i - 1, j - 7, k - 3, Blocks.air, 0, 0);
		world.setBlock(i - 1, j - 7, k - 2, Blocks.air, 0, 0);
		world.setBlock(i - 1, j - 7, k - 1, Blocks.air, 0, 0);
		world.setBlock(i - 1, j - 7, k, Blocks.air, 0, 0);
		world.setBlock(i - 1, j - 6, k - 3, Blocks.air, 0, 0);
		world.setBlock(i - 1, j - 6, k - 2, Blocks.air, 0, 0);
		world.setBlock(i - 1, j - 6, k - 1, Blocks.air, 0, 0);
		world.setBlock(i - 1, j - 6, k, Blocks.air, 0, 0);
		world.setBlock(i - 1, j - 5, k - 3, Blocks.air, 0, 0);
		world.setBlock(i - 1, j - 5, k - 2, Blocks.air, 0, 0);
		world.setBlock(i - 1, j - 5, k - 1, Blocks.air, 0, 0);
		world.setBlock(i - 1, j - 5, k, Blocks.air, 0, 0);
		world.setBlock(i - 1, j + 1, k - 6, Blocks.air, 0, 0);
		world.setBlock(i - 1, j + 1, k - 5, Blocks.air, 0, 0);
		world.setBlock(i - 1, j + 1, k - 4, Blocks.air, 0, 0);
		world.setBlock(i - 1, j + 1, k - 3, Blocks.air, 0, 0);
		world.setBlock(i - 1, j + 1, k - 2, Blocks.air, 0, 0);
		world.setBlock(i - 1, j + 1, k - 1, Blocks.air, 0, 0);
		world.setBlock(i - 1, j + 1, k, Blocks.air, 0, 0);
		world.setBlock(i - 1, j + 1, k + 1, Blocks.air, 0, 0);
		world.setBlock(i - 1, j + 2, k - 6, Blocks.air, 0, 0);
		world.setBlock(i - 1, j + 2, k - 5, Blocks.air, 0, 0);
		world.setBlock(i - 1, j + 2, k - 4, Blocks.air, 0, 0);
		world.setBlock(i - 1, j + 2, k - 3, Blocks.air, 0, 0);
		world.setBlock(i - 1, j + 2, k - 2, Blocks.air, 0, 0);
		world.setBlock(i - 1, j + 2, k - 1, Blocks.air, 0, 0);
		world.setBlock(i - 1, j + 2, k, Blocks.air, 0, 0);
		world.setBlock(i - 1, j + 2, k + 1, Blocks.air, 0, 0);
		world.setBlock(i - 1, j + 3, k - 6, Blocks.air, 0, 0);
		world.setBlock(i - 1, j + 3, k - 5, Blocks.air, 0, 0);
		world.setBlock(i - 1, j + 3, k - 4, Blocks.air, 0, 0);
		world.setBlock(i - 1, j + 3, k - 3, Blocks.air, 0, 0);
		world.setBlock(i - 1, j + 3, k - 2, Blocks.air, 0, 0);
		world.setBlock(i - 1, j + 3, k - 1, Blocks.air, 0, 0);
		world.setBlock(i - 1, j + 3, k, Blocks.air, 0, 0);
		world.setBlock(i - 1, j + 3, k + 1, Blocks.air, 0, 0);
		world.setBlock(i - 1, j + 4, k - 5, Blocks.air, 0, 0);
		world.setBlock(i - 1, j + 4, k - 4, Blocks.air, 0, 0);
		world.setBlock(i - 1, j + 4, k - 3, Blocks.air, 0, 0);
		world.setBlock(i - 1, j + 4, k - 2, Blocks.air, 0, 0);
		world.setBlock(i - 1, j + 4, k - 1, Blocks.air, 0, 0);
		world.setBlock(i - 1, j + 4, k, Blocks.air, 0, 0);
		world.setBlock(i - 1, j + 4, k + 1, Blocks.air, 0, 0);
		world.setBlock(i, j - 7, k - 2, Blocks.air, 0, 0);
		world.setBlock(i, j - 7, k - 1, Blocks.air, 0, 0);
		world.setBlock(i, j - 7, k, Blocks.ladder, 2, 0);
		world.setBlock(i, j - 6, k - 3, Blocks.air, 0, 0);
		world.setBlock(i, j - 6, k - 2, Blocks.air, 0, 0);
		world.setBlock(i, j - 6, k - 1, Blocks.air, 0, 0);
		world.setBlock(i, j - 6, k, Blocks.ladder, 2, 0);
		world.setBlock(i, j - 5, k - 3, Blocks.air, 0, 0);
		world.setBlock(i, j - 5, k - 2, Blocks.air, 0, 0);
		world.setBlock(i, j - 5, k - 1, Blocks.air, 0, 0);
		world.setBlock(i, j - 5, k, Blocks.ladder, 2, 0);
		world.setBlock(i, j - 4, k, Blocks.ladder, 2, 0);
		world.setBlock(i, j - 3, k, Blocks.ladder, 2, 0);
		world.setBlock(i, j - 2, k, Blocks.ladder, 2, 0);
		world.setBlock(i, j - 1, k, Blocks.ladder, 2, 0);
		world.setBlock(i, j + 1, k - 6, Blocks.air, 0, 0);
		world.setBlock(i, j + 1, k - 5, Blocks.air, 0, 0);
		world.setBlock(i, j + 1, k - 4, Blocks.air, 0, 0);
		world.setBlock(i, j + 1, k - 3, Blocks.air, 0, 0);
		world.setBlock(i, j + 1, k - 2, Blocks.air, 0, 0);
		world.setBlock(i, j + 1, k - 1, Blocks.air, 0, 0);
		world.setBlock(i, j + 1, k, Blocks.air, 0, 0);
		world.setBlock(i, j + 2, k - 6, Blocks.air, 0, 0);
		world.setBlock(i, j + 2, k - 5, Blocks.air, 0, 0);
		world.setBlock(i, j + 2, k - 4, Blocks.air, 0, 0);
		world.setBlock(i, j + 2, k - 3, Blocks.air, 0, 0);
		world.setBlock(i, j + 2, k - 2, Blocks.air, 0, 0);
		world.setBlock(i, j + 2, k - 1, Blocks.air, 0, 0);
		world.setBlock(i, j + 2, k, Blocks.air, 0, 0);
		world.setBlock(i, j + 2, k + 1, Blocks.air, 0, 0);
		world.setBlock(i, j + 3, k - 6, Blocks.air, 0, 0);
		world.setBlock(i, j + 3, k - 5, Blocks.air, 0, 0);
		world.setBlock(i, j + 3, k - 4, Blocks.air, 0, 0);
		world.setBlock(i, j + 3, k - 3, Blocks.air, 0, 0);
		world.setBlock(i, j + 3, k - 2, Blocks.air, 0, 0);
		world.setBlock(i, j + 3, k - 1, Blocks.air, 0, 0);
		world.setBlock(i, j + 3, k, Blocks.air, 0, 0);
		world.setBlock(i, j + 3, k + 1, Blocks.air, 0, 0);
		world.setBlock(i, j + 4, k - 5, Blocks.air, 0, 0);
		world.setBlock(i, j + 4, k - 4, Blocks.air, 0, 0);
		world.setBlock(i, j + 4, k - 3, Blocks.air, 0, 0);
		world.setBlock(i, j + 4, k - 2, Blocks.air, 0, 0);
		world.setBlock(i, j + 4, k - 1, Blocks.air, 0, 0);
		world.setBlock(i, j + 4, k, Blocks.air, 0, 0);
		world.setBlock(i, j + 4, k + 1, Blocks.air, 0, 0);
		world.setBlock(i + 1, j - 7, k - 3, Blocks.air, 0, 0);
		world.setBlock(i + 1, j - 7, k - 2, Blocks.air, 0, 0);
		world.setBlock(i + 1, j - 7, k - 1, Blocks.air, 0, 0);
		world.setBlock(i + 1, j - 7, k, Blocks.air, 0, 0);
		world.setBlock(i + 1, j - 6, k - 3, Blocks.air, 0, 0);
		world.setBlock(i + 1, j - 6, k - 2, Blocks.air, 0, 0);
		world.setBlock(i + 1, j - 6, k - 1, Blocks.air, 0, 0);
		world.setBlock(i + 1, j - 6, k, Blocks.air, 0, 0);
		world.setBlock(i + 1, j - 5, k - 3, Blocks.air, 0, 0);
		world.setBlock(i + 1, j - 5, k - 2, Blocks.air, 0, 0);
		world.setBlock(i + 1, j - 5, k - 1, Blocks.air, 0, 0);
		world.setBlock(i + 1, j - 5, k, Blocks.air, 0, 0);
		world.setBlock(i + 1, j + 1, k - 6, Blocks.air, 0, 0);
		world.setBlock(i + 1, j + 1, k - 5, Blocks.air, 0, 0);
		world.setBlock(i + 1, j + 1, k - 4, Blocks.air, 0, 0);
		world.setBlock(i + 1, j + 1, k - 3, Blocks.air, 0, 0);
		world.setBlock(i + 1, j + 1, k - 2, Blocks.air, 0, 0);
		world.setBlock(i + 1, j + 1, k - 1, Blocks.air, 0, 0);
		world.setBlock(i + 1, j + 1, k, Blocks.air, 0, 0);
		world.setBlock(i + 1, j + 1, k + 1, Blocks.air, 0, 0);
		world.setBlock(i + 1, j + 2, k - 6, Blocks.air, 0, 0);
		world.setBlock(i + 1, j + 2, k - 5, Blocks.air, 0, 0);
		world.setBlock(i + 1, j + 2, k - 4, Blocks.air, 0, 0);
		world.setBlock(i + 1, j + 2, k - 3, Blocks.air, 0, 0);
		world.setBlock(i + 1, j + 2, k - 2, Blocks.air, 0, 0);
		world.setBlock(i + 1, j + 2, k - 1, Blocks.air, 0, 0);
		world.setBlock(i + 1, j + 2, k, Blocks.air, 0, 0);
		world.setBlock(i + 1, j + 2, k + 1, Blocks.air, 0, 0);
		world.setBlock(i + 1, j + 3, k - 6, Blocks.air, 0, 0);
		world.setBlock(i + 1, j + 3, k - 5, Blocks.air, 0, 0);
		world.setBlock(i + 1, j + 3, k - 4, Blocks.air, 0, 0);
		world.setBlock(i + 1, j + 3, k - 3, Blocks.air, 0, 0);
		world.setBlock(i + 1, j + 3, k - 2, Blocks.air, 0, 0);
		world.setBlock(i + 1, j + 3, k - 1, Blocks.air, 0, 0);
		world.setBlock(i + 1, j + 3, k, Blocks.air, 0, 0);
		world.setBlock(i + 1, j + 3, k + 1, Blocks.air, 0, 0);
		world.setBlock(i + 1, j + 4, k - 5, Blocks.air, 0, 0);
		world.setBlock(i + 1, j + 4, k - 4, Blocks.air, 0, 0);
		world.setBlock(i + 1, j + 4, k - 3, Blocks.air, 0, 0);
		world.setBlock(i + 1, j + 4, k - 2, Blocks.air, 0, 0);
		world.setBlock(i + 1, j + 4, k - 1, Blocks.air, 0, 0);
		world.setBlock(i + 1, j + 4, k, Blocks.air, 0, 0);
		world.setBlock(i + 1, j + 4, k + 1, Blocks.air, 0, 0);
		world.setBlock(i + 2, j + 1, k - 5, Blocks.air, 0, 0);
		world.setBlock(i + 2, j + 1, k - 4, Blocks.air, 0, 0);
		world.setBlock(i + 2, j + 1, k - 2, Blocks.air, 0, 0);
		world.setBlock(i + 2, j + 1, k - 1, Blocks.air, 0, 0);
		world.setBlock(i + 2, j + 1, k + 1, Blocks.air, 0, 0);
		world.setBlock(i + 2, j + 2, k - 5, Blocks.air, 0, 0);
		world.setBlock(i + 2, j + 2, k - 4, Blocks.air, 0, 0);
		world.setBlock(i + 2, j + 2, k - 3, Blocks.torch, 5, 0);
		world.setBlock(i + 2, j + 2, k - 2, Blocks.air, 0, 0);
		world.setBlock(i + 2, j + 2, k - 1, Blocks.air, 0, 0);
		world.setBlock(i + 2, j + 2, k, Blocks.torch, 5, 0);
		world.setBlock(i + 2, j + 2, k + 1, Blocks.air, 0, 0);
		world.setBlock(i + 2, j + 3, k - 5, Blocks.air, 0, 0);
		world.setBlock(i + 2, j + 3, k - 4, Blocks.air, 0, 0);
		world.setBlock(i + 2, j + 3, k - 3, Blocks.air, 0, 0);
		world.setBlock(i + 2, j + 3, k - 2, Blocks.air, 0, 0);
		world.setBlock(i + 2, j + 3, k - 1, Blocks.air, 0, 0);
		world.setBlock(i + 2, j + 3, k, Blocks.air, 0, 0);
		world.setBlock(i + 2, j + 3, k + 1, Blocks.air, 0, 0);
		world.setBlock(i + 2, j + 4, k - 5, Blocks.air, 0, 0);
		world.setBlock(i + 2, j + 4, k - 4, Blocks.air, 0, 0);
		world.setBlock(i + 2, j + 4, k - 3, Blocks.air, 0, 0);
		world.setBlock(i + 2, j + 4, k - 2, Blocks.air, 0, 0);
		world.setBlock(i + 2, j + 4, k - 1, Blocks.air, 0, 0);
		world.setBlock(i + 2, j + 4, k, Blocks.air, 0, 0);
		world.setBlock(i + 2, j + 4, k + 1, Blocks.air, 0, 0);
		world.setBlock(i + 3, j + 1, k - 5, Blocks.air, 0, 0);
		world.setBlock(i + 3, j + 1, k - 4, Blocks.air, 0, 0);
		world.setBlock(i + 3, j + 1, k - 3, Blocks.air, 0, 0);
		world.setBlock(i + 3, j + 1, k - 2, Blocks.air, 0, 0);
		world.setBlock(i + 3, j + 1, k - 1, Blocks.air, 0, 0);
		world.setBlock(i + 3, j + 1, k, Blocks.air, 0, 0);
		world.setBlock(i + 3, j + 1, k + 1, Blocks.air, 0, 0);
		world.setBlock(i + 3, j + 2, k - 5, Blocks.air, 0, 0);
		world.setBlock(i + 3, j + 2, k - 4, Blocks.air, 0, 0);
		world.setBlock(i + 3, j + 2, k - 3, Blocks.air, 0, 0);
		world.setBlock(i + 3, j + 2, k - 2, Blocks.air, 0, 0);
		world.setBlock(i + 3, j + 2, k - 1, Blocks.air, 0, 0);
		world.setBlock(i + 3, j + 2, k, Blocks.air, 0, 0);
		world.setBlock(i + 3, j + 2, k + 1, Blocks.air, 0, 0);
		world.setBlock(i + 3, j + 3, k - 5, Blocks.air, 0, 0);
		world.setBlock(i + 3, j + 3, k - 4, Blocks.air, 0, 0);
		world.setBlock(i + 3, j + 3, k - 3, Blocks.air, 0, 0);
		world.setBlock(i + 3, j + 3, k - 2, Blocks.air, 0, 0);
		world.setBlock(i + 3, j + 3, k - 1, Blocks.air, 0, 0);
		world.setBlock(i + 3, j + 3, k, Blocks.air, 0, 0);
		world.setBlock(i + 3, j + 3, k + 1, Blocks.air, 0, 0);
		world.setBlock(i + 3, j + 4, k - 5, Blocks.air, 0, 0);
		world.setBlock(i + 3, j + 4, k - 4, Blocks.air, 0, 0);
		world.setBlock(i + 3, j + 4, k - 3, Blocks.air, 0, 0);
		world.setBlock(i + 3, j + 4, k - 2, Blocks.air, 0, 0);
		world.setBlock(i + 3, j + 4, k - 1, Blocks.air, 0, 0);
		world.setBlock(i + 3, j + 4, k, Blocks.air, 0, 0);
		world.setBlock(i + 3, j + 4, k + 1, Blocks.air, 0, 0);

		return true;
	}
	
	private void setBasementFurniture(World world, int i, int j, int k) {
		final int roll = world.rand.nextInt(40);
		Block block = null;
		int meta = 0;
		
		if (roll == 0) {
			block = Blocks.anvil;
			meta = 8;
		} else if (roll == 1) {
			block = Blocks.brewing_stand;
		} else if (roll == 2) {
			block = Blocks.cauldron;
		} else if (roll == 3) {
			block = Blocks.flower_pot;
		} else if (roll == 4) {
			block = Blocks.hay_block;
		} else if (roll == 5) {
			block = Blocks.furnace;
		} else if (roll == 6) {
			block = AbyssalCraft.relic;
		} else if (roll == 7) {
			block = GameRegistry.findBlock("Botania", "pool");
		} else if (roll == 8) {
			block = GameRegistry.findBlock("Thaumcraft", "blockTable");
			meta = 15;
		} else if (roll == 9) {
			block = AbyssalCraft.gatekeeperminionspawner;
		}
		
		// Place Chest or Special Block
		if (block != null) {
			world.setBlock(i, j - 7, k - 3, block, meta, 0);
		} else {
			world.setBlock(i, j - 7, k - 3, Blocks.chest, 0, 0);
			TileEntityChest tileentitychest = (TileEntityChest)world.getTileEntity(i, j - 7, k - 3);
			if(tileentitychest != null) {
				WeightedRandomChestContent.generateChestContents(world.rand, ChestCorridor.strongholdChestContents, tileentitychest, ChestGenHooks.getCount(STRONGHOLD_CORRIDOR, world.rand));
			}
		}
	}
}
