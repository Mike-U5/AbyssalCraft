package com.shinoow.abyssalcraft.common.potion;

import net.minecraft.potion.PotionEffect;

/** Wrapper for Potioneffects so that they cannot be removed by sipping Milk **/
public class CurseEffect extends PotionEffect {
	public CurseEffect(int id, int duration) {
		super(new PotionEffect(id, duration, -1));
		getCurativeItems().clear();
	}
}
