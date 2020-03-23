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

import com.shinoow.abyssalcraft.common.util.EntityUtil;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityDreadSlug extends EntityThrowable {

	public EntityDreadSlug(World world) {
		super(world);
	}

	public EntityDreadSlug(World world, EntityLivingBase entity) {
		super(world, entity);
	}

	public EntityDreadSlug(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	private boolean isEntityBlocking(Entity entity) {
		if (entity instanceof EntityPlayer) {
			final EntityPlayer player = (EntityPlayer) entity;
			return (player.isBlocking());
		}
		return false;
	}

	@Override
	protected void onImpact(MovingObjectPosition mop) {

		if (mop.entityHit instanceof EntityLivingBase) {
			final EntityLivingBase target = (EntityLivingBase) mop.entityHit;
			if (isEntityBlocking(mop.entityHit)) {
				// Fake Knockback
				playSound("mob.slime.small", 1.5F, 0.4F);
				double kbX = this.posX - target.posX;
				double kbZ;
				for (kbZ = this.posZ - target.posZ; kbX * kbX + kbZ * kbZ < 1.0E-4D; kbZ = (Math.random() - Math.random()) * 0.01D) {
					kbX = (Math.random() - Math.random()) * 0.01D;
				}
				target.attackedAtYaw = (float) (Math.atan2(kbZ, kbX) * 180.0D / Math.PI) - target.rotationYaw;
				target.knockBack(this, 0, kbX, kbZ);
			} else {
				EntityUtil.increaseDreadPlague(target, 100);
				mop.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), (byte) 6);
			}
		}

		if (!worldObj.isRemote) {
			setDead();
		}
	}

}
