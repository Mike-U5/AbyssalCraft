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
import net.minecraft.util.IIcon;

public class BlockDivineRelic extends Block {

	@SideOnly(Side.CLIENT)
	private IIcon ODBcTopIcon;
	@SideOnly(Side.CLIENT)
	private IIcon ODBcBottomIcon;
	@SideOnly(Side.CLIENT)
	private static IIcon iconODBcSideOverlay;

	public BlockDivineRelic() {
		super(Material.iron);
		setCreativeTab(AbyssalCraft.tabBlock);
		setHarvestLevel("pickaxe", 0);
		setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 1.0F, 0.75F);
	}

	@Override
	public boolean isOpaqueCube(){
		return false;
	}

	@Override
	public boolean renderAsNormalBlock(){
		return false;
	}

	@Override
	public int quantityDropped(Random par1Random) {
		return 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int par1, int par2) {
		return par1 == 1 ? ODBcTopIcon : par1 == 0 ? ODBcBottomIcon : blockIcon;
	}


	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		blockIcon = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + "materializer_side");
		ODBcTopIcon = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + "relic");
		BlockDivineRelic.iconODBcSideOverlay = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + "materializer_side");
	}

	@SideOnly(Side.CLIENT)
	public static IIcon getIconSideOverlay() {
		return iconODBcSideOverlay;
	}
}
