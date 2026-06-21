package com.dolthhaven.dolt_mod_how.integration;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import umpaz.brewinandchewin.common.registry.BnCEffects;
import umpaz.brewinandchewin.common.registry.BnCItems;

public class DMHBnCCompat {
    public static boolean canShootTankard(Player player, ItemStack stack) {
        return stack.is(BnCItems.TANKARD.get()) && player.getEffect(BnCEffects.TIPSY.get()) != null;
    }

    public static Item tankard() {
        return BnCItems.TANKARD.get();
    }
}
