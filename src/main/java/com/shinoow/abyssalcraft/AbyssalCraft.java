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
package com.shinoow.abyssalcraft;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI.FuelType;
import com.shinoow.abyssalcraft.api.biome.ACBiomes;
import com.shinoow.abyssalcraft.api.energy.EnergyEnum.DeityType;
import com.shinoow.abyssalcraft.api.item.ItemEngraving;
import com.shinoow.abyssalcraft.common.AbyssalCrafting;
import com.shinoow.abyssalcraft.common.CommonProxy;
import com.shinoow.abyssalcraft.common.blocks.BlockACBasic;
import com.shinoow.abyssalcraft.common.blocks.BlockACButton;
import com.shinoow.abyssalcraft.common.blocks.BlockACDoubleSlab;
import com.shinoow.abyssalcraft.common.blocks.BlockACFence;
import com.shinoow.abyssalcraft.common.blocks.BlockACOre;
import com.shinoow.abyssalcraft.common.blocks.BlockACPressureplate;
import com.shinoow.abyssalcraft.common.blocks.BlockACSingleSlab;
import com.shinoow.abyssalcraft.common.blocks.BlockACStairs;
import com.shinoow.abyssalcraft.common.blocks.BlockAbyssPortal;
import com.shinoow.abyssalcraft.common.blocks.BlockAbyssalGrass;
import com.shinoow.abyssalcraft.common.blocks.BlockAltar;
import com.shinoow.abyssalcraft.common.blocks.BlockAntiliquid;
import com.shinoow.abyssalcraft.common.blocks.BlockAzathothStatue;
import com.shinoow.abyssalcraft.common.blocks.BlockCLiquid;
import com.shinoow.abyssalcraft.common.blocks.BlockChagarothSpawner;
import com.shinoow.abyssalcraft.common.blocks.BlockCoraliumfire;
import com.shinoow.abyssalcraft.common.blocks.BlockCoraliumstone;
import com.shinoow.abyssalcraft.common.blocks.BlockCthulhuStatue;
import com.shinoow.abyssalcraft.common.blocks.BlockDGhead;
import com.shinoow.abyssalcraft.common.blocks.BlockDLTLeaves;
import com.shinoow.abyssalcraft.common.blocks.BlockDLTLog;
import com.shinoow.abyssalcraft.common.blocks.BlockDLTSapling;
import com.shinoow.abyssalcraft.common.blocks.BlockDSGlow;
import com.shinoow.abyssalcraft.common.blocks.BlockDarkEthaxiumBrick;
import com.shinoow.abyssalcraft.common.blocks.BlockDarkEthaxiumPillar;
import com.shinoow.abyssalcraft.common.blocks.BlockDarklandsgrass;
import com.shinoow.abyssalcraft.common.blocks.BlockDarkstone;
import com.shinoow.abyssalcraft.common.blocks.BlockDarkstoneSlab;
import com.shinoow.abyssalcraft.common.blocks.BlockDarkstoneSlabDouble;
import com.shinoow.abyssalcraft.common.blocks.BlockDarkstonecobblewall;
import com.shinoow.abyssalcraft.common.blocks.BlockDreadAltarBottom;
import com.shinoow.abyssalcraft.common.blocks.BlockDreadAltarTop;
import com.shinoow.abyssalcraft.common.blocks.BlockDreadFire;
import com.shinoow.abyssalcraft.common.blocks.BlockDreadGrass;
import com.shinoow.abyssalcraft.common.blocks.BlockDreadLeaves;
import com.shinoow.abyssalcraft.common.blocks.BlockDreadLog;
import com.shinoow.abyssalcraft.common.blocks.BlockDreadSapling;
import com.shinoow.abyssalcraft.common.blocks.BlockDreadguardSpawner;
import com.shinoow.abyssalcraft.common.blocks.BlockDreadlandsPortal;
import com.shinoow.abyssalcraft.common.blocks.BlockEnergyPedestal;
import com.shinoow.abyssalcraft.common.blocks.BlockEngraver;
import com.shinoow.abyssalcraft.common.blocks.BlockEthaxiumBrick;
import com.shinoow.abyssalcraft.common.blocks.BlockEthaxiumPillar;
import com.shinoow.abyssalcraft.common.blocks.BlockGatekeeperMinionSpawner;
import com.shinoow.abyssalcraft.common.blocks.BlockHasturStatue;
import com.shinoow.abyssalcraft.common.blocks.BlockHouse;
import com.shinoow.abyssalcraft.common.blocks.BlockInfusedDreadstone;
import com.shinoow.abyssalcraft.common.blocks.BlockJzaharSpawner;
import com.shinoow.abyssalcraft.common.blocks.BlockJzaharStatue;
import com.shinoow.abyssalcraft.common.blocks.BlockMimicFire;
import com.shinoow.abyssalcraft.common.blocks.BlockMonolithPillar;
import com.shinoow.abyssalcraft.common.blocks.BlockNyarlathotepStatue;
import com.shinoow.abyssalcraft.common.blocks.BlockODBcore;
import com.shinoow.abyssalcraft.common.blocks.BlockOhead;
import com.shinoow.abyssalcraft.common.blocks.BlockOmotholFire;
import com.shinoow.abyssalcraft.common.blocks.BlockOmotholPortal;
import com.shinoow.abyssalcraft.common.blocks.BlockPSDL;
import com.shinoow.abyssalcraft.common.blocks.BlockPhead;
import com.shinoow.abyssalcraft.common.blocks.BlockRitualAltar;
import com.shinoow.abyssalcraft.common.blocks.BlockRitualPedestal;
import com.shinoow.abyssalcraft.common.blocks.BlockSGlow;
import com.shinoow.abyssalcraft.common.blocks.BlockSacrificialAltar;
import com.shinoow.abyssalcraft.common.blocks.BlockShoggothBiomass;
import com.shinoow.abyssalcraft.common.blocks.BlockShoggothOoze;
import com.shinoow.abyssalcraft.common.blocks.BlockShubniggurathStatue;
import com.shinoow.abyssalcraft.common.blocks.BlockSolidLava;
import com.shinoow.abyssalcraft.common.blocks.BlockTieredEnergyPedestal;
import com.shinoow.abyssalcraft.common.blocks.BlockTieredSacrificialAltar;
import com.shinoow.abyssalcraft.common.blocks.BlockWhead;
import com.shinoow.abyssalcraft.common.blocks.BlockYogsothothStatue;
import com.shinoow.abyssalcraft.common.blocks.IngotBlock;
import com.shinoow.abyssalcraft.common.blocks.itemblock.ItemAbyDreadbrickSlab;
import com.shinoow.abyssalcraft.common.blocks.itemblock.ItemAbySlab;
import com.shinoow.abyssalcraft.common.blocks.itemblock.ItemBlockColorName;
import com.shinoow.abyssalcraft.common.blocks.itemblock.ItemCstonebrickSlab;
import com.shinoow.abyssalcraft.common.blocks.itemblock.ItemDLTSlab;
import com.shinoow.abyssalcraft.common.blocks.itemblock.ItemDarkEthaxiumSlab;
import com.shinoow.abyssalcraft.common.blocks.itemblock.ItemDarkbrickSlab;
import com.shinoow.abyssalcraft.common.blocks.itemblock.ItemDarkcobbleSlab;
import com.shinoow.abyssalcraft.common.blocks.itemblock.ItemDarkstoneSlab;
import com.shinoow.abyssalcraft.common.blocks.itemblock.ItemDreadbrickSlab;
import com.shinoow.abyssalcraft.common.blocks.itemblock.ItemEthaxiumSlab;
import com.shinoow.abyssalcraft.common.blocks.itemblock.ItemMetadataBlock;
import com.shinoow.abyssalcraft.common.blocks.itemblock.ItemRitualBlock;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityAltar;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityAzathothStatue;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityChagarothSpawner;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityCthulhuStatue;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityDGhead;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityDreadAltarBottom;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityDreadAltarTop;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityDreadguardSpawner;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityEnergyPedestal;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityEngraver;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityGatekeeperMinionSpawner;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityHasturStatue;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityJzaharSpawner;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityJzaharStatue;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityMaterializer;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityNyarlathotepStatue;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityODB;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityOhead;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityPSDL;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityPhead;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityRitualAltar;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityRitualPedestal;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntitySacrificialAltar;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityShoggothBiomass;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityShubniggurathStatue;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityTieredEnergyPedestal;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityTieredSacrificialAltar;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityWhead;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityYogsothothStatue;
import com.shinoow.abyssalcraft.common.enchantments.EnchantmentLightPierce;
import com.shinoow.abyssalcraft.common.enchantments.EnchantmentWeaponInfusion;
import com.shinoow.abyssalcraft.common.entity.EntityAbygolem;
import com.shinoow.abyssalcraft.common.entity.EntityAbyssalZombie;
import com.shinoow.abyssalcraft.common.entity.EntityAntimatterArrow;
import com.shinoow.abyssalcraft.common.entity.EntityChagaroth;
import com.shinoow.abyssalcraft.common.entity.EntityChagarothFist;
import com.shinoow.abyssalcraft.common.entity.EntityChagarothSpawn;
import com.shinoow.abyssalcraft.common.entity.EntityCoraliumArrow;
import com.shinoow.abyssalcraft.common.entity.EntityDepthsGhoul;
import com.shinoow.abyssalcraft.common.entity.EntityDragonBoss;
import com.shinoow.abyssalcraft.common.entity.EntityDreadSlug;
import com.shinoow.abyssalcraft.common.entity.EntityDreadSpawn;
import com.shinoow.abyssalcraft.common.entity.EntityDreadgolem;
import com.shinoow.abyssalcraft.common.entity.EntityDreadguard;
import com.shinoow.abyssalcraft.common.entity.EntityDreadling;
import com.shinoow.abyssalcraft.common.entity.EntityGatekeeperEssence;
import com.shinoow.abyssalcraft.common.entity.EntityGatekeeperMinion;
import com.shinoow.abyssalcraft.common.entity.EntityGreaterDreadSpawn;
import com.shinoow.abyssalcraft.common.entity.EntityJzahar;
import com.shinoow.abyssalcraft.common.entity.EntityLesserDreadbeast;
import com.shinoow.abyssalcraft.common.entity.EntityLesserShoggoth;
import com.shinoow.abyssalcraft.common.entity.EntityODBPrimed;
import com.shinoow.abyssalcraft.common.entity.EntityODBcPrimed;
import com.shinoow.abyssalcraft.common.entity.EntityOmotholGhoul;
import com.shinoow.abyssalcraft.common.entity.EntityPSDLTracker;
import com.shinoow.abyssalcraft.common.entity.EntityRemnant;
import com.shinoow.abyssalcraft.common.entity.EntitySacthoth;
import com.shinoow.abyssalcraft.common.entity.EntityShadowBeast;
import com.shinoow.abyssalcraft.common.entity.EntityShadowCreature;
import com.shinoow.abyssalcraft.common.entity.EntityShadowMonster;
import com.shinoow.abyssalcraft.common.entity.EntitySkeletonGoliath;
import com.shinoow.abyssalcraft.common.entity.EntityWormHole;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiAbyssalZombie;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiBat;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiChicken;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiCow;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiCreeper;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiGhoul;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiPig;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiSpider;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiZombie;
import com.shinoow.abyssalcraft.common.entity.anti.EntityDaoloth;
import com.shinoow.abyssalcraft.common.entity.anti.EntityFallenHero;
import com.shinoow.abyssalcraft.common.entity.demon.EntityDemonChicken;
import com.shinoow.abyssalcraft.common.entity.demon.EntityDemonCow;
import com.shinoow.abyssalcraft.common.entity.demon.EntityDemonPig;
import com.shinoow.abyssalcraft.common.entity.demon.EntityEvilChicken;
import com.shinoow.abyssalcraft.common.entity.demon.EntityEvilCow;
import com.shinoow.abyssalcraft.common.entity.demon.EntityEvilpig;
import com.shinoow.abyssalcraft.common.handlers.AbyssalCraftEventHooks;
import com.shinoow.abyssalcraft.common.handlers.BucketHandler;
import com.shinoow.abyssalcraft.common.handlers.FurnaceFuelHandler;
import com.shinoow.abyssalcraft.common.handlers.IntegrationHandler;
import com.shinoow.abyssalcraft.common.handlers.InternalNecroDataHandler;
import com.shinoow.abyssalcraft.common.handlers.ReputationEventHandler;
import com.shinoow.abyssalcraft.common.items.AbyssalCraftTool;
import com.shinoow.abyssalcraft.common.items.ItemACAxe;
import com.shinoow.abyssalcraft.common.items.ItemACBasic;
import com.shinoow.abyssalcraft.common.items.ItemACFood;
import com.shinoow.abyssalcraft.common.items.ItemACHoe;
import com.shinoow.abyssalcraft.common.items.ItemACKatana;
import com.shinoow.abyssalcraft.common.items.ItemACPickaxe;
import com.shinoow.abyssalcraft.common.items.ItemACShovel;
import com.shinoow.abyssalcraft.common.items.ItemACSword;
import com.shinoow.abyssalcraft.common.items.ItemAntiBucket;
import com.shinoow.abyssalcraft.common.items.ItemCBucket;
import com.shinoow.abyssalcraft.common.items.ItemCharm;
import com.shinoow.abyssalcraft.common.items.ItemCoin;
import com.shinoow.abyssalcraft.common.items.ItemCoraliumBow;
import com.shinoow.abyssalcraft.common.items.ItemCoraliumcluster;
import com.shinoow.abyssalcraft.common.items.ItemCorb;
import com.shinoow.abyssalcraft.common.items.ItemCorbone;
import com.shinoow.abyssalcraft.common.items.ItemCorflesh;
import com.shinoow.abyssalcraft.common.items.ItemCrozier;
import com.shinoow.abyssalcraft.common.items.ItemCudgel;
import com.shinoow.abyssalcraft.common.items.ItemDrainStaff;
import com.shinoow.abyssalcraft.common.items.ItemDrainStaffBeast;
import com.shinoow.abyssalcraft.common.items.ItemDrainStaffMonster;
import com.shinoow.abyssalcraft.common.items.ItemEoA;
import com.shinoow.abyssalcraft.common.items.ItemEthaxiumBow;
import com.shinoow.abyssalcraft.common.items.ItemEthaxiumPickaxe;
import com.shinoow.abyssalcraft.common.items.ItemGatekeeperEssence;
import com.shinoow.abyssalcraft.common.items.ItemMetadata;
import com.shinoow.abyssalcraft.common.items.ItemNecronomicon;
import com.shinoow.abyssalcraft.common.items.ItemOC;
import com.shinoow.abyssalcraft.common.items.ItemOmotholFlesh;
import com.shinoow.abyssalcraft.common.items.ItemPortalPlacer;
import com.shinoow.abyssalcraft.common.items.ItemPortalPlacerDL;
import com.shinoow.abyssalcraft.common.items.ItemPortalPlacerJzh;
import com.shinoow.abyssalcraft.common.items.ItemSoulReaper;
import com.shinoow.abyssalcraft.common.items.ItemTrackerPSDL;
import com.shinoow.abyssalcraft.common.items.armor.ItemCoraliumArmor;
import com.shinoow.abyssalcraft.common.items.armor.ItemCoraliumPArmor;
import com.shinoow.abyssalcraft.common.items.armor.ItemDepthsArmor;
import com.shinoow.abyssalcraft.common.items.armor.ItemDreadArmor;
import com.shinoow.abyssalcraft.common.items.armor.ItemDreadiumArmor;
import com.shinoow.abyssalcraft.common.items.armor.ItemDreadiumSamuraiArmor;
import com.shinoow.abyssalcraft.common.items.armor.ItemEthaxiumArmor;
import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.potion.PotionAntimatter;
import com.shinoow.abyssalcraft.common.potion.PotionCplague;
import com.shinoow.abyssalcraft.common.potion.PotionDoomed;
import com.shinoow.abyssalcraft.common.potion.PotionDplague;
import com.shinoow.abyssalcraft.common.potion.PotionEarthquake;
import com.shinoow.abyssalcraft.common.structures.abyss.stronghold.MapGenAbyStronghold;
import com.shinoow.abyssalcraft.common.structures.abyss.stronghold.StructureAbyStrongholdPieces;
import com.shinoow.abyssalcraft.common.structures.dreadlands.mineshaft.StructureDreadlandsMinePieces;
import com.shinoow.abyssalcraft.common.structures.dreadlands.mineshaft.StructureDreadlandsMineStart;
import com.shinoow.abyssalcraft.common.structures.omothol.MapGenOmothol;
import com.shinoow.abyssalcraft.common.structures.omothol.StructureOmotholPieces;
import com.shinoow.abyssalcraft.common.util.ACLogger;
import com.shinoow.abyssalcraft.common.util.RitualUtil;
import com.shinoow.abyssalcraft.common.world.AbyssalCraftWorldGenerator;
import com.shinoow.abyssalcraft.common.world.WorldProviderAbyss;
import com.shinoow.abyssalcraft.common.world.WorldProviderDarkRealm;
import com.shinoow.abyssalcraft.common.world.WorldProviderDreadlands;
import com.shinoow.abyssalcraft.common.world.WorldProviderOmothol;
import com.shinoow.abyssalcraft.common.world.biome.BiomeGenAbyDreadlands;
import com.shinoow.abyssalcraft.common.world.biome.BiomeGenAbywasteland;
import com.shinoow.abyssalcraft.common.world.biome.BiomeGenCorSwamp;
import com.shinoow.abyssalcraft.common.world.biome.BiomeGenDarkRealm;
import com.shinoow.abyssalcraft.common.world.biome.BiomeGenDarklands;
import com.shinoow.abyssalcraft.common.world.biome.BiomeGenDarklandsForest;
import com.shinoow.abyssalcraft.common.world.biome.BiomeGenDarklandsHills;
import com.shinoow.abyssalcraft.common.world.biome.BiomeGenDarklandsMountains;
import com.shinoow.abyssalcraft.common.world.biome.BiomeGenDarklandsPlains;
import com.shinoow.abyssalcraft.common.world.biome.BiomeGenDreadlands;
import com.shinoow.abyssalcraft.common.world.biome.BiomeGenForestDreadlands;
import com.shinoow.abyssalcraft.common.world.biome.BiomeGenMountainDreadlands;
import com.shinoow.abyssalcraft.common.world.biome.BiomeGenOmothol;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.Metadata;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityList.EntityEggInfo;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.DungeonHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

@Mod(modid = AbyssalCraft.modid, name = AbyssalCraft.name, version = AbyssalCraft.version, dependencies = "required-after:Forge@[forgeversion,)", useMetadata = false, guiFactory = "com.shinoow.abyssalcraft.client.config.ACGuiFactory")
public class AbyssalCraft {

	public static final String version = "1.9.1.3";
	public static final String modid = "abyssalcraft";
	public static final String name = "AbyssalCraft";

	@Metadata(AbyssalCraft.modid)
	public static ModMetadata metadata;

	@Instance(AbyssalCraft.modid)
	public static AbyssalCraft instance = new AbyssalCraft();

	@SidedProxy(clientSide = "com.shinoow.abyssalcraft.client.ClientProxy", serverSide = "com.shinoow.abyssalcraft.common.CommonProxy")
	public static CommonProxy proxy;

	public static Map<String, Integer> stringtoIDMapping = new HashMap<String, Integer>();

	public static Configuration cfg;

	public static Fluid CFluid, antifluid;

	public static final Fluid LIQUID_CORALIUM = new Fluid("liquidcoralium") {
		@Override
		public String getLocalizedName(FluidStack stack) {
			return StatCollector.translateToLocal("tile.Cwater.name");
		}
	}.setDensity(3000).setTemperature(350);

	public static final Fluid LIQUID_ANTIMATTER = new Fluid("liquidantimatter") {
		@Override
		public String getLocalizedName(FluidStack stack) {
			return StatCollector.translateToLocal("tile.antiliquid.name");
		}
	}.setDensity(4000).setViscosity(1500).setTemperature(100);

	public static Achievement killghoul, enterabyss, killdragon, summonAsorah, killAsorah, enterdreadlands, killdreadguard, ghoulhead, petehead, wilsonhead, orangehead, mineCorgem, mineCor, findPSDL, GK1, GK2, GK3, summonChagaroth, killChagaroth, enterOmothol, enterDarkRealm, killJzahar, killOmotholelite, locateJzahar, necro, necrou1, necrou2, necrou3, abyssaln, ritual, ritualSummon, ritualCreate, shadowGems, mineAbyOres, mineDread, dreadium, eth, makeEngraver, ritualBreed, ritualPotion, ritualPotionAoE, ritualInfusion, shoggothInfestation;

	public static Block abyore, Darkstone, Darkstone_brick, Darkstone_cobble, SGlow, DSGlow, Darkbrickslab1, Darkbrickslab2, Darkcobbleslab1, Darkcobbleslab2, Darkgrass, DBstairs, DCstairs, DLTLeaves, DLTLog, DLTSapling, abystone, abyssalsand, fusedabyssalsand, abybrick, abyslab1, abyslab2, abystairs, Coraliumore, abyfence, DSCwall, CoraliumInfusedStone, ODBcore, portal, Darkstoneslab1, Darkstoneslab2, Coraliumfire, DSbutton, DSpplate, DLTplank, DLTbutton, DLTpplate, DLTstairs, DLTslab1, DLTslab2, Cwater, PSDL, AbyCorOre, corblock, Altar, Abybutton, Abypplate, DSBfence, DLTfence, dreadstone, magicdreadstone, abydreadstone, abydreadore, dreadore, dreadbrick, abydreadbrick, dreadgrass, dreadlog, dreadleaves, dreadsapling, dreadplanks, dreadportal, dreadfire, dreadbrickfence, dreadbrickstairs, dreadbrickslab1, dreadbrickslab2, abydreadbrickfence, abydreadbrickstairs, abydreadbrickslab1, abydreadbrickslab2, DGhead, Phead, Whead, Ohead, anticwater, cstone, cstonebrick, cstonebrickfence,
			cstonebrickstairs, cstonebrickslab1, cstonebrickslab2, cstonebutton, cstonepplate, dreadaltartop, dreadaltarbottom, dreadiumblock, dreadguardspawner, chagarothspawner, DrTfence, nitreOre, AbyIroOre, AbyGolOre, AbyDiaOre, AbyNitOre, AbyTinOre, AbyCopOre, AbyPCorOre, AbyLCorOre, solidLava, ethaxium, ethaxiumbrick, ethaxiumpillar, ethaxiumstairs, ethaxiumslab1, ethaxiumslab2, ethaxiumfence, omotholstone, ethaxiumblock, omotholportal, omotholfire, engraver, house, darkethaxiumbrick, darkethaxiumpillar, darkethaxiumstairs, darkethaxiumslab1, darkethaxiumslab2, darkethaxiumfence, ritualaltar, ritualpedestal, shoggothBlock, cthulhuStatue, hasturStatue, jzaharStatue, azathothStatue, nyarlathotepStatue, yogsothothStatue, shubniggurathStatue, monolithStone, shoggothBiomass, energyPedestal, monolithPillar, sacrificialAltar, tieredEnergyPedestal, tieredSacrificialAltar, jzaharspawner, gatekeeperminionspawner, mimicFire;

	// "secret" dev stuff
	public static Item devsword;
	// misc items
	public static Item antibucket, OC, Staff, portalPlacer, Cbucket, PSDLfinder, EoA, portalPlacerDL, cbrick, cudgel, carbonCluster, denseCarbonCluster, methane, nitre, sulfur, portalPlacerJzh, tinIngot, copperIngot, bronzeIngot, bronzeNugget, bronzeGrit, lifeCrystal, shoggothFlesh, eldritchScale, omotholFlesh, necronomicon, necronomicon_cor, necronomicon_dre, necronomicon_omt, abyssalnomicon, nugget, essence, skin, charm, cthulhuCharm, hasturCharm, jzaharCharm, azathothCharm, nyarlathotepCharm, yogsothothCharm, shubniggurathCharm, gatekeeperEssence;
	// foodstuff
	public static Item MRE, crushedwart, jellyfish, burger;
	// coin stuff
	public static Item coin, cthulhuCoin, elderCoin, jzaharCoin, engravingBlank, engravingCthulhu, engravingElder, engravingJzahar, hasturCoin, azathothCoin, nyarlathotepCoin, yogsothothCoin, shubniggurathCoin, engravingHastur, engravingAzathoth, engravingNyarlathotep, engravingYogsothoth, engravingShubniggurath;
	// shadow items
	public static Item shadowfragment, shadowshard, shadowgem, oblivionshard, soulReaper, shadowPlate, drainStaff, drainStaffMonster, drainStaffBeast;
	// dread items
	public static Item Dreadshard, dreadiumingot, dreadfragment, dreadcloth, dreadplate, dreadblade, dreadKey;
	// coralium items
	public static Item Coralium, Coraliumcluster2, Coraliumcluster3, Coraliumcluster4, Coraliumcluster5, Coraliumcluster6, Coraliumcluster7, Coraliumcluster8, Coraliumcluster9, Cpearl, Corb, Cchunk, Cingot, Cplate, Corflesh, Corbone, corbow, ethaxiumBow;
	// tools
	public static Item Corpickaxe, Coraxe, Corshovel, Corsword, Corhoe, dreadiumpickaxe, dreadiumaxe, dreadiumshovel, dreadiumsword, dreadiumhoe, dreadhilt, dreadkatana, ethPickaxe, ethAxe, ethShovel, ethSword, ethHoe, soulSword;
	// armor
	public static Item bootsD, helmetD, plateD, legsD, Corboots, Corhelmet, Corplate, Corlegs, CorbootsP, CorhelmetP, CorplateP, CorlegsP, Depthsboots, Depthshelmet, Depthsplate, Depthslegs, dreadiumboots, dreadiumhelmet, dreadiumplate, dreadiumlegs, dreadiumSboots, dreadiumShelmet, dreadiumSplate, dreadiumSlegs, ethBoots, ethHelmet, ethPlate, ethLegs;
	// upgrade kits
	public static Item CobbleU, IronU, GoldU, DiamondU, AbyssalniteU, CoraliumU, DreadiumU, EthaxiumU;
	// Ethaxium items
	public static Item ethaxium_brick, ethaxiumIngot;

	public static Potion Cplague, Dplague, antiMatter, earthquake, doom;

	public static Enchantment coraliumE, dreadE, lightPierce;

	public static final CreativeTabs tabBlock = new CreativeTabs("acblocks") {
		@Override
		public Item getTabIconItem() {
			return Item.getItemFromBlock(Darkstone);
		}
	};
	public static final CreativeTabs tabItems = new CreativeTabs("acitems") {
		@Override
		public Item getTabIconItem() {
			return necronomicon;
		}
	};
	public static final CreativeTabs tabTools = new CreativeTabs("actools") {
		@Override
		public Item getTabIconItem() {
			return ethAxe;
		}
	};
	public static final CreativeTabs tabDecoration = new CreativeTabs("acdblocks") {
		@Override
		public Item getTabIconItem() {
			return Item.getItemFromBlock(engraver);
		}
	};
	public static final CreativeTabs tabCoins = new CreativeTabs("accoins") {
		@Override
		public Item getTabIconItem() {
			return coin;
		}
	};

	// Dimension Ids
	public static int configDimId1, configDimId2, configDimId3, configDimId4;

	public static boolean keepLoaded1, keepLoaded2, keepLoaded3, keepLoaded4;

	// Biome Ids
	public static int configBiomeId1, configBiomeId2, configBiomeId3, configBiomeId4, configBiomeId5, configBiomeId6, configBiomeId7, configBiomeId8, configBiomeId9, configBiomeId10, configBiomeId11, configBiomeId12, configBiomeId13, configBiomeId14;

	public static boolean dark1, dark2, dark3, dark4, dark5, coralium1;
	public static boolean darkspawn1, darkspawn2, darkspawn3, darkspawn4, darkspawn5, coraliumspawn1;
	public static int darkWeight1, darkWeight2, darkWeight3, darkWeight4, darkWeight5, coraliumWeight;

	public static boolean shouldSpread, shouldInfect, breakLogic, destroyOcean, demonAnimalFire, darkness, particleBlock, particleEntity, hardcoreMode, useDynamicPotionIds, endAbyssalZombie, evilAnimalCreatureType, antiItemDisintegration;
	public static int evilAnimalSpawnRate;
	public static boolean shoggothOoze, oozeLeaves, oozeGrass, oozeGround, oozeSand, oozeRock, oozeCloth, oozeWood, oozeGourd, oozeIron, oozeClay;
	public static boolean generateDarklandsStructures, generateShoggothLairs, generateAbyssalWastelandPillars, generateAbyssalWastelandRuins, generateAntimatterLake, generateCoraliumLake, generateDreadlandsStalagmite;
	public static boolean generateCoraliumOre, generateNitreOre, generateAbyssalniteOre, generateAbyssalCoraliumOre, generateDreadlandsAbyssalniteOre, generateDreadedAbyssalniteOre, generateAbyssalIronOre, generateAbyssalGoldOre, generateAbyssalDiamondOre, generateAbyssalNitreOre, generateAbyssalTinOre, generateAbyssalCopperOre, generatePearlescentCoraliumOre, generateLiquifiedCoraliumOre;

	static int startEntityId = 300;

	public static final int engraverGuiID = 32;
	public static final int necronmiconGuiID = 33;

	private Item registerItem(Item item, String id) {
		GameRegistry.registerItem(item, id);
		return item;
	}

	private Block registerBlock(Block block, String id) {
		block.setBlockName(id);
		GameRegistry.registerBlock(block, id);
		return block;
	}

	private Block registerClassBlock(Block block, Class<? extends ItemBlock> clazz, String id) {
		block.setBlockName(id).setBlockTextureName(modid + ":" + id);
		GameRegistry.registerBlock(block, id);
		return block;
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ACLogger.info("Pre-initializing AbyssalCraft.");
		metadata = event.getModMetadata();
		metadata.description = metadata.description + "\n\n\u00a76Supporters: " + getSupporterList() + "\u00a7r";

		MinecraftForge.EVENT_BUS.register(new AbyssalCraftEventHooks());
		FMLCommonHandler.instance().bus().register(new AbyssalCraftEventHooks());
		MinecraftForge.TERRAIN_GEN_BUS.register(new AbyssalCraftEventHooks());
		MinecraftForge.EVENT_BUS.register(this);
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new CommonProxy());
		instance = this;
		proxy.preInit();
		AbyssalCraftAPI.setInternalNDHandler(new InternalNecroDataHandler());

		cfg = new Configuration(event.getSuggestedConfigurationFile());
		syncConfig();

		if (!useDynamicPotionIds) {
			extendPotionArray();
		}

		if (!FluidRegistry.isFluidRegistered("liquidcoralium")) {
			CFluid = LIQUID_CORALIUM;
			FluidRegistry.registerFluid(CFluid);
		} else {
			ACLogger.warning("Liquid Coralium was already registered by another mod, adding ours as alternative.");
			CFluid = FluidRegistry.getFluid("liquidcoralium");
			FluidRegistry.registerFluid(LIQUID_CORALIUM);
		}

		if (!FluidRegistry.isFluidRegistered("liquidantimatter")) {
			antifluid = LIQUID_ANTIMATTER;
			FluidRegistry.registerFluid(antifluid);
		} else {
			ACLogger.warning("Liquid Antimatter was already registered by another mod, adding ours as alternative.");
			antifluid = FluidRegistry.getFluid("liquidantimatter");
			FluidRegistry.registerFluid(LIQUID_ANTIMATTER);
		}

		// Blocks
		Darkstone = registerBlock(new BlockDarkstone().setCreativeTab(tabBlock).setStepSound(Block.soundTypeStone).setHardness(1.65F).setResistance(12.0F).setBlockTextureName(modid + ":" + "darkstone"), "darkstone");
		Darkstone_brick = new BlockACBasic(Material.rock, 1.65F, 12.0F, Block.soundTypeStone).setBlockName("DSB").setBlockTextureName(modid + ":" + "DSB");
		Darkstone_cobble = new BlockACBasic(Material.rock, 2.2F, 12.0F, Block.soundTypeStone).setBlockName("DSC").setBlockTextureName(modid + ":" + "DSC");
		DSGlow = new BlockDSGlow().setCreativeTab(tabBlock).setBlockName("DSGlow");
		SGlow = registerBlock(new BlockSGlow().setCreativeTab(tabBlock).setBlockName("DSGlow"), "SGlow");
		Darkbrickslab1 = new BlockACSingleSlab(Material.rock).setStepSound(Block.soundTypeStone).setHardness(1.65F).setResistance(12.0F).setBlockName("DSBs1").setBlockTextureName(modid + ":" + "DSB");
		Darkbrickslab2 = new BlockACDoubleSlab(Darkbrickslab1, Material.rock).setStepSound(Block.soundTypeStone).setHardness(1.65F).setResistance(12.0F).setBlockName("DSBs2").setBlockTextureName(modid + ":" + "DSB");
		Darkcobbleslab1 = new BlockACSingleSlab(Material.rock).setStepSound(Block.soundTypeStone).setHardness(1.65F).setResistance(12.0F).setBlockName("DSCs1").setBlockTextureName(modid + ":" + "DSC");
		Darkcobbleslab2 = new BlockACDoubleSlab(Darkcobbleslab1, Material.rock).setStepSound(Block.soundTypeStone).setHardness(1.65F).setResistance(12.0F).setBlockName("DSCs2").setBlockTextureName(modid + ":" + "DSC");
		Darkgrass = new BlockDarklandsgrass().setCreativeTab(tabBlock).setHardness(0.4F).setBlockName("DLG");
		DBstairs = new BlockACStairs(Darkstone_brick).setStepSound(Block.soundTypeStone).setHardness(1.65F).setResistance(12.0F).setBlockName("DSBs");
		DCstairs = new BlockACStairs(Darkstone_cobble).setStepSound(Block.soundTypeStone).setHardness(1.65F).setResistance(12.0F).setBlockName("DSCs");
		DLTLeaves = new BlockDLTLeaves().setBlockName("DLTL").setBlockTextureName(modid + ":" + "DLTL");
		DLTLog = new BlockDLTLog().setBlockName("DLTT");
		DLTSapling = new BlockDLTSapling().setStepSound(Block.soundTypeGrass).setHardness(0.0F).setResistance(0.0F).setBlockName("DLTS").setBlockTextureName(modid + ":" + "DLTS");
		abystone = new BlockACBasic(Material.rock, "pickaxe", 2, 1.8F, 12.0F, Block.soundTypeStone).setBlockName("AS").setBlockTextureName(modid + ":" + "AS");
		abyssalsand = new BlockACBasic(Material.sand, "shovel", 2, 1.8F, 12.0F, Block.soundTypeSand).setBlockName("abyssalsand").setBlockTextureName(modid + ":" + "abyssalsand");
		fusedabyssalsand = new BlockAbyssalGrass().setStepSound(Block.soundTypeGrass).setBlockName("fusedabyssalsand").setBlockTextureName(modid + ":" + "fusedabyssalsand");

		abybrick = registerClassBlock(new BlockACBasic(Material.rock, "pickaxe", 2, 1.8F, 12.0F, Block.soundTypeStone), ItemBlockColorName.class, "ASB");

		abyslab1 = new BlockACSingleSlab(Material.rock, "pickaxe", 2).setCreativeTab(tabBlock).setStepSound(Block.soundTypeStone).setHardness(1.8F).setResistance(12.0F).setBlockName("ASBs1").setBlockTextureName(modid + ":" + "ASB");
		abyslab2 = new BlockACDoubleSlab(abyslab1, Material.rock, "pickaxe", 2).setStepSound(Block.soundTypeStone).setHardness(1.8F).setResistance(12.0F).setBlockName("ASBs2").setBlockTextureName(modid + ":" + "ASB");
		abystairs = new BlockACStairs(abybrick, "pickaxe", 2).setStepSound(Block.soundTypeStone).setHardness(1.65F).setResistance(12.0F).setBlockName("ASBs");
		abyfence = new BlockACFence("ASBf", Material.rock, "pickaxe", 2).setHardness(1.8F).setResistance(12.0F).setStepSound(Block.soundTypeStone).setBlockName("ASBf").setBlockTextureName(modid + ":" + "ASBf");
	///	abyore = registerBlock(new BlockACOre(2, 3.0F, 6.0F).setBlockTextureName(modid + ":" + "abyore"), "abyore");
		Coraliumore = new BlockACOre(2, 3.0F, 6.0F).setBlockName("CO").setBlockTextureName(modid + ":" + "CO");

		DSCwall = new BlockDarkstonecobblewall(Darkstone_cobble).setHardness(1.65F).setResistance(12.0F).setStepSound(Block.soundTypeStone).setBlockName("DSCw").setBlockTextureName(modid + ":" + "DSC");
		/// Crate = new
		/// BlockCrate().setStepSound(Block.soundTypeStone).setHardness(3.0F).setResistance(6.0F).setBlockName("Crate").setBlockTextureName(modid
		/// + ":" + "Crate");
		CoraliumInfusedStone = new BlockACOre(3, 3.0F, 6.0F).setBlockName("CIS").setBlockTextureName(modid + ":" + "CIS");
		ODBcore = new BlockODBcore().setStepSound(Block.soundTypeMetal).setHardness(3.0F).setResistance(0F).setBlockName("ODBC");
		portal = new BlockAbyssPortal().setBlockName("AG").setBlockTextureName(modid + ":" + "AG");
		Darkstoneslab1 = new BlockDarkstoneSlab().setStepSound(Block.soundTypeStone).setCreativeTab(tabBlock).setHardness(1.65F).setResistance(12.0F).setBlockName("DSs1");
		Darkstoneslab2 = new BlockDarkstoneSlabDouble().setStepSound(Block.soundTypeStone).setHardness(1.65F).setResistance(12.0F).setBlockName("DSs2");
		Coraliumfire = new BlockCoraliumfire().setLightLevel(1.0F).setBlockName("Cfire");
		DSbutton = new BlockACButton(true, "DS").setHardness(0.6F).setResistance(12.0F).setBlockName("DSbb").setBlockTextureName(modid + ":" + "darkstone");
		DSpplate = new BlockACPressureplate("DS", Material.rock, BlockACPressureplate.Sensitivity.mobs).setHardness(0.6F).setResistance(12.0F).setStepSound(Block.soundTypeStone).setBlockName("DSpp").setBlockTextureName(modid + ":" + "darkstone");
		DLTplank = new BlockACBasic(Material.wood, 2.0F, 5.0F, Block.soundTypeWood).setBlockName("DLTplank").setBlockTextureName(modid + ":" + "DLTplank");
		DLTbutton = new BlockACButton(true, "DLTplank").setHardness(0.5F).setBlockName("DLTplankb").setBlockTextureName(modid + ":" + "DLTplank");
		DLTpplate = new BlockACPressureplate("DLTplank", Material.wood, BlockACPressureplate.Sensitivity.everything).setHardness(0.5F).setStepSound(Block.soundTypeWood).setBlockName("DLTpp").setBlockTextureName(modid + ":" + "DLTplank");
		DLTstairs = new BlockACStairs(DLTplank).setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setBlockName("DLTplanks");
		DLTslab1 = new BlockACSingleSlab(Material.wood).setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setBlockName("DLTplanks1").setBlockTextureName(modid + ":" + "DLTplank");
		DLTslab2 = new BlockACDoubleSlab(DLTslab1, Material.wood).setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setBlockName("DLTplanks2").setBlockTextureName(modid + ":" + "DLTplank");
		corblock = new IngotBlock(5).setBlockName("BOC").setBlockTextureName(modid + ":" + "BOC");
		PSDL = new BlockPSDL().setHardness(50.0F).setResistance(3000F).setCreativeTab(tabDecoration).setBlockName("PSDL").setBlockTextureName(modid + ":" + "PSDL");
		AbyCorOre = new BlockACOre(3, 3.0F, 6.0F).setBlockName("ACorO").setBlockTextureName(modid + ":" + "ACorO");
		Altar = new BlockAltar().setStepSound(Block.soundTypeStone).setHardness(4.0F).setResistance(300.0F).setBlockName("Altar").setBlockTextureName(modid + ":" + "Altar");
		Abybutton = new BlockACButton(false, "pickaxe", 2, "AS").setHardness(0.8F).setResistance(12.0F).setBlockName("ASbb").setBlockTextureName(modid + ":" + "AS");
		Abypplate = new BlockACPressureplate("AS", Material.rock, BlockACPressureplate.Sensitivity.mobs, "pickaxe", 2).setHardness(0.8F).setResistance(12.0F).setStepSound(Block.soundTypeStone).setBlockName("ASpp").setBlockTextureName(modid + ":" + "AS");
		DSBfence = new BlockACFence("DSBf", Material.rock).setHardness(1.65F).setResistance(12.0F).setStepSound(Block.soundTypeStone).setBlockName("DSBf");
		DLTfence = new BlockACFence("DLTplank", Material.wood).setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setBlockName("DLTf");
		dreadore = new BlockACOre(4, 2.5F, 20.0F).setBlockName("DrSO").setBlockTextureName(modid + ":" + "DrSO");
		abydreadore = new BlockACOre(4, 2.5F, 20.0F).setBlockName("AbyDrSO").setBlockTextureName(modid + ":" + "AbyDrSO");
		dreadbrick = new BlockACBasic(Material.rock, "pickaxe", 4, 2.5F, 20.0F, Block.soundTypeStone).setBlockName("DrSB").setBlockTextureName(modid + ":" + "DrSB");
		abydreadbrick = new BlockACBasic(Material.rock, "pickaxe", 4, 2.5F, 20.0F, Block.soundTypeStone).setBlockName("AbyDrSB").setBlockTextureName(modid + ":" + "AbyDrSB");
		dreadlog = new BlockDreadLog().setHardness(2.0F).setResistance(12.0F).setStepSound(Block.soundTypeWood).setBlockName("DrT");
		dreadleaves = new BlockDreadLeaves(false).setStepSound(Block.soundTypeGrass).setHardness(0.2F).setResistance(1.0F).setBlockName("DrTL");
		dreadsapling = new BlockDreadSapling().setStepSound(Block.soundTypeGrass).setHardness(0.0F).setResistance(0.0F).setBlockName("DrTS").setBlockTextureName(modid + ":" + "DrTS");
		dreadplanks = new BlockACBasic(Material.wood, 2.0F, 5.0F, Block.soundTypeWood).setBlockName("DrTplank").setBlockTextureName(modid + ":" + "DrTplank");
		dreadportal = new BlockDreadlandsPortal().setBlockName("DG").setBlockTextureName(modid + ":" + "DG");
		dreadfire = new BlockDreadFire().setLightLevel(1.0F).setBlockName("Dfire");
		DGhead = new BlockDGhead().setHardness(1.0F).setResistance(6.0F).setStepSound(Block.soundTypeCloth).setCreativeTab(tabDecoration).setBlockName("DGhead").setBlockTextureName(modid + ":" + "DGhead");
		Cwater = new BlockCLiquid().setResistance(500.0F).setLightLevel(1.0F).setBlockName("Cwater");
		dreadstone = new BlockACBasic(Material.rock, "pickaxe", 4, 2.5F, 20.0F, Block.soundTypeStone).setBlockName("DrS").setBlockTextureName(modid + ":" + "DrS");
		magicdreadstone = new BlockInfusedDreadstone(5, 2.5F, 25.0F).setBlockName("MDrS").setBlockTextureName(modid + ":" + "MDrS");
		abydreadstone = new BlockACBasic(Material.rock, "pickaxe", 4, 2.5F, 20.0F, Block.soundTypeStone).setBlockName("AbyDrS").setBlockTextureName(modid + ":" + "AbyDrS");
		dreadgrass = new BlockDreadGrass().setHardness(0.4F).setStepSound(Block.soundTypeGrass).setBlockName("DrG");
		Phead = registerBlock(new BlockPhead().setHardness(1.0F).setResistance(6.0F).setStepSound(Block.soundTypeCloth).setCreativeTab(tabDecoration).setBlockName("Phead").setBlockTextureName(modid + ":" + "phead"), "phead");
		Whead = registerBlock(new BlockWhead().setHardness(1.0F).setResistance(6.0F).setStepSound(Block.soundTypeCloth).setCreativeTab(tabDecoration).setBlockName("Whead").setBlockTextureName(modid + ":" + "whead"), "whead");
		Ohead = registerBlock(new BlockOhead().setHardness(1.0F).setResistance(6.0F).setStepSound(Block.soundTypeCloth).setCreativeTab(tabDecoration).setBlockName("Ohead").setBlockTextureName(modid + ":" + "ohead"), "ohead");

		// Bricks
		dreadbrickstairs = new BlockACStairs(dreadbrick, "pickaxe", 4).setHardness(2.5F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("DrSBs");
		dreadbrickfence = new BlockACFence("DrSBf", Material.rock, "pickaxe", 4).setHardness(2.5F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("DrSBf");
		dreadbrickslab1 = new BlockACSingleSlab(Material.rock, "pickaxe", 4).setHardness(2.5F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("DrSBs1").setBlockTextureName(modid + ":" + "DrSB");
		dreadbrickslab2 = new BlockACDoubleSlab(dreadbrickslab1, Material.rock, "pickaxe", 4).setHardness(2.5F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("DrSBs2").setBlockTextureName(modid + ":" + "DrSB");
		abydreadbrickstairs = new BlockACStairs(abydreadbrick, "pickaxe", 4).setHardness(2.5F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("AbyDrSBs");
		abydreadbrickfence = new BlockACFence("AbyDrSBf", Material.rock, "pickaxe", 4).setHardness(2.5F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("AbyDrSBf");
		abydreadbrickslab1 = new BlockACSingleSlab(Material.rock, "pickaxe", 4).setHardness(2.5F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("AbyDrSBs1").setBlockTextureName(modid + ":" + "AbyDrSB");
		abydreadbrickslab2 = new BlockACDoubleSlab(abydreadbrickslab1, Material.rock, "pickaxe", 4).setHardness(2.5F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("AbyDrSBs2").setBlockTextureName(modid + ":" + "AbyDrSB");
		anticwater = new BlockAntiliquid().setResistance(500.0F).setLightLevel(0.5F).setBlockName("antiliquid");
		cstone = new BlockCoraliumstone().setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundTypeStone).setBlockName("cstone").setBlockTextureName(modid + ":" + "cstone");
		cstonebrick = new BlockACBasic(Material.rock, 1.5F, 10.0F, Block.soundTypeStone).setBlockName("cstonebrick").setBlockTextureName(modid + ":" + "cstonebrick");
		cstonebrickfence = new BlockACFence("cstonebrick", Material.rock).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundTypeStone).setBlockName("cstonebrickf");
		cstonebrickslab1 = new BlockACSingleSlab(Material.rock).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundTypeStone).setBlockName("cstonebricks1").setBlockTextureName(modid + ":" + "cstonebrick");
		cstonebrickslab2 = new BlockACDoubleSlab(cstonebrickslab1, Material.rock).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundTypeStone).setBlockName("cstonebricks2").setBlockTextureName(modid + ":" + "cstonebrick");
		cstonebrickstairs = new BlockACStairs(cstonebrick, "pickaxe", 0).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundTypeStone).setBlockName("cstonebricks");
		cstonebutton = new BlockACButton(false, "cstone").setHardness(0.6F).setResistance(12.0F).setBlockName("cstonebutton");
		cstonepplate = new BlockACPressureplate("cstone", Material.rock, BlockACPressureplate.Sensitivity.mobs).setHardness(0.6F).setResistance(12.0F).setStepSound(Block.soundTypeStone).setBlockName("cstonepplate");
		dreadaltartop = new BlockDreadAltarTop().setCreativeTab(tabDecoration).setBlockName("dreadaltar1").setBlockTextureName(modid + ":" + "PSDL");
		dreadaltarbottom = new BlockDreadAltarBottom().setHardness(30.0F).setResistance(300.0F).setStepSound(Block.soundTypeStone).setCreativeTab(tabDecoration).setBlockName("dreadaltar2").setBlockTextureName(modid + ":" + "PSDL");
		dreadiumblock = new IngotBlock(6).setBlockName("BOD").setBlockTextureName(modid + ":" + "BOD");
		dreadguardspawner = new BlockDreadguardSpawner().setBlockName("dreadguardspawner").setBlockTextureName(modid + ":" + "PSDL");
		chagarothspawner = new BlockChagarothSpawner().setBlockName("chagarothspawner").setBlockTextureName(modid + ":" + "PSDL");
		DrTfence = new BlockACFence("DrTplank", Material.wood).setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setBlockName("DrTf");
		nitreOre = new BlockACOre(2, 3.0F, 6.0F).setBlockName("NO").setBlockTextureName(modid + ":" + "NO");
		AbyIroOre = new BlockACOre(2, 3.0F, 6.0F).setBlockName("AIO").setBlockTextureName(modid + ":" + "AIO");
		AbyGolOre = new BlockACOre(2, 5.0F, 10.0F).setBlockName("AGO").setBlockTextureName(modid + ":" + "AGO");
		AbyDiaOre = new BlockACOre(2, 5.0F, 10.0F).setBlockName("ADO").setBlockTextureName(modid + ":" + "ADO");
		AbyNitOre = new BlockACOre(2, 3.0F, 6.0F).setBlockName("ANO").setBlockTextureName(modid + ":" + "ANO");
		AbyTinOre = new BlockACOre(2, 3.0F, 6.0F).setBlockName("ATO").setBlockTextureName(modid + ":" + "ATO");
		AbyCopOre = new BlockACOre(2, 3.0F, 6.0F).setBlockName("ACO").setBlockTextureName(modid + ":" + "ACO");
		AbyLCorOre = new BlockACOre(3, 10.0F, 12.0F).setBlockName("ALCorO").setBlockTextureName(modid + ":" + "ALCorO");
		AbyPCorOre = new BlockACOre(4, 10.0F, 12.0F).setBlockName("APCorO").setBlockTextureName(modid + ":" + "APCorO");
		solidLava = new BlockSolidLava("solidLava");
		ethaxium = new BlockACBasic(Material.rock, "pickaxe", 8, 100.0F, Float.MAX_VALUE, Block.soundTypeStone).setBlockName("Eth").setBlockTextureName(modid + ":" + "Eth");
		ethaxiumbrick = new BlockEthaxiumBrick().setBlockName("EB");
		ethaxiumpillar = new BlockEthaxiumPillar().setBlockName("EBP");
		ethaxiumstairs = new BlockACStairs(ethaxiumbrick, "pickaxe", 8).setHardness(100.0F).setResistance(Float.MAX_VALUE).setStepSound(Block.soundTypeStone).setBlockName("EBs");
		ethaxiumslab1 = new BlockACSingleSlab(Material.rock, "pickaxe", 8).setHardness(100.0F).setResistance(Float.MAX_VALUE).setStepSound(Block.soundTypeStone).setBlockName("EBs1").setBlockTextureName(modid + ":" + "EB");
		ethaxiumslab2 = new BlockACDoubleSlab(ethaxiumslab1, Material.rock, "pickaxe", 8).setHardness(100.0F).setResistance(Float.MAX_VALUE).setStepSound(Block.soundTypeStone).setBlockName("EBs2").setBlockTextureName(modid + ":" + "EB");
		ethaxiumfence = new BlockACFence("EB", Material.rock, "pickaxe", 8).setHardness(100.0F).setResistance(Float.MAX_VALUE).setStepSound(Block.soundTypeStone).setBlockName("EBf");
		omotholstone = new BlockACBasic(Material.rock, "pickaxe", 6, 10.0F, 12.0F, Block.soundTypeStone).setBlockName("OS").setBlockTextureName(modid + ":" + "OS");
		ethaxiumblock = new IngotBlock(8).setResistance(Float.MAX_VALUE).setBlockName("BOE").setBlockTextureName(modid + ":" + "BOE");
		omotholportal = new BlockOmotholPortal().setBlockName("OG").setBlockTextureName(modid + ":" + "OG");
		omotholfire = new BlockOmotholFire().setLightLevel(1.0F).setBlockName("Ofire");
		engraver = new BlockEngraver().setHardness(2.5F).setResistance(12.0F).setStepSound(Block.soundTypeStone).setBlockName("engraver");
		house = new BlockHouse().setHardness(1.0F).setResistance(Float.MAX_VALUE).setStepSound(Block.soundTypeWood).setBlockName("house");
		darkethaxiumbrick = new BlockDarkEthaxiumBrick().setBlockName("DEB");
		darkethaxiumpillar = new BlockDarkEthaxiumPillar().setBlockName("DEBP");
		darkethaxiumstairs = new BlockACStairs(darkethaxiumbrick, "pickaxe", 8).setHardness(150.0F).setResistance(Float.MAX_VALUE).setStepSound(Block.soundTypeStone).setBlockName("DEBs");
		darkethaxiumslab1 = new BlockACSingleSlab(Material.rock, "pickaxe", 8).setHardness(150.0F).setResistance(Float.MAX_VALUE).setStepSound(Block.soundTypeStone).setBlockName("DEBs1").setBlockTextureName(modid + ":" + "DEB");
		darkethaxiumslab2 = new BlockACDoubleSlab(darkethaxiumslab1, Material.rock, "pickaxe", 8).setHardness(150.0F).setResistance(Float.MAX_VALUE).setStepSound(Block.soundTypeStone).setBlockName("DEBs2").setBlockTextureName(modid + ":" + "DEB");
		darkethaxiumfence = new BlockACFence("DEB", Material.rock, "pickaxe", 8).setHardness(150.0F).setResistance(Float.MAX_VALUE).setStepSound(Block.soundTypeStone).setBlockName("DEBf");
		ritualaltar = new BlockRitualAltar().setBlockName("ritualaltar");
		ritualpedestal = new BlockRitualPedestal().setBlockName("ritualpedestal");
		shoggothBlock = new BlockShoggothOoze().setBlockName("shoggothBlock").setBlockTextureName(modid + ":" + "shoggothOoze");
		cthulhuStatue = new BlockCthulhuStatue().setBlockName("cthulhuStatue").setBlockTextureName(modid + ":" + "monolithStone");
		hasturStatue = new BlockHasturStatue().setBlockName("hasturStatue").setBlockTextureName(modid + ":" + "monolithStone");
		jzaharStatue = new BlockJzaharStatue().setBlockName("jzaharStatue").setBlockTextureName(modid + ":" + "monolithStone");
		azathothStatue = new BlockAzathothStatue().setBlockName("azathothStatue").setBlockTextureName(modid + ":" + "monolithStone");
		nyarlathotepStatue = new BlockNyarlathotepStatue().setBlockName("nyarlathotepStatue").setBlockTextureName(modid + ":" + "monolithStone");
		yogsothothStatue = new BlockYogsothothStatue().setBlockName("yogsothothStatue").setBlockTextureName(modid + ":" + "monolithStone");
		shubniggurathStatue = new BlockShubniggurathStatue().setBlockName("shubniggurathStatue").setBlockTextureName(modid + ":" + "monolithStone");
		monolithStone = new BlockACBasic(Material.rock, 6.0F, 24.0F, Block.soundTypeStone).setBlockName("monolithStone").setBlockTextureName(modid + ":" + "monolithStone");
		shoggothBiomass = new BlockShoggothBiomass();
		energyPedestal = new BlockEnergyPedestal();
		monolithPillar = new BlockMonolithPillar();
		sacrificialAltar = new BlockSacrificialAltar();
		tieredEnergyPedestal = new BlockTieredEnergyPedestal();
		tieredSacrificialAltar = new BlockTieredSacrificialAltar();
		jzaharspawner = new BlockJzaharSpawner().setBlockName("jzaharspawner").setBlockTextureName(modid + ":" + "PSDL");
		gatekeeperminionspawner = new BlockGatekeeperMinionSpawner().setBlockName("gatekeeperminionspawner").setBlockTextureName(modid + ":" + "gatekeeperspawner");
		mimicFire = new BlockMimicFire().setBlockName("fire").setBlockTextureName("fire");

		checkBiomeIds(true);

		// Biome
		ACBiomes.darklands = new BiomeGenDarklands(configBiomeId1).setColor(522674).setBiomeName("Darklands");
		ACBiomes.abyssal_wastelands = new BiomeGenAbywasteland(configBiomeId2).setColor(522674).setBiomeName("Abyssal Wastelands").setDisableRain();
		ACBiomes.dreadlands = new BiomeGenDreadlands(configBiomeId3).setColor(522674).setBiomeName("Dreadlands").setDisableRain();
		ACBiomes.purified_dreadlands = new BiomeGenAbyDreadlands(configBiomeId4).setColor(522674).setBiomeName("Purified Dreadlands").setDisableRain();
		ACBiomes.dreadlands_forest = new BiomeGenForestDreadlands(configBiomeId5).setColor(522674).setBiomeName("Dreadlands Forest").setDisableRain();
		ACBiomes.dreadlands_mountains = new BiomeGenMountainDreadlands(configBiomeId6).setColor(522674).setBiomeName("Dreadlands Mountains").setDisableRain();
		ACBiomes.darklands_forest = new BiomeGenDarklandsForest(configBiomeId7).setColor(522674).setBiomeName("Darklands Forest");
		ACBiomes.darklands_plains = new BiomeGenDarklandsPlains(configBiomeId8).setColor(522674).setBiomeName("Darklands Plains").setDisableRain();
		ACBiomes.darklands_hills = new BiomeGenDarklandsHills(configBiomeId9).setColor(522674).setBiomeName("Darklands Highland");
		ACBiomes.darklands_mountains = new BiomeGenDarklandsMountains(configBiomeId10).setColor(522674).setBiomeName("Darklands Mountains").setDisableRain();
		ACBiomes.coralium_infested_swamp = new BiomeGenCorSwamp(configBiomeId11).setColor(522674).setBiomeName("Coralium Infested Swamp");
		ACBiomes.omothol = new BiomeGenOmothol(configBiomeId12).setColor(5522674).setBiomeName("Omothol").setDisableRain();
		ACBiomes.dark_realm = new BiomeGenDarkRealm(configBiomeId13).setColor(522674).setBiomeName("Dark Realm").setDisableRain();

		// "secret" dev stuff
		devsword = new AbyssalCraftTool().setUnlocalizedName("DEV_BLADE").setTextureName(modid + ":" + "Sword");

		// Misc items
		OC = registerItem(new ItemOC().setCreativeTab(tabItems).setUnlocalizedName("OC").setTextureName(modid + ":" + "OC"), "OC");
		Staff = registerItem(new ItemCrozier().setCreativeTab(tabTools).setFull3D().setUnlocalizedName("Staff").setTextureName(modid + ":" + "Staff"), "staff");
		portalPlacer = registerItem(new ItemPortalPlacer().setUnlocalizedName("GK").setTextureName(modid + ":" + "GK"), "portalplacer");
		Cbucket = registerItem(new ItemCBucket(Cwater).setCreativeTab(tabItems).setContainerItem(Items.bucket).setUnlocalizedName("Cbucket").setTextureName(modid + ":" + "Cbucket"), "cbucket");
		PSDLfinder = registerItem(new ItemTrackerPSDL().setCreativeTab(tabItems).setUnlocalizedName("PSDLf").setTextureName(modid + ":" + "PSDLf"), "psdlfinder");
		EoA = registerItem(new ItemEoA().setCreativeTab(tabItems).setUnlocalizedName("EoA").setTextureName(modid + ":" + "EoA"), "eoa");
		portalPlacerDL = registerItem(new ItemPortalPlacerDL().setUnlocalizedName("GKD").setTextureName(modid + ":" + "GKD"), "portalplacerdl");
		cbrick = registerItem(new ItemACBasic("cbrick"), "cbrick");
		cudgel = registerItem(new ItemCudgel().setCreativeTab(tabTools).setFull3D().setUnlocalizedName("cudgel").setTextureName(modid + ":" + "cudgel"), "cudgel");
		carbonCluster = registerItem(new ItemACBasic("CarbC"), "carboncluster");
		denseCarbonCluster = registerItem(new ItemACBasic("DCarbC"), "densecarboncluster");
		methane = registerItem(new ItemACBasic("methane"), "methane");
		nitre = registerItem(new ItemACBasic("nitre"), "nitre");
		sulfur = registerItem(new ItemACBasic("sulfur"), "sulfur");
		portalPlacerJzh = registerItem(new ItemPortalPlacerJzh().setUnlocalizedName("GKJ").setTextureName(modid + ":" + "GKJ"), "portalplacerjzh");
		tinIngot = registerItem(new ItemACBasic("IT"), "tingingot");
		copperIngot = registerItem(new ItemACBasic("IC"), "copperingot");
		bronzeIngot = registerItem(new ItemACBasic("ingotBronze"), "bronzeingot");
		bronzeGrit = registerItem(new ItemACBasic("gritBronze"), "bronzegrit");
		bronzeNugget = registerItem(new ItemACBasic("nuggetBronze"), "bronzenugget");
		lifeCrystal = registerItem(new ItemACBasic("lifeCrystal"), "lifecrystal");
		shoggothFlesh = registerItem(new ItemMetadata("shoggothFlesh", true, "overworld", "abyssalwasteland", "dreadlands", "omothol", "darkrealm"), "shoggothflesh");
		eldritchScale = registerItem(new ItemACBasic("eldritchScale"), "eldritchscale");
		omotholFlesh = registerItem(new ItemOmotholFlesh(3, 0.3F, false), "omotholflesh");
		necronomicon = registerItem(new ItemNecronomicon("necronomicon", 0), "necronomicon");
		necronomicon_cor = registerItem(new ItemNecronomicon("necronomicon_cor", 1), "necronomicon_cor");
		necronomicon_dre = registerItem(new ItemNecronomicon("necronomicon_dre", 2), "necronomicon_dre");
		necronomicon_omt = registerItem(new ItemNecronomicon("necronomicon_omt", 3), "necronomicon_omt");
		abyssalnomicon = registerItem(new ItemNecronomicon("abyssalnomicon", 4), "abyssalnomicon");
		nugget = registerItem(new ItemMetadata("nugget", true, "coralium", "dreadium", "ethaxium"), "ingotnugget");
		essence = registerItem(new ItemMetadata("essence", true, "abyssalwasteland", "dreadlands", "omothol"), "essence");
		skin = registerItem(new ItemMetadata("skin", true, "abyssalwasteland", "dreadlands", "omothol"), "skin");
		gatekeeperEssence = registerItem(new ItemGatekeeperEssence().setTextureName(modid + ":" + "gatekeeperessence"), "gatekeeperessence");

		// Coins
		coin = registerItem(new ItemCoin("coin"), "coin");
		cthulhuCoin = registerItem(new ItemCoin("cthulhucoin"), "cthulhucoin");
		elderCoin = registerItem(new ItemCoin("eldercoin"), "eldercoin");
		jzaharCoin = registerItem(new ItemCoin("jzaharcoin"), "jzaharcoin");
		engravingBlank = registerItem(new ItemEngraving("blank", 50).setCreativeTab(tabCoins).setTextureName(modid + ":" + "engraving_blank"), "engraving_blank");
		engravingCthulhu = registerItem(new ItemEngraving("cthulhu", 10).setCreativeTab(tabCoins).setTextureName(modid + ":" + "engraving_cthulhu"), "engraving_cthulhu");
		engravingElder = registerItem(new ItemEngraving("elder", 10).setCreativeTab(tabCoins).setTextureName(modid + ":" + "engraving_elder"), "engraving_elder");
		engravingJzahar = registerItem(new ItemEngraving("jzahar", 10).setCreativeTab(tabCoins).setTextureName(modid + ":" + "engraving_jzahar"), "engraving_jzahar");
		hasturCoin = registerItem(new ItemCoin("hasturcoin"), "hasturcoin");
		azathothCoin = registerItem(new ItemCoin("azathothcoin"), "azathothcoin");
		nyarlathotepCoin = registerItem(new ItemCoin("nyarlathotepcoin"), "nyarlathotepcoin");
		yogsothothCoin = registerItem(new ItemCoin("yogsothothcoin"), "yogsothothcoin");
		shubniggurathCoin = registerItem(new ItemCoin("shubniggurathcoin"), "shubniggurathcoin");
		engravingHastur = registerItem(new ItemEngraving("hastur", 10).setCreativeTab(tabCoins).setTextureName(modid + ":" + "engraving_hastur"), "engraving_hastur");
		engravingAzathoth = registerItem(new ItemEngraving("azathoth", 10).setCreativeTab(tabCoins).setTextureName(modid + ":" + "engraving_azathoth"), "engraving_azathoth");
		engravingNyarlathotep = registerItem(new ItemEngraving("nyarlathotep", 10).setCreativeTab(tabCoins).setTextureName(modid + ":" + "engraving_nyarlathotep"), "engraving_nyarlathotep");
		engravingYogsothoth = registerItem(new ItemEngraving("yogsothoth", 10).setCreativeTab(tabCoins).setTextureName(modid + ":" + "engraving_yogsothoth"), "engraving_yogsothoth");
		engravingShubniggurath = registerItem(new ItemEngraving("shubniggurath", 10).setCreativeTab(tabCoins).setTextureName(modid + ":" + "engraving_shubniggurath"), "engraving_shubniggurath");

		// Charms
		charm = registerItem(new ItemCharm("ritualCharm", true, null), "charm");
		cthulhuCharm = registerItem(new ItemCharm("cthulhuCharm", false, DeityType.CTHULHU).setTextureName(modid + ":" + "charm_cthulhu"), "cthulhucharm");
		hasturCharm = registerItem(new ItemCharm("hasturCharm", false, DeityType.HASTUR).setTextureName(modid + ":" + "charm_hastur"), "hasturcharm");
		jzaharCharm = registerItem(new ItemCharm("jzaharCharm", false, DeityType.JZAHAR).setTextureName(modid + ":" + "charm_jzahar"), "jzaharcharm");
		azathothCharm = registerItem(new ItemCharm("azathothCharm", false, DeityType.AZATHOTH).setTextureName(modid + ":" + "charm_azathoth"), "azathothcharm");
		nyarlathotepCharm = registerItem(new ItemCharm("nyarlathotepCharm", false, DeityType.NYARLATHOTEP).setTextureName(modid + ":" + "charm_nyarlathotep"), "nyarlathotepcharm");
		yogsothothCharm = registerItem(new ItemCharm("yogsothothCharm", false, DeityType.YOGSOTHOTH).setTextureName(modid + ":" + "charm_yogsothoth"), "yogsothothcharm");
		shubniggurathCharm = registerItem(new ItemCharm("shubniggurathCharm", false, DeityType.SHUBNIGGURATH).setTextureName(modid + ":" + "charm_shubniggurath"), "shubniggurathcharm");

		// Ethaxium
		ethaxium_brick = registerItem(new ItemACBasic("EB"), "ethbrick");
		ethaxiumIngot = registerItem(new ItemACBasic("EI"), "ethaxiumingot");

		// anti-items
		antibucket = registerItem(new ItemAntiBucket(anticwater).setCreativeTab(tabItems).setContainerItem(Items.bucket).setUnlocalizedName("Antibucket").setTextureName(modid + ":" + "Antibucket"), "antibucket");

		// Shadow items
		shadowfragment = registerItem(new ItemACBasic("SF"), "shadowfragment");
		shadowshard = registerItem(new ItemACBasic("SS"), "shadowshard");
		shadowgem = registerItem(new ItemACBasic("SG"), "shadowgem");
		oblivionshard = registerItem(new ItemACBasic("OS"), "oblivionshard");

		// Dread items
		Dreadshard = registerItem(new ItemACBasic("DSOA"), "dreadshard");
		dreadiumingot = registerItem(new ItemACBasic("DI"), "dreadumingot");
		dreadfragment = registerItem(new ItemACBasic("DF"), "dreadfragment");
		dreadcloth = registerItem(new ItemACBasic("DC"), "dreadcloth");
		dreadplate = registerItem(new ItemACBasic("DPP"), "dreadplate");
		dreadblade = registerItem(new ItemACBasic("DB"), "dreadblade");
		dreadKey = registerItem(new ItemACBasic("DK"), "dreadkey");

		// Coralium items
		Coraliumcluster2 = registerItem(new ItemCoraliumcluster("2").setCreativeTab(tabItems).setUnlocalizedName("CGCA").setTextureName(modid + ":" + "CGCA"), "ccluster2");
		Coraliumcluster3 = registerItem(new ItemCoraliumcluster("3").setCreativeTab(tabItems).setUnlocalizedName("CGCB").setTextureName(modid + ":" + "CGCB"), "ccluster3");
		Coraliumcluster4 = registerItem(new ItemCoraliumcluster("4").setCreativeTab(tabItems).setUnlocalizedName("CGCC").setTextureName(modid + ":" + "CGCC"), "ccluster4");
		Coraliumcluster5 = registerItem(new ItemCoraliumcluster("5").setCreativeTab(tabItems).setUnlocalizedName("CGCD").setTextureName(modid + ":" + "CGCD"), "ccluster5");
		Coraliumcluster6 = registerItem(new ItemCoraliumcluster("6").setCreativeTab(tabItems).setUnlocalizedName("CGCE").setTextureName(modid + ":" + "CGCE"), "ccluster6");
		Coraliumcluster7 = registerItem(new ItemCoraliumcluster("7").setCreativeTab(tabItems).setUnlocalizedName("CGCF").setTextureName(modid + ":" + "CGCF"), "ccluster7");
		Coraliumcluster8 = registerItem(new ItemCoraliumcluster("8").setCreativeTab(tabItems).setUnlocalizedName("CGCG").setTextureName(modid + ":" + "CGCG"), "ccluster8");
		Coraliumcluster9 = registerItem(new ItemCoraliumcluster("9").setCreativeTab(tabItems).setUnlocalizedName("CGCH").setTextureName(modid + ":" + "CGCH"), "ccluster9");
		Cpearl = registerItem(new ItemACBasic("CP"), "cpearl");
		Cchunk = registerItem(new ItemACBasic("CC"), "cchunk");
		Cingot = registerItem(new ItemACBasic("RCI"), "cingot");
		Cplate = registerItem(new ItemACBasic("CPP"), "platec");
		Coralium = registerItem(new ItemACBasic("CG"), "coralium");
		Corb = registerItem(new ItemCorb().setCreativeTab(tabTools).setUnlocalizedName("TG").setTextureName(modid + ":" + "TG"), "corb");
		Corflesh = registerItem(new ItemCorflesh(2, 0.1F, false, "CF"), "corflesh");
		Corbone = registerItem(new ItemCorbone(2, 0.1F, false, "CB"), "corbone");
		corbow = registerItem(new ItemCoraliumBow(20.0F, 0, 8, 16).setUnlocalizedName("Corbow").setTextureName(modid + ":" + "Corbow"), "corbow");
		ethaxiumBow = registerItem(new ItemEthaxiumBow(20.0F, 0, 8, 16).setUnlocalizedName("ethaxiumBow").setTextureName(modid + ":" + "ethaxiumBow"), "ethaxiumBow");

		// Tools
		/// pickaxe = registerItem(new ItemACPickaxe(AbyssalCraftAPI.darkstoneTool,
		// "DP", 1), "dpick");
		/// axe = registerItem(new ItemACAxe(AbyssalCraftAPI.darkstoneTool, "DA", 1),
		// "daxe");
		/// shovel = registerItem(new ItemACShovel(AbyssalCraftAPI.darkstoneTool, "DS",
		// 1), "dshovel");
		/// sword = registerItem(new ItemACSword(AbyssalCraftAPI.darkstoneTool, "DSW"),
		// "dsword");
		/// hoe = registerItem(new ItemACHoe(AbyssalCraftAPI.darkstoneTool, "DH"),
		// "dhoe");
		Corpickaxe = registerItem(new ItemACPickaxe(AbyssalCraftAPI.refinedCoraliumTool, "RCP", 5, EnumChatFormatting.AQUA), "corpick");
		Coraxe = registerItem(new ItemACAxe(AbyssalCraftAPI.refinedCoraliumTool, "RCA", 5, EnumChatFormatting.AQUA), "coraxe");
		Corshovel = registerItem(new ItemACShovel(AbyssalCraftAPI.refinedCoraliumTool, "RCS", 5, EnumChatFormatting.AQUA), "corshovel");
		Corsword = registerItem(new ItemACSword(AbyssalCraftAPI.refinedCoraliumTool, "RCSW", EnumChatFormatting.AQUA), "corsword");
		Corhoe = registerItem(new ItemACHoe(AbyssalCraftAPI.refinedCoraliumTool, "RCH", EnumChatFormatting.AQUA), "corhoe");
		dreadiumpickaxe = registerItem(new ItemACPickaxe(AbyssalCraftAPI.dreadiumTool, "DDP", 6, EnumChatFormatting.DARK_RED), "dreadiumpickaxe");
		dreadiumaxe = registerItem(new ItemACAxe(AbyssalCraftAPI.dreadiumTool, "DDA", 6, EnumChatFormatting.DARK_RED), "dreadiumaxe");
		dreadiumshovel = registerItem(new ItemACShovel(AbyssalCraftAPI.dreadiumTool, "DDS", 6, EnumChatFormatting.DARK_RED), "dreadiumshovel");
		dreadiumsword = registerItem(new ItemACSword(AbyssalCraftAPI.dreadiumTool, "DDSW", EnumChatFormatting.DARK_RED), "dreadiumsword");
		dreadiumhoe = registerItem(new ItemACHoe(AbyssalCraftAPI.dreadiumTool, "DDH", EnumChatFormatting.DARK_RED), "dreadiumhoe");
		dreadhilt = registerItem(new ItemACBasic("hilt"), "dreadhilt");
		dreadkatana = registerItem(new ItemACKatana(AbyssalCraftAPI.dreadiumTool, "katana", EnumChatFormatting.DARK_RED), "dreadkatana");
		soulReaper = registerItem(new ItemSoulReaper("soulReaper"), "soulreaper");
		ethPickaxe = registerItem(new ItemEthaxiumPickaxe(AbyssalCraftAPI.ethaxiumTool, "EP"), "ethaxiumpickaxe");
		ethAxe = registerItem(new ItemACAxe(AbyssalCraftAPI.ethaxiumTool, "EA", 8, EnumChatFormatting.AQUA), "ethaxiumaxe");
		ethShovel = registerItem(new ItemACShovel(AbyssalCraftAPI.ethaxiumTool, "ES", 8, EnumChatFormatting.AQUA), "ethaxiumshovel");
		ethSword = registerItem(new ItemACSword(AbyssalCraftAPI.ethaxiumTool, "ESW", EnumChatFormatting.AQUA), "ethaxiumsword");
		ethHoe = registerItem(new ItemACHoe(AbyssalCraftAPI.ethaxiumTool, "EH", EnumChatFormatting.AQUA), "ethaxiumhoe");
		drainStaff = registerItem(new ItemDrainStaff(), "drainstaff");
		drainStaffMonster = registerItem(new ItemDrainStaffMonster(), "drainstaffmonster");
		drainStaffBeast = registerItem(new ItemDrainStaffBeast(), "drainstaffbeast");

		// Armor
		/// boots = registerItem(new
		// ItemAbyssalniteArmor(AbyssalCraftAPI.abyssalniteArmor, 5,
		// 3).setUnlocalizedName("AAB").setTextureName(modid + ":" + "AAB"), "aboots");
		/// helmet = registerItem(new
		// ItemAbyssalniteArmor(AbyssalCraftAPI.abyssalniteArmor, 5,
		// 0).setUnlocalizedName("AAH").setTextureName(modid + ":" + "AAh"), "ahelmet");
		/// plate = registerItem(new
		// ItemAbyssalniteArmor(AbyssalCraftAPI.abyssalniteArmor, 5,
		// 1).setUnlocalizedName("AAC").setTextureName(modid + ":" + "AAC"), "aplate");
		/// legs = registerItem(new
		// ItemAbyssalniteArmor(AbyssalCraftAPI.abyssalniteArmor, 5,
		// 2).setUnlocalizedName("AAP").setTextureName(modid + ":" + "AAP"), "alegs");
		bootsD = registerItem(new ItemDreadArmor(AbyssalCraftAPI.dreadedAbyssalniteArmor, 5, 3).setUnlocalizedName("ADAB").setTextureName(modid + ":" + "ADAB"), "dboots");
		helmetD = registerItem(new ItemDreadArmor(AbyssalCraftAPI.dreadedAbyssalniteArmor, 5, 0).setUnlocalizedName("ADAH").setTextureName(modid + ":" + "ADAH"), "dhelmet");
		plateD = registerItem(new ItemDreadArmor(AbyssalCraftAPI.dreadedAbyssalniteArmor, 5, 1).setUnlocalizedName("ADAC").setTextureName(modid + ":" + "ADAC"), "dplate");
		legsD = registerItem(new ItemDreadArmor(AbyssalCraftAPI.dreadedAbyssalniteArmor, 5, 2).setUnlocalizedName("ADAP").setTextureName(modid + ":" + "ADAP"), "dlegs");
		Corboots = registerItem(new ItemCoraliumArmor(AbyssalCraftAPI.refinedCoraliumArmor, 5, 3).setUnlocalizedName("ACB").setTextureName(modid + ":" + "ACB"), "corboots");
		Corhelmet = registerItem(new ItemCoraliumArmor(AbyssalCraftAPI.refinedCoraliumArmor, 5, 0).setUnlocalizedName("ACH").setTextureName(modid + ":" + "ACH"), "corhelmet");
		Corplate = registerItem(new ItemCoraliumArmor(AbyssalCraftAPI.refinedCoraliumArmor, 5, 1).setUnlocalizedName("ACC").setTextureName(modid + ":" + "ACC"), "corplate");
		Corlegs = registerItem(new ItemCoraliumArmor(AbyssalCraftAPI.refinedCoraliumArmor, 5, 2).setUnlocalizedName("ACP").setTextureName(modid + ":" + "ACP"), "corlegs");
		CorbootsP = registerItem(new ItemCoraliumPArmor(AbyssalCraftAPI.platedCoraliumArmor, 5, 3).setUnlocalizedName("ACBP").setTextureName(modid + ":" + "ACBP"), "corbootsp");
		CorhelmetP = registerItem(new ItemCoraliumPArmor(AbyssalCraftAPI.platedCoraliumArmor, 5, 0).setUnlocalizedName("ACHP").setTextureName(modid + ":" + "ACHP"), "corhelmetp");
		CorplateP = registerItem(new ItemCoraliumPArmor(AbyssalCraftAPI.platedCoraliumArmor, 5, 1).setUnlocalizedName("ACCP").setTextureName(modid + ":" + "ACCP"), "corplatep");
		CorlegsP = registerItem(new ItemCoraliumPArmor(AbyssalCraftAPI.platedCoraliumArmor, 5, 2).setUnlocalizedName("ACPP").setTextureName(modid + ":" + "ACPP"), "corlegsp");
		Depthsboots = registerItem(new ItemDepthsArmor(AbyssalCraftAPI.depthsArmor, 5, 3).setUnlocalizedName("ADB").setTextureName(modid + ":" + "ADB"), "depthsboots");
		Depthshelmet = registerItem(new ItemDepthsArmor(AbyssalCraftAPI.depthsArmor, 5, 0).setUnlocalizedName("ADH").setTextureName(modid + ":" + "ADH"), "depthshelmet");
		Depthsplate = registerItem(new ItemDepthsArmor(AbyssalCraftAPI.depthsArmor, 5, 1).setUnlocalizedName("ADC").setTextureName(modid + ":" + "ADC"), "depthsplate");
		Depthslegs = registerItem(new ItemDepthsArmor(AbyssalCraftAPI.depthsArmor, 5, 2).setUnlocalizedName("ADP").setTextureName(modid + ":" + "ADP"), "depthslegs");
		dreadiumboots = registerItem(new ItemDreadiumArmor(AbyssalCraftAPI.dreadiumArmor, 5, 3).setUnlocalizedName("ADDB").setTextureName(modid + ":" + "ADDB"), "dreadiumboots");
		dreadiumhelmet = registerItem(new ItemDreadiumArmor(AbyssalCraftAPI.dreadiumArmor, 5, 0).setUnlocalizedName("ADDH").setTextureName(modid + ":" + "ADDH"), "dreadiumhelmet");
		dreadiumplate = registerItem(new ItemDreadiumArmor(AbyssalCraftAPI.dreadiumArmor, 5, 1).setUnlocalizedName("ADDC").setTextureName(modid + ":" + "ADDC"), "dreadiumplate");
		dreadiumlegs = registerItem(new ItemDreadiumArmor(AbyssalCraftAPI.dreadiumArmor, 5, 2).setUnlocalizedName("ADDP").setTextureName(modid + ":" + "ADDP"), "dreadiumlegs");
		dreadiumSboots = registerItem(new ItemDreadiumSamuraiArmor(AbyssalCraftAPI.dreadiumSamuraiArmor, 5, 3).setUnlocalizedName("ADSB").setTextureName(modid + ":" + "ADSB"), "dreadiumsamuraiboots");
		dreadiumShelmet = registerItem(new ItemDreadiumSamuraiArmor(AbyssalCraftAPI.dreadiumSamuraiArmor, 5, 0).setUnlocalizedName("ADSH").setTextureName(modid + ":" + "ADSH"), "dreadiumsamuraihelmet");
		dreadiumSplate = registerItem(new ItemDreadiumSamuraiArmor(AbyssalCraftAPI.dreadiumSamuraiArmor, 5, 1).setUnlocalizedName("ADSC").setTextureName(modid + ":" + "ADSC"), "dreadiumsamuraiplate");
		dreadiumSlegs = registerItem(new ItemDreadiumSamuraiArmor(AbyssalCraftAPI.dreadiumSamuraiArmor, 5, 2).setUnlocalizedName("ADSP").setTextureName(modid + ":" + "ADSP"), "dreadiumsamurailegs");
		ethBoots = registerItem(new ItemEthaxiumArmor(AbyssalCraftAPI.ethaxiumArmor, 5, 3).setUnlocalizedName("AEB").setTextureName(modid + ":" + "AEB"), "ethaxiumboots");
		ethHelmet = registerItem(new ItemEthaxiumArmor(AbyssalCraftAPI.ethaxiumArmor, 5, 0).setUnlocalizedName("AEH").setTextureName(modid + ":" + "AEH"), "ethaxiumhelmet");
		ethPlate = registerItem(new ItemEthaxiumArmor(AbyssalCraftAPI.ethaxiumArmor, 5, 1).setUnlocalizedName("AEC").setTextureName(modid + ":" + "AEC"), "ethaxiumplate");
		ethLegs = registerItem(new ItemEthaxiumArmor(AbyssalCraftAPI.ethaxiumArmor, 5, 2).setUnlocalizedName("AEP").setTextureName(modid + ":" + "AEP"), "ethaxiumlegs");

		// Special
		crushedwart = registerItem(new ItemACBasic("crushedwart"), "crushedwart").setPotionEffect("+4");
		jellyfish = registerItem(new ItemACBasic("jellyFish"), "jellyfish");

		// Foodstuffs (MRE stays for remnant trades)
		MRE = registerItem(new ItemACFood(20, 1F, false).setUnlocalizedName("MRE").setTextureName(modid + ":" + "MRE"), "mre");

		// Tile Entities
		GameRegistry.registerTileEntity(TileEntityPSDL.class, "tileEntityPSDL");
		GameRegistry.registerTileEntity(TileEntityAltar.class, "tileEntityAltar");
		GameRegistry.registerTileEntity(TileEntityDGhead.class, "tileEntityDGhead");
		GameRegistry.registerTileEntity(TileEntityPhead.class, "tileEntityPhead");
		GameRegistry.registerTileEntity(TileEntityWhead.class, "tileEntityWhead");
		GameRegistry.registerTileEntity(TileEntityOhead.class, "tileEntityOhead");
		GameRegistry.registerTileEntity(TileEntityDreadAltarTop.class, "tileEntityDreadAltarTop");
		GameRegistry.registerTileEntity(TileEntityDreadAltarBottom.class, "tileEntityDreadAltarBottom");
		GameRegistry.registerTileEntity(TileEntityDreadguardSpawner.class, "tileEntityDradguardSpawner");
		GameRegistry.registerTileEntity(TileEntityChagarothSpawner.class, "tileEntityChagarothSpawner");
		GameRegistry.registerTileEntity(TileEntityODB.class, "tileEntityODB");
		GameRegistry.registerTileEntity(TileEntityEngraver.class, "tileEntityEngraver");
		GameRegistry.registerTileEntity(TileEntityMaterializer.class, "tileEntityMaterializer");
		GameRegistry.registerTileEntity(TileEntityRitualAltar.class, "tileEntityRitualAltar");
		GameRegistry.registerTileEntity(TileEntityRitualPedestal.class, "tileEntityRitualPedestal");
		GameRegistry.registerTileEntity(TileEntityCthulhuStatue.class, "tileEntityCthulhuStatue");
		GameRegistry.registerTileEntity(TileEntityHasturStatue.class, "tileEntityHasturStatue");
		GameRegistry.registerTileEntity(TileEntityJzaharStatue.class, "tileEntityJzaharStatue");
		GameRegistry.registerTileEntity(TileEntityAzathothStatue.class, "tileEntityAzathothStatue");
		GameRegistry.registerTileEntity(TileEntityNyarlathotepStatue.class, "tileEntityNyarlathotepStatue");
		GameRegistry.registerTileEntity(TileEntityYogsothothStatue.class, "tileEntityyogsothothStatue");
		GameRegistry.registerTileEntity(TileEntityShubniggurathStatue.class, "tileEntityShubniggurathStatue");
		GameRegistry.registerTileEntity(TileEntityShoggothBiomass.class, "tileEntityShoggothBiomass");
		GameRegistry.registerTileEntity(TileEntityEnergyPedestal.class, "tileEntityEnergyPedestal");
		GameRegistry.registerTileEntity(TileEntitySacrificialAltar.class, "tileEntitySacrificialAltar");
		GameRegistry.registerTileEntity(TileEntityTieredEnergyPedestal.class, "tileEntityTieredEnergyPedestal");
		GameRegistry.registerTileEntity(TileEntityTieredSacrificialAltar.class, "tileEntityTieredSacrificialAltar");
		GameRegistry.registerTileEntity(TileEntityJzaharSpawner.class, "tileEntityJzaharSpawner");
		GameRegistry.registerTileEntity(TileEntityGatekeeperMinionSpawner.class, "tileEntityGatekeeperMinionSpawner");

		Cplague = new PotionCplague(useDynamicPotionIds ? getNextAvailablePotionId() : AbyssalCraftAPI.potionId1, true, 0x00FFFF).setIconIndex(1, 0).setPotionName("potion.Cplague");
		Dplague = new PotionDplague(useDynamicPotionIds ? getNextAvailablePotionId() : AbyssalCraftAPI.potionId2, true, 0xAD1313).setIconIndex(1, 0).setPotionName("potion.Dplague");
		antiMatter = new PotionAntimatter(useDynamicPotionIds ? getNextAvailablePotionId() : AbyssalCraftAPI.potionId3, true, 0xFFFFFF).setIconIndex(1, 0).setPotionName("potion.Antimatter");
		earthquake = new PotionEarthquake(useDynamicPotionIds ? getNextAvailablePotionId() : AbyssalCraftAPI.potionId4, true, 0x826C34).setIconIndex(1, 0).setPotionName("potion.Earthquake");
		doom = new PotionDoomed(useDynamicPotionIds ? getNextAvailablePotionId() : AbyssalCraftAPI.potionId5, true, 0x826C34).setIconIndex(1, 0).setPotionName("potion.Doom");

		coraliumE = new EnchantmentWeaponInfusion(getNextAvailableEnchantmentId(), 2, "coralium");
		dreadE = new EnchantmentWeaponInfusion(getNextAvailableEnchantmentId(), 2, "dread");
		lightPierce = new EnchantmentLightPierce(getNextAvailableEnchantmentId());

		AbyssalCraftAPI.enchId1 = coraliumE.effectId;
		AbyssalCraftAPI.enchId2 = dreadE.effectId;
		AbyssalCraftAPI.enchId3 = lightPierce.effectId;

		if (useDynamicPotionIds) {
			AbyssalCraftAPI.potionId1 = Cplague.id;
			AbyssalCraftAPI.potionId2 = Dplague.id;
			AbyssalCraftAPI.potionId3 = antiMatter.id;
			AbyssalCraftAPI.potionId4 = earthquake.id;
			AbyssalCraftAPI.potionId5 = doom.id;
		}

		// Block Register
		// GameRegistry.registerBlock(Darkstone, "darkstone");
		GameRegistry.registerBlock(Darkstone_cobble, "darkstone_cobble");
		GameRegistry.registerBlock(Darkstone_brick, "darkstone_brick");
		GameRegistry.registerBlock(DSGlow, "dsglow");
		GameRegistry.registerBlock(Darkbrickslab1, ItemDarkbrickSlab.class, "darkbrickslab1");
		GameRegistry.registerBlock(Darkbrickslab2, ItemDarkbrickSlab.class, "darkbrickslab2");
		GameRegistry.registerBlock(Darkcobbleslab1, ItemDarkcobbleSlab.class, "darkcobblelsab1");
		GameRegistry.registerBlock(Darkcobbleslab2, ItemDarkcobbleSlab.class, "darkcobblelsab2");
		GameRegistry.registerBlock(Darkgrass, "darkgrass");
		GameRegistry.registerBlock(DBstairs, "dbstairs");
		GameRegistry.registerBlock(DCstairs, "dcstairs");
		GameRegistry.registerBlock(DLTLeaves, "dltleaves");
		GameRegistry.registerBlock(DLTLog, "dltlog");
		GameRegistry.registerBlock(DLTSapling, "dltsapling");
		GameRegistry.registerBlock(abystone, ItemBlockColorName.class, "abystone");
		GameRegistry.registerBlock(abyssalsand, ItemBlockColorName.class, "abyssalsand");
		GameRegistry.registerBlock(fusedabyssalsand, ItemBlockColorName.class, "fusedabyssalsand");
		GameRegistry.registerBlock(abyslab1, ItemAbySlab.class, "abyslab1");
		GameRegistry.registerBlock(abyslab2, ItemAbySlab.class, "abyslab2");
		GameRegistry.registerBlock(abystairs, ItemBlockColorName.class, "abystairs");
		GameRegistry.registerBlock(Coraliumore, "coraliumore");

		GameRegistry.registerBlock(abyfence, ItemBlockColorName.class, "abyfence");
		GameRegistry.registerBlock(DSCwall, "dscwall");
		GameRegistry.registerBlock(CoraliumInfusedStone, "coraliumstone");
		GameRegistry.registerBlock(ODBcore, ItemBlockColorName.class, "odbcore");
		/// GameRegistry.registerBlock(Crate, "Crate");
		GameRegistry.registerBlock(portal, "portal");
		GameRegistry.registerBlock(Darkstoneslab1, ItemDarkstoneSlab.class, "darkstoneslab1");
		GameRegistry.registerBlock(Darkstoneslab2, ItemDarkstoneSlab.class, "darkstoneslab2");
		GameRegistry.registerBlock(Coraliumfire, "coraliumfire");
		GameRegistry.registerBlock(DSbutton, "dsbutton");
		GameRegistry.registerBlock(DSpplate, "dspplate");
		GameRegistry.registerBlock(DLTplank, "dltplank");
		GameRegistry.registerBlock(DLTbutton, "dltbutton");
		GameRegistry.registerBlock(DLTpplate, "dltpplate");
		GameRegistry.registerBlock(DLTstairs, "dltstairs");
		GameRegistry.registerBlock(DLTslab1, ItemDLTSlab.class, "dltslab1");
		GameRegistry.registerBlock(DLTslab2, ItemDLTSlab.class, "dltslab2");
		GameRegistry.registerBlock(Cwater, "cwater");
		GameRegistry.registerBlock(corblock, ItemBlockColorName.class, "corblock");
		GameRegistry.registerBlock(PSDL, "psdl");
		GameRegistry.registerBlock(AbyCorOre, "abycorore");
		GameRegistry.registerBlock(Altar, "altar");
		GameRegistry.registerBlock(Abybutton, ItemBlockColorName.class, "abybutton");
		GameRegistry.registerBlock(Abypplate, ItemBlockColorName.class, "abypplate");
		GameRegistry.registerBlock(DSBfence, "dsbfence");
		GameRegistry.registerBlock(DLTfence, "dltfence");
		GameRegistry.registerBlock(magicdreadstone, "magicdreadstone");
		GameRegistry.registerBlock(dreadstone, "dreadstone");
		GameRegistry.registerBlock(abydreadstone, "abydreadstone");
		GameRegistry.registerBlock(abydreadore, "abydreadore");
		GameRegistry.registerBlock(dreadore, "dreadore");
		GameRegistry.registerBlock(dreadbrick, "dreadbrick");
		GameRegistry.registerBlock(abydreadbrick, "abydreadbrick");
		GameRegistry.registerBlock(dreadgrass, "dreadgrass");
		GameRegistry.registerBlock(dreadlog, "dreadlog");
		GameRegistry.registerBlock(dreadleaves, "dreadleaves");
		GameRegistry.registerBlock(dreadsapling, "dreadsapling");
		GameRegistry.registerBlock(dreadplanks, "dreadplanks");
		GameRegistry.registerBlock(dreadportal, "dreadportal");
		GameRegistry.registerBlock(dreadfire, "dreadfire");
		GameRegistry.registerBlock(DGhead, "dghead");
		GameRegistry.registerBlock(dreadbrickstairs, "dreadbrickstairs");
		GameRegistry.registerBlock(dreadbrickfence, "dreadbrickfence");
		GameRegistry.registerBlock(dreadbrickslab1, ItemDreadbrickSlab.class, "dreadbrickslab1");
		GameRegistry.registerBlock(dreadbrickslab2, ItemDreadbrickSlab.class, "dreadbrickslab2");
		GameRegistry.registerBlock(abydreadbrickstairs, "abydreadbrickstairs");
		GameRegistry.registerBlock(abydreadbrickfence, "abydreadbrickfence");
		GameRegistry.registerBlock(abydreadbrickslab1, ItemAbyDreadbrickSlab.class, "abydreadbrick1");
		GameRegistry.registerBlock(abydreadbrickslab2, ItemAbyDreadbrickSlab.class, "abydreadbrick2");
		GameRegistry.registerBlock(anticwater, "antiwater");
		GameRegistry.registerBlock(cstone, "cstone");
		GameRegistry.registerBlock(cstonebrick, "cstonebrick");
		GameRegistry.registerBlock(cstonebrickfence, "cstonebrickfence");
		GameRegistry.registerBlock(cstonebrickslab1, ItemCstonebrickSlab.class, "cstonebrickslab1");
		GameRegistry.registerBlock(cstonebrickslab2, ItemCstonebrickSlab.class, "cstobebrickslab2");
		GameRegistry.registerBlock(cstonebrickstairs, "cstonebrickstairs");
		GameRegistry.registerBlock(cstonebutton, "cstonebutton");
		GameRegistry.registerBlock(cstonepplate, "cstonepplate");
		GameRegistry.registerBlock(dreadaltartop, "dreadaltartop");
		GameRegistry.registerBlock(dreadaltarbottom, "dreadaltarbottom");
		GameRegistry.registerBlock(dreadiumblock, ItemBlockColorName.class, "dreadiumblock");
		GameRegistry.registerBlock(dreadguardspawner, "dreadguardspawner");
		GameRegistry.registerBlock(chagarothspawner, "chagarothspawner");
		GameRegistry.registerBlock(DrTfence, "drtfence");
		GameRegistry.registerBlock(nitreOre, "nitreore");
		GameRegistry.registerBlock(AbyIroOre, "abyiroore");
		GameRegistry.registerBlock(AbyGolOre, "abygolore");
		GameRegistry.registerBlock(AbyDiaOre, "abydiaore");
		GameRegistry.registerBlock(AbyNitOre, "abynitore");
		GameRegistry.registerBlock(AbyTinOre, "abytinore");
		GameRegistry.registerBlock(AbyCopOre, "abycopore");
		GameRegistry.registerBlock(AbyPCorOre, "abypcorore");
		GameRegistry.registerBlock(AbyLCorOre, "abylcorore");
		GameRegistry.registerBlock(solidLava, "solidlava");
		GameRegistry.registerBlock(ethaxium, ItemBlockColorName.class, "ethaxium");
		GameRegistry.registerBlock(ethaxiumbrick, ItemMetadataBlock.class, "ethaxiumbrick");
		GameRegistry.registerBlock(ethaxiumpillar, ItemBlockColorName.class, "ethaxiumpillar");
		GameRegistry.registerBlock(ethaxiumstairs, ItemBlockColorName.class, "ethaxiumbrickstairs");
		GameRegistry.registerBlock(ethaxiumslab1, ItemEthaxiumSlab.class, "ethaxiumbrickslab1");
		GameRegistry.registerBlock(ethaxiumslab2, ItemEthaxiumSlab.class, "ethaxiumbrickslab2");
		GameRegistry.registerBlock(ethaxiumfence, ItemBlockColorName.class, "ethaxiumfence");
		GameRegistry.registerBlock(ethaxiumblock, ItemBlockColorName.class, "ethaxiumblock");
		GameRegistry.registerBlock(omotholstone, "omotholstone");
		GameRegistry.registerBlock(omotholportal, "omotholportal");
		GameRegistry.registerBlock(omotholfire, "omotholfire");
		GameRegistry.registerBlock(engraver, "engraver");
		GameRegistry.registerBlock(house, "engraver_on");
		GameRegistry.registerBlock(darkethaxiumbrick, ItemMetadataBlock.class, "darkethaxiumbrick");
		GameRegistry.registerBlock(darkethaxiumpillar, ItemBlockColorName.class, "darkethaxiumpillar");
		GameRegistry.registerBlock(darkethaxiumstairs, ItemBlockColorName.class, "darkethaxiumbrickstairs");
		GameRegistry.registerBlock(darkethaxiumslab1, ItemDarkEthaxiumSlab.class, "darkethaxiumbrickslab1");
		GameRegistry.registerBlock(darkethaxiumslab2, ItemDarkEthaxiumSlab.class, "darkethaxiumbrickslab2");
		GameRegistry.registerBlock(darkethaxiumfence, ItemBlockColorName.class, "darkethaxiumbrickfence");
		GameRegistry.registerBlock(ritualaltar, ItemRitualBlock.class, "ritualaltar");
		GameRegistry.registerBlock(ritualpedestal, ItemRitualBlock.class, "ritualpedestal");
		GameRegistry.registerBlock(shoggothBlock, "shoggothblock");
		GameRegistry.registerBlock(cthulhuStatue, "cthulhustatue");
		GameRegistry.registerBlock(hasturStatue, "hasturstatue");
		GameRegistry.registerBlock(jzaharStatue, "jzahatstatue");
		GameRegistry.registerBlock(azathothStatue, "azathothstatue");
		GameRegistry.registerBlock(nyarlathotepStatue, "nyarlathotepstatue");
		GameRegistry.registerBlock(yogsothothStatue, "yogsothothstatue");
		GameRegistry.registerBlock(shubniggurathStatue, "shubniggurathstatue");
		GameRegistry.registerBlock(monolithStone, "monolithstone");
		GameRegistry.registerBlock(shoggothBiomass, "shoggothbiomass");
		GameRegistry.registerBlock(energyPedestal, "energypedestal");
		GameRegistry.registerBlock(monolithPillar, "monolithpillar");
		GameRegistry.registerBlock(sacrificialAltar, "sacrificialaltar");
		GameRegistry.registerBlock(tieredEnergyPedestal, ItemMetadataBlock.class, "tieredenergypedestal");
		GameRegistry.registerBlock(tieredSacrificialAltar, ItemMetadataBlock.class, "tieredsacrificialaltar");
		GameRegistry.registerBlock(jzaharspawner, "jzaharspawner");
		GameRegistry.registerBlock(gatekeeperminionspawner, "gatekeeperminionspawner");
		GameRegistry.registerBlock(mimicFire, "fire");

		// Item Register
		GameRegistry.registerItem(devsword, "devsword");

		AbyssalCraftAPI.setRepairItems();
		LIQUID_CORALIUM.setBlock(Cwater).setUnlocalizedName(Cwater.getUnlocalizedName());
		LIQUID_ANTIMATTER.setBlock(anticwater).setUnlocalizedName(anticwater.getUnlocalizedName());
		if (CFluid.getBlock() == null)
			CFluid.setBlock(Cwater);
		if (antifluid.getBlock() == null)
			antifluid.setBlock(anticwater);
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack(CFluid.getName(), FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(Cbucket), new ItemStack(Items.bucket));
		BucketHandler.INSTANCE.buckets.put(CFluid.getBlock(), Cbucket);
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack(antifluid.getName(), FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(antibucket), new ItemStack(Items.bucket));
		BucketHandler.INSTANCE.buckets.put(antifluid.getBlock(), antibucket);
		MinecraftForge.EVENT_BUS.register(BucketHandler.INSTANCE);

		// Biome
		if (dark1 == true) {
			registerBiomeWithTypes(ACBiomes.darklands, darkWeight1, BiomeType.WARM, Type.WASTELAND, Type.SPOOKY);
			BiomeManager.addVillageBiome(ACBiomes.darklands, true);
		}
		if (dark2 == true) {
			registerBiomeWithTypes(ACBiomes.darklands_forest, darkWeight2, BiomeType.WARM, Type.FOREST, Type.SPOOKY);
			BiomeManager.addVillageBiome(ACBiomes.darklands_forest, true);
		}
		if (dark3 == true) {
			registerBiomeWithTypes(ACBiomes.darklands_plains, darkWeight3, BiomeType.WARM, Type.PLAINS, Type.SPOOKY);
			BiomeManager.addVillageBiome(ACBiomes.darklands_plains, true);
		}
		if (dark4 == true) {
			registerBiomeWithTypes(ACBiomes.darklands_hills, darkWeight4, BiomeType.WARM, Type.HILLS, Type.SPOOKY);
			BiomeManager.addVillageBiome(ACBiomes.darklands_hills, true);
		}
		if (dark5 == true) {
			registerBiomeWithTypes(ACBiomes.darklands_mountains, darkWeight5, BiomeType.WARM, Type.MOUNTAIN, Type.SPOOKY);
			BiomeManager.addVillageBiome(ACBiomes.darklands_mountains, true);
			BiomeManager.addStrongholdBiome(ACBiomes.darklands_mountains);
		}
		if (coralium1 == true)
			registerBiomeWithTypes(ACBiomes.coralium_infested_swamp, coraliumWeight, BiomeType.WARM, Type.SWAMP);
		if (darkspawn1 == true)
			BiomeManager.addSpawnBiome(ACBiomes.darklands);
		if (darkspawn2 == true)
			BiomeManager.addSpawnBiome(ACBiomes.darklands_forest);
		if (darkspawn3 == true)
			BiomeManager.addSpawnBiome(ACBiomes.darklands_plains);
		if (darkspawn4 == true)
			BiomeManager.addSpawnBiome(ACBiomes.darklands_hills);
		if (darkspawn5 == true)
			BiomeManager.addSpawnBiome(ACBiomes.darklands_mountains);
		if (coraliumspawn1 == true)
			BiomeManager.addSpawnBiome(ACBiomes.coralium_infested_swamp);

		BiomeDictionary.registerBiomeType(ACBiomes.abyssal_wastelands, Type.DEAD);
		BiomeDictionary.registerBiomeType(ACBiomes.dreadlands, Type.DEAD);
		BiomeDictionary.registerBiomeType(ACBiomes.purified_dreadlands, Type.DEAD);
		BiomeDictionary.registerBiomeType(ACBiomes.dreadlands_mountains, Type.DEAD);
		BiomeDictionary.registerBiomeType(ACBiomes.dreadlands_forest, Type.DEAD);
		BiomeDictionary.registerBiomeType(ACBiomes.omothol, Type.DEAD);
		BiomeDictionary.registerBiomeType(ACBiomes.dark_realm, Type.DEAD);

		// Dimension
		registerDimension(configDimId1, WorldProviderAbyss.class, keepLoaded1);
		registerDimension(configDimId2, WorldProviderDreadlands.class, keepLoaded2);
		registerDimension(configDimId3, WorldProviderOmothol.class, keepLoaded3);
		registerDimension(configDimId4, WorldProviderDarkRealm.class, keepLoaded4);

		// Mobs
		registerEntityWithEgg(EntityDepthsGhoul.class, "depthsghoul", 25, 80, 3, true, 0x36A880, 0x012626);
		EntityRegistry.addSpawn(EntityDepthsGhoul.class, 10, 1, 3, EnumCreatureType.monster, BiomeDictionary.getBiomesForType(Type.WATER));
		EntityRegistry.addSpawn(EntityDepthsGhoul.class, 10, 1, 3, EnumCreatureType.monster, BiomeDictionary.getBiomesForType(Type.BEACH));
		EntityRegistry.addSpawn(EntityDepthsGhoul.class, 10, 1, 3, EnumCreatureType.monster, BiomeDictionary.getBiomesForType(Type.SWAMP));
		EntityRegistry.removeSpawn(EntityDepthsGhoul.class, EnumCreatureType.monster, new BiomeGenBase[] { BiomeGenBase.mushroomIslandShore });

		registerEntityWithEgg(EntityEvilpig.class, "evilpig", 26, 80, 3, true, 15771042, 14377823);
		if (evilAnimalSpawnRate > 0)
			EntityRegistry.addSpawn(EntityEvilpig.class, evilAnimalSpawnRate, 1, 3, evilAnimalCreatureType ? EnumCreatureType.monster : EnumCreatureType.creature, new BiomeGenBase[] { BiomeGenBase.taiga, BiomeGenBase.plains, BiomeGenBase.forest, BiomeGenBase.savanna, BiomeGenBase.beach, BiomeGenBase.extremeHills, BiomeGenBase.jungle, BiomeGenBase.savannaPlateau, BiomeGenBase.swampland, BiomeGenBase.icePlains, BiomeGenBase.birchForest, BiomeGenBase.birchForestHills, BiomeGenBase.roofedForest });

		registerEntityWithEgg(EntityAbyssalZombie.class, "abyssalzombie", 27, 80, 3, true, 0x36A880, 0x052824);
		EntityRegistry.addSpawn(EntityAbyssalZombie.class, 10, 1, 3, EnumCreatureType.monster, BiomeDictionary.getBiomesForType(Type.WATER));
		EntityRegistry.addSpawn(EntityAbyssalZombie.class, 10, 1, 3, EnumCreatureType.monster, BiomeDictionary.getBiomesForType(Type.BEACH));
		EntityRegistry.addSpawn(EntityAbyssalZombie.class, 10, 1, 3, EnumCreatureType.monster, BiomeDictionary.getBiomesForType(Type.SWAMP));
		if (endAbyssalZombie)
			EntityRegistry.addSpawn(EntityAbyssalZombie.class, 10, 1, 3, EnumCreatureType.monster, BiomeDictionary.getBiomesForType(Type.END));
		EntityRegistry.removeSpawn(EntityAbyssalZombie.class, EnumCreatureType.monster, new BiomeGenBase[] { BiomeGenBase.mushroomIslandShore });

		EntityRegistry.registerModEntity(EntityODBPrimed.class, "Primed ODB", 28, this, 80, 3, true);

		registerEntityWithEgg(EntityJzahar.class, "Jzahar", 29, 80, 3, true, 0x133133, 0x342122);

		registerEntityWithEgg(EntityAbygolem.class, "abygolem", 30, 80, 3, true, 0x8A00E6, 0x6100A1);

		registerEntityWithEgg(EntityDreadgolem.class, "dreadgolem", 31, 80, 3, true, 0x1E60000, 0xCC0000);

		registerEntityWithEgg(EntityDreadguard.class, "dreadguard", 32, 80, 3, true, 0xE60000, 0xCC0000);

		EntityRegistry.registerModEntity(EntityPSDLTracker.class, "PowerstoneTracker", 33, this, 64, 10, true);

		/// registerEntityWithEgg(EntityDragonMinion.class, "dragonminion", 34, 80, 3,
		/// true, 0x433434, 0x344344);

		registerEntityWithEgg(EntityDragonBoss.class, "dragonboss", 35, 80, 3, true, 0x476767, 0x768833);

		EntityRegistry.registerModEntity(EntityODBcPrimed.class, "Primed ODB Core", 36, this, 80, 3, true);

		registerEntityWithEgg(EntityShadowCreature.class, "shadowcreature", 37, 80, 3, true, 0, 0xFFFFFF);

		registerEntityWithEgg(EntityShadowMonster.class, "shadowmonster", 38, 80, 3, true, 0, 0xFFFFFF);

		registerEntityWithEgg(EntityDreadling.class, "dreadling", 39, 80, 3, true, 0xE60000, 0xCC0000);

		registerEntityWithEgg(EntityDreadSpawn.class, "dreadspawn", 40, 80, 3, true, 0xE60000, 0xCC0000);

		registerEntityWithEgg(EntityDemonPig.class, "demonpig", 41, 80, 3, true, 15771042, 14377823);

		registerEntityWithEgg(EntitySkeletonGoliath.class, "gskeleton", 42, 80, 3, true, 0xD6D6C9, 0xC6C7AD);

		registerEntityWithEgg(EntityChagarothSpawn.class, "chagarothspawn", 43, 80, 3, true, 0xE60000, 0xCC0000);

		registerEntityWithEgg(EntityChagarothFist.class, "chagarothfist", 44, 80, 3, true, 0xE60000, 0xCC0000);

		registerEntityWithEgg(EntityChagaroth.class, "chagaroth", 45, 80, 3, true, 0xE60000, 0xCC0000);

		registerEntityWithEgg(EntityShadowBeast.class, "shadowbeast", 46, 80, 3, true, 0, 0xFFFFFF);

		registerEntityWithEgg(EntitySacthoth.class, "shadowboss", 47, 80, 3, true, 0, 0xFFFFFF);

		registerEntityWithEgg(EntityAntiAbyssalZombie.class, "antiabyssalzombie", 48, 80, 3, true, 0xFFFFFF, 0xFFFFFF);

		registerEntityWithEgg(EntityAntiBat.class, "antibat", 49, 80, 3, true, 0xFFFFFF, 0xFFFFFF);

		registerEntityWithEgg(EntityAntiChicken.class, "antichicken", 50, 80, 3, true, 0xFFFFFF, 0xFFFFFF);

		registerEntityWithEgg(EntityAntiCow.class, "anticow", 51, 80, 3, true, 0xFFFFFF, 0xFFFFFF);

		registerEntityWithEgg(EntityAntiCreeper.class, "anticreeper", 52, 80, 3, true, 0xFFFFFF, 0xFFFFFF);

		registerEntityWithEgg(EntityAntiGhoul.class, "antighoul", 53, 80, 3, true, 0xFFFFFF, 0xFFFFFF);

		registerEntityWithEgg(EntityAntiPig.class, "antipig", 54, 80, 3, true, 0xFFFFFF, 0xFFFFFF);

		registerEntityWithEgg(EntityDaoloth.class, "antiplayer", 55, 80, 3, true, 0xFFFFFF, 0xFFFFFF);

		registerEntityWithEgg(EntityFallenHero.class, "antiskeleton", 56, 80, 3, true, 0xFFFFFF, 0xFFFFFF);

		registerEntityWithEgg(EntityAntiSpider.class, "antispider", 57, 80, 3, true, 0xFFFFFF, 0xFFFFFF);

		registerEntityWithEgg(EntityAntiZombie.class, "antizombie", 58, 80, 3, true, 0xFFFFFF, 0xFFFFFF);

		registerEntityWithEgg(EntityRemnant.class, "remnant", 59, 80, 3, true, 0x133133, 0x342122);

		registerEntityWithEgg(EntityOmotholGhoul.class, "omotholghoul", 60, 80, 3, true, 0x133133, 0x342122);

		EntityRegistry.registerModEntity(EntityCoraliumArrow.class, "CoraliumArrow", 61, this, 64, 10, true);

		EntityRegistry.registerModEntity(EntityAntimatterArrow.class, "AntimatterArrow", 61, this, 64, 10, true);

		registerEntityWithEgg(EntityGatekeeperMinion.class, "jzaharminion", 62, 80, 3, true, 0x133133, 0x342122);

		registerEntityWithEgg(EntityGreaterDreadSpawn.class, "greaterdreadspawn", 63, 80, 3, true, 0xE60000, 0xCC0000);

		registerEntityWithEgg(EntityLesserDreadbeast.class, "lesserdreadbeast", 64, 80, 3, true, 0xE60000, 0xCC0000);

		EntityRegistry.registerModEntity(EntityDreadSlug.class, "DreadSlug", 65, this, 64, 10, true);

		registerEntityWithEgg(EntityLesserShoggoth.class, "lessershoggoth", 66, 80, 3, true, 0x133133, 0x342122);
		EntityRegistry.addSpawn(EntityLesserShoggoth.class, 3, 1, 1, EnumCreatureType.monster, new BiomeGenBase[] { ACBiomes.abyssal_wastelands, ACBiomes.dreadlands, ACBiomes.purified_dreadlands, ACBiomes.dreadlands_mountains, ACBiomes.dreadlands_forest, ACBiomes.omothol, ACBiomes.dark_realm });
		EntityRegistry.removeSpawn(EntityLesserShoggoth.class, EnumCreatureType.monster, new BiomeGenBase[] { BiomeGenBase.mushroomIslandShore });

		registerEntityWithEgg(EntityEvilCow.class, "evilcow", 67, 80, 3, true, 4470310, 10592673);
		if (evilAnimalSpawnRate > 0)
			EntityRegistry.addSpawn(EntityEvilCow.class, evilAnimalSpawnRate, 1, 3, evilAnimalCreatureType ? EnumCreatureType.monster : EnumCreatureType.creature, new BiomeGenBase[] { BiomeGenBase.taiga, BiomeGenBase.plains, BiomeGenBase.forest, BiomeGenBase.savanna, BiomeGenBase.beach, BiomeGenBase.extremeHills, BiomeGenBase.jungle, BiomeGenBase.savannaPlateau, BiomeGenBase.swampland, BiomeGenBase.icePlains, BiomeGenBase.birchForest, BiomeGenBase.birchForestHills, BiomeGenBase.roofedForest });

		registerEntityWithEgg(EntityEvilChicken.class, "evilchicken", 68, 80, 3, true, 10592673, 16711680);
		if (evilAnimalSpawnRate > 0)
			EntityRegistry.addSpawn(EntityEvilChicken.class, evilAnimalSpawnRate, 1, 3, evilAnimalCreatureType ? EnumCreatureType.monster : EnumCreatureType.creature, new BiomeGenBase[] { BiomeGenBase.taiga, BiomeGenBase.plains, BiomeGenBase.forest, BiomeGenBase.savanna, BiomeGenBase.beach, BiomeGenBase.extremeHills, BiomeGenBase.jungle, BiomeGenBase.savannaPlateau, BiomeGenBase.swampland, BiomeGenBase.icePlains, BiomeGenBase.birchForest, BiomeGenBase.birchForestHills, BiomeGenBase.roofedForest });

		registerEntityWithEgg(EntityDemonCow.class, "demoncow", 69, 80, 3, true, 4470310, 10592673);
		registerEntityWithEgg(EntityDemonChicken.class, "demonchicken", 70, 80, 3, true, 10592673, 16711680);

		EntityRegistry.registerModEntity(EntityGatekeeperEssence.class, "GatekeeperEssence", 71, instance, 64, 10, true);

		registerEntityWithEgg(EntityWormHole.class, "wormhole", 72, 80, 3, true, 10592673, 16711680);

		proxy.addArmor("Abyssalnite");
		proxy.addArmor("Dread");
		proxy.addArmor("Coralium");
		proxy.addArmor("CoraliumP");
		proxy.addArmor("Depths");
		proxy.addArmor("Dreadium");
		proxy.addArmor("DreadiumS");
		proxy.addArmor("Ethaxium");

		RitualUtil.addBlocks();
		addOreDictionaryStuff();
		addChestGenHooks();
		addDungeonHooks();
		sendIMC();
		PacketDispatcher.registerPackets();
		IntegrationHandler.preInit(event.getAsmData());
	}

	@EventHandler
	public void Init(FMLInitializationEvent event) {
		ACLogger.info("Initializing AbyssalCraft.");
		// Achievements
		necro = new Achievement("achievement.necro", "necro", 0, 0, necronomicon, AchievementList.openInventory).registerStat();
		// Materials Achievements
		mineCorgem = new Achievement("achievement.mineCorgem", "mineCorgem", 4, 0, Coralium, necro).registerStat();
		shadowGems = new Achievement("achievement.shadowGems", "shadowGems", 6, 0, shadowgem, mineCorgem).registerStat();
		// coraliumpearl
		mineCor = new Achievement("achievement.mineCor", "mineCor", 8, 0, AbyLCorOre, shadowGems).registerStat();
		mineAbyOres = new Achievement("achievement.mineAbyOres", "mineAbyOres", 10, 0, AbyDiaOre, mineCor).registerStat();
		mineDread = new Achievement("achievement.mineDread", "mineDread", 12, 0, dreadore, mineAbyOres).registerStat();
		dreadium = new Achievement("achievement.dreadium", "dreadium", 14, 0, dreadiumingot, mineDread).registerStat();
		eth = new Achievement("achievement.ethaxium", "ethaxium", 16, 0, ethaxiumIngot, dreadium).setSpecial().registerStat();
		// Depths Ghoul Achievements
		killghoul = new Achievement("achievement.killghoul", "killghoul", -2, 0, Corbone, necro).registerStat();
		ghoulhead = new Achievement("achievement.ghoulhead", "ghoulhead", -4, 0, DGhead, killghoul).registerStat();
		petehead = new Achievement("achievement.petehead", "petehead", -4, -2, Phead, ghoulhead).registerStat();
		wilsonhead = new Achievement("achievement.wilsonhead", "wilsonhead", -4, -4, Whead, petehead).registerStat();
		orangehead = new Achievement("achievement.orangehead", "orangehead", -4, -6, Ohead, wilsonhead).registerStat();
		// Necronomicon Achievements
		necrou1 = new Achievement("achievement.necrou1", "necrou1", 2, 1, necronomicon_cor, necro).registerStat();
		necrou2 = new Achievement("achievement.necrou2", "necrou2", 4, 1, necronomicon_dre, necrou1).registerStat();
		necrou3 = new Achievement("achievement.necrou3", "necrou3", 6, 1, necronomicon_omt, necrou2).registerStat();
		abyssaln = new Achievement("achievement.abyssaln", "abyssaln", 8, 1, abyssalnomicon, necrou3).setSpecial().registerStat();
		// Ritual Achievements
		ritual = new Achievement("achievement.ritual", "ritual", -2, 1, ritualaltar, necro).setSpecial().registerStat();
		ritualSummon = new Achievement("achievement.ritualSummon", "ritualSummon", -4, 1, DGhead, ritual).registerStat();
		ritualCreate = new Achievement("achievement.ritualCreate", "ritualCreate", -4, 2, lifeCrystal, ritual).registerStat();
		ritualBreed = new Achievement("achievement.ritualBreed", "ritualBreed", -4, 3, Items.egg, ritual).registerStat();
		ritualPotion = new Achievement("achievement.ritualPotion", "ritualPotion", -4, 4, Items.potionitem, ritual).registerStat();
		ritualPotionAoE = new Achievement("achievement.ritualPotionAoE", "ritualPotionAoE", -4, 5, new ItemStack(Items.potionitem, 1, 16384), ritual).registerStat();
		ritualInfusion = new Achievement("achievement.ritualInfusion", "ritualInfusion", -4, 6, Depthshelmet, ritual).registerStat();
		shoggothInfestation = new Achievement("achievement.shoggothInfestation", "shoggothInfestation", -6, 3, Items.skull, ritualBreed).registerStat();
		// Progression Achievements
		enterabyss = new Achievement("achievement.enterabyss", "enterabyss", 0, 2, abystone, necro).setSpecial().registerStat();
		killdragon = new Achievement("achievement.killdragon", "killdragon", 2, 2, Corflesh, enterabyss).registerStat();
		summonAsorah = new Achievement("achievement.summonAsorah", "summonAsorah", 0, 4, Altar, enterabyss).registerStat();
		killAsorah = new Achievement("achievement.killAsorah", "killAsorah", 2, 4, EoA, summonAsorah).setSpecial().registerStat();
		enterdreadlands = new Achievement("achievement.enterdreadlands", "enterdreadlands", 2, 6, dreadstone, killAsorah).setSpecial().registerStat();
		killdreadguard = new Achievement("achievement.killdreadguard", "killdreadguard", 4, 6, Dreadshard, enterdreadlands).registerStat();
		summonChagaroth = new Achievement("achievement.summonChagaroth", "summonChagaroth", 2, 8, dreadaltarbottom, enterdreadlands).registerStat();
		killChagaroth = new Achievement("achievement.killChagaroth", "killChagaroth", 4, 8, dreadKey, summonChagaroth).setSpecial().registerStat();
		enterOmothol = new Achievement("achievement.enterOmothol", "enterOmothol", 4, 10, omotholstone, killChagaroth).setSpecial().registerStat();
		enterDarkRealm = new Achievement("achievement.darkRealm", "darkRealm", 2, 10, Darkstone, enterOmothol).registerStat();
		killOmotholelite = new Achievement("achievement.killOmotholelite", "killOmotholelite", 6, 10, eldritchScale, enterOmothol).registerStat();
		locateJzahar = new Achievement("achievement.locateJzahar", "locateJzahar", 4, 12, jzaharCharm, enterOmothol).registerStat();
		killJzahar = new Achievement("achievement.killJzahar", "killJzahar", 6, 12, Staff, locateJzahar).setSpecial().registerStat();
		// Gateway Key Achievements
		GK1 = new Achievement("achievement.GK1", "GK1", 0, -2, portalPlacer, necro).registerStat();
		findPSDL = new Achievement("achievement.findPSDL", "findPSDL", -2, -2, PSDL, GK1).registerStat();
		GK2 = new Achievement("achievement.GK2", "GK2", 0, -4, portalPlacerDL, GK1).registerStat();
		GK3 = new Achievement("achievement.GK3", "GK3", 0, -6, portalPlacerJzh, GK2).registerStat();
		// Machinery Achievements
		makeEngraver = new Achievement("achievement.makeEngraver", "makeEngraver", 2, -3, engraver, AchievementList.openInventory).registerStat();

		AchievementPage.registerAchievementPage(new AchievementPage("AbyssalCraft", new Achievement[] { necro, killghoul, enterabyss, killdragon, summonAsorah, killAsorah, enterdreadlands, killdreadguard, ghoulhead, petehead, wilsonhead, orangehead, mineCorgem, mineCor, findPSDL, GK1, GK2, GK3, summonChagaroth, killChagaroth, enterOmothol, enterDarkRealm, necrou1, necrou2, necrou3, abyssaln, ritual, ritualSummon, ritualCreate, killOmotholelite, locateJzahar, killJzahar, shadowGems, mineAbyOres, mineDread, dreadium, eth, makeEngraver, ritualBreed, ritualPotion, ritualPotionAoE, ritualInfusion, shoggothInfestation }));

		proxy.init();
		FMLCommonHandler.instance().bus().register(instance);
		MinecraftForge.EVENT_BUS.register(new ReputationEventHandler());
		MapGenStructureIO.registerStructure(MapGenAbyStronghold.Start.class, "AbyStronghold");
		StructureAbyStrongholdPieces.registerStructurePieces();
		MapGenStructureIO.registerStructure(StructureDreadlandsMineStart.class, "DreadMine");
		StructureDreadlandsMinePieces.registerStructurePieces();
		MapGenStructureIO.registerStructure(MapGenOmothol.Start.class, "Omothol");
		StructureOmotholPieces.registerOmotholPieces();
		GameRegistry.registerWorldGenerator(new AbyssalCraftWorldGenerator(), 0);
		GameRegistry.registerFuelHandler(new FurnaceFuelHandler());
		AbyssalCrafting.addRecipes();
		AbyssalCraftAPI.getInternalNDHandler().registerInternalPages();
		proxy.registerRenderThings();
		IntegrationHandler.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		ACLogger.info("Post-initializing AbyssalCraft");
		proxy.postInit();
		IntegrationHandler.postInit();
		((BlockShoggothOoze) shoggothBlock).initBlacklist();
		checkBiomeIds(false);
		ACLogger.info("AbyssalCraft loaded.");
	}

	@EventHandler
	public void serverStart(FMLServerAboutToStartEvent event) {
		String clname = AbyssalCraftAPI.getInternalNDHandler().getClass().getName();
		String expect = "com.shinoow.abyssalcraft.common.handlers.InternalNecroDataHandler";
		if (!clname.equals(expect)) {
			new IllegalAccessError("The AbyssalCraft API internal NecroData handler has been overriden. " + "Since things are not going to work correctly, the game will now shut down." + " (Expected classname: " + expect + ", Actual classname: " + clname + ")").printStackTrace();
			FMLCommonHandler.instance().exitJava(1, true);
		}
	}

	@SuppressWarnings("unchecked")
	@EventHandler
	public void handleIMC(FMLInterModComms.IMCEvent event) {

		List<String> senders = new ArrayList<String>();
		for (final FMLInterModComms.IMCMessage imcMessage : event.getMessages())
			if (imcMessage.key.equals("shoggothFood"))
				try {
					AbyssalCraftAPI.addShoggothFood((Class<? extends EntityLivingBase>) Class.forName(imcMessage.getStringValue()));
					ACLogger.imcInfo("Received Shoggoth Food addition %s from mod %s", imcMessage.getStringValue(), imcMessage.getSender());
					if (!senders.contains(imcMessage.getSender()))
						senders.add(imcMessage.getSender());
				} catch (ClassNotFoundException e) {
					ACLogger.imcWarning("Could not find class %s", imcMessage.getStringValue());
				}
			else if (imcMessage.key.equals("registerTransmutatorFuel"))
				try {
					AbyssalCraftAPI.registerFuelHandler((IFuelHandler) Class.forName(imcMessage.getStringValue()).newInstance(), FuelType.TRANSMUTATOR);
					ACLogger.imcInfo("Recieved Transmutator fuel handler %s from mod %s", imcMessage.getStringValue(), imcMessage.getSender());
					if (!senders.contains(imcMessage.getSender()))
						senders.add(imcMessage.getSender());
				} catch (InstantiationException e) {
					ACLogger.imcWarning("Could not create a instance of class %s (not a IFuelHandler?)", imcMessage.getStringValue());
				} catch (IllegalAccessException e) {
					ACLogger.imcWarning("Unable to access class %s", imcMessage.getStringValue());
				} catch (ClassNotFoundException e) {
					ACLogger.imcWarning("Could not find class %s", imcMessage.getStringValue());
				}
			else if (imcMessage.key.equals("registerCrystallizerFuel"))
				try {
					AbyssalCraftAPI.registerFuelHandler((IFuelHandler) Class.forName(imcMessage.getStringValue()).newInstance(), FuelType.CRYSTALLIZER);
					ACLogger.imcInfo("Recieved Crystallizer fuel handler %s from mod %s", imcMessage.getStringValue(), imcMessage.getSender());
					if (!senders.contains(imcMessage.getSender()))
						senders.add(imcMessage.getSender());
				} catch (InstantiationException e) {
					ACLogger.imcWarning("Could not create a instance of class %s (not a IFuelHandler?)", imcMessage.getStringValue());
				} catch (IllegalAccessException e) {
					ACLogger.imcWarning("Unable to access class %s", imcMessage.getStringValue());
				} catch (ClassNotFoundException e) {
					ACLogger.imcWarning("Could not find class %s", imcMessage.getStringValue());
				}
			else if (imcMessage.key.equals("addCrystal")) {
				boolean failed = false;
				if (!imcMessage.isItemStackMessage())
					failed = true;
				else {
					ItemStack crystal = imcMessage.getItemStackValue();
					if (crystal == null) {
						failed = true;
						return;
					}
					AbyssalCraftAPI.addCrystal(crystal);
				}
				if (failed)
					ACLogger.imcWarning("Received invalid Crystal addition from mo %s", imcMessage.getSender());
				else
					ACLogger.imcInfo("Received Crystal addition from mo %s", imcMessage.getSender());
			} else if (imcMessage.key.equals("addCrystallization")) {
				boolean failed = false;
				if (!imcMessage.isNBTMessage())
					failed = true;
				else {
					if (!senders.contains(imcMessage.getSender()))
						senders.add(imcMessage.getSender());
					NBTTagCompound stuff = imcMessage.getNBTValue();
					if (!stuff.hasKey("input") || !stuff.hasKey("output1") || !stuff.hasKey("output2") || !stuff.hasKey("xp")) {
						failed = true;
						return;
					}
					ItemStack input = ItemStack.loadItemStackFromNBT(stuff.getCompoundTag("input"));
					ItemStack output1 = ItemStack.loadItemStackFromNBT(stuff.getCompoundTag("output1"));
					ItemStack output2 = ItemStack.loadItemStackFromNBT(stuff.getCompoundTag("output2"));
					if (input == null || output1 == null || output2 == null) {
						failed = true;
						return;
					}
					AbyssalCraftAPI.addCrystallization(input, output1, output2, stuff.getFloat("xp"));
				}
				if (failed)
					ACLogger.imcWarning("Received invalid Crystallizer recipe from mod %s!", imcMessage.getSender());
				else
					ACLogger.imcInfo("Received Crystallizer recipe from mod %s", imcMessage.getSender());
			} else if (imcMessage.key.equals("addSingleCrystallization")) {
				boolean failed = false;
				if (!imcMessage.isNBTMessage())
					failed = true;
				else {
					if (!senders.contains(imcMessage.getSender()))
						senders.add(imcMessage.getSender());
					NBTTagCompound stuff = imcMessage.getNBTValue();
					if (!stuff.hasKey("input") || !stuff.hasKey("output") || !stuff.hasKey("xp")) {
						failed = true;
						return;
					}
					ItemStack input = ItemStack.loadItemStackFromNBT(stuff.getCompoundTag("input"));
					ItemStack output = ItemStack.loadItemStackFromNBT(stuff.getCompoundTag("output"));
					if (input == null || output == null) {
						failed = true;
						return;
					}
					AbyssalCraftAPI.addSingleCrystallization(input, output, stuff.getFloat("xp"));
				}
				if (failed)
					ACLogger.imcWarning("Received invalid Single Crystallizer recipe from mod %s!", imcMessage.getSender());
				else
					ACLogger.imcInfo("Received Single Crystallizer recipe from mod %s", imcMessage.getSender());
			} else if (imcMessage.key.equals("addOredictCrystallization")) {
				boolean failed = false;
				if (!imcMessage.isNBTMessage())
					failed = true;
				else {
					if (!senders.contains(imcMessage.getSender()))
						senders.add(imcMessage.getSender());
					NBTTagCompound stuff = imcMessage.getNBTValue();
					if (!stuff.hasKey("input") || !stuff.hasKey("output1") || !stuff.hasKey("output2") || !stuff.hasKey("xp")) {
						failed = true;
						return;
					}
					String input = stuff.getString("input");
					String output1 = stuff.getString("output1");
					String output2 = stuff.getString("output2");
					if (input == null || output1 == null || output2 == null) {
						failed = true;
						return;
					}
					if (stuff.hasKey("quantity1") && stuff.hasKey("quantity2"))
						AbyssalCraftAPI.addCrystallization(input, output1, stuff.getInteger("quantity1"), output2, stuff.getInteger("quantity2"), stuff.getFloat("xp"));
					else
						AbyssalCraftAPI.addCrystallization(input, output1, output2, stuff.getFloat("xp"));
				}
				if (failed)
					ACLogger.imcWarning("Received invalid OreDictionary Crystallizer recipe from mod %s!", imcMessage.getSender());
				else
					ACLogger.imcInfo("Received OreDictionary Crystallizer recipe from mod %s", imcMessage.getSender());
			} else if (imcMessage.key.equals("addSingleOredictCrystallization")) {
				boolean failed = false;
				if (!imcMessage.isNBTMessage())
					failed = true;
				else {
					if (!senders.contains(imcMessage.getSender()))
						senders.add(imcMessage.getSender());
					NBTTagCompound stuff = imcMessage.getNBTValue();
					if (!stuff.hasKey("input") || !stuff.hasKey("output") || !stuff.hasKey("xp")) {
						failed = true;
						return;
					}
					String input = stuff.getString("input");
					String output = stuff.getString("output");
					if (input == null || output == null) {
						failed = true;
						return;
					}
					if (stuff.hasKey("quantity")) {
						AbyssalCraftAPI.addSingleCrystallization(input, output, stuff.getInteger("quantity"), stuff.getFloat("xp"));
					} else {
						AbyssalCraftAPI.addSingleCrystallization(input, output, stuff.getFloat("xp"));
					}
				}
				if (failed) {
					ACLogger.imcWarning("Received invalid Single OreDictionary Crystallizer recipe from mod %s!", imcMessage.getSender());
				} else {
					ACLogger.imcInfo("Received Single OreDictionary Crystallizer recipe from mod %s", imcMessage.getSender());
				}

			} else if (imcMessage.key.equals("addTransmutation")) {
				boolean failed = false;
				if (!imcMessage.isNBTMessage())
					failed = true;
				else {
					if (!senders.contains(imcMessage.getSender()))
						senders.add(imcMessage.getSender());
					NBTTagCompound stuff = imcMessage.getNBTValue();
					if (!stuff.hasKey("input") || !stuff.hasKey("output") || !stuff.hasKey("xp")) {
						failed = true;
						return;
					}
					ItemStack input = ItemStack.loadItemStackFromNBT(stuff.getCompoundTag("input"));
					ItemStack output = ItemStack.loadItemStackFromNBT(stuff.getCompoundTag("output"));
					if (input == null || output == null) {
						failed = true;
						return;
					}
					AbyssalCraftAPI.addTransmutation(input, output, stuff.getFloat("xp"));
				}
				if (failed)
					ACLogger.imcWarning("Received invalid Transmutator recipe from mod %s!", imcMessage.getSender());
				else
					ACLogger.imcInfo("Received Transmutator recipe from mod %s", imcMessage.getSender());
			} else if (imcMessage.key.equals("addOredictTransmutation")) {
				boolean failed = false;
				if (!imcMessage.isNBTMessage())
					failed = true;
				else {
					if (!senders.contains(imcMessage.getSender()))
						senders.add(imcMessage.getSender());
					NBTTagCompound stuff = imcMessage.getNBTValue();
					if (!stuff.hasKey("input") || !stuff.hasKey("output") || !stuff.hasKey("xp")) {
						failed = true;
						return;
					}
					String input = stuff.getString("input");
					String output = stuff.getString("output");
					if (input == null || output == null) {
						failed = true;
						return;
					}
					if (stuff.hasKey("quantity"))
						AbyssalCraftAPI.addTransmutation(input, output, stuff.getInteger("quantity"), stuff.getFloat("xp"));
					else
						AbyssalCraftAPI.addTransmutation(input, output, stuff.getFloat("xp"));
				}
				if (failed)
					ACLogger.imcWarning("Received invalid OreDictionary Transmutator recipe from mod %s!", imcMessage.getSender());
				else
					ACLogger.imcInfo("Received OreDictionary Transmutator recipe from mod %s", imcMessage.getSender());
			} else if (imcMessage.key.equals("addMaterialization")) { // TODO: rewrite this
				boolean failed = false;
				ItemStack[] items;
				if (!imcMessage.isNBTMessage())
					failed = true;
				else {
					if (!senders.contains(imcMessage.getSender()))
						senders.add(imcMessage.getSender());
					NBTTagCompound stuff = imcMessage.getNBTValue();
					if (!stuff.hasKey("input1") || !stuff.hasKey("output")) {
						failed = true;
						return;
					}
					ItemStack input1 = ItemStack.loadItemStackFromNBT(stuff.getCompoundTag("input1"));
					ItemStack input2 = ItemStack.loadItemStackFromNBT(stuff.getCompoundTag("input2"));
					ItemStack input3 = ItemStack.loadItemStackFromNBT(stuff.getCompoundTag("input3"));
					ItemStack input4 = ItemStack.loadItemStackFromNBT(stuff.getCompoundTag("input4"));
					ItemStack input5 = ItemStack.loadItemStackFromNBT(stuff.getCompoundTag("input5"));
					ItemStack output = ItemStack.loadItemStackFromNBT(stuff.getCompoundTag("output"));
					if (input1 == null || output == null) {
						failed = true;
						return;
					}
					if (input5 != null) {
						items = new ItemStack[5];
						items[0] = input1;
						items[1] = input2;
						items[2] = input3;
						items[3] = input4;
						items[4] = input5;
					} else if (input4 != null) {
						items = new ItemStack[4];
						items[0] = input1;
						items[1] = input2;
						items[2] = input3;
						items[3] = input4;
					} else if (input3 != null) {
						items = new ItemStack[3];
						items[0] = input1;
						items[1] = input2;
						items[2] = input3;
					} else if (input2 != null) {
						items = new ItemStack[2];
						items[0] = input1;
						items[1] = input2;
					} else {
						items = new ItemStack[1];
						items[0] = input1;
					}
					AbyssalCraftAPI.addMaterialization(items, output);
				}
				if (failed)
					ACLogger.imcWarning("Received invalid Materializer recipe from mod %s!", imcMessage.getSender());
				else
					ACLogger.imcInfo("Received Materializer recipe from mod %s", imcMessage.getSender());
			} else if (imcMessage.key.equals("shoggothBlacklist")) {
				boolean failed = false;
				if (!imcMessage.isItemStackMessage())
					failed = true;
				else if (Block.getBlockFromItem(imcMessage.getItemStackValue().getItem()) != null) {
					if (!senders.contains(imcMessage.getSender()))
						senders.add(imcMessage.getSender());
					AbyssalCraftAPI.addShoggothBlacklist(Block.getBlockFromItem(imcMessage.getItemStackValue().getItem()));
				} else {
					failed = true;
				}
				if (failed)
					ACLogger.imcWarning("Received invalid Shoggoth Block Blacklist from mod %s!", imcMessage.getSender());
				else
					ACLogger.imcInfo("Received Shoggoth Block Blacklist from mod %s", imcMessage.getSender());
			} else
				ACLogger.imcWarning("Received an IMC Message with unknown key (%s) from mod %s!", imcMessage.key, imcMessage.getSender());
		if (!senders.isEmpty())
			ACLogger.imcInfo("Recieved messages from the following mods: %s", senders);
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
		if (eventArgs.modID.equals("abyssalcraft"))
			syncConfig();
	}

	private static void syncConfig() {
		configDimId1 = cfg.get("dimensions", "The Abyssal Wasteland", 50, "The first dimension, full of undead monsters.").getInt();
		configDimId2 = cfg.get("dimensions", "The Dreadlands", 51, "The second dimension, infested with mutated monsters.").getInt();
		configDimId3 = cfg.get("dimensions", "Omothol", 52, "The third dimension, also known as \u00A7oThe Realm of J'zahar\u00A7r.").getInt();
		configDimId4 = cfg.get("dimensions", "The Dark Realm", 53, "Hidden fourth dimension, reached by falling down from Omothol").getInt();

		keepLoaded1 = cfg.get("dimensions", "Prevent unloading: The Abyssal Wasteland", false, "Set true to prevent The Abyssal Wasteland from automatically unloading (might affect performance)").getBoolean();
		keepLoaded2 = cfg.get("dimensions", "Prevent unloading: The Dreadlands", false, "Set true to prevent The Dreadlands from automatically unloading (might affect performance)").getBoolean();
		keepLoaded3 = cfg.get("dimensions", "Prevent unloading: Omothol", false, "Set true to prevent Omothol from automatically unloading (might affect performance)").getBoolean();
		keepLoaded4 = cfg.get("dimensions", "Prevent unloading: The Dark Realm", false, "Set true to prevent The Dark Realm from automatically unloading (might affect performance)").getBoolean();

		configBiomeId1 = cfg.get("biomes", "Darklands", 100, "Dark biome that contains Abyssalnite", 0, 255).getInt();
		configBiomeId2 = cfg.get("biomes", "Abyssal Wasteland", 101, "Abyssal Wasteland biome, contains large quantities of Coralium", 0, 255).getInt();
		configBiomeId3 = cfg.get("biomes", "Dreadlands", 102, "Main Dreadlands biome, desolate.", 0, 255).getInt();
		configBiomeId4 = cfg.get("biomes", "Purified Dreadlands", 103, "Pre-Dreadlands biome, with larger quantities of pure Abyssalnite.", 0, 255).getInt();
		configBiomeId5 = cfg.get("biomes", "Dreadlands Forest", 104, "Forest taken over by the Dread Plague.", 0, 255).getInt();
		configBiomeId6 = cfg.get("biomes", "Dreadlands Mountains", 105, "Mountain equivalent to the Dreadlands biome.", 0, 255).getInt();
		configBiomeId7 = cfg.get("biomes", "Darklands Forest", 106, "Forest equivalent to the Darklands biome.", 0, 255).getInt();
		configBiomeId8 = cfg.get("biomes", "Darklands Plains", 107, "Plains equivalent to the Darklands biome.", 0, 255).getInt();
		configBiomeId9 = cfg.get("biomes", "Darklands Highland", 108, "Plateau version of the Darklands Plains biome.", 0, 255).getInt();
		configBiomeId10 = cfg.get("biomes", "Darklands Mountains", 109, "Mountain equivalent to the Darklands biome.", 0, 255).getInt();
		configBiomeId11 = cfg.get("biomes", "Coralium Infested Swamp", 110, "A swamp biome infested with Coralium.", 0, 255).getInt();
		configBiomeId12 = cfg.get("biomes", "Omothol", 112, "Main biome in Omothol, the realm of J'zahar.", 0, 255).getInt();
		configBiomeId13 = cfg.get("biomes", "Dark Realm", 113, "Dark Realm biome, made out of Darkstone.", 0, 255).getInt();

		dark1 = cfg.get("biome_generation", "Darklands", true, "Set true for the Darklands biome to generate.").getBoolean();
		dark2 = cfg.get("biome_generation", "Darklands Forest", true, "Set true for the Darklands Forest biome to generate.").getBoolean();
		dark3 = cfg.get("biome_generation", "Darklands Plains", true, "Set true for the Darklands Plains biome to generate.").getBoolean();
		dark4 = cfg.get("biome_generation", "Darklands Highland", true, "Set true for the Darklands Highland biome to generate.").getBoolean();
		dark5 = cfg.get("biome_generation", "Darklands Mountain", true, "Set true for the Darklands Mountain biome to generate.").getBoolean();
		coralium1 = cfg.get("biome_generation", "Coralium Infested Swamp", true, "Set true for the Coralium Infested Swamp to generate.").getBoolean();

		darkspawn1 = cfg.get("biome_spawning", "Darklands", true, "If true, you can spawn in the Darklands biome.").getBoolean();
		darkspawn2 = cfg.get("biome_spawning", "Darklands Forest", true, "If true, you can spawn in the Darklands Forest biome.").getBoolean();
		darkspawn3 = cfg.get("biome_spawning", "Darklands Plains", true, "If true, you can spawn in the Darklands Plains biome.").getBoolean();
		darkspawn4 = cfg.get("biome_spawning", "Darklands Highland", true, "If true, you can spawn in the Darklands Highland biome.").getBoolean();
		darkspawn5 = cfg.get("biome_spawning", "Darklands Mountain", true, "If true, you can spawn in the Darklands Mountain biome.").getBoolean();
		coraliumspawn1 = cfg.get("biome_spawning", "Coralium Infested Swamp", true, "If true, you can spawn in the Coralium Infested Swamp biome.").getBoolean();

		shouldSpread = cfg.get(Configuration.CATEGORY_GENERAL, "Liquid Coralium transmutation", true, "Set true for the Liquid Coralium to convert other liquids into itself in the overworld.").getBoolean();
		shouldInfect = cfg.get(Configuration.CATEGORY_GENERAL, "Coralium Plague spreading", false, "Set true to allow the Coralium Plague to spread outside The Abyssal Wasteland.").getBoolean();
		breakLogic = cfg.get(Configuration.CATEGORY_GENERAL, "Liquid Coralium Physics", false, "Set true to allow the Liquid Coralium to break the laws of physics in terms of movement").getBoolean();
		destroyOcean = cfg.get(Configuration.CATEGORY_GENERAL, "Oceanic Coralium Pollution", false, "Set true to allow the Liquid Coralium to spread across oceans. WARNING: The game can crash from this.").getBoolean();
		demonAnimalFire = cfg.get(Configuration.CATEGORY_GENERAL, "Demon Animal burning", true, "Set to false to prevent Demon Animals (Pigs, Cows, Chickens) from burning in the overworld.").getBoolean();
		evilAnimalSpawnRate = cfg.get(Configuration.CATEGORY_GENERAL, "Evil Animal spawn rate", 20, "Spawn rate for the Evil Animals (Pigs, Cows, Chickens), keep under 35 to avoid complete annihilation.").getInt();
		darkness = cfg.get(Configuration.CATEGORY_GENERAL, "Darkness", true, "Set to false to disable the random blindness within Darklands biomes").getBoolean();
		particleBlock = cfg.get(Configuration.CATEGORY_GENERAL, "Block particles", true, "Toggles whether blocks that emits particles should do so.").getBoolean();
		particleEntity = cfg.get(Configuration.CATEGORY_GENERAL, "Entity particles", true, "Toggles whether entities that emits particles should do so.").getBoolean();
		hardcoreMode = cfg.get(Configuration.CATEGORY_GENERAL, "Hardcore Mode", false, "Toggles Hardcore mode. If set to true, all mobs will become tougher.").getBoolean();
		endAbyssalZombie = cfg.get(Configuration.CATEGORY_GENERAL, "End Abyssal Zombies", true, "Toggles whether Abyssal Zombies should spawn in The End. Takes effect after restart.").getBoolean();
		evilAnimalCreatureType = cfg.get(Configuration.CATEGORY_GENERAL, "Evil Animals Are Monsters", false, "If enabled, sets the creature type of Evil Animals to \"monster\". The creature type affects how a entity spawns, eg \"creature\" " + "treats the entity as an animal, while \"monster\" treats it as a hostile mob. If you enable this, Evil Animals will spawn like any other hostile mobs, instead of mimicking vanilla animals.\n" + EnumChatFormatting.RED + "[Minecraft Restart Required]" + EnumChatFormatting.RESET).getBoolean();
		antiItemDisintegration = cfg.get(Configuration.CATEGORY_GENERAL, "Liquid Antimatter item disintegration", true, "Toggles whether or not Liquid Antimatter will disintegrate any items dropped into a pool of it.").getBoolean();

		darkWeight1 = cfg.get("biome_weight", "Darklands", 10, "Biome weight for the Darklands biome, controls the chance of it generating", 0, 100).getInt();
		darkWeight2 = cfg.get("biome_weight", "Darklands Forest", 10, "Biome weight for the Darklands Forest biome, controls the chance of it generating", 0, 100).getInt();
		darkWeight3 = cfg.get("biome_weight", "Darklands Plains", 10, "Biome weight for the Darklands Plains biome, controls the chance of it generating", 0, 100).getInt();
		darkWeight4 = cfg.get("biome_weight", "Darklands Highland", 10, "Biome weight for the Darklands Highland biome, controls the chance of it generating", 0, 100).getInt();
		darkWeight5 = cfg.get("biome_weight", "Darklands Mountain", 10, "Biome weight for the Darklands Mountain biome, controls the chance of it generating").getInt();
		coraliumWeight = cfg.get("biome_weight", "Coralium Infested Swamp", 10, "Biome weight for the Coralium Infested Swamp biome, controls the chance of it generating", 0, 100).getInt();

		useDynamicPotionIds = cfg.get("potions", "Dynamic Potion IDs", true, "Toggles whether to use the dynamic potion ID assigner, or to manually assign them (disable this if you get potion ID conflict with a mod that doesn't have config options for them)").getBoolean();

		AbyssalCraftAPI.potionId1 = cfg.get("potions", "Coralium Plague", 100, "The Coralium Plague potion effect. (Only use this if Dynamic Potion IDs is disabled)", 0, 127).getInt();
		AbyssalCraftAPI.potionId2 = cfg.get("potions", "Dread Plague", 101, "The Dread Plague potion effect. (Only use this if Dynamic Potion IDs is disabled)", 0, 127).getInt();
		AbyssalCraftAPI.potionId3 = cfg.get("potions", "Antimatter", 102, "The Antimatter potion effect. (Only use this if Dynamic Potion IDs is disabled)", 0, 127).getInt();
		AbyssalCraftAPI.potionId4 = cfg.get("potions", "Earthquake", 103, "The Earthquake potion effect. (Only use this if Dynamic Potion IDs is disabled)", 0, 127).getInt();
		AbyssalCraftAPI.potionId5 = cfg.get("potions", "Doom", 104, "The Doom potion effect. (Only use this if Dynamic Potion IDs is disabled)", 0, 127).getInt();

		shoggothOoze = cfg.get("shoggoth", "Shoggoth Ooze Spread", true, "Toggles whether or not Lesser Shoggoths should spread their ooze when walking around. (Overrides all the Ooze Spread options)").getBoolean();
		oozeLeaves = cfg.get("shoggoth", "Ooze Spread: Leaves", true, "Toggles ooze spreading on blocks with the Leaves material (Leaf blocks).").getBoolean();
		oozeGrass = cfg.get("shoggoth", "Ooze Spread: Grass", true, "Toggles ooze spreading on blocks with the Grass material (Grass blocks).").getBoolean();
		oozeGround = cfg.get("shoggoth", "Ooze Spread: Ground", true, "Toggles ooze spreading on blocks with the Ground material (Dirt, Podzol).").getBoolean();
		oozeSand = cfg.get("shoggoth", "Ooze Spread: Sand", true, "Toggles ooze spreading on blocks with the Sand material (Sand, Gravel, Soul Sand).").getBoolean();
		oozeRock = cfg.get("shoggoth", "Ooze Spread: Rock", true, "Toggles ooze spreading on blocks with the Rock material (Stone, Cobblestone, Stone Bricks, Ores).").getBoolean();
		oozeCloth = cfg.get("shoggoth", "Ooze Spread: Cloth", true, "Toggles ooze spreading on blocks with the Cloth material (Wool blocks).").getBoolean();
		oozeWood = cfg.get("shoggoth", "Ooze Spread: Wood", true, "Toggles ooze spreading on blocks with the Wood material (Logs, Planks).").getBoolean();
		oozeGourd = cfg.get("shoggoth", "Ooze Spread: Gourd", true, "Toggles ooze spreading on blocks with the Gourd material (Pumpkin, Melon).").getBoolean();
		oozeIron = cfg.get("shoggoth", "Ooze Spread: Iron", true, "Toggles ooze spreading on blocks with the Iron material (Iron blocks, Gold blocks, Diamond blocks).").getBoolean();
		oozeClay = cfg.get("shoggoth", "Ooze Spread: Clay", true, "Toggles ooze spreading on blocks with the Clay material (Clay blocks).").getBoolean();

		generateDarklandsStructures = cfg.get("worldgen", "Darklands Structures", true, "Toggles whether or not to generate random Darklands structures.").getBoolean();
		generateShoggothLairs = cfg.get("worldgen", "Shoggoth Lairs", true, "Toggles whether or not to generate Shoggoth Lairs (however, they will still generate in Omothol).").getBoolean();
		generateAbyssalWastelandPillars = cfg.get("worldgen", "Abyssal Wasteland Pillars", true, "Toggles whether or not to generate Tall Obsidian Pillars in the Abyssal Wasteland.").getBoolean();
		generateAbyssalWastelandRuins = cfg.get("worldgen", "Abyssal Wasteland Ruins", true, "Toggles whether or not to generate small ruins in the Abyssal Wasteland.").getBoolean();
		generateAntimatterLake = cfg.get("worldgen", "Liquid Antimatter Lakes", true, "Toggles whether or not to generate Liquid Antimatter Lakes in Coralium Infested Swamps.").getBoolean();
		generateCoraliumLake = cfg.get("worldgen", "Liquid Coralium Lakes", true, "Toggles whether or not to generate Liquid Coralium Lakes in the Abyssal Wasteland.").getBoolean();
		generateDreadlandsStalagmite = cfg.get("worldgen", "Dreadlands Stalagmites", true, "Toggles whether or not to generate Stalagmites in Dreadlands and Purified Dreadlands biomes.").getBoolean();

		generateCoraliumOre = cfg.get("worldgen", "Coralium Ore", true, "Toggles whether or not to generate Coralium Ore in the Overworld.").getBoolean();
		generateNitreOre = cfg.get("worldgen", "Nitre Ore", true, "Toggles whether or not to generate Nitre Ore in the Overworld.").getBoolean();
		generateAbyssalniteOre = cfg.get("worldgen", "Abyssalnite Ore", true, "Toggles wheter or not to generate Abyssalnite Ore in Darklands Biomes.").getBoolean();
		generateAbyssalCoraliumOre = cfg.get("worldgen", "Abyssal Coralium Ore", true, "Toggles whether or not to generate Coralium Ore in the Abyssal Wasteland.").getBoolean();
		generateDreadlandsAbyssalniteOre = cfg.get("worldgen", "Dreadlands Abyssalnite Ore", true, "Toggles whether or not to generate Abyssalnite Ore in the Dreadlands.").getBoolean();
		generateDreadedAbyssalniteOre = cfg.get("worldgen", "Dreaded Abyssalnite Ore", true, "Toggles whether or not to generate Dreaded Abyssalnite Ore in the Dreadlands.").getBoolean();
		generateAbyssalIronOre = cfg.get("worldgen", "Abyssal Iron Ore", true, "Toggles whether or not to generate Iron Ore in the Abyssal Wasteland.").getBoolean();
		generateAbyssalGoldOre = cfg.get("worldgen", "Abyssal Gold Ore", true, "Toggles whether or not to generate Gold Ore in the Abyssal Wasteland.").getBoolean();
		generateAbyssalDiamondOre = cfg.get("worldgen", "Abyssal Diamond Ore", true, "Toggles whether or not to generate Diamond Ore in the Abyssal Wasteland").getBoolean();
		generateAbyssalNitreOre = cfg.get("worldgen", "Abyssal Nitre Ore", true, "Toggles whether or not to generate Nitre Ore in the Abyssal Wasteland.").getBoolean();
		generateAbyssalTinOre = cfg.get("worldgen", "Abyssal Tin Ore", true, "Toggles whether or not to generate Tin Ore in the Abyssal Wasteland").getBoolean();
		generateAbyssalCopperOre = cfg.get("worldgen", "Abyssal Copper Ore", true, "Toggles whether or not to generate Copper Ore in the Abyssal Wasteland.").getBoolean();
		generatePearlescentCoraliumOre = cfg.get("worldgen", "Pearlescent Coralium Ore", true, "Toggles whether or not to generate Pearlescent Coralium Ore in the Abyssal Wasteland.").getBoolean();
		generateLiquifiedCoraliumOre = cfg.get("worldgen", "Liquified Coralium Ore", true, "Toggles whether or not to generate Liquified Coralium Ore in the Abyssal Wasteland.").getBoolean();

		if (cfg.hasChanged()) {
			cfg.save();
		}
	}

	private void addOreDictionaryStuff() {
		OreDictionary.registerOre("ingotLiquifiedCoralium", Cingot);
		OreDictionary.registerOre("gemCoralium", Coralium);
		OreDictionary.registerOre("oreCoralium", Coraliumore);
		OreDictionary.registerOre("oreCoralium", AbyCorOre);
		OreDictionary.registerOre("oreDreadedAbyssalnite", dreadore);
		OreDictionary.registerOre("oreAbyssalnite", abydreadore);
		OreDictionary.registerOre("oreCoraliumStone", CoraliumInfusedStone);
		OreDictionary.registerOre("gemShadow", shadowgem);
		OreDictionary.registerOre("liquidCoralium", Cwater);
		OreDictionary.registerOre("materialCoraliumPearl", Cpearl);
		OreDictionary.registerOre("liquidAntimatter", anticwater);
		OreDictionary.registerOre("logWood", DLTLog);
		OreDictionary.registerOre("logWood", dreadlog);
		OreDictionary.registerOre("plankWood", DLTplank);
		OreDictionary.registerOre("plankWood", dreadplanks);
		OreDictionary.registerOre("treeSapling", DLTSapling);
		OreDictionary.registerOre("treeSapling", dreadsapling);
		OreDictionary.registerOre("treeLeaves", DLTLeaves);
		OreDictionary.registerOre("treeLeaves", dreadleaves);
		OreDictionary.registerOre("blockLiquifiedCoralium", corblock);
		OreDictionary.registerOre("blockDreadium", dreadiumblock);
		OreDictionary.registerOre("ingotCoraliumBrick", cbrick);
		OreDictionary.registerOre("ingotDreadium", dreadiumingot);
		OreDictionary.registerOre("dustSulfur", sulfur);
		OreDictionary.registerOre("dustSaltpeter", nitre);
		OreDictionary.registerOre("materialMethane", methane);
		OreDictionary.registerOre("oreSaltpeter", nitreOre);
		OreDictionary.registerOre("oreIron", AbyIroOre);
		OreDictionary.registerOre("oreGold", AbyGolOre);
		OreDictionary.registerOre("oreDiamond", AbyDiaOre);
		OreDictionary.registerOre("oreSaltpeter", AbyNitOre);
		OreDictionary.registerOre("oreTin", AbyTinOre);
		OreDictionary.registerOre("oreCopper", AbyCopOre);
		OreDictionary.registerOre("ingotTin", tinIngot);
		OreDictionary.registerOre("ingotCopper", copperIngot);
		OreDictionary.registerOre("ingotBronze", bronzeIngot);
		OreDictionary.registerOre("nuggetBronze", bronzeNugget);
		OreDictionary.registerOre("orePearlescentCoralium", AbyPCorOre);
		OreDictionary.registerOre("oreLiquifiedCoralium", AbyLCorOre);
		OreDictionary.registerOre("ingotEthaxiumBrick", ethaxium_brick);
		OreDictionary.registerOre("ingotEthaxium", ethaxiumIngot);
		OreDictionary.registerOre("blockEthaxium", ethaxiumblock);
		OreDictionary.registerOre("nuggetLiquifiedCoralium", new ItemStack(nugget, 1, 0));
		OreDictionary.registerOre("nuggetDreadium", new ItemStack(nugget, 1, 1));
		OreDictionary.registerOre("nuggetEthaxium", new ItemStack(nugget, 1, 2));
	}

	private void addChestGenHooks() {
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(copperIngot), 1, 5, 7));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(Cingot), 1, 2, 1));

		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(MRE), 1, 1, 5));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(Coralium), 1, 5, 8));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(shadowfragment), 1, 10, 8));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(shadowshard), 1, 6, 5));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(shadowgem), 1, 1, 3));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(tinIngot), 1, 5, 7));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(new ItemStack(shadowfragment), 1, 10, 8));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(new ItemStack(shadowshard), 1, 6, 5));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(new ItemStack(shadowgem), 1, 1, 3));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(new ItemStack(tinIngot), 1, 5, 7));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(new ItemStack(Cingot), 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(new ItemStack(copperIngot), 1, 5, 7));
	}

	private void addDungeonHooks() {
		DungeonHooks.addDungeonMob("abyssalcraft.abyssalzombie", 150);
		DungeonHooks.addDungeonMob("abyssalcraft.depthsghoul", 100);
		DungeonHooks.addDungeonMob("abyssalcraft.shadowcreature", 120);
		DungeonHooks.addDungeonMob("abyssalcraft.shadowmonster", 100);
		DungeonHooks.addDungeonMob("abyssalcraft.shadowbeast", 30);
		DungeonHooks.addDungeonMob("abyssalcraft.antiabyssalzombie", 50);
		DungeonHooks.addDungeonMob("abyssalcraft.antighoul", 50);
		DungeonHooks.addDungeonMob("abyssalcraft.antispider", 50);
		DungeonHooks.addDungeonMob("abyssalcraft.antizombie", 50);
	}

	private void sendIMC() {

	}

	private void checkBiomeIds(boolean first) {
		if (first) {
			ACLogger.info("Scanning biome IDs to see if the ones needed are available.");

			if (BiomeGenBase.getBiomeGenArray()[configBiomeId1] != null && dark1)
				throw new RuntimeException(String.format("Biome ID %d is already occupied by the biome %s (%s)!", configBiomeId1, BiomeGenBase.getBiome(configBiomeId1).biomeName, BiomeGenBase.getBiome(configBiomeId1).getBiomeClass().getName()));
			if (BiomeGenBase.getBiomeGenArray()[configBiomeId2] != null)
				throw new RuntimeException(String.format("Biome ID %d is already occupied by the biome %s (%s)!", configBiomeId2, BiomeGenBase.getBiome(configBiomeId2).biomeName, BiomeGenBase.getBiome(configBiomeId2).getBiomeClass().getName()));
			if (BiomeGenBase.getBiomeGenArray()[configBiomeId3] != null)
				throw new RuntimeException(String.format("Biome ID %d is already occupied by the biome %s (%s)!", configBiomeId3, BiomeGenBase.getBiome(configBiomeId3).biomeName, BiomeGenBase.getBiome(configBiomeId3).getBiomeClass().getName()));
			if (BiomeGenBase.getBiomeGenArray()[configBiomeId4] != null)
				throw new RuntimeException(String.format("Biome ID %d is already occupied by the biome %s (%s)!", configBiomeId4, BiomeGenBase.getBiome(configBiomeId4).biomeName, BiomeGenBase.getBiome(configBiomeId4).getBiomeClass().getName()));
			if (BiomeGenBase.getBiomeGenArray()[configBiomeId5] != null)
				throw new RuntimeException(String.format("Biome ID %d is already occupied by the biome %s (%s)!", configBiomeId5, BiomeGenBase.getBiome(configBiomeId5).biomeName, BiomeGenBase.getBiome(configBiomeId5).getBiomeClass().getName()));
			if (BiomeGenBase.getBiomeGenArray()[configBiomeId6] != null)
				throw new RuntimeException(String.format("Biome ID %d is already occupied by the biome %s (%s)!", configBiomeId6, BiomeGenBase.getBiome(configBiomeId6).biomeName, BiomeGenBase.getBiome(configBiomeId6).getBiomeClass().getName()));
			if (BiomeGenBase.getBiomeGenArray()[configBiomeId7] != null && dark2)
				throw new RuntimeException(String.format("Biome ID %d is already occupied by the biome %s (%s)!", configBiomeId7, BiomeGenBase.getBiome(configBiomeId7).biomeName, BiomeGenBase.getBiome(configBiomeId7).getBiomeClass().getName()));
			if (BiomeGenBase.getBiomeGenArray()[configBiomeId8] != null && dark3)
				throw new RuntimeException(String.format("Biome ID %d is already occupied by the biome %s (%s)!", configBiomeId8, BiomeGenBase.getBiome(configBiomeId8).biomeName, BiomeGenBase.getBiome(configBiomeId8).getBiomeClass().getName()));
			if (BiomeGenBase.getBiomeGenArray()[configBiomeId9] != null && dark4)
				throw new RuntimeException(String.format("Biome ID %d is already occupied by the biome %s (%s)!", configBiomeId9, BiomeGenBase.getBiome(configBiomeId9).biomeName, BiomeGenBase.getBiome(configBiomeId9).getBiomeClass().getName()));
			if (BiomeGenBase.getBiomeGenArray()[configBiomeId10] != null && dark5)
				throw new RuntimeException(String.format("Biome ID %d is already occupied by the biome %s (%s)!", configBiomeId10, BiomeGenBase.getBiome(configBiomeId10).biomeName, BiomeGenBase.getBiome(configBiomeId10).getBiomeClass().getName()));
			if (BiomeGenBase.getBiomeGenArray()[configBiomeId11] != null && coralium1)
				throw new RuntimeException(String.format("Biome ID %d is already occupied by the biome %s (%s)!", configBiomeId11, BiomeGenBase.getBiome(configBiomeId11).biomeName, BiomeGenBase.getBiome(configBiomeId11).getBiomeClass().getName()));
			if (BiomeGenBase.getBiomeGenArray()[configBiomeId12] != null)
				throw new RuntimeException(String.format("Biome ID %d is already occupied by the biome %s (%s)!", configBiomeId12, BiomeGenBase.getBiome(configBiomeId12).biomeName, BiomeGenBase.getBiome(configBiomeId12).getBiomeClass().getName()));
			if (BiomeGenBase.getBiomeGenArray()[configBiomeId13] != null)
				throw new RuntimeException(String.format("Biome ID %d is already occupied by the biome %s (%s)!", configBiomeId13, BiomeGenBase.getBiome(configBiomeId13).biomeName, BiomeGenBase.getBiome(configBiomeId13).getBiomeClass().getName()));

			ACLogger.info("None of the needed biome IDs are occupied!");
		} else {
			ACLogger.info("Checking so that no other mod has overridden a used biome ID.");

			if (BiomeGenBase.getBiomeGenArray()[configBiomeId1] != ACBiomes.darklands && dark1)
				throw new RuntimeException(String.format("Biome ID %d was overridden by the biome %s (%s)!", configBiomeId1, BiomeGenBase.getBiome(configBiomeId1).biomeName, BiomeGenBase.getBiome(configBiomeId1).getBiomeClass().getName()));
			if (BiomeGenBase.getBiomeGenArray()[configBiomeId2] != ACBiomes.abyssal_wastelands)
				throw new RuntimeException(String.format("Biome ID %d was overridden by the biome %s (%s)!", configBiomeId2, BiomeGenBase.getBiome(configBiomeId2).biomeName, BiomeGenBase.getBiome(configBiomeId2).getBiomeClass().getName()));
			if (BiomeGenBase.getBiomeGenArray()[configBiomeId3] != ACBiomes.dreadlands)
				throw new RuntimeException(String.format("Biome ID %d was overridden by the biome %s (%s)!", configBiomeId3, BiomeGenBase.getBiome(configBiomeId3).biomeName, BiomeGenBase.getBiome(configBiomeId3).getBiomeClass().getName()));
			if (BiomeGenBase.getBiomeGenArray()[configBiomeId4] != ACBiomes.purified_dreadlands)
				throw new RuntimeException(String.format("Biome ID %d was overridden by the biome %s (%s)!", configBiomeId4, BiomeGenBase.getBiome(configBiomeId4).biomeName, BiomeGenBase.getBiome(configBiomeId4).getBiomeClass().getName()));
			if (BiomeGenBase.getBiomeGenArray()[configBiomeId5] != ACBiomes.dreadlands_forest)
				throw new RuntimeException(String.format("Biome ID %d was overridden by the biome %s (%s)!", configBiomeId5, BiomeGenBase.getBiome(configBiomeId5).biomeName, BiomeGenBase.getBiome(configBiomeId5).getBiomeClass().getName()));
			if (BiomeGenBase.getBiomeGenArray()[configBiomeId6] != ACBiomes.dreadlands_mountains)
				throw new RuntimeException(String.format("Biome ID %d was overridden by the biome %s (%s)!", configBiomeId6, BiomeGenBase.getBiome(configBiomeId6).biomeName, BiomeGenBase.getBiome(configBiomeId6).getBiomeClass().getName()));
			if (BiomeGenBase.getBiomeGenArray()[configBiomeId7] != ACBiomes.darklands_forest && dark2)
				throw new RuntimeException(String.format("Biome ID %d was overridden by the biome %s (%s)!", configBiomeId7, BiomeGenBase.getBiome(configBiomeId7).biomeName, BiomeGenBase.getBiome(configBiomeId7).getBiomeClass().getName()));
			if (BiomeGenBase.getBiomeGenArray()[configBiomeId8] != ACBiomes.darklands_plains && dark3)
				throw new RuntimeException(String.format("Biome ID %d was overridden by the biome %s (%s)!", configBiomeId8, BiomeGenBase.getBiome(configBiomeId8).biomeName, BiomeGenBase.getBiome(configBiomeId8).getBiomeClass().getName()));
			if (BiomeGenBase.getBiomeGenArray()[configBiomeId9] != ACBiomes.darklands_hills && dark4)
				throw new RuntimeException(String.format("Biome ID %d was overridden by the biome %s (%s)!", configBiomeId9, BiomeGenBase.getBiome(configBiomeId9).biomeName, BiomeGenBase.getBiome(configBiomeId9).getBiomeClass().getName()));
			if (BiomeGenBase.getBiomeGenArray()[configBiomeId10] != ACBiomes.darklands_mountains && dark5)
				throw new RuntimeException(String.format("Biome ID %d was overridden by the biome %s (%s)!", configBiomeId10, BiomeGenBase.getBiome(configBiomeId10).biomeName, BiomeGenBase.getBiome(configBiomeId10).getBiomeClass().getName()));
			if (BiomeGenBase.getBiomeGenArray()[configBiomeId11] != ACBiomes.coralium_infested_swamp && coralium1)
				throw new RuntimeException(String.format("Biome ID %d was overridden by the biome %s (%s)!", configBiomeId11, BiomeGenBase.getBiome(configBiomeId11).biomeName, BiomeGenBase.getBiome(configBiomeId11).getBiomeClass().getName()));
			if (BiomeGenBase.getBiomeGenArray()[configBiomeId12] != ACBiomes.omothol)
				throw new RuntimeException(String.format("Biome ID %d was overridden by the biome %s (%s)!", configBiomeId12, BiomeGenBase.getBiome(configBiomeId12).biomeName, BiomeGenBase.getBiome(configBiomeId12).getBiomeClass().getName()));
			if (BiomeGenBase.getBiomeGenArray()[configBiomeId13] != ACBiomes.dark_realm)
				throw new RuntimeException(String.format("Biome ID %d was overridden by the biome %s (%s)!", configBiomeId13, BiomeGenBase.getBiome(configBiomeId13).biomeName, BiomeGenBase.getBiome(configBiomeId13).getBiomeClass().getName()));

			ACLogger.info("None of the biome IDs has been overridden.");
		}
	}

	private static int getUniqueEntityId() {
		do
			startEntityId++;
		while (EntityList.getStringFromID(startEntityId) != null);

		return startEntityId;
	}

	private int getNextAvailablePotionId() {

		int pot = Potion.potionTypes.length;
		int i = 1;

		do {
			i++;
			if (i >= 128)
				throw new RuntimeException("Out of available Potion IDs, AbyssalCraft can't load unless some IDs are freed up!");
			if (i == pot)
				for (Field f : Potion.class.getDeclaredFields()) {
					f.setAccessible(true);
					try {
						if (f.getName().equals("potionTypes") || f.getName().equals("field_76425_a")) {
							Field modfield = Field.class.getDeclaredField("modifiers");
							modfield.setAccessible(true);
							modfield.setInt(f, f.getModifiers() & ~Modifier.FINAL);

							Potion[] potionTypes = (Potion[]) f.get(null);
							final Potion[] newPotionTypes = new Potion[pot++];
							System.arraycopy(potionTypes, 0, newPotionTypes, 0, potionTypes.length);
							f.set(null, newPotionTypes);
						}
					} catch (Exception e) {
						System.err.println("Whoops, something screwed up here, please report this to shinoow:");
						System.err.println(e);
					}
				}

		} while (Potion.potionTypes[i] != null);

		return i;
	}

	private int getNextAvailableEnchantmentId() {

		int i = 0;

		do {
			i++;
			if (i >= 256)
				throw new RuntimeException("Out of available Enchantment IDs, AbyssalCraft can't load unless some IDs are freed up!");

		} while (Enchantment.enchantmentsList[i] != null);

		return i;
	}

	private void extendPotionArray() {
		if (Potion.potionTypes.length < 128) {
			Potion[] potionTypes = null;
			for (Field f : Potion.class.getDeclaredFields()) {
				f.setAccessible(true);
				try {
					if (f.getName().equals("potionTypes") || f.getName().equals("field_76425_a")) {
						Field modfield = Field.class.getDeclaredField("modifiers");
						modfield.setAccessible(true);
						modfield.setInt(f, f.getModifiers() & ~Modifier.FINAL);

						potionTypes = (Potion[]) f.get(null);
						final Potion[] newPotionTypes = new Potion[128];
						System.arraycopy(potionTypes, 0, newPotionTypes, 0, potionTypes.length);
						f.set(null, newPotionTypes);
					}
				} catch (Exception e) {
					System.err.println("Whoops, something screwed up here, please report this to shinoow:");
					System.err.println(e);
				}
			}
		} else
			ACLogger.info("The potion array has already been extended (%d), so we're not doing it again.", Potion.potionTypes.length);
	}

	@SuppressWarnings("unchecked")
	private static void registerEntityWithEgg(Class<? extends Entity> entity, String name, int modid, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates, int primaryColor, int secondaryColor) {
		final int id = getUniqueEntityId();
		stringtoIDMapping.put(name, id);
		EntityRegistry.registerModEntity(entity, name, modid, instance, trackingRange, updateFrequency, sendsVelocityUpdates);
		EntityList.IDtoClassMapping.put(id, entity);
		EntityList.entityEggs.put(id, new EntityEggInfo(id, primaryColor, secondaryColor));
	}

	@SuppressWarnings({ "unchecked", "unused" })
	private static void registerEntity(Class<? extends Entity> entity, String name, int modid, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) {
		final int id = getUniqueEntityId();
		stringtoIDMapping.put(name, id);
		EntityRegistry.registerModEntity(entity, name, modid, instance, trackingRange, updateFrequency, sendsVelocityUpdates);
		EntityList.IDtoClassMapping.put(id, entity);
	}

	private static void registerDimension(int id, Class<? extends WorldProvider> provider, boolean keepLoaded) {
		DimensionManager.registerProviderType(id, provider, keepLoaded);
		DimensionManager.registerDimension(id, id);
	}

	private static void registerBiomeWithTypes(BiomeGenBase biome, int weight, BiomeType btype, Type... types) {
		BiomeDictionary.registerBiomeType(biome, types);
		BiomeManager.addBiome(btype, new BiomeEntry(biome, weight));
	}

	private String getSupporterList() {
		BufferedReader nameFile;
		String names = "";
		try {
			nameFile = new BufferedReader(new InputStreamReader(new URL("https://raw.githubusercontent.com/Shinoow/AbyssalCraft/master/supporters.txt").openStream()));

			names = nameFile.readLine();
			nameFile.close();

		} catch (IOException e) {
			ACLogger.severe("Failed to fetch supporter list, using local version!");
			names = "Enfalas, Saice Shoop";
		}

		return names;
	}
}