package com.dolthhaven.dolt_mod_how.integration;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import umpaz.brewinandchewin.common.registry.BnCEffects;

public class DMHBCCompat {

    public static int tipsyEffectLevel(Player player) {
        MobEffectInstance instance = player.getEffect(BnCEffects.TIPSY.get());
        if (instance == null) return -1;
        else return instance.getAmplifier();
    }
}
