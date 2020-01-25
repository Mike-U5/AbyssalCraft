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
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemCrozier extends ItemDrainStaff {

	public ItemCrozier() {
		super();
		setMaxStackSize(1);
	}
	
	@Override
	public boolean onItemUse(ItemStack usedItem, EntityPlayer player, World world, int parX, int parY, int parZ, int targetSide, float targetX, float targetY, float targetZ) {
		if (player.isSneaking()) {
			return new ItemPortalPlacerJzh().portalAction(usedItem, player, world, parX, parY, parZ, targetSide, targetX, targetY, targetZ);
		} else {
			return false;
		}
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {		
		if (!player.isSneaking()) {
			drain(stack, world, player, 4);
		}
		return stack;
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {
		return EnumChatFormatting.BLUE + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
	}
	
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addInformation(ItemStack is, EntityPlayer player, List list, boolean bl) {
		super.addInformation(is, player, list, bl);
		list.add(StatCollector.translateToLocal("tooltip.portalplacer.3"));
		list.add(StatCollector.translateToLocal("tooltip.portalplacer.4"));
	}
}
