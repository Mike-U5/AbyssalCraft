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

import java.util.List;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.entity.IDreadEntity;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityLesserDreadbeast extends DreadEntity implements IDreadEntity, IRangedAttackMob {

	private EntityAIArrowAttack arrowAttack = new EntityAIArrowAttack(this, 1.0D, 20, 60, 15.0F);
	private EntityAIAttackOnCollide attackOnCollide = new EntityAIAttackOnCollide(this, EntityPlayer.class, 0.35D, true);
	
	public EntityLesserDreadbeast(World world) {
		super(world);
		setSize(1.8F, 1.65F);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(2, attackOnCollide);
		tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 0.7D));
		tasks.addTask(4, new EntityAIWander(this, 0.7D));
		tasks.addTask(5, new EntityAILookIdle(this));
		tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		isImmuneToFire = true;
		experienceValue = 25;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.5D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);

		if(AbyssalCraft.hardcoreMode){
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(600.0D);
			getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(36.0D);
		} else {
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(300.0D);
			getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(18.0D);
		}
	}

	@Override
	protected boolean isAIEnabled() {
		return true;
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObject(18, Byte.valueOf((byte)0));
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if (!worldObj.isRemote) {
			setBesideClimbableBlock(isCollidedHorizontally);
		}
	}
	
	@Override
	protected float getSoundPitch() {
		return 0.3F;
	}

	@Override
	protected String getHurtSound() {
		return "abyssalcraft:dreadslug.hurt";
	}

	@Override
	protected String getDeathSound() {
		return "abyssalcraft:dreadslug.death";
	}

	@Override
	protected void func_145780_a(int par1, int par2, int par3, Block block) {
		worldObj.playSoundAtEntity(this, "mob.spider.step", 0.15F, 0.8F);
	}

	@Override
	public boolean isOnLadder() {
		return isBesideClimbableBlock();
	}

	/**
	 * Returns true if the WatchableObject (Byte) is 0x01 otherwise returns false. The WatchableObject is updated using
	 * setBesideClimableBlock.
	 */
	public boolean isBesideClimbableBlock() {
		return (dataWatcher.getWatchableObjectByte(18) & 1) != 0;
	}

	/**
	 * Updates the WatchableObject (Byte) created in entityInit(), setting it to 0x01 if par1 is true or 0x00 if it is
	 * false.
	 */
	public void setBesideClimbableBlock(boolean par1) {
		byte b0 = dataWatcher.getWatchableObjectByte(18);

		if (par1) {
			b0 = (byte)(b0 | 1);
		} else {
			b0 &= -2;
		}

		dataWatcher.updateObject(18, Byte.valueOf(b0));
	}

	@Override
	protected void fall(float par1) {}
	
	@Override
	protected void dropFewItems(boolean para, int lootLvl) {
		final int fragAmount = 4 + lootLvl;
		int shardAmount = 1 + (int)Math.floor(lootLvl/2);
		
		if (lootLvl % 2 != 0 && worldObj.rand.nextBoolean()) {
			shardAmount += 1;
		}
		
		entityDropItem(new ItemStack(AbyssalCraft.dreadfragment, fragAmount), 0);
		entityDropItem(new ItemStack(AbyssalCraft.Dreadshard, shardAmount), 0);
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.ARTHROPOD;
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();

		if(entityToAttack != null && getDistanceToEntity(entityToAttack) >= 2) {
			setAttackMode(true);
		} else {
			setAttackMode(false);
		}

		if(!worldObj.isRemote && !isAtSpawnLimit() && this.ticksExisted % 300 == 0) {
			final float pitch = (0.5F + (float)(Math.random()/2));
			playSound("abyssalcraft:spawner.create", 1F, pitch);
			this.spawnChild();
		}
	}
	
	@SuppressWarnings("unchecked")
	private boolean isAtSpawnLimit() {
		final List<EntityDreadSpawn> spawns = worldObj.getEntitiesWithinAABB(EntityChagarothSpawn.class, boundingBox.expand(32D, 32D, 32D));
		return (spawns.size() >= 20);
	}
	
	private void spawnChild() {
		final EntityChagarothSpawn spawn = new EntityChagarothSpawn(worldObj);
		spawn.capturedDrops.clear();
		spawn.setLocationAndAngles(this.posX, this.posY + 1.5D, this.posZ, this.rotationYaw, this.rotationPitch);
		final double vx = -1 + (Math.random() * 2);
		final double vz = -1 + (Math.random() * 2);
		spawn.setVelocity(vx, 0, vz);
		worldObj.spawnEntityInWorld(spawn);
	}

	private void setAttackMode(boolean useRanged) {
		tasks.removeTask(arrowAttack);
		tasks.removeTask(attackOnCollide);

		if(useRanged) {
			tasks.addTask(2, arrowAttack);
		} else {
			tasks.addTask(2, attackOnCollide);
		}
	}

	@Override
	public void onDeath(DamageSource damageSource) {
		if(!worldObj.isRemote){
			this.spawnChild();
			this.spawnChild();
			this.spawnChild();
			this.spawnChild();
		}
		super.onDeath(damageSource);
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float p_82196_2_) {
		final EntityDreadSlug entitydreadslug = new EntityDreadSlug(worldObj, this);
		double x = target.posX - posX;
		double y = target.posY + target.getEyeHeight() - 1.100000023841858D - entitydreadslug.posY;
		double z = target.posZ - posZ;
		float sqrt = MathHelper.sqrt_double(x * x + z * z) * 0.2F;
		entitydreadslug.setThrowableHeading(x, y + sqrt, z, 1.6F, 12.0F);
		playSound("random.bow", 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
		worldObj.spawnEntityInWorld(entitydreadslug);
	}
}
