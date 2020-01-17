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

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.entity.IDreadEntity;
import com.shinoow.abyssalcraft.common.util.SpecialTextUtil;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class EntityChagaroth extends ACMob implements IBossDisplayData, IDreadEntity {

	private int skillTicks;
	public int deathTicks;

	public EntityChagaroth(World world) {
		super(world);
		setSize(3.0F, 4.8F);
		tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 0.0D, true));
		tasks.addTask(3, new EntityAILookIdle(this));
		tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		ignoreFrustumCheck = true;
		isImmuneToFire = true;
	}

	@Override
	public String getCommandSenderName() {
		return EnumChatFormatting.DARK_RED + super.getCommandSenderName();
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		if (super.attackEntityAsMob(entity)) {
			if (entity instanceof EntityLivingBase) {
				((EntityLivingBase)entity).addPotionEffect(new PotionEffect(AbyssalCraft.Dplague.id, 100));
			}
		}
		return super.attackEntityAsMob(entity);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.0D);

		if(AbyssalCraft.hardcoreMode){
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(2000.0D);
			getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(30.0D);
		} else {
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1000.0D);
			getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(15.0D);
		}
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0D);
	}

	@Override
	protected boolean isAIEnabled() {
		return true;
	}

	@Override
	public boolean canBreatheUnderwater() {
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

	@Override
	protected float getSoundVolume() {
		return 4.0F;
	}

	@Override
	public int getTotalArmorValue() {
		return 10;
	}

	@Override
	protected boolean canDespawn() {
		return false;
	}
	
	// Fireball Creation
	private EntityLargeFireball positionFireball(World world, double x, double y, double z, double fallspeed) {
		EntityLargeFireball fireball = new EntityLargeFireball(world);
		fireball.setLocationAndAngles(x, y, z, fireball.rotationYaw, fireball.rotationPitch);
		fireball.setPosition(x, y, z);
		fireball.accelerationY = -fallspeed;
		return fireball;
	}

	@Override
	public void onLivingUpdate() {
		motionX = motionY = motionZ = 0;
		
		// Only perform special actions while alive
		if (deathTicks == 0) {
			EntityPlayer player = worldObj.getClosestPlayerToEntity(this, 32D);
			
			// Regenerate
			if (!worldObj.isRemote && ticksExisted % 5 == 0 && getAttackTarget() == null) {
				heal(1);
			}
			
			// Specials
			skillTicks += 1;
			if (skillTicks > 500) {
				skillTicks = 0;
			}
			performSpecialAttack(skillTicks % 40);
			
			// Fireball Skill
			
			// Chant FIRE
			if (skillTicks == 500) {
				playSound("abyssalcraft:chagaroth.fire", 5F, 1F);
			}
			
			// Execute FIRE
			if (skillTicks == 530) {
				if (!worldObj.isRemote) {
					EntityLivingBase target = getAttackTarget();
					// If not attack target, grab the nearest player
					if (target == null) {
						target = player;
					}
					// Execute attack if there is a target
					if (target != null) {
						double x = target.posX;
						double y = target.posY + 4;
						double z = target.posZ;
						worldObj.spawnEntityInWorld(positionFireball(worldObj, x + 2.5, y, z + 2.5, 0.1D));
						worldObj.spawnEntityInWorld(positionFireball(worldObj, x - 2.5, y, z + 2.5, 0.1D));
						worldObj.spawnEntityInWorld(positionFireball(worldObj, x + 2.5, y, z - 2.5, 0.1D));
						worldObj.spawnEntityInWorld(positionFireball(worldObj, x - 2.5, y, z - 2.5, 0.1D));
						worldObj.spawnEntityInWorld(positionFireball(worldObj, x + 2.5, y, z, 0.1D));
						worldObj.spawnEntityInWorld(positionFireball(worldObj, x - 2.5, y, z, 0.1D));
						worldObj.spawnEntityInWorld(positionFireball(worldObj, x, y, z + 2.5, 0.1D));
						worldObj.spawnEntityInWorld(positionFireball(worldObj, x, y, z - 2.5, 0.1D));
						worldObj.spawnEntityInWorld(positionFireball(worldObj, x, y + 1, z - 2.5, 0.07D));
					}
				}
				skillTicks = -150 + worldObj.rand.nextInt(200);
			}
			
			if(!worldObj.isRemote) {
				if(rand.nextInt(600) == 0 && player != null) {
					EntityChagarothSpawn mob = new EntityChagarothSpawn(worldObj);
					mob.copyLocationAndAnglesFrom(player);
					worldObj.spawnEntityInWorld(mob);
				}
				if(rand.nextInt(600) == 0) {
					EntityDreadSpawn mob = new EntityDreadSpawn(worldObj);
					mob.copyLocationAndAnglesFrom(this);
					worldObj.spawnEntityInWorld(mob);
				}
				EntityChagarothFist fist = new EntityChagarothFist(worldObj);
				fist.copyLocationAndAnglesFrom(this);
				EntityDreadguard dreadGuard = new EntityDreadguard(worldObj);
				dreadGuard.copyLocationAndAnglesFrom(fist);
				if(rand.nextInt(3600) == 0) {
					worldObj.spawnEntityInWorld(fist);
				}

				if(player != null) {
					switch((int)getHealth()){
					case 900:
						worldObj.spawnEntityInWorld(fist);
						damageEntity(DamageSource.generic, 1);
						break;
					case 800:
						worldObj.spawnEntityInWorld(fist);
						damageEntity(DamageSource.generic, 1);
						break;
					case 700:
						worldObj.spawnEntityInWorld(fist);
						damageEntity(DamageSource.generic, 1);
						break;
					case 600:
						worldObj.spawnEntityInWorld(fist);
						damageEntity(DamageSource.generic, 1);
						break;
					case 500:
						worldObj.spawnEntityInWorld(fist);
						damageEntity(DamageSource.generic, 1);
						break;
					case 400:
						worldObj.spawnEntityInWorld(fist);
						damageEntity(DamageSource.generic, 1);
						break;
					case 300:
						worldObj.spawnEntityInWorld(fist);
						damageEntity(DamageSource.generic, 1);
						break;
					case 200:
						worldObj.spawnEntityInWorld(fist);
						damageEntity(DamageSource.generic, 1);
						break;
					case 100:
						worldObj.spawnEntityInWorld(fist);
						worldObj.spawnEntityInWorld(dreadGuard);
						damageEntity(DamageSource.generic, 1);
						break;
					}
				}
			}
		}
		super.onLivingUpdate();
	}
	
	private void performSpecialAttack(int cycle) {
		// Chant FIRE
		if (cycle == 12) {
			playSound("abyssalcraft:chagaroth.fire", 5F, 1F);
			return;
		}
		
		// Execute FIRE
		if (cycle == 13 && !worldObj.isRemote) {
			EntityLivingBase target = getAttackTarget();
			// Execute attack if there is a target
			if (target != null) {
				double x = target.posX;
				double y = target.posY + 4;
				double z = target.posZ;
				worldObj.spawnEntityInWorld(positionFireball(worldObj, x + 2.5, y, z + 2.5, 0.11D));
				worldObj.spawnEntityInWorld(positionFireball(worldObj, x - 2.5, y, z + 2.5, 0.11D));
				worldObj.spawnEntityInWorld(positionFireball(worldObj, x + 2.5, y, z - 2.5, 0.11D));
				worldObj.spawnEntityInWorld(positionFireball(worldObj, x - 2.5, y, z - 2.5, 0.11D));
				worldObj.spawnEntityInWorld(positionFireball(worldObj, x + 2.5, y, z, 0.11D));
				worldObj.spawnEntityInWorld(positionFireball(worldObj, x - 2.5, y, z, 0.11D));
				worldObj.spawnEntityInWorld(positionFireball(worldObj, x, y, z + 2.5, 0.11D));
				worldObj.spawnEntityInWorld(positionFireball(worldObj, x, y, z - 2.5, 0.11D));
				worldObj.spawnEntityInWorld(positionFireball(worldObj, x, y + 1, z - 2.5, 0.07D));
			}
			return;
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("DeathTicks", deathTicks);
		nbt.setInteger("SkillTicks", skillTicks);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		deathTicks = nbt.getInteger("DeathTicks");
		skillTicks = nbt.getInteger("SkillTicks");
	}

	@Override
	public void onDeath(DamageSource dmgSrc) {
		if (dmgSrc.getEntity() instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer)dmgSrc.getEntity();
			entityplayer.addStat(AbyssalCraft.killChagaroth, 1);
		}
		super.onDeath(dmgSrc);
	}

	@Override
	public boolean attackEntityFrom(DamageSource dmgSrc, float amount) {
		if (getAttackTarget() == null) {
			return false;
		}
		return super.attackEntityFrom(dmgSrc, amount);
	}

	@Override
	protected void onDeathUpdate() {
		deathTicks++;

		if (deathTicks <= 200) {
			float f = (rand.nextFloat() - 0.5F) * 8.0F;
			float f1 = (rand.nextFloat() - 0.5F) * 4.0F;
			float f2 = (rand.nextFloat() - 0.5F) * 8.0F;
			if(AbyssalCraft.particleEntity){
				worldObj.spawnParticle("flame", posX + f, posY + 2.0D + f1, posZ + f2, 0.0D, 0.0D, 0.0D);
				worldObj.spawnParticle("lava", posX + f, posY + 2.0D + f1, posZ + f2, 0.0D, 0.0D, 0.0D);
				worldObj.spawnParticle("largesmoke", posX + f, posY + 2.0D + f1, posZ + f2, 0.0D, 0.0D, 0.0D);
				worldObj.spawnParticle("explode", posX + f, posY + 2.0D + f1, posZ + f2, 0.0D, 0.0D, 0.0D);
				if (deathTicks >= 190 && deathTicks <= 200)
					worldObj.spawnParticle("hugeexplosion", posX + f, posY + 2.0D + f1, posZ + f2, 0.0D, 0.0D, 0.0D);
			}
		}

		int i;
		int j;

		if (!worldObj.isRemote) {
			if (deathTicks > 150 && deathTicks % 5 == 0) {
				i = 500;

				while (i > 0) {
					j = EntityXPOrb.getXPSplit(i);
					i -= j;
					worldObj.spawnEntityInWorld(new EntityXPOrb(worldObj, posX, posY, posZ, j));
					if(deathTicks == 100 || deathTicks == 120 || deathTicks == 140 || deathTicks == 160 || deathTicks == 180){
						worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX + posneg(3), posY + rand.nextInt(3), posZ + posneg(3), new ItemStack(AbyssalCraft.dreadfragment, 4)));
						worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX + posneg(3), posY + rand.nextInt(3), posZ + posneg(3), new ItemStack(AbyssalCraft.Dreadshard)));
						worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX + posneg(3), posY + rand.nextInt(3), posZ + posneg(3), new ItemStack(AbyssalCraft.dreadiumingot)));
					}
				}
			}
		}
		if(deathTicks == 20 && !worldObj.isRemote)
			SpecialTextUtil.ChagarothGroup(worldObj, StatCollector.translateToLocal("message.chagaroth.death.1"));
		if(deathTicks == 80 && !worldObj.isRemote)
			SpecialTextUtil.ChagarothGroup(worldObj, StatCollector.translateToLocal("message.chagaroth.death.2"));
		if(deathTicks == 140 && !worldObj.isRemote)
			SpecialTextUtil.ChagarothGroup(worldObj, StatCollector.translateToLocal("message.chagaroth.death.3"));
		if(deathTicks == 200 && !worldObj.isRemote){
			SpecialTextUtil.ChagarothGroup(worldObj, StatCollector.translateToLocal("message.chagaroth.death.4"));
			setDead();
			worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX, posY, posZ, new ItemStack(AbyssalCraft.dreadKey)));
		}
	}

	private int posneg(int num){
		return rand.nextBoolean() ? rand.nextInt(num) : -1 * rand.nextInt(num);
	}
	
	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.ARTHROPOD;
	}
	
	@Override
    public void knockBack(Entity entity, float unused, double xRatio, double zRatio) {}
}