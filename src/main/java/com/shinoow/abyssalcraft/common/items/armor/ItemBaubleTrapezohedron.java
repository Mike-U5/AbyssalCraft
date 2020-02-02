// 
// Decompiled by Procyon v0.5.30
// 

package com.shinoow.abyssalcraft.common.items.armor;

import java.util.List;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import thaumcraft.api.IRunicArmor;

public class ItemBaubleTrapezohedron extends Item implements IBauble, IRunicArmor {
	public IIcon icon;

	public ItemBaubleTrapezohedron() {
		super();
		setMaxStackSize(1);
	}

	public BaubleType getBaubleType(final ItemStack itemstack) {
		return BaubleType.AMULET;
	}

	public int getRunicCharge(final ItemStack itemstack) {
		return 5;
	}

	@SuppressWarnings("rawtypes")
	@SideOnly(Side.CLIENT)
	public void addInformation(final ItemStack item, final EntityPlayer player, final List list, final boolean bool) {
		super.addInformation(item, player, list, bool);
	}

	@Override
	public boolean canEquip(ItemStack arg0, EntityLivingBase arg1) {
		return true;
	}

	@Override
	public boolean canUnequip(ItemStack arg0, EntityLivingBase arg1) {
		return true;
	}

	@Override
	public void onEquipped(ItemStack arg0, EntityLivingBase arg1) {
		// Does nothing special.
	}

	@Override
	public void onUnequipped(ItemStack arg0, EntityLivingBase arg1) {
		// Does nothing special.
	}

	@Override
	public void onWornTick(ItemStack arg0, EntityLivingBase arg1) {
		// Does nothing special.
	}
}
