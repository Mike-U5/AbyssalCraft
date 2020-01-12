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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeModContainer;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.entity.*;
import com.shinoow.abyssalcraft.common.entity.demon.*;
import com.shinoow.abyssalcraft.common.util.EntityUtil;
import com.shinoow.abyssalcraft.common.world.gen.WorldGenShoggothMonolith;

public class EntityLesserShoggoth extends ACMob implements ICoraliumEntity, IDreadEntity {

	private static final UUID babySpeedBoostUUID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
	private static final AttributeModifier babySpeedBoostModifier = new AttributeModifier(babySpeedBoostUUID, "Baby speed boost", 0.3D, 1);

	private static List<Class<? extends EntityLivingBase>> noms = new ArrayList<Class<? extends EntityLivingBase>>();

	private int monolithTimer;
	private float shoggothWidth = -1.0F;
	private float shoggothHeight;

	public EntityLesserShoggoth(World par1World) {
		super(par1World);
		setDrop(AbyssalCraft.shoggothFlesh, 1.0F);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 0.35D, true));
		for(int i = 0; i < noms.size(); i++) {
			tasks.addTask(2, new EntityAIAttackOnCollide(this, noms.get(i), 0.40D, true));
		}
		tasks.addTask(3, new EntityAIFleeSun(this, 0.35D));
		tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 0.35D));
		tasks.addTask(5, new EntityAIWander(this, 0.35D));
		tasks.addTask(7, new EntityAILookIdle(this));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityDepthsGhoul.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityAbyssalZombie.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityZombie.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntitySkeleton.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntitySkeletonGoliath.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityDreadling.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityDreadSpawn.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityDemonPig.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityDreadguard.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityOmotholWarden.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityOmotholGhoul.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityRemnant.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityLesserShoggoth.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityGatekeeperMinion.class, 8.0F));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		for(int i = 0; i < noms.size(); i++) {
			targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, noms.get(i), 0, true));
		}
		setSize(2.4F, 2.6F);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		
		// Adult shoggots a have a reduced default knockback rate

		if(AbyssalCraft.hardcoreMode){
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(200.0D);
			getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(16.0D);
		} else {
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0D);
			getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(8.0D);
		}
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObject(12, Byte.valueOf((byte)0));
		dataWatcher.addObject(14, Byte.valueOf((byte)0));
		dataWatcher.addObject(16, Byte.valueOf((byte)0));
		dataWatcher.addObject(18, Byte.valueOf((byte)0));
	}

	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if (!worldObj.isRemote) {
			setBesideClimbableBlock(isCollidedHorizontally);
		}
	}

	@Override
	public boolean isChild() {
		return getDataWatcher().getWatchableObjectByte(12) == 1;
	}

	public void setChild(boolean isChild) {
		getDataWatcher().updateObject(12, Byte.valueOf((byte)(isChild ? 1 : 0)));

		if (worldObj != null && !worldObj.isRemote) {
			IAttributeInstance attributeinstance = getEntityAttribute(SharedMonsterAttributes.movementSpeed);
			attributeinstance.removeModifier(babySpeedBoostModifier);

			if (isChild) {
				attributeinstance.applyModifier(babySpeedBoostModifier);
			}
		}
		setChildSize(isChild);
	}

	@Override
	protected boolean isAIEnabled() {
		return true;
	}

	public int getShoggothType() {
		return dataWatcher.getWatchableObjectByte(14);
	}

	public void setShoggothType(int typeId) {
		dataWatcher.updateObject(14, Byte.valueOf((byte)typeId));
	}

	public void setFoodLevel(int foodLvl){
		dataWatcher.updateObject(16, Byte.valueOf((byte)foodLvl));
	}

	public int getFoodLevel(){
		return dataWatcher.getWatchableObjectByte(16);
	}

	public void feed() {
		int food = getFoodLevel() + 1;
		dataWatcher.updateObject(16, Byte.valueOf((byte)food));
	}

	@Override
	public boolean isOnLadder() {
		return isBesideClimbableBlock();
	}

	/**
	 * Reduces this Shoggoth's monolith timer
	 */
	public void reduceMonolithTimer() {
		if(monolithTimer - 200 >= 200) {
			monolithTimer -= 200;
		} else  {
			monolithTimer = 0;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();

		if(worldObj.isRemote) {
			setChildSize(isChild());
		}

		monolithTimer++;

		if(getFoodLevel() == 4 && !worldObj.isRemote) {
			setFoodLevel(0);
			if(!isChild()) {
				EntityLesserShoggoth shoggoth = new EntityLesserShoggoth(worldObj);
				shoggoth.copyLocationAndAnglesFrom(this);
				shoggoth.onSpawnWithEgg((IEntityLivingData)null);
				shoggoth.setChild(true);
				worldObj.spawnEntityInWorld(shoggoth);
				playSound("mob.chicken.plop", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
			} else {
				setChild(false);
			}
		}

		if(!worldObj.isRemote){
			int x = (int)posX;
			int y = (int)(posY - 0.6);
			int z = (int)posZ;
			
			spawnOoze(x, y, z);
			if(!isChild()) {
				spawnOoze(x - 1, y, z);
				spawnOoze(x, y, z - 1);
				spawnOoze(x - 1, y, z - 1);
			}
		}

		if(monolithTimer >= 1800) {
			monolithTimer = 0;
			if(worldObj.getEntitiesWithinAABB(getClass(), boundingBox.expand(32D, 32D, 32D)).size() >= 5 && !isChild()){
				for(EntityLesserShoggoth shoggoth : (List<EntityLesserShoggoth>)worldObj.getEntitiesWithinAABB(getClass(), boundingBox.expand(32D, 32D, 32D))) {
					shoggoth.reduceMonolithTimer();
				}
				if(!worldObj.isRemote) {
					new WorldGenShoggothMonolith().generate(worldObj, rand, MathHelper.floor_double(posX) + 3, MathHelper.floor_double(posY), MathHelper.floor_double(posZ) + 3);
				}
			}
		}
	}

	/**
	 * Checks if a Lesser Shoggoth can generate ooze at the specific coordinates
	 * @param x X-coord
	 * @param y Y-coord
	 * @param z Z-coord
	 */
	private void spawnOoze(int x, int y, int z) {
		if(AbyssalCraft.shoggothBlock.canPlaceBlockAt(worldObj, x, y, z)) {
			worldObj.setBlock(x, y, z, AbyssalCraft.shoggothBlock);
			return;
		}
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
	public boolean attackEntityAsMob(Entity entity) {
		if (getDistanceToEntity(entity) > 3) {
			return false;
		}
		if (super.attackEntityAsMob(entity) && entity instanceof EntityLivingBase) {
			EntityLivingBase victim = (EntityLivingBase) entity;
			if(getShoggothType() == 1 && !EntityUtil.isEntityCoralium(victim)) {
				victim.addPotionEffect(new PotionEffect(AbyssalCraft.Cplague.id, 100));
			} else if(getShoggothType() == 2 && !EntityUtil.isEntityDread(victim)) {
				victim.addPotionEffect(new PotionEffect(AbyssalCraft.Dplague.id, 100));
			} else if(getShoggothType() == 3) {
				victim.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 100));
			} else if(getShoggothType() == 4) {
				victim.addPotionEffect(new PotionEffect(Potion.blindness.id, 100));
			}
		}
		return super.attackEntityAsMob(entity);
	}

	@Override
	public boolean attackEntityFrom(DamageSource dmgSrc, float damage) {
		if(dmgSrc.isProjectile()) {
			playSound("mob.slime.small", getSoundVolume(), ((rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F) / 0.8F);
			return false;
		}
		if (dmgSrc == DamageSource.cactus) return false;
		if (dmgSrc == DamageSource.drown) return false;
		if (dmgSrc == DamageSource.lava) return false;
		if (dmgSrc == DamageSource.fall) return false;
		if (dmgSrc == DamageSource.inWall) return false;
		if (dmgSrc == DamageSource.fallingBlock) return false;
		
		if(dmgSrc.isFireDamage() && dmgSrc != DamageSource.lava) {
			damage *= 1.5;
		}

		return super.attackEntityFrom(dmgSrc, damage);
	}

	@Override
	protected void fall(float par1) {}

	@Override
	protected String getLivingSound() {
		return "abyssalcraft:shoggoth.idle";
	}

	@Override
	protected String getHurtSound() {
		return "abyssalcraft:shoggoth.hit";
	}

	@Override
	protected String getDeathSound() {
		return "abyssalcraft:shoggoth.death";
	}

	@Override
	protected void func_145780_a(int par1, int par2, int par3, Block par4) {
		playSound("mob.spider.step", 0.15F, 1.0F);
	}

	@Override
	protected void dropFewItems(boolean par1, int lootLvl) {
		float drp = 1.0F + (lootLvl*0.5F);
		int finalAmt = (drp % 1 > Math.random()) ? (int)Math.ceil(drp) : (int)Math.floor(drp);
		
		entityDropItem(new ItemStack(AbyssalCraft.shoggothFlesh, finalAmt, getShoggothType()), 0);
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return AbyssalCraftAPI.SHADOW;
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);

		if(compound.getBoolean("IsBaby")) {
			setChild(true);
		}

		if (compound.hasKey("ShoggothType")) {
			byte typeId = compound.getByte("ShoggothType");
			setShoggothType(typeId);
		}

		if(compound.hasKey("FoodLevel")) {
			byte foodLvl = compound.getByte("FoodLevel");
			setFoodLevel(foodLvl);
		}

		monolithTimer = compound.getInteger("MonolithTimer");
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);

		if (isChild()) {
			compound.setBoolean("IsBaby", true);
		}

		compound.setByte("ShoggothType", (byte)getShoggothType());
		compound.setByte("FoodLevel", (byte)getFoodLevel());
		compound.setInteger("MonolithTimer", monolithTimer);
	}

	@Override
	public void onKillEntity(EntityLivingBase victim) {
		super.onKillEntity(victim);

		if(isFood(victim)) {
			feed();
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData) {
		Object data = super.onSpawnWithEgg(par1EntityLivingData);

		setShoggothType(0);

		if(worldObj.provider.dimensionId == AbyssalCraft.configDimId1)
			setShoggothType(1);
		if(worldObj.provider.dimensionId == AbyssalCraft.configDimId2)
			setShoggothType(2);
		if(worldObj.provider.dimensionId == AbyssalCraft.configDimId3)
			setShoggothType(3);
		if(worldObj.provider.dimensionId == AbyssalCraft.configDimId4)
			setShoggothType(4);

		if (data == null)
			data = new EntityLesserShoggoth.GroupData(worldObj.rand.nextFloat() < ForgeModContainer.zombieBabyChance, null);

		if (data instanceof EntityLesserShoggoth.GroupData) {
			EntityLesserShoggoth.GroupData groupdata = (EntityLesserShoggoth.GroupData)data;

			if (groupdata.isBaby)
				setChild(true);
		}

		return (IEntityLivingData)data;
	}

	public void setChildSize(boolean isChild) {
		float size = isChild ? 0.6F : 1.0F;
		multiplySize(size, size);
	}

	@Override
	protected final void setSize(float sWidth, float sHeight) {
		boolean flag = shoggothWidth > 0.0F && shoggothHeight > 0.0F;
		shoggothWidth = sWidth;
		shoggothHeight = sHeight;

		if (!flag) {
			multiplySize(1.0F, 1.0F);
		}
	}

	protected final void multiplySize(float widthMod, float heightMod) {
		super.setSize(shoggothWidth * widthMod, shoggothHeight * heightMod);
	}

	static {
		noms.add(EntityAnimal.class);
		noms.add(EntityAmbientCreature.class);
		noms.add(EntityWaterMob.class);
		noms.add(EntityEvilpig.class);
		noms.add(EntityEvilCow.class);
		noms.add(EntityEvilChicken.class);
		noms.add(EntityDemonAnimal.class);
		noms.addAll(AbyssalCraftAPI.getShoggothFood());
	}

	/**
	 * Checks if the Entity class, it's superclass or it's superclass' superclass is food
	 * @param par1 The Entity to check
	 * @return true if the Entity was food, otherwise false
	 */
	private boolean isFood(EntityLivingBase par1){
		return noms.contains(par1.getClass()) ? true : noms.contains(par1.getClass().getSuperclass()) ? true :
			noms.contains(par1.getClass().getSuperclass().getSuperclass()) ? true : false;
	}
	
	
	@Override
	protected double getBaseKnockbackRate() {
		return this.isChild() ? 0.4 : 0.3;
	}


	class GroupData implements IEntityLivingData {
		public boolean isBaby;
		private GroupData(boolean isBab) {
			isBaby = false;
			isBaby = isBab;
		}

		GroupData(boolean par2, Object par4EntityLesserShoggothINNER1) {
			this(par2);
		}
	}
}
