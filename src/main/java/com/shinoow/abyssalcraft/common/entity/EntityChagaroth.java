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
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class EntityChagaroth extends ACMob implements IBossDisplayData, IDreadEntity {

	public int deathTicks;

	public EntityChagaroth(World par1World) {
		super(par1World);
		setSize(3.0F, 4.8F);
		tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 0.0D, true));
		tasks.addTask(3, new EntityAILookIdle(this));
		tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 16.0F));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		ignoreFrustumCheck = true;
		isImmuneToFire = true;
	}

	@Override
	public String getCommandSenderName()
	{
		return EnumChatFormatting.DARK_RED + super.getCommandSenderName();
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity) {

		if (super.attackEntityAsMob(par1Entity))
			if (par1Entity instanceof EntityLivingBase)
				((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(AbyssalCraft.Dplague.id, 100));
		return super.attackEntityAsMob(par1Entity);
	}

	@Override
	protected void applyEntityAttributes()
	{
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
	}

	@Override
	protected boolean isAIEnabled()
	{
		return true;
	}

	@Override
	public boolean canBreatheUnderwater()
	{
		return true;
	}

	@Override
	protected String getLivingSound()
	{
		return "abyssalcraft:dreadguard.idle";
	}

	@Override
	protected String getHurtSound()
	{
		return "abyssalcraft:dreadguard.hit";
	}

	@Override
	protected String getDeathSound()
	{
		return "abyssalcraft:dreadguard.death";
	}

	@Override
	protected float getSoundVolume()
	{
		return 5.0F;
	}

	@Override
	public int getTotalArmorValue()
	{
		return 10;
	}

	@Override
	protected boolean canDespawn()
	{
		return false;
	}

	@Override
	public void onLivingUpdate()
	{
		EntityPlayer player = worldObj.getClosestPlayerToEntity(this, 32D);
		if(!worldObj.isRemote && deathTicks == 0){
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

			if(player != null)
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
		super.onLivingUpdate();
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("DeathTicks", deathTicks);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);
		deathTicks = par1NBTTagCompound.getInteger("DeathTicks");
	}

	@Override
	public void onDeath(DamageSource par1DamageSource) {
		if (par1DamageSource.getEntity() instanceof EntityPlayer)
		{
			EntityPlayer entityplayer = (EntityPlayer)par1DamageSource.getEntity();
			entityplayer.addStat(AbyssalCraft.killChagaroth, 1);
		}
		super.onDeath(par1DamageSource);
	}

	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
	{
		if(par2 > 50)
			if(par2 > 500001 || par2 < 500000)
				if(par2 > 750001.5F || par2 < 750001)
					par2 = 30 + worldObj.rand.nextInt(20);

		return super.attackEntityFrom(par1DamageSource, par2);
	}

	@Override
	protected void onDeathUpdate()
	{
		++deathTicks;

		if (deathTicks <= 200)
		{
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

		if (!worldObj.isRemote)
			if (deathTicks > 150 && deathTicks % 5 == 0)
			{
				i = 500;

				while (i > 0)
				{
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
}