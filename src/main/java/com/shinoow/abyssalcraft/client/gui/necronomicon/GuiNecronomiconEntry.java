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
package com.shinoow.abyssalcraft.client.gui.necronomicon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.google.common.collect.Lists;
import com.shinoow.abyssalcraft.api.necronomicon.CraftingStack;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData.Chapter;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData.Page;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;
import com.shinoow.abyssalcraft.api.ritual.RitualRegistry;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonCategory;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonHome;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonNextPage;
import com.shinoow.abyssalcraft.client.lib.GuiRenderHelper;
import com.shinoow.abyssalcraft.client.lib.NecronomiconResources;
import com.shinoow.abyssalcraft.client.lib.NecronomiconText;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiNecronomiconEntry extends GuiNecronomicon {

	private ButtonNextPage buttonNextPage;
	private ButtonNextPage buttonPreviousPage;
	private ButtonHome buttonHome;
	private GuiButton buttonDone;
	private final ButtonCategory[] buttons;
	private final NecroData data;
	private final GuiNecronomicon parent;
	private final Item icon;
	private final int dimId;
	private int chapterId = -1;

	public GuiNecronomiconEntry(int bookType, NecroData nd, GuiNecronomicon gui, Item item) {
		super(bookType);
		data = nd;
		parent = gui;
		icon = item;
		buttons = new ButtonCategory[data.getChapters().length];
		dimId = findDimId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui(){
		buttonList.clear();
		Keyboard.enableRepeatEvents(true);

		buttonList.add(buttonDone = new GuiButton(0, width / 2 - 100, 4 + guiHeight, 200, 20, I18n.format("gui.done", new Object[0])));

		int w = (width - guiWidth) / 2;
		byte b0 = 2;
		buttonList.add(buttonNextPage = new ButtonNextPage(1, w + 215, b0 + 154, true));
		buttonList.add(buttonPreviousPage = new ButtonNextPage(2, w + 18, b0 + 154, false));
		buttonList.add(buttonHome = new ButtonHome(3, w + 119, b0 + 167, false));
		
		int posX = w + 14;
		int posY = b0 + 24;
		if(data != null) {
			// Place Chapters
			for(int i = 0; i < data.getChapters().length; i++) {
				buttonList.add(buttons[i] = new ButtonCategory(4 + i, posX, posY, this, data.getChapters()[i].getTitle(), icon));
				posY += 17;
			}
			
			//ID finder
			if (dimId >= 0) {
				buttonList.add(new ButtonCategory(42, posX, posY, this, NecronomiconText.LABEL_RITUALS, icon));
			}
			
		}
		
		updateButtons();
	}
	
	private int findDimId() {
		switch(data.getIdentifier()) {
			case "overworld": return 0;
			case "abyssalwasteland": return 1;
			case "dreadlands": return 2;
			case "omothol": return 3;
			case "darkrealm": return 4;
			default: return -1;
		}
	}

	private void updateButtons() {
		buttonNextPage.visible = currTurnup < getTurnupLimit() - 1 && isInfo;
		buttonPreviousPage.visible = true;
		buttonDone.visible = true;
		if(data != null) {
			for(int i = 0; i < data.getChapters().length; i++) {
				buttons[i].visible = !isInfo;
			}
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if (button.enabled) {
			// Controls
			if (button.id == 0) {
				mc.displayGuiScreen((GuiScreen)null);
			} else if(button.id == 1) {
				if (currTurnup < getTurnupLimit() -1) {
					++currTurnup;
				}
			} else if (button.id == 2) {
				if(currTurnup == 0 && !isInfo) {
					mc.displayGuiScreen(parent);
				} else if(currTurnup == 0 && isInfo) {
					initGui();
					isInfo = false;
					setTurnupLimit(2);
				} else if (currTurnup > 0) {
					--currTurnup;
				}
			} else if (button.id == 3) {
				mc.displayGuiScreen(parent);
			} else if (button.id == 42) {
				if(hasRituals(dimId)) {
					mc.displayGuiScreen(new GuiNecronomiconRitualEntry(getBookType(), this, dimId));
				}
			// Categories
			} else if (button.id >= 4) {
				chapterId = button.id - 4;
				isInfo = true;
				drawButtons();
			}
			updateButtons();
		}
	}

	@SuppressWarnings("unchecked")
	private void drawButtons(){
		buttonList.clear();
		buttonList.add(buttonDone = new GuiButton(0, width / 2 - 100, 4 + guiHeight, 200, 20, I18n.format("gui.done", new Object[0])));

		int i = (width - guiWidth) / 2;
		byte b0 = 2;
		buttonList.add(buttonNextPage = new ButtonNextPage(1, i + 215, b0 + 154, true));
		buttonList.add(buttonPreviousPage = new ButtonNextPage(2, i + 18, b0 + 154, false));
	}
	
	private boolean hasRituals(int book) {
		List<NecronomiconRitual> rituals = Lists.newArrayList();
		for(NecronomiconRitual ritual : RitualRegistry.instance().getRituals()) {
			if(ritual.getBookType() == book) {
				rituals.add(ritual);
			}
		}
		return !rituals.isEmpty();
	}

	@Override
	protected void drawInformationText(int x, int y){
		drawChapter(data.getChapters()[chapterId], x, y);
		updateButtons();
	}

	private void drawChapter(Chapter chapter, int x, int y){
		int k = (width - guiWidth) / 2;
		byte b0 = 2;
		String text = StatCollector.translateToLocal(chapter.getTitle());

		fontRendererObj.drawSplitString(text, k + 20, b0 + 16, 116, 0xC40000);
		setTurnupLimit(chapter.getTurnupAmount());

		int num = (currTurnup + 1) * 2;

		addPage(chapter.getPage(num-1), chapter.getPage(num), num, x, y);
	}

	private void addPage(Page page1, Page page2, int displayNum, int x, int y){
		int k = (width - guiWidth) / 2;
		byte b0 = 2;
		String text1 = "";
		String text2 = "";
		Object icon1 = null;
		Object icon2 = null;

		if(page1 != null) {
			text1 = page1.getText();
			icon1 = page1.getIcon();
		}
		if(page2 != null) {
			text2 = page2.getText();
			icon2 = page2.getIcon();
		}

		tooltipStack = null;

		writeTexts(icon1, icon2, text1, text2);

		writeText(1, String.valueOf(displayNum - 1), 165, 50);
		writeText(2, String.valueOf(displayNum), 165, 50);

		if(icon1 != null) {
			if(icon1 instanceof ItemStack) {
				renderItem(k + 60, b0 + 28,(ItemStack)icon1, x, y);
			}
			if(icon1 instanceof ResourceLocation) {
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				icon1 = verify((ResourceLocation)icon1);
				mc.renderEngine.bindTexture((ResourceLocation)icon1);
				drawTexturedModalRect(k, b0, 0, 0, 256, 256);
			}
			if(icon1 instanceof CraftingStack){
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				mc.renderEngine.bindTexture(NecronomiconResources.CRAFTING);
				drawTexturedModalRect(k, b0, 0, 0, 256, 256);
				boolean unicode = fontRendererObj.getUnicodeFlag();
				fontRendererObj.setUnicodeFlag(false);
				renderItem(k + 93, b0 + 52,((CraftingStack)icon1).getOutput(), x, y);
				fontRendererObj.setUnicodeFlag(unicode);
				for(int i = 0; i <= 2; i++) {
					renderItem(k + 24 +i*21, b0 + 31,((CraftingStack)icon1).getFirstArray()[i], x, y);
					renderItem(k + 24 +i*21, b0 + 52,((CraftingStack)icon1).getSecondArray()[i], x, y);
					renderItem(k + 24 +i*21, b0 + 73,((CraftingStack)icon1).getThirdArray()[i], x, y);
				}
			}
		}
		if(icon2 != null) {
			int n = 123;
			if(icon2 instanceof ItemStack)
				renderItem(k + 60 + n, b0 + 28,(ItemStack)icon2, x, y);
			if(icon2 instanceof ResourceLocation) {
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				icon2 = verify((ResourceLocation)icon2);
				mc.renderEngine.bindTexture((ResourceLocation)icon2);
				drawTexturedModalRect(k + n, b0, 0, 0, 256, 256);
			}
			if(icon2 instanceof CraftingStack) {
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glPushMatrix();
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				mc.renderEngine.bindTexture(NecronomiconResources.CRAFTING);
				drawTexturedModalRect(k + n, b0, 0, 0, 256, 256);
				GL11.glEnable(GL12.GL_RESCALE_NORMAL);
				GL11.glEnable(GL11.GL_DEPTH_TEST);
				GL11.glPopMatrix();
				GL11.glDisable(GL11.GL_LIGHTING);
				boolean unicode = fontRendererObj.getUnicodeFlag();
				fontRendererObj.setUnicodeFlag(false);
				renderItem(k + 93 + n, b0 + 52,((CraftingStack)icon2).getOutput(), x, y);
				fontRendererObj.setUnicodeFlag(unicode);
				for(int i = 0; i <= 2; i++){
					renderItem(k + 24 + n +i*21, b0 + 31,((CraftingStack)icon2).getFirstArray()[i], x, y);
					renderItem(k + 24 + n +i*21, b0 + 52,((CraftingStack)icon2).getSecondArray()[i], x, y);
					renderItem(k + 24 + n +i*21, b0 + 73,((CraftingStack)icon2).getThirdArray()[i], x, y);
				}
			}
		}

		if(tooltipStack != null) {
			@SuppressWarnings("unchecked")
			List<String> tooltipData = tooltipStack.getTooltip(Minecraft.getMinecraft().thePlayer, false);
			List<String> parsedTooltip = new ArrayList<String>();
			boolean first = true;

			for(String s : tooltipData) {
				String s_ = s;
				if(!first)
					s_ = EnumChatFormatting.GRAY + s;
				parsedTooltip.add(s_);
				first = false;
			}
			GuiRenderHelper.renderTooltip(x, y, parsedTooltip);
		}
	}

	private ResourceLocation verify(ResourceLocation res){
		try {
			ImageIO.read(mc.getResourceManager().getResource(res).getInputStream());
		} catch (IOException e) {
			return new ResourceLocation("abyssalcraft", "textures/gui/necronomicon/missing.png");
		}
		return res;
	}

	private void writeTexts(Object icon1, Object icon2, String text1, String text2){

		if(icon1 != null){
			if(icon1 instanceof ItemStack) {
				writeText(1, text1, 50);
			}
			if(icon1 instanceof ResourceLocation) {
				writeText(1, text1, 100);
			}
			if(icon1 instanceof CraftingStack) {
				writeText(1, text1, 95);
			}
		} else { 
			writeText(1, text1);
		}
		if(icon2 != null) {
			if(icon2 instanceof ItemStack) {
				writeText(2, text2, 50);
			}
			if(icon2 instanceof ResourceLocation) {
				writeText(2, text2, 100);
			}
			if(icon2 instanceof CraftingStack) {
				writeText(2, text2, 95);
			}
		} else {
			writeText(2, text2);
		}
	}

	@Override
	protected void drawIndexText() {
		int k = (width - guiWidth) / 2;
		byte b0 = 2;
		String stuff;
		stuff = StatCollector.translateToLocal(data.getTitle());
		fontRendererObj.drawSplitString(stuff, k + 20, b0 + 16, 116, 0xC40000);
		if(data.getInformation() != null) {
			writeText(2, data.getInformation());
		}
	}

	private ItemStack tooltipStack;
	public void renderItem(int xPos, int yPos, ItemStack stack, int mx, int my) {
		RenderItem render = new RenderItem();
		if(mx > xPos && mx < xPos+16 && my > yPos && my < yPos+16) {
			tooltipStack = stack;
		}

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		render.renderItemAndEffectIntoGUI(Minecraft.getMinecraft().fontRenderer, Minecraft.getMinecraft().getTextureManager(), stack, xPos, yPos);
		render.renderItemOverlayIntoGUI(Minecraft.getMinecraft().fontRenderer, Minecraft.getMinecraft().getTextureManager(), stack, xPos, yPos);
		RenderHelper.disableStandardItemLighting();
		GL11.glPopMatrix();

		GL11.glDisable(GL11.GL_LIGHTING);
	}
}
