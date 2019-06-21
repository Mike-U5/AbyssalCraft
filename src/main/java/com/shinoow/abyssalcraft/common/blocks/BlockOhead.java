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

import thaumcraft.api.crafting.IInfusionStabiliser;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityOhead;

import cpw.mods.fml.common.Optional.Interface;
import cpw.mods.fml.common.Optional.Method;

@Interface(modid = "Thaumcraft", iface = "thaumcraft.api.crafting.IInfusionStabiliser", striprefs = true)
public class BlockOhead extends BlockContainer implements IInfusionStabiliser {

	public BlockOhead() {
		super(Material.cloth);
		setBlockBounds(0.1F, 0.0F, 0.1F, 0.8F, 0.7F, 0.8F);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityOhead();
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public int getRenderType() {
		return -3;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		setBlockBoundsBasedOnState(par1World, par2, par3, par4);
		return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
	}


	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase placer, ItemStack par6ItemStack)
	{
		if (placer == null) {
			return;
		}

		TileEntityOhead tile = (TileEntityOhead) world.getTileEntity(x, y, z);
		tile.direction = MathHelper.floor_double(placer.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
	}

	@Override
	@Method(modid = "Thaumcraft")
	public boolean canStabaliseInfusion(World world, int x, int y, int z) {
		return true;
	}
}
