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
package com.shinoow.abyssalcraft.common.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDamage;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;

public class EnchantmentLightPierce extends EnchantmentDamage {

	public EnchantmentLightPierce(int par1) {
		super(par1, 5, 3);
	}

	@Override
	public int getMinEnchantability(int par1) {
		return 5 + (par1 - 1) * 8;
	}

	@Override
	public int getMaxEnchantability(int par1) {
		return getMinEnchantability(par1) + 20;
	}

	@Override
	public int getMaxLevel() {
		return 5;
	}

	@Override
	public float func_152376_a(int level, EnumCreatureAttribute attrib) {
		if (attrib.name() == "SHADOW") {
			return level * 2.5F;
		}
		return attrib == AbyssalCraftAPI.SHADOW ? level * 2.5F : 0.0F;
	}

	@Override
	public String getName() {
		return "enchantment.damage.shadow";
	}

	@Override
	public boolean canApplyTogether(Enchantment par1Enchantment) {
		return !(par1Enchantment instanceof EnchantmentDamage);
	}

	@Override
	public boolean canApply(ItemStack par1ItemStack) {
		return par1ItemStack.getItem() instanceof ItemAxe ? true : super.canApply(par1ItemStack);
	}
}
