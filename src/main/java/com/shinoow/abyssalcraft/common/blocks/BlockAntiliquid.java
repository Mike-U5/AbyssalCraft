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
package com.shinoow.abyssalcraft.common.blocks;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidFinite;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.util.EntityUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockAntiliquid extends BlockFluidFinite {

	public static final MaterialLiquid antimatter = new MaterialLiquid(MapColor.silverColor);

	@SideOnly(Side.CLIENT)
	protected IIcon[] theIcon;

	public BlockAntiliquid() {
		super(AbyssalCraft.antifluid, Material.water);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		theIcon = new IIcon[]{iconRegister.registerIcon("abyssalcraft:anti_still"), iconRegister.registerIcon("abyssalcraft:anti_flow")};
		AbyssalCraft.antifluid.setStillIcon(theIcon[0]);
		AbyssalCraft.antifluid.setFlowingIcon(theIcon[1]);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return side != 0 && side != 1 ? theIcon[1] : theIcon[0];
	}

	@Override
	public MapColor getMapColor(int meta){
		return MapColor.silverColor;
	}

	@Override
	public boolean canDisplace(IBlockAccess world, int x, int y, int z) {
		if(world.getBlock(x, y, z) == AbyssalCraft.Cwater || world.getBlock(x, y, z).getMaterial() == Material.water && world.getBlock(x, y, z) != AbyssalCraft.Cwater && world.getBlock(x, y, z) != this || world.getBlock(x, y, z).getMaterial() == Material.lava) {
			return true;
		}
		return super.canDisplace(world, x, y, z);
	}

	@Override
	public boolean displaceIfPossible(World world, int x, int y, int z) {

		if(!world.isRemote && world.getBlock(x, y, z) == AbyssalCraft.Cwater) {
			world.setBlock(x, y, z, AbyssalCraft.cstone);
		}

		if(!world.isRemote && world.getBlock(x, y, z).getMaterial() == Material.water && world.getBlock(x, y, z) != AbyssalCraft.Cwater && world.getBlock(x, y, z) != this) {
			world.setBlock(x, y, z, Blocks.packed_ice);
		}

		if(!world.isRemote && world.getBlock(x, y, z).getMaterial() == Material.lava) {
			world.setBlock(x, y, z, Blocks.obsidian);
		}

		return super.displaceIfPossible(world, x, y, z);
	}

	@Override
	public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity entity) {
		super.onEntityCollidedWithBlock(par1World, par2, par3, par4, entity);

		if(entity instanceof EntityLivingBase && !EntityUtil.isEntityAnti((EntityLivingBase)entity)){
			EntityLivingBase livingEntity = (EntityLivingBase)entity;
			livingEntity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 400));
			livingEntity.addPotionEffect(new PotionEffect(Potion.weakness.id, 400));
			livingEntity.addPotionEffect(new PotionEffect(Potion.hunger.id, 400));
			livingEntity.addPotionEffect(new PotionEffect(Potion.nightVision.id, 400));
			livingEntity.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 400));
			if(livingEntity.getActivePotionEffect(AbyssalCraft.antiMatter) == null || livingEntity.getActivePotionEffect(AbyssalCraft.antiMatter).getDuration() < 196) {
				livingEntity.addPotionEffect(new PotionEffect(AbyssalCraft.antiMatter.id, 200));
			}
		}
		if(entity instanceof EntityItem && AbyssalCraft.antiItemDisintegration) {
			entity.setDead();
		}
	}
}
