package com.shinoow.abyssalcraft.common.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.world.World;

public class WardenProjectile extends EntityArrow {

	public WardenProjectile(World world, EntityLivingBase user, EntityLivingBase target, float numA, float numB) {
		super(world, user, target, numA, numB);
	}


}
