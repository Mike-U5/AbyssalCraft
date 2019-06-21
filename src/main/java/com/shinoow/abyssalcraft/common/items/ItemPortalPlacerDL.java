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

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

public class ItemPortalPlacerDL extends ItemPortalPlacer {

	public ItemPortalPlacerDL() {
		super();
		maxStackSize = 1;
		setMaxDamage(1);
		setCreativeTab(AbyssalCraft.tabTools);
	}
	
	protected int getKeyTier() {
		return 1;
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return EnumChatFormatting.DARK_RED + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
	}
}
