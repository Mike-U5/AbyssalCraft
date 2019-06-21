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
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
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
import cpw.mods.fml.common.Optional.Interface;
import cpw.mods.fml.common.Optional.InterfaceList;
import cpw.mods.fml.common.Optional.Method;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@InterfaceList(value = { @Interface(iface = "thaumcraft.api.IVisDiscountGear", modid = "Thaumcraft"),
		@Interface(iface = "thaumcraft.api.nodes.IRevealer", modid = "Thaumcraft")})
public class ItemDepthsArmor extends ItemArmor implements IVisDiscountGear, IRevealer {
	
	private final Item[] armorSet = new Item[] {
		AbyssalCraft.Depthshelmet,
		AbyssalCraft.Depthsplate,
		AbyssalCraft.Depthslegs,
		AbyssalCraft.Depthsboots
	};
	
	public ItemDepthsArmor(ArmorMaterial armorMaterial, int par3, int slotId) {
		super(armorMaterial, par3, slotId);
		setCreativeTab(AbyssalCraft.tabCombat);
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return EnumChatFormatting.DARK_RED + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer) {
		if(stack.getItem() == AbyssalCraft.Depthshelmet || stack.getItem() == AbyssalCraft.Depthsplate || stack.getItem() == AbyssalCraft.Depthsboots) {
			return "abyssalcraft:textures/armor/depths_1.png";
		}
			
		if(stack.getItem() == AbyssalCraft.Depthslegs) {
			return "abyssalcraft:textures/armor/depths_2.png";
		}
		
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister) {
		itemIcon = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + this.getUnlocalizedName().substring(5));
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {		
		// Applies to every piece
		if(player.isInWater()) {
			// Increased Breathe
			if (player.getAir() < 300 && player.ticksExisted % 16 == 0) {
				player.setAir(player.getAir() + 1);
			}
			// Increased Heal
			if (player.ticksExisted % 100 == 0) {
				player.heal(0.25F);
			}
			// Increased Swimming
			final double maxSpeed = 1.3;
			final double motionX = player.motionX * 1.045;
			final double motionY = player.motionY * 1.045;
			final double motionZ = player.motionZ * 1.045;
			final boolean flying = (player instanceof EntityPlayer && ((EntityPlayer) player).capabilities.isFlying);

			if(Math.abs(motionX) < maxSpeed && !flying) {
				player.motionX = motionX;
			}
			if(Math.abs(motionY) < maxSpeed && !flying) {
				player.motionY = motionY;
			}
			if(Math.abs(motionZ) < maxSpeed && !flying) {
				player.motionZ = motionZ;
			}
		}
		
		// Set Effect(s)
		int setPiecesWorn = 0;
		for (int i = 0; i < this.armorSet.length; i++) {
			if (player.getCurrentArmor(i) != null && player.getCurrentArmor(i).getItem().equals(this.armorSet[i])) {
				setPiecesWorn += 1;
			}
		}
		
		
		// Plague Resistance
		if (setPiecesWorn >= 3) {
			if(player.getActivePotionEffect(AbyssalCraft.Cplague) != null) {
				player.removePotionEffect(AbyssalCraft.Cplague.getId());
			}
		}
		
		// Damage Reductions
		if (setPiecesWorn >= 4) {
			if(player.getActivePotionEffect(Potion.resistance) == null || player.getActivePotionEffect(Potion.resistance).getDuration() == 0) {
				player.addPotionEffect(new PotionEffect(Potion.resistance.getId(), 2, 0));
			}
		}
	}

	@Override
	@Method(modid = "Thaumcraft")
	public int getVisDiscount(ItemStack stack, EntityPlayer player, Aspect aspect) {
		return stack.getItem() == AbyssalCraft.Depthshelmet ? 5 : stack.getItem() == AbyssalCraft.Depthsplate ? 2 :
			stack.getItem() == AbyssalCraft.Depthslegs ? 2 : stack.getItem() == AbyssalCraft.Depthsboots ? 1 : 0;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List l, boolean B){
		if(Loader.isModLoaded("Thaumcraft")){
			if(is.getItem() == AbyssalCraft.Depthshelmet)
				l.add("\u00A75"+StatCollector.translateToLocal("tc.visdiscount")+": 5%");
			if(is.getItem() == AbyssalCraft.Depthsplate)
				l.add("\u00A75"+StatCollector.translateToLocal("tc.visdiscount")+": 2%");
			if(is.getItem() == AbyssalCraft.Depthslegs)
				l.add("\u00A75"+StatCollector.translateToLocal("tc.visdiscount")+": 2%");
			if(is.getItem() == AbyssalCraft.Depthsboots)
				l.add("\u00A75"+StatCollector.translateToLocal("tc.visdiscount")+": 1%");
		}
	}

	@Override
	@Method(modid = "Thaumcraft")
	public boolean showNodes(ItemStack itemstack, EntityLivingBase player) {
		return itemstack.getItem() == AbyssalCraft.Depthshelmet;
	}
}
