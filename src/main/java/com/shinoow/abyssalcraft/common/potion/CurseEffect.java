package com.shinoow.abyssalcraft.common.potion;

import net.minecraft.potion.PotionEffect;

/** Wrapper for Potioneffects so that they cannot be removed by sipping Milk **/
public class CurseEffect extends PotionEffect {
	public CurseEffect(PotionEffect effect) {
		super(effect);
		getCurativeItems().clear();
	}
}
