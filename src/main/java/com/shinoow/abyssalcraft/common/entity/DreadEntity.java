package com.shinoow.abyssalcraft.common.entity;

import com.shinoow.abyssalcraft.api.entity.IDreadEntity;
import com.shinoow.abyssalcraft.common.util.EntityUtil;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class DreadEntity extends ACMob implements IDreadEntity {

	public DreadEntity(World world) {
		super(world);
	}
	
	@Override
	public boolean attackEntityAsMob(Entity entity) {
		if (super.attackEntityAsMob(entity)) {
			if (entity instanceof EntityLivingBase) {
				EntityUtil.increaseDreadPlague((EntityLivingBase)entity, 100);
			}
		}
		return super.attackEntityAsMob(entity);
	}

}
