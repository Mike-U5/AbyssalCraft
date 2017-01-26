/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2017 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.common.entity.EntityPSDLTracker;
import com.shinoow.abyssalcraft.lib.ACTabs;

public class ItemTrackerPSDL extends Item {

	public ItemTrackerPSDL() {
		setUnlocalizedName("powerstonetracker");
		setCreativeTab(ACTabs.tabItems);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, EnumHand hand)
	{
		RayTraceResult movingobjectposition = getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer, false);

		if (movingobjectposition != null && movingobjectposition.typeOfHit == RayTraceResult.Type.BLOCK && par2World.getBlockState(movingobjectposition.getBlockPos()) == ACBlocks.dreadlands_infused_powerstone)
			return new ActionResult(EnumActionResult.PASS, par1ItemStack);

		if (!par2World.isRemote)
		{
			BlockPos blockpos = ((WorldServer)par2World).getChunkProvider().getStrongholdGen(par2World, "AbyStronghold", new BlockPos(par3EntityPlayer));

			if (blockpos != null)
			{
				EntityPSDLTracker entitypsdltracker = new EntityPSDLTracker(par2World, par3EntityPlayer.posX, par3EntityPlayer.posY, par3EntityPlayer.posZ);
				entitypsdltracker.moveTowards(blockpos);
				par2World.spawnEntityInWorld(entitypsdltracker);
				par2World.playSound((EntityPlayer)null, par3EntityPlayer.posX, par3EntityPlayer.posY, par3EntityPlayer.posZ, SoundEvents.entity_endereye_launch, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
				par2World.playAuxSFXAtEntity((EntityPlayer)null, 1003, new BlockPos(par3EntityPlayer), 0);

				if (!par3EntityPlayer.capabilities.isCreativeMode)
					--par1ItemStack.stackSize;

				return new ActionResult(EnumActionResult.SUCCESS, par1ItemStack);
			}
		}

		return new ActionResult(EnumActionResult.FAIL, par1ItemStack);
	}

	@Override
	public boolean hasEffect(ItemStack is){
		return true;
	}
}
