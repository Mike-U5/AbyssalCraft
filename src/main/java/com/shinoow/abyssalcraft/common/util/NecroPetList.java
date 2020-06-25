package com.shinoow.abyssalcraft.common.util;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class NecroPetList {
	private final int limit = 30;
	private final NBTTagCompound nbt;
	private final ArrayList<NBTTagCompound> list = new ArrayList<NBTTagCompound>();
	
	public NecroPetList(EntityPlayer player) {
		this.nbt = player.getEntityData();
		for (int i = 0; i < this.limit; i++) {
			final String index = "necroIndex" + i;
			if (this.nbt.hasKey(index)) {
				this.list.add(this.nbt.getCompoundTag(index));
			} else {
				break;
			}
		}
	}
	
	//public NecroPetByName(EntityPlayer player) {
		
	//}
	
	public void writeToPlayer() {
		for (int i = 0; i < this.limit; i++) {
			final String index = "necroIndex" + i;
			if (nbt.hasKey(index)) {
				nbt.removeTag(index);
			}
			if (i < this.list.size()) {
				final NBTTagCompound tag = this.list.get(i);
				this.nbt.setTag(index, tag);
			}
		}
	}
	
	public int size() {
		return this.list.size();
	}
	
	public NBTTagCompound shift() {
		return this.list.remove(0);
	}
	
	public NBTTagCompound getFirst() {
		return this.list.get(0);
	}
	
	public void add(NBTTagCompound tag) {
		if (this.list.size() >= this.limit) {
			this.shift();
		}
		this.list.add(tag);
	}
}
