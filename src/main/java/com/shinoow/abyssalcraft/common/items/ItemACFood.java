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

import net.minecraft.item.ItemFood;

public class ItemACFood extends ItemFood {
	public ItemACFood(int foodPts, float saturation, boolean dogFood) {
		super(foodPts, saturation, dogFood);
		setCreativeTab(AbyssalCraft.tabItems);
		setMaxStackSize((foodPts >= 20) ? 16 : 64);
	}
}
