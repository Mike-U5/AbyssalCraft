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
package com.shinoow.abyssalcraft.common.blocks;

import java.util.Random;
import java.util.ArrayList;
import cpw.mods.fml.common.registry.*;

import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import com.shinoow.abyssalcraft.AbyssalCraft;

public class BlockInfusedDreadstone extends BlockACBasic {
	
	private Random rand = new Random();

	public BlockInfusedDreadstone(int harvestlevel, float hardness, float resistance) {
		super(Material.rock, "pickaxe", harvestlevel, hardness, resistance, soundTypeStone);
	}

	@Override
	public Item getItemDropped(int par1, Random par2Random, int par3) {
		Item item = GameRegistry.findItem("Thaumcraft", "ItemShard");
		if (item != null) {
			return item;
		}
		return AbyssalCraft.Coralium;
	}
	
	@Override
	public int damageDropped(int meta) {
	    return rand.nextInt(6);
	}

	@Override
	public int quantityDropped(int meta, int fortune, Random rand) {
		return 2 + rand.nextInt(fortune+1);
	}

	@Override
	public int getExpDrop(IBlockAccess par1BlockAccess, int par2, int par3) {
		return 3;
	}
}
