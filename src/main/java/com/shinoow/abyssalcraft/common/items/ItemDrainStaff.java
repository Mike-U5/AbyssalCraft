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

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.entity.ICoraliumEntity;
import com.shinoow.abyssalcraft.api.entity.IDreadEntity;
import com.shinoow.abyssalcraft.api.entity.IOmotholEntity;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemDrainStaff extends Item {

	public ItemDrainStaff() {
		super();
		setUnlocalizedName("drainStaff");
		setCreativeTab(AbyssalCraft.tabTools);
		setTextureName("abyssalcraft:DrainStaff");
		setMaxStackSize(1);
	}

	@Override
	public boolean isFull3D() {
		return true;
	}

	public void increaseEnergy(ItemStack stack, String type, int amount) {
		if(!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		stack.stackTagCompound.setInteger("energy"+type, getEnergy(stack, type) + amount);
	}

	public void setEnergy(int amount, ItemStack stack, String type) {
		stack.stackTagCompound.setInteger("energy"+type, amount);
	}

	public int getEnergy(ItemStack stack, String type) {
		return stack.hasTagCompound() && stack.stackTagCompound.hasKey("energy"+type) ? (int)stack.stackTagCompound.getInteger("energy"+type) : 0;
	}
	
	@SuppressWarnings("unchecked")
	protected void drain(ItemStack stack, World world, EntityPlayer player, int drainPower) {
		world.playSoundAtEntity(player, "mob.silverfish.say", 0.4F, 2.5F);
		
		// Give essence
		if(getEnergy(stack, "Abyssal") >= 100) {
			world.playSoundAtEntity(player, "random.pop", 2.0F, 0.5F);
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.essence, 1, 0));
			setEnergy(0, stack, "Abyssal");
		}
		if(getEnergy(stack, "Dread") >= 100) {
			world.playSoundAtEntity(player, "random.pop", 2.0F, 0.5F);
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.essence, 1, 1));
			setEnergy(0, stack, "Dread");
		}
		if(getEnergy(stack, "Omothol") >= 100) {
			world.playSoundAtEntity(player, "random.pop", 2.0F, 0.5F);
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.essence, 1, 2));
			setEnergy(0, stack, "Omothol");
		}
		
		// Drain life
		final Vec3 vec = player.getLookVec().normalize();
		for(int i = 1; i < 32; i++) {
			// Make the cone thicker at close range
			double cone = 1.2 - (i*0.03);
			double pXa = player.posX - cone;
			double pYa = player.posY - cone + player.getEyeHeight();
			double pZa = player.posZ - cone;
			double pXb = player.posX + cone;
			double pYb = player.posY + cone + player.getEyeHeight();
			double pZb = player.posZ + cone;
			final AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(pXa + vec.xCoord * i, pYa + vec.yCoord * i, pZa + vec.zCoord * i, pXb + vec.xCoord * i, pYb + vec.yCoord * i, pZb + vec.zCoord * i);
			final List<EntityLiving> list = world.getEntitiesWithinAABB(EntityLiving.class, aabb);
			if(list.iterator().hasNext()) {
				for (int j = 0; j < list.size(); j++) {
					// Drain Energy
					final EntityLiving target = list.get(j);
					if (target != null && !target.isDead && !(target instanceof IBossDisplayData)) {
						final DamageSource dmgSrc = new EntityDamageSource("vajra", player).setDamageBypassesArmor().setDamageIsAbsolute();
						final int trueDmg = (int)Math.min(Math.floor(target.getHealth()), drainPower);
						// Find drain type
						if(world.provider.dimensionId == AbyssalCraft.configDimId1 && target instanceof ICoraliumEntity) {
							if(target.attackEntityFrom(dmgSrc, trueDmg)) {
								target.setLastAttacker(player);
								increaseEnergy(stack, "Abyssal", trueDmg);
								return;
							}
						} else if(world.provider.dimensionId == AbyssalCraft.configDimId2 && target instanceof IDreadEntity) {
							if(target.attackEntityFrom(dmgSrc, trueDmg)) {
								target.setLastAttacker(player);
								increaseEnergy(stack, "Dread", trueDmg);
								return;
							}
						} else if((world.provider.dimensionId == AbyssalCraft.configDimId3 && target instanceof IOmotholEntity) || EntityList.getEntityString(target).equals("w_angels.EntityWeepingAngel")) {
							if(target.attackEntityFrom(dmgSrc, trueDmg)) {
								target.setLastAttacker(player);
								increaseEnergy(stack, "Omothol", trueDmg);
								return;
							}
						}
					}
				}
			}
		}
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	private EntityLiving getLookingTarget(EntityPlayer player, World world) {
		final Vec3 vec = player.getLookVec().normalize();
		for(int i = 0; i <= 30; i++) {
			// Make the cone thicker at close range
			double cone = 1.15 - (i*0.03);
			double pXa = player.posX - cone;
			double pYa = player.posY - cone + player.getEyeHeight();
			double pZa = player.posZ - cone;
			double pXb = player.posX + cone;
			double pYb = player.posY + cone + player.getEyeHeight();
			double pZb = player.posZ + cone;
			AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(pXa + vec.xCoord * i, pYa + vec.yCoord * i, pZa + vec.zCoord * i, pXb + vec.xCoord * i, pYb + vec.yCoord * i, pZb + vec.zCoord * i);
			List<EntityLiving> list = world.getEntitiesWithinAABB(EntityLiving.class, aabb);
			if(list.iterator().hasNext()) {
				return list.get(0);
			}
		}
		return null;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {		
		if (!player.isSneaking()) {
			drain(stack, world, player, 1);
		}
		return stack;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addInformation(ItemStack is, EntityPlayer player, List l, boolean B) {
		final int aE = getEnergy(is, "Abyssal");
		final int dE = getEnergy(is, "Dread");
		final int oE = getEnergy(is, "Omothol");
		l.add(EnumChatFormatting.DARK_GREEN + StatCollector.translateToLocal("tooltip.drainstaff.energy.1")+": " + EnumChatFormatting.WHITE + aE + "%");
		l.add(EnumChatFormatting.DARK_RED + StatCollector.translateToLocal("tooltip.drainstaff.energy.2")+": " + EnumChatFormatting.WHITE + dE + "%");
		l.add(EnumChatFormatting.DARK_AQUA + StatCollector.translateToLocal("tooltip.drainstaff.energy.3")+": " + EnumChatFormatting.WHITE + oE + "%");
	}
}