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

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import com.shinoow.abyssalcraft.api.energy.IEnergyContainer;
import com.shinoow.abyssalcraft.api.energy.IEnergyTransporter;
import com.shinoow.abyssalcraft.common.util.ISingletonInventory;

public class TileEntityEnergyPedestal extends TileEntity implements IEnergyContainer {

	private ItemStack item;
	private int rot;
	private float energy;
	Random rand = new Random();
	private boolean isDirty;
	private int particleTimer = 0;

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		NBTTagCompound nbtItem = nbttagcompound.getCompoundTag("Item");
		item = ItemStack.loadItemStackFromNBT(nbtItem);
		rot = nbttagcompound.getInteger("Rot");
		energy = nbttagcompound.getFloat("PotEnergy");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		NBTTagCompound nbtItem = new NBTTagCompound();
		if(item != null)
			item.writeToNBT(nbtItem);
		nbttagcompound.setTag("Item", nbtItem);
		nbttagcompound.setInteger("Rot", rot);
		nbttagcompound.setFloat("PotEnergy", energy);
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
			if(item.getItem() instanceof IEnergyTransporter)
				if(getContainedEnergy() > 0 && ((IEnergyTransporter) item.getItem()).getContainedEnergy(item) < ((IEnergyTransporter) item.getItem()).getMaxEnergy(item)){
					((IEnergyTransporter) item.getItem()).addEnergy(item, 1);
					consumeEnergy(1);
				}
		}
		
		particleTimer++;
		int particleRate = Math.round(getContainedEnergy()/getMaxEnergy())*40;
		if (getContainedEnergy() >= 1 && particleTimer > (40 - particleRate)) {
			worldObj.spawnParticle("reddust", xCoord + 0.5, yCoord + 0.95, zCoord + 0.5, 0, 0, 0);
			particleTimer = 0;
		}
	}

	public int getRotation(){
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
		return 5000;
	}

	@Override
	public void addEnergy(float energy) {
		this.energy += energy;
	}

	@Override
	public void consumeEnergy(float energy) {
		this.energy -= energy;
	}

	@Override
	public boolean canAcceptPE() {
		return true;
	}
}
