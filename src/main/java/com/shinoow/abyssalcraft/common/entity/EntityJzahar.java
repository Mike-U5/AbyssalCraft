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
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.entity.IAntiEntity;
import com.shinoow.abyssalcraft.api.entity.ICoraliumEntity;
import com.shinoow.abyssalcraft.api.entity.IDreadEntity;
import com.shinoow.abyssalcraft.api.entity.IOmotholEntity;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiSkeleton;
import com.shinoow.abyssalcraft.common.potion.CurseEffect;
import com.shinoow.abyssalcraft.common.util.SpecialTextUtil;
import com.shinoow.abyssalcraft.common.world.TeleporterDarkRealm;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;

public class EntityJzahar extends ACMob implements IBossDisplayData, IAntiEntity, ICoraliumEntity, IDreadEntity, IOmotholEntity {

	public int deathTicks;
	private int skillTicks;
	private boolean that = false;
	private double speed = 0.05D;

	public EntityJzahar(World world) {
		super(world);
		setSize(1.5F, 5.7F);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIAttackOnCollide(this, EntityPlayer.class, 0.35D, true));
		tasks.addTask(2, new EntityAIMoveTowardsRestriction(this, 0.35D));
		tasks.addTask(3, new EntityAIWander(this, 0.35D));
		tasks.addTask(4, new EntityAILookIdle(this));
		tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		isImmuneToFire = true;
	}

	@Override
	public String getCommandSenderName() {
		return EnumChatFormatting.BLUE + super.getCommandSenderName();
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();

		if(AbyssalCraft.hardcoreMode){
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1000.0D);
		} else {
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(666.0D);
		}
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(40.0D);
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.8D);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(48.0D);
	}

	@Override
	protected boolean isAIEnabled() {
		return true;
	}

	@Override
	protected String getLivingSound() {
		return "mob.blaze.breathe";
	}

	@Override
	protected String getHurtSound() {
		return "mob.enderdragon.hit";
	}

	@Override
	protected String getDeathSound() {
		return "mob.wither.death";
	}

	@Override
	protected float getSoundVolume() {
		return 3.5F;
	}

	@Override
	public int getTotalArmorValue() {
		return 10;
	}

	@Override
	protected boolean canDespawn() {
		return false;
	}

	@Override
	public boolean attackEntityAsMob(Entity target) {
		swingItem();
		return super.attackEntityAsMob(target);
	}

	@Override
	public boolean attackEntityFrom(DamageSource dmgSrc, float amount) {
		// If I can't see you then you can't see me!
		if (getAttackTarget() == null) {
			return false;
		}
		
		
		Entity srcEntity = dmgSrc.getEntity();
		
		// Resistant to arrows
		if (dmgSrc.damageType == "arrow") {
			amount *= 0.5F;
		}
		
		// Cancel damage from non-enties
		if (srcEntity == null) {
			return false;
		}
		
		// Jz can never be harmed by his own minions
		if (srcEntity instanceof IOmotholEntity) {
			return false;
		}
		
		// Cap damage at 30
		if(amount > 30) {
			amount = 30;
		}

		return super.attackEntityFrom(dmgSrc, amount);
	}
	
	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return AbyssalCraftAPI.SHADOW;
	}
	
	protected boolean teleportRandomly() {
		double d0 = posX + (rand.nextDouble() - 0.5D) * 64.0D;
		double d1 = rand.nextInt(15) + 54;
		double d2 = posZ + (rand.nextDouble() - 0.5D) * 64.0D;
		return teleportTo(d0, d1, d2);
	}

	protected boolean teleportTo(double par1, double par3, double par5) {
		EnderTeleportEvent event = new EnderTeleportEvent(this, par1, par3, par5, 0);
		if (MinecraftForge.EVENT_BUS.post(event)) {
			return false;
		}
		double dX = posX;
		double dY = posY;
		double dZ = posZ;
		posX = event.targetX;
		posY = event.targetY;
		posZ = event.targetZ;
		boolean flag = false;
		int i = MathHelper.floor_double(posX);
		int j = MathHelper.floor_double(posY);
		int k = MathHelper.floor_double(posZ);

		if (worldObj.blockExists(i, j, k)) {
			boolean flag1 = false;

			while (!flag1 && j > 0){
				Block block = worldObj.getBlock(i, j - 1, k);

				if (block.getMaterial().blocksMovement()) {
					flag1 = true;
				} else {
					--posY;
					--j;
				}
			}

			if (flag1) {
				setPosition(posX, posY, posZ);

				if (worldObj.getCollidingBoundingBoxes(this, boundingBox).isEmpty() && !worldObj.isAnyLiquid(boundingBox)) {
					flag = true;
				}
			}
		}

		if (!flag) {
			setPosition(dX, dY, dZ);
			return false;
		} else {
			short short1 = 128;

			for (int l = 0; l < short1; ++l) {
				double d6 = l / (short1 - 1.0D);
				float f = (rand.nextFloat() - 0.5F) * 0.2F;
				float f1 = (rand.nextFloat() - 0.5F) * 0.2F;
				float f2 = (rand.nextFloat() - 0.5F) * 0.2F;
				double d7 = dX + (posX - dX) * d6 + (rand.nextDouble() - 0.5D) * width * 2.0D;
				double d8 = dY + (posY - dY) * d6 + rand.nextDouble() * height;
				double d9 = dZ + (posZ - dZ) * d6 + (rand.nextDouble() - 0.5D) * width * 2.0D;
				if(AbyssalCraft.particleEntity) {
					worldObj.spawnParticle("largesmoke", d7, d8, d9, f, f1, f2);
				}
			}

			worldObj.playSoundEffect(dX, dY, dZ, "mob.endermen.portal", 1.0F, 1.0F);
			playSound("mob.endermen.portal", 1.0F, 1.0F);
			return true;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onDeath(DamageSource dmgSrc) {
		List<EntityPlayer> players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, boundingBox.expand(10, 10, 10));
		for(EntityPlayer player : players) {
			player.addStat(AbyssalCraft.killJzahar, 1);
		}
		super.onDeath(dmgSrc);
	}

	private double func_82214_u(int par1) {
		if (par1 <= 0)
			return posX;
		else {
			float f = (renderYawOffset + 180 * (par1 - 1)) / 180.0F * (float)Math.PI;
			float f1 = MathHelper.cos(f);
			return posX + f1 * 1.3D;
		}
	}

	private double func_82208_v(int par1) {
		return par1 <= 0 ? posY + 3.0D : posY + 2.2D;
	}

	private double func_82213_w(int par1) {
		if (par1 <= 0) {
			return posZ;
		} else {
			float f = (renderYawOffset + 180 * (par1 - 1)) / 180.0F * (float)Math.PI;
			float f1 = MathHelper.sin(f);
			return posZ + f1 * 1.3D;
		}
	}

	@Override
	public float getEyeHeight() {
		return height * 0.90F;
	}
	
	private void banishBosses() {
		final float f = (rand.nextFloat() - 0.5F) * 8.0F;
		final float f1 = (rand.nextFloat() - 0.5F) * 4.0F;
		final float f2 = (rand.nextFloat() - 0.5F) * 8.0F;

		List<?> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(48.0D, 48.0D, 48.0D));
		if (list != null) {
			for (int k2 = 0; k2 < list.size(); k2++) {
				Entity entity = (Entity)list.get(k2);
				if(entity instanceof EntityDragon || entity instanceof EntityWither){
					if(!worldObj.isRemote){
						worldObj.removeEntity(entity);
						if(entity.isDead)
							SpecialTextUtil.JzaharGroup(worldObj, StatCollector.translateToLocal("message.jzahar.banish.vanilla"));
					} else if(AbyssalCraft.particleEntity)
						worldObj.spawnParticle("hugeexplosion", entity.posX + f, entity.posY + 2.0D + f1, entity.posZ + f2, 0.0D, 0.0D, 0.0D);
				} else if(entity instanceof EntityDragonBoss || entity instanceof EntitySacthoth || entity instanceof EntityChagaroth){
					if(!worldObj.isRemote){
						worldObj.removeEntity(entity);
						if(entity.isDead)
							SpecialTextUtil.JzaharGroup(worldObj, StatCollector.translateToLocal("message.jzahar.banish.ac"));
					} else if(AbyssalCraft.particleEntity)
						worldObj.spawnParticle("hugeexplosion", entity.posX + f, entity.posY + 2.0D + f1, entity.posZ + f2, 0.0D, 0.0D, 0.0D);
				} else if(entity instanceof EntityJzahar){
					if(!worldObj.isRemote){
						worldObj.removeEntity(entity);
						worldObj.removeEntity(this);
						EntityJzahar newgatekeeper = new EntityJzahar(worldObj);
						newgatekeeper.copyLocationAndAnglesFrom(this);
						worldObj.spawnEntityInWorld(newgatekeeper);
						if(!that){
							that = true;
							SpecialTextUtil.JzaharGroup(worldObj, StatCollector.translateToLocal("message.jzahar.banish.jzh"));
						}
					} else if(AbyssalCraft.particleEntity){
						worldObj.spawnParticle("hugeexplosion", entity.posX + f, entity.posY + 2.0D + f1, entity.posZ + f2, 0.0D, 0.0D, 0.0D);
						worldObj.spawnParticle("hugeexplosion", posX + f, posY + 2.0D + f1, posZ + f2, 0.0D, 0.0D, 0.0D);
					}
				} else if(entity instanceof IBossDisplayData){
					if(!worldObj.isRemote) {
						worldObj.removeEntity(entity);
						if(entity.isDead) {
							SpecialTextUtil.JzaharGroup(worldObj, StatCollector.translateToLocal("message.jzahar.banish.other"));
						}
					} else if(AbyssalCraft.particleEntity) {
						worldObj.spawnParticle("hugeexplosion", entity.posX + f, entity.posY + 2.0D + f1, entity.posZ + f2, 0.0D, 0.0D, 0.0D);
					}
				}
			}
		}
	}

	@Override
	public void onLivingUpdate() {
		// Banish other bosses
		banishBosses();
		
		// Prevents J'zhar falling to it's untimely demise
		if (posY <= 0) {
			teleportRandomly();
		}
		
		// Regenerate
		if (!worldObj.isRemote && ticksExisted % 5 == 0 && getAttackTarget() == null) {
			heal(1);
		}
		
		// Special Attack checker
		skillTicks += 1;
		if (skillTicks > 2440) {
			skillTicks = 0;
		}
		if (skillTicks % 40 == 0 && deathTicks <= 0) {
			performSpecialAttack(skillTicks / 40);
		}
		
		// Call Super
		super.onLivingUpdate();
	}
	
	/*
	 * -------------------------
	 * Skill-related functions. 1 cycle = 40 ticks.
	 * -------------------------
	 */
	@SuppressWarnings("unchecked")
	private void performSpecialAttack(int cycle) {
		// Set the cast time
		final int castTime = 1;
		
		// Chant EARTHQUAKE
		if (cycle == 11) {
			playSound("abyssalcraft:jzahar.chant.shout", 4.5F, 1F);
			return;
		}
		
		// Perform EARTHQUAKE
		if (cycle == 11 + castTime) {
			playSound("abyssalcraft:jzahar.skill.quake", 4.5F, 1F);
			// Apply
			List<?> ents = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, boundingBox.expand(64.0D, 64.0D, 64.0D));
			if (ents != null && ents.size() > 0) {
				swingItem();
				for (int i = 0; i < ents.size(); i++) {
					EntityLivingBase e = (EntityLivingBase) ents.get(i);
					if (!(e instanceof IOmotholEntity) && !worldObj.isRemote) {
						e.addPotionEffect(new CurseEffect(new PotionEffect(AbyssalCraftAPI.potionIdEarthquake, 200)));
					}
				}
			}
			return;
		}
		
		// Chant SUMMON
		if (cycle == 22) {
			playSound("abyssalcraft:jzahar.chant.summon", 4.5F, 1F);
			return;
		}
		
		// Perform SUMMON
		if (cycle == 22 + castTime) {
			swingItem();
			if(!worldObj.isRemote) {
				EntityLivingBase entity = new EntityAntiSkeleton(worldObj);
				final double x = posX - 2 + Math.random() * 4;
				final double y = posY + Math.random() * 2;
				final double z = posZ - 2 + Math.random() * 4;
				worldObj.addWeatherEffect(new EntityLightningBolt(worldObj, x, y, z));
				entity.setLocationAndAngles(x, y, z, entity.rotationYaw, entity.rotationPitch);
				entity.capturedDrops.clear();
				worldObj.spawnEntityInWorld(entity);
			}
			return;
		}
		
		// Chant WITHER
		if (cycle == 33) {
			playSound("abyssalcraft:jzahar.chant.wither", 4.5F, 1F);
			return;
		}
		
		// Perform WITHER
		if (cycle == 33 + castTime) {
			List<?> players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, boundingBox.expand(64.0D, 64.0D, 64.0D));
			if (players != null && players.size() > 0) {
				swingItem();
				if (!worldObj.isRemote) {
					for (int i = 0; i < players.size(); i++) {
						EntityLivingBase p = (EntityLivingBase) players.get(i);
						rangedAttackSkull(0, p);
					}
				}
			}
			return;
		}
		
		// Chant BLACK HOLE
		if (cycle == 44) {
			playSound("abyssalcraft:jzahar.chant.doorway", 4.5F, 1F);
			return;
		}
		
		// Perform BLACK HOLE
		if (cycle == 44 + castTime) {
			swingItem();
			if (!worldObj.isRemote) {
				EntityWormHole hole = new EntityWormHole(worldObj);
				if (getAttackTarget() instanceof EntityLivingBase) {
					hole.copyLocationAndAnglesFrom(getAttackTarget());
				} else {
					hole.copyLocationAndAnglesFrom(this);
				}
				worldObj.spawnEntityInWorld(hole);
			}
			return;
		}
		
		// Extra wait time here to black hole can finish.
		
		// Chant DISPLACE
		if (cycle == 60) {
			playSound("abyssalcraft:jzahar.chant.displace", 4.5F, 1F);
			return;
		}
		
		// Perform DISPLACE
		if (cycle == 60 + castTime) {
			swingItem();
			List<EntityLivingBase> entities = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(posX, posY, posZ, posX + 1, posY + 1, posZ + 1).expand(64, 64, 64));

			if(!entities.isEmpty()) {
				for(EntityLivingBase entity : entities) {
					EntityLivingBase other = entities.get(worldObj.rand.nextInt(entities.size()));
					double posX = entity.posX;
					double posY = entity.posY;
					double posZ = entity.posZ;
					playSound("mob.endermen.portal", 3.0F, 0.8F);
					if(!worldObj.isRemote){
						entity.setPositionAndUpdate(other.posX, other.posY, other.posZ);
						other.setPositionAndUpdate(posX, posY, posZ);
					}
				}
			}
			return;
		}
	}
	
	/*
	 * -------------------------
	 * Misc
	 * -------------------------
	 */
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

	@SuppressWarnings("unchecked")
	@Override
	protected void onDeathUpdate() {
		motionX = motionY = motionZ = 0;
		deathTicks++;

		if(deathTicks <= 800){
			if(deathTicks == 410) {
				worldObj.playSoundAtEntity(this, "abyssalcraft:jzahar.charge", 1, 1);
			}
			if(deathTicks < 400) {
				worldObj.spawnParticle("largesmoke", posX, posY + 2.5D, posZ, 0, 0, 0);
			}
			float f = (rand.nextFloat() - 0.5F) * 3.0F;
			float f1 = (rand.nextFloat() - 0.5F) * 2.0F;
			float f2 = (rand.nextFloat() - 0.5F) * 3.0F;
			if(deathTicks >= 100 && deathTicks < 400) {
				worldObj.spawnParticle("smoke", posX + f, posY + f1, posZ + f2, 0, 0, 0);
			}
			if(deathTicks >= 200 && deathTicks < 400){
				worldObj.spawnParticle("largesmoke", posX + f, posY + f1, posZ + f2, 0.0D, 0.0D, 0.0D);
				worldObj.spawnParticle("lava", posX, posY + 2.5D, posZ, 0, 0, 0);
			}
			if (deathTicks >= 790 && deathTicks <= 800){
				worldObj.spawnParticle("hugeexplosion", posX, posY + 1.5D, posZ, 0.0D, 0.0D, 0.0D);
				worldObj.playSoundAtEntity(this, "random.explode", 4, (1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.2F) * 0.7F);
			}
			
			if(deathTicks > 400 && deathTicks < 800) {
				pullEntities(speed);
				speed += 0.0001;
			}
		}

		int i;
		int j;

		if (!worldObj.isRemote)
			if (deathTicks > 750 && deathTicks % 5 == 0) {
				i = 500;

				while (i > 0) {
					j = EntityXPOrb.getXPSplit(i);
					i -= j;
					worldObj.spawnEntityInWorld(new EntityXPOrb(worldObj, posX, posY, posZ, j));
					if(deathTicks == 700 || deathTicks == 720 || deathTicks == 740 || deathTicks == 760 || deathTicks == 780){
						worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX + posneg(3), posY + rand.nextInt(3), posZ + posneg(3), new ItemStack(AbyssalCraft.Cingot)));
						worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX + posneg(3), posY + rand.nextInt(3), posZ + posneg(3), new ItemStack(AbyssalCraft.dreadiumingot)));
						worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX + posneg(3), posY + rand.nextInt(3), posZ + posneg(3), new ItemStack(AbyssalCraft.ethaxiumIngot)));
					}
				}
			}
		if(deathTicks == 790 && !worldObj.isRemote){
			if(!worldObj.getEntitiesWithinAABB(Entity.class, boundingBox.expand(3,1,3)).isEmpty()){
				List<Entity> entities = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(3,1,3));
				for(Entity entity: entities)
					if(entity instanceof EntityPlayer){
						EntityPlayer player = (EntityPlayer)entity;
						player.setHealth(1);
						player.addPotionEffect(new PotionEffect(Potion.blindness.id, 2400, 5));
						player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 2400, 5));
						player.addPotionEffect(new PotionEffect(Potion.confusion.id, 2400, 5));
						player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 2400, 5));
						player.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 2400, 5));
						player.addPotionEffect(new PotionEffect(Potion.weakness.id, 2400, 5));
						player.addPotionEffect(new PotionEffect(Potion.hunger.id, 2400, 5));
						player.addPotionEffect(new PotionEffect(Potion.poison.id, 2400, 5));
						if(player instanceof EntityPlayerMP){
							WorldServer worldServer = (WorldServer) player.worldObj;
							EntityPlayerMP mp = (EntityPlayerMP) player;
							mp.addPotionEffect(new PotionEffect(Potion.resistance.getId(), 80, 255));
							mp.mcServer.getConfigurationManager().transferPlayerToDimension(mp, AbyssalCraft.configDimId4, new TeleporterDarkRealm(worldServer));
							player.addStat(AbyssalCraft.enterDarkRealm, 1);
						}
					} else if(entity instanceof EntityLivingBase || entity instanceof EntityItem) {
						entity.setDead();
					}
			}

			for(int x = 0; x < 10; x++)
				for(int y = 0; y < 10; y++)
					for(int z = 0; z < 10; z++){
						if(!worldObj.isAirBlock((int)posX + x, (int)posY + y, (int)posZ + z))
							if(worldObj.getBlock((int)posX + x, (int)posY + y, (int)posZ + z) != Blocks.bedrock)
								worldObj.setBlockToAir((int)posX + x, (int)posY + y, (int)posZ + z);
						if(!worldObj.isAirBlock((int)posX - x, (int)posY + y, (int)posZ + z))
							if(worldObj.getBlock((int)posX - x, (int)posY + y, (int)posZ + z) != Blocks.bedrock)
								worldObj.setBlockToAir((int)posX - x, (int)posY + y, (int)posZ + z);
						if(!worldObj.isAirBlock((int)posX + x, (int)posY + y, (int)posZ - z))
							if(worldObj.getBlock((int)posX + x, (int)posY + y, (int)posZ - z) != Blocks.bedrock)
								worldObj.setBlockToAir((int)posX + x, (int)posY + y, (int)posZ - z);
						if(!worldObj.isAirBlock((int)posX - x, (int)posY + y, (int)posZ - z))
							if(worldObj.getBlock((int)posX - x, (int)posY + y, (int)posZ - z) != Blocks.bedrock)
								worldObj.setBlockToAir((int)posX - x, (int)posY + y, (int)posZ - z);
						if(!worldObj.isAirBlock((int)posX + x, (int)posY - y, (int)posZ + z))
							if(worldObj.getBlock((int)posX + x, (int)posY - y, (int)posZ + z) != Blocks.bedrock)
								worldObj.setBlockToAir((int)posX + x, (int)posY - y, (int)posZ + z);
						if(!worldObj.isAirBlock((int)posX - x, (int)posY - y, (int)posZ + z))
							if(worldObj.getBlock((int)posX - x, (int)posY - y, (int)posZ + z) != Blocks.bedrock)
								worldObj.setBlockToAir((int)posX - x, (int)posY - y, (int)posZ + z);
						if(!worldObj.isAirBlock((int)posX + x, (int)posY - y, (int)posZ - z))
							if(worldObj.getBlock((int)posX + x, (int)posY - y, (int)posZ - z) != Blocks.bedrock)
								worldObj.setBlockToAir((int)posX + x, (int)posY - y, (int)posZ - z);
						if(!worldObj.isAirBlock((int)posX - x, (int)posY - y, (int)posZ - z))
							if(worldObj.getBlock((int)posX - x, (int)posY - y, (int)posZ - z) != Blocks.bedrock)
								worldObj.setBlockToAir((int)posX - x, (int)posY - y, (int)posZ - z);
					}

			if(worldObj.getClosestPlayer(posX, posY, posZ, 48) != null)
				worldObj.spawnEntityInWorld(new EntityGatekeeperEssence(worldObj, posX, posY, posZ));
		}
		if(deathTicks == 20 && !worldObj.isRemote)
			SpecialTextUtil.JzaharGroup(worldObj, StatCollector.translateToLocal("message.jzahar.death.1"));
		if(deathTicks == 100 && !worldObj.isRemote)
			SpecialTextUtil.JzaharGroup(worldObj, StatCollector.translateToLocal("message.jzahar.death.2"));
		if(deathTicks == 180 && !worldObj.isRemote)
			SpecialTextUtil.JzaharGroup(worldObj, StatCollector.translateToLocal("message.jzahar.death.3"));
		if(deathTicks == 260 && !worldObj.isRemote)
			SpecialTextUtil.JzaharGroup(worldObj, StatCollector.translateToLocal("message.jzahar.death.4"));
		if(deathTicks == 340 && !worldObj.isRemote)
			SpecialTextUtil.JzaharGroup(worldObj, StatCollector.translateToLocal("message.jzahar.death.5"));
		if(deathTicks == 420 && !worldObj.isRemote)
			SpecialTextUtil.JzaharGroup(worldObj, StatCollector.translateToLocal("message.jzahar.death.6"));
		if(deathTicks == 500 && !worldObj.isRemote)
			SpecialTextUtil.JzaharGroup(worldObj, StatCollector.translateToLocal("message.jzahar.death.7"));
		if(deathTicks == 580 && !worldObj.isRemote)
			SpecialTextUtil.JzaharGroup(worldObj, StatCollector.translateToLocal("message.jzahar.death.8"));
		if(deathTicks == 660 && !worldObj.isRemote)
			SpecialTextUtil.JzaharGroup(worldObj, StatCollector.translateToLocal("message.jzahar.death.9"));
		if(deathTicks == 800 && !worldObj.isRemote){
			SpecialTextUtil.JzaharGroup(worldObj, StatCollector.translateToLocal("message.jzahar.death.10"));
			setDead();
		}
	}
	
	@Override
	public boolean isPushedByWater() {
		return false;
	}
	
	@SuppressWarnings("unchecked")
	private void pullEntities(double power) {
		float size = 32F;

		List<Entity> list = worldObj.getEntitiesWithinAABB(Entity.class, boundingBox.expand(size, size, size));

		for(Entity entity : list) {
			double scale = (size - entity.getDistance(posX, posY, posZ))/size;

			Vec3 dir = Vec3.createVectorHelper(entity.posX - posX, entity.posY - posY, entity.posZ - posZ);
			dir = dir.normalize();
			entity.addVelocity(dir.xCoord * -power * scale, dir.yCoord * -power * scale, dir.zCoord * -power * scale);
		}
	}
	
	private int posneg(int num){
		return rand.nextBoolean() ? rand.nextInt(num) : -1 * rand.nextInt(num);
	}

	private void rangedAttackSkull(int par1, EntityLivingBase target) {
		fireSkull(par1, target.posX, target.posY + target.getEyeHeight() * 0.35D, target.posZ, par1 == 0 && rand.nextFloat() < 0.001F);
	}

	private void fireSkull(int par1, double par2, double par4, double par6, boolean isInvuln) {
		worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1014, (int)posX, (int)posY, (int)posZ, 0);
		double x = func_82214_u(par1);
		double y = func_82208_v(par1);
		double z = func_82213_w(par1);
		double dX = par2 - x;
		double dY = par4 - y;
		double dZ = par6 - z;
		EntityWitherSkull entityskull = new EntityWitherSkull(worldObj, this, dX, dY, dZ);
		if (isInvuln) {
			entityskull.setInvulnerable(true);
		}
		entityskull.posY = y;
		entityskull.posX = x;
		entityskull.posZ = z;
		worldObj.spawnEntityInWorld(entityskull);
	}
	
	@Override
	public void onStruckByLightning(EntityLightningBolt bolt) {
		return;
	}
}
