/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2017 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.blocks;

import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.lib.ACTabs;

public class BlockWastelandsThorn extends BlockBush {

	public BlockWastelandsThorn(){
		super();
		setUnlocalizedName("wastelandsthorn");
		setCreativeTab(ACTabs.tabDecoration);
	}

	@Override
	protected boolean func_185514_i(IBlockState ground)
	{
		return ground.getBlock() == ACBlocks.fused_abyssal_sand || ground.getBlock() == ACBlocks.abyssal_sand ||
				ground.getMaterial() == Material.grass || super.func_185514_i(ground);
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	{
		if(entityIn instanceof EntityPlayer && ((EntityPlayer)entityIn).getItemStackFromSlot(EntityEquipmentSlot.FEET) == null &&
				((EntityPlayer)entityIn).getItemStackFromSlot(EntityEquipmentSlot.LEGS) == null)
			entityIn.attackEntityFrom(DamageSource.cactus, 1.0F);
	}
}
