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

import com.shinoow.abyssalcraft.api.biome.IDarklandsBiome;
import com.shinoow.abyssalcraft.api.entity.IAntiEntity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class EntityAntiPlayer extends EntityMob implements IAntiEntity {
	private PathEntity pathToEntity;

	public EntityAntiPlayer(World par1World) {
		super(par1World);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 0.35D, true));
		tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 0.35D));
		tasks.addTask(4, new EntityAIWander(this, 0.35D));
		tasks.addTask(5, new EntityAILookIdle(this));
		tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1.0D);
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(8.5D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.4D);
	}

	@Override
	protected boolean isAIEnabled() {
		return true;
	}

	@Override
	public boolean canDespawn(){
		return false;
	}
	
	@Override
	protected String getLivingSound()
	{
		return "abyssalcraft:antiplayer.idle";
	}
	
	@Override
	protected String getDeathSound() {
		return "abyssalcraft:antiplayer.death";
	}

	@Override
	public void onDeath(DamageSource src) {
		boolean flag = worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
		if(!worldObj.isRemote) {
			worldObj.createExplosion(this, posX, posY, posZ, 1, flag);
		}
		super.onDeath(src);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData entity) {
		entity = super.onSpawnWithEgg(entity);
		setCanPickUpLoot(false);

		return entity;
	}
	
	@Override
	public void onLivingUpdate() {
    	if(isInDarklands(this)) {
    		this.attackEntityFrom(DamageSource.outOfWorld, 99999);
    	}
        super.onLivingUpdate();
	}
	
	public boolean isInDarklands(Entity entity) {
		if (!worldObj.isRemote) {
			int roundX = (int)Math.round(entity.posX);
			int roundZ = (int)Math.round(entity.posZ);
            BiomeGenBase biome = worldObj.getBiomeGenForCoords(roundX, roundZ);
        	if(!(biome instanceof IDarklandsBiome)) {
        		return true;
        	}
        }
		return false;
	}
	
	@Override
	public int getTotalArmorValue() {
		return 30;
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource src, float dmg) {
		if (src == DamageSource.outOfWorld) {
			return super.attackEntityFrom(src, dmg);
		} else if (src.getEntity() instanceof Entity || src.isExplosion()) {
			return super.attackEntityFrom(src, 0);
		}
		
		return false;
	}
	
	/** Don't commit suicide **/
	@Override
	protected Entity findPlayerToAttack() {
        EntityPlayer player = this.worldObj.getClosestVulnerablePlayerToEntity(this, 16.0D);
        if (!isInDarklands(player)) {
        	return null;
        }
        return player != null && this.canEntityBeSeen(player) ? player : null;
    }
	
	
	@Override
	protected void updateEntityActionState() {
        this.worldObj.theProfiler.startSection("ai");

        this.hasAttacked = this.isMovementCeased();
        float f4 = 16.0F;

        if (this.entityToAttack == null) {
            this.entityToAttack = this.findPlayerToAttack();

            if (this.entityToAttack != null) {
                this.pathToEntity = this.worldObj.getPathEntityToEntity(this, this.entityToAttack, f4, true, false, false, true);
            }
        } else if (this.entityToAttack.isEntityAlive())  {
            float f = this.entityToAttack.getDistanceToEntity(this);

            if (this.canEntityBeSeen(this.entityToAttack)) {
                this.attackEntity(this.entityToAttack, f);
            }
        } else {
            this.entityToAttack = null;
        }

        if (this.entityToAttack instanceof EntityPlayerMP && ((EntityPlayerMP)this.entityToAttack).theItemInWorldManager.isCreative()) {
            this.entityToAttack = null;
        }

        this.worldObj.theProfiler.endSection();

        if (!this.hasAttacked && this.entityToAttack != null && (this.pathToEntity == null || this.rand.nextInt(20) == 0)) {
            this.pathToEntity = this.worldObj.getPathEntityToEntity(this, this.entityToAttack, f4, true, false, false, true);
        } else if (!this.hasAttacked && (this.pathToEntity == null && this.rand.nextInt(180) == 0 || this.rand.nextInt(120) == 0 || this.fleeingTick > 0) && this.entityAge < 100) {
            this.updateWanderPath();
        }

        int i = MathHelper.floor_double(this.boundingBox.minY + 0.5D);
        boolean flag = this.isInWater();
        boolean flag1 = this.handleLavaMovement();
        this.rotationPitch = 0.0F;

        if (this.pathToEntity != null && this.rand.nextInt(100) != 0) {
            this.worldObj.theProfiler.startSection("followpath");
            Vec3 vec3 = this.pathToEntity.getPosition(this);
            double d0 = (double)(this.width * 2.0F);

            while (vec3 != null && vec3.squareDistanceTo(this.posX, vec3.yCoord, this.posZ) < d0 * d0) {
                this.pathToEntity.incrementPathIndex();

                if (this.pathToEntity.isFinished()) {
                    vec3 = null;
                    this.pathToEntity = null;
                } else {
                    vec3 = this.pathToEntity.getPosition(this);
                }
            }

            this.isJumping = false;

            if (vec3 != null) {
                double d1 = vec3.xCoord - this.posX;
                double d2 = vec3.zCoord - this.posZ;
                double d3 = vec3.yCoord - (double)i;
                float f1 = (float)(Math.atan2(d2, d1) * 180.0D / Math.PI) - 90.0F;
                float f2 = MathHelper.wrapAngleTo180_float(f1 - this.rotationYaw);
                this.moveForward = (float)this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();

                if (f2 > 30.0F) {
                    f2 = 30.0F;
                }

                if (f2 < -30.0F) {
                    f2 = -30.0F;
                }

                this.rotationYaw += f2;

                if (this.hasAttacked && this.entityToAttack != null) {
                    double d4 = this.entityToAttack.posX - this.posX;
                    double d5 = this.entityToAttack.posZ - this.posZ;
                    float f3 = this.rotationYaw;
                    this.rotationYaw = (float)(Math.atan2(d5, d4) * 180.0D / Math.PI) - 90.0F;
                    f2 = (f3 - this.rotationYaw + 90.0F) * (float)Math.PI / 180.0F;
                    this.moveStrafing = -MathHelper.sin(f2) * this.moveForward * 1.0F;
                    this.moveForward = MathHelper.cos(f2) * this.moveForward * 1.0F;
                }

                if (d3 > 0.0D) {
                    this.isJumping = true;
                }
            }

            if (this.entityToAttack != null)
            {
                this.faceEntity(this.entityToAttack, 30.0F, 30.0F);
            }

            if (this.isCollidedHorizontally && !this.hasPath())
            {
                this.isJumping = true;
            }

            if (this.rand.nextFloat() < 0.8F && (flag || flag1))
            {
                this.isJumping = true;
            }

            this.worldObj.theProfiler.endSection();
        } else  {
            ///super.updateEntityActionState();
        	++this.entityAge;
            this.pathToEntity = null;
        }
    }
	
	@Override
	protected void updateWanderPath() {
        this.worldObj.theProfiler.startSection("stroll");
        boolean flag = false;
        int i = -1;
        int j = -1;
        int k = -1;
        float f = -99999.0F;

        for (int l = 0; l < 10; ++l) {
            int i1 = MathHelper.floor_double(this.posX + (double)this.rand.nextInt(13) - 6.0D);
            int j1 = MathHelper.floor_double(this.posY + (double)this.rand.nextInt(7) - 3.0D);
            int k1 = MathHelper.floor_double(this.posZ + (double)this.rand.nextInt(13) - 6.0D);
            BiomeGenBase biome = worldObj.getBiomeGenForCoords(i, k);
            if (!(biome instanceof IDarklandsBiome)) {
            	return;
            }
            
            float f1 = this.getBlockPathWeight(i1, j1, k1);

            if (f1 > f) {
                f = f1;
                i = i1;
                j = j1;
                k = k1;
                flag = true;
            }
        }

        if (flag) {
            this.pathToEntity = this.worldObj.getEntityPathToXYZ(this, i, j, k, 10.0F, true, false, false, true);
        }

        this.worldObj.theProfiler.endSection();
    }

}
