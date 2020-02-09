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
package com.shinoow.abyssalcraft.client.render.entity;

import org.lwjgl.opengl.GL11;

import com.shinoow.abyssalcraft.client.model.entity.ModelLesserDreadbeast;
import com.shinoow.abyssalcraft.common.entity.EntityLesserDreadbeast;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderLesserDreadbeast extends RenderLiving {

	private float scale = 3.0F;

	private static final ResourceLocation mobTexture = new ResourceLocation("abyssalcraft:textures/model/lesser_dreadbeast.png");

	public RenderLesserDreadbeast() {
		super(new ModelLesserDreadbeast(), 0.5F);
	}

	/**
	 * Applies the scale to the transform matrix
	 */
	protected void preRenderScale(EntityLesserDreadbeast par1EntityEntityLesserDreadbeast, float par2) {
		GL11.glScalef(scale, scale, scale);
	}

	public void doRender(EntityLesserDreadbeast entity, double par2, double par4, double par6, float par8, float par9) {
		super.doRender(entity, par2, par4, par6, par8, par9);
	}

	@Override
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
		doRender((EntityLesserDreadbeast) par1Entity, par2, par4, par6, par8, par9);
	}

	@Override
	protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2) {
		preRenderScale((EntityLesserDreadbeast) par1EntityLivingBase, par2);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return mobTexture;
	}
}
