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
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockAbyssalGrass extends Block
{
	@SideOnly(Side.CLIENT)
	private IIcon iconGrassTop;
	@SideOnly(Side.CLIENT)
	private IIcon iconSnowOverlay;
	@SideOnly(Side.CLIENT)
	private static IIcon iconGrassSideOverlay;

	public BlockAbyssalGrass() {
		super(Material.sand);
		setTickRandomly(true);
		setCreativeTab(AbyssalCraft.tabBlock);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int par1, int par2)
	{
		return par1 == 1 ? iconGrassTop : par1 == 0 ? AbyssalCraft.abyssalsand.getBlockTextureFromSide(par1) : blockIcon;
	}

	@Override
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if (!par1World.isRemote)
			if (par1World.getBlockLightValue(par2, par3 + 1, par4) < 4 && par1World.getBlockLightOpacity(par2, par3 + 1, par4) > 2)
				par1World.setBlock(par2, par3, par4, AbyssalCraft.abyssalsand);
			else if (par1World.getBlockLightValue(par2, par3 + 1, par4) >= 9)
				for (int l = 0; l < 4; ++l)
				{
					int i1 = par2 + par5Random.nextInt(3) - 1;
					int j1 = par3 + par5Random.nextInt(5) - 3;
					int k1 = par4 + par5Random.nextInt(3) - 1;

					if (par1World.getBlock(i1, j1, k1) == AbyssalCraft.abyssalsand && par1World.getBlockMetadata(i1, j1, k1) == 0 && par1World.getBlockLightValue(i1, j1 + 1, k1) >= 4 && par1World.getBlockLightOpacity(i1, j1 + 1, k1) <= 2)
						par1World.setBlock(i1, j1, k1, AbyssalCraft.fusedabyssalsand);
				}
	}

	@Override
	public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction, IPlantable plantable) {
		return false;
	}

	@Override
	public Item getItemDropped(int par1, Random par2Random, int par3) {
		return AbyssalCraft.abyssalsand.getItemDropped(0, par2Random, par3);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		if (par5 == 1)
			return iconGrassTop;
		else if (par5 == 0)
			return AbyssalCraft.abyssalsand.getBlockTextureFromSide(par5);
		else
		{
			Material material = par1IBlockAccess.getBlock(par2, par3 + 1, par4).getMaterial();
			return material != Material.snow && material != Material.craftedSnow ? blockIcon : iconSnowOverlay;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister)
	{
		blockIcon = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + "fusedabyssalsand_side");
		iconGrassTop = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + "fusedabyssalsand");
		iconSnowOverlay = par1IconRegister.registerIcon("grass_side_snowed");
		BlockAbyssalGrass.iconGrassSideOverlay = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + "fusedabyssalsand_side");
	}

	@SideOnly(Side.CLIENT)
	public static IIcon getIconSideOverlay()
	{
		return iconGrassSideOverlay;
	}
}
