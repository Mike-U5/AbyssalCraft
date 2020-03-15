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

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.util.EntityUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockFire;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockDreadFire extends BlockFire {

	@SideOnly(Side.CLIENT)
	private IIcon[] iconArray;

	public BlockDreadFire() {
		super();
		setTickRandomly(true);
	}

	@Override
	public boolean isBurning(IBlockAccess world, int x, int y, int z) {
		return true;
	}

	@Override
	public IIcon getIcon(int par1, int par2) {
		return iconArray[0];
	}

	private boolean canNeighborBurn(World par1World, int par2, int par3, int par4) {
		return canCatchFire(par1World, par2 + 1, par3, par4, ForgeDirection.WEST) || canCatchFire(par1World, par2 - 1, par3, par4, ForgeDirection.EAST) || canCatchFire(par1World, par2, par3 - 1, par4, ForgeDirection.UP) || canCatchFire(par1World, par2, par3 + 1, par4, ForgeDirection.DOWN) || canCatchFire(par1World, par2, par3, par4 - 1, ForgeDirection.SOUTH) || canCatchFire(par1World, par2, par3, par4 + 1, ForgeDirection.NORTH);
	}

	@Override
	public void onBlockAdded(World par1World, int par2, int par3, int par4) {

		if (par1World.getBlock(par2, par3 - 1, par4) != AbyssalCraft.dreadstone || !BlockDreadlandsPortal.tryToCreatePortal(par1World, par2, par3, par4))
			if (!World.doesBlockHaveSolidTopSurface(par1World, par2, par3 - 1, par4) && !canNeighborBurn(par1World, par2, par3, par4))
				par1World.setBlockToAir(par2, par3, par4);
			else
				par1World.scheduleBlockUpdate(par2, par3, par4, this, tickRate(par1World) + par1World.rand.nextInt(10));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		iconArray = new IIcon[] { par1IconRegister.registerIcon("abyssalcraft:dfire_layer_0"), par1IconRegister.registerIcon("abyssalcraft:dfire_layer_1") };
		blockIcon = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + "dfire_layer_0");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getFireIcon(int par1) {
		return iconArray[par1];
	}

}
