package com.shinoow.abyssalcraft.common.entity.ai;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;

public class EntityAINearestTargetFix extends EntityAITarget {
	private final Class<?> targetClass;
	private final int targetChance;
	/** Instance of EntityAINearestAttackableTargetSorter. */
	private final EntityAINearestTargetFix.Sorter theNearestAttackableTargetSorter;
	/**
	 * This filter is applied to the Entity search. Only matching entities will be
	 * targeted. (null -> no restrictions)
	 */
	private final IEntitySelector targetEntitySelector;
	private EntityLivingBase targetEntity;
	
	public EntityAINearestTargetFix(EntityCreature entity, Class<?> targetClass, int targetChance, boolean shouldCheckSight) {
		this(entity, targetClass, targetChance, shouldCheckSight, false);
	}

	public EntityAINearestTargetFix(EntityCreature entity, Class<?> targetClass, int targetChance, boolean shouldCheckSight, boolean nearbyOnly) {
		this(entity, targetClass, targetChance, shouldCheckSight, nearbyOnly, (IEntitySelector) null);
	}

	public EntityAINearestTargetFix(EntityCreature entity, Class<?> targetClass, int targetChance, boolean shouldCheckSight, boolean nearbyOnly, final IEntitySelector selector) {
		super(entity, shouldCheckSight, nearbyOnly);
		this.targetClass = targetClass;
		this.targetChance = targetChance;
		this.theNearestAttackableTargetSorter = new EntityAINearestTargetFix.Sorter(entity);
		this.setMutexBits(1);
		this.targetEntitySelector = new IEntitySelector() {
			/**
			 * Return whether the specified entity is applicable to this filter.
			 */
			public boolean isEntityApplicable(Entity entity) {
				if (entity instanceof EntityLivingBase) {
					if (selector != null && !selector.isEntityApplicable(entity)) {
						return false;
					} else {
						return EntityAINearestTargetFix.this.isSuitableTarget((EntityLivingBase) entity, false);
					}
				}
				return false;
			}
		};
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute() {
		if (this.targetChance > 0 && this.taskOwner.getRNG().nextInt(this.targetChance) != 0) {
			return false;
		} else {
			double d0 = this.getTargetDistance();
			List<?> list = this.taskOwner.worldObj.selectEntitiesWithinAABB(this.targetClass, this.taskOwner.boundingBox.expand(d0, 4.0D, d0), this.targetEntitySelector);
			Collections.sort(list, this.theNearestAttackableTargetSorter);

			if (list.isEmpty()) {
				return false;
			} else {
				this.targetEntity = (EntityLivingBase) list.get(0);
				return true;
			}
		}
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting() {
		this.taskOwner.setAttackTarget(this.targetEntity);
		super.startExecuting();
	}

	public static class Sorter implements Comparator<Object> {
		private final Entity theEntity;
		public Sorter(Entity p_i1662_1_) {
			this.theEntity = p_i1662_1_;
		}

		public int compare(Entity entityA, Entity entityB) {
			double d0 = this.theEntity.getDistanceSqToEntity(entityA);
			double d1 = this.theEntity.getDistanceSqToEntity(entityB);
			return d0 < d1 ? -1 : (d0 > d1 ? 1 : 0);
		}

		public int compare(Object p_compare_1_, Object p_compare_2_) {
			return this.compare((Entity) p_compare_1_, (Entity) p_compare_2_);
		}
	}
}