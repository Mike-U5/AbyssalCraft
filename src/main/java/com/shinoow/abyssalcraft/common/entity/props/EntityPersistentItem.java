package com.shinoow.abyssalcraft.common.entity.props;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityPersistentItem extends EntityItem {

	public EntityPersistentItem(World world, double x, double y, double z, ItemStack stack) {
		super(world, x, y, z, stack);
		this.isImmuneToFire = true;
	}

	public boolean attackEntityFrom(DamageSource src, float amount) {
		if (!src.canHarmInCreative()) {
			return false;
		}
		
		return super.attackEntityFrom(src, amount);
	}

	protected void dealFireDamage(int amount) {}
}
