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
package com.shinoow.abyssalcraft.common.items.armor;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import thaumcraft.api.IVisDiscountGear;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.nodes.IRevealer;

import com.shinoow.abyssalcraft.AbyssalCraft;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional.Interface;
import cpw.mods.fml.common.Optional.InterfaceList;
import cpw.mods.fml.common.Optional.Method;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@InterfaceList(value = { @Interface(iface = "thaumcraft.api.IVisDiscountGear", modid = "Thaumcraft"),
		@Interface(iface = "thaumcraft.api.nodes.IRevealer", modid = "Thaumcraft")})
public class ItemDepthsArmor extends ItemArmor implements IVisDiscountGear, IRevealer {
	public ItemDepthsArmor(ArmorMaterial par2EnumArmorMaterial, int par3, int par4){
		super(par2EnumArmorMaterial, par3, par4);
		setCreativeTab(AbyssalCraft.tabCombat);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return EnumChatFormatting.DARK_RED + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer)
	{
		if(stack.getItem() == AbyssalCraft.Depthshelmet || stack.getItem() == AbyssalCraft.Depthsplate || stack.getItem() == AbyssalCraft.Depthsboots)
			return "abyssalcraft:textures/armor/depths_1.png";

		if(stack.getItem() == AbyssalCraft.Depthslegs)
			return "abyssalcraft:textures/armor/depths_2.png";
		else return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister)
	{
		itemIcon = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + this.getUnlocalizedName().substring(5));
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemstack) {
		int setEff = 0;
		
		if (player.getCurrentArmor(3) != null && player.getCurrentArmor(3).getItem().equals(AbyssalCraft.Depthshelmet)) {
			setEff++;
			if(player.isInWater()) {
				player.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), 260, 0));
			}
		}
		
		if (player.getCurrentArmor(2) != null && player.getCurrentArmor(2).getItem().equals(AbyssalCraft.Depthsplate)) {
			setEff++;
			if(player.isInWater()) {
				player.addPotionEffect(new PotionEffect(Potion.resistance.getId(), 10, 0));
			}
		}
		
		if (player.getCurrentArmor(1) != null && player.getCurrentArmor(1).getItem().equals(AbyssalCraft.Depthslegs)) {
			setEff++;
			if(player.isInWater()) {
				player.addPotionEffect(new PotionEffect(Potion.regeneration.getId(), 20, 0));
			}
		}
		
		if (player.getCurrentArmor(0) != null && player.getCurrentArmor(0).getItem().equals(AbyssalCraft.Depthsboots)) {
			setEff++;
			if(player.isInWater()) {
				player.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), 10, 3));
			}
		}
		
		//Set Effect
		if (setEff >= 3) {
			player.addPotionEffect(new PotionEffect(Potion.waterBreathing.id, 10, 0));
			if(player.getActivePotionEffect(AbyssalCraft.Cplague) != null) {
				player.removePotionEffect(AbyssalCraft.Cplague.getId());
			}	
		}
		
		//Remove existing effects
		if(player.getActivePotionEffect(Potion.regeneration) != null && player.getActivePotionEffect(Potion.regeneration).getDuration() <= 0) {
			player.removePotionEffect(Potion.regeneration.id);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderHelmetOverlay(ItemStack stack, EntityPlayer player, ScaledResolution resolution, float partialTicks, boolean hasScreen, int mouseX, int mouseY){
		final ResourceLocation coraliumBlur = new ResourceLocation("abyssalcraft:textures/misc/coraliumblur.png");


		if(Minecraft.getMinecraft().gameSettings.thirdPersonView == 0 && stack != null && stack.getItem() == AbyssalCraft.Depthshelmet) {
			GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);

			Tessellator t = Tessellator.instance;

			ScaledResolution scale = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
			int width = scale.getScaledWidth();
			int height = scale.getScaledHeight();

			GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glDepthMask(false);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			Minecraft.getMinecraft().renderEngine.bindTexture(coraliumBlur);

			t.startDrawingQuads();
			t.addVertexWithUV(0.0D, height, 90.0D, 0.0D, 1.0D);
			t.addVertexWithUV(width, height, 90.0D, 1.0D, 1.0D);
			t.addVertexWithUV(width, 0.0D, 90.0D, 1.0D, 0.0D);
			t.addVertexWithUV(0.0D, 0.0D, 90.0D, 0.0D, 0.0D);
			t.draw();

			GL11.glPopAttrib();
		}
	}

	@Override
	@Method(modid = "Thaumcraft")
	public int getVisDiscount(ItemStack stack, EntityPlayer player,
			Aspect aspect) {
		return stack.getItem() == AbyssalCraft.Depthshelmet ? 5 : stack.getItem() == AbyssalCraft.Depthsplate ? 2 :
			stack.getItem() == AbyssalCraft.Depthslegs ? 2 : stack.getItem() == AbyssalCraft.Depthsboots ? 1 : 0;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List l, boolean B){
		if(Loader.isModLoaded("Thaumcraft")){
			if(is.getItem() == AbyssalCraft.Depthshelmet)
				l.add("\u00A75"+StatCollector.translateToLocal("tc.visdiscount")+": 5%");
			if(is.getItem() == AbyssalCraft.Depthsplate)
				l.add("\u00A75"+StatCollector.translateToLocal("tc.visdiscount")+": 2%");
			if(is.getItem() == AbyssalCraft.Depthslegs)
				l.add("\u00A75"+StatCollector.translateToLocal("tc.visdiscount")+": 2%");
			if(is.getItem() == AbyssalCraft.Depthsboots)
				l.add("\u00A75"+StatCollector.translateToLocal("tc.visdiscount")+": 1%");
		}
	}

	@Override
	@Method(modid = "Thaumcraft")
	public boolean showNodes(ItemStack itemstack, EntityLivingBase player) {

		return itemstack.getItem() == AbyssalCraft.Depthshelmet;
	}
}
