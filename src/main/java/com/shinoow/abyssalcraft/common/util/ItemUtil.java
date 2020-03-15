package com.shinoow.abyssalcraft.common.util;

import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemUtil {
	public static boolean isFood(ItemStack stack) {
		return (stack.getItem().getItemUseAction(stack) == EnumAction.eat);
	}
	
	// Check if food is dreaded
	public static boolean isDreadful(ItemStack stack) {
		return (stack.hasTagCompound() && stack.stackTagCompound.getBoolean("dreadplagued"));
	}
	
	// Turn food into dreadful food
	public static void dreadifyFood(ItemStack stack) {
		if (stack != null && ItemUtil.isFood(stack) && !ItemUtil.isDreadful(stack)) {
			if(!stack.hasTagCompound()) {
				stack.setTagCompound(new NBTTagCompound());
			}
			stack.stackTagCompound.setBoolean("dreadplagued", true);
			stack.setStackDisplayName("§4" + stack.getDisplayName() +"§f");	
		}
	}
}
