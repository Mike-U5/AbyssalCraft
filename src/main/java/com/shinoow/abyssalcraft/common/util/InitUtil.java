package com.shinoow.abyssalcraft.common.util;

import net.minecraft.potion.Potion;

public class InitUtil {
	public static Potion getBlurPot() {
		// Check if blur is available. 0-23 are vanilla ID's and can be skipped.
		for (int i = 24; i < Potion.potionTypes.length; i++) {
			final Potion pot = Potion.potionTypes[i];
			if (pot != null && pot.getName() == "potion.blurred") {
				return pot;
			}
		}
		// If not, just use blindness
		return Potion.blindness;
	}
}
