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

import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.util.StatCollector;
import org.lwjgl.input.Keyboard;

import com.google.common.collect.Lists;
import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;
import com.shinoow.abyssalcraft.api.ritual.RitualRegistry;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonCategory;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonNextPage;
import com.shinoow.abyssalcraft.client.lib.NecronomiconText;

public class GuiNecronomiconRituals extends GuiNecronomicon {

	private ButtonNextPage buttonPreviousPage;
	private GuiButton buttonDone;
	private ButtonCategory info;
	private ButtonCategory ritual[] = new ButtonCategory[5];
	private final Item[] icons = new Item[] {
		AbyssalCraft.necronomicon,
		AbyssalCraft.necronomicon_cor,
		AbyssalCraft.necronomicon_dre,
		AbyssalCraft.necronomicon_omt,
		AbyssalCraft.abyssalnomicon
	};
	private final String[] labels = new String[] {
		NecronomiconText.LABEL_NORMAL,
		NecronomiconText.LABEL_INFORMATION_ABYSSAL_WASTELAND,
		NecronomiconText.LABEL_INFORMATION_DREADLANDS,
		NecronomiconText.LABEL_INFORMATION_OMOTHOL,
		StatCollector.translateToLocal(AbyssalCraft.abyssalnomicon.getUnlocalizedName() + ".name")
	};

	public GuiNecronomiconRituals(int bookType){
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
		buttonList.add(buttonPreviousPage = new ButtonNextPage(1, w + 18, b0 + 154, false));
		buttonList.add(info = new ButtonCategory(2, w + 14, b0 + 24, this, NecronomiconText.LABEL_INFO, AbyssalCraft.necronomicon));
		
		for (int i = 0; i < ritual.length && i <= getBookType(); i++) {
			buttonList.add(ritual[i] = new ButtonCategory(3 + i, w + 14, b0 + 41 + (i * 17), this, labels[i], hasRituals(i) ? icons[i] : AbyssalCraft.OC));
			ritual[i].visible = true;
		}
		
		updateButtons();
	}

	private void updateButtons() {
		buttonPreviousPage.visible = true;
		buttonDone.visible = true;
		info.visible = true;
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if (button.enabled) {
			if(button.id == 0) {
				mc.displayGuiScreen((GuiScreen)null);
			} else if(button.id == 1) {
				mc.displayGuiScreen(new GuiNecronomicon(getBookType()));
			} else if(button.id == 2) {
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), AbyssalCraftAPI.getInternalNDHandler().getInternalNecroData("rituals"), this, AbyssalCraft.necronomicon));
			} else if(button.id >= 3) {
				int bookId = button.id - 3;
				if(hasRituals(bookId)) {
					mc.displayGuiScreen(new GuiNecronomiconRitualEntry(getBookType(), this, bookId));
				}
			}

			updateButtons();
		}
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
	protected void drawIndexText(){
		int k = (width - guiWidth) / 2;
		byte b0 = 2;
		String stuff;
		stuff = NecronomiconText.LABEL_RITUALS;
		fontRendererObj.drawSplitString(stuff, k + 20, b0 + 16, 116, 0xC40000);
		writeText(2, NecronomiconText.RITUAL_INFO);
	}
}
