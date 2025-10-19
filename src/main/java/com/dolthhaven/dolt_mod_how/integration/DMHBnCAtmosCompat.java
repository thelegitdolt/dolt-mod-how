package com.dolthhaven.dolt_mod_how.integration;

import com.dolthhaven.dolt_mod_how.core.registry.DMHFluids;
import com.teamabnormals.atmospheric.core.registry.AtmosphericMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import umpaz.brewinandchewin.common.item.BoozeItem;
import umpaz.brewinandchewin.common.registry.BnCEffects;
import umpaz.brewinandchewin.common.registry.BnCItems;

import java.util.function.Supplier;

public class DMHBnCAtmosCompat {
    public static final Supplier<Item> TEQUILA = () ->
            new BoozeItem(DMHFluids.TEQUILA.get(), new Item.Properties().craftRemainder(BnCItems.TANKARD.get()).stacksTo(16)
                    .food(new FoodProperties.Builder().alwaysEat()
                            .effect(() -> new MobEffectInstance(BnCEffects.TIPSY.get(), 2400, 2), 1.0F)
                            .effect(() -> new MobEffectInstance(BnCEffects.INTOXICATION.get(), 1800, 0), 1.0F)
                            .effect(() -> new MobEffectInstance(AtmosphericMobEffects.PERSISTENCE.get(), 1200, 0), 1.0F).build()));
}
