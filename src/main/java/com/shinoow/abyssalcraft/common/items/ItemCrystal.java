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

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.item.ICrystal;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCrystal extends Item implements ICrystal {

	private String[] names = new String[]{"Iron", "Gold", "Sulfur", "Carbon", "Oxygen", "Hydrogen", "Nitrogen", "Phosphorus",
			"Potassium", "Nitrate", "Methane", "Redstone", "Abyssalnite", "Coralium", "Dreadium", "Blaze", "Tin", "Copper",
			"Silicon", "Magnesium", "Aluminium", "Silica", "Alumina", "Magnesia", "Zinc"};
	private String[] atoms = new String[]{"Fe", "Au", "S", "C", "O", "H", "N", "P", "K", "NO\u2083", "CH\u2084", "none", "An",
			"Cor", "Dr", "none", "Sn", "Cu", "Si", "Mg", "Al", "SiO\u2082", "Al\u2082O\u2083", "MgO", "Zn"};
	private int[] crystalColors = new int[]{0xD9D9D9, 0xF3CC3E, 0xF6FF00, 0x3D3D36, 0x4BA3E3, 0x4B59D0, 0x214102, 0x996A18,
			0xFCED80, 0x1500FF, 0x19FC00, 0xFF0000, 0x8002BF, 0x00FFEE, 0xB00000, 0xFFCC00, 0xD9D8D7, 0xE89207, 
			0x5E5D6C, 0x510F0D, 0xC6C6D0, 0xFF0066, 0xAA9477, 0xF39F43, 0xC9CABC};

	public ItemCrystal(){
		super();
		setCreativeTab(AbyssalCraft.tabCrystals);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	//	@Override
	//	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
	//		if(par1ItemStack.getItem() == AbyssalCraft.crystalOxygen && par3EntityPlayer.isInWater() && par3EntityPlayer.getAir() != 300){
	//			par3EntityPlayer.setAir(300);
	//			par1ItemStack.stackSize--;
	//		}
	//		if(par1ItemStack.getItem() == AbyssalCraft.crystalSulfur)
	//			par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 100));
	//		return par1ItemStack;
	//	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item par1Item, CreativeTabs par2CreativeTab, List par3List){
		for(int i = 0; i < names.length; ++i)
			par3List.add(new ItemStack(par1Item, 1, i));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
		return crystalColors[par1ItemStack.getItemDamage()];
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List l, boolean B){
		l.add(StatCollector.translateToLocal("tooltip.crystal")+ ": " + atoms[is.getItemDamage()]);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {
		return StatCollector.translateToLocal(getUnlocalizedName() + "." + names[par1ItemStack.getItemDamage()] + ".name");
	}
}
