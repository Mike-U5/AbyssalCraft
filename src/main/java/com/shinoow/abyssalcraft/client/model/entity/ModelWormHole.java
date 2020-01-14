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
	public ModelRenderer head;
	public ModelRenderer mask1;
	public ModelRenderer mask2;
	public ModelRenderer facetentacle1;
	public ModelRenderer facetentacle2;
	public ModelRenderer facetentacle3;
	public ModelRenderer body1;
	public ModelRenderer body2;
	public ModelRenderer body3;
	public ModelRenderer body4;
	public ModelRenderer body5;
	public ModelRenderer eye1;
	public ModelRenderer eye2;
	public ModelRenderer arm;
	public ModelRenderer Cube;

	public ModelWormHole(boolean renderStaff) {
		textureWidth = 128;
		textureHeight = 64;

		head = new ModelRenderer(this, 72, 0);
		head.addBox(-5F, -10F, -5F, 10, 10, 10);
		head.setRotationPoint(2F, -28F, 1F);
		head.setTextureSize(64, 32);
		head.mirror = true;
		setRotation(head, 0F, 0F, 0F);
		mask1 = new ModelRenderer(this, 102, 0);
		mask1.addBox(-3.5F, -10F, -7F, 6, 8, 1);
		mask1.setRotationPoint(0,0,0);
		mask1.setTextureSize(64, 32);
		mask1.mirror = true;
		setRotation(mask1, 0F, 0.3490659F, 0F);
		head.addChild(mask1);
		mask2 = new ModelRenderer(this, 102, 0);
		mask2.addBox(-2.5F, -10F, -7F, 6, 8, 1);
		mask2.setRotationPoint(0,0,0);
		mask2.setTextureSize(64, 32);
		mask2.mirror = true;
		setRotation(mask2, 0F, -0.3490659F, 0F);
		head.addChild(mask2);
		facetentacle1 = new ModelRenderer(this, 116, 0);
		facetentacle1.addBox(0F, 0F, 0F, 2, 6, 2);
		facetentacle1.setRotationPoint(-4F, -2F, -7F);
		facetentacle1.setTextureSize(64, 32);
		facetentacle1.mirror = true;
		setRotation(facetentacle1, 0F, 0F, 0F);
		head.addChild(facetentacle1);
		facetentacle2 = new ModelRenderer(this, 116, 0);
		facetentacle2.addBox(0F, 0F, 0F, 2, 6, 2);
		facetentacle2.setRotationPoint(-1F, -2F, -7F);
		facetentacle2.setTextureSize(64, 32);
		facetentacle2.mirror = true;
		setRotation(facetentacle2, 0F, 0F, 0F);
		head.addChild(facetentacle2);
		facetentacle3 = new ModelRenderer(this, 116, 0);
		facetentacle3.addBox(0F, 0F, 0F, 2, 6, 2);
		facetentacle3.setRotationPoint(2F, -2F, -7F);
		facetentacle3.setTextureSize(64, 32);
		facetentacle3.mirror = true;
		setRotation(facetentacle3, 0F, 0F, 0F);
		head.addChild(facetentacle3);
		body1 = new ModelRenderer(this, 0, 0);
		body1.addBox(0F, 0F, 0F, 18, 22, 5);
		body1.setRotationPoint(-7F, -28F, 0F);
		body1.setTextureSize(64, 32);
		body1.mirror = true;
		setRotation(body1, 0F, 0F, 0F);
		body2 = new ModelRenderer(this, 46, 0);
		body2.addBox(0F, 0F, 0F, 5, 26, 1);
		body2.setRotationPoint(-7F, -28F, -1F);
		body2.setTextureSize(64, 32);
		body2.mirror = true;
		setRotation(body2, 0F, 0F, 0F);
		body3 = new ModelRenderer(this, 58, 0);
		body3.addBox(0F, 0F, 0F, 5, 26, 1);
		body3.setRotationPoint(6F, -28F, -1F);
		body3.setTextureSize(64, 32);
		body3.mirror = true;
		setRotation(body3, 0F, 0F, 0F);
		body4 = new ModelRenderer(this, 46, 0);
		body4.addBox(0F, 0F, 0F, 4, 26, 1);
		body4.setRotationPoint(-7F, -28F, -2F);
		body4.setTextureSize(64, 32);
		body4.mirror = true;
		setRotation(body4, 0F, 0F, 0F);
		body5 = new ModelRenderer(this, 59, 0);
		body5.addBox(0F, 0F, 0F, 4, 26, 1);
		body5.setRotationPoint(7F, -28F, -2F);
		body5.setTextureSize(64, 32);
		body5.mirror = true;
		setRotation(body5, 0F, 0F, 0F);
		eye1 = new ModelRenderer(this, 70, 0);
		eye1.addBox(0F, 0F, 0F, 5, 5, 1);
		eye1.setRotationPoint(-0.5F, -21F, -1F);
		eye1.setTextureSize(64, 32);
		eye1.mirror = true;
		setRotation(eye1, 0F, 0F, 0F);
		eye2 = new ModelRenderer(this, 70, 6);
		eye2.addBox(-1F, -1F, 0F, 2, 2, 1);
		eye2.setRotationPoint(2F, -18.5F, -1.5F);
		eye2.setTextureSize(64, 32);
		eye2.mirror = true;
		setRotation(eye2, 0F, 0F, 0F);
		arm = new ModelRenderer(this, 12, 33);
		arm.addBox(-1.5F, -9F, -1.5F, 3, 9, 3);
		arm.setRotationPoint(-8.2F, 6.7F, 3.4F);
		arm.setTextureSize(64, 32);
		arm.mirror = true;
		setRotation(arm, 0F, -0.2617994F, -0.5235988F);
		Cube = new ModelRenderer(this, 62, 42);
		Cube.addBox(-14.0F, -20.0F, -8.5F, 2, 2, 2);
		Cube.setRotationPoint(0,0,0);
		Cube.setTextureSize(64, 32);
		Cube.mirror = true;
		setRotation(Cube, 0F, 0.4833219F, 0.5205006F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		head.render(f5);
		body1.render(f5);
		body2.render(f5);
		body3.render(f5);
		body4.render(f5);
		body5.render(f5);
		eye1.render(f5);
		eye2.render(f5);
		arm.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		head.rotateAngleY = f3 / (180F / (float)Math.PI);
		head.rotateAngleX = f4 / (180F / (float)Math.PI);
		
		setRotation(arm, 0F, -0.2617994F, -0.5235988F);
		float f6;
		float f7;

		if (onGround > -9990.0F) {
			f6 = onGround;
			arm.rotateAngleY += MathHelper.sin(MathHelper.sqrt_float(f6) * (float)Math.PI * 2.0F) * 0.2F;
			f6 = 1.0F - onGround;
			f6 *= f6;
			f6 *= f6;
			f6 = 1.0F - f6;
			f7 = MathHelper.sin(f6 * (float)Math.PI);
			float f8 = MathHelper.sin(onGround * (float)Math.PI) * 0.75F;
			arm.rotateAngleX = (float)(arm.rotateAngleX + (f7 * 1.2D + f8));
			arm.rotateAngleY += MathHelper.sin(MathHelper.sqrt_float(f6) * (float)Math.PI * 2.0F) * 0.2F * 2.0F;
		}

		if(((EntityWormHole)entity).deathTicks <= 800 && ((EntityWormHole)entity).deathTicks > 0){
			head.rotateAngleX = 20F;
			eye1.isHidden = true;
			eye2.isHidden = true;
			facetentacle1.isHidden = true;
			facetentacle2.isHidden = true;
			facetentacle3.isHidden = true;
			arm.isHidden = true;

			for(int i = 0; i < 4; i++){
				head.rotationPointY = -9.5F + MathHelper.cos((i * 2 + f2) * 0.25F);
				body1.rotationPointY = -9.5F + MathHelper.cos((i * 2 + f2) * 0.25F);
				body2.rotationPointY = -9.5F + MathHelper.cos((i * 2 + f2) * 0.25F);
				body3.rotationPointY = -9.5F + MathHelper.cos((i * 2 + f2) * 0.25F);
				body4.rotationPointY = -9.5F + MathHelper.cos((i * 2 + f2) * 0.25F);
				body5.rotationPointY = -9.5F + MathHelper.cos((i * 2 + f2) * 0.25F);
			}
		}
		if(((EntityWormHole)entity).deathTicks == 0){
			head.rotateAngleX = f4 / (180F / (float)Math.PI);
			eye1.isHidden = false;
			eye2.isHidden = false;
			facetentacle1.isHidden = false;
			facetentacle2.isHidden = false;
			facetentacle3.isHidden = false;
			arm.isHidden = false;
			head.rotationPointY = -28F;
			body1.rotationPointY = -28F;
			body2.rotationPointY = -28F;
			body3.rotationPointY = -28F;
			body4.rotationPointY = -28F;
			body5.rotationPointY = -28F;
		}
	}
}
