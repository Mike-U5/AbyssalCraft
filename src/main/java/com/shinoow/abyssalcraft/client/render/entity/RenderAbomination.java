package com.shinoow.abyssalcraft.client.render.entity;

import com.shinoow.abyssalcraft.client.model.entity.ModelAbomination;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAbomination;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderAbomination extends RenderLiving {
	private static final ResourceLocation mobTexture = new ResourceLocation("abyssalcraft:textures/model/anti/abomination.png");

	public RenderAbomination() {
		super(new ModelAbomination(), 0.5F);
	}

	public void doRender(EntityAbomination entity, double par2, double par4, double par6, float par8, float par9) {
		super.doRender(entity, par2, par4, par6, par8, par9);
	}

	@Override
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
		doRender((EntityAbomination) par1Entity, par2, par4, par6, par8, par9);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return mobTexture;
	}
}