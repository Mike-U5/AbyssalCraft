/**AbyssalCraft
 *Copyright 2012-2014 Shinoow
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 */
package com.shinoow.abyssalcraft.common.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.TeleporterAbyss;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTeleporter extends BlockBreakable
{
	public BlockTeleporter()
	{
		super("AG", Material.portal , false);
		setTickRandomly(true);
		setHardness(-1.0F);
		setLightLevel(0.75F);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister)
	{
		blockIcon = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + "AG");
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return null;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1BlockAccess, int par2, int par3, int par4)
	{
		int l = func_149999_b(par1BlockAccess.getBlockMetadata(par2, par3, par4));

		if (l == 0)
		{
			if (par1BlockAccess.getBlock(par2 - 1, par3, par4) != this && par1BlockAccess.getBlock(par2 + 1, par3, par4) != this)
				l = 2;
			else
				l = 1;

			if (par1BlockAccess instanceof World && !((World)par1BlockAccess).isRemote)
				((World)par1BlockAccess).setBlockMetadataWithNotify(par2, par3, par4, l, 2);
		}

		float f = 0.125F;
		float f1 = 0.125F;

		if (l == 1)
			f = 0.5F;

		if (l == 2)
			f1 = 0.5F;

		setBlockBounds(0.5F - f, 0.0F, 0.5F - f1, 0.5F + f, 1.0F, 0.5F + f1);
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	public static boolean tryToCreatePortal(World par1World, int par2, int par3, int par4)
	{
		byte b0 = 0;
		byte b1 = 0;
		if (par1World.getBlock(par2 - 1, par3, par4) == AbyssalCraft.abystone || par1World.getBlock(par2 + 1, par3, par4) == AbyssalCraft.abystone)
			b0 = 1;
		if (par1World.getBlock(par2, par3, par4 - 1) == AbyssalCraft.abystone || par1World.getBlock(par2, par3, par4 + 1) == AbyssalCraft.abystone)
			b1 = 1;
		if (b0 == b1)
			return false;
		else
		{
			if (par1World.getBlock(par2 - b0, par3, par4 - b1) == Blocks.air)
			{
				par2 -= b0;
				par4 -= b1;
			}
			int l;
			int i1;
			for (l = -1; l <= 2; ++l)
				for (i1 = -1; i1 <= 3; ++i1)
				{
					boolean flag = l == -1 || l == 2 || i1 == -1 || i1 == 3;
					if (l != -1 && l != 2 || i1 != -1 && i1 != 3)
					{
						Block j1 = par1World.getBlock(par2 + b0 * l, par3 + i1, par4 + b1 * l);
						if (flag)
						{
							if (j1 != AbyssalCraft.abystone)
								return false;
						}
						else if (j1 != Blocks.air && j1 != AbyssalCraft.Coraliumfire)
							return false;
					}
				}
			for (l = 0; l < 2; ++l)
				for (i1 = 0; i1 < 3; ++i1)
					par1World.setBlock(par2 + b0 * l, par3 + i1, par4 + b1 * l, AbyssalCraft.portal, 0, 2);
			return true;
		}
	}

	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, Block par5)
	{
		byte b0 = 0;
		byte b1 = 1;
		if (par1World.getBlock(par2 - 1, par3, par4) == this || par1World.getBlock(par2 + 1, par3, par4) == this)
		{
			b0 = 1;
			b1 = 0;
		}
		int i1;
		for (i1 = par3; par1World.getBlock(par2, i1 - 1, par4) == this; --i1)
			;
		if (par1World.getBlock(par2, i1 - 1, par4) != AbyssalCraft.abystone)
			par1World.setBlockToAir(par2, par3, par4);
		else
		{
			int j1;
			for (j1 = 1; j1 < 4 && par1World.getBlock(par2, i1 + j1, par4) == this; ++j1)
				;
			if (j1 == 3 && par1World.getBlock(par2, i1 + j1, par4) == AbyssalCraft.abystone)
			{
				boolean flag = par1World.getBlock(par2 - 1, par3, par4) == this || par1World.getBlock(par2 + 1, par3, par4) == this;
				boolean flag1 = par1World.getBlock(par2, par3, par4 - 1) == this || par1World.getBlock(par2, par3, par4 + 1) == this;
				if (flag && flag1)
					par1World.setBlockToAir(par2, par3, par4);
				else if ((par1World.getBlock(par2 + b0, par3, par4 + b1) != AbyssalCraft.abystone || par1World.getBlock(par2 - b0, par3, par4 - b1) != this) && (par1World.getBlock(par2 - b0, par3, par4 - b1) != AbyssalCraft.abystone || par1World.getBlock(par2 + b0, par3, par4 + b1) != this))
					par1World.setBlockToAir(par2, par3, par4);
			} else
				par1World.setBlockToAir(par2, par3, par4);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess par1BlockAccess, int par2, int par3, int par4, int par5)
	{
		int i1 = 0;

		if (par1BlockAccess.getBlock(par2, par3, par4) == this)
		{
			i1 = func_149999_b(par1BlockAccess.getBlockMetadata(par2, par3, par4));

			if (i1 == 0)
				return false;

			if (i1 == 2 && par5 != 5 && par5 != 4)
				return false;

			if (i1 == 1 && par5 != 3 && par5 != 2)
				return false;
		}

		boolean flag = par1BlockAccess.getBlock(par2 - 1, par3, par4) == this && par1BlockAccess.getBlock(par2 - 2, par3, par4) != this;
		boolean flag1 = par1BlockAccess.getBlock(par2 + 1, par3, par4) == this && par1BlockAccess.getBlock(par2 + 2, par3, par4) != this;
		boolean flag2 = par1BlockAccess.getBlock(par2, par3, par4 - 1) == this && par1BlockAccess.getBlock(par2, par3, par4 - 2) != this;
		boolean flag3 = par1BlockAccess.getBlock(par2, par3, par4 + 1) == this && par1BlockAccess.getBlock(par2, par3, par4 + 2) != this;
		boolean flag4 = flag || flag1 || i1 == 1;
		boolean flag5 = flag2 || flag3 || i1 == 2;
		return flag4 && par5 == 4 ? true : flag4 && par5 == 5 ? true : flag5 && par5 == 2 ? true : flag5 && par5 == 3;
	}

	@Override
	public int quantityDropped(Random par1Random)
	{
		return 0;
	}

	@Override
	public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
	{

		if (par5Entity.ridingEntity == null && par5Entity.riddenByEntity == null && par5Entity instanceof EntityPlayerMP)
		{
			EntityPlayerMP thePlayer = (EntityPlayerMP)par5Entity;
			thePlayer.addStat(AbyssalCraft.enterabyss, 1);
			if (thePlayer.timeUntilPortal > 0)
				thePlayer.timeUntilPortal = 10;
			else if (thePlayer.dimension != AbyssalCraft.configDimId1)
			{
				thePlayer.timeUntilPortal = 10;
				thePlayer.mcServer.getConfigurationManager().transferPlayerToDimension(thePlayer, AbyssalCraft.configDimId1, new TeleporterAbyss(thePlayer.mcServer.worldServerForDimension(AbyssalCraft.configDimId1)));
			}
			else {
				thePlayer.timeUntilPortal = 10;
				thePlayer.mcServer.getConfigurationManager().transferPlayerToDimension(thePlayer, 0, new TeleporterAbyss(thePlayer.mcServer.worldServerForDimension(0)));
			}
		}
	}

	public static int func_149999_b(int p_149999_0_)
	{
		return p_149999_0_ & 3;
	}


	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World par1World, int par2, int par3, int par4)
	{
		return Item.getItemById(0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass()
	{
		return 1;
	}
}