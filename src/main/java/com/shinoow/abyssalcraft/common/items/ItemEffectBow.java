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

import com.shinoow.abyssalcraft.AbyssalCraft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;

public abstract class ItemEffectBow extends ItemBow {

	public float charge;

	public int anim_0;
	public int anim_1;
	public int anim_2;
	public String[] bowPullIconNameArray;
	@SideOnly(Side.CLIENT)
	protected IIcon[] iconArray;
	/**
	 * @param texture is String of item texture, ie itemName.png
	 * 		also sets the UnlocalizedName to avoid render issues
	 * @param texture_0 is String of item animation texture 0, ie itemName_0.png
	 * @param texture_1 is String of item animation texture 1, ie itemName_1.png
	 * @param texture_2 is String of item animation texture 2, ie itemName_2.png
	 * @param chargeTime is Float of how fast the bow charges, ie 21.0F where 20.0F is
	 * the default bow speed
	 * @param anim_0 is used for syncing charge time with pull_0 animation,
	 *  recommended left at 0
	 * @param anim_1 is used for syncing charge time with pull_1 animation, ie 9 where
	 * 8 is default bow
	 * @param anim_2 is used for syncing charge time with pull_2 animation, ie 17 where
	 * 16 is default bow
	 * 
	 * Notes: adjust anim_0-2 whenever chargeTime is changed for smoother animation flow
	 */
	public ItemEffectBow(float chargeTime, int anim_0, int anim_1, int anim_2) {
		maxStackSize = 1;
		setCreativeTab(AbyssalCraft.tabItems);

		charge = chargeTime;

		this.anim_0 = anim_0;
		this.anim_1 = anim_1;
		this.anim_2 = anim_2;
	}
	
	protected EntityArrow getFiredArrow(EntityPlayer player, World world, float f) {
		return null;
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {
		return EnumChatFormatting.AQUA + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
	}

	public void getBowPullIconNameArray(){
		bowPullIconNameArray = new String[] {"pulling_0", "pulling_1", "pulling_2" };
	}

	/**
	 * called when the player releases the use item button. Args: itemstack, world, entityplayer, itemInUseCount
	 */
	@Override
	public void onPlayerStoppedUsing(ItemStack tool, World world, EntityPlayer player, int par4) {
		int j = getMaxItemUseDuration(tool) - par4;

		ArrowLooseEvent event = new ArrowLooseEvent(player, tool, j);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled()) {
			return;
		}
		j = event.charge;

		boolean flag = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, tool) > 0;

		if (flag || player.inventory.hasItem(Items.arrow)) {
			float f = j / charge;
			f = (f * f + f * 2.0F) / 3.0F;

			if (f < 0.1D) {
				return;
			}

			if (f > 1.0F) {
				f = 1.0F;
			}
			
			final int powerLvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, tool);
			final int punchLvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, tool);
			final int flameLvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, tool);

			EntityArrow entityarrow = getFiredArrow(player, world, f);

			if (f == 1.0F) {
				entityarrow.setIsCritical(true);
			}
			
			if (powerLvl > 0) {
				entityarrow.setDamage(entityarrow.getDamage() + powerLvl * 0.5D + 0.5D);
			}

			
			if (punchLvl > 0) {
				entityarrow.setKnockbackStrength(punchLvl);
			}

			if (flameLvl > 0) {
				entityarrow.setFire(100);
			}

			tool.damageItem(1, player);
			world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

			if (flag) {
				entityarrow.canBePickedUp = 2;
			} else {
				player.inventory.consumeInventoryItem(Items.arrow);
			}

			if (!world.isRemote) {
				world.spawnEntityInWorld(entityarrow);
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		getBowPullIconNameArray();
		itemIcon = iconRegister.registerIcon(AbyssalCraft.modid + ":" + getUnlocalizedName().substring(5));
		iconArray = new IIcon[bowPullIconNameArray.length];

		for (int i = 0; i < iconArray.length; ++i) {
			iconArray[i] = iconRegister.registerIcon(AbyssalCraft.modid + ":" + getUnlocalizedName().substring(5) + "_" + bowPullIconNameArray[i]);
		}
	}

	/**
	 * used to cycle through icons based on their used duration, i.e. for the bow
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
		if(player.getItemInUse() == null) return itemIcon;
		int Pulling = stack.getMaxItemUseDuration() - useRemaining;
		if (Pulling >= anim_2) {
			return iconArray[2];
		} else if (Pulling > anim_1) {
			return iconArray[1];
		} else if (Pulling > anim_0) {
			return iconArray[0];
		}
		return itemIcon;
	}
}
