package com.dolthhaven.dolt_mod_how.core.tweaks;

import com.dolthhaven.dolt_mod_how.core.mixin.PurulentTeaItemMixin;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import umpaz.farmersrespite.common.FRFoodValues;

import java.util.function.Supplier;

import static net.minecraft.world.effect.MobEffects.*;
import static umpaz.farmersrespite.common.FRFoodValues.*;
import static umpaz.farmersrespite.common.registry.FREffects.*;

public class DoltModHowFoodTweaks {

    public static void transformFoods() {
        FRFoodValues.BLACK_TEA.effects.remove(0);
        FRFoodValues.BLACK_TEA.effects.add(e1(POISON, 10));
        FRFoodValues.BLACK_TEA.effects.add(e1(CAFFEINATED.get(), 60));


        FRFoodValues.LONG_BLACK_TEA.effects.remove(0);
        FRFoodValues.LONG_BLACK_TEA.effects.add(e1(POISON, 15));
        FRFoodValues.LONG_BLACK_TEA.effects.add(e1(CAFFEINATED.get(), 90));

        STRONG_BLACK_TEA.effects.remove(0);
        add(STRONG_BLACK_TEA, e2(POISON, 7));
        add(STRONG_BLACK_TEA, e2(CAFFEINATED.get(), 45));


        PURULENT_TEA.effects.remove(0);



    }


    public static void add(FoodProperties prop, Pair<Supplier<MobEffectInstance>, Float> e) {
        prop.effects.add(e);
    }

    public static Pair<Supplier<MobEffectInstance>, Float> e(MobEffect effect, int duration, int amp) {
        return Pair.of(() -> new MobEffectInstance(effect, duration * 20, amp), 1.0f);
    }

    public static Pair<Supplier<MobEffectInstance>, Float> e1(MobEffect effect, int duration) {
        return e(effect, duration, 0);
    }

    public static Pair<Supplier<MobEffectInstance>, Float> e2(MobEffect effect, int duration) {
        return e(effect, duration, 1);
    }

    public static Pair<Supplier<MobEffectInstance>, Float> e3(MobEffect effect, int duration) {
        return e(effect, duration, 2);
    }


}
