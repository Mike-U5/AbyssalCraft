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

import java.util.Calendar;
import java.util.UUID;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.entity.IDreadEntity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityDreadguard extends DreadEntity implements IDreadEntity {

	private static final UUID attackDamageBoostUUID = UUID.fromString("648D7064-6A60-4F59-8ABE-C2C23A6DD7A9");
	private static final AttributeModifier attackDamageBoost = new AttributeModifier(attackDamageBoostUUID, "Halloween Attack Damage Boost", 5.0D, 0);

	public EntityDreadguard(World par1World) {
		super(par1World);
		setSize(1.0F, 3.0F);
		isImmuneToFire = true;
		tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, true));
		tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 1.0D));
		tasks.addTask(4, new EntityAIWander(this, 1.0D));
		tasks.addTask(5, new EntityAILookIdle(this));
		tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);

		if(AbyssalCraft.hardcoreMode){
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(240.0D);
			getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(20.0D);
		} else {
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(120.0D);
			getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(10.0D);
		}
	}

	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}

	@Override
	public void onDeath(DamageSource par1DamageSource) {
		if (par1DamageSource.getEntity() instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer)par1DamageSource.getEntity();
			entityplayer.addStat(AbyssalCraft.killdreadguard, 1);
		}
		super.onDeath(par1DamageSource);
	}

	@Override
	public boolean attackEntityAsMob(Entity target) {
		if (super.attackEntityAsMob(target)) {
			if (target instanceof EntityLivingBase) {
				final Vec3 vec = this.getLookVec();
				target.motionX = vec.xCoord;
				target.motionY = vec.yCoord;
				target.motionZ = vec.zCoord;
			}
		}
		return super.attackEntityAsMob(target);
	}

	@Override
	public int getTotalArmorValue() {
		int var1 = super.getTotalArmorValue() + 2;

		if (var1 > 20)
			var1 = 20;

		return var1;
	}

	@Override
	protected boolean isAIEnabled() {
		return true;
	}

	@Override
	protected String getLivingSound() {
		return "abyssalcraft:dreadguard.idle";
	}

	@Override
	protected String getHurtSound() {
		return "abyssalcraft:dreadguard.hit";
	}

	@Override
	protected String getDeathSound() {
		return "abyssalcraft:dreadguard.death";
	}

	protected void playStepSound(int par1, int par2, int par3, int par4) {
		worldObj.playSoundAtEntity(this, "mob.zombie.step", 0.15F, 1.0F);
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.UNDEFINED;
	}

	@Override
	protected void dropFewItems(boolean para, int lootLvl) {
		float drp = 1.2F + (lootLvl*0.6F);
		int finalAmt = (drp % 1 > Math.random()) ? (int)Math.ceil(drp) : (int)Math.floor(drp);
		
		entityDropItem(new ItemStack(AbyssalCraft.Dreadshard, finalAmt), 0);
	}

	@Override
	public void onLivingUpdate() {
		if (worldObj.isRemote){
			setCurrentItemOrArmor(1, new ItemStack(AbyssalCraft.bootsD));
			setCurrentItemOrArmor(3, new ItemStack(AbyssalCraft.plateD));
			setCurrentItemOrArmor(4, new ItemStack(AbyssalCraft.helmetD));
			setCurrentItemOrArmor(2, new ItemStack(AbyssalCraft.legsD));
		}
		super.onLivingUpdate();
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData) {
		par1EntityLivingData = super.onSpawnWithEgg(par1EntityLivingData);

		IAttributeInstance attribute = getEntityAttribute(SharedMonsterAttributes.attackDamage);
		Calendar calendar = worldObj.getCurrentDate();

		if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && rand.nextFloat() < 0.25F)
			attribute.applyModifier(attackDamageBoost);

		return par1EntityLivingData;
	}
}
