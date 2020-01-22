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
package com.shinoow.abyssalcraft.common.items.armor;

import com.shinoow.abyssalcraft.AbyssalCraft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemDreadiumArmor extends ItemArmor {
	public ItemDreadiumArmor(ArmorMaterial par2EnumArmorMaterial, int par3, int par4){
		super(par2EnumArmorMaterial, par3, par4);
		setCreativeTab(AbyssalCraft.tabTools);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return EnumChatFormatting.DARK_RED + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer) {
		if(stack.getItem() == AbyssalCraft.dreadiumhelmet || stack.getItem() == AbyssalCraft.dreadiumplate || stack.getItem() == AbyssalCraft.dreadiumboots)
			return "abyssalcraft:textures/armor/dreadium_1.png";

		if(stack.getItem() == AbyssalCraft.dreadiumlegs)
			return "abyssalcraft:textures/armor/dreadium_2.png";
		else return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister)
	{
		itemIcon = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + this.getUnlocalizedName().substring(5));
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemstack) {
		int setEff = 0;
		if (player.getCurrentArmor(3) != null && player.getCurrentArmor(3).getItem().equals(AbyssalCraft.dreadiumhelmet)) {
			setEff++;
		}
		
		if (player.getCurrentArmor(2) != null && player.getCurrentArmor(2).getItem().equals(AbyssalCraft.dreadiumplate)) {
			setEff++;
		}
		
		if (player.getCurrentArmor(1) != null && player.getCurrentArmor(1).getItem().equals(AbyssalCraft.dreadiumlegs)) {
			setEff++;
		}
		
		if (player.getCurrentArmor(0) != null && player.getCurrentArmor(0).getItem().equals(AbyssalCraft.dreadiumboots)) {
			setEff++;
		}
		
		if (setEff >= 4) {
			if(player.getActivePotionEffect(AbyssalCraft.Dplague) != null) {
				player.removePotionEffect(AbyssalCraft.Dplague.getId());
			}
		}
	}
}
