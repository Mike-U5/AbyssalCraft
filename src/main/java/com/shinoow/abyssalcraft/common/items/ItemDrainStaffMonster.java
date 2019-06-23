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
package com.shinoow.abyssalcraft.common.items;

import com.shinoow.abyssalcraft.AbyssalCraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemDrainStaffMonster extends ItemDrainStaff {

	public ItemDrainStaffMonster() {
		super();
		setUnlocalizedName("drainStaffMonster");
		setCreativeTab(AbyssalCraft.tabTools);
		setTextureName("abyssalcraft:DrainStaffMonster");
		setMaxStackSize(1);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {		
		if (!player.isSneaking()) {
			drain(stack, world, player, 2);
		}
		return stack;
	}
}