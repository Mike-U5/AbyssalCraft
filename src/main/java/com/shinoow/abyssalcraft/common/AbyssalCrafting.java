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
package com.shinoow.abyssalcraft.common;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.energy.EnergyEnum.DeityType;
import com.shinoow.abyssalcraft.api.energy.disruption.DisruptionBiomeSwarm;
import com.shinoow.abyssalcraft.api.energy.disruption.DisruptionHandler;
import com.shinoow.abyssalcraft.api.energy.disruption.DisruptionPotion;
import com.shinoow.abyssalcraft.api.energy.disruption.DisruptionSpawn;
import com.shinoow.abyssalcraft.api.energy.disruption.DisruptionSwarm;
import com.shinoow.abyssalcraft.api.item.ItemEngraving;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconCreationRitual;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconInfusionRitual;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconPotionAoERitual;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconSummonRitual;
import com.shinoow.abyssalcraft.api.ritual.RitualRegistry;
import com.shinoow.abyssalcraft.client.lib.NecronomiconText;
import com.shinoow.abyssalcraft.common.disruptions.DisruptionDisplaceEntities;
import com.shinoow.abyssalcraft.common.disruptions.DisruptionFire;
import com.shinoow.abyssalcraft.common.disruptions.DisruptionFireRain;
import com.shinoow.abyssalcraft.common.disruptions.DisruptionFreeze;
import com.shinoow.abyssalcraft.common.disruptions.DisruptionLightning;
import com.shinoow.abyssalcraft.common.disruptions.DisruptionOoze;
import com.shinoow.abyssalcraft.common.disruptions.DisruptionPotentialEnergy;
import com.shinoow.abyssalcraft.common.disruptions.DisruptionTaintRain;
import com.shinoow.abyssalcraft.common.disruptions.DisruptionTeleportRandomly;
import com.shinoow.abyssalcraft.common.entity.EntityDragonBoss;
import com.shinoow.abyssalcraft.common.entity.EntityGatekeeperMinion;
import com.shinoow.abyssalcraft.common.entity.EntityLesserShoggoth;
import com.shinoow.abyssalcraft.common.entity.EntitySacthoth;
import com.shinoow.abyssalcraft.common.entity.EntityShadowBeast;
import com.shinoow.abyssalcraft.common.entity.EntityShadowCreature;
import com.shinoow.abyssalcraft.common.entity.EntityShadowMonster;
import com.shinoow.abyssalcraft.common.entity.anti.EntityFallenHero;
import com.shinoow.abyssalcraft.common.ritual.NecronomiconBreedingRitual;
import com.shinoow.abyssalcraft.common.ritual.NecronomiconDreadSpawnRitual;
import com.shinoow.abyssalcraft.common.ritual.NecronomiconPurificationRitual;
import com.shinoow.abyssalcraft.common.ritual.NecronomiconRespawnJzaharRitual;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class AbyssalCrafting {

	public static void addRecipes() {
		addBlockCrafting();
		addBlockSmelting();
		addItemCrafting();
		addItemSmelting();
		addEngraving();
		addRitualRecipes();
		addDisruptions();
	}

	private static void addBlockCrafting() {
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Darkstone_brick, 4), new Object[] { "AA", "AA", 'A', AbyssalCraft.Darkstone });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Darkbrickslab1, 6), new Object[] { "AAA", 'A', AbyssalCraft.Darkstone_brick });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.DSGlow, 4), new Object[] { "#$#", "&%&", "#&#", '#', AbyssalCraft.Darkstone_brick, '$', Items.diamond, '&', Blocks.obsidian, '%', Blocks.glowstone });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Darkcobbleslab1, 6), new Object[] { "AAA", 'A', AbyssalCraft.Darkstone_cobble });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.DLTplank, 4), new Object[] { "A", 'A', AbyssalCraft.DLTLog });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.abybrick, 4), new Object[] { "##", "##", '#', AbyssalCraft.abystone });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.abyslab1, 6), new Object[] { "###", '#', AbyssalCraft.abybrick });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.abyfence, 6), new Object[] { "###", "###", '#', AbyssalCraft.abybrick });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.DSCwall, 6), new Object[] { "###", "###", '#', AbyssalCraft.Darkstone_cobble });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.DSbutton, 1), new Object[] { "#", '#', AbyssalCraft.Darkstone });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.DSpplate, 1), new Object[] { "##", '#', AbyssalCraft.Darkstone });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.DLTbutton, 1), new Object[] { "#", '#', AbyssalCraft.DLTplank });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.DLTpplate, 1), new Object[] { "##", '#', AbyssalCraft.DLTplank });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.DLTslab1, 6), new Object[] { "###", '#', AbyssalCraft.DLTplank });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Darkstoneslab1, 6), new Object[] { "###", '#', AbyssalCraft.Darkstone });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.corblock, 1), new Object[] { "###", "###", "###", '#', AbyssalCraft.Cingot });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Abybutton, 1), new Object[] { "#", '#', AbyssalCraft.abystone });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Abypplate, 1), new Object[] { "##", '#', AbyssalCraft.abystone });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.DSBfence, 6), new Object[] { "###", "###", '#', AbyssalCraft.Darkstone_brick });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.DLTfence, 4), new Object[] { "###", "###", '#', AbyssalCraft.DLTplank });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadbrick, 4), new Object[] { "##", "##", '#', AbyssalCraft.dreadstone });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.abydreadbrick, 4), new Object[] { "##", "##", '#', AbyssalCraft.abydreadstone });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadplanks, 4), new Object[] { "%", '%', AbyssalCraft.dreadlog });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadbrickslab1, 6), new Object[] { "###", '#', AbyssalCraft.dreadbrick });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadbrickfence, 6), new Object[] { "###", "###", '#', AbyssalCraft.dreadbrick });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.abydreadbrickslab1, 6), new Object[] { "###", '#', AbyssalCraft.abydreadbrick });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.abydreadbrickfence, 6), new Object[] { "###", "###", '#', AbyssalCraft.abydreadbrick });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.cstonebrick, 1), new Object[] { "##", "##", '#', AbyssalCraft.cbrick });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.cstonebrickslab1, 6), new Object[] { "###", '#', AbyssalCraft.cstonebrick });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.cstonebrickfence, 6), new Object[] { "###", "###", '#', AbyssalCraft.cstonebrick });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.cstonebutton, 1), new Object[] { "#", '#', AbyssalCraft.cstone });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.cstonepplate, 1), new Object[] { "##", '#', AbyssalCraft.cstone });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumblock, 1), new Object[] { "###", "###", "###", '#', AbyssalCraft.dreadiumingot });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.DrTfence, 4), new Object[] { "###", "###", '#', AbyssalCraft.dreadplanks });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadaltartop, 1), new Object[] { "#%#", "&&&", "@@@", '#', Items.stick, '%', Items.bucket, '&', AbyssalCraft.dreadcloth, '@', AbyssalCraft.dreadiumingot });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadaltarbottom, 1), new Object[] { "#%#", "&@&", "T$T", '#', Items.bone, '%', AbyssalCraft.dreadcloth, '&', AbyssalCraft.dreadiumingot, '@', AbyssalCraft.portalPlacerDL, '$', AbyssalCraft.Dreadshard, 'T', AbyssalCraft.dreadstone });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethaxiumbrick, 1, 0), new Object[] { "##", "##", '#', AbyssalCraft.ethaxium_brick });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethaxiumbrick, 1, 1), new Object[] { "##", "##", '#', new ItemStack(AbyssalCraft.ethaxiumbrick, 1, 0) });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethaxiumpillar, 2), new Object[] { "#%", "#%", '#', new ItemStack(AbyssalCraft.ethaxiumbrick, 1, 0), '%', AbyssalCraft.ethaxium });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.DBstairs, 4), new Object[] { "#  ", "## ", "###", '#', AbyssalCraft.Darkstone_brick });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.DCstairs, 4), new Object[] { "#  ", "## ", "###", '#', AbyssalCraft.Darkstone_cobble });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.abystairs, 4), new Object[] { "#  ", "## ", "###", '#', AbyssalCraft.abybrick });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.DLTstairs, 4), new Object[] { "#  ", "## ", "###", '#', AbyssalCraft.DLTplank });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadbrickstairs, 4), new Object[] { "#  ", "## ", "###", '#', AbyssalCraft.dreadbrick });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.abydreadbrickstairs, 4), new Object[] { "#  ", "## ", "###", '#', AbyssalCraft.abydreadbrick });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.cstonebrickstairs, 4), new Object[] { "#  ", "## ", "###", '#', AbyssalCraft.cstonebrick });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethaxiumblock, 1), new Object[] { "###", "###", "###", '#', AbyssalCraft.ethaxiumIngot });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethaxiumstairs, 4), new Object[] { "#  ", "## ", "###", '#', new ItemStack(AbyssalCraft.ethaxiumbrick, 0) });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethaxiumslab1, 6), new Object[] { "AAA", 'A', new ItemStack(AbyssalCraft.ethaxiumbrick, 0) });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethaxiumfence, 6), new Object[] { "###", "###", '#', new ItemStack(AbyssalCraft.ethaxiumbrick, 0) });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.engraver, 1), new Object[] { "#% ", "#%&", "@% ", '#', AbyssalCraft.engravingBlank, '%', Blocks.stone, '&', Blocks.lever, '@', Blocks.anvil });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(AbyssalCraft.house, 1), true, new Object[] { "#%#", "%&%", "%%%", '#', "stairWood", '%', "plankWood", '&', Items.wooden_door })); // Quite frankly, this recipe doesn't exist
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.darkethaxiumbrick, 1, 0), new Object[] { "#%", "#%", '#', AbyssalCraft.omotholstone, '%', AbyssalCraft.ethaxium });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.darkethaxiumbrick, 1, 1), new Object[] { "##", "##", '#', new ItemStack(AbyssalCraft.darkethaxiumbrick, 1, 0) });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.darkethaxiumpillar, 2), new Object[] { "#%", "#%", '#', new ItemStack(AbyssalCraft.darkethaxiumbrick, 1, 0), '%', AbyssalCraft.omotholstone });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.darkethaxiumstairs, 4), new Object[] { "#  ", "## ", "###", '#', new ItemStack(AbyssalCraft.darkethaxiumbrick, 0) });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.darkethaxiumslab1, 6), new Object[] { "AAA", 'A', new ItemStack(AbyssalCraft.darkethaxiumbrick, 0) });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.darkethaxiumfence, 6), new Object[] { "###", "###", '#', new ItemStack(AbyssalCraft.darkethaxiumbrick, 0) });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.energyPedestal), new Object[] { "#%#", "#&#", "###", '#', AbyssalCraft.monolithStone, '%', AbyssalCraft.Cpearl, '&', AbyssalCraft.shadowgem });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.monolithPillar), new Object[] { "##", "##", '#', AbyssalCraft.monolithStone });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.sacrificialAltar), new Object[] { "#%#", "&$&", "&&&", '#', Blocks.torch, '%', AbyssalCraft.Cpearl, '$', AbyssalCraft.shadowgem, '&', AbyssalCraft.monolithStone });
	}

	private static void addBlockSmelting() {
		GameRegistry.addSmelting(AbyssalCraft.Darkstone_cobble, new ItemStack(AbyssalCraft.Darkstone, 1), 0.1F);
		AbyssalCraftAPI.addOreSmelting("oreCoralium", "gemCoralium", 3F);
		GameRegistry.addSmelting(AbyssalCraft.DLTLog, new ItemStack(Items.coal, 1, 1), 1F);
		GameRegistry.addSmelting(AbyssalCraft.AbyPCorOre, new ItemStack(AbyssalCraft.Cpearl), 3F);
		GameRegistry.addSmelting(AbyssalCraft.AbyLCorOre, new ItemStack(AbyssalCraft.Cingot), 3F);
		GameRegistry.addSmelting(AbyssalCraft.cstone, new ItemStack(AbyssalCraft.cbrick, 1), 0.1F);
		GameRegistry.addSmelting(AbyssalCraft.nitreOre, new ItemStack(AbyssalCraft.nitre, 1), 1F);
		GameRegistry.addSmelting(AbyssalCraft.AbyIroOre, new ItemStack(Items.iron_ingot, 1), 0.7F);
		GameRegistry.addSmelting(AbyssalCraft.AbyGolOre, new ItemStack(Items.gold_ingot, 1), 1F);
		GameRegistry.addSmelting(AbyssalCraft.AbyDiaOre, new ItemStack(Items.diamond, 1), 1F);
		GameRegistry.addSmelting(AbyssalCraft.AbyNitOre, new ItemStack(AbyssalCraft.nitre, 1), 1F);
		GameRegistry.addSmelting(AbyssalCraft.AbyTinOre, new ItemStack(AbyssalCraft.tinIngot, 1), 0.7F);
		GameRegistry.addSmelting(AbyssalCraft.AbyCopOre, new ItemStack(AbyssalCraft.copperIngot, 1), 0.7F);
		GameRegistry.addSmelting(AbyssalCraft.ethaxium, new ItemStack(AbyssalCraft.ethaxium_brick), 0.2F);
	}

	private static void addItemCrafting() {
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.portalPlacer, 1), new Object[] { " #%", " &#", "&  ", '#', AbyssalCraft.Cpearl, '%', AbyssalCraft.OC, '&', Items.blaze_rod });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Cplate, 1), new Object[] { "#%#", "#%#", "#%#", '#', AbyssalCraft.Cingot, '%', AbyssalCraft.Cpearl });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Cchunk, 1), new Object[] { "###", "#%#", "###", '#', AbyssalCraft.Coraliumcluster9, '%', AbyssalCraft.abystone });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.PSDLfinder, 4), new Object[] { "###", "#%#", "###", '#', AbyssalCraft.Coralium, '%', Items.ender_eye });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Cingot, 9), new Object[] { "#", '#', AbyssalCraft.corblock });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Corpickaxe, 1), new Object[] { "###", " % ", " % ", '#', AbyssalCraft.Cingot, '%', Items.stick });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Coraxe, 1), new Object[] { "##", "#%", " %", '#', AbyssalCraft.Cingot, '%', Items.stick });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Corshovel, 1), new Object[] { "#", "%", "%", '#', AbyssalCraft.Cingot, '%', Items.stick });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Corsword, 1), new Object[] { "#", "#", "%", '#', AbyssalCraft.Cingot, '%', Items.stick });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Corhoe, 1), new Object[] { "##", " %", " %", '#', AbyssalCraft.Cingot, '%', Items.stick });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.shadowshard, 1), new Object[] { "###", "###", "###", '#', AbyssalCraft.shadowfragment });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.shadowgem, 1), new Object[] { "###", "###", "###", '#', AbyssalCraft.shadowshard });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.oblivionshard, 1), new Object[] { " # ", "#%#", " # ", '#', AbyssalCraft.shadowgem, '%', new ItemStack(AbyssalCraft.Corb, 1, OreDictionary.WILDCARD_VALUE) });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.corbow, 1), new Object[] { " #%", "&$%", " #%", '#', AbyssalCraft.Cingot, '%', Items.string, '&', AbyssalCraft.Cpearl, '$', new ItemStack(Items.bow, 1, OreDictionary.WILDCARD_VALUE) });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumingot, 9), new Object[] { "#", '#', AbyssalCraft.dreadiumblock });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumpickaxe, 1), new Object[] { "###", " % ", " % ", '#', AbyssalCraft.dreadiumingot, '%', Items.stick });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumaxe, 1), new Object[] { "##", "#%", " %", '#', AbyssalCraft.dreadiumingot, '%', Items.stick });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumshovel, 1), new Object[] { "#", "%", "%", '#', AbyssalCraft.dreadiumingot, '%', Items.stick });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumsword, 1), new Object[] { "#", "#", "%", '#', AbyssalCraft.dreadiumingot, '%', Items.stick });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumhoe, 1), new Object[] { "##", " %", " %", '#', AbyssalCraft.dreadiumingot, '%', Items.stick });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.denseCarbonCluster, 1), new Object[] { "###", "#%#", "###", '#', AbyssalCraft.carbonCluster, '%', Blocks.obsidian });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.antibucket, 1), new Object[] { "#@%", "$&$", "$$$", '#', Items.lava_bucket, '@', Items.milk_bucket, '%', Items.water_bucket, '$', Items.iron_ingot, '&', AbyssalCraft.Cbucket });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.antibucket, 1), new Object[] { "@%&", "$#$", "$$$", '#', Items.lava_bucket, '@', Items.milk_bucket, '%', Items.water_bucket, '$', Items.iron_ingot, '&', AbyssalCraft.Cbucket });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.antibucket, 1), new Object[] { "%&#", "$@$", "$$$", '#', Items.lava_bucket, '@', Items.milk_bucket, '%', Items.water_bucket, '$', Items.iron_ingot, '&', AbyssalCraft.Cbucket });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.antibucket, 1), new Object[] { "&#@", "$%$", "$$$", '#', Items.lava_bucket, '@', Items.milk_bucket, '%', Items.water_bucket, '$', Items.iron_ingot, '&', AbyssalCraft.Cbucket });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadcloth, 1), new Object[] { "#%#", "%&%", "#%#", '#', Items.string, '%', AbyssalCraft.dreadfragment, '&', Items.leather });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadcloth, 1), new Object[] { "#%#", "%&%", "#%#", '%', Items.string, '#', AbyssalCraft.dreadfragment, '&', Items.leather });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadplate, 1), new Object[] { "###", "#%#", "###", '#', AbyssalCraft.dreadiumingot, '%', AbyssalCraft.dreadcloth });
		///GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadhilt, 1), new Object[] { "###", "%&%", "%&%", '#', AbyssalCraft.dreadiumingot, '%', AbyssalCraft.dreadcloth, '&', AbyssalCraft.dreadplanks });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadkatana, 1), new Object[] { "# ", "% ", '#', AbyssalCraft.dreadblade, '%', AbyssalCraft.dreadhilt });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.gunpowder, 4), true, new Object[] { "#&#", "#%#", "###", '#', "dustSaltpeter", '%', new ItemStack(Items.coal, 1, 1), '&', "dustSulfur" }));
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethaxiumIngot), new Object[] { "###", "#%#", "###", '#', AbyssalCraft.ethaxium_brick, '%', AbyssalCraft.lifeCrystal });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethaxiumIngot), new Object[] { " # ", "#%#", " # ", '#', AbyssalCraft.ethaxium_brick, '%', AbyssalCraft.OC });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethaxiumIngot, 9), new Object[] { "#", '#', AbyssalCraft.ethaxiumblock });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethPickaxe, 1), new Object[] { "###", " % ", " % ", '#', AbyssalCraft.ethaxiumIngot, '%', AbyssalCraft.ethaxium_brick });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethAxe, 1), new Object[] { "##", "#%", " %", '#', AbyssalCraft.ethaxiumIngot, '%', AbyssalCraft.ethaxium_brick });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethShovel, 1), new Object[] { "#", "%", "%", '#', AbyssalCraft.ethaxiumIngot, '%', AbyssalCraft.ethaxium_brick });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethSword, 1), new Object[] { "#", "#", "%", '#', AbyssalCraft.ethaxiumIngot, '%', AbyssalCraft.ethaxium_brick });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethHoe, 1), new Object[] { "##", " %", " %", '#', AbyssalCraft.ethaxiumIngot, '%', AbyssalCraft.ethaxium_brick });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(AbyssalCraft.coin, 1), true, new Object[] { " # ", "#%#", " # ", '#', "ingotCopper", '%', Items.flint }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(AbyssalCraft.coin, 1), true, new Object[] { " # ", "#%#", " # ", '#', "ingotIron", '%', Items.flint }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(AbyssalCraft.coin, 1), true, new Object[] { " # ", "#%#", " # ", '#', "ingotTin", '%', Items.flint }));
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.engravingBlank, 1), new Object[] { "###", "#%#", "###", '#', new ItemStack(Blocks.stone_slab, 1, 0), '%', Items.iron_ingot });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.engravingElder, 1), new Object[] { "#", "%", '#', AbyssalCraft.engravingBlank, '%', AbyssalCraft.ethaxiumIngot });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.engravingCthulhu, 1), new Object[] { "%#%", "%%%", '#', AbyssalCraft.engravingElder, '%', new ItemStack(AbyssalCraft.shoggothFlesh, 1, 0) });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.engravingHastur, 1), new Object[] { "%#%", "%%%", '#', AbyssalCraft.engravingElder, '%', new ItemStack(AbyssalCraft.shoggothFlesh, 1, 1) });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.engravingJzahar, 1), new Object[] { "%#%", "%%%", '#', AbyssalCraft.engravingElder, '%', AbyssalCraft.eldritchScale });
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.engravingAzathoth, 1), new ItemStack(AbyssalCraft.shoggothFlesh, 1, 0), AbyssalCraft.engravingElder, new ItemStack(AbyssalCraft.shoggothFlesh, 1, 1), new ItemStack(AbyssalCraft.shoggothFlesh, 1, 2), new ItemStack(AbyssalCraft.shoggothFlesh, 1, 3), new ItemStack(AbyssalCraft.shoggothFlesh, 1, 4));
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.engravingNyarlathotep, 1), new Object[] { "%#%", "%%%", '#', AbyssalCraft.engravingElder, '%', new ItemStack(AbyssalCraft.shoggothFlesh, 1, 2) });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.engravingYogsothoth, 1), new Object[] { "%#%", "%%%", '#', AbyssalCraft.engravingElder, '%', new ItemStack(AbyssalCraft.shoggothFlesh, 1, 3) });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.engravingShubniggurath, 1), new Object[] { "%#%", "%%%", '#', AbyssalCraft.engravingElder, '%', new ItemStack(AbyssalCraft.shoggothFlesh, 1, 4) });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.necronomicon, 1), new Object[] { "##%", "#&#", "##%", '#', Items.rotten_flesh, '%', Items.iron_ingot, '&', Items.book });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.necronomicon_cor, 1), new Object[] { "###", "#%#", "###", '#', new ItemStack(AbyssalCraft.skin, 1, 0), '%', AbyssalCraft.necronomicon });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.necronomicon_dre, 1), new Object[] { "###", "#%#", "###", '#', new ItemStack(AbyssalCraft.skin, 1, 1), '%', AbyssalCraft.necronomicon_cor });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.necronomicon_omt, 1), new Object[] { "###", "#%#", "###", '#', new ItemStack(AbyssalCraft.skin, 1, 2), '%', AbyssalCraft.necronomicon_dre });
		///GameRegistry.addRecipe(new ItemStack(AbyssalCraft.abyssalnomicon, 1), new Object[] { "#$#", "%&%", "#%#", '#', AbyssalCraft.ethaxiumIngot, '%', AbyssalCraft.eldritchScale, '&', AbyssalCraft.necronomicon_omt, '$', AbyssalCraft.gatekeeperEssence });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystalbag_s, 1), new Object[] {"#%#", "%&%", "%%%", '#', Items.string, '%', Items.leather, '&', Items.gold_ingot});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystalbag_m, 1), new Object[] {"###", "#%#", "###", '#', new ItemStack(AbyssalCraft.skin, 1, 0), '%', AbyssalCraft.crystalbag_s});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystalbag_l, 1), new Object[] {"###", "#%#", "###", '#', new ItemStack(AbyssalCraft.skin, 1, 1), '%', AbyssalCraft.crystalbag_m});
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.crystalbag_h, 1), new Object[] {"###", "#%#", "###", '#', new ItemStack(AbyssalCraft.skin, 1, 2), '%', AbyssalCraft.crystalbag_l});
		
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.nugget, 9, 0), new Object[] { "#", '#', AbyssalCraft.Cingot });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.nugget, 9, 1), new Object[] { "#", '#', AbyssalCraft.dreadiumingot });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.nugget, 9, 2), new Object[] { "#", '#', AbyssalCraft.ethaxiumIngot });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Cingot), new Object[] { "###", "###", "###", '#', new ItemStack(AbyssalCraft.nugget, 1, 0) });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumingot), new Object[] { "###", "###", "###", '#', new ItemStack(AbyssalCraft.nugget, 1, 1) });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.ethaxiumIngot), new Object[] { "###", "###", "###", '#', new ItemStack(AbyssalCraft.nugget, 1, 2) });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.skin, 1, 0), new Object[] { "###", "#%#", "###", '#', AbyssalCraft.Corflesh, '%', new ItemStack(AbyssalCraft.essence, 1, 0) });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.skin, 1, 1), new Object[] { "###", "#%#", "###", '#', AbyssalCraft.dreadfragment, '%', new ItemStack(AbyssalCraft.essence, 1, 1) });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.skin, 1, 2), new Object[] { "###", "#%#", "###", '#', AbyssalCraft.omotholFlesh, '%', new ItemStack(AbyssalCraft.essence, 1, 2) });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.drainStaff), new Object[] { " #%", " ##", "#  ", '#', AbyssalCraft.shadowshard, '%', AbyssalCraft.oblivionshard });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.shadowshard, 9), new Object[] { "#", '#', AbyssalCraft.shadowgem });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.shadowfragment, 9), new Object[] { "#", '#', AbyssalCraft.shadowshard });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.charm, 1, 0), new Object[] { "###", "#%#", "###", '#', Items.gold_ingot, '%', Items.diamond });

		// Bronze Ingot/Nugget Conversion
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.bronzeNugget, 9), new Object[] { AbyssalCraft.bronzeIngot });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.bronzeIngot, 1), new Object[] { "###", "###", "###", '#', new ItemStack(AbyssalCraft.bronzeNugget) });
		
		// Coralium Gem Cluster Recipes
		GameRegistry.addShapelessRecipe(new ItemStack(AbyssalCraft.Coraliumcluster9, 1), AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium);
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.Coralium, 9), new Object[] { "#", '#', AbyssalCraft.Coraliumcluster9 });

		addArmor(AbyssalCraft.Corhelmet, AbyssalCraft.Corplate, AbyssalCraft.Corlegs, AbyssalCraft.Corboots, AbyssalCraft.Cingot, AbyssalCraft.CoraliumU, Items.iron_helmet, Items.iron_chestplate, Items.iron_leggings, Items.iron_boots);
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.CorbootsP, 1), new Object[] { "# #", "%&%", '#', AbyssalCraft.Cingot, '%', AbyssalCraft.Cplate, '&', AbyssalCraft.Corboots });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.CorhelmetP, 1), new Object[] { "&#&", "#@#", "%%%", '#', AbyssalCraft.Cplate, '&', AbyssalCraft.Cpearl, '@', AbyssalCraft.Corhelmet, '%', AbyssalCraft.Cingot });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.CorplateP, 1), new Object[] { "# #", "%@%", "%#%", '#', AbyssalCraft.Cplate, '%', AbyssalCraft.Cingot, '@', AbyssalCraft.Corplate });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.CorlegsP, 1), new Object[] { "%&%", "# #", "# #", '#', AbyssalCraft.Cingot, '%', AbyssalCraft.Cplate, '&', AbyssalCraft.Corlegs });
		addArmor(AbyssalCraft.dreadiumhelmet, AbyssalCraft.dreadiumplate, AbyssalCraft.dreadiumlegs, AbyssalCraft.dreadiumboots, AbyssalCraft.dreadiumingot, AbyssalCraft.DreadiumU, AbyssalCraft.Corhelmet, AbyssalCraft.Corplate, AbyssalCraft.Corlegs, AbyssalCraft.Corboots);
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumSboots, 1), new Object[] { "#%#", "&&&", '#', AbyssalCraft.dreadcloth, '%', new ItemStack(AbyssalCraft.dreadiumboots, 1, OreDictionary.WILDCARD_VALUE), '&', AbyssalCraft.dreadplanks });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumShelmet, 1), new Object[] { " # ", "%&%", '#', AbyssalCraft.dreadiumingot, '%', AbyssalCraft.dreadplate, '&', new ItemStack(AbyssalCraft.dreadiumhelmet, 1, OreDictionary.WILDCARD_VALUE) });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumSplate, 1), new Object[] { "#%#", "#&#", "@@@", '#', AbyssalCraft.dreadplate, '%', AbyssalCraft.dreadiumingot, '&', new ItemStack(AbyssalCraft.dreadiumplate, 1, OreDictionary.WILDCARD_VALUE), '@', AbyssalCraft.dreadcloth });
		GameRegistry.addRecipe(new ItemStack(AbyssalCraft.dreadiumSlegs, 1), new Object[] { "#%#", "&&&", '#', AbyssalCraft.dreadplate, '%', new ItemStack(AbyssalCraft.dreadiumlegs, 1, OreDictionary.WILDCARD_VALUE), '&', AbyssalCraft.dreadcloth });
		addArmor(AbyssalCraft.ethHelmet, AbyssalCraft.ethPlate, AbyssalCraft.ethLegs, AbyssalCraft.ethBoots, AbyssalCraft.ethaxiumIngot, AbyssalCraft.EthaxiumU, AbyssalCraft.dreadiumhelmet, AbyssalCraft.dreadiumplate, AbyssalCraft.dreadiumlegs, AbyssalCraft.dreadiumboots);
	}

	private static void addItemSmelting() {
		GameRegistry.addSmelting(AbyssalCraft.Coraliumcluster9, new ItemStack(AbyssalCraft.Cpearl), 3F);
		GameRegistry.addSmelting(AbyssalCraft.Cchunk, new ItemStack(AbyssalCraft.Cingot, 2), 3F);
		GameRegistry.addSmelting(AbyssalCraft.Cbucket, new ItemStack(AbyssalCraft.cstone, 1), 0.2F);
	}

	private static void addEngraving() {
		AbyssalCraftAPI.addCoin(AbyssalCraft.coin);
		AbyssalCraftAPI.addCoin(AbyssalCraft.cthulhuCoin);
		AbyssalCraftAPI.addCoin(AbyssalCraft.elderCoin);
		AbyssalCraftAPI.addCoin(AbyssalCraft.jzaharCoin);
		AbyssalCraftAPI.addCoin(AbyssalCraft.hasturCoin);
		AbyssalCraftAPI.addCoin(AbyssalCraft.azathothCoin);
		AbyssalCraftAPI.addCoin(AbyssalCraft.nyarlathotepCoin);
		AbyssalCraftAPI.addCoin(AbyssalCraft.yogsothothCoin);
		AbyssalCraftAPI.addCoin(AbyssalCraft.shubniggurathCoin);
		AbyssalCraftAPI.addEngraving(AbyssalCraft.coin, (ItemEngraving) AbyssalCraft.engravingBlank, 0.0F);
		AbyssalCraftAPI.addEngraving(AbyssalCraft.cthulhuCoin, (ItemEngraving) AbyssalCraft.engravingCthulhu, 0.5F);
		AbyssalCraftAPI.addEngraving(AbyssalCraft.elderCoin, (ItemEngraving) AbyssalCraft.engravingElder, 0.5F);
		AbyssalCraftAPI.addEngraving(AbyssalCraft.jzaharCoin, (ItemEngraving) AbyssalCraft.engravingJzahar, 0.5F);
		AbyssalCraftAPI.addEngraving(AbyssalCraft.hasturCoin, (ItemEngraving) AbyssalCraft.engravingHastur, 0.5F);
		AbyssalCraftAPI.addEngraving(AbyssalCraft.azathothCoin, (ItemEngraving) AbyssalCraft.engravingAzathoth, 0.5F);
		AbyssalCraftAPI.addEngraving(AbyssalCraft.nyarlathotepCoin, (ItemEngraving) AbyssalCraft.engravingNyarlathotep, 0.5F);
		AbyssalCraftAPI.addEngraving(AbyssalCraft.yogsothothCoin, (ItemEngraving) AbyssalCraft.engravingYogsothoth, 0.5F);
		AbyssalCraftAPI.addEngraving(AbyssalCraft.shubniggurathCoin, (ItemEngraving) AbyssalCraft.engravingShubniggurath, 0.5F);
	}

	private static void addRitualRecipes() {
		RitualRegistry.instance().addDimensionToBookTypeAndName(0, 0, NecronomiconText.LABEL_INFORMATION_OVERWORLD_TITLE);
		RitualRegistry.instance().addDimensionToBookTypeAndName(AbyssalCraft.configDimId1, 1, NecronomiconText.LABEL_INFORMATION_ABYSSAL_WASTELAND_TITLE);
		RitualRegistry.instance().addDimensionToBookTypeAndName(AbyssalCraft.configDimId2, 2, NecronomiconText.LABEL_INFORMATION_DREADLANDS_TITLE);
		RitualRegistry.instance().addDimensionToBookTypeAndName(AbyssalCraft.configDimId3, 3, NecronomiconText.LABEL_INFORMATION_OMOTHOL_TITLE);
		RitualRegistry.instance().addDimensionToBookTypeAndName(AbyssalCraft.configDimId4, 3, NecronomiconText.LABEL_INFORMATION_DARK_REALM_TITLE);

		Object[] gk2offerings = new Object[] { new ItemStack(AbyssalCraft.Corb), new ItemStack(AbyssalCraft.PSDL), new ItemStack(AbyssalCraft.EoA) };
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("asorahGatewayKey", 1, AbyssalCraft.configDimId1, 10000F, new ItemStack(AbyssalCraft.portalPlacerDL), new ItemStack(AbyssalCraft.portalPlacer), gk2offerings));
		Object[] ocofferings = new Object[] { new ItemStack(Items.redstone), new ItemStack(AbyssalCraft.oblivionshard), new ItemStack(Items.redstone), new ItemStack(AbyssalCraft.oblivionshard), new ItemStack(Items.redstone), new ItemStack(AbyssalCraft.oblivionshard), new ItemStack(Items.redstone), new ItemStack(AbyssalCraft.oblivionshard) };
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("oblivionCatalyst", 0, 5000F, new ItemStack(AbyssalCraft.OC), new ItemStack(Items.ender_eye), ocofferings));
		Object[] tgofferings = new Object[] { new ItemStack(Items.diamond), new ItemStack(Items.blaze_powder), new ItemStack(Items.ender_pearl), new ItemStack(Items.blaze_powder), new ItemStack(Items.diamond), new ItemStack(Items.blaze_powder), new ItemStack(Items.ender_pearl), new ItemStack(Items.blaze_powder) };
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("transmutationGem", 0, 300F, new ItemStack(AbyssalCraft.Corb), new ItemStack(AbyssalCraft.Cpearl), tgofferings));
		Object[] depthsofferings = new Object[] { new ItemStack(AbyssalCraft.Coraliumcluster9), new ItemStack(AbyssalCraft.Coraliumcluster9), new ItemStack(AbyssalCraft.Cbucket), new ItemStack(Blocks.vine), new ItemStack(Blocks.waterlily), new ItemStack(AbyssalCraft.Corb), new ItemStack(AbyssalCraft.Corflesh) };
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("depthsHelmet", 1, AbyssalCraft.configDimId1, 300F, new ItemStack(AbyssalCraft.Depthshelmet), new ItemStack(AbyssalCraft.Corhelmet), depthsofferings));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("depthsChestplate", 1, AbyssalCraft.configDimId1, 300F, new ItemStack(AbyssalCraft.Depthsplate), new ItemStack(AbyssalCraft.Corplate), depthsofferings));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("depthsLeggings", 1, AbyssalCraft.configDimId1, 300F, new ItemStack(AbyssalCraft.Depthslegs), new ItemStack(AbyssalCraft.Corlegs), depthsofferings));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("depthsBoots", 1, AbyssalCraft.configDimId1, 300F, new ItemStack(AbyssalCraft.Depthsboots), new ItemStack(AbyssalCraft.Corboots), depthsofferings));
		Object[] asorahofferings = new Object[] { new ItemStack(Items.gold_ingot), new ItemStack(AbyssalCraft.Corb), new ItemStack(Items.gold_ingot), new ItemStack(AbyssalCraft.Cbucket), new ItemStack(Items.gold_ingot), new ItemStack(Blocks.enchanting_table), new ItemStack(Items.gold_ingot) };
		RitualRegistry.instance().registerRitual(new NecronomiconSummonRitual("summonAsorah", 1, AbyssalCraft.configDimId1, 1000F, EntityDragonBoss.class, asorahofferings));
		RitualRegistry.instance().registerRitual(new NecronomiconBreedingRitual());
		RitualRegistry.instance().registerRitual(new NecronomiconPurificationRitual());

		Object[] sacthothofferings = new Object[] { new ItemStack(AbyssalCraft.OC), new ItemStack(Blocks.obsidian), new ItemStack(AbyssalCraft.Cbucket), new ItemStack(Blocks.obsidian), new ItemStack(AbyssalCraft.antibucket), new ItemStack(Blocks.obsidian), new ItemStack(AbyssalCraft.relic), new ItemStack(Blocks.obsidian) };
		RitualRegistry.instance().registerRitual(new NecronomiconSummonRitual("summonSacthoth", 1, 1000F, EntitySacthoth.class, sacthothofferings));
		RitualRegistry.instance().registerRitual(new NecronomiconDreadSpawnRitual());
		Object[] coraoeofferings = new Object[] { new ItemStack(AbyssalCraft.Corflesh), new ItemStack(Items.potionitem, 1, 0), new ItemStack(AbyssalCraft.Corflesh), new ItemStack(Items.potionitem, 1, 0), new ItemStack(AbyssalCraft.Corflesh), new ItemStack(Items.potionitem, 1, 0), new ItemStack(AbyssalCraft.Corflesh), new ItemStack(Items.gunpowder) };
		RitualRegistry.instance().registerRitual(new NecronomiconPotionAoERitual("corPotionAoE", 1, 300F, AbyssalCraft.Cplague, coraoeofferings));
		Object[] dreaoeofferings = new Object[] { new ItemStack(AbyssalCraft.dreadfragment), new ItemStack(Items.potionitem, 1, 0), new ItemStack(AbyssalCraft.dreadfragment), new ItemStack(Items.potionitem, 1, 0), new ItemStack(AbyssalCraft.dreadfragment), new ItemStack(Items.potionitem, 1, 0), new ItemStack(AbyssalCraft.dreadfragment), new ItemStack(Items.gunpowder) };
		RitualRegistry.instance().registerRitual(new NecronomiconPotionAoERitual("drePotionAoE", 2, 300F, AbyssalCraft.Dplague, dreaoeofferings));
		Object[] antiaoeofferings = new Object[] { new ItemStack(AbyssalCraft.Corflesh), new ItemStack(Items.potionitem, 1, 0), new ItemStack(AbyssalCraft.Corflesh), new ItemStack(Items.potionitem, 1, 0), new ItemStack(AbyssalCraft.Corflesh), new ItemStack(Items.potionitem, 1, 0), new ItemStack(AbyssalCraft.Corflesh), new ItemStack(Items.gunpowder) };
		RitualRegistry.instance().registerRitual(new NecronomiconPotionAoERitual("antiPotionAoE", 0, 300F, AbyssalCraft.antiMatter, antiaoeofferings));
		Object[] cthulhuofferings = new Object[] { new ItemStack(AbyssalCraft.shoggothFlesh, 1, 0), new ItemStack(AbyssalCraft.shoggothFlesh, 1, 0), new ItemStack(AbyssalCraft.shoggothFlesh, 1, 0), new ItemStack(AbyssalCraft.shoggothFlesh, 1, 0), new ItemStack(AbyssalCraft.shoggothFlesh, 1, 0), new ItemStack(AbyssalCraft.essence, 1, 0), new ItemStack(AbyssalCraft.essence, 1, 1), new ItemStack(AbyssalCraft.essence, 1, 2) };
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("cthulhuStatue", 4, AbyssalCraft.configDimId3, 20000F, true, new ItemStack(AbyssalCraft.cthulhuStatue), AbyssalCraft.monolithStone, cthulhuofferings));
		Object[] hasturofferings = new Object[] { new ItemStack(AbyssalCraft.shoggothFlesh, 1, 1), new ItemStack(AbyssalCraft.shoggothFlesh, 1, 1), new ItemStack(AbyssalCraft.shoggothFlesh, 1, 1), new ItemStack(AbyssalCraft.shoggothFlesh, 1, 1), new ItemStack(AbyssalCraft.shoggothFlesh, 1, 1), new ItemStack(AbyssalCraft.essence, 1, 0), new ItemStack(AbyssalCraft.essence, 1, 1), new ItemStack(AbyssalCraft.essence, 1, 2) };
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("hasturStatue", 4, AbyssalCraft.configDimId3, 20000F, true, new ItemStack(AbyssalCraft.hasturStatue), AbyssalCraft.monolithStone, hasturofferings));
		Object[] jzaharofferings = new Object[] { AbyssalCraft.eldritchScale, AbyssalCraft.eldritchScale, AbyssalCraft.eldritchScale, AbyssalCraft.eldritchScale, AbyssalCraft.eldritchScale, new ItemStack(AbyssalCraft.essence, 1, 0), new ItemStack(AbyssalCraft.essence, 1, 1), new ItemStack(AbyssalCraft.essence, 1, 2) };
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("jzaharStatue", 4, AbyssalCraft.configDimId3, 20000F, true, new ItemStack(AbyssalCraft.jzaharStatue), AbyssalCraft.monolithStone, jzaharofferings));
		Object[] azathothofferings = new Object[] { new ItemStack(AbyssalCraft.shoggothFlesh, 1, 0), new ItemStack(AbyssalCraft.shoggothFlesh, 1, 1), new ItemStack(AbyssalCraft.shoggothFlesh, 1, 2), new ItemStack(AbyssalCraft.shoggothFlesh, 1, 3), new ItemStack(AbyssalCraft.shoggothFlesh, 1, 4), new ItemStack(AbyssalCraft.essence, 1, 0), new ItemStack(AbyssalCraft.essence, 1, 1), new ItemStack(AbyssalCraft.essence, 1, 2) };
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("azathothStatue", 4, AbyssalCraft.configDimId3, 20000F, true, new ItemStack(AbyssalCraft.azathothStatue), AbyssalCraft.monolithStone, azathothofferings));
		Object[] nyarlathotepofferings = new Object[] { new ItemStack(AbyssalCraft.shoggothFlesh, 1, 2), new ItemStack(AbyssalCraft.shoggothFlesh, 1, 2), new ItemStack(AbyssalCraft.shoggothFlesh, 1, 2), new ItemStack(AbyssalCraft.shoggothFlesh, 1, 2), new ItemStack(AbyssalCraft.shoggothFlesh, 1, 2), new ItemStack(AbyssalCraft.essence, 1, 0), new ItemStack(AbyssalCraft.essence, 1, 1), new ItemStack(AbyssalCraft.essence, 1, 2) };
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("nyarlathotepStatue", 4, AbyssalCraft.configDimId3, 20000F, true, new ItemStack(AbyssalCraft.nyarlathotepStatue), AbyssalCraft.monolithStone, nyarlathotepofferings));
		Object[] yogsothothofferings = new Object[] { new ItemStack(AbyssalCraft.shoggothFlesh, 1, 3), new ItemStack(AbyssalCraft.shoggothFlesh, 1, 3), new ItemStack(AbyssalCraft.shoggothFlesh, 1, 3), new ItemStack(AbyssalCraft.shoggothFlesh, 1, 3), new ItemStack(AbyssalCraft.shoggothFlesh, 1, 3), new ItemStack(AbyssalCraft.essence, 1, 0), new ItemStack(AbyssalCraft.essence, 1, 1), new ItemStack(AbyssalCraft.essence, 1, 2) };
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("yogsothothStatue", 4, AbyssalCraft.configDimId3, 20000F, true, new ItemStack(AbyssalCraft.yogsothothStatue), AbyssalCraft.monolithStone, yogsothothofferings));
		Object[] shubniggurathofferings = new Object[] { new ItemStack(AbyssalCraft.shoggothFlesh, 1, 4), new ItemStack(AbyssalCraft.shoggothFlesh, 1, 4), new ItemStack(AbyssalCraft.shoggothFlesh, 1, 4), new ItemStack(AbyssalCraft.shoggothFlesh, 1, 4), new ItemStack(AbyssalCraft.shoggothFlesh, 1, 4), new ItemStack(AbyssalCraft.essence, 1, 0), new ItemStack(AbyssalCraft.essence, 1, 1), new ItemStack(AbyssalCraft.essence, 1, 2) };
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("shubniggurathStatue", 4, AbyssalCraft.configDimId3, 20000F, true, new ItemStack(AbyssalCraft.shubniggurathStatue), AbyssalCraft.monolithStone, shubniggurathofferings));
		Object[] ethofferings = new Object[] { AbyssalCraft.ethaxium_brick, AbyssalCraft.ethaxium_brick, AbyssalCraft.lifeCrystal, AbyssalCraft.ethaxium_brick, AbyssalCraft.ethaxium_brick };
		RitualRegistry.instance().registerRitual(new NecronomiconCreationRitual("ethaxiumIngot", 3, AbyssalCraft.configDimId3, 1000F, new ItemStack(AbyssalCraft.ethaxiumIngot), ethofferings));
		Object[] dreadofferings = new Object[] { new ItemStack(AbyssalCraft.essence, 1, 1), AbyssalCraft.Dreadshard, AbyssalCraft.Dreadshard, AbyssalCraft.Dreadshard, AbyssalCraft.Dreadshard, AbyssalCraft.Dreadshard, AbyssalCraft.Dreadshard, AbyssalCraft.Dreadshard };
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("dreadHelmet", 2, AbyssalCraft.configDimId2, 500F, new ItemStack(AbyssalCraft.helmetD), AbyssalCraft.dreadiumhelmet, dreadofferings));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("dreadChestplate", 2, AbyssalCraft.configDimId2, 500F, new ItemStack(AbyssalCraft.plateD), AbyssalCraft.dreadiumplate, dreadofferings));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("dreadLeggings", 2, AbyssalCraft.configDimId2, 500F, new ItemStack(AbyssalCraft.legsD), AbyssalCraft.dreadiumlegs, dreadofferings));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("dreadBoots", 2, AbyssalCraft.configDimId2, 500F, new ItemStack(AbyssalCraft.bootsD), AbyssalCraft.dreadiumboots, dreadofferings));
		Object[] rcoffers = new Object[] { AbyssalCraft.shadowfragment, Items.arrow, AbyssalCraft.shadowfragment, Items.arrow, AbyssalCraft.shadowfragment, Items.arrow, AbyssalCraft.shadowfragment, Items.arrow };
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("rangeCharm", 0, 100F, new ItemStack(AbyssalCraft.charm, 1, 1), new ItemStack(AbyssalCraft.charm, 1, 0), rcoffers));
		Object[] dcoffers = new Object[] { AbyssalCraft.shadowfragment, Items.redstone, AbyssalCraft.shadowfragment, Items.redstone, AbyssalCraft.shadowfragment, Items.redstone, AbyssalCraft.shadowfragment, Items.redstone };
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("durationCharm", 0, 100F, new ItemStack(AbyssalCraft.charm, 1, 2), new ItemStack(AbyssalCraft.charm, 1, 0), dcoffers));
		Object[] pcoffers = new Object[] { AbyssalCraft.shadowfragment, Items.glowstone_dust, AbyssalCraft.shadowfragment, Items.glowstone_dust, AbyssalCraft.shadowfragment, Items.glowstone_dust, AbyssalCraft.shadowfragment, Items.glowstone_dust };
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("powerCharm", 0, 100F, new ItemStack(AbyssalCraft.charm, 1, 3), new ItemStack(AbyssalCraft.charm, 1, 0), pcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconRespawnJzaharRitual());
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("crangeCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(AbyssalCraft.cthulhuCharm, 1, 1), new ItemStack(AbyssalCraft.cthulhuCharm, 1, 0), rcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("cdurationCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(AbyssalCraft.cthulhuCharm, 1, 2), new ItemStack(AbyssalCraft.cthulhuCharm, 1, 0), dcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("cpowerCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(AbyssalCraft.cthulhuCharm, 1, 3), new ItemStack(AbyssalCraft.cthulhuCharm, 1, 0), pcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("hrangeCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(AbyssalCraft.hasturCharm, 1, 1), new ItemStack(AbyssalCraft.hasturCharm, 1, 0), rcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("hdurationCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(AbyssalCraft.hasturCharm, 1, 2), new ItemStack(AbyssalCraft.hasturCharm, 1, 0), dcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("hpowerCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(AbyssalCraft.hasturCharm, 1, 3), new ItemStack(AbyssalCraft.hasturCharm, 1, 0), pcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("jrangeCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(AbyssalCraft.jzaharCharm, 1, 1), new ItemStack(AbyssalCraft.jzaharCharm, 1, 0), rcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("jdurationCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(AbyssalCraft.jzaharCharm, 1, 2), new ItemStack(AbyssalCraft.jzaharCharm, 1, 0), dcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("jpowerCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(AbyssalCraft.jzaharCharm, 1, 3), new ItemStack(AbyssalCraft.jzaharCharm, 1, 0), pcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("arangeCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(AbyssalCraft.azathothCharm, 1, 1), new ItemStack(AbyssalCraft.azathothCharm, 1, 0), rcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("adurationCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(AbyssalCraft.azathothCharm, 1, 2), new ItemStack(AbyssalCraft.azathothCharm, 1, 0), dcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("apowerCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(AbyssalCraft.azathothCharm, 1, 3), new ItemStack(AbyssalCraft.azathothCharm, 1, 0), pcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("nrangeCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(AbyssalCraft.nyarlathotepCharm, 1, 1), new ItemStack(AbyssalCraft.nyarlathotepCharm, 1, 0), rcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("ndurationCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(AbyssalCraft.nyarlathotepCharm, 1, 2), new ItemStack(AbyssalCraft.nyarlathotepCharm, 1, 0), dcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("npowerCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(AbyssalCraft.nyarlathotepCharm, 1, 3), new ItemStack(AbyssalCraft.nyarlathotepCharm, 1, 0), pcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("yrangeCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(AbyssalCraft.yogsothothCharm, 1, 1), new ItemStack(AbyssalCraft.yogsothothCharm, 1, 0), rcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("ydurationCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(AbyssalCraft.yogsothothCharm, 1, 2), new ItemStack(AbyssalCraft.yogsothothCharm, 1, 0), dcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("ypowerCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(AbyssalCraft.yogsothothCharm, 1, 3), new ItemStack(AbyssalCraft.yogsothothCharm, 1, 0), pcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("srangeCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(AbyssalCraft.shubniggurathCharm, 1, 1), new ItemStack(AbyssalCraft.shubniggurathCharm, 1, 0), rcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("sdurationCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(AbyssalCraft.shubniggurathCharm, 1, 2), new ItemStack(AbyssalCraft.shubniggurathCharm, 1, 0), dcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("spowerCharm", 3, AbyssalCraft.configDimId3, 200F, new ItemStack(AbyssalCraft.shubniggurathCharm, 1, 3), new ItemStack(AbyssalCraft.shubniggurathCharm, 1, 0), pcoffers));
		Object[] ccoffers = new Object[] { AbyssalCraft.cthulhuCoin, AbyssalCraft.cthulhuCoin, AbyssalCraft.cthulhuCoin, AbyssalCraft.cthulhuCoin, AbyssalCraft.cthulhuCoin, AbyssalCraft.cthulhuCoin, AbyssalCraft.cthulhuCoin, AbyssalCraft.cthulhuCoin };
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("cthulhuCharm", 4, 2000F, new ItemStack(AbyssalCraft.cthulhuCharm, 1, 0), new ItemStack(AbyssalCraft.charm, 1, 0), ccoffers));
		Object[] hcoffers = new Object[] { AbyssalCraft.hasturCoin, AbyssalCraft.hasturCoin, AbyssalCraft.hasturCoin, AbyssalCraft.hasturCoin, AbyssalCraft.hasturCoin, AbyssalCraft.hasturCoin, AbyssalCraft.hasturCoin, AbyssalCraft.hasturCoin };
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("hasturCharm", 4, 2000F, new ItemStack(AbyssalCraft.hasturCharm, 1, 0), new ItemStack(AbyssalCraft.charm, 1, 0), hcoffers));
		Object[] jcoffers = new Object[] { AbyssalCraft.jzaharCoin, AbyssalCraft.jzaharCoin, AbyssalCraft.jzaharCoin, AbyssalCraft.jzaharCoin, AbyssalCraft.jzaharCoin, AbyssalCraft.jzaharCoin, AbyssalCraft.jzaharCoin, AbyssalCraft.jzaharCoin };
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("jzaharCharm", 4, 2000F, new ItemStack(AbyssalCraft.jzaharCharm, 1, 0), new ItemStack(AbyssalCraft.charm, 1, 0), jcoffers));
		Object[] acoffers = new Object[] { AbyssalCraft.azathothCoin, AbyssalCraft.azathothCoin, AbyssalCraft.azathothCoin, AbyssalCraft.azathothCoin, AbyssalCraft.azathothCoin, AbyssalCraft.azathothCoin, AbyssalCraft.azathothCoin, AbyssalCraft.azathothCoin };
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("azathothCharm", 4, 2000F, new ItemStack(AbyssalCraft.azathothCharm, 1, 0), new ItemStack(AbyssalCraft.charm, 1, 0), acoffers));
		Object[] ncoffers = new Object[] { AbyssalCraft.nyarlathotepCoin, AbyssalCraft.nyarlathotepCoin, AbyssalCraft.nyarlathotepCoin, AbyssalCraft.nyarlathotepCoin, AbyssalCraft.nyarlathotepCoin, AbyssalCraft.nyarlathotepCoin, AbyssalCraft.nyarlathotepCoin, AbyssalCraft.nyarlathotepCoin };
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("nyarlathotepCharm", 4, 2000F, new ItemStack(AbyssalCraft.nyarlathotepCharm, 1, 0), new ItemStack(AbyssalCraft.charm, 1, 0), ncoffers));
		Object[] ycoffers = new Object[] { AbyssalCraft.yogsothothCoin, AbyssalCraft.yogsothothCoin, AbyssalCraft.yogsothothCoin, AbyssalCraft.yogsothothCoin, AbyssalCraft.yogsothothCoin, AbyssalCraft.yogsothothCoin, AbyssalCraft.yogsothothCoin, AbyssalCraft.yogsothothCoin };
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("yogsothothCharm", 4, 2000F, new ItemStack(AbyssalCraft.yogsothothCharm, 1, 0), new ItemStack(AbyssalCraft.charm, 1, 0), ycoffers));
		Object[] scoffers = new Object[] { AbyssalCraft.shubniggurathCoin, AbyssalCraft.shubniggurathCoin, AbyssalCraft.shubniggurathCoin, AbyssalCraft.shubniggurathCoin, AbyssalCraft.shubniggurathCoin, AbyssalCraft.shubniggurathCoin, AbyssalCraft.shubniggurathCoin, AbyssalCraft.shubniggurathCoin };
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("shubniggurathCharm", 4, 2000F, new ItemStack(AbyssalCraft.shubniggurathCharm, 1, 0), new ItemStack(AbyssalCraft.charm, 1, 0), scoffers));
		Object[] owoffers = new Object[] { AbyssalCraft.shadowshard, Blocks.cobblestone, AbyssalCraft.Coralium, AbyssalCraft.Darkstone_cobble, AbyssalCraft.shadowshard, Blocks.cobblestone, AbyssalCraft.Coralium, AbyssalCraft.Darkstone_cobble };
		Object[] awoffers = new Object[] { AbyssalCraft.shadowshard, AbyssalCraft.abybrick, AbyssalCraft.Coralium, AbyssalCraft.cstonebrick, AbyssalCraft.shadowshard, AbyssalCraft.abybrick, AbyssalCraft.Coralium, AbyssalCraft.cstonebrick };
		Object[] dloffers = new Object[] { AbyssalCraft.shadowshard, AbyssalCraft.dreadbrick, AbyssalCraft.Coralium, AbyssalCraft.abydreadbrick, AbyssalCraft.shadowshard, AbyssalCraft.dreadbrick, AbyssalCraft.Coralium, AbyssalCraft.abydreadbrick };
		Object[] omtoffers = new Object[] { AbyssalCraft.shadowshard, new ItemStack(AbyssalCraft.ethaxiumbrick, 1, 0), AbyssalCraft.Coralium, new ItemStack(AbyssalCraft.darkethaxiumbrick, 1, 0), AbyssalCraft.shadowshard, new ItemStack(AbyssalCraft.ethaxiumbrick, 1, 0), AbyssalCraft.Coralium, new ItemStack(AbyssalCraft.darkethaxiumbrick, 1, 0) };
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("epOWupgrade", 0, 400F, new ItemStack(AbyssalCraft.tieredEnergyPedestal, 1, 0), AbyssalCraft.energyPedestal, owoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("epAWupgrade", 1, 800F, new ItemStack(AbyssalCraft.tieredEnergyPedestal, 1, 1), new ItemStack(AbyssalCraft.tieredEnergyPedestal, 1, 0), awoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("epDLupgrade", 2, 1200F, new ItemStack(AbyssalCraft.tieredEnergyPedestal, 1, 2), new ItemStack(AbyssalCraft.tieredEnergyPedestal, 1, 1), dloffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("epOMTupgrade", 3, 1600F, new ItemStack(AbyssalCraft.tieredEnergyPedestal, 1, 3), new ItemStack(AbyssalCraft.tieredEnergyPedestal, 1, 2), omtoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("saOWupgrade", 0, 400F, new ItemStack(AbyssalCraft.tieredSacrificialAltar, 1, 0), AbyssalCraft.sacrificialAltar, owoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("saAWupgrade", 1, 800F, new ItemStack(AbyssalCraft.tieredSacrificialAltar, 1, 1), new ItemStack(AbyssalCraft.tieredSacrificialAltar, 1, 0), awoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("saDLupgrade", 2, 1200F, new ItemStack(AbyssalCraft.tieredSacrificialAltar, 1, 2), new ItemStack(AbyssalCraft.tieredSacrificialAltar, 1, 1), dloffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("saOMTupgrade", 3, 1600F, new ItemStack(AbyssalCraft.tieredSacrificialAltar, 1, 3), new ItemStack(AbyssalCraft.tieredSacrificialAltar, 1, 2), omtoffers));

		// Dreadium Katana Hilt
		final Object[] hiltoffers = new Object[] {new ItemStack(AbyssalCraft.dreadcloth), new ItemStack(AbyssalCraft.dreadiumingot), new ItemStack(AbyssalCraft.dreadiumingot), new ItemStack(AbyssalCraft.dreadiumingot), new ItemStack(AbyssalCraft.dreadcloth), new ItemStack(AbyssalCraft.dreadcloth), new ItemStack(AbyssalCraft.essence, 1, 1), new ItemStack(AbyssalCraft.dreadcloth), new ItemStack(AbyssalCraft.dreadcloth), new ItemStack(AbyssalCraft.dreadiumingot)};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("dreadHilt", 2, AbyssalCraft.configDimId2, 150F, false, new ItemStack(AbyssalCraft.dreadhilt), new ItemStack(AbyssalCraft.dreadlog), hiltoffers));
		
		// Anti-Jzhar Amulet
		final Object[] pendantOfferings = new Object[] { new ItemStack(AbyssalCraft.essence, 1, 1), new ItemStack(AbyssalCraft.essence, 1, 2), AbyssalCraft.ethaxium_brick, AbyssalCraft.eldritchScale, AbyssalCraft.ethaxiumIngot, AbyssalCraft.eldritchScale, AbyssalCraft.ethaxium_brick, new ItemStack(AbyssalCraft.essence, 1, 0) };
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("jzaharAmulet", 3, AbyssalCraft.configDimId3, 666F, false, new ItemStack(AbyssalCraft.trapezohedron), AbyssalCraft.Cpearl, pendantOfferings));
		
		// Monster Drain Staff
		final Object[] rendingMonsterOfferings = new Object[] { AbyssalCraft.dreadfragment, AbyssalCraft.dreadfragment, AbyssalCraft.Corflesh, AbyssalCraft.Corflesh, AbyssalCraft.shadowgem, AbyssalCraft.Corflesh, AbyssalCraft.Corflesh, AbyssalCraft.dreadfragment };
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("drainStaffMonster", 2, AbyssalCraft.configDimId1, 1500F, false, new ItemStack(AbyssalCraft.drainStaffMonster), AbyssalCraft.drainStaff, rendingMonsterOfferings));
		// Beast Drain Staff
		final Object[] rendingBeastofferings = new Object[] { AbyssalCraft.dreadfragment, AbyssalCraft.dreadfragment, AbyssalCraft.omotholFlesh, AbyssalCraft.omotholFlesh, AbyssalCraft.shadowgem, AbyssalCraft.omotholFlesh, AbyssalCraft.omotholFlesh, AbyssalCraft.dreadfragment };
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("drainStaffBeast", 3, AbyssalCraft.configDimId2, 3000F, false, new ItemStack(AbyssalCraft.drainStaffBeast), AbyssalCraft.drainStaffMonster, rendingBeastofferings));
		// Jzahar Crozier
		final Object[] staffofferings = new Object[] { new ItemStack(AbyssalCraft.essence, 1, 1), new ItemStack(AbyssalCraft.essence, 1, 2), AbyssalCraft.eldritchScale, AbyssalCraft.ethaxiumIngot, AbyssalCraft.portalPlacerJzh, AbyssalCraft.ethaxiumIngot, AbyssalCraft.eldritchScale, new ItemStack(AbyssalCraft.essence, 1, 0) };
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("jzaharStaff", 4, AbyssalCraft.configDimId3, 50000F, true, new ItemStack(AbyssalCraft.Staff), AbyssalCraft.drainStaffBeast, staffofferings));
	}

	@SuppressWarnings("unchecked")
	private static void addDisruptions() {
		DisruptionHandler.instance().registerDisruption(new DisruptionLightning());
		DisruptionHandler.instance().registerDisruption(new DisruptionFire());
		DisruptionHandler.instance().registerDisruption(new DisruptionSpawn("spawnShoggoth", null, EntityLesserShoggoth.class));
		DisruptionHandler.instance().registerDisruption(new DisruptionSpawn("spawnGatekeeperMinion", DeityType.JZAHAR, EntityGatekeeperMinion.class));
		DisruptionHandler.instance().registerDisruption(new DisruptionPotion("poisonPotion", null, Potion.poison));
		DisruptionHandler.instance().registerDisruption(new DisruptionPotion("slownessPotion", null, Potion.moveSlowdown));
		DisruptionHandler.instance().registerDisruption(new DisruptionPotion("weaknessPotion", null, Potion.weakness));
		DisruptionHandler.instance().registerDisruption(new DisruptionPotion("witherPotion", null, Potion.wither));
		DisruptionHandler.instance().registerDisruption(new DisruptionPotion("coraliumPotion", null, AbyssalCraft.Cplague));
		DisruptionHandler.instance().registerDisruption(new DisruptionPotentialEnergy());
		DisruptionHandler.instance().registerDisruption(new DisruptionFreeze());
		DisruptionHandler.instance().registerDisruption(new DisruptionSwarm("swarmShadow", null, EntityShadowCreature.class, EntityShadowMonster.class, EntityShadowBeast.class));
		DisruptionHandler.instance().registerDisruption(new DisruptionSwarm("swarmHeroes", null, EntityFallenHero.class));
		DisruptionHandler.instance().registerDisruption(new DisruptionFireRain());
		DisruptionHandler.instance().registerDisruption(new DisruptionTaintRain());
		DisruptionHandler.instance().registerDisruption(new DisruptionDisplaceEntities());
		DisruptionHandler.instance().registerDisruption(new DisruptionTeleportRandomly());
		DisruptionHandler.instance().registerDisruption(new DisruptionOoze());
		DisruptionHandler.instance().registerDisruption(new DisruptionBiomeSwarm("swarmBiome", DeityType.JZAHAR, BiomeGenBase.hell));
	}

	private static void addArmor(Item helmet, Item chestplate, Item pants, Item boots, Item material, Item upgrade, Item oldh, Item oldc, Item oldp, Item oldb) {
		GameRegistry.addRecipe(new ItemStack(helmet), new Object[] { "###", "# #", '#', material });
		GameRegistry.addRecipe(new ItemStack(chestplate), new Object[] { "# #", "###", "###", '#', material });
		GameRegistry.addRecipe(new ItemStack(pants), new Object[] { "###", "# #", "# #", '#', material });
		GameRegistry.addRecipe(new ItemStack(boots), new Object[] { "# #", "# #", '#', material });

		GameRegistry.addSmelting(helmet, new ItemStack(material), 1F);
		GameRegistry.addSmelting(chestplate, new ItemStack(material), 1F);
		GameRegistry.addSmelting(pants, new ItemStack(material), 1F);
		GameRegistry.addSmelting(boots, new ItemStack(material), 1F);
	}
}
