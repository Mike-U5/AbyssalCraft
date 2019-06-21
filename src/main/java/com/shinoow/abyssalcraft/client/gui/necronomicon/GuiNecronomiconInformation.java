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

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonCategory;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonNextPage;
import com.shinoow.abyssalcraft.client.lib.NecronomiconResources;
import com.shinoow.abyssalcraft.client.lib.NecronomiconText;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

enum InfoType {ABYSSALCRAFT, ABOUTTHISBOOK, PATRONS, GREATOLDONES, OUTERGODS};

public class GuiNecronomiconInformation extends GuiNecronomicon {
	private ButtonNextPage buttonNextPage, buttonPreviousPage;
	private ButtonCategory[] buttonCat = new ButtonCategory[8];
	private final int[] buttonBooks = new int[] {0,0,0,0,1,2,3,4};
	private final Item[] itemBooks = new Item[] {
		AbyssalCraft.necronomicon,
		AbyssalCraft.necronomicon_cor,
		AbyssalCraft.necronomicon_dre,
		AbyssalCraft.necronomicon_omt,
		AbyssalCraft.abyssalnomicon
	};
	private final String[] buttonText = new String[] {
		NecronomiconText.LABEL_INFORMATION_ABYSSALCRAFT,
		NecronomiconText.LABEL_INFORMATION_GREAT_OLD_ONES,
		NecronomiconText.LABEL_HUH,
		NecronomiconText.LABEL_INFORMATION_OVERWORLD,
		NecronomiconText.LABEL_INFORMATION_ABYSSAL_WASTELAND,
		NecronomiconText.LABEL_INFORMATION_DREADLANDS,
		NecronomiconText.LABEL_INFORMATION_OMOTHOL,
		NecronomiconText.LABEL_INFORMATION_DARK_REALM
	};
	private GuiButton buttonDone;
	private InfoType infoType;

	public GuiNecronomiconInformation(int bookType){
		super(bookType);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void initGui() {
		buttonList.clear();
		Keyboard.enableRepeatEvents(true);

		buttonList.add(buttonDone = new GuiButton(0, width / 2 - 100, 4 + guiHeight, 200, 20, I18n.format("gui.done", new Object[0])));

		int w = (width - guiWidth) / 2;
		byte b0 = 2;
		
		buttonList.add(buttonNextPage = new ButtonNextPage(1, w + 215, b0 + 154, true));
		buttonList.add(buttonPreviousPage = new ButtonNextPage(2, w + 18, b0 + 154, false));
		
		for (int i = 0; i < buttonCat.length; i++) {
			int xPos = w + 14;
			int yPos = b0 + 24 + (i * 17);
			// Left or Right Page?
			if (i >= 5) {
				xPos += 118;
				yPos -= 85;
			}
			// Icon
			final Item icon = itemBooks[buttonBooks[i]];
			// Add
			if(getBookType() >= buttonBooks[i]) {
				buttonList.add(buttonCat[i] = new ButtonCategory(3 + i, xPos, yPos, this, buttonText[i], icon));
				buttonCat[i].visible = true;
			}
		}
		updateButtons();
	}

	private void updateButtons() {
		buttonNextPage.visible = (currTurnup < getTurnupLimit() - 1 && isInfo && infoType == InfoType.ABYSSALCRAFT);
		buttonPreviousPage.visible = true;
		buttonDone.visible = true;
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
					mc.displayGuiScreen(new GuiNecronomicon(getBookType()));
				} else if(currTurnup == 0 && isInfo) {
					initGui();
					isInfo = false;
					setTurnupLimit(2);
				} else if (currTurnup > 0) {
					--currTurnup;
				}
			// Categories
			} else if(button.id == 3) {
				isInfo = true;
				infoType = InfoType.ABYSSALCRAFT;
				drawButtons();
			} else if(button.id == 4) {
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), AbyssalCraftAPI.getInternalNDHandler().getInternalNecroData("greatoldones"), this, AbyssalCraft.necronomicon));
			} else if(button.id == 5) {
				isInfo = true;
				infoType = InfoType.ABOUTTHISBOOK;
				drawButtons();
			} else if(button.id == 6) {
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), AbyssalCraftAPI.getInternalNDHandler().getInternalNecroData("overworld"), this, AbyssalCraft.necronomicon));
			} else if(button.id == 7) { 
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), AbyssalCraftAPI.getInternalNDHandler().getInternalNecroData("abyssalwasteland"), this, AbyssalCraft.necronomicon_cor));
			} else if(button.id == 8) {
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), AbyssalCraftAPI.getInternalNDHandler().getInternalNecroData("dreadlands"), this, AbyssalCraft.necronomicon_dre));
			} else if(button.id == 9) {
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), AbyssalCraftAPI.getInternalNDHandler().getInternalNecroData("omothol"), this, AbyssalCraft.necronomicon_omt));
			} else if(button.id == 10) {
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), AbyssalCraftAPI.getInternalNDHandler().getInternalNecroData("darkrealm"), this, AbyssalCraft.abyssalnomicon));
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

	@Override
	protected void drawInformationText(int x, int y){
		int k = (width - guiWidth) / 2;
		byte b0 = 2;
		if(infoType == InfoType.ABYSSALCRAFT) {
			setTurnupLimit(3);
			String title = StatCollector.translateToLocal(NecronomiconText.LABEL_INFORMATION_ABYSSALCRAFT);
			fontRendererObj.drawSplitString(title, k + 20, b0 + 16, 116, 0xC40000);
			if(currTurnup == 0) {
				writeText(1, NecronomiconText.INFORMATION_ABYSSALCRAFT_PAGE_1, 100);
				writeText(2, NecronomiconText.INFORMATION_ABYSSALCRAFT_PAGE_2, 100);
				renderPicture(NecronomiconResources.ABYSSALCRAFT_1, k, b0);
			} else if(currTurnup == 1) {
				writeText(1, NecronomiconText.INFORMATION_ABYSSALCRAFT_PAGE_3, 100);
				writeText(2, NecronomiconText.INFORMATION_ABYSSALCRAFT_PAGE_4, 100);
				renderPicture(NecronomiconResources.ABYSSALCRAFT_2, k, b0);
			} else if(currTurnup == 2) {
				writeText(1, NecronomiconText.INFORMATION_ABYSSALCRAFT_PAGE_5, 100);
				writeText(2, NecronomiconText.INFORMATION_ABYSSALCRAFT_PAGE_6, 100);
				renderPicture(NecronomiconResources.ABYSSALCRAFT_3, k, b0);
			}
		} else if(infoType == InfoType.ABOUTTHISBOOK) {
			setTurnupLimit(getBookType() == 4 ? 1 : 2);
			String title = StatCollector.translateToLocal(NecronomiconText.LABEL_HUH);
			fontRendererObj.drawSplitString(title, k + 20, b0 + 16, 116, 0xC40000);
			if (getBookType() == 4) {
				writeText(1, NecronomiconText.ABYSSALNOMICON_PAGE_1);
				writeText(2, NecronomiconText.ABYSSALNOMICON_PAGE_2);
			} else if (currTurnup == 0) {
				writeText(1, NecronomiconText.NECRONOMICON_PAGE_1);
				writeText(2, NecronomiconText.NECRONOMICON_PAGE_2);
			} else if (currTurnup == 1) {
				writeText(1, NecronomiconText.NECRONOMICON_PAGE_3);
				writeText(2, NecronomiconText.NECRONOMICON_PAGE_4);
			}
		}
	}
	
	private void renderPicture(ResourceLocation srcLoc, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(srcLoc);
		drawTexturedModalRect(x, y, 0, 0, 256, 256);
	}
}
