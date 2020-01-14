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

import net.minecraft.world.World;

public class EntityGatekeeperWarden extends EntityGatekeeperMinion {

	public EntityGatekeeperWarden(World world) {
		super(world);
		isImmuneToFire = true;
	}

	@Override
	protected void dropFewItems(boolean hitByPlayer, int lootLvl) {}

	@Override
	protected void dropRareDrop(int lootLvl) {}
	
	@Override
	public void onLivingUpdate() {
		if (ticksExisted > 10000 && isEntityAlive()) {
			this.setHealth(0);
		}
		super.onLivingUpdate();
	}
}
