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

public class ItemPortalPlacerJzh extends ItemPortalPlacer {
	
	public ItemPortalPlacerJzh(){
		super();
		maxStackSize = 1;
		setCreativeTab(AbyssalCraft.tabTools);
	}
	
	protected int getKeyTier() {
		return 2;
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return EnumChatFormatting.BLUE + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
	}
}
