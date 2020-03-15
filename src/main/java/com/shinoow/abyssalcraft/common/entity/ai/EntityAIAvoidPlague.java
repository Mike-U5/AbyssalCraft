package com.shinoow.abyssalcraft.common.entity.ai;

import java.util.List;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.potion.Potion;
import net.minecraft.util.Vec3;

public class EntityAIAvoidPlague extends EntityAIBase {
	public final IEntitySelector field_98218_a = new IEntitySelector() {
		/**
		 * Return whether the specified entity is applicable to this filter.
		 */
		public boolean isEntityApplicable(Entity target) {
			// Don't bother running if already infected
			if (EntityAIAvoidPlague.this.theEntity.isPotionActive(Potion.potionTypes[AbyssalCraftAPI.potionId2])) {
				return false;
			}
			// Ruuuuuuun
			if (target.isEntityAlive() && EntityAIAvoidPlague.this.theEntity.getEntitySenses().canSee(target) && target instanceof EntityLivingBase) {
				final EntityLivingBase livingTarget = (EntityLivingBase)target;
				return livingTarget.isPotionActive(Potion.potionTypes[AbyssalCraftAPI.potionId2]);
			}
			return false;
		}
	};
	/** The entity we are attached to */
	private EntityCreature theEntity;
	private double farSpeed;
	private double nearSpeed;
	private Entity closestLivingEntity;
	private float distanceFromEntity;
	/** The PathEntity of our entity */
	private PathEntity entityPathEntity;
	/** The PathNavigate of our entity */
	private PathNavigate entityPathNavigate;
	/** The class of the entity we should avoid */
	@SuppressWarnings("rawtypes")
	private Class targetEntityClass;
	
	@SuppressWarnings("rawtypes")
	public EntityAIAvoidPlague(EntityCreature entity, Class targetClazz, float distance, double farSpeed, double nearSpeed) {
		this.theEntity = entity;
		this.targetEntityClass = targetClazz;
		this.distanceFromEntity = distance;
		this.farSpeed = farSpeed;
		this.nearSpeed = nearSpeed;
		this.entityPathNavigate = entity.getNavigator();
		this.setMutexBits(1);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute() {
		if (this.targetEntityClass == EntityPlayer.class) {
			if (this.theEntity instanceof EntityTameable && ((EntityTameable) this.theEntity).isTamed()) {
				return false;
			}

			this.closestLivingEntity = this.theEntity.worldObj.getClosestPlayerToEntity(this.theEntity, (double) this.distanceFromEntity);

			if (this.closestLivingEntity == null) {
				return false;
			}
		} else {
			List<?> list = this.theEntity.worldObj.selectEntitiesWithinAABB(this.targetEntityClass, this.theEntity.boundingBox.expand((double) this.distanceFromEntity, 3.0D, (double) this.distanceFromEntity), this.field_98218_a);

			if (list.isEmpty()) {
				return false;
			}

			this.closestLivingEntity = (Entity) list.get(0);
		}

		Vec3 vec3 = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.theEntity, 16, 7, Vec3.createVectorHelper(this.closestLivingEntity.posX, this.closestLivingEntity.posY, this.closestLivingEntity.posZ));

		if (vec3 == null) {
			return false;
		} else if (this.closestLivingEntity.getDistanceSq(vec3.xCoord, vec3.yCoord, vec3.zCoord) < this.closestLivingEntity.getDistanceSqToEntity(this.theEntity)) {
			return false;
		} else {
			this.entityPathEntity = this.entityPathNavigate.getPathToXYZ(vec3.xCoord, vec3.yCoord, vec3.zCoord);
			return this.entityPathEntity == null ? false : this.entityPathEntity.isDestinationSame(vec3);
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean continueExecuting() {
		return !this.entityPathNavigate.noPath();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting() {
		this.entityPathNavigate.setPath(this.entityPathEntity, this.farSpeed);
	}

	/**
	 * Resets the task
	 */
	public void resetTask() {
		this.closestLivingEntity = null;
	}

	/**
	 * Updates the task
	 */
	public void updateTask() {
		if (this.theEntity.getDistanceSqToEntity(this.closestLivingEntity) < 49.0D) {
			this.theEntity.getNavigator().setSpeed(this.nearSpeed);
		} else {
			this.theEntity.getNavigator().setSpeed(this.farSpeed);
		}
	}
}