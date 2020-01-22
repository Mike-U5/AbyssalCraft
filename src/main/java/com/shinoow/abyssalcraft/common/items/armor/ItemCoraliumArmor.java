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

public class ItemCoraliumArmor extends ItemArmor {
	public ItemCoraliumArmor(ArmorMaterial par2EnumArmorMaterial, int par3, int par4){
		super(par2EnumArmorMaterial, par3, par4);
		setCreativeTab(AbyssalCraft.tabTools);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return EnumChatFormatting.AQUA + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	{
		if(stack.getItem() == AbyssalCraft.Corhelmet || stack.getItem() == AbyssalCraft.Corplate || stack.getItem() == AbyssalCraft.Corboots)
			return "abyssalcraft:textures/armor/coralium_1.png";

		if(stack.getItem() == AbyssalCraft.Corlegs)
			return "abyssalcraft:textures/armor/coralium_2.png";
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
		if (player.getCurrentArmor(3) != null && player.getCurrentArmor(3).getItem().equals(AbyssalCraft.Corhelmet)) {
			setEff++;
		}
		
		if (player.getCurrentArmor(2) != null && player.getCurrentArmor(2).getItem().equals(AbyssalCraft.Corplate)) {
			setEff++;
		}
		
		if (player.getCurrentArmor(1) != null && player.getCurrentArmor(1).getItem().equals(AbyssalCraft.Corlegs)) {
			setEff++;
		}
		
		if (player.getCurrentArmor(0) != null && player.getCurrentArmor(0).getItem().equals(AbyssalCraft.Corboots)) {
			setEff++;
		}
		
		if (setEff >= 4) {
			if(player.getActivePotionEffect(AbyssalCraft.Cplague) != null) {
				player.removePotionEffect(AbyssalCraft.Cplague.getId());
			}
		}
	}
}
