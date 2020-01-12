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
package com.shinoow.abyssalcraft.integration.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.shinoow.abyssalcraft.api.recipe.CrystallizerRecipes;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class CrystallizerRecipeHandler extends TemplateRecipeHandler
{
	public class CrystallizationPair extends CachedRecipe
	{
		public CrystallizationPair(ItemStack ingred, ItemStack[] itemStacks) {
			ingred.stackSize = 1;
			this.ingred = new PositionedStack(ingred, 51, 6);
			result = new PositionedStack(itemStacks[0], 111, 24);
			if(itemStacks[1] != null)
				result2 = new PositionedStack(itemStacks[1], 128, 24);
			resultList.add(result);
			if(result2 != null)
				resultList.add(result2);
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(cycleticks / 48, resultList);
		}

		@Override
		public PositionedStack getResult() {
			List<PositionedStack> list = getCycledIngredients(cycleticks / 48, Arrays.asList(ingred));
			while(list.iterator().hasNext())
				return list.iterator().next();
			return ingred;
		}

		@Override
		public PositionedStack getOtherStack() {
			return afuels.get(cycleticks / 48 % afuels.size()).stack;
		}

		PositionedStack ingred;
		PositionedStack result;
		PositionedStack result2;
		private List<PositionedStack> resultList = new ArrayList<PositionedStack>();
	}

	public static class FuelPair
	{
		public FuelPair(ItemStack ingred, int burnTime) {
			stack = new PositionedStack(ingred, 51, 42, false);
			this.burnTime = burnTime;
		}

		public PositionedStack stack;
		public int burnTime;
	}

	public static ArrayList<FuelPair> afuels;
	public static HashSet<Block> efuels;

	@Override
	public void loadTransferRects() {
		transferRects.add(new RecipeTransferRect(new Rectangle(50, 23, 18, 18), "fuel"));
		transferRects.add(new RecipeTransferRect(new Rectangle(74, 23, 24, 18), "crystallization"));
	}

	@Override
	public String getRecipeName() {
		return StatCollector.translateToLocal("container.abyssalcraft.crystallizer.nei");
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId.equals("crystallization") && getClass() == CrystallizerRecipeHandler.class) {//don't want subclasses getting a hold of this
			Map<ItemStack, ItemStack[]> recipes = CrystallizerRecipes.instance().getCrystallizationList();
			for (Entry<ItemStack, ItemStack[]> recipe : recipes.entrySet())
				arecipes.add(new CrystallizationPair(recipe.getKey(), new ItemStack[] {recipe.getValue()[0], recipe.getValue()[1]}));
		} else
			super.loadCraftingRecipes(outputId, results);
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		Map<ItemStack, ItemStack[]> recipes = CrystallizerRecipes.instance().getCrystallizationList();
		for (Entry<ItemStack, ItemStack[]> recipe : recipes.entrySet())
			if (NEIServerUtils.areStacksSameType(recipe.getValue()[0], result) || NEIServerUtils.areStacksSameType(recipe.getValue()[1], result))
				arecipes.add(new CrystallizationPair(recipe.getKey(), new ItemStack[] {recipe.getValue()[0], recipe.getValue()[1]}));
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if (inputId.equals("fuel") && getClass() == CrystallizerRecipeHandler.class)//don't want subclasses getting a hold of this
			loadCraftingRecipes("crystallization");
		else
			super.loadUsageRecipes(inputId, ingredients);
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		Map<ItemStack, ItemStack[]> recipes = CrystallizerRecipes.instance().getCrystallizationList();
		for (Entry<ItemStack, ItemStack[]> recipe : recipes.entrySet())
			if (NEIServerUtils.areStacksSameTypeCrafting(recipe.getKey(), ingredient)) {
				CrystallizationPair arecipe = new CrystallizationPair(recipe.getKey(), new ItemStack[] {recipe.getValue()[0], recipe.getValue()[1]});
				arecipe.setIngredientPermutation(Arrays.asList(arecipe.ingred), ingredient);
				arecipes.add(arecipe);
			}
	}

	@Override
	public String getGuiTexture() {
		return "abyssalcraft:textures/gui/container/crystallizer_NEI.png";
	}

	@Override
	public void drawExtras(int recipe) {
		drawProgressBar(51, 25, 176, 0, 14, 14, 48, 7);
		drawProgressBar(74, 23, 176, 14, 24, 16, 48, 0);
	}

	@Override
	public String getOverlayIdentifier() {
		return "crystallization";
	}
}
