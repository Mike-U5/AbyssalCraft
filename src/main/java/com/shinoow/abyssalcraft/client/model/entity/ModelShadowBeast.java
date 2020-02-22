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

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelShadowBeast extends ModelBase {

	public ModelRenderer head;
	public ModelRenderer[] bteeth = new ModelRenderer[5];
	public ModelRenderer[] steeth = new ModelRenderer[4];
	public ModelRenderer jaw;
	public ModelRenderer spine;
	public ModelRenderer leftshoulder;
	public ModelRenderer lsspike;
	public ModelRenderer larm1;
	public ModelRenderer larm2;
	public ModelRenderer laspike1;
	public ModelRenderer laspike2;
	public ModelRenderer[] lfingers = new ModelRenderer[4];
	public ModelRenderer rightshoulder;
	public ModelRenderer rsspike;
	public ModelRenderer rarm1;
	public ModelRenderer rarm2;
	public ModelRenderer raspike1;
	public ModelRenderer raspike2;
	public ModelRenderer[] rfingers = new ModelRenderer[4];
	public ModelRenderer pelvis;
	public ModelRenderer leftleg;
	public ModelRenderer rightleg;

	public ModelShadowBeast() {
		textureWidth = 128;
		textureHeight = 64;
		
		final float y = 5.5F;
		
		// Head
		head = new ModelRenderer(this, 0, 0);
		head.addBox(-4.5F, -9F, -4.5F, 9, 9, 9);
		head.setRotationPoint(0F, y - 13F, -1F);
		head.setTextureSize(128, 64);
		head.mirror = true;
		setRotation(head, 0F, 0F, 0F);
		
		// Top Teeth Row
		for (int i = 0; i < bteeth.length; i++) {
			bteeth[i] = new ModelRenderer(this, 37, 4);
			bteeth[i].addBox(0F, 0F, 0F, 1, 2, 1);
			bteeth[i].setRotationPoint(-4.5F + (i * 2F), 0F, -4.5F);
			bteeth[i].setTextureSize(128, 64);
			bteeth[i].mirror = true;
			setRotation(bteeth[i], 0F, 0F, 0F);
			head.addChild(bteeth[i]);
		}
		
		// Jaw
		jaw = new ModelRenderer(this, 36, 0);
		jaw.addBox(-4.5F, 1.9F, -5F, 9, 1, 9);
		jaw.setRotationPoint(0, 0, 0);
		jaw.setTextureSize(128, 64);
		jaw.mirror = true;
		setRotation(jaw, 0.4461433F, 0F, 0F);
		head.addChild(jaw);
		
		// Bottom Teeth Row
		for (int i = 0; i < steeth.length; i++) {
			steeth[i] = new ModelRenderer(this, 37, 4);
			steeth[i].addBox(0F, 0F, 0F, 1, 1, 1);
			steeth[i].setRotationPoint(-3.5F + (i * 2F), 0.9F, -5F);
			steeth[i].setTextureSize(128, 64);
			steeth[i].mirror = true;
			setRotation(steeth[i], 0F, 0F, 0F);
			jaw.addChild(steeth[i]);
		}
		// Body
		spine = new ModelRenderer(this, 72, 0);
		spine.addBox(-2.5F, 0F, -2.5F, 5, 20, 5);
		spine.setRotationPoint(0F, y - 12F, -1F);
		spine.setTextureSize(128, 64);
		spine.mirror = true;
		setRotation(spine, 0.2974289F, 0F, 0F);
		
		leftshoulder = new ModelRenderer(this, 41, 11);
		leftshoulder.addBox(-8F, 0F, 0F, 8, 5, 7);
		leftshoulder.setRotationPoint(9.5F, y - 8F, -3F);
		leftshoulder.setTextureSize(128, 64);
		leftshoulder.mirror = true;
		setRotation(leftshoulder, 0F, 0F, 0.111544F);
		
		lsspike = new ModelRenderer(this, 0, 0);
		lsspike.addBox(0F, -4F, 0F, 1, 5, 1);
		lsspike.setRotationPoint(7F, y - 9F, 0F);
		lsspike.setTextureSize(128, 64);
		lsspike.mirror = true;
		setRotation(lsspike, 0F, 0F, 0.111544F);
		
		larm1 = new ModelRenderer(this, 0, 19);
		larm1.addBox(0F, -1F, -1.5F, 3, 11, 3);
		larm1.setRotationPoint(5F, y - 6.5F, 0.5F);
		larm1.setTextureSize(128, 64);
		larm1.mirror = true;
		setRotation(larm1, 0F, 0F, 0F);
		
		larm2 = new ModelRenderer(this, 0, 19);
		larm2.addBox(0F, 6F, 5F, 3, 7, 3);
		larm2.setRotationPoint(0, 0, 0);
		larm2.setTextureSize(128, 64);
		larm2.mirror = true;
		setRotation(larm2, -0.7807508F, 0F, 0F);
		larm1.addChild(larm2);
		
		laspike1 = new ModelRenderer(this, 0, 0);
		laspike1.addBox(6F, -1F, -0.5F, 1, 4, 1);
		laspike1.setRotationPoint(0, 0, 0);
		laspike1.setTextureSize(128, 64);
		laspike1.mirror = true;
		setRotation(laspike1, 0F, 0F, 0.8179311F);
		larm1.addChild(laspike1);
		
		laspike2 = new ModelRenderer(this, 34, 0);
		laspike2.addBox(-1F, 10F, 5.5F, 4, 1, 1);
		laspike2.setRotationPoint(0, 0, 0);
		laspike2.setTextureSize(128, 64);
		laspike2.mirror = true;
		setRotation(laspike2, -0.7744724F, -0.2617994F, -0.2617994F);
		larm1.addChild(laspike2);
		
		// Fingers on left hand
		final float[] lfxo = {3F, -1F, 1F, 1F};
		final float[] lfzo = {6F, 6F, 4F, 8F};
		for (int i = 0; i < lfingers.length; i++) {
			lfingers[i] = new ModelRenderer(this, 30, 0);
			lfingers[i].addBox(lfxo[i], 11F, lfzo[i], 1, 5, 1);
			lfingers[i].setRotationPoint(0, 0, 0);
			lfingers[i].setTextureSize(128, 64);
			lfingers[i].mirror = true;
			setRotation(lfingers[i], -0.7807556F, 0F, 0F);
			larm1.addChild(lfingers[i]);
		}
		
		rightshoulder = new ModelRenderer(this, 41, 11);
		rightshoulder.addBox(0F, 0F, 0F, 8, 5, 7);
		rightshoulder.setRotationPoint(-9.5F, y - 8F, -3F);
		rightshoulder.setTextureSize(128, 64);
		rightshoulder.mirror = true;
		setRotation(rightshoulder, 0F, 0F, -0.1115358F);
		
		rsspike = new ModelRenderer(this, 0, 0);
		rsspike.addBox(0F, -4F, 0F, 1, 5, 1);
		rsspike.setRotationPoint(-8F, y - 9F, 0F);
		rsspike.setTextureSize(128, 64);
		rsspike.mirror = true;
		setRotation(rsspike, 0F, 0F, -0.111544F);
		
		rarm1 = new ModelRenderer(this, 0, 19);
		rarm1.addBox(-3F, -1F, -1.5F, 3, 11, 3);
		rarm1.setRotationPoint(-5F, y - 6.5F, 0.5F);
		rarm1.setTextureSize(128, 64);
		rarm1.mirror = true;
		setRotation(rarm1, 0F, 0F, 0F);
		
		rarm2 = new ModelRenderer(this, 0, 19);
		rarm2.addBox(-3F, 6F, 5F, 3, 7, 3);
		rarm2.setRotationPoint(0, 0, 0);
		rarm2.setTextureSize(128, 64);
		rarm2.mirror = true;
		setRotation(rarm2, -0.7807508F, 0F, 0F);
		rarm1.addChild(rarm2);
		
		raspike1 = new ModelRenderer(this, 0, 0);
		raspike1.addBox(-7F, -1F, -0.5F, 1, 4, 1);
		raspike1.setRotationPoint(0, 0, 0);
		raspike1.setTextureSize(128, 64);
		raspike1.mirror = true;
		setRotation(raspike1, 0F, 0F, -0.8179294F);
		rarm1.addChild(raspike1);
		
		raspike2 = new ModelRenderer(this, 34, 0);
		raspike2.addBox(-3F, 10F, 5.5F, 4, 1, 1);
		raspike2.setRotationPoint(0, 0, 0);
		raspike2.setTextureSize(128, 64);
		raspike2.mirror = true;
		setRotation(raspike2, -0.7807556F, 0.2617994F, 0.2617994F);
		rarm1.addChild(raspike2);
		
		// Fingers on right hand
		final float[] rfxo = {0F, -4F, -2F, -2F};
		final float[] rfzo = {6F, 6F, 4F, 8F};
		for (int i = 0; i < lfingers.length; i++) {
			rfingers[i] = new ModelRenderer(this, 30, 0);
			rfingers[i].addBox(rfxo[i], 11F, rfzo[i], 1, 5, 1);
			rfingers[i].setRotationPoint(0, 0, 0);
			rfingers[i].setTextureSize(128, 64);
			rfingers[i].mirror = true;
			setRotation(rfingers[i], -0.7807556F, 0F, 0F);
			rarm1.addChild(rfingers[i]);
		}
		
		pelvis = new ModelRenderer(this, 37, 24);
		pelvis.addBox(0F, 0F, -1F, 12, 5, 5);
		pelvis.setRotationPoint(-6F, y + 7F, 3F);
		pelvis.setTextureSize(128, 64);
		pelvis.mirror = true;
		setRotation(pelvis, 0.2974216F, 0F, 0F);
		leftleg = new ModelRenderer(this, 16, 18);
		leftleg.addBox(-2.5F, 0F, -2.5F, 5, 8, 5);
		leftleg.setRotationPoint(3.5F, y + 11F, 6F);
		leftleg.setTextureSize(128, 64);
		leftleg.mirror = true;
		setRotation(leftleg, 0.2974216F, 0F, 0F);
		rightleg = new ModelRenderer(this, 16, 18);
		rightleg.addBox(-2.5F, 0F, -2.5F, 5, 8, 5);
		rightleg.setRotationPoint(-3.5F, y + 11F, 6F);
		rightleg.setTextureSize(128, 64);
		rightleg.mirror = true;
		setRotation(rightleg, 0.2974216F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		head.render(f5);
		spine.render(f5);
		leftshoulder.render(f5);
		lsspike.render(f5);
		larm1.render(f5);
		rightshoulder.render(f5);
		rsspike.render(f5);
		rarm1.render(f5);
		pelvis.render(f5);
		leftleg.render(f5);
		rightleg.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		head.rotateAngleY = f3 / (180F / (float) Math.PI);
		head.rotateAngleX = f4 / (180F / (float) Math.PI);

		rightleg.rotateAngleX = MathHelper.cos(f * 0.3331F) * 0.07F * f1;
		rightleg.rotateAngleY = 0.0F;

		leftleg.rotateAngleX = MathHelper.cos(f * 0.3331F + (float) Math.PI) * 0.07F * f1;
		leftleg.rotateAngleY = 0.0F;

		float f6 = MathHelper.sin(onGround * (float) Math.PI);
		float f7 = MathHelper.sin((1.0F - (1.0F - onGround) * (1.0F - onGround)) * (float) Math.PI);
		rarm1.rotateAngleZ = 0.0F;
		larm1.rotateAngleZ = 0.0F;
		rarm1.rotateAngleY = -(0.1F - f6 * 0.6F);
		larm1.rotateAngleY = 0.1F - f6 * 0.6F;
		rarm1.rotateAngleX = -((float) Math.PI / 7.2F);
		larm1.rotateAngleX = -((float) Math.PI / 7.2F);
		rarm1.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
		larm1.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
		rarm1.rotateAngleZ += MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F;
		larm1.rotateAngleZ -= MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F;
		rarm1.rotateAngleX += MathHelper.sin(f2 * 0.067F) * 0.05F;
		larm1.rotateAngleX -= MathHelper.sin(f2 * 0.067F) * 0.05F;
	}
}
