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
package com.shinoow.abyssalcraft.common.entity;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.entity.ICoraliumEntity;
import com.shinoow.abyssalcraft.common.util.EntityUtil;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class CoraliumEntity extends ACMob implements ICoraliumEntity {

	public CoraliumEntity(World world) {
		super(world);
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource src, float dmg) {
		if (dmg > 0 && !src.isFireDamage() && !worldObj.isRemote) {
			int xPos = (int)(Math.floor(this.posX));
			int yPos = (int)(Math.floor(this.posY));
			int zPos = (int)(Math.floor(this.posZ));
			
			if (worldObj.getBlock(xPos, yPos, zPos) == Blocks.water) {
				worldObj.setBlock(xPos, yPos, zPos, AbyssalCraft.Cwater);
			}
			
			yPos -= 1;
			if (worldObj.getBlock(xPos, yPos, zPos) == Blocks.water) {
				worldObj.setBlock(xPos, yPos, zPos, AbyssalCraft.Cwater);
			}
		}
		return super.attackEntityFrom(src, dmg);
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		if (!worldObj.isRemote && entity instanceof EntityPlayer) {
			EntityUtil.corruptWaterContainers((EntityPlayer)entity);
		}
		return super.attackEntityAsMob(entity);
	}
}
