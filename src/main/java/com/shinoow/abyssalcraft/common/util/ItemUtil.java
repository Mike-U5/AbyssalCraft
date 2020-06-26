package com.shinoow.abyssalcraft.common.util;

import java.util.Arrays;

import com.shinoow.abyssalcraft.AbyssalCraft;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemUtil {
	public static Item[] dreadables = new Item[] {
		Items.wheat,
		Items.egg,
		Items.sugar,
		Item.getItemFromBlock(Blocks.brown_mushroom_block),
		Item.getItemFromBlock(Blocks.red_mushroom_block),
		Item.getItemFromBlock(Blocks.brown_mushroom),
		Item.getItemFromBlock(Blocks.red_mushroom_block)
	}; 
	
	public static boolean isConsumable(ItemStack stack) {
		final EnumAction act = stack.getItem().getItemUseAction(stack);
		return (act == EnumAction.eat || act == EnumAction.drink);
	}
	
	// Check if food is dreaded
	public static boolean isDreadful(ItemStack stack) {
		return (stack.hasTagCompound() && stack.stackTagCompound.getBoolean("dreadplagued"));
	}
	
	public static boolean canBeDreadified(ItemStack stack) {
		if (stack != null && !ItemUtil.isDreadful(stack)) {
			final Item item = stack.getItem();
			return (ItemUtil.isConsumable(stack) || Arrays.asList(ItemUtil.dreadables).contains(item));
		}
		return false;
	}
	
	// Turn food into dreadful food
	public static void dreadifyFood(ItemStack stack) {
		if (ItemUtil.canBeDreadified(stack)) {
			if(!stack.hasTagCompound()) {
				stack.setTagCompound(new NBTTagCompound());
			}
			stack.stackTagCompound.setBoolean("dreadplagued", true);
			stack.setStackDisplayName("§4" + stack.getDisplayName() +"§f");	
		}
	}
	
	public static ItemStack makeNameTagWithName(String name) {
		final ItemStack nameTagStack = new ItemStack(Items.name_tag);
		final NBTTagCompound nbtStack = new NBTTagCompound();
		final NBTTagCompound nbtName = new NBTTagCompound();
		nbtName.setString("Name", name);
		nbtStack.setTag("display", nbtName);
		nameTagStack.setTagCompound(nbtStack);
		
		return nameTagStack;
	}
	
	public static ItemStack makeMementoWithName(String name) {
		final ItemStack nameTagStack = new ItemStack(AbyssalCraft.memento);
		final NBTTagCompound nbtStack = new NBTTagCompound();
		final NBTTagCompound nbtName = new NBTTagCompound();
		nbtName.setString("Name", name);
		nbtStack.setTag("display", nbtName);
		nameTagStack.setTagCompound(nbtStack);
		
		return nameTagStack;
	}
}
