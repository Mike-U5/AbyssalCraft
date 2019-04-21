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

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import thaumcraft.api.ThaumcraftApiHelper;
import com.shinoow.abyssalcraft.api.energy.disruption.DisruptionEntry;

public class DisruptionTaintRain extends DisruptionEntry {

	public DisruptionTaintRain() {
		super("taintRain", null);
	}

	@Override
	public void disrupt(World world, int x, int y, int z, List<EntityPlayer> players) {
		double rx = 0;
		double rz = 0;
		
		rx = world.rand.nextInt(500) / 100;
		rz = world.rand.nextInt(500) / 100;
		Entity tainter1 = EntityProjectile(world, x + rx, y + 10, z + rz, 0, -0.5, 0);
		
		rx = world.rand.nextInt(500) / 100;
		rz = world.rand.nextInt(500) / 100;
		Entity tainter2 = EntityProjectile(world, x + rx, y + 10, z - rz, 0, -0.5, 0);
		
		rx = world.rand.nextInt(500) / 100;
		rz = world.rand.nextInt(500) / 100;
		Entity tainter3 = EntityProjectile(world, x + rx, y + 10, z - rz, 0, -0.5, 0);
		
		rx = world.rand.nextInt(500) / 100;
		rz = world.rand.nextInt(500) / 100;
		Entity tainter4 = EntityProjectile(world, x - rx, y + 10, z - rz, 0, -0.5, 0);

		if(!world.isRemote){
			world.spawnEntityInWorld(tainter1);
			world.spawnEntityInWorld(tainter2);
			world.spawnEntityInWorld(tainter3);
			world.spawnEntityInWorld(tainter4);
		}
		
		//Apply warp to players
		if(!players.isEmpty()) {
			for(EntityPlayer player : players) {
				ThaumcraftApiHelper.addWarpToPlayer(player, 10, true);
			}
		}
	}

	private Entity EntityProjectile(World world, double x, double y, double z, double accx, double accy, double accz){
		Entity projectile = EntityList.createEntityByName("Thaumcraft.BottleTaint", world);
		projectile.setLocationAndAngles(x, y, z, projectile.rotationYaw, projectile.rotationPitch);
		projectile.setPosition(x, y, z);

		return projectile;
	}
}
