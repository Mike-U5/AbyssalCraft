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

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;

public class ItemACKatana extends ItemACSword {

	public ItemACKatana(ToolMaterial mat, String name) {
		this(mat, name, null);
	}

	public ItemACKatana(ToolMaterial mat, String name, EnumChatFormatting format) {
		super(mat, name, format);
	}

	@Override
	public boolean isFull3D() {
		return true;
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase user) {
		target.addPotionEffect(new PotionEffect(AbyssalCraft.Dplague.id, 200));
		return super.hitEntity(stack, target, user);
	}
}
