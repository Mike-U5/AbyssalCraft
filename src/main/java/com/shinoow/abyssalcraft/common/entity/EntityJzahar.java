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
import java.util.List;
import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityMob;
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
import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.energy.disruption.DisruptionHandler;
import com.shinoow.abyssalcraft.api.energy.EnergyEnum.DeityType;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.entity.IAntiEntity;
import com.shinoow.abyssalcraft.api.entity.ICoraliumEntity;
import com.shinoow.abyssalcraft.api.entity.IDreadEntity;
import com.shinoow.abyssalcraft.common.util.EntityUtil;
import com.shinoow.abyssalcraft.common.util.SpecialTextUtil;
import com.shinoow.abyssalcraft.common.world.TeleporterDarkRealm;

public class EntityJzahar extends EntityMob implements IBossDisplayData, IRangedAttackMob, IAntiEntity, ICoraliumEntity, IDreadEntity {

	private static final UUID attackDamageBoostUUID = UUID.fromString("648D7064-6A60-4F59-8ABE-C2C23A6DD7A9");
	private static final AttributeModifier attackDamageBoost = new AttributeModifier(attackDamageBoostUUID, "Halloween Attack Damage Boost", 10.0D, 0);
	public int deathTicks;
	private int talkTimer;
	private int skullTimer = 600;
	private int rage = 0;
	private int skillRotation = 1;
	private boolean that = false;

	public EntityJzahar(World par1World) {
		super(par1World);
		setSize(1.5F, 5.7F);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 0.35D, true));
		tasks.addTask(3, new EntityAIArrowAttack(this, 0.4D, 40, 20.0F));
		tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 0.35D));
		tasks.addTask(5, new EntityAIWander(this, 0.35D));
		tasks.addTask(6, new EntityAILookIdle(this));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
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
			getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(40.0D);
		} else {
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(666.0D);
			getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(40.0D);
		}
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
		return 5.0F;
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
	public boolean attackEntityAsMob(Entity par1Entity) {
		swingItem();
		boolean flag = super.attackEntityAsMob(par1Entity);

		return flag;
	}

	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float dmg) {
		if(par1DamageSource == DamageSource.cactus) return false;
		if(par1DamageSource == DamageSource.lava) return false;
		if(par1DamageSource == DamageSource.inWall) return false;
		if(par1DamageSource == DamageSource.drown) return false;
		if(par1DamageSource == DamageSource.fall) return false;
				
		if(dmg > 30) {
			if(dmg > 500001 || dmg < 500000) {
				if(dmg > 750001.5F || dmg < 750001) {
					dmg = 30 + worldObj.rand.nextInt(20);
				}
			} else {
				dmg = 30;
			}
		}
		
		rage += dmg;
		if (rage > 50) {
			performRageAction();
			rage -= 50;
		}

		return super.attackEntityFrom(par1DamageSource, dmg);
	}
	
	private void performRageAction() {
		int x = (int)posX;
		int y = (int)posY;
		int z = (int)posZ;
		
		switch(skillRotation) {
			// Insanity
			case 6 :
				if (!worldObj.isRemote) {
					worldObj.playSoundAtEntity(this, "abyssalcraft:jzahar.skill.1", 1, 1);
					SpecialTextUtil.JzaharGroup(worldObj, StatCollector.translateToLocal("message.jzahar.skill.1"));
					List<EntityPlayer> players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1).expand(16, 16, 16));
						
					if(!players.isEmpty()) {
						for(EntityPlayer player : players) {
							if(!player.isDead) {
								player.addPotionEffect(new PotionEffect(54, 300));
							}
						}
					}
					
				}
				break;
				
			// Disrupt
			default:
				DisruptionHandler.instance().generateDisruption(DeityType.JZAHAR, worldObj, x, y, z, worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1).expand(16, 16, 16)));
		}
		
		skillRotation++;
		if (skillRotation > 6) {
			skillRotation = 1;
		}
		
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
		if (MinecraftForge.EVENT_BUS.post(event))
			return false;
		double d3 = posX;
		double d4 = posY;
		double d5 = posZ;
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

				if (worldObj.getCollidingBoundingBoxes(this, boundingBox).isEmpty() && !worldObj.isAnyLiquid(boundingBox))
					flag = true;
			}
		}

		if (!flag) {
			setPosition(d3, d4, d5);
			return false;
		} else {
			short short1 = 128;

			for (int l = 0; l < short1; ++l) {
				double d6 = l / (short1 - 1.0D);
				float f = (rand.nextFloat() - 0.5F) * 0.2F;
				float f1 = (rand.nextFloat() - 0.5F) * 0.2F;
				float f2 = (rand.nextFloat() - 0.5F) * 0.2F;
				double d7 = d3 + (posX - d3) * d6 + (rand.nextDouble() - 0.5D) * width * 2.0D;
				double d8 = d4 + (posY - d4) * d6 + rand.nextDouble() * height;
				double d9 = d5 + (posZ - d5) * d6 + (rand.nextDouble() - 0.5D) * width * 2.0D;
				if(AbyssalCraft.particleEntity)
					worldObj.spawnParticle("largesmoke", d7, d8, d9, f, f1, f2);
			}

			worldObj.playSoundEffect(d3, d4, d5, "mob.endermen.portal", 1.0F, 1.0F);
			playSound("mob.endermen.portal", 1.0F, 1.0F);
			return true;
		}
	}

	@Override
	public void onDeath(DamageSource par1DamageSource) {
		List<EntityPlayer> players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, boundingBox.expand(10, 10, 10));
		for(EntityPlayer player : players)
			player.addStat(AbyssalCraft.killJzahar, 1);
		super.onDeath(par1DamageSource);
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
		if (par1 <= 0)
			return posZ;
		else {
			float f = (renderYawOffset + 180 * (par1 - 1)) / 180.0F * (float)Math.PI;
			float f1 = MathHelper.sin(f);
			return posZ + f1 * 1.3D;
		}
	}

	@Override
	public float getEyeHeight() {
		return height * 0.90F;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void onLivingUpdate() {
		if(talkTimer > 0) {
			talkTimer--;
		}

		float f = (rand.nextFloat() - 0.5F) * 8.0F;
		float f1 = (rand.nextFloat() - 0.5F) * 4.0F;
		float f2 = (rand.nextFloat() - 0.5F) * 8.0F;

		List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(64.0D, 64.0D, 64.0D));
		if (list != null)
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
					if(!worldObj.isRemote){
						worldObj.removeEntity(entity);
						if(entity.isDead)
							SpecialTextUtil.JzaharGroup(worldObj, StatCollector.translateToLocal("message.jzahar.banish.other"));
					} else if(AbyssalCraft.particleEntity)
						worldObj.spawnParticle("hugeexplosion", entity.posX + f, entity.posY + 2.0D + f1, entity.posZ + f2, 0.0D, 0.0D, 0.0D);
				} else if(entity instanceof EntityPlayer) {
					skullTimer--;
					if(((EntityPlayer)entity).capabilities.isCreativeMode && talkTimer == 0 && getDistanceToEntity(entity) <= 5){
						talkTimer = 1200;
						if(worldObj.isRemote)
							if(EntityUtil.isPlayerCoralium((EntityPlayer)entity))
								SpecialTextUtil.JzaharText("<insert generic text here>");
							else {
								SpecialTextUtil.JzaharText(String.format(StatCollector.translateToLocal("message.jzahar.creative.1"), entity.getCommandSenderName()));
								SpecialTextUtil.JzaharText(StatCollector.translateToLocal("message.jzahar.creative.2"));
							}
					} else if (skullTimer <= 0 && deathTicks == 0) {
						attackEntityWithRangedAttack((EntityPlayer)entity, 1);
						skullTimer = 500;
					}
				}
			}
		
		if (posY <= 0) {
			teleportRandomly();
		}
		
		super.onLivingUpdate();
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("DeathTicks", deathTicks);
		nbt.setInteger("Rage", rage);
		nbt.setInteger("SkillRotation", skillRotation);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		deathTicks = nbt.getInteger("DeathTicks");
		rage = nbt.getInteger("Rage");
		skillRotation = nbt.getInteger("SkillRotation");
	}

	private double speed = 0.05D;

	@Override
	protected void onDeathUpdate() {
		motionX = motionY = motionZ = 0;
		++deathTicks;

		if(deathTicks <= 800){
			if(deathTicks == 410)
				worldObj.playSoundAtEntity(this, "abyssalcraft:jzahar.charge", 1, 1);
			if(deathTicks < 400)
				worldObj.spawnParticle("largesmoke", posX, posY + 2.5D, posZ, 0, 0, 0);
			float f = (rand.nextFloat() - 0.5F) * 3.0F;
			float f1 = (rand.nextFloat() - 0.5F) * 2.0F;
			float f2 = (rand.nextFloat() - 0.5F) * 3.0F;
			if(deathTicks >= 100 && deathTicks < 400)
				worldObj.spawnParticle("smoke", posX + f, posY + f1, posZ + f2, 0, 0, 0);
			if(deathTicks >= 200 && deathTicks < 400){
				worldObj.spawnParticle("largesmoke", posX + f, posY + f1, posZ + f2, 0.0D, 0.0D, 0.0D);
				worldObj.spawnParticle("lava", posX, posY + 2.5D, posZ, 0, 0, 0);
			}
			if (deathTicks >= 790 && deathTicks <= 800){
				worldObj.spawnParticle("hugeexplosion", posX, posY + 1.5D, posZ, 0.0D, 0.0D, 0.0D);
				worldObj.playSoundAtEntity(this, "random.explode", 4, (1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.2F) * 0.7F);
			}
			
			if(deathTicks > 400 && deathTicks < 800) {
				blackHole(speed);
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
						worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX + posneg(3), posY + rand.nextInt(3), posZ + posneg(3), new ItemStack(AbyssalCraft.abyingot)));
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
					}
					else if(entity instanceof EntityLivingBase || entity instanceof EntityItem)
						entity.setDead();
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
	
	private void blackHole(double power) {
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

	private void func_82216_a(int par1, EntityLivingBase par2EntityLivingBase) {
		func_82209_a(par1, par2EntityLivingBase.posX, par2EntityLivingBase.posY + par2EntityLivingBase.getEyeHeight() * 0.35D, par2EntityLivingBase.posZ, par1 == 0 && rand.nextFloat() < 0.001F);
	}

	private void func_82209_a(int par1, double par2, double par4, double par6, boolean par8) {
		worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1014, (int)posX, (int)posY, (int)posZ, 0);
		double d3 = func_82214_u(par1);
		double d4 = func_82208_v(par1);
		double d5 = func_82213_w(par1);
		double d6 = par2 - d3;
		double d7 = par4 - d4;
		double d8 = par6 - d5;
		EntityWitherSkull entitywitherskull = new EntityWitherSkull(worldObj, this, d6, d7, d8);
		if (par8)
			entitywitherskull.setInvulnerable(true);
		entitywitherskull.posY = d4;
		entitywitherskull.posX = d3;
		entitywitherskull.posZ = d5;
		worldObj.spawnEntityInWorld(entitywitherskull);
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase par1EntityLivingBase, float par2) {
		func_82216_a(0, par1EntityLivingBase);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData) {
		par1EntityLivingData = super.onSpawnWithEgg(par1EntityLivingData);

		IAttributeInstance attribute = getEntityAttribute(SharedMonsterAttributes.attackDamage);
		Calendar calendar = worldObj.getCurrentDate();

		if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && rand.nextFloat() < 0.25F)
			attribute.applyModifier(attackDamageBoost);

		return par1EntityLivingData;
	}
	
}
