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

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
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

import cpw.mods.fml.common.Optional.Interface;
import cpw.mods.fml.common.Optional.InterfaceList;
import cpw.mods.fml.common.Optional.Method;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@InterfaceList(value = { @Interface(iface = "thaumcraft.api.IVisDiscountGear", modid = "Thaumcraft"), @Interface(iface = "thaumcraft.api.nodes.IRevealer", modid = "Thaumcraft") })
public class ItemDreadiumSamuraiArmor extends ItemArmor {
	public ItemDreadiumSamuraiArmor(ArmorMaterial par2EnumArmorMaterial, int par3, int par4) {
		super(par2EnumArmorMaterial, par3, par4);
		setCreativeTab(AbyssalCraft.tabTools);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return EnumChatFormatting.DARK_RED + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer) {
		if (stack.getItem() == AbyssalCraft.dreadiumShelmet || stack.getItem() == AbyssalCraft.dreadiumSplate || stack.getItem() == AbyssalCraft.dreadiumSboots)
			return "abyssalcraft:textures/armor/dreadiumS_1.png";

		if (stack.getItem() == AbyssalCraft.dreadiumSlegs)
			return "abyssalcraft:textures/armor/dreadiumS_2.png";
		else
			return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
		ModelBiped armorModel = new ModelBiped();
		if (itemStack != null) {
			if (itemStack.getItem() instanceof ItemDreadiumSamuraiArmor) {
				int type = ((ItemArmor) itemStack.getItem()).armorType;
				if (type == 1 || type == 3)
					armorModel = AbyssalCraft.proxy.getArmorModel(0);
				else
					armorModel = AbyssalCraft.proxy.getArmorModel(1);
			}
			if (armorModel != null) {
				armorModel.bipedHead.showModel = armorSlot == 0;
				armorModel.bipedHeadwear.showModel = armorSlot == 0;
				armorModel.bipedBody.showModel = armorSlot == 1 || armorSlot == 2;
				armorModel.bipedRightArm.showModel = armorSlot == 1;
				armorModel.bipedLeftArm.showModel = armorSlot == 1;
				armorModel.bipedRightLeg.showModel = armorSlot == 2 || armorSlot == 3;
				armorModel.bipedLeftLeg.showModel = armorSlot == 2 || armorSlot == 3;
				armorModel.isSneak = entityLiving.isSneaking();
				armorModel.isRiding = entityLiving.isRiding();
				armorModel.isChild = entityLiving.isChild();
				armorModel.heldItemRight = entityLiving.getEquipmentInSlot(0) != null ? 1 : 0;
				if (entityLiving instanceof EntityPlayer) {
					armorModel.aimedBow = ((EntityPlayer) entityLiving).getItemInUseDuration() > 2 && ((EntityPlayer) entityLiving).getItemInUse().getItem().getItemUseAction(((EntityPlayer) entityLiving).getItemInUse()) == EnumAction.bow;
					armorModel.heldItemRight = ((EntityPlayer) entityLiving).isBlocking() ? 3 : entityLiving.getEquipmentInSlot(0) != null ? 1 : 0;
				}
				return armorModel;
			}
		}
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister) {
		itemIcon = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + this.getUnlocalizedName().substring(5));
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemstack) {
		int setItems = 0;

		if (player.getCurrentArmor(3) != null && player.getCurrentArmor(3).getItem().equals(AbyssalCraft.dreadiumShelmet)) {
			setItems++;
		}

		if (player.getCurrentArmor(2) != null && player.getCurrentArmor(2).getItem().equals(AbyssalCraft.dreadiumSplate)) {
			setItems++;
			player.addPotionEffect(new PotionEffect(Potion.fireResistance.getId(), 20, 1));
		}

		if (player.getCurrentArmor(1) != null && player.getCurrentArmor(1).getItem().equals(AbyssalCraft.dreadiumSlegs)) {
			setItems++;
			if (world.getWorldTime() % 100 == 0) {
				player.addPotionEffect(new PotionEffect(Potion.damageBoost.getId(), 30));
			}
		}

		if (player.getCurrentArmor(0) != null && player.getCurrentArmor(0).getItem().equals(AbyssalCraft.dreadiumSboots)) {
			setItems++;
			player.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), 20, 1));
		}

		// Set effect
		if (setItems >= 3) {
			if (player.getActivePotionEffect(AbyssalCraft.Dplague) != null) {
				player.removePotionEffect(AbyssalCraft.Dplague.getId());
			}
		}

		// Clear potion effects
		if (player.getActivePotionEffect(Potion.damageBoost) != null && player.getActivePotionEffect(Potion.damageBoost).getDuration() == 0) {
			player.removePotionEffect(Potion.damageBoost.id);
		}

	}
	
	@Method(modid = "Thaumcraft")
	public boolean showNodes(ItemStack itemstack, EntityLivingBase player) {
		return itemstack.getItem() == AbyssalCraft.dreadiumShelmet;
	}
}
