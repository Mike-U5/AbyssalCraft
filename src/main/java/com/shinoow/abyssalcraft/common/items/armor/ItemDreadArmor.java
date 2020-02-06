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

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
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

import com.shinoow.abyssalcraft.AbyssalCraft;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional.Method;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDreadArmor extends ItemArmor implements IVisDiscountGear, IRevealer {
	public ItemDreadArmor(ArmorMaterial par2EnumArmorMaterial, int par3, int par4) {
		super(par2EnumArmorMaterial, par3, par4);
		setCreativeTab(AbyssalCraft.tabTools);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {
		return EnumChatFormatting.DARK_RED + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer) {
		if (stack.getItem() == AbyssalCraft.helmetD || stack.getItem() == AbyssalCraft.plateD || stack.getItem() == AbyssalCraft.bootsD)
			return "abyssalcraft:textures/armor/dread_1.png";

		if (stack.getItem() == AbyssalCraft.legsD)
			return "abyssalcraft:textures/armor/dread_2.png";
		else
			return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister) {
		itemIcon = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + this.getUnlocalizedName().substring(5));
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemstack) {
		int setEff = 0;
		boolean plateWorn = false;
		if (player.getCurrentArmor(3) != null && player.getCurrentArmor(3).getItem().equals(AbyssalCraft.helmetD)) {
			setEff++;
			player.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), 260, 0));
		}

		if (player.getCurrentArmor(2) != null && player.getCurrentArmor(2).getItem().equals(AbyssalCraft.plateD)) {
			setEff++;
			plateWorn = true;
		}

		if (player.getCurrentArmor(1) != null && player.getCurrentArmor(1).getItem().equals(AbyssalCraft.legsD)) {
			setEff++;
			if (plateWorn) {
				player.addPotionEffect(new PotionEffect(Potion.fireResistance.getId(), 20, 3));
			}
		}

		if (player.getCurrentArmor(0) != null && player.getCurrentArmor(0).getItem().equals(AbyssalCraft.bootsD)) {
			setEff++;
			player.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), 20, 0));
		}

		if (setEff >= 3) {
			List list = player.worldObj.getEntitiesWithinAABBExcludingEntity(player, player.boundingBox.expand(4D, 0.0D, 4D));
			if (list != null) {
				for (int k2 = 0; k2 < list.size(); k2++) {
					Entity entity = (Entity) list.get(k2);
					if (entity instanceof EntityLiving && !entity.isDead && !entity.isBurning()) {
						entity.setFire(99);
					} else if (entity instanceof EntityPlayer && !entity.isDead && !entity.isBurning()) {
						entity.setFire(99);
					}
				}
			}
		}
	}
	
	// Thaumcraft Bonuses
	@Override
	@Method(modid = "Thaumcraft")
	public int getVisDiscount(ItemStack stack, EntityPlayer player, Aspect aspect) {
		return (stack.getItem() == AbyssalCraft.dreadiumhelmet && aspect == Aspect.ORDER) ? 7 : 0;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List l, boolean B){
		if(Loader.isModLoaded("Thaumcraft")) {
			if(is.getItem() == AbyssalCraft.dreadiumhelmet) {
				l.add("\u00A75"+StatCollector.translateToLocal("tc.visdiscount")+" (Ordo) : 7%");
			}
		}
	}

	@Override
	@Method(modid = "Thaumcraft")
	public boolean showNodes(ItemStack itemstack, EntityLivingBase player) {
		return itemstack.getItem() == AbyssalCraft.dreadiumhelmet;
	}
}
