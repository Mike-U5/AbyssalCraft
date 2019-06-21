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

import com.shinoow.abyssalcraft.AbyssalCraft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockDarklandsgrass extends Block
{
	@SideOnly(Side.CLIENT)
	private IIcon iconGrassTop;
	@SideOnly(Side.CLIENT)
	private IIcon iconSnowOverlay;
	@SideOnly(Side.CLIENT)
	private static IIcon iconGrassSideOverlay;

	public BlockDarklandsgrass()
	{
		super(Material.grass);
		setHardness(2.0F); //Harder than regular grass
		setStepSound(Block.soundTypeGrass);
	}

	@Override
	@SideOnly(Side.CLIENT)

	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	public IIcon getIcon(int par1, int par2) {
		return par1 == 1 ? iconGrassTop : par1 == 0 ? Blocks.dirt.getBlockTextureFromSide(par1) : blockIcon;
	}

	@Override
	public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction, IPlantable plantable) {
		Block plant = plantable.getPlant(world, x, y + 1, z);
		if (plant == AbyssalCraft.DLTSapling) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns the ID of the items to drop on destruction.
	 */
	@Override
	public Item getItemDropped(int par1, Random par2Random, int par3) {
		return Blocks.dirt.getItemDropped(0, par2Random, par3);
	}

	@Override
	@SideOnly(Side.CLIENT)

	/**
	 * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
	 */
	public IIcon getIcon(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
		if (par5 == 1)
			return iconGrassTop;
		else if (par5 == 0)
			return Blocks.dirt.getBlockTextureFromSide(par5);
		else
		{
			Material material = par1IBlockAccess.getBlock(par2, par3 + 1, par4).getMaterial();
			return material != Material.snow && material != Material.craftedSnow ? blockIcon : iconSnowOverlay;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)

	/**
	 * When this method is called, your block should register all the icons it needs with the given IconRegister. This
	 * is the only chance you get to register icons.
	 */
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		blockIcon = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + "DLGsides");
		iconGrassTop = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + "DLGtop");
		iconSnowOverlay = par1IconRegister.registerIcon("grass_side_snowed");
		BlockDarklandsgrass.iconGrassSideOverlay = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + "DLGsides");
	}

	@SideOnly(Side.CLIENT)
	public static IIcon getIconSideOverlay() {
		return iconGrassSideOverlay;
	}
}
