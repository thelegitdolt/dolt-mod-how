package com.dolthhaven.dolt_mod_how.integration;

import com.google.common.base.Suppliers;
import net.minecraft.Util;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.yirmiri.dungeonsdelight.core.registry.DDEffects;
import vectorwing.farmersdelight.common.registry.ModEffects;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class DMHDDCompat {
    private static final Supplier<Map<MobEffect, MobEffect>> MONSTER_EFFECT_MAP = Suppliers.memoize(() -> Util.make(new HashMap<>(), map -> {
        map.put(MobEffects.DAMAGE_BOOST, DDEffects.DECISIVE.get());
        map.put(ModEffects.NOURISHMENT.get(), DDEffects.VORACITY.get());
        map.put(MobEffects.DIG_SPEED, DDEffects.BURROW_GUT.get());
        map.put(MobEffects.ABSORPTION, DDEffects.EXUDATION.get());
        map.put(MobEffects.JUMP, DDEffects.POUNCING.get());
    }));

    public static MobEffect getMonsterEffect(MobEffect effect) {
        return MONSTER_EFFECT_MAP.get().get(effect);
    }
}
