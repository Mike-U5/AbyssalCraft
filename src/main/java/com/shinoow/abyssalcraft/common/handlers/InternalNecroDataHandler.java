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

import java.util.List;

import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.google.common.collect.Lists;
import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.internal.DummyNecroDataHandler;
import com.shinoow.abyssalcraft.api.necronomicon.CraftingStack;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData.Chapter;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData.Page;
import com.shinoow.abyssalcraft.client.lib.NecronomiconResources;
import com.shinoow.abyssalcraft.client.lib.NecronomiconText;

public class InternalNecroDataHandler extends DummyNecroDataHandler {

	private final List<NecroData> internalNecroData = Lists.newArrayList();

	public InternalNecroDataHandler(){
		internalNecroData.add(new NecroData("greatoldones", NecronomiconText.LABEL_INFORMATION_GREAT_OLD_ONES, NecronomiconText.INFORMATION_GREAT_OLD_ONES,
			new Chapter("outergods", NecronomiconText.LABEL_OUTER_GODS),
			new Chapter("greatoldones", NecronomiconText.LABEL_INFORMATION_GREAT_OLD_ONES)
		));
		internalNecroData.add(new NecroData("overworld", NecronomiconText.LABEL_INFORMATION_OVERWORLD_TITLE, NecronomiconText.INFORMATION_OVERWORLD,
			new Chapter("materials", NecronomiconText.LABEL_INFORMATION_MATERIALS),
			new Chapter("progression", NecronomiconText.LABEL_INFORMATION_PROGRESSION),
			new Chapter("entities", NecronomiconText.LABEL_INFORMATION_ENTITIES),
			new Chapter("specialmaterials", NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS),
			new Chapter("armortools", NecronomiconText.LABEL_INFORMATION_ARMOR_TOOLS)
		));
		internalNecroData.add(new NecroData("abyssalwasteland", NecronomiconText.LABEL_INFORMATION_ABYSSAL_WASTELAND_TITLE, NecronomiconText.INFORMATION_ABYSSAL_WASTELAND,
			new Chapter("materials", NecronomiconText.LABEL_INFORMATION_MATERIALS),
			new Chapter("progression", NecronomiconText.LABEL_INFORMATION_PROGRESSION),
			new Chapter("entities", NecronomiconText.LABEL_INFORMATION_ENTITIES),
			new Chapter("specialmaterials", NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS),
			new Chapter("armortools", NecronomiconText.LABEL_INFORMATION_ARMOR_TOOLS)
		));
		internalNecroData.add(new NecroData("dreadlands", NecronomiconText.LABEL_INFORMATION_DREADLANDS_TITLE, NecronomiconText.INFORMATION_DREADLANDS,
			new Chapter("materials", NecronomiconText.LABEL_INFORMATION_MATERIALS),
			new Chapter("progression", NecronomiconText.LABEL_INFORMATION_PROGRESSION),
			new Chapter("entities", NecronomiconText.LABEL_INFORMATION_ENTITIES),
			new Chapter("specialmaterials", NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS),
			new Chapter("armortools", NecronomiconText.LABEL_INFORMATION_ARMOR_TOOLS)
		));
		internalNecroData.add(new NecroData("omothol", NecronomiconText.LABEL_INFORMATION_OMOTHOL_TITLE, NecronomiconText.INFORMATION_OMOTHOL,
			new Chapter("materials", NecronomiconText.LABEL_INFORMATION_MATERIALS),
			new Chapter("progression", NecronomiconText.LABEL_INFORMATION_PROGRESSION),
			new Chapter("entities", NecronomiconText.LABEL_INFORMATION_ENTITIES),
			new Chapter("specialmaterials", NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS)
		));
		internalNecroData.add(new NecroData("darkrealm", NecronomiconText.LABEL_INFORMATION_DARK_REALM_TITLE, NecronomiconText.INFORMATION_DARK_REALM, 
			new Chapter("entities", NecronomiconText.LABEL_INFORMATION_ENTITIES)
		));
		internalNecroData.add(new NecroData("rituals", NecronomiconText.LABEL_INFO, 
			new Chapter("gettingstarted", NecronomiconText.LABEL_GETTING_STARTED),
			new Chapter("materials", NecronomiconText.LABEL_INFORMATION_MATERIALS),
			new Chapter("specialmaterials", NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS),
			new Chapter("potentialenergy", NecronomiconText.LABEL_POTENTIAL_ENERGY)
		));
		internalNecroData.add(new NecroData("miscinfo", NecronomiconText.LABEL_MISC_INFORMATION, NecronomiconText.MISC_INFORMATION,
			new Chapter("specialmaterials", NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS),
			new Chapter("enchantments", NecronomiconText.LABEL_INFORMATION_ENCHANTMENTS)
		));
	}

	@Override
	public NecroData getInternalNecroData(String identifier) {
		for(NecroData data : internalNecroData) {
			if(data.getIdentifier().equals(identifier)) {
				return data;
			}
		}
		return null;
	}

	@Override
	public void addChapter(Chapter chapter, String identifier) {
		for(NecroData data : internalNecroData) {
			if(data.getIdentifier().equals(identifier)){
				data.addChapter(chapter);
				return;
			}
		}
	}

	@Override
	public void removeChapter(String necroidentifier, String chapteridentifier) {
		for(NecroData data : internalNecroData) {
			if(data.getIdentifier().equals(necroidentifier)) {
				data.removeChapter(chapteridentifier);
			}
		}
	}

	@Override
	public void addPage(Page page, String necroidentifier, String chapteridentifier) {
		for(NecroData data : internalNecroData) {
			if(data.getIdentifier().equals(necroidentifier)) {
				for(Chapter chapter : data.getChapters()) {
					if(chapter.getIdentifier().equals(chapteridentifier)) {
						chapter.addPage(page);
					}
				}
			}
		}
	}

	@Override
	public void removePage(int pageNum, String necroidentifier, String chapteridentifier) {
		for(NecroData data : internalNecroData) {
			if(data.getIdentifier().equals(necroidentifier)) {
				for(Chapter chapter : data.getChapters()) {
					if(chapter.getIdentifier().equals(chapteridentifier)) {
						chapter.removePage(pageNum);
					}
				}
			}
		}
	}

	private void addPages(String necroidentifier, String chapteridentifier, Page...pages){
		for(Page page : pages) {
			addPage(page, necroidentifier, chapteridentifier);
		}
	}

	@Override
	public void registerInternalPages() {
		addPages("greatoldones", "outergods", 
			new Page(1, NecronomiconResources.AZATHOTH_SEAL, NecronomiconText.AZATHOTH_1),
			new Page(2, NecronomiconText.AZATHOTH_2),
			new Page(3, NecronomiconResources.NYARLATHOTEP_SEAL, NecronomiconText.NYARLATHOTEP_1),
			new Page(4, NecronomiconText.NYARLATHOTEP_2),
			new Page(5, NecronomiconResources.YOG_SOTHOTH_SEAL, NecronomiconText.YOG_SOTHOTH_1),
			new Page(6, NecronomiconText.YOG_SOTHOTH_2),
			new Page(7, NecronomiconResources.SHUB_NIGGURATH_SEAL, NecronomiconText.SHUB_NIGGURATH_1),
			new Page(8, NecronomiconText.SHUB_NIGGURATH_2)
		);
		addPages("greatoldones", "greatoldones", 
			new Page(1, NecronomiconResources.CTHULHU_SEAL, NecronomiconText.CTHULHU_1),
			new Page(2, NecronomiconText.CTHULHU_2),
			new Page(3, NecronomiconResources.HASTUR_SEAL, NecronomiconText.HASTUR_1),
			new Page(4, NecronomiconText.HASTUR_2),
			new Page(5, NecronomiconResources.JZAHAR_SEAL, NecronomiconText.JZAHAR_1),
			new Page(6, NecronomiconText.JZAHAR_2)
		);
		addPages("overworld", "materials", 
			//new Page(1, new ItemStack(AbyssalCraft.abyore), NecronomiconText.MATERIAL_ABYSSALNITE_1),
			//new Page(2, NecronomiconText.MATERIAL_ABYSSALNITE_2),
			//new Page(3, new ItemStack(AbyssalCraft.Darkstone), NecronomiconText.MATERIAL_DARKSTONE_1),
			//new Page(4, NecronomiconText.MATERIAL_DARKSTONE_2),
			new Page(1, new ItemStack(AbyssalCraft.Coraliumore), NecronomiconText.MATERIAL_CORALIUM_1),
			new Page(2, NecronomiconText.MATERIAL_CORALIUM_2)
			//new Page(7, new ItemStack(AbyssalCraft.DLTSapling), NecronomiconText.MATERIAL_DARKLANDS_OAK_1),
			//new Page(8, NecronomiconText.MATERIAL_DARKLANDS_OAK_2),
			//new Page(9, new ItemStack(AbyssalCraft.nitreOre), NecronomiconText.MATERIAL_NITRE_1),
			//new Page(10, NecronomiconText.MATERIAL_NITRE_2),
			//new Page(13, new ItemStack(AbyssalCraft.Darkgrass), NecronomiconText.MATERIAL_DARKLANDS_GRASS_1),
			//new Page(14, NecronomiconText.MATERIAL_DARKLANDS_GRASS_2));
			//new Page(5, new ItemStack(AbyssalCraft.anticwater), NecronomiconText.MATERIAL_LIQUID_ANTIMATTER_1),
			//new Page(6, NecronomiconText.MATERIAL_LIQUID_ANTIMATTER_2)
		);
		addPages("overworld", "progression", 
			new Page(1, NecronomiconText.PROGRESSION_OVERWORLD_1),
			new Page(2, NecronomiconText.PROGRESSION_OVERWORLD_2),
			new Page(3, NecronomiconText.PROGRESSION_OVERWORLD_3),
			new Page(4, NecronomiconText.PROGRESSION_OVERWORLD_4),
			new Page(5, NecronomiconText.PROGRESSION_OVERWORLD_5)
		);
		addPages("overworld", "entities", 
			new Page(1, NecronomiconResources.ABYSSAL_ZOMBIE, NecronomiconText.ENTITY_ABYSSAL_ZOMBIE_1),
			new Page(2, NecronomiconText.ENTITY_ABYSSAL_ZOMBIE_2),
			new Page(3, NecronomiconResources.DEPTHS_GHOUL, NecronomiconText.ENTITY_DEPTHS_GHOUL_1),
			new Page(4, NecronomiconText.ENTITY_DEPTHS_GHOUL_2),
			new Page(5, NecronomiconResources.SHADOW_CREATURE, NecronomiconText.ENTITY_SHADOW_CREATURE_1),
			new Page(6, NecronomiconText.ENTITY_SHADOW_CREATURE_2),
			new Page(7, NecronomiconResources.SHADOW_MONSTER, NecronomiconText.ENTITY_SHADOW_MONSTER_1),
			new Page(8, NecronomiconText.ENTITY_SHADOW_MONSTER_2),
			new Page(9, NecronomiconResources.SHADOW_BEAST, NecronomiconText.ENTITY_SHADOW_BEAST_1),
			new Page(10, NecronomiconText.ENTITY_SHADOW_BEAST_2),
			new Page(11, NecronomiconResources.ANTI_ENTITIES, NecronomiconText.ENTITY_ANTI_1),
			new Page(12, NecronomiconText.ENTITY_ANTI_2),
			new Page(13, NecronomiconResources.DAOLOTH, NecronomiconText.DAOLOTH_1),
			new Page(14, NecronomiconText.DAOLOTH_2),
			new Page(15, NecronomiconResources.LESSER_SHOGGOTH, NecronomiconText.ENTITY_LESSER_SHOGGOTH_1),
			new Page(16, NecronomiconText.ENTITY_LESSER_SHOGGOTH_2)
		);
		addPages("overworld", "specialmaterials", 
			new Page(1, new CraftingStack(AbyssalCraft.energyPedestal), NecronomiconText.CRAFTING_ENERGY_PEDESTAL_1),
			new Page(2, NecronomiconText.CRAFTING_ENERGY_PEDESTAL_2),
			new Page(3, new CraftingStack(AbyssalCraft.sacrificialAltar), NecronomiconText.CRAFTING_SACRIFICIAL_ALTAR_1),
			new Page(4, NecronomiconText.CRAFTING_SACRIFICIAL_ALTAR_2),
			///new Page(1, new CraftingStack(AbyssalCraft.CoraliumInfusedStone, Blocks.stone, Blocks.stone, Blocks.stone, AbyssalCraft.Coraliumcluster3, AbyssalCraft.Coraliumcluster3, AbyssalCraft.Coraliumcluster3, Blocks.stone, Blocks.stone, Blocks.stone), NecronomiconText.CRAFTING_CORALIUM_INFUSED_STONE_1),
			///new Page(2, NecronomiconText.CRAFTING_CORALIUM_INFUSED_STONE_2),
			new Page(5, new CraftingStack(AbyssalCraft.shadowgem), NecronomiconText.CRAFTING_SHADOW_GEM_1),
			new Page(6, NecronomiconText.CRAFTING_SHADOW_GEM_2),
			new Page(7, new CraftingStack(AbyssalCraft.oblivionshard), NecronomiconText.CRAFTING_SHARD_OF_OBLIVION),
			new Page(8, new CraftingStack(AbyssalCraft.portalPlacer), NecronomiconText.CRAFTING_GATEWAY_KEY),
			new Page(9, new CraftingStack(new ItemStack(AbyssalCraft.skin, 1, 0)), NecronomiconText.CRAFTING_SKIN_OF_THE_ABYSSAL_WASTELAND_1),
			new Page(10, new CraftingStack(AbyssalCraft.necronomicon_cor), NecronomiconText.CRAFTING_NECRONOMICON_C)
		);
		addPages("overworld", "armortools", 
			new Page(1, new CraftingStack(AbyssalCraft.drainStaff), NecronomiconText.CRAFTING_STAFF_OF_RENDING_1),
			new Page(2, NecronomiconText.CRAFTING_STAFF_OF_RENDING_2)
		);
		addPages("overworld", "rituals", 
			new Page(1, new CraftingStack(AbyssalCraft.drainStaff), NecronomiconText.CRAFTING_STAFF_OF_RENDING_1),
			new Page(2, NecronomiconText.CRAFTING_STAFF_OF_RENDING_2)
		);
		addPages("abyssalwasteland", "materials", 
			new Page(1, new ItemStack(AbyssalCraft.abystone), NecronomiconText.MATERIAL_ABYSSAL_STONE_1),
			new Page(2, NecronomiconText.MATERIAL_ABYSSAL_STONE_2),
			new Page(3, new ItemStack(AbyssalCraft.AbyDiaOre), NecronomiconText.MATERIAL_ABYSSAL_ORES_1),
			new Page(4, NecronomiconText.MATERIAL_ABYSSAL_ORES_2),
			new Page(5, new ItemStack(AbyssalCraft.AbyCorOre), NecronomiconText.MATERIAL_ABYSSAL_CORALIUM_1),
			new Page(6, NecronomiconText.MATERIAL_ABYSSAL_CORALIUM_2),
			new Page(7, new ItemStack(AbyssalCraft.AbyLCorOre), NecronomiconText.MATERIAL_LIQUIFIED_CORALIUM_1),
			new Page(8, NecronomiconText.MATERIAL_LIQUIFIED_CORALIUM_2),
			new Page(9, new ItemStack(AbyssalCraft.AbyPCorOre), NecronomiconText.MATERIAL_PEARLESCENT_CORALIUM_1),
			new Page(10, NecronomiconText.MATERIAL_PEARLESCENT_CORALIUM_2),
			new Page(11, new ItemStack(AbyssalCraft.Cwater), NecronomiconText.MATERIAL_LIQUID_CORALIUM_1),
			new Page(12, NecronomiconText.MATERIAL_LIQUID_CORALIUM_2),
			new Page(13, new ItemStack(AbyssalCraft.PSDL), NecronomiconText.MATERIAL_DREADLANDS_INFUSED_POWERSTONE_1),
			new Page(14, NecronomiconText.MATERIAL_DREADLANDS_INFUSED_POWERSTONE_2)
		);
		addPages("abyssalwasteland", "progression", 
			new Page(1, NecronomiconText.PROGRESSION_ABYSSAL_1),
			new Page(2, NecronomiconText.PROGRESSION_ABYSSAL_2),
			new Page(3, NecronomiconText.PROGRESSION_ABYSSAL_3)
		);
		addPages("abyssalwasteland", "entities", 
			new Page(1, NecronomiconResources.ABYSSAL_ZOMBIE, NecronomiconText.ENTITY_ABYSSAL_ZOMBIE_1),
			new Page(2, NecronomiconText.ENTITY_ABYSSAL_ZOMBIE_2),
			new Page(3, NecronomiconResources.DEPTHS_GHOUL, NecronomiconText.ENTITY_DEPTHS_GHOUL_1),
			new Page(4, NecronomiconText.ENTITY_DEPTHS_GHOUL_2),
			new Page(5, NecronomiconResources.SKELETON_GOLIATH, NecronomiconText.ENTITY_SKELETON_GOLIATH_1),
			new Page(6, NecronomiconText.ENTITY_SKELETON_GOLIATH_2),
			//new Page(7, NecronomiconResources.SPECTRAL_DRAGON, NecronomiconText.ENTITY_SPECTRAL_DRAGON_1),
			//new Page(8, NecronomiconText.ENTITY_SPECTRAL_DRAGON_2),
			new Page(7, NecronomiconResources.ASORAH, NecronomiconText.ENTITY_ASORAH_1),
			new Page(8, NecronomiconText.ENTITY_ASORAH_2),
			new Page(9, NecronomiconResources.LESSER_SHOGGOTH, NecronomiconText.ENTITY_LESSER_SHOGGOTH_1),
			new Page(10, NecronomiconText.ENTITY_LESSER_SHOGGOTH_2)
		);
		addPages("abyssalwasteland", "specialmaterials", 
			new Page(1, new CraftingStack(new ItemStack(AbyssalCraft.PSDLfinder, 4)), NecronomiconText.CRAFTING_POWERSTONE_TRACKER),
			new Page(2, new CraftingStack(AbyssalCraft.Cchunk), NecronomiconText.CRAFTING_CORALIUM_CHUNK),
			new Page(3, new CraftingStack(AbyssalCraft.Cplate), NecronomiconText.CRAFTING_CORALIUM_PLATE),
			new Page(4, new CraftingStack(new ItemStack(AbyssalCraft.skin, 1, 1)), NecronomiconText.CRAFTING_SKIN_OF_THE_DREADLANDS_1),
			new Page(5, new CraftingStack(AbyssalCraft.necronomicon_dre), NecronomiconText.CRAFTING_NECRONOMICON_D)
		);
		addPages("abyssalwasteland", "armortools", 
			new Page(1, new CraftingStack(AbyssalCraft.CorhelmetP), NecronomiconText.CRAFTING_PLATED_CORALIUM_HELMET),
			new Page(2, new CraftingStack(AbyssalCraft.CorplateP), NecronomiconText.CRAFTING_PLATED_CORALIUM_CHESTPLATE),
			new Page(3, new CraftingStack(AbyssalCraft.CorlegsP), NecronomiconText.CRAFTING_PLATED_CORALIUM_LEGGINGS),
			new Page(4, new CraftingStack(AbyssalCraft.CorbootsP), NecronomiconText.CRAFTING_PLATED_CORALIUM_BOOTS),
			new Page(5, new CraftingStack(AbyssalCraft.corbow), NecronomiconText.CRAFTING_CORALIUM_LONGBOW)
		);
		addPages("dreadlands", "materials", 
			new Page(1, new ItemStack(AbyssalCraft.dreadstone), NecronomiconText.MATERIAL_DREADSTONE_1),
			new Page(2, NecronomiconText.MATERIAL_DREADSTONE_2),
			new Page(3, new ItemStack(AbyssalCraft.abydreadstone), NecronomiconText.MATERIAL_ABYSSALNITE_STONE_1),
			new Page(4, NecronomiconText.MATERIAL_ABYSSALNITE_STONE_2),
			new Page(5, new ItemStack(AbyssalCraft.abydreadore), NecronomiconText.MATERIAL_DREADLANDS_ABYSSALNITE_1),
			new Page(6, NecronomiconText.MATERIAL_DREADLANDS_ABYSSALNITE_2),
			new Page(7, new ItemStack(AbyssalCraft.dreadore), NecronomiconText.MATERIAL_DREADED_ABYSSALNITE_1),
			new Page(8, NecronomiconText.MATERIAL_DREADED_ABYSSALNITE_2),
			new Page(9, new ItemStack(AbyssalCraft.dreadgrass), NecronomiconText.MATERIAL_DREADLANDS_GRASS_1),
			new Page(10, NecronomiconText.MATERIAL_DREADLANDS_GRASS_2),
			new Page(11, new ItemStack(AbyssalCraft.dreadsapling), NecronomiconText.MATERIAL_DREADLANDS_TREE_1),
			new Page(12, NecronomiconText.MATERIAL_DREADLANDS_TREE_2)
		);
		addPages("dreadlands", "progression", 
			new Page(1, NecronomiconText.PROGRESSION_DREADLANDS_1),
			new Page(2, NecronomiconText.PROGRESSION_DREADLANDS_2),
			new Page(3, NecronomiconText.PROGRESSION_DREADLANDS_3)
		);
		addPages("dreadlands", "entities", 
			new Page(1, NecronomiconResources.ABYSSALNITE_GOLEM, NecronomiconText.ENTITY_ABYSSALNITE_GOLEM_1),
			new Page(2, NecronomiconText.ENTITY_ABYSSALNITE_GOLEM_2),
			new Page(3, NecronomiconResources.DREADED_ABYSSALNITE_GOLEM, NecronomiconText.ENTITY_DREADED_ABYSSALNITE_GOLEM_1),
			new Page(4, NecronomiconText.ENTITY_DREADED_ABYSSALNITE_GOLEM_2),
			new Page(5, NecronomiconResources.DREADLING, NecronomiconText.ENTITY_DREADLING_1),
			new Page(6, NecronomiconText.ENTITY_DREADLING_2),
			new Page(7, NecronomiconResources.DREAD_SPAWN, NecronomiconText.ENTITY_DREAD_SPAWN_1),
			new Page(8, NecronomiconText.ENTITY_DREAD_SPAWN_2),
			new Page(9, NecronomiconResources.DEMON_ANIMALS, NecronomiconText.ENTITY_DEMON_ANIMALS_1),
			new Page(10, NecronomiconText.ENTITY_DEMON_ANIMALS_2),
			new Page(11, NecronomiconResources.SPAWN_OF_CHAGAROTH, NecronomiconText.ENTITY_SPAWN_OF_CHAGAROTH_1),
			new Page(12, NecronomiconText.ENTITY_SPAWN_OF_CHAGAROTH_2),
			new Page(13, NecronomiconResources.FIST_OF_CHAGAROTH, NecronomiconText.ENTITY_FIST_OF_CHAGAROTH_1),
			new Page(14, NecronomiconText.ENTITY_FIST_OF_CHAGAROTH_2),
			new Page(15, NecronomiconResources.DREADGUARD, NecronomiconText.ENTITY_DREADGUARD_1),
			new Page(16, NecronomiconText.ENTITY_DREADGUARD_2),
			new Page(17, NecronomiconResources.CHAGAROTH, NecronomiconText.ENTITY_CHAGAROTH_1),
			new Page(18, NecronomiconText.ENTITY_CHAGAROTH_2),
			new Page(19, NecronomiconResources.LESSER_SHOGGOTH, NecronomiconText.ENTITY_LESSER_SHOGGOTH_1),
			new Page(20, NecronomiconText.ENTITY_LESSER_SHOGGOTH_2)
		);
		addPages("dreadlands", "specialmaterials", 
			new Page(1, new CraftingStack(AbyssalCraft.dreadiumblock), NecronomiconText.CRAFTING_DREADIUM_1),
			new Page(2, NecronomiconText.CRAFTING_DREADIUM_2),
			new Page(3, new CraftingStack(AbyssalCraft.dreadcloth), NecronomiconText.CRAFTING_DREAD_CLOTH),
			new Page(4, new CraftingStack(AbyssalCraft.dreadaltarbottom), NecronomiconText.CRAFTING_DREAD_ALTAR_BOTTOM),
			new Page(5, new CraftingStack(AbyssalCraft.dreadaltartop), NecronomiconText.CRAFTING_DREAD_ALTAR_TOP),
			new Page(6, new CraftingStack(AbyssalCraft.dreadplate), NecronomiconText.CRAFTING_DREADIUM_PLATE),
			new Page(7, new CraftingStack(new ItemStack(AbyssalCraft.skin, 1, 2)), NecronomiconText.CRAFTING_SKIN_OF_OMOTHOL_1),
			new Page(8, new CraftingStack(AbyssalCraft.necronomicon_omt), NecronomiconText.CRAFTING_NECRONOMICON_O)
		);
		addPages("dreadlands", "armortools", 
			new Page(1, new CraftingStack(AbyssalCraft.dreadiumShelmet), NecronomiconText.CRAFTING_DREADIUM_SAMURAI_HELMET),
			new Page(2, new CraftingStack(AbyssalCraft.dreadiumSplate), NecronomiconText.CRAFTING_DREADIUM_SAMURAI_CHESTPLATE),
			new Page(3, new CraftingStack(AbyssalCraft.dreadiumSlegs), NecronomiconText.CRAFTING_DREADIUM_SAMURAI_LEGGINGS),
			new Page(4, new CraftingStack(AbyssalCraft.dreadiumSboots), NecronomiconText.CRAFTING_DREADIUM_SAMURAI_BOOTS)
		);
		addPages("omothol", "materials", 
			new Page(1, new ItemStack(AbyssalCraft.omotholstone), NecronomiconText.MATERIAL_OMOTHOL_STONE_1),
			new Page(2, NecronomiconText.MATERIAL_OMOTHOL_STONE_2),
			new Page(3, new ItemStack(AbyssalCraft.ethaxium), NecronomiconText.MATERIAL_ETHAXIUM_1),
			new Page(4, NecronomiconText.MATERIAL_ETHAXIUM_2),
			new Page(5, new ItemStack(AbyssalCraft.darkethaxiumbrick), NecronomiconText.MATERIAL_DARK_ETHAXIUM_1),
			new Page(6, NecronomiconText.MATERIAL_DARK_ETHAXIUM_2)
		);
		addPages("omothol", "progression", 
			new Page(1, NecronomiconText.PROGRESSION_OMOTHOL_1),
			new Page(2, NecronomiconText.PROGRESSION_OMOTHOL_2)
		);
		addPages("omothol", "entities", 
			new Page(1, NecronomiconResources.REMNANT, NecronomiconText.ENTITY_REMNANT_1),
			new Page(2, NecronomiconText.ENTITY_REMNANT_2),
			new Page(3, NecronomiconResources.OMOTHOL_GHOUL, NecronomiconText.ENTITY_OMOTHOL_GHOUL_1),
			new Page(4, NecronomiconText.ENTITY_OMOTHOL_GHOUL_2),
			new Page(5, NecronomiconResources.MINION_OF_THE_GATEKEEPER, NecronomiconText.ENTITY_MINION_OF_THE_GATEKEEPER_1),
			new Page(6, NecronomiconText.ENTITY_MINION_OF_THE_GATEKEEPER_2),
			new Page(7, NecronomiconResources.JZAHAR, NecronomiconText.ENTITY_JZAHAR_1),
			new Page(8, NecronomiconText.ENTITY_JZAHAR_2),
			new Page(9, NecronomiconResources.LESSER_SHOGGOTH, NecronomiconText.ENTITY_LESSER_SHOGGOTH_1),
			new Page(10, NecronomiconText.ENTITY_LESSER_SHOGGOTH_2)
		);
		addPages("omothol", "specialmaterials", 
			new Page(1, new CraftingStack(AbyssalCraft.lifeCrystal), NecronomiconText.CRAFTING_LIFE_CRYSTAL_1),
			new Page(2, NecronomiconText.CRAFTING_LIFE_CRYSTAL_2),
			new Page(3, new CraftingStack(AbyssalCraft.ethaxiumIngot, AbyssalCraft.ethaxium_brick, AbyssalCraft.ethaxium_brick, AbyssalCraft.ethaxium_brick, AbyssalCraft.ethaxium_brick,AbyssalCraft.lifeCrystal, AbyssalCraft.ethaxium_brick, AbyssalCraft.ethaxium_brick, AbyssalCraft.ethaxium_brick, AbyssalCraft.ethaxium_brick), NecronomiconText.CRAFTING_ETHAXIUM_INGOT_1),
			new Page(4, NecronomiconText.CRAFTING_ETHAXIUM_INGOT_2),
			new Page(5, new CraftingStack(AbyssalCraft.engravingBlank), NecronomiconText.CRAFTING_BLANK_ENGRAVING_1),
			new Page(6, NecronomiconText.CRAFTING_BLANK_ENGRAVING_2),
			new Page(7, new CraftingStack(AbyssalCraft.coin, null, Items.iron_ingot, null, Items.iron_ingot, Items.flint, Items.iron_ingot, null, Items.iron_ingot, null), NecronomiconText.CRAFTING_COIN),
			new Page(8, new CraftingStack(AbyssalCraft.engraver), NecronomiconText.CRAFTING_ENGRAVER),
			new Page(9, new CraftingStack(AbyssalCraft.abyssalnomicon), NecronomiconText.CRAFTING_ABYSSALNOMICON_1),
			new Page(10, NecronomiconText.CRAFTING_ABYSSALNOMICON_2)
		);
		addPages("darkrealm", "materials", 
			new Page(1, new ItemStack(AbyssalCraft.Darkstone), NecronomiconText.MATERIAL_DARKSTONE_1),
			new Page(2, NecronomiconText.MATERIAL_DARKSTONE_2)
		);
		addPages("darkrealm", "entities", 
			new Page(1, NecronomiconResources.SHADOW_CREATURE, NecronomiconText.ENTITY_SHADOW_CREATURE_1),
			new Page(2, NecronomiconText.ENTITY_SHADOW_CREATURE_2),
			new Page(3, NecronomiconResources.SHADOW_MONSTER, NecronomiconText.ENTITY_SHADOW_MONSTER_1),
			new Page(4, NecronomiconText.ENTITY_SHADOW_MONSTER_2),
			new Page(5, NecronomiconResources.SHADOW_BEAST, NecronomiconText.ENTITY_SHADOW_BEAST_1),
			new Page(6, NecronomiconText.ENTITY_SHADOW_BEAST_2),
			new Page(7, NecronomiconResources.SACTHOTH, NecronomiconText.ENTITY_SACTHOTH_1),
			new Page(8, NecronomiconText.ENTITY_SACTHOTH_2),
			new Page(9, NecronomiconResources.LESSER_SHOGGOTH, NecronomiconText.ENTITY_LESSER_SHOGGOTH_1),
			new Page(10, NecronomiconText.ENTITY_LESSER_SHOGGOTH_2)
		);
		addPages("rituals", "gettingstarted", 
			new Page(1, NecronomiconResources.RITUAL_TUT_1, NecronomiconText.RITUAL_TUT_1),
			new Page(2, NecronomiconText.RITUAL_TUT_2),
			new Page(3, NecronomiconResources.RITUAL_TUT_2, NecronomiconText.RITUAL_TUT_3),
			new Page(4, NecronomiconResources.BLANK, NecronomiconText.RITUAL_TUT_4),
			new Page(5, NecronomiconResources.RITUAL_TUT_3, NecronomiconText.RITUAL_TUT_5),
			new Page(6, NecronomiconText.RITUAL_TUT_6)
		);
		addPages("rituals", "materials", 
			new Page(1, new ItemStack(AbyssalCraft.ritualaltar), NecronomiconText.MATERIAL_RITUAL_ALTAR_1),
			new Page(2, new ItemStack(AbyssalCraft.ritualpedestal), NecronomiconText.MATERIAL_RITUAL_PEDESTAL_1),
			new Page(3, new ItemStack(AbyssalCraft.monolithStone), NecronomiconText.MATERIAL_MONOLITH_STONE_1)
		);
		addPages("rituals", "specialmaterials", 
			new Page(1, new CraftingStack(AbyssalCraft.monolithPillar), NecronomiconText.CRAFTING_MONOLITH_PILLAR_1),
			new Page(2, new CraftingStack(new ItemStack(AbyssalCraft.charm, 1, 0)), NecronomiconText.CRAFTING_RITUAL_CHARM_1)
		);
		addPages("rituals", "potentialenergy", 
			new Page(1, NecronomiconText.PE_TUT_1),
			new Page(2, NecronomiconResources.PE_TUT_1, NecronomiconText.PE_TUT_2),
			new Page(3, NecronomiconResources.PE_TUT_2, NecronomiconText.PE_TUT_3),
			new Page(4, NecronomiconText.PE_TUT_4),
			new Page(5, NecronomiconResources.PE_TUT_3, NecronomiconText.PE_TUT_5),
			new Page(6, NecronomiconText.PE_TUT_6),
			new Page(7, NecronomiconResources.PE_TUT_4, NecronomiconText.PE_TUT_7),
			new Page(8, NecronomiconText.PE_TUT_8),
			new Page(9, NecronomiconResources.PE_TUT_5, NecronomiconText.PE_TUT_9),
			new Page(10, NecronomiconResources.PE_TUT_6, NecronomiconText.PE_TUT_10),
			new Page(11, NecronomiconResources.PE_TUT_7, NecronomiconText.PE_TUT_11),
			new Page(12, NecronomiconResources.PE_TUT_8, NecronomiconText.PE_TUT_12),
			new Page(13, NecronomiconText.PE_TUT_13)
		);
		
		addPages("miscinfo", "specialmaterials", 
			new Page(1, new CraftingStack(AbyssalCraft.carbonCluster), NecronomiconText.CRAFTING_CARBON_CLUSTER),
			new Page(2, new CraftingStack(AbyssalCraft.denseCarbonCluster), NecronomiconText.CRAFTING_DENSE_CARBON_CLUSTER)
		);
		
		addPages("miscinfo", "enchantments", 
			new Page(1, Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(AbyssalCraft.coraliumE, 1)), NecronomiconText.ENCHANTMENT_CORALIUM),
			new Page(2, Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(AbyssalCraft.dreadE, 1)), NecronomiconText.ENCHANTMENT_DREAD),
			new Page(3, Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(AbyssalCraft.lightPierce, AbyssalCraft.lightPierce.getMaxLevel())), NecronomiconText.ENCHANTMENT_LIGHT_PIERCE)
		);
	}
}
