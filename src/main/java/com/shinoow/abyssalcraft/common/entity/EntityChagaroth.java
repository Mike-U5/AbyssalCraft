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
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI.ACPotions;
import com.shinoow.abyssalcraft.api.entity.IDreadEntity;
import com.shinoow.abyssalcraft.common.potion.CurseEffect;
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
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class EntityChagaroth extends ACMob implements IBossDisplayData, IDreadEntity {

	private int skillTicks;
	private int damageSpawns;
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
				// Reset damage spawns if healed back to full
				if (getHealth() >= 1000) {
					damageSpawns = 0;
				}
			}
			
			// Specials
			skillTicks += 1;
			if (skillTicks > 600) {
				skillTicks = 0;
			}
			
			if (skillTicks % 40 == 0) {
				performSpecialAttack(skillTicks / 40);
			}
			
			// Spawns
			if(!worldObj.isRemote) {	
				final int hp = (int)getHealth();
				if (hp <= 900 && damageSpawns < 1) {
					worldObj.spawnEntityInWorld(makeFist());
					damageSpawns = 1;
				}
				if (hp <= 800 && damageSpawns < 2) {
					worldObj.spawnEntityInWorld(makeFist());
					damageSpawns = 2;				
				}
				if (hp <= 700 && damageSpawns < 3) {
					worldObj.spawnEntityInWorld(makeFist());
					damageSpawns = 3;
				}
				if (hp <= 600 && damageSpawns < 4) {
					worldObj.spawnEntityInWorld(makeFist());
					damageSpawns = 4;			
				}
				if (hp <= 500 && damageSpawns < 5) {
					worldObj.spawnEntityInWorld(makeFist());
					damageSpawns = 5;
				}
				if (hp <= 400 && damageSpawns < 6) {
					worldObj.spawnEntityInWorld(makeFist());
					damageSpawns = 6;
				}
				if (hp <= 300 && damageSpawns < 7) {
					worldObj.spawnEntityInWorld(makeFist());
					damageSpawns = 7;
				}
				if (hp <= 200 && damageSpawns < 8) {
					worldObj.spawnEntityInWorld(makeFist());
					damageSpawns = 8;
				}
				if (hp <= 100 && damageSpawns < 9) {
					worldObj.spawnEntityInWorld(makeFist());
					damageSpawns = 9;
				}
			}
		}
		super.onLivingUpdate();
	}
	
	private EntityChagarothSpawn makeSpawn() {
		EntityChagarothSpawn cspawn = new EntityChagarothSpawn(worldObj);
		cspawn.addPotionEffect(new CurseEffect(ACPotions.Doom.id, 6000));
		cspawn.copyLocationAndAnglesFrom(this);
		return cspawn;
	}
	
	private EntityChagarothFist makeFist() {
		EntityChagarothFist fist = new EntityChagarothFist(worldObj);
		fist.copyLocationAndAnglesFrom(this);
		return fist;
	}
	
	private ACMob makeDreadMob() {
		ACMob mob = (worldObj.rand.nextBoolean()) ? new EntityDreadSpawn(worldObj) : new EntityDreadling(worldObj);
		mob.capturedDrops.clear();
		mob.addPotionEffect(new CurseEffect(ACPotions.Doom.id, 6000));
		mob.copyLocationAndAnglesFrom(this);
		mob.motionX = (worldObj.rand.nextBoolean()) ? Math.random() : -Math.random();
		mob.motionY = 1.5D;
		mob.motionZ = (worldObj.rand.nextBoolean()) ? Math.random() : -Math.random();
		return mob;
	}
	
	private void performSpecialAttack(int cycle) {
		// Spawn Fuccboi
		if (cycle == 5 && !worldObj.isRemote) {
			worldObj.spawnEntityInWorld(makeSpawn());
			return;
		}
				
		// Chant FIRE
		if (cycle == 10) {
			playSound("abyssalcraft:chagaroth.fire", 5F, 1F);
			return;
		}
		
		// Execute FIRE
		if (cycle == 11 && !worldObj.isRemote) {
			EntityLivingBase target = getAttackTarget();
			// Execute attack if there is a target
			double x;
			double y;
			double z;
			if (target == null) {
				x = (worldObj.rand.nextBoolean()) ? posX + 2 + (Math.random()) * 9 : posX - 2 - (Math.random() * 9);
				y = posY;
				z = (worldObj.rand.nextBoolean()) ? posZ + 2 + (Math.random()) * 9 : posZ - 2 - (Math.random() * 9);
			} else {
				x = target.posX;
				y = target.posY + 4;
				z = target.posZ;
			}
				
			worldObj.spawnEntityInWorld(positionFireball(worldObj, x + 3, y, z, 0.12D));
			worldObj.spawnEntityInWorld(positionFireball(worldObj, x - 3, y, z, 0.12D));
			worldObj.spawnEntityInWorld(positionFireball(worldObj, x, y, z + 3, 0.12D));
			worldObj.spawnEntityInWorld(positionFireball(worldObj, x, y, z - 3, 0.12D));
			worldObj.spawnEntityInWorld(positionFireball(worldObj, x, y + 1, z - 3, 0.07D));
			return;
		}
		
		// Spawn Chagaroth Spawn
		if (cycle == 15 && !worldObj.isRemote) {
			worldObj.spawnEntityInWorld(makeDreadMob());
			return;
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("DeathTicks", deathTicks);
		nbt.setInteger("SkillTicks", skillTicks);
		nbt.setInteger("DamageSpawns", damageSpawns);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		deathTicks = nbt.getInteger("DeathTicks");
		skillTicks = nbt.getInteger("SkillTicks");
		damageSpawns = nbt.getInteger("DamageSpawns");
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
				if (deathTicks >= 190 && deathTicks <= 200) {
					worldObj.spawnParticle("hugeexplosion", posX + f, posY + 2.0D + f1, posZ + f2, 0.0D, 0.0D, 0.0D);
				}
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