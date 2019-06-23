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

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

import com.shinoow.abyssalcraft.AbyssalCraft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSGlow extends Block {

	@SideOnly(Side.CLIENT)
	private IIcon[] icons;
	@SideOnly(Side.CLIENT)
	private static IIcon iconSideOverlay;

	public BlockSGlow() {
		super(Material.rock);
		setCreativeTab(AbyssalCraft.tabBlock);
		setHarvestLevel("pickaxe", 3);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int par1, int par2) {
		return par1 == 1 ? icons[1] : par1 == 0 ? icons[1] : icons[0];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconReg) {
		icons = new IIcon[2];
		icons[0] = iconReg.registerIcon("abyssalCraft:SGlow");
		icons[1] = iconReg.registerIcon("stonebrick");
		BlockSGlow.iconSideOverlay = iconReg.registerIcon("stonebrick");
	}

	@SideOnly(Side.CLIENT)
	public static IIcon getIconSideOverlay() {
		return iconSideOverlay;
	}
}
