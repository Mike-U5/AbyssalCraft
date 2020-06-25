/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2018 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.caps;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Tuple;

public class NecromancyCapability implements INecromancyCapability {

	List<Tuple> data = Lists.newArrayList();
	Map<String, Integer> sizes = Maps.newHashMap();

	public static INecromancyCapability getCap(EntityPlayer player){
		//return player.getCapability(NecromancyCapabilityProvider.NECROMANCY_CAP, null);
		return null;
	}

	@Override
	public NBTTagCompound getDataForName(String name) {
		for(Tuple t : data) {
			if(t.getFirst().equals(name) && t.getSecond() instanceof NBTTagCompound) {
				return (NBTTagCompound) t.getSecond();
			}
		}
		return null;
	}

	@Override
	public void storeData(String name, NBTTagCompound data, int size) {

		if(getDataForName(name) == null) {
			if(this.data.size() == 5) {
				sizes.remove(this.data.get(0).getFirst());
				this.data.remove(0);
			}
			this.data.add(new Tuple(name, data));
		} else {
			for(Tuple t : this.data) {
				if(t.getFirst().equals(name)){
					t = new Tuple(name, data);
					break;
				}
			}
		}
		sizes.put(name, size);
	}

	@Override
	public void clearEntry(String name) {
		for(Tuple t : data)
			if(t.getFirst().equals(name)){
				data.remove(t);
				break;
			}
		sizes.remove(name);
	}

	@Override
	public List<Tuple> getData() {

		return data;
	}

	@Override
	public Map<String, Integer> getSizeData() {

		return sizes;
	}

	@Override
	public void copy(INecromancyCapability cap) {
		data = cap.getData();
		sizes = cap.getSizeData();
	}
}