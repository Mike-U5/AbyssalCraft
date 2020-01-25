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
package com.shinoow.abyssalcraft.common.util;

import java.util.List;
import java.util.UUID;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.entity.IAntiEntity;
import com.shinoow.abyssalcraft.api.entity.ICoraliumEntity;
import com.shinoow.abyssalcraft.api.entity.IDreadEntity;
import com.shinoow.abyssalcraft.common.entity.EntityGatekeeperMinion;
import com.shinoow.abyssalcraft.common.entity.EntityOmotholGhoul;
import com.shinoow.abyssalcraft.common.entity.EntityRemnant;
import com.shinoow.abyssalcraft.common.items.ItemCrozier;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public final class EntityUtil {

	private EntityUtil(){}
	
	public static void corruptWaterContainers(EntityPlayer player) {
		for (int i = 0; i < player.inventory.getSizeInventory() - 4; i++) {
			ItemStack stack = player.inventory.getStackInSlot(i);
			if (stack != null && stack.getItem() == Items.water_bucket) {
				player.inventory.setInventorySlotContents(i, new ItemStack(AbyssalCraft.Cbucket));
				break;
			}
		}
	}

	/**
	 * Checks if the Entity is immune to the Coralium Plague
	 * @param par1 The Entity to check
	 * @return True if the Entity is immune, otherwise false
	 */
	public static boolean isEntityCoralium(EntityLivingBase par1){
		return par1 instanceof ICoraliumEntity || par1 instanceof EntityPlayer && isPlayerCoralium((EntityPlayer)par1);
	}

	/**
	 * Checks if a Player has a certain name, and nulls the Coralium Plague if they do
	 * @param par1 The Player to check
	 * @return True if the Player has a certain name, otherwise false
	 */
	public static final boolean isPlayerCoralium(EntityPlayer par1){
		if(Vars.dev)
			return par1.getCommandSenderName().equals("shinoow") || par1.getCommandSenderName().equals("Oblivionaire");
		else return par1.getUniqueID().equals(Vars.uuid1) || par1.getUniqueID().equals(Vars.uuid2);
	}

	/**
	 * Checks if the Entity is immune to the Dread Plague
	 * @param par1 The Entity to check
	 * @return True if the Entity is immune, otherwise false
	 */
	public static boolean isEntityDread(EntityLivingBase par1){
		return par1 instanceof IDreadEntity;
	}

	/**
	 * Checks if the Entity is immune to Antimatter
	 * @param par1 The Entity to check
	 * @return True if the Entity is immune, otherwise false
	 */
	public static boolean isEntityAnti(EntityLivingBase par1){
		return par1 instanceof IAntiEntity;
	}
	
	public static boolean isEntityOmothol(EntityLivingBase entity) {
		return (
			entity instanceof EntityRemnant || 
			entity instanceof EntityGatekeeperMinion || 
			entity instanceof EntityOmotholGhoul
		);
	}

	/**
	 * Checks if a Player has a Necronomicon
	 * @param player The Player to check
	 * @return True if the Player has a Necronomicon, otherwise false
	 */
	public static boolean hasNecronomicon(EntityPlayer player){
		return player.inventory.hasItem(AbyssalCraft.necronomicon) || player.inventory.hasItem(AbyssalCraft.necronomicon_cor) ||
				player.inventory.hasItem(AbyssalCraft.necronomicon_dre) || player.inventory.hasItem(AbyssalCraft.necronomicon_omt) ||
				player.inventory.hasItem(AbyssalCraft.abyssalnomicon);
	}

	static class Vars{
		static boolean dev = (Boolean)Launch.blackboard.get("fml.deobfuscatedEnvironment");
		static UUID uuid1 = UUID.fromString("a5d8abca-0979-4bb0-825a-f1ccda0b350b");
		static UUID uuid2 = UUID.fromString("08f3211c-d425-47fd-afd8-f0e7f94152c4");
	}
	
	/**
	 * Get a vector between entities
	 */
	public static Vec3 getVectorBetweenEntities(Entity e, Entity target) {
		Vec3 fromPosition = Vec3.createVectorHelper(e.posX, e.posY, e.posZ);
		Vec3 toPosition = Vec3.createVectorHelper(target.posX, target.posY, target.posZ);
		Vec3 dist = fromPosition.subtract(toPosition);
		dist.normalize();
		return dist;
	}
	
	/**
	 * This selector will target players unless they hold a Crozier
	 */
	public static IEntitySelector selectorCrozier = new IEntitySelector() {
		@Override
		public boolean isEntityApplicable(Entity e) {
			if (e instanceof EntityLivingBase) {
				final ItemStack stack = ((EntityLivingBase)e).getHeldItem();
				if (stack != null && stack.getItem() instanceof ItemCrozier) {
					return false;
				}
			}
			return true;
		}
	};
	
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static Entity getPointedEntity(final World world, final EntityPlayer entityplayer, final double range, final Class<?> clazz) {
        Entity pointedEntity = null;
        final double d = range;
        final Vec3 vec3d = Vec3.createVectorHelper(entityplayer.posX, entityplayer.posY + entityplayer.getEyeHeight(), entityplayer.posZ);
        final Vec3 vec3d2 = entityplayer.getLookVec();
        final Vec3 vec3d3 = vec3d.addVector(vec3d2.xCoord * d, vec3d2.yCoord * d, vec3d2.zCoord * d);
        final float f1 = 1.1f;
        final List<Entity> list = world.getEntitiesWithinAABBExcludingEntity((Entity)entityplayer, entityplayer.boundingBox.addCoord(vec3d2.xCoord * d, vec3d2.yCoord * d, vec3d2.zCoord * d).expand((double)f1, (double)f1, (double)f1));
        double d2 = 0.0;
        for (int i = 0; i < list.size(); ++i) {
            final Entity entity = list.get(i);
            if (entity.canBeCollidedWith() && world.func_147447_a(Vec3.createVectorHelper(entityplayer.posX, entityplayer.posY + entityplayer.getEyeHeight(), entityplayer.posZ), Vec3.createVectorHelper(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ), false, true, false) == null) {
                if (!clazz.isInstance(entity)) {
                    final float f2 = Math.max(0.8f, entity.getCollisionBorderSize());
                    final AxisAlignedBB axisalignedbb = entity.boundingBox.expand((double)f2, (double)f2, (double)f2);
                    final MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3d, vec3d3);
                    if (axisalignedbb.isVecInside(vec3d) && (0.0 < d2 || d2 == 0.0)) {
                        if (0.0 < d2 || d2 == 0.0) {
                            pointedEntity = entity;
                            d2 = 0.0;
                        }
                    } else if (movingobjectposition != null) {
                        final double d3 = vec3d.distanceTo(movingobjectposition.hitVec);
                        if (d3 < d2 || d2 == 0.0) {
                            pointedEntity = entity;
                            d2 = d3;
                        }
                    }
                }
            }
        }
        return pointedEntity;
    }
}
