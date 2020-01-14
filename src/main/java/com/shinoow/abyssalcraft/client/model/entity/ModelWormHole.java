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
package com.shinoow.abyssalcraft.client.model.entity;

import com.shinoow.abyssalcraft.common.entity.EntityWormHole;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelWormHole extends ModelBase {
	public ModelRenderer bodyMain;
	public ModelRenderer body2;
	public ModelRenderer body3;
	public ModelRenderer body4;
	public ModelRenderer body5;
	
	public ModelRenderer body6;
	public ModelRenderer body7;
	public ModelRenderer body8;
	public ModelRenderer body9;

	public ModelWormHole(boolean renderStaff) {
		textureWidth = 128;
		textureHeight = 64;

		bodyMain = makeCube(0, 18, 22, 5, -7F, -28F, 0F);
		body2 = makeCube(46, 5, 26, 1, -7F, -28F, -1F);
		body3 = makeCube(58, 5, 26, 1, 6F, -28F, -1F);
		body4 = makeCube(46, 4, 26, 1, -7F, -28F, -2F);
		body5 = makeCube(59, 4, 26, 1, 7F, -28F, -2F);
		/// ADDED BODY PARTS
		/*body6 = new ModelRenderer(this, 46, 0);
		body6.addBox(0F, 0F, 0F, 5, 26, 1);
		body6.setRotationPoint(-7F, -28F, 1F);
		body6.setTextureSize(64, 32);
		body6.mirror = true;
		setRotation(body6, 0F, 0F, 0F);
		body7 = new ModelRenderer(this, 58, 0);
		body7.addBox(0F, 0F, 0F, 5, 26, 1);
		body7.setRotationPoint(6F, -28F, 1F);
		body7.setTextureSize(64, 32);
		body7.mirror = true;
		setRotation(body7, 0F, 0F, 0F);
		body8 = new ModelRenderer(this, 46, 0);
		body8.addBox(0F, 0F, 0F, 4, 26, 1);
		body8.setRotationPoint(-7F, -28F, 2F);
		body8.setTextureSize(64, 32);
		body8.mirror = true;
		setRotation(body8, 0F, 0F, 0F);
		body9 = new ModelRenderer(this, 59, 0);
		body9.addBox(0F, 0F, 0F, 4, 26, 1);
		body9.setRotationPoint(7F, -28F, 2F);
		body9.setTextureSize(64, 32);
		body9.mirror = true;
		setRotation(body9, 0F, 0F, 0F);*/
	}
	
	private ModelRenderer makeCube(int numMr, int numX, int numY, int numZ, float fX, float fY, float fZ) {
		final ModelRenderer r = new ModelRenderer(this, numMr, 0);
		r.addBox(0F, 0F, 0F, numX, numY, numZ);
		r.setRotationPoint(fX, fY, fZ);
		r.setTextureSize(64, 32);
		r.mirror = true;
		///setRotation(r, 0, 0, 0);
		return r;
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		bodyMain.render(f5);
		body2.render(f5);
		body3.render(f5);
		body4.render(f5);
		body5.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		float f6;
		
		if (onGround > -9990.0F) {
			f6 = onGround;
			f6 = 1.0F - onGround;
			f6 *= f6;
			f6 *= f6;
			f6 = 1.0F - f6;
		}

		for(int i = 0; i < 4; i++){
			bodyMain.rotationPointY = -9.5F + MathHelper.cos((i * 2 + f2) * 0.25F);
			body2.rotationPointY = -9.5F + MathHelper.cos((i * 2 + f2) * 0.25F);
			body3.rotationPointY = -9.5F + MathHelper.cos((i * 2 + f2) * 0.25F);
			body4.rotationPointY = -9.5F + MathHelper.cos((i * 2 + f2) * 0.25F);
			body5.rotationPointY = -9.5F + MathHelper.cos((i * 2 + f2) * 0.25F);
		}
		
		if(((EntityWormHole)entity).deathTicks == 0) {
			bodyMain.rotationPointY = -28F;
			body2.rotationPointY = -28F;
			body3.rotationPointY = -28F;
			body4.rotationPointY = -28F;
			body5.rotationPointY = -28F;
		}
	}
}
