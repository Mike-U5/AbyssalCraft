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
package com.shinoow.abyssalcraft.common.entity.anti;

import com.shinoow.abyssalcraft.api.entity.IOmotholEntity;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityFallenHero extends EntityMob implements IRangedAttackMob, IOmotholEntity {

	private EntityAIArrowAttack aiArrowAttack = new EntityAIArrowAttack(this, 1.0D, 20, 60, 15.0F);

	public EntityFallenHero(World world) {
		super(world);
		tasks.addTask(1, new EntityAISwimming(this));
		tasks.addTask(5, new EntityAIWander(this, 1.0D));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(6, new EntityAILookIdle(this));
		targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		tasks.addTask(4, aiArrowAttack);
		isImmuneToFire = true;
		
		Item bow = GameRegistry.findItem("Botania", "livingwoodBow");
		if (bow == null) {
			bow = Items.bow;
		}
		setCurrentItemOrArmor(0, new ItemStack(bow));
	}
	
	
	@Override
	protected float getSoundPitch() {
		return 0.85F + (rand.nextFloat() * 0.2F);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(200.0D);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(48.0D);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObject(13, new Byte((byte)0));
	}
	
	@Override
	public void onLivingUpdate() {
		// Max lifetime of 10 minutes. Less at low health.
		if (ticksExisted > (getHealth() * 60)) {
            setHealth(0F);
		}
		super.onLivingUpdate();
	}

	@Override
	public boolean isAIEnabled() {
		return true;
	}

	@Override
	protected String getLivingSound() {
		return "abyssalcraft:stray.ambient";
	}

	@Override
	protected String getHurtSound() {
		return "abyssalcraft:stray.hurt";
	}

	@Override
	protected String getDeathSound() {
		return "abyssalcraft:stray.death";
	}

	@Override
	protected void func_145780_a(int par1, int par2, int par3, Block block) {
		playSound("mob.skeleton.step", 0.15F, 0.5F);
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.UNDEAD;
	}
	
	@Override
	public int getTotalArmorValue() {
		return 5;
	}

	/**
	 * Attack the specified entity using a ranged attack.
	 */
	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float dmg) {
        EntityArrow entityarrow = new EntityArrow(this.worldObj, this, target, 1.6F, (float)(14 - this.worldObj.difficultySetting.getDifficultyId() * 4));
        final int power = 5;
        final int punch = target.isAirBorne ? 2 : 0;
        entityarrow.setDamage((double)(dmg * 2.0F) + this.rand.nextGaussian() * 0.25D + (double)((float)this.worldObj.difficultySetting.getDifficultyId() * 0.11F));

        if (power > 0) {
            entityarrow.setDamage(entityarrow.getDamage() + (double)power * 0.5D + 0.5D);
        }

        if (punch > 0) {
            entityarrow.setKnockbackStrength(punch);
        }

        playSound("random.bow", 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        worldObj.spawnEntityInWorld(entityarrow);
	}
	
	public boolean attackEntityFrom(DamageSource dmgSrc, float amount) {
		if (dmgSrc == DamageSource.inWall) {
			return false;
		}
		return super.attackEntityFrom(dmgSrc, amount);
	}

	@Override
	public double getYOffset() {
		return super.getYOffset() - 0.5D;
	}

}