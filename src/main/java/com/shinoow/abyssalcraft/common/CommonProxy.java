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
package com.shinoow.abyssalcraft.common;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.client.gui.GuiEngraver;
import com.shinoow.abyssalcraft.client.gui.necronomicon.GuiNecronomiconInformation;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityEngraver;
import com.shinoow.abyssalcraft.common.inventory.ContainerEngraver;
import com.shinoow.abyssalcraft.common.items.ItemNecronomicon;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class CommonProxy implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getTileEntity(x, y, z);
		if(entity != null) {
			switch(ID) {
			case AbyssalCraft.engraverGuiID:
				if (entity instanceof TileEntityEngraver)
					return new ContainerEngraver(player.inventory, (TileEntityEngraver) entity);
				break;
			}
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getTileEntity(x, y, z);
		ItemStack stack = player.getCurrentEquippedItem();

		if(entity != null)
			switch(ID) {
			case AbyssalCraft.engraverGuiID:
				if (entity instanceof TileEntityEngraver)
					return new GuiEngraver(player.inventory, (TileEntityEngraver) entity);
				break;
			}
		if(stack != null)
			switch(ID){
			case AbyssalCraft.necronmiconGuiID:
				if(stack.getItem() instanceof ItemNecronomicon)
					///return new GuiNecronomicon(((ItemNecronomicon)stack.getItem()).getBookType());
					return new GuiNecronomiconInformation(((ItemNecronomicon)stack.getItem()).getBookType());
				break;
			}
		return null;
	}

	public void registerRenderThings() {
	}

	public void preInit() {
	}

	public void init() {
	}

	public void postInit() {
	}

	public int addArmor(String armor) {
		return 0;
	}

	public ModelBiped getArmorModel(int id){
		return null;
	}

	/**
	 * Returns a side-appropriate EntityPlayer for use during message handling
	 */
	public EntityPlayer getPlayerEntity(MessageContext ctx) {
		return ctx.getServerHandler().playerEntity;
	}
}
