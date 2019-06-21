/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2016 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser private License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;

import cpw.mods.fml.client.FMLClientHandler;

public class ItemPortalPlacer extends Item {
	
	private final Block[] portalEdges = {AbyssalCraft.abystone, AbyssalCraft.dreadstone, AbyssalCraft.omotholstone};
	private final Block[] portalIgniters = {AbyssalCraft.Coraliumfire, AbyssalCraft.dreadfire, AbyssalCraft.omotholfire};
	private final Block[] portals = {AbyssalCraft.portal, AbyssalCraft.dreadportal, AbyssalCraft.omotholportal};

	public ItemPortalPlacer(){
		super();
		maxStackSize = 1;
		setCreativeTab(AbyssalCraft.tabTools);
	}
	
	protected int getKeyTier() {
		return 0;
	}

	@Override
	public boolean isFull3D() {
		return true;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addInformation(ItemStack world, EntityPlayer entityplayer, List list, boolean is){
		list.add(StatCollector.translateToLocal("tooltip.portalplacer.1"));
		list.add(StatCollector.translateToLocal("tooltip.portalplacer.2"));
	}
	
	protected boolean makePortal(boolean isFlipped, int type, World world, int parX, int parY, int parZ) {
		if(isFlipped) {
			for(int y = 1; y <= 5; y++) {
				for (int z = -1; z <= 3; z++) {
					if(world.getBlock(parX, parY + y, parZ + z) != Blocks.air) {
						return false;
					}
				}
			}

			world.setBlock(parX, parY + 1, parZ + 0, portalEdges[type]);
			world.setBlock(parX, parY + 1, parZ + 1, portalEdges[type]);
			world.setBlock(parX, parY + 1, parZ + 2, portalEdges[type]);
			world.setBlock(parX, parY + 1, parZ - 1, portalEdges[type]);

			world.setBlock(parX, parY + 2, parZ - 1, portalEdges[type]);
			world.setBlock(parX, parY + 3, parZ - 1, portalEdges[type]);
			world.setBlock(parX, parY + 4, parZ - 1, portalEdges[type]);
			world.setBlock(parX, parY + 5, parZ - 1, portalEdges[type]);

			world.setBlock(parX, parY + 2, parZ + 2, portalEdges[type]);
			world.setBlock(parX, parY + 3, parZ + 2, portalEdges[type]);
			world.setBlock(parX, parY + 4, parZ + 2, portalEdges[type]);
			world.setBlock(parX, parY + 5, parZ + 2, portalEdges[type]);

			world.setBlock(parX, parY + 5, parZ, portalEdges[type]);
			world.setBlock(parX, parY + 5, parZ + 1, portalEdges[type]);

			world.setBlock(parX, parY + 2, parZ + 1, portalIgniters[type]);
		} else {
			for(int y = 1; y <= 5; y++) {
				for (int x = -1; x <= 3; x++) {
					if(world.getBlock(parX + x, parY + y, parZ) != Blocks.air) {
						return false;
					}
				}
			}

			world.setBlock(parX + 0, parY + 1, parZ, portalEdges[type]);
			world.setBlock(parX + 1, parY + 1, parZ, portalEdges[type]);
			world.setBlock(parX + 2, parY + 1, parZ, portalEdges[type]);
			world.setBlock(parX - 1, parY + 1, parZ, portalEdges[type]);

			world.setBlock(parX - 1, parY + 2, parZ, portalEdges[type]);
			world.setBlock(parX - 1, parY + 3, parZ, portalEdges[type]);
			world.setBlock(parX - 1, parY + 4, parZ, portalEdges[type]);
			world.setBlock(parX - 1, parY + 5, parZ, portalEdges[type]);

			world.setBlock(parX + 2, parY + 2, parZ, portalEdges[type]);
			world.setBlock(parX + 2, parY + 3, parZ, portalEdges[type]);
			world.setBlock(parX + 2, parY + 4, parZ, portalEdges[type]);
			world.setBlock(parX + 2, parY + 5, parZ, portalEdges[type]);

			world.setBlock(parX + 0, parY + 5, parZ, portalEdges[type]);
			world.setBlock(parX + 1, parY + 5, parZ, portalEdges[type]);
			
			world.setBlock(parX + 1, parY + 2, parZ, portalIgniters[type]);
		}
		return true;
	}
	
	private int blockIsPortal(Block block) {
		for (int i = 0; i < portals.length; i++) {
			if (block == portals[i]) {
				return i;
			}
		}
		return -1;
	}
	
	private int blockIsEdge(Block block) {
		for (int i = 0; i < portalEdges.length; i++) {
			if (block == portalEdges[i]) {
				return i;
			}
		}
		return -1;
	}
	
	private void replacePortal(World world, int dim, int parX, int parY, int parZ, int currentType) {
		// Fetch the type we want to transform into
		int newType = -1;
		if (dim == AbyssalCraft.configDimId1) {
			newType = (currentType == 0) ? 1 : 0;
		}
		if (dim == AbyssalCraft.configDimId2) {
			newType = (currentType == 1) ? 2 : 1;
		}
		if (dim == AbyssalCraft.configDimId3) {
			newType = (currentType == 2) ? 1 : 2;
		}
		
		// Check if the new destination is actually legit
		if (newType < 0 || currentType == newType || getKeyTier() < newType) {
			FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.portalplacer.error.2")));
			return;
		}
		
		// Replace the portal itself
		if (newType >= 0) {
			//Fetch the bottom of the portal
			if (blockIsPortal(world.getBlock(parX, parY -1, parZ)) >= 0) {
				parY--;
			}
			if (blockIsPortal(world.getBlock(parX, parY -1, parZ)) >= 0) {
				parY--;
			}
		}
		
		// Set new edges
		for (int iX = -2; iX <= 2; iX++) {
			for (int iY = -1; iY <= 3; iY++) {
				if (blockIsEdge(world.getBlock(parX + iX, parY + iY, parZ)) >= 0) {
					world.setBlock(parX + iX, parY + iY, parZ, portalEdges[newType]);
				}
			}
		}
		for (int iZ = -2; iZ <= 2; iZ++) {
			for (int iY = -1; iY <= 3; iY++) {
				if (blockIsEdge(world.getBlock(parX, parY + iY, parZ + iZ)) >= 0) {
					world.setBlock(parX, parY + iY, parZ + iZ, portalEdges[newType]);
				}
			}
		}
		
		// Set new fire
		world.setBlock(parX, parY, parZ, portalIgniters[newType]);
	}
	
	public boolean portalAction(ItemStack usedItem, EntityPlayer player, World world, int parX, int parY, int parZ, int targetSide, float targetX, float targetY, float targetZ) {
		if(!world.isRemote) {	
			// Replace Portal
			int clickedPortal = blockIsPortal(world.getBlock(parX, parY, parZ)); 
			if (clickedPortal >= 0) {
				replacePortal(world, player.dimension, parX, parY, parZ, clickedPortal);
				return true;
			}
			
			// Check which portal we want
			int portalTier = -1;
			if (getKeyTier() >= 2 && (player.dimension == AbyssalCraft.configDimId2 ||player.dimension == AbyssalCraft.configDimId3 || player.dimension == AbyssalCraft.configDimId4)) {
				portalTier = 2;
			} else if (getKeyTier() >= 1 && (player.dimension == AbyssalCraft.configDimId1 || player.dimension == AbyssalCraft.configDimId2 || player.dimension == AbyssalCraft.configDimId3 || player.dimension == AbyssalCraft.configDimId4)) {
				portalTier = 1;
			} else if (getKeyTier() >= 0 && (player.dimension == 0 || player.dimension == AbyssalCraft.configDimId1 || player.dimension == AbyssalCraft.configDimId2 || player.dimension == AbyssalCraft.configDimId3 || player.dimension == AbyssalCraft.configDimId4)) {
				portalTier = 0;
			}
			
			// Create Portal
			if (portalTier >= 0) {
				int direction = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
				boolean isFlipped = (direction == 1 || direction == 3);
				return makePortal(isFlipped, portalTier, world, parX, parY, parZ);
			} else {
				FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.portalplacer.error.1")));
			}
		}
		return false;
	}

	@Override
	public boolean onItemUse(ItemStack usedItem, EntityPlayer player, World world, int parX, int parY, int parZ, int targetSide, float targetX, float targetY, float targetZ) {
		return portalAction(usedItem, player, world, parX, parY, parZ, targetSide, targetX, targetY, targetZ);
	}
}
