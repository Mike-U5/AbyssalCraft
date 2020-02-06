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

import java.util.List;

import com.shinoow.abyssalcraft.AbyssalCraft;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional.Interface;
import cpw.mods.fml.common.Optional.Method;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import thaumcraft.api.IVisDiscountGear;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.nodes.IRevealer;

@Interface(iface = "thaumcraft.api.nodes.IRevealer", modid = "Thaumcraft")
public class ItemEthaxiumArmor extends ItemArmor implements IVisDiscountGear, IRevealer {
	public ItemEthaxiumArmor(ArmorMaterial par2EnumArmorMaterial, int par3, int par4) {
		super(par2EnumArmorMaterial, par3, par4);
		setCreativeTab(AbyssalCraft.tabTools);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return EnumChatFormatting.AQUA + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer) {
		if (stack.getItem() == AbyssalCraft.ethHelmet || stack.getItem() == AbyssalCraft.ethPlate || stack.getItem() == AbyssalCraft.ethBoots)
			return "abyssalcraft:textures/armor/ethaxium_1.png";

		if (stack.getItem() == AbyssalCraft.ethLegs)
			return "abyssalcraft:textures/armor/ethaxium_2.png";
		else
			return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister) {
		itemIcon = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + this.getUnlocalizedName().substring(5));
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemstack) {
		if (itemstack.getItem() == AbyssalCraft.ethPlate) {
			player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 10));
			if (player.getActivePotionEffect(Potion.poison) != null) {
				player.removePotionEffect(Potion.poison.getId());
			}
		}

		if (itemstack.getItem() == AbyssalCraft.ethLegs)
			player.addPotionEffect(new PotionEffect(Potion.jump.getId(), 10, 0));
		if (player.ticksExisted % 200 == 0) {
			player.heal(1F);
		}

		if (itemstack.getItem() == AbyssalCraft.ethBoots) {
			player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 10, 2));
		}

		// Clear potion effects
		if (player.getActivePotionEffect(Potion.damageBoost) != null && player.getActivePotionEffect(Potion.damageBoost).getDuration() == 0)
			player.removePotionEffect(Potion.damageBoost.id);
		if (player.getActivePotionEffect(Potion.regeneration) != null && player.getActivePotionEffect(Potion.regeneration).getDuration() == 0)
			player.removePotionEffect(Potion.regeneration.id);
		if (player.getActivePotionEffect(Potion.fireResistance) != null && player.getActivePotionEffect(Potion.fireResistance).getDuration() == 0)
			player.removePotionEffect(Potion.fireResistance.id);
		if (player.getActivePotionEffect(Potion.jump) != null && player.getActivePotionEffect(Potion.jump).getDuration() == 0)
			player.removePotionEffect(Potion.jump.id);
	}
	
	// Thaumcraft Bonuses
	@Override
	@Method(modid = "Thaumcraft")
	public int getVisDiscount(ItemStack stack, EntityPlayer player, Aspect aspect) {
		return (stack.getItem() == AbyssalCraft.ethHelmet && aspect == Aspect.ENTROPY) ? 7 : 0;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List l, boolean B){
		if(Loader.isModLoaded("Thaumcraft")) {
			if(is.getItem() == AbyssalCraft.ethHelmet) {
				l.add("\u00A75"+StatCollector.translateToLocal("tc.visdiscount")+" (Perditio) : 7%");
			}
		}
	}

	@Override
	@Method(modid = "Thaumcraft")
	public boolean showNodes(ItemStack itemstack, EntityLivingBase player) {
		return itemstack.getItem() == AbyssalCraft.ethHelmet;
	}
}
