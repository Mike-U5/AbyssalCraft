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
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraftforge.common.ForgeModContainer;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.entity.ICoraliumEntity;
import com.shinoow.abyssalcraft.common.util.EntityUtil;

public class EntityAbyssalZombie extends EntityMob implements ICoraliumEntity {

	private static final UUID babySpeedBoostUUID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
	private static final AttributeModifier babySpeedBoostModifier = new AttributeModifier(babySpeedBoostUUID, "Baby speed boost", 0.5D, 1);
	private static final UUID attackDamageBoostUUID = UUID.fromString("648D7064-6A60-4F59-8ABE-C2C23A6DD7A9");
	private static final AttributeModifier attackDamageBoostModifier = new AttributeModifier(attackDamageBoostUUID, "Attack Damage Boost", 3.0D, 0);

	private float zombieWidth = -1.0F;
	private float zombieHeight;

	public EntityAbyssalZombie(World par1World) {
		super(par1World);
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
	protected void entityInit()
	{
		super.entityInit();
		getDataWatcher().addObject(12, Byte.valueOf((byte)0));
		getDataWatcher().addObject(14, Byte.valueOf((byte)0));
	}

	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}

	@Override
	public boolean isChild()
	{
		return getDataWatcher().getWatchableObjectByte(12) == 1;
	}

	@Override
	protected float getSoundPitch()
	{
		return isChild() ? rand.nextFloat() - rand.nextFloat() * 0.2F + 1.3F : 0.9F;
	}

	/**
	 * Set whether this zombie is a child.
	 */
	public void setChild(boolean par1)
	{
		getDataWatcher().updateObject(12, Byte.valueOf((byte)(par1 ? 1 : 0)));

		if (worldObj != null && !worldObj.isRemote)
		{
			IAttributeInstance attributeinstance = getEntityAttribute(SharedMonsterAttributes.movementSpeed);
			attributeinstance.removeModifier(babySpeedBoostModifier);

			if (par1)
				attributeinstance.applyModifier(babySpeedBoostModifier);
		}

		setChildSize(par1);
	}

	@Override
	public int getTotalArmorValue()
	{
		int var1 = super.getTotalArmorValue() + 2;

		if (var1 > 20)
			var1 = 20;

		return var1;
	}

	@Override
	protected boolean isAIEnabled()
	{
		return true;
	}

	public int getZombieType()
	{
		return dataWatcher.getWatchableObjectByte(14);
	}

	public void setZombieType(int par1)
	{
		dataWatcher.updateObject(14, Byte.valueOf((byte)par1));
	}

	@Override
	public void onLivingUpdate()
	{
		if (worldObj.isDaytime() && !worldObj.isRemote && !isChild() && worldObj.provider.dimensionId != AbyssalCraft.configDimId1)
		{
			float var1 = getBrightness(1.0F);

			if (var1 > 0.5F && rand.nextFloat() * 30.0F < (var1 - 0.4F) * 2.0F && worldObj.canBlockSeeTheSky(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ)))
			{
				boolean var2 = true;
				ItemStack var3 = getEquipmentInSlot(4);

				if (var3 != null)
				{
					if (var3.isItemStackDamageable())
					{
						var3.setItemDamage(var3.getItemDamageForDisplay() + rand.nextInt(2));

						if (var3.getItemDamageForDisplay() >= var3.getMaxDamage())
						{
							renderBrokenItemStack(var3);
							setCurrentItemOrArmor(4, (ItemStack)null);
						}
					}

					var2 = false;
				}

				if (var2)
					setFire(16);
			}
		}

		if(worldObj.isRemote)
			setChildSize(isChild());

		super.onLivingUpdate();
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity)
	{

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
	protected Item getDropItem()
	{
		return AbyssalCraft.Corflesh;
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.UNDEAD;
	}

	@Override
	protected void dropRareDrop(int par1)
	{
		switch (rand.nextInt(3))
		{
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
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);

		if(par1NBTTagCompound.getBoolean("IsBaby"))
			setChild(true);;
			if (par1NBTTagCompound.hasKey("ZombieType"))
			{
				byte var2 = par1NBTTagCompound.getByte("ZombieType");
				setZombieType(var2);
			}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);

		if (isChild())
			par1NBTTagCompound.setBoolean("IsBaby", true);

		par1NBTTagCompound.setByte("ZombieType", (byte)getZombieType());
	}



	@Override
	public void onKillEntity(EntityLivingBase par1EntityLivingBase)
	{
		super.onKillEntity(par1EntityLivingBase);

		if (worldObj.difficultySetting == EnumDifficulty.NORMAL || worldObj.difficultySetting == EnumDifficulty.HARD
				&& par1EntityLivingBase instanceof EntityZombie) {

			if (rand.nextBoolean())
				return;

			EntityAbyssalZombie EntityDephsZombie = new EntityAbyssalZombie(worldObj);
			EntityDephsZombie.copyLocationAndAnglesFrom(par1EntityLivingBase);
			worldObj.removeEntity(par1EntityLivingBase);
			EntityDephsZombie.onSpawnWithEgg((IEntityLivingData)null);

			if (par1EntityLivingBase.isChild())
				EntityDephsZombie.setChild(true);

			worldObj.spawnEntityInWorld(EntityDephsZombie);
			worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1016, (int)posX, (int)posY, (int)posZ, 0);
		}
		else if (worldObj.difficultySetting == EnumDifficulty.NORMAL || worldObj.difficultySetting == EnumDifficulty.HARD
				&& par1EntityLivingBase instanceof EntityPlayer) {

			if (rand.nextBoolean())
				return;

			EntityAbyssalZombie EntityDephsZombie = new EntityAbyssalZombie(worldObj);
			EntityDephsZombie.copyLocationAndAnglesFrom(par1EntityLivingBase);
			worldObj.removeEntity(par1EntityLivingBase);
			EntityDephsZombie.onSpawnWithEgg((IEntityLivingData)null);

			if (par1EntityLivingBase.isChild())
				EntityDephsZombie.setChild(true);

			worldObj.spawnEntityInWorld(EntityDephsZombie);
			worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1016, (int)posX, (int)posY, (int)posZ, 0);
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData)
	{
		Object data = super.onSpawnWithEgg(par1EntityLivingData);

		if (worldObj.provider instanceof WorldProviderEnd)
			setZombieType(2);

		float f = worldObj.func_147462_b(posX, posY, posZ);
		setCanPickUpLoot(rand.nextFloat() < 0.55F * f);

		if (data == null)
			data = new EntityAbyssalZombie.GroupData(worldObj.rand.nextFloat() < ForgeModContainer.zombieBabyChance, null);

		if (data instanceof EntityAbyssalZombie.GroupData)
		{
			EntityAbyssalZombie.GroupData groupdata = (EntityAbyssalZombie.GroupData)data;

			if (groupdata.isBaby)
				setChild(true);
		}

		addRandomArmor();
		enchantEquipment();

		if (getEquipmentInSlot(4) == null)
		{
			Calendar calendar = worldObj.getCurrentDate();

			if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && rand.nextFloat() < 0.25F)
			{
				setCurrentItemOrArmor(4, new ItemStack(rand.nextFloat() < 0.1F ? Blocks.lit_pumpkin : Blocks.pumpkin));
				equipmentDropChances[4] = 0.0F;
			}
		}

		IAttributeInstance attribute = getEntityAttribute(SharedMonsterAttributes.attackDamage);
		Calendar calendar = worldObj.getCurrentDate();
		if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && rand.nextFloat() < 0.25F)
			attribute.applyModifier(attackDamageBoostModifier);

		return (IEntityLivingData)data;
	}

	public void setChildSize(boolean p_146071_1_)
	{
		multiplySize(p_146071_1_ ? 0.5F : 1.0F);
	}

	@Override
	protected final void setSize(float p_70105_1_, float p_70105_2_)
	{
		boolean flag = zombieWidth > 0.0F && zombieHeight > 0.0F;
		zombieWidth = p_70105_1_;
		zombieHeight = p_70105_2_;

		if (!flag)
			multiplySize(1.0F);
	}

	protected final void multiplySize(float p_146069_1_)
	{
		super.setSize(zombieWidth * p_146069_1_, zombieHeight * p_146069_1_);
	}

	class GroupData implements IEntityLivingData
	{
		public boolean isBaby;
		private GroupData(boolean par2)
		{
			isBaby = false;
			isBaby = par2;
		}

		GroupData(boolean par2, Object par4EntityZombieINNER1)
		{
			this(par2);
		}
	}
}
