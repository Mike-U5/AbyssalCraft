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
package com.shinoow.abyssalcraft.common.handlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI.ACPotions;
import com.shinoow.abyssalcraft.api.biome.ACBiomes;
import com.shinoow.abyssalcraft.api.biome.IDarklandsBiome;
import com.shinoow.abyssalcraft.api.event.ACEvents.RitualEvent;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconCreationRitual;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconInfusionRitual;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconPotionAoERitual;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconPotionRitual;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconSummonRitual;
import com.shinoow.abyssalcraft.common.blocks.BlockDLTSapling;
import com.shinoow.abyssalcraft.common.blocks.BlockDreadSapling;
import com.shinoow.abyssalcraft.common.entity.EntityJzahar;
import com.shinoow.abyssalcraft.common.entity.props.EntityPersistentItem;
import com.shinoow.abyssalcraft.common.items.ItemCrystalBag;
import com.shinoow.abyssalcraft.common.items.ItemNecronomicon;
import com.shinoow.abyssalcraft.common.items.armor.ItemEthaxiumArmor;
import com.shinoow.abyssalcraft.common.ritual.NecronomiconBreedingRitual;
import com.shinoow.abyssalcraft.common.ritual.NecronomiconDreadSpawnRitual;
import com.shinoow.abyssalcraft.common.util.EntityUtil;
import com.shinoow.abyssalcraft.common.util.ItemUtil;
import com.shinoow.abyssalcraft.common.util.SpecialTextUtil;
import com.shinoow.abyssalcraft.common.world.TeleporterDarkRealm;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatisticsFile;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
import net.minecraftforge.event.terraingen.BiomeEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import thaumcraft.api.ThaumcraftApiHelper;

public class AbyssalCraftEventHooks {
	// Bonemeal events
	@SubscribeEvent
	public void bonemealUsed(BonemealEvent event) {
		if (event.getResult() == Result.DEFAULT) {
			if (event.block == AbyssalCraft.DLTSapling) {
				if (!event.world.isRemote)
					if (event.world.rand.nextFloat() < 0.45D)
						((BlockDLTSapling) AbyssalCraft.DLTSapling).growTree(event.world, event.x, event.y, event.z, event.world.rand);
				event.setResult(Result.ALLOW);
			}
			if (event.block == AbyssalCraft.dreadsapling) {
				if (!event.world.isRemote)
					if (event.world.rand.nextFloat() < 0.45D)
						((BlockDreadSapling) AbyssalCraft.dreadsapling).growTree(event.world, event.x, event.y, event.z, event.world.rand);
				event.setResult(Result.ALLOW);
			}
		}
	}

	@SubscribeEvent
	public void populateChunk(PopulateChunkEvent.Pre event) {
		Chunk chunk = event.world.getChunkFromChunkCoords(event.chunkX, event.chunkZ);
		for (ExtendedBlockStorage storage : chunk.getBlockStorageArray())
			if (storage != null && storage.getYLocation() >= 60)
				for (int x = 0; x < 16; ++x)
					for (int y = 0; y < 16; ++y)
						for (int z = 0; z < 16; ++z)
							if (chunk.getBiomeGenForWorldCoords(x, z, event.world.getWorldChunkManager()) == ACBiomes.darklands_mountains)
								if (storage.getBlockByExtId(x, y, z) == Blocks.stone)
									storage.func_150818_a(x, y, z, AbyssalCraft.Darkstone);
	}

	@SubscribeEvent
	public void onItemPickup(EntityItemPickupEvent event) {
		if (event.item.getEntityItem().getItem() == AbyssalCraft.Coralium || event.item.getEntityItem().getItem() == AbyssalCraft.Coraliumcluster9 || event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.Coraliumore))
			event.entityPlayer.addStat(AbyssalCraft.mineCorgem, 1);
		if (event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.AbyLCorOre) || event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.AbyPCorOre) || event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.AbyCorOre))
			event.entityPlayer.addStat(AbyssalCraft.mineCor, 1);
		if (event.item.getEntityItem().getItem() == AbyssalCraft.shadowgem)
			event.entityPlayer.addStat(AbyssalCraft.shadowGems, 1);
		if (event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.AbyCopOre) || event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.AbyIroOre) || event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.AbyGolOre) || event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.AbyNitOre) || event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.AbyDiaOre) || event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.AbyTinOre))
			event.entityPlayer.addStat(AbyssalCraft.mineAbyOres, 1);
		if (event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.abydreadore) || event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.dreadore))
			event.entityPlayer.addStat(AbyssalCraft.mineDread, 1);
		if (event.item.getEntityItem().getItem() == AbyssalCraft.dreadiumingot)
			event.entityPlayer.addStat(AbyssalCraft.dreadium, 1);
		if (event.item.getEntityItem().getItem() == AbyssalCraft.ethaxiumIngot)
			event.entityPlayer.addStat(AbyssalCraft.eth, 1);
		if (event.item.getEntityItem().getItem() == AbyssalCraft.necronomicon)
			event.entityPlayer.addStat(AbyssalCraft.necro, 1);
		if (event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.DGhead))
			event.entityPlayer.addStat(AbyssalCraft.ghoulhead, 1);
		if (event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.Phead))
			event.entityPlayer.addStat(AbyssalCraft.petehead, 1);
		if (event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.Whead))
			event.entityPlayer.addStat(AbyssalCraft.wilsonhead, 1);
		if (event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.Ohead))
			event.entityPlayer.addStat(AbyssalCraft.orangehead, 1);
		if (event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.PSDL))
			event.entityPlayer.addStat(AbyssalCraft.findPSDL, 1);
		if (event.item.getEntityItem().getItem() == AbyssalCraft.portalPlacer)
			event.entityPlayer.addStat(AbyssalCraft.GK1, 1);
		if (event.item.getEntityItem().getItem() == AbyssalCraft.portalPlacerDL)
			event.entityPlayer.addStat(AbyssalCraft.GK2, 1);
		if (event.item.getEntityItem().getItem() == AbyssalCraft.portalPlacerJzh)
			event.entityPlayer.addStat(AbyssalCraft.GK3, 1);
		if (event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.engraver))
			event.entityPlayer.addStat(AbyssalCraft.makeEngraver, 1);
		if (event.item.getEntityItem().getItem() == AbyssalCraft.abyssalnomicon) {
			event.entityPlayer.addStat(AbyssalCraft.killJzahar, 1);
		}
	}

	@SubscribeEvent
	public void darkRealm(LivingUpdateEvent event) {
		if (event.entityLiving instanceof EntityPlayerMP) {
			WorldServer worldServer = (WorldServer) event.entityLiving.worldObj;
			EntityPlayerMP player = (EntityPlayerMP) event.entityLiving;
			if (player.dimension == AbyssalCraft.configDimId3 && player.posY <= 0) {
				player.addPotionEffect(new PotionEffect(Potion.resistance.getId(), 80, 255));
				player.addPotionEffect(new PotionEffect(Potion.blindness.getId(), 20));
				player.mcServer.getConfigurationManager().transferPlayerToDimension(player, AbyssalCraft.configDimId4, new TeleporterDarkRealm(worldServer));
				player.addStat(AbyssalCraft.enterDarkRealm, 1);
			}
		}
	}

	@SubscribeEvent
	public void onCraftingEvent(PlayerEvent.ItemCraftedEvent event) {
		// Dreadful food used as ingredient results into more dreadful food
		boolean dreadfulcomponent = false;
		for (int c = 0; c < event.craftMatrix.getSizeInventory(); c++) {
			final ItemStack craftStack = event.craftMatrix.getStackInSlot(c);
			if (craftStack != null && ItemUtil.isDreadful(craftStack)) {
				dreadfulcomponent = true;
				break;
			}
		}

		if (dreadfulcomponent && ItemUtil.isConsumable(event.crafting)) {
			ItemUtil.dreadifyFood(event.crafting);
		}

		// Does, uh, stuff?
		for (int h = 0; h < event.craftMatrix.getSizeInventory(); h++) {
			if (event.craftMatrix.getStackInSlot(h) != null) {
				for (int i = 0; i < event.craftMatrix.getSizeInventory(); i++) {
					if (event.craftMatrix.getStackInSlot(i) != null) {
						ItemStack k = event.craftMatrix.getStackInSlot(h);

						if (k.getItem() instanceof ItemCrystalBag) {
							NBTTagCompound compound = new NBTTagCompound();
							NBTTagList items = new NBTTagList();

							if (k.stackTagCompound == null) {
								k.stackTagCompound = compound;
							}
							items = k.stackTagCompound.getTagList("ItemInventory", 10);

							ItemStack l = event.crafting;

							if (l.getItem() instanceof ItemCrystalBag) {
								((ItemCrystalBag) l.getItem()).setInventorySize(l);
								if (l.stackTagCompound == null)
									l.stackTagCompound = compound;
								l.stackTagCompound.setTag("ItemInventory", items);

								event.craftMatrix.setInventorySlotContents(i, l);
							}
						} else if (k.getItem() != null && k.getItem() instanceof ItemNecronomicon) {
							NBTTagCompound compound = new NBTTagCompound();
							String owner = "";
							float energy = 0;

							if (k.stackTagCompound == null) {
								k.stackTagCompound = compound;
							}
							owner = k.stackTagCompound.getString("owner");
							energy = k.stackTagCompound.getFloat("PotEnergy");

							ItemStack l = event.crafting;

							if (l.getItem() instanceof ItemNecronomicon) {
								if (l.stackTagCompound == null)
									l.stackTagCompound = compound;
								if (!owner.equals(""))
									l.stackTagCompound.setString("owner", owner);
								if (energy != 0)
									l.stackTagCompound.setFloat("PotEnergy", energy);

								event.craftMatrix.setInventorySlotContents(i, l);
							}
						}
					}
				}
			}
		}

		if (event.crafting.getItem() == AbyssalCraft.portalPlacer)
			event.player.addStat(AbyssalCraft.GK1, 1);
		if (event.crafting.getItem() == AbyssalCraft.shadowgem)
			event.player.addStat(AbyssalCraft.shadowGems, 1);
		if (event.crafting.getItem() == AbyssalCraft.ethaxiumIngot)
			event.player.addStat(AbyssalCraft.eth, 1);
		if (event.crafting.getItem() == AbyssalCraft.necronomicon)
			event.player.addStat(AbyssalCraft.necro, 1);
		if (event.crafting.getItem() == AbyssalCraft.necronomicon_cor)
			event.player.addStat(AbyssalCraft.necrou1, 1);
		if (event.crafting.getItem() == AbyssalCraft.necronomicon_dre)
			event.player.addStat(AbyssalCraft.necrou2, 1);
		if (event.crafting.getItem() == AbyssalCraft.necronomicon_omt)
			event.player.addStat(AbyssalCraft.necrou3, 1);
		if (event.crafting.getItem() == AbyssalCraft.abyssalnomicon)
			event.player.addStat(AbyssalCraft.abyssaln, 1);
		if (event.crafting.getItem() == Item.getItemFromBlock(AbyssalCraft.engraver))
			event.player.addStat(AbyssalCraft.makeEngraver, 1);
	}

	@SubscribeEvent
	public void noTPinOmothol(EnderTeleportEvent event) {
		if (!(event.entityLiving instanceof EntityJzahar)) {
			if (event.entityLiving.dimension == AbyssalCraft.configDimId3) {
				event.entityLiving.attackEntityFrom(DamageSource.fall, event.attackDamage);
				event.entityLiving.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 200, 1));
				event.entityLiving.addPotionEffect(new PotionEffect(Potion.weakness.id, 200, 1));
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void darklandsVillages(BiomeEvent.GetVillageBlockID event) {
		if (event.biome instanceof IDarklandsBiome) {
			if (event.original == Blocks.log || event.original == Blocks.log2) {
				event.replacement = AbyssalCraft.DLTLog;
				event.setResult(Result.DENY);
			}
			if (event.original == Blocks.cobblestone) {
				event.replacement = AbyssalCraft.Darkstone_cobble;
				event.setResult(Result.DENY);
			}
			if (event.original == Blocks.planks) {
				event.replacement = AbyssalCraft.DLTplank;
				event.setResult(Result.DENY);
			}
			if (event.original == Blocks.oak_stairs) {
				event.replacement = AbyssalCraft.DLTstairs;
				event.setResult(Result.DENY);
			}
			if (event.original == Blocks.stone_stairs) {
				event.replacement = AbyssalCraft.DCstairs;
				event.setResult(Result.DENY);
			}
			if (event.original == Blocks.fence) {
				event.replacement = AbyssalCraft.DLTfence;
				event.setResult(Result.DENY);
			}
			if (event.original == Blocks.stone_slab) {
				event.replacement = AbyssalCraft.Darkstoneslab1;
				event.setResult(Result.DENY);
			}
			if (event.original == Blocks.double_stone_slab) {
				event.replacement = AbyssalCraft.Darkstoneslab2;
				event.setResult(Result.DENY);
			}
			if (event.original == Blocks.wooden_pressure_plate) {
				event.replacement = AbyssalCraft.DLTpplate;
				event.setResult(Result.DENY);
			}
		}
	}

	@SubscribeEvent
	public void onRitualPerformed(RitualEvent.Post event) {
		if (event.ritual instanceof NecronomiconSummonRitual) {
			event.entityPlayer.addStat(AbyssalCraft.ritualSummon, 1);
			if (event.ritual.getUnlocalizedName().substring(10).equals("summonSacthoth"))
				if (!event.world.isRemote) {
					SpecialTextUtil.SacthothGroup(event.world, StatCollector.translateToLocal("message.sacthoth.spawn.1"));
				}
			if (event.ritual.getUnlocalizedName().substring(10).equals("summonAsorah")) {
				if (!event.world.isRemote) {
					SpecialTextUtil.AsorahGroup(event.world, StatCollector.translateToLocal("message.asorah.spawn"));
				}
				event.entityPlayer.addStat(AbyssalCraft.summonAsorah, 1);
			}
		}
		if (event.ritual instanceof NecronomiconCreationRitual)
			event.entityPlayer.addStat(AbyssalCraft.ritualCreate, 1);
		if (event.ritual instanceof NecronomiconBreedingRitual || event.ritual instanceof NecronomiconDreadSpawnRitual)
			event.entityPlayer.addStat(AbyssalCraft.ritualBreed, 1);
		if (event.ritual instanceof NecronomiconPotionRitual)
			event.entityPlayer.addStat(AbyssalCraft.ritualPotion, 1);
		if (event.ritual instanceof NecronomiconPotionAoERitual)
			event.entityPlayer.addStat(AbyssalCraft.ritualPotionAoE, 1);
		if (event.ritual instanceof NecronomiconInfusionRitual)
			event.entityPlayer.addStat(AbyssalCraft.ritualInfusion, 1);
	}

	// Consuming Food Effect
	@SubscribeEvent
	public void onConsume(PlayerUseItemEvent.Finish event) {
		if (ItemUtil.isConsumable(event.item)) {
			if (event.item.hasTagCompound() && event.item.stackTagCompound.getBoolean("dreadplagued")) {
				int amountToAdd = 500;
				// Reduce based on active plague
				if (event.entityPlayer.isPotionActive(ACPotions.Dread_plague)) {
					final PotionEffect effect = event.entityPlayer.getActivePotionEffect(ACPotions.Dread_plague);
					amountToAdd -= effect.getDuration() / 25;
				}
				// Apply
				if (amountToAdd > 0) {
					EntityUtil.increaseDreadPlague(event.entityPlayer, amountToAdd);
					if (!event.entityPlayer.worldObj.isRemote) {
						FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("§4It tastes rather dreadful...§f"));
					}
				}
			}
		}
	}

	// Called when the world ticks
	@SuppressWarnings("unchecked")
	@SubscribeEvent
	public void onWorldTick(TickEvent.WorldTickEvent event) {
		// Only run at end of tick
		if (event.phase == TickEvent.Phase.END) {
			return;
		}
		// Only exec every X world ticks
		if (event.world.getTotalWorldTime() % 1250 != 0) {
			return;
		}
		// Hit one random player
		final List<EntityPlayer> players = event.world.playerEntities;
		Collections.shuffle(players);

		for (int i = 0; i < players.size(); i++) {
			final EntityPlayer p = players.get(i);

			// Only happens in Omothol
			if (p.dimension != AbyssalCraft.configDimId3 && p.dimension != AbyssalCraft.configDimId4) {
				continue;
			}

			// If the player killed Jzhar once, they gain no warp in Omothol
			if (p instanceof EntityPlayerMP && ((EntityPlayerMP) p).func_147099_x() != null) {
				final StatisticsFile stats = ((EntityPlayerMP) p).func_147099_x();
				if (stats.hasAchievementUnlocked(AbyssalCraft.killJzahar)) {
					continue;
				}
			}

			// If pendant is worn, cancel warp
			if (EntityUtil.isPlayerWearingPendant(p)) {
				continue;
			}

			// Add temporary warp to the player
			ThaumcraftApiHelper.addWarpToPlayer(p, 1, true);
			event.world.playSoundAtEntity(p, "abyssalcraft:jzahar.speak", 1.5F, 1F);
			return;
		}
	}

	// Hurt Event
	@SubscribeEvent
	public void LivingHurtEvent(final LivingHurtEvent event) {
		if (event.entity instanceof EntityPlayer) {
			// Base stats
			final EntityPlayer victim = (EntityPlayer) event.entity;
			float cactusMult = 1;
			float pierceMult = 1;
			float allMult = 1;

			// Check all armor types
			for (int i = 0; i < 4; i++) {
				if (victim.getCurrentArmor(i) != null) {
					final Item armor = victim.getCurrentArmor(i).getItem();

					if (armor instanceof ItemEthaxiumArmor) {
						allMult -= 0.05;
						pierceMult -= 0.2;
						cactusMult -= 0.25;
					}
				}
			}

			// Damage multipliers
			event.ammount *= allMult;
			if (event.source == DamageSource.cactus) {
				event.ammount *= cactusMult;
			} else if (event.source.isUnblockable() && event.source != DamageSource.starve) {
				event.ammount *= pierceMult;
			}

			// Cancel if no damage is left
			if (event.ammount <= 0) {
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void EntityJoinWorld(final EntityJoinWorldEvent event) {
		// Remove curative items for incurable effects
		if (event.entity instanceof EntityLivingBase) {
			final EntityLivingBase entity = (EntityLivingBase) event.entity;

			if (entity.isPotionActive(ACPotions.Dread_plague)) {
				entity.getActivePotionEffect(ACPotions.Dread_plague).setCurativeItems(new ArrayList<ItemStack>());
			}
			if (entity.isPotionActive(ACPotions.Earthquake)) {
				entity.getActivePotionEffect(ACPotions.Earthquake).setCurativeItems(new ArrayList<ItemStack>());
			}
		}
	}
	
	@SubscribeEvent
	public void onDeath(LivingDeathEvent event) {
		if (event.entityLiving instanceof EntityLiving) {
			final EntityLiving entity = (EntityLiving)event.entityLiving;
			if(entity.hasCustomNameTag() && (entity instanceof EntityAnimal || entity instanceof IEntityOwnable)) {
				// Drop Nametag
				if (!entity.worldObj.isRemote) {
					// Create Memento
					final ItemStack mementoStack = new ItemStack(AbyssalCraft.memento);
					final NBTTagCompound petData = new NBTTagCompound();
					entity.writeToNBTOptional(petData);
					mementoStack.setTagCompound(petData);
					mementoStack.setStackDisplayName(entity.getCustomNameTag());
					
					// Convert Item to Entity
					final EntityPersistentItem entityItem = new EntityPersistentItem(entity.worldObj, entity.posX, entity.posY, entity.posZ, mementoStack);
					entityItem.lifespan = 72000;
					entityItem.delayBeforeCanPickup = 40;
					entity.worldObj.spawnEntityInWorld(entityItem);
				}
			}
		}
	}
}
