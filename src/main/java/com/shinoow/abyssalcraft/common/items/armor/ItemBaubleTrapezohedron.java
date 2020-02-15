// 
// Decompiled by Procyon v0.5.30
// 

package com.shinoow.abyssalcraft.common.items.armor;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
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

	// Rightclick to equip
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if(canEquip(stack, player)) {
			final IInventory baubles = BaublesApi.getBaubles(player);
			for(int i = 0; i < baubles.getSizeInventory(); i++) {
				if(baubles.isItemValidForSlot(i, stack)) {
					final ItemStack stackInSlot = baubles.getStackInSlot(i);
					if(stackInSlot == null && !world.isRemote) {
						baubles.setInventorySlotContents(i, stack.copy());
						player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
						break;
					}
				}
			}
		}

		return stack;
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
