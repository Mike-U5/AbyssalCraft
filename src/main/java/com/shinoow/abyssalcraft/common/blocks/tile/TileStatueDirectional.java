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

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.api.energy.EnergyEnum.DeityType;
import com.shinoow.abyssalcraft.api.energy.IEnergyAmplifier;
import com.shinoow.abyssalcraft.api.energy.IEnergyContainer;
import com.shinoow.abyssalcraft.api.energy.IEnergyManipulator;
import com.shinoow.abyssalcraft.api.energy.IEnergyTransporter;
import com.shinoow.abyssalcraft.api.energy.EnergyEnum.AmplifierType;
import com.shinoow.abyssalcraft.api.energy.disruption.DisruptionHandler;
import com.shinoow.abyssalcraft.common.util.EntityUtil;
import com.shinoow.abyssalcraft.common.blocks.BlockStatue;
import com.shinoow.abyssalcraft.common.blocks.BlockODBcore;

public class TileStatueDirectional extends TEDirectional implements IEnergyManipulator {

	private final int timerMax = 100;
	private int timer;
	private int activationTimer;
	private int wrath;
	private AmplifierType currentAmplifier;
	private DeityType currentDeity;


	@Override
	public boolean canUpdate() {
		return true;
	}
	
	private boolean onArtifact() {
		Block block = worldObj.getBlock(xCoord, yCoord - 1, zCoord);
		return (block instanceof BlockODBcore);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		timer = nbttagcompound.getInteger("Timer");
		activationTimer = nbttagcompound.getInteger("ActivationTimer");
		wrath = nbttagcompound.getInteger("Wrath");
		if(nbttagcompound.hasKey("Deity") && !nbttagcompound.getString("Deity").equals(""))
			currentDeity = DeityType.valueOf(nbttagcompound.getString("Deity"));
		if(nbttagcompound.hasKey("Amplifier") && !nbttagcompound.getString("Amplifier").equals(""))
			currentAmplifier = AmplifierType.valueOf(nbttagcompound.getString("Amplifier"));
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("Timer", timer);
		nbttagcompound.setInteger("ActivationTimer", activationTimer);
		nbttagcompound.setInteger("Wrath", wrath);
		if(currentDeity != null)
			nbttagcompound.setString("Deity", currentDeity.name());
		if(currentAmplifier != null)
			nbttagcompound.setString("Amplifier", currentAmplifier.name());

	}

	@Override
	public void setActive(AmplifierType amp, DeityType deity) {
		if(!isActive()){
			activationTimer = 1200;
			currentAmplifier = amp;
			currentDeity = deity;
		}
	}

	@Override
	public boolean isActive() {
		return activationTimer > 0;
	}

	@Override
	public float energyQuanta() {
		float e = isActive() ? 10 * getAmplifier(AmplifierType.POWER) : 5;
		if(!worldObj.isDaytime()) {
			e += 5;
		}
		return e;
	}

	@Override
	public DeityType getDeity() {
		return null;
	}

	private int getPillarMultiplier() {
		Block block1 = worldObj.getBlock(xCoord, yCoord - 1, zCoord);
		Block block2 = worldObj.getBlock(xCoord, yCoord - 2, zCoord);
		int num = 1;
		if(block1 != null && block1 instanceof IEnergyAmplifier &&
				((IEnergyAmplifier) block1).getAmplifierType() == AmplifierType.RANGE)
			num = 5;
		if(block1 != null && block1 instanceof IEnergyAmplifier &&
				((IEnergyAmplifier) block1).getAmplifierType() == AmplifierType.RANGE
				&& block2 != null && block2 instanceof IEnergyAmplifier &&
				((IEnergyAmplifier) block2).getAmplifierType() == AmplifierType.RANGE)
			num = 9;
		return num;
	}

	@Override
	public float getAmplifier(AmplifierType type) {
		switch(type){
		case DURATION:
			if(type == currentAmplifier)
				return currentDeity == getDeity() ? 4 : 2;
			else return 1;
		case POWER:
			if(type == currentAmplifier)
				return currentDeity == getDeity() ? 2.5F : 1.5F;
			else return 1;
		case RANGE:
			if(type == currentAmplifier)
				return currentDeity == getDeity() ? 6 : 4;
			else return 0;
		default:
			return 0;
		}
	}

	@Override
	public void clearData() {
		if(!isActive()){
			NBTTagCompound tag = new NBTTagCompound();
			writeToNBT(tag);

			tag.setString("Deity", "");
			tag.setString("Amplifier", "");
			currentDeity = null;
			currentAmplifier = null;

			readFromNBT(tag);
		}
	}

	@Override
	public void disrupt(boolean factor) {
		if(factor) {
			if(!worldObj.isRemote)
				worldObj.addWeatherEffect(new EntityLightningBolt(worldObj, xCoord, yCoord + 1, zCoord));
			DisruptionHandler.instance().generateDisruption(getDeity(), worldObj, xCoord, yCoord, zCoord, worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1).expand(16, 16, 16)));
		}
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if(isActive()) {
			activationTimer--;
			worldObj.spawnParticle("portal", xCoord + 0.5, yCoord + 0.9, zCoord + 0.5, 0, 0, 0);
		}
		
		timer++;
		if(timer >= (int)(timerMax / getAmplifier(AmplifierType.DURATION))) {
			timer = 0;
			int range = (int) (4 + getPillarMultiplier() + getAmplifier(AmplifierType.RANGE));
			int receivers = 0;
			int dirtyDeeds = 0;
			
			if(!(worldObj.getTileEntity(xCoord, yCoord + 1, zCoord) instanceof IEnergyManipulator) &&
					!(worldObj.getTileEntity(xCoord, yCoord - 1, zCoord) instanceof IEnergyManipulator) &&
					!(worldObj.getTileEntity(xCoord, yCoord + 2, zCoord) instanceof IEnergyManipulator) &&
					!(worldObj.getTileEntity(xCoord, yCoord - 2, zCoord) instanceof IEnergyManipulator)) {

				//Get Players
				List<EntityPlayer> players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1).expand(range, range, range));

				for(EntityPlayer player : players) {
					if(EntityUtil.hasNecronomicon(player)){
						ItemStack item = player.getCurrentEquippedItem();
						if(item != null && item.getItem() instanceof IEnergyTransporter){
							if(!worldObj.isRemote && ((IEnergyTransporter) item.getItem()).getContainedEnergy(item) < ((IEnergyTransporter) item.getItem()).getMaxEnergy(item)) {
								((IEnergyTransporter) item.getItem()).addEnergy(item, energyQuanta());
								dirtyDeeds += 2;
								for(double i = 0; i <= 0.7; i += 0.03) {
									int xPos = xCoord < (int) player.posX ? 1 : xCoord > (int) player.posX ? -1 : 0;
									int yPos = yCoord < (int) player.posY ? 1 : yCoord > (int) player.posY ? -1 : 0;
									int zPos = zCoord < (int) player.posZ ? 1 : zCoord > (int) player.posZ ? -1 : 0;
									double x = i * Math.cos(i) / 2 * xPos;
									double y = i * Math.sin(i) / 2 * yPos;
									double z = i * Math.sin(i) / 2 * zPos;
									worldObj.spawnParticle("largeSmoke", xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, x, y, z);
								}
							}
						}
					}
					if (receivers >= range) {
						break;
					}
				}

				
				//Get Pedestals
				for(int x = -range; x <= range; x++) {
					for(int y = -range; y <= range; y++) {
						for(int z = -range; z <= range; z++) {
							//Fetch one Pedestal
							if(worldObj.getTileEntity(xCoord + x, yCoord + y, zCoord + z) != null) {
								TileEntity pedestal = worldObj.getTileEntity(xCoord + x, yCoord + y, zCoord + z);
								if(pedestal != null && pedestal instanceof IEnergyContainer && ((IEnergyContainer) pedestal).canAcceptPE()) {
									if(((IEnergyContainer) pedestal).getContainedEnergy() < ((IEnergyContainer) pedestal).getMaxEnergy()){
										//Chuck energy in pedestal
										((IEnergyContainer) pedestal).addEnergy(energyQuanta());
										for(double i = 0; i <= 0.66; i += 0.03) {
											int xPos = xCoord < pedestal.xCoord ? 1 : xCoord > pedestal.xCoord ? -1 : 0;
											int yPos = yCoord < pedestal.yCoord ? 1 : yCoord > pedestal.yCoord ? -1 : 0;
											int zPos = zCoord < pedestal.zCoord ? 1 : zCoord > pedestal.zCoord ? -1 : 0;
											double dx = i * Math.cos(i) / 2 * xPos;
											double dy = i * Math.sin(i) / 2 * yPos;
											double dz = i * Math.sin(i) / 2 * zPos;
											worldObj.spawnParticle("largeSmoke", xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, dx, dy, dz);
										}
										dirtyDeeds += 2;
										receivers++;
									}
								}
							}
							if (receivers >= range) {
								break;
							}
						}
						if (receivers >= range) {
							break;
						}
					}
					if (receivers >= range) {
						break;
					}
				}
				
				// If on a relic, become dirt cheap
				if (onArtifact()) {
					dirtyDeeds -= 2;
				}
				// Incur the wrath of the great old ones
				if (dirtyDeeds > 0) {
					wrath += dirtyDeeds;
				}
				if (wrath >= 100) {
					wrath -= 100;
					disrupt(true);
				}
				dirtyDeeds = 0;
			}
		}
		clearData();
	}
}