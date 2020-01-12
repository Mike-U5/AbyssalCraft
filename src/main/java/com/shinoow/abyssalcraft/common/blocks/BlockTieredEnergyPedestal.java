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

import java.util.List;
import java.util.Random;

import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityTieredEnergyPedestal;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class BlockTieredEnergyPedestal extends BlockEnergyPedestal {

	public BlockTieredEnergyPedestal() {
		super();
		setBlockName("tieredEnergyPedestal");
		setBlockTextureName("anvil_top_damaged_0");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List subBlocks) {
		subBlocks.add(new ItemStack(par1, 1, 0));
		subBlocks.add(new ItemStack(par1, 1, 1));
		subBlocks.add(new ItemStack(par1, 1, 2));
		subBlocks.add(new ItemStack(par1, 1, 3));
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityTieredEnergyPedestal();
	}

	@Override
	public boolean isOpaqueCube(){
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float hitX, float hitY, float hitZ) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile != null && tile instanceof TileEntityTieredEnergyPedestal) {
			TileEntityTieredEnergyPedestal pedestal = (TileEntityTieredEnergyPedestal)tile; 
			if (player.isSneaking() && player.getHeldItem() == null) {
				if (!player.worldObj.isRemote) {
					String message = "PE: " + (int)pedestal.getContainedEnergy() + "/" + pedestal.getMaxEnergy();
		            player.addChatMessage(new ChatComponentText(message));
				}
				return false;
			}
			if(((TileEntityTieredEnergyPedestal)tile).getItem() != null) {
				player.inventory.addItemStackToInventory(((TileEntityTieredEnergyPedestal)tile).getItem());
				((TileEntityTieredEnergyPedestal)tile).setItem(null);
				world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, "random.pop", 0.5F, world.rand.nextFloat() - world.rand.nextFloat() * 0.2F + 1);
				return true;
			} else {
				ItemStack heldItem = player.getHeldItem();
				if(heldItem != null){
					ItemStack newItem = heldItem.copy();
					newItem.stackSize = 1;
					((TileEntityTieredEnergyPedestal)tile).setItem(newItem);
					player.inventory.decrStackSize(player.inventory.currentItem, 1);
					world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, "random.pop", 0.5F, world.rand.nextFloat() - world.rand.nextFloat() * 0.2F + 1);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		Random rand = new Random();
		TileEntityTieredEnergyPedestal pedestal = (TileEntityTieredEnergyPedestal) world.getTileEntity(x, y, z);

		if(pedestal != null)
			if(pedestal.getItem() != null) {
				float f = rand.nextFloat() * 0.8F + 0.1F;
				float f1 = rand.nextFloat() * 0.8F + 0.1F;
				float f2 = rand.nextFloat() * 0.8F + 0.1F;

				EntityItem item = new EntityItem(world, x + f, y + f1, z + f2, pedestal.getItem());
				float f3 = 0.05F;
				item.motionX = (float)rand.nextGaussian() * f3;
				item.motionY = (float)rand.nextGaussian() * f3 + 0.2F;
				item.motionZ = (float)rand.nextGaussian() * f3;
				world.spawnEntityInWorld(item);
			}

		super.breakBlock(world, x, y, z, block, meta);
	}
}
