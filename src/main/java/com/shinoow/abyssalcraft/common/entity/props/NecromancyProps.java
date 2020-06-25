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
package com.shinoow.abyssalcraft.common.entity.props;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

import java.util.ArrayList;

import com.shinoow.abyssalcraft.api.energy.EnergyEnum.DeityType;
import com.shinoow.abyssalcraft.common.util.ACLogger;

public class NecromancyProps implements IExtendedEntityProperties {
	public final static String EXT_PROP_NAME = "DeityReputation";
	private ArrayList<NBTTagCompound> data = new ArrayList<NBTTagCompound>();

	public NecromancyProps(EntityPlayer player) {
	}

	/**
	 * Used to register these extended properties for the player during
	 * EntityConstructing event
	 */
	public static final void register(EntityPlayer player) {
		player.registerExtendedProperties(NecromancyProps.EXT_PROP_NAME, new NecromancyProps(player));
	}

	/**
	 * Returns ReputationProps properties for player
	 */
	public static final NecromancyProps get(EntityPlayer player) {
		return (NecromancyProps) player.getExtendedProperties(EXT_PROP_NAME);
	}

	/**
	 * Copies additional player data from the given ReputationProps instance Avoids
	 * NBT disk I/O overhead when cloning a player after respawn
	 */
	public void copy(NecromancyProps props) {
		// No copy'ing
	}

	@Override
	public final void saveNBTData(NBTTagCompound compound) {
		final NBTTagCompound properties = new NBTTagCompound();
		compound.setTag(EXT_PROP_NAME, properties);
	}

	@Override
	public final void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound properties = (NBTTagCompound) compound.getTag(EXT_PROP_NAME);
	}

	@Override
	public void init(Entity entity, World world) {
	}

	public void onUpdate() {

	}
}
