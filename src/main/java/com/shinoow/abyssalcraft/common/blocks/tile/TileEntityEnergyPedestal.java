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
package com.shinoow.abyssalcraft.common.blocks.tile;

import java.util.Random;

import com.shinoow.abyssalcraft.api.energy.IEnergyContainer;
import com.shinoow.abyssalcraft.api.energy.IEnergyTransporter;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityEnergyPedestal extends TileEntity implements IEnergyContainer {

	private ItemStack item;
	private int rot;
	private float energy;
	Random rand = new Random();
	private boolean isDirty;
	
	protected int getTier() {
		return 1;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtData) {
		super.readFromNBT(nbtData);
		NBTTagCompound nbtItem = nbtData.getCompoundTag("Item");
		item = ItemStack.loadItemStackFromNBT(nbtItem);
		rot = nbtData.getInteger("Rot");
		energy = nbtData.getFloat("PotEnergy");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtData) {
		super.writeToNBT(nbtData);
		NBTTagCompound nbtItem = new NBTTagCompound();
		if(item != null)
			item.writeToNBT(nbtItem);
		nbtData.setTag("Item", nbtItem);
		nbtData.setInteger("Rot", rot);
		nbtData.setFloat("PotEnergy", energy);
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbtTag = new NBTTagCompound();
		writeToNBT(nbtTag);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbtTag);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		readFromNBT(packet.func_148857_g());
	}

	@Override
	public void updateEntity() {
		super.updateEntity();

		if(isDirty){
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			isDirty = false;
		}

		if(rot == 360)
			rot = 0;
		if(item != null)
			rot++;

		if(item != null) {
			if(item.getItem() instanceof IEnergyTransporter) {
				IEnergyTransporter energyItem = (IEnergyTransporter)item.getItem();
				int energySpace = (energyItem.getMaxEnergy(item) - (int)energyItem.getContainedEnergy(item));
				if(getContainedEnergy() > 0 && energySpace > 0) {
					int energyStored = (int)Math.max(10, getContainedEnergy());
					int transferAmount = Math.min(energyStored, energySpace);
					energyItem.addEnergy(item, transferAmount);
					consumeEnergy(transferAmount);
				}
			}
		}
	}

	public int getRotation() {
		return rot;
	}

	public ItemStack getItem(){
		return item;
	}

	public void setItem(ItemStack item){
		isDirty = true;
		this.item = item;
	}

	@Override
	public float getContainedEnergy() {
		return energy;
	}

	@Override
	public int getMaxEnergy() {
		switch(getTier()) {
			case 2: return 7500;
			case 3: return 10000;
			case 4: return 12500;
			case 5: return 15000;
			default: return 5000;
		}
	}

	@Override
	public void addEnergy(float energy) {
		switch(getTier()) {
			case 2: energy *= 1.2; break;
			case 3: energy *= 1.3; break;
			case 4: energy *= 1.6; break;
			case 5: energy *= 1.8; break;
		}
		this.energy += energy;
	}

	@Override
	public void consumeEnergy(float energy) {
		this.energy -= energy;
	}

	@Override
	public boolean canAcceptPE() {
		return (this.getContainedEnergy() < this.getMaxEnergy());
	}
}
