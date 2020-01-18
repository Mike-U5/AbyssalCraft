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

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.shinoow.abyssalcraft.client.model.entity.ModelDragonBoss;
import com.shinoow.abyssalcraft.common.entity.EntityDragonBoss;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderDragonBoss extends RenderLiving {

	/** Scale of the model to use */
	private float scale = 1.5F;

	private static final ResourceLocation field_110842_f = new ResourceLocation("abyssalcraft:textures/model/boss/dragonboss_exploding.png");
	/// private static final ResourceLocation field_110843_g = new
	/// ResourceLocation("textures/entity/endercrystal/endercrystal_beam.png");
	private static final ResourceLocation field_110845_h = new ResourceLocation("abyssalcraft:textures/model/boss/dragonboss_eyes.png");
	private static final ResourceLocation field_110844_k = new ResourceLocation("abyssalcraft:textures/model/boss/dragonboss.png");

	/** An instance of the dragon model in RenderDragon */
	protected ModelDragonBoss modelDragon;

	public RenderDragonBoss() {
		super(new ModelDragonBoss(0.0F), 0.9F);
		modelDragon = (ModelDragonBoss) mainModel;
		setRenderPassModel(mainModel);
	}

	/**
	 * Applies the scale to the transform matrix
	 */
	protected void preRenderScale(EntityDragonBoss par1EntityDragonMinion, float par2) {
		GL11.glScalef(scale, scale, scale);
	}

	/**
	 * Used to rotate the dragon as a whole in RenderDragon. It's called in the
	 * rotateCorpse method.
	 */
	protected void rotateDragonBody(EntityDragonBoss dragon, float par2, float par3, float par4) {
		float f3 = (float) dragon.getMovementOffsets(7, par4)[0];
		float f4 = (float) (dragon.getMovementOffsets(5, par4)[1] - dragon.getMovementOffsets(10, par4)[1]);
		GL11.glRotatef(-f3, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(f4 * 10.0F, 1.0F, 0.0F, 0.0F);
		GL11.glTranslatef(0.0F, 0.0F, 1.0F);

		if (dragon.deathTime > 0) {
			float f5 = (dragon.deathTime + par4 - 1.0F) / 20.0F * 1.6F;
			f5 = MathHelper.sqrt_float(f5);

			if (f5 > 1.0F) {
				f5 = 1.0F;
			}

			GL11.glRotatef(f5 * getDeathMaxRotation(dragon), 0.0F, 0.0F, 1.0F);
		}
	}

	/**
	 * Renders the dragon model. Called by renderModel.
	 */
	protected void renderDragonModel(EntityDragonBoss dragon, float par2, float par3, float par4, float par5, float par6, float par7) {
		if (dragon.deathTicks > 0) {
			float f6 = dragon.deathTicks / 200.0F;
			GL11.glDepthFunc(GL11.GL_LEQUAL);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glAlphaFunc(GL11.GL_GREATER, f6);
			bindTexture(field_110842_f);
			mainModel.render(dragon, par2, par3, par4, par5, par6, par7);
			GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
			GL11.glDepthFunc(GL11.GL_EQUAL);
		}

		bindEntityTexture(dragon);
		mainModel.render(dragon, par2, par3, par4, par5, par6, par7);

		if (dragon.hurtTime > 0) {
			GL11.glDepthFunc(GL11.GL_EQUAL);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glColor4f(1.0F, 0.0F, 0.0F, 0.5F);
			mainModel.render(dragon, par2, par3, par4, par5, par6, par7);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glDepthFunc(GL11.GL_LEQUAL);
		}
	}

	/**
	 * Renders the dragon, along with its dying animation
	 */
	public void renderDragon(EntityDragonBoss dragon, double par2, double par4, double par6, float par8, float par9) {
		BossStatus.setBossStatus(dragon, false);
		super.doRender(dragon, par2, par4, par6, par8, par9);

		/**
		 * if (par1entitydragonboss.healingcircle != null) { float f2 =
		 * EntityDragonMinion.innerRotation + par9; float f3 = MathHelper.sin(f2 * 0.2F)
		 * / 2.0F + 0.5F; f3 = (f3 * f3 + f3) * 0.2F; float f4 =
		 * (float)(par1entitydragonboss.healingcircle.posX - par1entitydragonboss.posX -
		 * (par1entitydragonboss.prevPosX - par1entitydragonboss.posX) * (1.0F - par9));
		 * float f5 = (float)(f3 + par1entitydragonboss.healingcircle.posY - 1.0D -
		 * par1entitydragonboss.posY - (par1entitydragonboss.prevPosY -
		 * par1entitydragonboss.posY) * (1.0F - par9)); float f6 =
		 * (float)(par1entitydragonboss.healingcircle.posZ - par1entitydragonboss.posZ -
		 * (par1entitydragonboss.prevPosZ - par1entitydragonboss.posZ) * (1.0F - par9));
		 * float f7 = MathHelper.sqrt_float(f4 * f4 + f6 * f6); float f8 =
		 * MathHelper.sqrt_float(f4 * f4 + f5 * f5 + f6 * f6); GL11.glPushMatrix();
		 * GL11.glTranslatef((float)par2, (float)par4 + 2.0F, (float)par6);
		 * GL11.glRotatef((float)-Math.atan2(f6, f4) * 180.0F / (float)Math.PI - 90.0F,
		 * 0.0F, 1.0F, 0.0F); GL11.glRotatef((float)-Math.atan2(f7, f5) * 180.0F /
		 * (float)Math.PI - 90.0F, 1.0F, 0.0F, 0.0F); Tessellator tessellator =
		 * Tessellator.instance; RenderHelper.disableStandardItemLighting();
		 * GL11.glDisable(GL11.GL_CULL_FACE); bindTexture(field_110843_g);
		 * GL11.glShadeModel(GL11.GL_SMOOTH); float f9 = 0.0F -
		 * (par1entitydragonboss.ticksExisted + par9) * 0.01F; float f10 =
		 * MathHelper.sqrt_float(f4 * f4 + f5 * f5 + f6 * f6) / 32.0F -
		 * (par1entitydragonboss.ticksExisted + par9) * 0.01F;
		 * tessellator.startDrawing(5); byte b0 = 8;
		 * 
		 * for (int i = 0; i <= b0; ++i) { float f11 = MathHelper.sin(i % b0 *
		 * (float)Math.PI * 2.0F / b0) * 0.75F; float f12 = MathHelper.cos(i % b0 *
		 * (float)Math.PI * 2.0F / b0) * 0.75F; float f13 = i % b0 * 1.0F / b0;
		 * tessellator.setColorOpaque_I(0); tessellator.addVertexWithUV(f11 * 0.2F, f12
		 * * 0.2F, 0.0D, f13, f10); tessellator.setColorOpaque_I(16777215);
		 * tessellator.addVertexWithUV(f11, f12, f8, f13, f9); }
		 * 
		 * tessellator.draw(); GL11.glEnable(GL11.GL_CULL_FACE);
		 * GL11.glShadeModel(GL11.GL_FLAT); RenderHelper.enableStandardItemLighting();
		 * GL11.glPopMatrix(); }
		 **/
	}

	protected ResourceLocation func_110841_a(EntityDragonBoss par1EntityDragonBoss) {
		return field_110844_k;
	}

	/**
	 * Renders the animation for when an enderdragon dies
	 */
	protected void renderDragonDying(EntityDragonBoss dragon, float par2) {
		super.renderEquippedItems(dragon, par2);
		Tessellator tessellator = Tessellator.instance;

		if (dragon.deathTicks > 0) {
			RenderHelper.disableStandardItemLighting();
			float f1 = (dragon.deathTicks + par2) / 200.0F;
			float f2 = 0.0F;

			if (f1 > 0.8F)
				f2 = (f1 - 0.8F) / 0.2F;

			Random random = new Random(432L);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glShadeModel(GL11.GL_SMOOTH);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glDepthMask(false);
			GL11.glPushMatrix();
			GL11.glTranslatef(0.0F, -1.0F, -2.0F);

			for (int i = 0; i < (f1 + f1 * f1) / 2.0F * 60.0F; ++i) {
				GL11.glRotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(random.nextFloat() * 360.0F + f1 * 90.0F, 0.0F, 0.0F, 1.0F);
				tessellator.startDrawing(6);
				float f3 = random.nextFloat() * 20.0F + 5.0F + f2 * 10.0F;
				float f4 = random.nextFloat() * 2.0F + 1.0F + f2 * 2.0F;
				tessellator.setColorRGBA_I(16777215, (int) (255.0F * (1.0F - f2)));
				tessellator.addVertex(0.0D, 0.0D, 0.0D);
				tessellator.setColorRGBA_I(16711935, 0);
				tessellator.addVertex(-0.866D * f4, f3, -0.5F * f4);
				tessellator.addVertex(0.866D * f4, f3, -0.5F * f4);
				tessellator.addVertex(0.0D, f3, 1.0F * f4);
				tessellator.addVertex(-0.866D * f4, f3, -0.5F * f4);
				tessellator.draw();
			}

			GL11.glPopMatrix();
			GL11.glDepthMask(true);
			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glShadeModel(GL11.GL_FLAT);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			RenderHelper.enableStandardItemLighting();
		}
	}

	/**
	 * Renders the overlay for glowing eyes and the mouth. Called by
	 * shouldRenderPass.
	 */
	protected int renderGlow(EntityDragonBoss entity, int par2, float par3) {
		if (par2 == 1) {
			GL11.glDepthFunc(GL11.GL_LEQUAL);
		}

		if (par2 != 0) {
			return -1;
		} else {
			bindTexture(field_110845_h);
			float f1 = 1.0F;
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDepthFunc(GL11.GL_EQUAL);
			char c0 = 61680;
			int j = c0 % 65536;
			int k = c0 / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, f1);
			return 1;
		}
	}

	public void doRenderLiving(EntityLiving entity, double par2, double par4, double par6, float par8, float par9) {
		renderDragon((EntityDragonBoss) entity, par2, par4, par6, par8, par9);
	}

	/**
	 * Queries whether should render the specified pass or not.
	 */
	@Override
	protected int shouldRenderPass(EntityLivingBase entity, int par2, float par3) {
		return renderGlow((EntityDragonBoss) entity, par2, par3);
	}

	@Override
	protected void renderEquippedItems(EntityLivingBase entity, float par2) {
		renderDragonDying((EntityDragonBoss) entity, par2);
	}

	@Override
	protected void rotateCorpse(EntityLivingBase entity, float par2, float par3, float par4) {
		rotateDragonBody((EntityDragonBoss) entity, par2, par3, par4);
	}

	/**
	 * Renders the model in RenderLiving
	 */
	@Override
	protected void renderModel(EntityLivingBase entity, float par2, float par3, float par4, float par5, float par6, float par7) {
		renderDragonModel((EntityDragonBoss) entity, par2, par3, par4, par5, par6, par7);
	}

	public void renderPlayer(EntityLivingBase entity, double par2, double par4, double par6, float par8, float par9) {
		renderDragon((EntityDragonBoss) entity, par2, par4, par6, par8, par9);
	}

	/**
	 * Allows the render to do any OpenGL state modifications necessary before the
	 * model is rendered. Args: entityLiving, partialTickTime
	 */
	@Override
	protected void preRenderCallback(EntityLivingBase entity, float par2) {
		preRenderScale((EntityDragonBoss) entity, par2);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return func_110841_a((EntityDragonBoss) entity);
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method,
	 * always casting down its argument and then handing it off to a worker function
	 * which does the actual work. In all probabilty, the class Render is generic
	 * (Render<T extends Entity) and this method has signature public void
	 * doRender(T entity, double d, double d1, double d2, float f, float f1). But
	 * JAD is pre 1.5 so doesn't do that.
	 */
	@Override
	public void doRender(Entity entity, double par2, double par4, double par6, float par8, float par9) {
		renderDragon((EntityDragonBoss) entity, par2, par4, par6, par8, par9);
	}

}
