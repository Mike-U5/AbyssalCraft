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
package com.shinoow.abyssalcraft.client.gui.necronomicon.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ButtonHome extends GuiButton {
	private final boolean leftOrRight;

	public ButtonHome(int par1, int par2, int par3, boolean leftOrRight) {
		super(par1, par2, par3, 18, 10, "");
		this.leftOrRight = leftOrRight;
	}

	/**
	 * Draws this button to the screen.
	 */
	@Override
	public void drawButton(Minecraft mc, int mX, int mY) {
		if (visible) {
			final int textureWidth = 18;
			final int textureHeight = 10;
			final boolean hoveringOver = mX >= xPosition && mY >= yPosition && mX < xPosition + width && mY < yPosition + height;
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			mc.getTextureManager().bindTexture(new ResourceLocation("abyssalcraft:textures/gui/necronomicon.png"));
			int textureX = 107;
			int textureY = 192;

			if (hoveringOver) {
				textureX += textureWidth;
			}

			drawTexturedModalRect(xPosition, yPosition, textureX, textureY, textureWidth, textureHeight);
		}
	}
}
