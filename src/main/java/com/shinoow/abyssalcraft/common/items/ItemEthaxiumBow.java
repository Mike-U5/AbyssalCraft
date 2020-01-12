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

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.entity.EntityAntimatterArrow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemEthaxiumBow extends ItemCoraliumBow {
	
	public ItemEthaxiumBow(float chargeTime, int anim_0, int anim_1, int anim_2) {
		super(chargeTime, anim_0, anim_1, anim_2);
		setMaxDamage(991);
	}
	
	@Override
	protected EntityArrow getFiredArrow(EntityPlayer player, World world, float f) {
		return new EntityAntimatterArrow(world, player, f * 2.0F);
	}

	@Override
	public boolean getIsRepairable(ItemStack tool, ItemStack repairMaterial) {
		return AbyssalCraft.ethaxiumIngot == repairMaterial.getItem() ? true : super.getIsRepairable(tool, repairMaterial);
	}
	
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addInformation(ItemStack par1ItemStack, EntityPlayer entityplayer, List list, boolean is) {
		list.add(StatCollector.translateToLocal("tooltip.corbow.1"));
		list.add(StatCollector.translateToLocal("tooltip.corbow.2"));
	}

}
