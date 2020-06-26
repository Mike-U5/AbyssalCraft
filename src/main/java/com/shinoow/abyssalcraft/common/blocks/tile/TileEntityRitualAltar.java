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

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.energy.IEnergyTransporter;
import com.shinoow.abyssalcraft.api.event.ACEvents.RitualEvent;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;
import com.shinoow.abyssalcraft.api.ritual.RitualRegistry;
import com.shinoow.abyssalcraft.common.entity.EntityRemnant;
import com.shinoow.abyssalcraft.common.items.ItemNecronomicon;
import com.shinoow.abyssalcraft.common.util.IRitualAltar;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class TileEntityRitualAltar extends TileEntity implements IRitualAltar {

	private int ritualTimer;
	private ItemStack[] offers = new ItemStack[8];
	private NecronomiconRitual ritual;
	private ItemStack item;
	private int rot;
	private EntityPlayer user;
	private float consumedEnergy;
	private boolean isDirty;

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		NBTTagCompound nbtItem = nbttagcompound.getCompoundTag("Item");
		item = ItemStack.loadItemStackFromNBT(nbtItem);
		rot = nbttagcompound.getInteger("Rot");
		ritualTimer = nbttagcompound.getInteger("Cooldown");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		NBTTagCompound nbtItem = new NBTTagCompound();
		if (item != null)
			item.writeToNBT(nbtItem);
		nbttagcompound.setTag("Item", nbtItem);
		nbttagcompound.setInteger("Rot", rot);
		nbttagcompound.setInteger("Cooldown", ritualTimer);
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

		if (isDirty || isPerformingRitual()) {
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			isDirty = false;
		}

		if (isPerformingRitual()) {
			ritualTimer++;

			if (ritual != null) {
				if (user != null) {
					for (ItemStack item : user.inventory.mainInventory)
						if (item != null && item.getItem() instanceof IEnergyTransporter && ((IEnergyTransporter) item.getItem()).getContainedEnergy(item) > 0) {
							if (!worldObj.isRemote)
								((IEnergyTransporter) item.getItem()).consumeEnergy(item, ritual.getReqEnergy() / 200);
							consumedEnergy += ritual.getReqEnergy() / 200;
							break;
						}
				} else {
					user = worldObj.getClosestPlayer(xCoord, yCoord, zCoord, 5);
				}
				
				if (ritualTimer == 200)
					if (user != null) {
						if (!MinecraftForge.EVENT_BUS.post(new RitualEvent.Post(user, ritual, worldObj, xCoord, yCoord, zCoord))) {
							for (ItemStack item : user.inventory.mainInventory)
								if (item != null && item.getItem() instanceof IEnergyTransporter && ((IEnergyTransporter) item.getItem()).getContainedEnergy(item) > 0) {
									if (!worldObj.isRemote)
										((IEnergyTransporter) item.getItem()).consumeEnergy(item, ritual.getReqEnergy() / 200);
									consumedEnergy += ritual.getReqEnergy() / 200;
									break;
								}
							if (consumedEnergy == ritual.getReqEnergy()) {
								ritual.completeRitual(worldObj, xCoord, yCoord, zCoord, user);
							} else {
								System.err.println("Not enough energy to complete ritual!");
								//if (!worldObj.isRemote)
									//worldObj.addWeatherEffect(new EntityLightningBolt(worldObj, xCoord, yCoord + 1, zCoord));
								//DisruptionHandler.instance().generateDisruption(null, worldObj, xCoord, yCoord, zCoord, worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1).expand(16, 16, 16)));
							}
							ritualTimer = 0;
							user = null;
							ritual = null;
							consumedEnergy = 0;
							markDirty();
						}
					} else {
						System.err.println("Ritual perfomer not found!");
						//if (!worldObj.isRemote) {
						//	worldObj.addWeatherEffect(new EntityLightningBolt(worldObj, xCoord, yCoord + 1, zCoord));
						//}
						//DisruptionHandler.instance().generateDisruption(null, worldObj, xCoord, yCoord, zCoord, worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1).expand(16, 16, 16)));
						ritualTimer = 0;
						ritual = null;
						consumedEnergy = 0;
						markDirty();
					}
			} else {
				ritualTimer = 0;
			}

			if (AbyssalCraft.particleBlock) {
				worldObj.spawnParticle("lava", xCoord + 0.5, yCoord + 1, zCoord + 0.5, 0, 0, 0);

				double x = 0, z = 0, o = 0;
				for (double i = 0; i <= 0.7; i += 0.03) {
					x = i * Math.cos(i) / 2;
					z = i * Math.sin(i) / 2;
					o = i * Math.tan(i) / 2;
				}

				worldObj.spawnParticle("largesmoke", xCoord - 2.5, yCoord + 0.95, zCoord + 0.5, x, 0, 0);
				worldObj.spawnParticle("largesmoke", xCoord + 0.5, yCoord + 0.95, zCoord - 2.5, 0, 0, z);
				worldObj.spawnParticle("largesmoke", xCoord + 3.5, yCoord + 0.95, zCoord + 0.5, -x, 0, 0);
				worldObj.spawnParticle("largesmoke", xCoord + 0.5, yCoord + 0.95, zCoord + 3.5, 0, 0, -z);
				worldObj.spawnParticle("largesmoke", xCoord - 1.5, yCoord + 0.95, zCoord + 2.5, o, 0, -o);
				worldObj.spawnParticle("largesmoke", xCoord - 1.5, yCoord + 0.95, zCoord - 1.5, o, 0, o);
				worldObj.spawnParticle("largesmoke", xCoord + 2.5, yCoord + 0.95, zCoord + 2.5, -o, 0, -o);
				worldObj.spawnParticle("largesmoke", xCoord + 2.5, yCoord + 0.95, zCoord - 1.5, -o, 0, o);

				worldObj.spawnParticle("flame", xCoord - 2.5, yCoord + 1.05, zCoord + 0.5, 0, 0, 0);
				worldObj.spawnParticle("flame", xCoord + 0.5, yCoord + 1.05, zCoord - 2.5, 0, 0, 0);
				worldObj.spawnParticle("flame", xCoord + 3.5, yCoord + 1.05, zCoord + 0.5, 0, 0, 0);
				worldObj.spawnParticle("flame", xCoord + 0.5, yCoord + 1.05, zCoord + 3.5, 0, 0, 0);
				worldObj.spawnParticle("flame", xCoord - 1.5, yCoord + 1.05, zCoord + 2.5, 0, 0, 0);
				worldObj.spawnParticle("flame", xCoord - 1.5, yCoord + 1.05, zCoord - 1.5, 0, 0, 0);
				worldObj.spawnParticle("flame", xCoord + 2.5, yCoord + 1.05, zCoord + 2.5, 0, 0, 0);
				worldObj.spawnParticle("flame", xCoord + 2.5, yCoord + 1.05, zCoord - 1.5, 0, 0, 0);

				worldObj.spawnParticle("smoke", xCoord - 2.5, yCoord + 1.05, zCoord + 0.5, 0, 0, 0);
				worldObj.spawnParticle("smoke", xCoord + 0.5, yCoord + 1.05, zCoord - 2.5, 0, 0, 0);
				worldObj.spawnParticle("smoke", xCoord + 3.5, yCoord + 1.05, zCoord + 0.5, 0, 0, 0);
				worldObj.spawnParticle("smoke", xCoord + 0.5, yCoord + 1.05, zCoord + 3.5, 0, 0, 0);
				worldObj.spawnParticle("smoke", xCoord - 1.5, yCoord + 1.05, zCoord + 2.5, 0, 0, 0);
				worldObj.spawnParticle("smoke", xCoord - 1.5, yCoord + 1.05, zCoord - 1.5, 0, 0, 0);
				worldObj.spawnParticle("smoke", xCoord + 2.5, yCoord + 1.05, zCoord + 2.5, 0, 0, 0);
				worldObj.spawnParticle("smoke", xCoord + 2.5, yCoord + 1.05, zCoord - 1.5, 0, 0, 0);
			}
		}

		if (rot == 360) {
			rot = 0;
		}
		if (item != null) {
			rot++;
		}
	}

	@Override
	public boolean canPerform() {
		if (checkSurroundings(worldObj, xCoord, yCoord, zCoord)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean checkSurroundings(World world, int x, int y, int z) {
		TileEntity ped1 = world.getTileEntity(x - 3, y, z);
		TileEntity ped2 = world.getTileEntity(x, y, z - 3);
		TileEntity ped3 = world.getTileEntity(x + 3, y, z);
		TileEntity ped4 = world.getTileEntity(x, y, z + 3);
		TileEntity ped5 = world.getTileEntity(x - 2, y, z + 2);
		TileEntity ped6 = world.getTileEntity(x - 2, y, z - 2);
		TileEntity ped7 = world.getTileEntity(x + 2, y, z + 2);
		TileEntity ped8 = world.getTileEntity(x + 2, y, z - 2);
		if (ped1 != null && ped2 != null && ped3 != null && ped4 != null && ped5 != null && ped6 != null && ped7 != null && ped8 != null)
			if (ped1 instanceof TileEntityRitualPedestal && ped2 instanceof TileEntityRitualPedestal && ped3 instanceof TileEntityRitualPedestal && ped4 instanceof TileEntityRitualPedestal && ped5 instanceof TileEntityRitualPedestal && ped6 instanceof TileEntityRitualPedestal && ped7 instanceof TileEntityRitualPedestal && ped8 instanceof TileEntityRitualPedestal) {
				offers[0] = ((TileEntityRitualPedestal) ped1).getItem();
				offers[1] = ((TileEntityRitualPedestal) ped2).getItem();
				offers[2] = ((TileEntityRitualPedestal) ped3).getItem();
				offers[3] = ((TileEntityRitualPedestal) ped4).getItem();
				offers[4] = ((TileEntityRitualPedestal) ped5).getItem();
				offers[5] = ((TileEntityRitualPedestal) ped6).getItem();
				offers[6] = ((TileEntityRitualPedestal) ped7).getItem();
				offers[7] = ((TileEntityRitualPedestal) ped8).getItem();
				
				if (offers[0] == null && offers[1] == null && offers[2] == null && offers[3] == null && offers[4] == null && offers[5] == null && offers[6] == null && offers[7] == null)
					return false;
				else
					return true;
			}
		return false;
	}

	@Override
	public void resetPedestals(World world, int x, int y, int z) {
		TileEntity ped1 = world.getTileEntity(x - 3, y, z);
		TileEntity ped2 = world.getTileEntity(x, y, z - 3);
		TileEntity ped3 = world.getTileEntity(x + 3, y, z);
		TileEntity ped4 = world.getTileEntity(x, y, z + 3);
		TileEntity ped5 = world.getTileEntity(x - 2, y, z + 2);
		TileEntity ped6 = world.getTileEntity(x - 2, y, z - 2);
		TileEntity ped7 = world.getTileEntity(x + 2, y, z + 2);
		TileEntity ped8 = world.getTileEntity(x + 2, y, z - 2);
		if (ped1 != null && ped2 != null && ped3 != null && ped4 != null && ped5 != null && ped6 != null && ped7 != null && ped8 != null)
			if (ped1 instanceof TileEntityRitualPedestal && ped2 instanceof TileEntityRitualPedestal && ped3 instanceof TileEntityRitualPedestal && ped4 instanceof TileEntityRitualPedestal && ped5 instanceof TileEntityRitualPedestal && ped6 instanceof TileEntityRitualPedestal && ped7 instanceof TileEntityRitualPedestal && ped8 instanceof TileEntityRitualPedestal) {
				((TileEntityRitualPedestal) ped1).setItem(null);
				((TileEntityRitualPedestal) ped2).setItem(null);
				((TileEntityRitualPedestal) ped3).setItem(null);
				((TileEntityRitualPedestal) ped4).setItem(null);
				((TileEntityRitualPedestal) ped5).setItem(null);
				((TileEntityRitualPedestal) ped6).setItem(null);
				((TileEntityRitualPedestal) ped7).setItem(null);
				((TileEntityRitualPedestal) ped8).setItem(null);
			}
	}

	@Override
	public void performRitual(World world, int x, int y, int z, EntityPlayer player) {

		if (!isPerformingRitual()) {
			ItemStack item = player.getCurrentEquippedItem();
			if (item.getItem() instanceof ItemNecronomicon) {
				if (RitualRegistry.instance().canPerformAction(world.provider.dimensionId, ((ItemNecronomicon) item.getItem()).getBookType())) {
					if (canPerform()) {
						ritual = RitualRegistry.instance().getRitual(world.provider.dimensionId, ((ItemNecronomicon) item.getItem()).getBookType(), offers, this.item);
						if (ritual != null) {
							if (ritual.canRemnantAid()) {
								if (!world.getEntitiesWithinAABB(EntityRemnant.class, AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1).expand(32, 32, 32)).isEmpty() && world.getEntitiesWithinAABB(EntityRemnant.class, AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1).expand(32, 32, 32)).size() >= ritual.getBookType() + 1) {
									if (ritual.canCompleteRitual(world, x, y, z, player)) {
										if (!MinecraftForge.EVENT_BUS.post(new RitualEvent.Pre(player, ritual, world, x, y, z))) {
											ritualTimer = 1;
											resetPedestals(world, x, y, z);
											user = player;
											consumedEnergy = 0;
										}
									}
								}
							} else if (ritual.canCompleteRitual(world, x, y, z, player)) {
								if (!MinecraftForge.EVENT_BUS.post(new RitualEvent.Pre(player, ritual, world, x, y, z))) {
									ritualTimer = 1;
									resetPedestals(world, x, y, z);
									user = player;
									consumedEnergy = 0;
								}
							}
						}
					}
				}
			}
		}
	}

	@Override
	public int getRitualCooldown() {
		return ritualTimer;
	}

	@Override
	public boolean isPerformingRitual() {
		return ritualTimer < 200 && ritualTimer > 0;
	}

	public int getRotation() {
		return rot;
	}

	@Override
	public ItemStack getItem() {
		return item;
	}

	@Override
	public void setItem(ItemStack item) {
		isDirty = true;
		this.item = item;
	}
}
