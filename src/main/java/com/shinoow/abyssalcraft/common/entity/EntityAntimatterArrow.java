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

import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityAntimatterArrow extends EntityArrow {
	protected Block inTile;
	protected int xTile = -1;
	protected int yTile = -1;
	protected int zTile = -1;
	protected int inData;
	protected int ticksInGround;
	protected int ticksInAir;
	protected int powerLvl;
	protected int knockbackStrength;
	protected boolean inGround;

	public EntityAntimatterArrow(World world, EntityLivingBase entity, float par3) {
		super(world, entity, par3);
	}

	public EntityAntimatterArrow(World world, EntityLivingBase entity, EntityLivingBase target, float par4, float par5) {
		super(world, entity, target, par4, par5);
	}

	public EntityAntimatterArrow(World world, double par2, double par4, double par6) {
		super(world, par2, par4, par6);
	}

	public EntityAntimatterArrow(World world) {
		super(world);
	}
	
	protected void onHitEffect(Entity entity, int dmg) {
		if (entity instanceof EntityLivingBase) {
			EntityLivingBase entityLiving = (EntityLivingBase)entity;
			Potion potion = AbyssalCraft.antiMatter;
			if(entityLiving.getActivePotionEffect(potion) == null) {
				final int duration = (int)Math.round(dmg * 15);
				entityLiving.addPotionEffect(new PotionEffect(potion.id, duration));
			}
		}
	}
	
	@Override
	public void onUpdate() {
		onEntityUpdate();
		if ((this.prevRotationPitch == 0.0F) && (this.prevRotationYaw == 0.0F)) {
			float var1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.prevRotationYaw = (this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / 3.141592653589793D));
			this.prevRotationPitch = (this.rotationPitch = (float)(Math.atan2(this.motionY, var1) * 180.0D / 3.141592653589793D));
		}
		Block block = this.worldObj.getBlock(this.xTile, this.yTile, this.zTile);
		if (block != Blocks.air) {
			block.setBlockBoundsBasedOnState(this.worldObj, this.xTile, this.yTile, this.zTile);
			AxisAlignedBB var2 = block.getCollisionBoundingBoxFromPool(this.worldObj, this.xTile, this.yTile, this.zTile);
			if ((var2 != null) && (var2.isVecInside(Vec3.createVectorHelper(this.posX, this.posY, this.posZ))))
				this.inGround = true;
		}
		if (this.arrowShake > 0)
			this.arrowShake -= 1;
		if (this.inGround) {
			Block blockInside = this.worldObj.getBlock(this.xTile, this.yTile, this.zTile);
			int metaInside = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);
			if ((blockInside == this.inTile) && (metaInside == this.inData)) {
				this.ticksInGround++;
				if (this.ticksInGround >= 1200)
					setDead();
			} else {
				this.inGround = false;
				this.motionX *= this.rand.nextFloat() * 0.2F;
				this.motionY *= this.rand.nextFloat() * 0.2F;
				this.motionZ *= this.rand.nextFloat() * 0.2F;
				this.ticksInGround = 0;
				this.ticksInAir = 0;
			}
		} else {
			this.ticksInAir += 1;
			Vec3 posVector = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
			Vec3 newPosVector = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
			MovingObjectPosition moPos = this.worldObj.func_147447_a(posVector, newPosVector, false, true, false);
			posVector = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
			newPosVector = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
			if (moPos != null)
				newPosVector = Vec3.createVectorHelper(moPos.hitVec.xCoord, moPos.hitVec.yCoord, moPos.hitVec.zCoord);
			Entity entity = null;
			List<?> entityList = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
			double var7 = 0.0D;

			for (int i = 0; i < entityList.size(); i++) {
				Entity var10 = (Entity)entityList.get(i);
				if ((var10.canBeCollidedWith()) && ((var10 != this.shootingEntity) || (this.ticksInAir >= 5))) {
					float var11 = 0.3F;
					AxisAlignedBB var12 = var10.boundingBox.expand(var11, var11, var11);
					MovingObjectPosition var13 = var12.calculateIntercept(posVector, newPosVector);
					if (var13 != null) {
						double var14 = posVector.distanceTo(var13.hitVec);
						if ((var14 < var7) || (var7 == 0.0D)) {
							entity = var10;
							var7 = var14;
						}
					}
				}
			}
			if (entity != null) {
				moPos = new MovingObjectPosition(entity);
			}

			if (moPos != null) {
				if (moPos.entityHit != null) {
					float dmgFloat = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
					int dmgInt = MathHelper.ceiling_double_int(dmgFloat * getDamage());
					if (getIsCritical()) {
						dmgInt += (dmgInt / 4) + 1;
					}
					DamageSource dmgSrc = null;
					if (this.shootingEntity == null) {
						dmgSrc = DamageSource.causeArrowDamage(this, this);
					} else {
						dmgSrc = DamageSource.causeArrowDamage(this, this.shootingEntity);
					}
					if ((isBurning()) && (!(moPos.entityHit instanceof EntityEnderman)))
						moPos.entityHit.setFire(5);
					if((shouldDamage(moPos.entityHit))) {
						moPos.entityHit.hurtResistantTime = 0;
						if ((moPos.entityHit.attackEntityFrom(dmgSrc, 0)) && !(moPos.entityHit instanceof EntityEnderman)) {
							onHitEffect(moPos.entityHit, dmgInt);
							if ((moPos.entityHit instanceof EntityLivingBase)) {
								EntityLivingBase entityLivingBase = (EntityLivingBase)moPos.entityHit;
								if (!this.worldObj.isRemote)
									entityLivingBase.setArrowCountInEntity(entityLivingBase.getArrowCountInEntity() + 1);
								if (this.knockbackStrength > 0) {
									float var26 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
									if (var26 > 0.0F) {
										moPos.entityHit.addVelocity(this.motionX * this.knockbackStrength * 0.6D / var26, 0.1D, this.motionZ * this.knockbackStrength * 0.6D / var26);
									}
								}
								if (this.shootingEntity != null) {
									EnchantmentHelper.func_151384_a(entityLivingBase, this.shootingEntity);
									EnchantmentHelper.func_151385_b((EntityLivingBase)this.shootingEntity, entityLivingBase);
								}
								if ((this.shootingEntity != null) && (moPos.entityHit != this.shootingEntity) && ((moPos.entityHit instanceof EntityPlayer)) && ((this.shootingEntity instanceof EntityPlayerMP)))
									((EntityPlayerMP)this.shootingEntity).playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(6, 0.0F));
							}
							playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
							if (!(moPos.entityHit instanceof EntityEnderman)) {
								setDead();
							}
						}
					} else {
						this.motionX *= -0.1D;
						this.motionY *= -0.1D;
						this.motionZ *= -0.1D;
						this.rotationYaw += 180.0F;
						this.prevRotationYaw += 180.0F;
						this.ticksInAir = 0;
					}
				} else {
					this.xTile = moPos.blockX;
					this.yTile = moPos.blockY;
					this.zTile = moPos.blockZ;
					this.inTile = this.worldObj.getBlock(this.xTile, this.yTile, this.zTile);
					this.inData = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);
					this.motionX = ((float)(moPos.hitVec.xCoord - this.posX));
					this.motionY = ((float)(moPos.hitVec.yCoord - this.posY));
					this.motionZ = ((float)(moPos.hitVec.zCoord - this.posZ));
					float var20 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
					this.posX -= this.motionX / var20 * 0.05D;
					this.posY -= this.motionY / var20 * 0.05D;
					this.posZ -= this.motionZ / var20 * 0.05D;
					playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
					this.inGround = true;
					this.arrowShake = 7;
					setIsCritical(false);
					if (this.inTile != Blocks.air)
						this.inTile.onEntityCollidedWithBlock(this.worldObj, this.xTile, this.yTile, this.zTile, this);
				}
			}
			if (getIsCritical()) {
				for (int i = 0; i < 4; i++) {
					this.worldObj.spawnParticle("crit", this.posX + this.motionX * i / 4.0D, this.posY + this.motionY * i / 4.0D, this.posZ + this.motionZ * i / 4.0D, -this.motionX, -this.motionY + 0.2D, -this.motionZ);
				}
			}
			this.posX += this.motionX;
			this.posY += this.motionY;
			this.posZ += this.motionZ;
			float var20 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.rotationYaw = ((float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / 3.141592653589793D));
			for (this.rotationPitch = ((float)(Math.atan2(this.motionY, var20) * 180.0D / 3.141592653589793D)); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F);
			while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
				this.prevRotationPitch += 360.0F;
			while (this.rotationYaw - this.prevRotationYaw < -180.0F)
				this.prevRotationYaw -= 360.0F;
			while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
				this.prevRotationYaw += 360.0F;
			this.rotationPitch = (this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F);
			this.rotationYaw = (this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F);
			float var22 = 0.99F;
			float var11 = 0.05F;
			if (isInWater()) {
				for (int var25 = 0; var25 < 4; var25++) {
					float var26 = 0.25F;
					this.worldObj.spawnParticle("bubble", this.posX - this.motionX * var26, this.posY - this.motionY * var26, this.posZ - this.motionZ * var26, this.motionX, this.motionY, this.motionZ);
				}
				var22 = 0.8F;
			}
			this.motionX *= var22;
			this.motionY *= var22;
			this.motionZ *= var22;
			this.motionY -= var11;
			setPosition(this.posX, this.posY, this.posZ);
			func_145775_I();
		}
	}
	
	private boolean shouldDamage(Entity entity) {
		return (entity != this.shootingEntity);
	}
}
