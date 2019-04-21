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

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
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
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.entity.IDreadEntity;

public class EntityLesserDreadbeast extends EntityMob implements IDreadEntity, IRangedAttackMob {

	private EntityAIArrowAttack arrowAttack = new EntityAIArrowAttack(this, 1.0D, 20, 60, 15.0F);
	private EntityAIAttackOnCollide attackOnCollide = new EntityAIAttackOnCollide(this, EntityPlayer.class, 0.35D, true);
	private int DreadSperm = 19;

	public EntityLesserDreadbeast(World par1World) {
		super(par1World);
		setSize(1.8F, 1.8F);
		tasks.addTask(2, attackOnCollide);
		tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 0.7D));
		tasks.addTask(4, new EntityAIWander(this, 0.7D));
		tasks.addTask(5, new EntityAILookIdle(this));
		tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		isImmuneToFire = true;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.4D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.7D);

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
	public boolean attackEntityAsMob(Entity par1Entity){

		if (super.attackEntityAsMob(par1Entity))
			if (par1Entity instanceof EntityLivingBase)
				((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(AbyssalCraft.Dplague.id, 100));
		return super.attackEntityAsMob(par1Entity);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObject(18, Byte.valueOf((byte)0));
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if (!worldObj.isRemote)
			setBesideClimbableBlock(isCollidedHorizontally);
	}

	@Override
	protected String getLivingSound() {
		return "abyssalcraft:dreadslug.idle";
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
	protected void func_145780_a(int par1, int par2, int par3, Block par4) {
		worldObj.playSoundAtEntity(this, "mob.zombie.step", 0.15F, 1.0F);
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

		if (par1)
			b0 = (byte)(b0 | 1);
		else
			b0 &= -2;

		dataWatcher.updateObject(18, Byte.valueOf(b0));
	}

	@Override
	protected void fall(float par1) {}
	
	@Override
	protected void dropFewItems(boolean para, int lootLvl) {
		int finalFrag = 1 + (int)Math.ceil(lootLvl*0.5);
		int finalShard = 1 + (int)Math.floor(lootLvl*0.5);
		
		entityDropItem(new ItemStack(AbyssalCraft.dreadfragment, finalFrag), 0);
		entityDropItem(new ItemStack(AbyssalCraft.Dreadshard, finalShard), 0);
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.ARTHROPOD;
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();

		if(entityToAttack != null && getDistanceToEntity(entityToAttack) >= 2)
			setAttackMode(true);
		else setAttackMode(false);

		if(worldObj.rand.nextInt(10000) == 0 && DreadSperm > 4) {
			if(!worldObj.isRemote){
				EntityGreaterDreadSpawn spawn = new EntityGreaterDreadSpawn(worldObj);
				spawn.copyLocationAndAnglesFrom(this);
				DreadSperm -= 5;
				///this.attackEntityFrom(DamageSource.causeMobDamage(this), 15);
			}
		} else if(worldObj.rand.nextInt(200) == 0 && DreadSperm > 0) {
			if(!worldObj.isRemote){
				EntityDreadSpawn spawn = new EntityDreadSpawn(worldObj);
				spawn.copyLocationAndAnglesFrom(this);
				worldObj.spawnEntityInWorld(spawn);
				DreadSperm -= 1;
				///this.attackEntityFrom(DamageSource.causeMobDamage(this), 3);
			}
		}
	}

	private void setAttackMode(boolean par1){
		tasks.removeTask(arrowAttack);
		tasks.removeTask(attackOnCollide);

		if(par1)
			tasks.addTask(2, arrowAttack);
		else tasks.addTask(2, attackOnCollide);
	}

	@Override
	public void onDeath(DamageSource par1DamageSource) {

		if(!worldObj.isRemote){
			EntityGreaterDreadSpawn spawn1 = new EntityGreaterDreadSpawn(worldObj);
			EntityGreaterDreadSpawn spawn2 = new EntityGreaterDreadSpawn(worldObj);
			spawn1.copyLocationAndAnglesFrom(this);
			spawn2.copyLocationAndAnglesFrom(this);
			worldObj.spawnEntityInWorld(spawn1);
			worldObj.spawnEntityInWorld(spawn2);
		}
		super.onDeath(par1DamageSource);
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase p_82196_1_,
			float p_82196_2_) {
		EntityDreadSlug entitydreadslug = new EntityDreadSlug(worldObj, this);
		double d0 = p_82196_1_.posX - posX;
		double d1 = p_82196_1_.posY + p_82196_1_.getEyeHeight() - 1.100000023841858D - entitydreadslug.posY;
		double d2 = p_82196_1_.posZ - posZ;
		float f1 = MathHelper.sqrt_double(d0 * d0 + d2 * d2) * 0.2F;
		entitydreadslug.setThrowableHeading(d0, d1 + f1, d2, 1.6F, 12.0F);
		playSound("random.bow", 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
		worldObj.spawnEntityInWorld(entitydreadslug);
	}
}
