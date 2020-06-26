package com.shinoow.abyssalcraft.common.items;

import com.shinoow.abyssalcraft.common.entity.props.EntityPersistentItem;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemACPersistent extends ItemACBasic {

	public ItemACPersistent(String name) {
		super(name);
	}

	@Override
	public boolean hasCustomEntity(ItemStack stack) {
		return true;
	}

	@Override
	public Entity createEntity(World world, Entity entity, ItemStack itemstack) {
		final EntityPersistentItem ie = new EntityPersistentItem(world, entity.posX, entity.posY, entity.posZ, itemstack);
		ie.delayBeforeCanPickup = 40;
		return ie;
	}

}
