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
package com.shinoow.abyssalcraft.common.disruptions;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;

import com.shinoow.abyssalcraft.api.energy.disruption.DisruptionEntry;

public class DisruptionTeleportRandomly extends DisruptionEntry {

	public DisruptionTeleportRandomly() {
		super("randomTeleport", null);
	}

	@Override
	public void disrupt(World world, int x, int y, int z, List<EntityPlayer> players) {
		if(!players.isEmpty())
			for(EntityPlayer player : players)
				teleportRandomly(player, world);
	}

	protected boolean teleportRandomly(EntityPlayer player, World world)
	{
		double locX = player.posX + (world.rand.nextDouble() - 0.5D) * 256.0D;
		double locY = player.posY + (world.rand.nextInt(64) - 32);
		double locZ = player.posZ + (world.rand.nextDouble() - 0.5D) * 256.0D;
		return teleportTo(locX, locY, locZ, player, world);
	}

	protected boolean teleportTo(double par1, double par3, double par5, EntityPlayer player, World world)
	{
		EnderTeleportEvent event = new EnderTeleportEvent(player, par1, par3, par5, 0);
		if (MinecraftForge.EVENT_BUS.post(event)) {
			return false;
		}
		double d3 = player.posX;
		double d4 = player.posY;
		double d5 = player.posZ;
		player.posX = event.targetX;
		player.posY = event.targetY;
		player.posZ = event.targetZ;
		boolean flag = false;
		int i = MathHelper.floor_double(player.posX);
		int j = MathHelper.floor_double(player.posY);
		int k = MathHelper.floor_double(player.posZ);

		if (world.blockExists(i, j, k))
		{
			boolean flag1 = false;

			while (!flag1 && j > 0) {
				Block block = world.getBlock(i, j - 1, k);

				if (block.getMaterial().blocksMovement()) {
					flag1 = true;
				} else {
					--player.posY;
					--j;
				}
			}

			if (flag1) {
				player.setPosition(player.posX, player.posY, player.posZ);

				if (world.getCollidingBoundingBoxes(player, player.boundingBox).isEmpty() && !world.isAnyLiquid(player.boundingBox)) {
					flag = true;
				}
			}
		}

		if (!flag) {
			player.setPosition(d3, d4, d5);
			return false;
		} else {
			short short1 = 128;

			for (int l = 0; l < short1; ++l)
			{
				double d6 = l / (short1 - 1.0D);
				float f = (world.rand.nextFloat() - 0.5F) * 0.2F;
				float f1 = (world.rand.nextFloat() - 0.5F) * 0.2F;
				float f2 = (world.rand.nextFloat() - 0.5F) * 0.2F;
				double d7 = d3 + (player.posX - d3) * d6 + (world.rand.nextDouble() - 0.5D) * player.width * 2.0D;
				double d8 = d4 + (player.posY - d4) * d6 + world.rand.nextDouble() * player.height;
				double d9 = d5 + (player.posZ - d5) * d6 + (world.rand.nextDouble() - 0.5D) * player.width * 2.0D;
				world.spawnParticle("portal", d7, d8, d9, f, f1, f2);
			}

			world.playSoundEffect(d3, d4, d5, "mob.endermen.portal", 1.0F, 1.0F);
			player.playSound("mob.endermen.portal", 1.0F, 1.0F);
			return true;
		}
	}
}
