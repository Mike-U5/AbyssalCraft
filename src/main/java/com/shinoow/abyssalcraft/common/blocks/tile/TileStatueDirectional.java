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

import com.shinoow.abyssalcraft.api.energy.EnergyEnum.DeityType;
import com.shinoow.abyssalcraft.api.energy.IEnergyAmplifier;
import com.shinoow.abyssalcraft.api.energy.IEnergyContainer;
import com.shinoow.abyssalcraft.api.energy.IEnergyManipulator;
import com.shinoow.abyssalcraft.api.energy.IEnergyTransporter;
import com.shinoow.abyssalcraft.api.energy.EnergyEnum.AmplifierType;
import com.shinoow.abyssalcraft.api.energy.disruption.DisruptionHandler;
import com.shinoow.abyssalcraft.common.util.EntityUtil;
import com.shinoow.abyssalcraft.common.blocks.BlockODBcore;

public class TileStatueDirectional extends TEDirectional implements IEnergyManipulator {

	private int timer;
	private int amplifierTimer;
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
	public void readFromNBT(NBTTagCompound nbtData) {
		super.readFromNBT(nbtData);
		timer = nbtData.getInteger("Timer");
		amplifierTimer = nbtData.getInteger("AmplifierTimer");
		wrath = nbtData.getInteger("Wrath");
		if(nbtData.hasKey("Deity") && !nbtData.getString("Deity").equals("")) {
			currentDeity = DeityType.valueOf(nbtData.getString("Deity"));
		}
		if(nbtData.hasKey("Amplifier") && !nbtData.getString("Amplifier").equals("")) {
			currentAmplifier = AmplifierType.valueOf(nbtData.getString("Amplifier"));
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtData) {
		super.writeToNBT(nbtData);
		nbtData.setInteger("Timer", timer);
		nbtData.setInteger("AmplifierTimer", amplifierTimer);
		nbtData.setInteger("Wrath", wrath);
		if(currentDeity != null) {
			nbtData.setString("Deity", currentDeity.name());
		}
		if(currentAmplifier != null) {
			nbtData.setString("Amplifier", currentAmplifier.name());
		}

	}

	@Override
	public void setActive(AmplifierType amp, DeityType deity) {
		if(!isActive()) {
			amplifierTimer = 1500;
			currentAmplifier = amp;
			currentDeity = deity;
		}
	}

	@Override
	public boolean isActive() {
		return amplifierTimer > 0;
	}

	@Override
	public float energyQuanta() {
		float pe = 5 + getAmplifier(AmplifierType.POWER);
		// Bonus PE for using any amplifier
		if (isActive()) {
			pe += 5;
		}
		// Bonus PE at night in lieu of the old pedestal generation.
		if(!worldObj.isDaytime() && worldObj.canBlockSeeTheSky(xCoord, yCoord, zCoord)) {
			pe += 5;
		}
		return pe;
	}

	@Override
	public DeityType getDeity() {
		return null;
	}

	private int getPillarBonus() {
		Block block = worldObj.getBlock(xCoord, yCoord - 1, zCoord);
		if(block != null && block instanceof IEnergyAmplifier && ((IEnergyAmplifier) block).getAmplifierType() == AmplifierType.RANGE) {
			return 2;
		}
		return 0;
	}

	@Override
	public float getAmplifier(AmplifierType type) {
		switch(type) {
		case DURATION:
			if(type == currentAmplifier) {
				return currentDeity == getDeity() ? 4 : 8;
			}
			break;
		case POWER:
			if(type == currentAmplifier) {
				return currentDeity == getDeity() ? 20 : 5;
			}
			break;
		case RANGE:
			if(type == currentAmplifier) {
				return currentDeity == getDeity() ? 6 : 3;
			}
			break;
		}
		return 0;
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

	@SuppressWarnings("unchecked")
	@Override
	public void disrupt(boolean doDisrupt) {
		if(!worldObj.isRemote && doDisrupt) {
			worldObj.addWeatherEffect(new EntityLightningBolt(worldObj, xCoord, yCoord + 1, zCoord));
			DisruptionHandler.instance().generateDisruption(getDeity(), worldObj, xCoord, yCoord, zCoord, worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1).expand(16, 16, 16)));
		}
	}
	
	private int getMaxTime() {
		int time = 12;
		time -= getAmplifier(AmplifierType.DURATION);
		return time * 10;
	}
	
	private int getRndTimerReset() {
		int time = 12;
		time -= getAmplifier(AmplifierType.DURATION);
		return worldObj.rand.nextInt(time);
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if(isActive()) {
			amplifierTimer--;
			worldObj.spawnParticle("portal", xCoord + 0.5, yCoord + 0.9, zCoord + 0.5, 0, 0, 0);
		}
		
		timer++;
		if(timer >= getMaxTime()) {
			timer = getRndTimerReset();
			int range = (4 + getPillarBonus() + (int)getAmplifier(AmplifierType.RANGE));
			int receivers = 0;
			
			if(!(worldObj.getTileEntity(xCoord, yCoord + 1, zCoord) instanceof IEnergyManipulator) &&
					!(worldObj.getTileEntity(xCoord, yCoord - 1, zCoord) instanceof IEnergyManipulator) &&
					!(worldObj.getTileEntity(xCoord, yCoord + 2, zCoord) instanceof IEnergyManipulator) &&
					!(worldObj.getTileEntity(xCoord, yCoord - 2, zCoord) instanceof IEnergyManipulator)) {

				// Give PE to players
				@SuppressWarnings("unchecked")
				List<EntityPlayer> players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1).expand(range, range, range));

				for(EntityPlayer player : players) {
					// Stop if receiver cap is reached
					if (receivers >= range) {
						break;
					}
					// Add energy to player
					if(EntityUtil.hasNecronomicon(player)) {
						ItemStack item = player.getCurrentEquippedItem();
						if(item != null && item.getItem() instanceof IEnergyTransporter) {
							if(!worldObj.isRemote && ((IEnergyTransporter) item.getItem()).getContainedEnergy(item) < ((IEnergyTransporter) item.getItem()).getMaxEnergy(item)) {
								((IEnergyTransporter) item.getItem()).addEnergy(item, (int)(energyQuanta() * 1.8));
								receivers += 1;
							}
							final int xPos = xCoord < (int) player.posX ? 1 : xCoord > (int) player.posX ? -1 : 0;
							final int yPos = yCoord < (int) player.posY ? 1 : yCoord > (int) player.posY ? -1 : 0;
							final int zPos = zCoord < (int) player.posZ ? 1 : zCoord > (int) player.posZ ? -1 : 0;
							for(double i = 0; i <= 0.7; i += 0.03) {
								double x = i * Math.cos(i) / 2 * xPos;
								double y = i * Math.sin(i) / 2 * yPos;
								double z = i * Math.sin(i) / 2 * zPos;
								worldObj.spawnParticle("smoke", xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, x, y, z);
							}
							worldObj.spawnParticle("smoke", player.posX, player.posY, player.posZ, 0, 0, 0);
						}
					}
				}

				// Give PE to pedestals
				for(int x = -range; x <= range; x++) {
					// X: Stop if receiver cap is reached
					if (receivers >= range) {
						break;
					}
					for(int y = -range; y <= range; y++) {
						// Y: Stop if receiver cap is reached
						if (receivers >= range) {
							break;
						}
						for(int z = -range; z <= range; z++) {
							// Z: Stop if receiver cap is reached
							if (receivers >= range) {
								break;
							}
							//Fetch one Pedestal
							if(worldObj.getTileEntity(xCoord + x, yCoord + y, zCoord + z) != null) {
								TileEntity tile = worldObj.getTileEntity(xCoord + x, yCoord + y, zCoord + z);
								if(tile != null && tile instanceof IEnergyContainer) {
									IEnergyContainer peContainer = (IEnergyContainer)tile;
									if(peContainer.canAcceptPE()) {
										//Chuck energy in pedestal
										peContainer.addEnergy(energyQuanta());
										receivers += 1;
										final int xPos = xCoord < tile.xCoord ? 1 : xCoord > tile.xCoord ? -1 : 0;
										final int yPos = yCoord < tile.yCoord ? 1 : yCoord > tile.yCoord ? -1 : 0;
										final int zPos = zCoord < tile.zCoord ? 1 : zCoord > tile.zCoord ? -1 : 0;
										for(double i = 0; i <= 0.7; i += 0.03) {
											double dx = i * Math.cos(i) / 2 * xPos;
											double dy = i * Math.sin(i) / 2 * yPos;
											double dz = i * Math.sin(i) / 2 * zPos;
											worldObj.spawnParticle("smoke", xCoord + 0.5, yCoord + 0.95, zCoord + 0.5, dx, dy, dz);
										}
										worldObj.spawnParticle("smoke", tile.xCoord + 0.5, tile.yCoord + 0.95, tile.zCoord + 0.5, 0, 0, 0);
									}
								}
							}
						}
					}
				}
				
				// If on a relic the gods are less mad
				if (onArtifact()) {
					receivers -= 1;
				}
				// Incur the wrath of the great old ones
				if (receivers > 0) {
					wrath += Math.max((9 + receivers) * receivers, 0);
				}
				// If wrath is too high, cause disruption
				if (wrath >= 500) {
					wrath -= 500;
					disrupt(true);
				}
			}
		}
		clearData();
	}
}