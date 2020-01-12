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

import java.util.UUID;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.entity.ICoraliumEntity;
import com.shinoow.abyssalcraft.common.util.EntityUtil;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraftforge.common.ForgeModContainer;

public class EntityAbyssalZombie extends CoraliumEntity implements ICoraliumEntity {

	private static final UUID babySpeedBoostUUID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
	private static final AttributeModifier babySpeedBoostModifier = new AttributeModifier(babySpeedBoostUUID, "Baby speed boost", 0.5D, 1);

	private float zombieWidth = -1.0F;
	private float zombieHeight;

	public EntityAbyssalZombie(World world) {
		super(world);
		setDrop(AbyssalCraft.Corflesh, 1.0F);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityZombie.class, 1.0D, true));
		tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
		tasks.addTask(4, new EntityAIFleeSun(this, 1.0D));
		tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
		tasks.addTask(6, new EntityAIWander(this, 1.0D));
		tasks.addTask(8, new EntityAILookIdle(this));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntityAbyssalZombie.class, 8.0F));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntityZombie.class, 8.0F));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntityDepthsGhoul.class, 8.0F));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntitySkeleton.class, 8.0F));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntitySkeletonGoliath.class, 8.0F));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityZombie.class, 0, true));
		targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		setSize(0.6F, 1.8F);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(64.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);

		if(AbyssalCraft.hardcoreMode){
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50.0D);
			getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(12.0D);
		} else {
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25.0D);
			getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0D);
		}
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		getDataWatcher().addObject(12, Byte.valueOf((byte)0));
		getDataWatcher().addObject(14, Byte.valueOf((byte)0));
	}

	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}

	@Override
	public boolean isChild() {
		return getDataWatcher().getWatchableObjectByte(12) == 1;
	}

	@Override
	protected float getSoundPitch() {
		return isChild() ? rand.nextFloat() - rand.nextFloat() * 0.2F + 1.3F : 0.9F;
	}

	/**
	 * Set whether this zombie is a child.
	 */
	public void setChild(boolean par1) {
		getDataWatcher().updateObject(12, Byte.valueOf((byte)(par1 ? 1 : 0)));

		if (worldObj != null && !worldObj.isRemote) {
			IAttributeInstance attributeinstance = getEntityAttribute(SharedMonsterAttributes.movementSpeed);
			attributeinstance.removeModifier(babySpeedBoostModifier);

			if (par1) {
				attributeinstance.applyModifier(babySpeedBoostModifier);
			}
		}

		setChildSize(par1);
	}

	@Override
	public int getTotalArmorValue() {
		return 3;
	}

	@Override
	protected boolean isAIEnabled() {
		return true;
	}

	public int getZombieType() {
		return dataWatcher.getWatchableObjectByte(14);
	}

	public void setZombieType(int par1) {
		dataWatcher.updateObject(14, Byte.valueOf((byte)par1));
	}

	@Override
	public void onLivingUpdate() {
		if (worldObj.isDaytime() && !worldObj.isRemote && !isChild() && worldObj.provider.dimensionId != AbyssalCraft.configDimId1) {
			float var1 = getBrightness(1.0F);

			if (var1 > 0.5F && rand.nextFloat() * 30.0F < (var1 - 0.4F) * 2.0F && worldObj.canBlockSeeTheSky(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ))) {
				boolean var2 = true;
				ItemStack var3 = getEquipmentInSlot(4);

				if (var3 != null) {
					if (var3.isItemStackDamageable()) {
						var3.setItemDamage(var3.getItemDamageForDisplay() + rand.nextInt(2));

						if (var3.getItemDamageForDisplay() >= var3.getMaxDamage()) {
							renderBrokenItemStack(var3);
							setCurrentItemOrArmor(4, (ItemStack)null);
						}
					}

					var2 = false;
				}

				if (var2) {
					setFire(16);
				}
			}
		}

		if(worldObj.isRemote) {
			setChildSize(isChild());
		}

		super.onLivingUpdate();
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity) {

		if (super.attackEntityAsMob(par1Entity))
			if (par1Entity instanceof EntityLivingBase)
				if(worldObj.provider.dimensionId == AbyssalCraft.configDimId1 && !EntityUtil.isEntityCoralium((EntityLivingBase)par1Entity)
				|| AbyssalCraft.shouldInfect == true && !EntityUtil.isEntityCoralium((EntityLivingBase)par1Entity))
					((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(AbyssalCraft.Cplague.id, 100));

		boolean flag = super.attackEntityAsMob(par1Entity);

		if (flag && getHeldItem() == null && isBurning() && rand.nextFloat() < worldObj.difficultySetting.getDifficultyId() * 0.3F)
			par1Entity.setFire(2 * worldObj.difficultySetting.getDifficultyId());

		return flag;
	}

	@Override
	protected String getLivingSound() {
		return "abyssalcraft:abyzombie.idle";
	}

	@Override
	protected String getHurtSound() {
		return "abyssalcraft:abyzombie.hurt";
	}

	@Override
	protected String getDeathSound() {
		return "abyssalcraft:abyzombie.death";
	}

	@Override
	protected void func_145780_a(int par1, int par2, int par3, Block par4)
	{
		playSound("mob.zombie.step", 0.15F, 1.0F);
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.UNDEAD;
	}

	@Override
	protected void dropRareDrop(int par1) {
		switch (rand.nextInt(3)) {
		case 0:
			dropItem(Items.bone, 1);
			break;
		case 1:
			dropItem(AbyssalCraft.sword, 1);
			break;
		case 2:
			dropItem(AbyssalCraft.Coralium, rand.nextInt(3));
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbtTag) {
		super.readEntityFromNBT(nbtTag);

		if(nbtTag.getBoolean("IsBaby")) {
			setChild(true);
			if (nbtTag.hasKey("ZombieType")) {
				setZombieType(nbtTag.getByte("ZombieType"));
			}
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbtTag) {
		super.writeEntityToNBT(nbtTag);

		if (isChild()) {
			nbtTag.setBoolean("IsBaby", true);
		}

		nbtTag.setByte("ZombieType", (byte)getZombieType());
	}



	@Override
	public void onKillEntity(EntityLivingBase victim) {
		super.onKillEntity(victim);

		if (worldObj.difficultySetting == EnumDifficulty.NORMAL || worldObj.difficultySetting == EnumDifficulty.HARD
				&& victim instanceof EntityZombie) {

			if (rand.nextBoolean())
				return;

			EntityAbyssalZombie EntityDephsZombie = new EntityAbyssalZombie(worldObj);
			EntityDephsZombie.copyLocationAndAnglesFrom(victim);
			worldObj.removeEntity(victim);
			EntityDephsZombie.onSpawnWithEgg((IEntityLivingData)null);

			if (victim.isChild())
				EntityDephsZombie.setChild(true);

			worldObj.spawnEntityInWorld(EntityDephsZombie);
			worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1016, (int)posX, (int)posY, (int)posZ, 0);
		}
		else if (worldObj.difficultySetting == EnumDifficulty.NORMAL || worldObj.difficultySetting == EnumDifficulty.HARD
				&& victim instanceof EntityPlayer) {

			if (rand.nextBoolean())
				return;

			EntityAbyssalZombie EntityDephsZombie = new EntityAbyssalZombie(worldObj);
			EntityDephsZombie.copyLocationAndAnglesFrom(victim);
			worldObj.removeEntity(victim);
			EntityDephsZombie.onSpawnWithEgg((IEntityLivingData)null);

			if (victim.isChild())
				EntityDephsZombie.setChild(true);

			worldObj.spawnEntityInWorld(EntityDephsZombie);
			worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1016, (int)posX, (int)posY, (int)posZ, 0);
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData spawnedEntity) {
		Object data = super.onSpawnWithEgg(spawnedEntity);

		if (worldObj.provider instanceof WorldProviderEnd) {
			setZombieType(2);
		}

		setCanPickUpLoot(false);

		if (data == null) {
			data = new EntityAbyssalZombie.GroupData(worldObj.rand.nextFloat() < ForgeModContainer.zombieBabyChance, null);
		}
		
		if (data instanceof EntityAbyssalZombie.GroupData) {
			EntityAbyssalZombie.GroupData groupdata = (EntityAbyssalZombie.GroupData)data;

			if (groupdata.isBaby) {
				setChild(true);
			}
		}

		return (IEntityLivingData)data;
	}

	public void setChildSize(boolean isChild) {
		multiplySize(isChild ? 0.5F : 1.0F);
	}

	@Override
	protected final void setSize(float widthMod, float heightMod) {
		boolean flag = zombieWidth > 0.0F && zombieHeight > 0.0F;
		zombieWidth = widthMod;
		zombieHeight = heightMod;

		if (!flag) {
			multiplySize(1.0F);
		}
	}

	protected final void multiplySize(float sizeMod) {
		super.setSize(zombieWidth * sizeMod, zombieHeight * sizeMod);
	}

	class GroupData implements IEntityLivingData {
		public boolean isBaby;
		private GroupData(boolean par2) {
			isBaby = false;
			isBaby = par2;
		}

		GroupData(boolean par2, Object par4EntityZombieINNER1) {
			this(par2);
		}
	}
}
