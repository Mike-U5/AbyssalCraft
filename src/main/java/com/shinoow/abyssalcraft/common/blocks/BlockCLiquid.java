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

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.util.EntityUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;

public class BlockCLiquid extends BlockFluidClassic {

	public static final MaterialLiquid Cwater = new MaterialLiquid(MapColor.lightBlueColor);

	@SideOnly(Side.CLIENT)
	protected IIcon[] theIcon;

	public BlockCLiquid() {
		super(AbyssalCraft.CFluid, Material.water);
	}
	
	@Override
	public boolean displaceIfPossible(World world, int x, int y, int z) {
		if(!world.isRemote) {
			Block block = world.getBlock(x, y, z);
			if (block == Blocks.lava) {
				world.setBlock(x, y, z, AbyssalCraft.solidLava);
			} else if (block.getMaterial().isLiquid() && block != this) {
				world.setBlock(x, y, z, AbyssalCraft.abyssalsand);
			}
		}
		return super.displaceIfPossible(world, x, y, z);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		theIcon = new IIcon[]{iconRegister.registerIcon("abyssalcraft:cwater_still"), iconRegister.registerIcon("abyssalcraft:cwater_flow")};
		AbyssalCraft.CFluid.setStillIcon(theIcon[0]);
		AbyssalCraft.CFluid.setFlowingIcon(theIcon[1]);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return side != 0 && side != 1 ? theIcon[1] : theIcon[0];
	}

	@Override
	public MapColor getMapColor(int meta){
		return MapColor.lightBlueColor;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int par2, int par3, int par4, Entity entity) {
		super.onEntityCollidedWithBlock(world, par2, par3, par4, entity);

		if(entity instanceof EntityLivingBase && !EntityUtil.isEntityCoralium((EntityLivingBase)entity) && ((EntityLivingBase)entity).getActivePotionEffect(AbyssalCraft.Cplague) == null) {
			((EntityLivingBase)entity).addPotionEffect(new PotionEffect(AbyssalCraft.Cplague.id, 200));
		}
		if (entity instanceof EntityPlayer && entity.ticksExisted % 10 == 0) {
			EntityUtil.corruptWaterContainers((EntityPlayer)entity);
		}
	}
}
