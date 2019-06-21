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

import com.shinoow.abyssalcraft.api.energy.IEnergyContainer;
import com.shinoow.abyssalcraft.common.util.ISingletonInventory;

public class TileEntityTieredEnergyPedestal extends TileEntityEnergyPedestal implements IEnergyContainer, ISingletonInventory {	
	@Override
	protected int getTier() {
		switch(worldObj.getBlockMetadata(xCoord, yCoord, zCoord)){
		case 0: return 2;
		case 1: return 3;
		case 2: return 4;
		case 3: return 5;
		default: return 1;
		}
	}
}
