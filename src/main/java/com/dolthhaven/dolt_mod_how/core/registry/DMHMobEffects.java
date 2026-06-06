package com.dolthhaven.dolt_mod_how.core.registry;

import com.dolthhaven.dolt_mod_how.common.effect.GenerosityMobEffect;
import com.dolthhaven.dolt_mod_how.common.effect.MimingMobEffect;
import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.teamabnormals.blueprint.common.effect.BlueprintMobEffect;
import com.teamabnormals.blueprint.core.util.DataUtil;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = DoltModHow.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DMHMobEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, DoltModHow.MOD_ID);
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, DoltModHow.MOD_ID);

    public static final RegistryObject<MobEffect> MIMING = MOB_EFFECTS.register("miming", () ->
            new MimingMobEffect(MobEffectCategory.NEUTRAL));
    public static final RegistryObject<MobEffect> GENEROSITY = MOB_EFFECTS.register("generosity", () ->
            new GenerosityMobEffect(MobEffectCategory.NEUTRAL));
    public static final RegistryObject<MobEffect> RAPACITY = MOB_EFFECTS.register("rapacity", () ->
            new BlueprintMobEffect(MobEffectCategory.BENEFICIAL, 0xff9970));


    public static final RegistryObject<Potion> NORMAL_MIMING = POTIONS.register("miming",
            () -> new Potion("miming", new MobEffectInstance(MIMING.get())));
    public static final RegistryObject<Potion> MIMING_STRONG = POTIONS.register("strong_miming",
            () -> new Potion("miming", new MobEffectInstance(MIMING.get(), 0, 1)));
    public static final RegistryObject<Potion> NORMAL_GENEROSITY = POTIONS.register("generosity",
            () -> new Potion("generosity", new MobEffectInstance(GENEROSITY.get())));
    public static final RegistryObject<Potion> STRONG_GENEROSITY = POTIONS.register("strong_generosity",
            () -> new Potion("generosity", new MobEffectInstance(GENEROSITY.get(), 0, 1)));

    public static final RegistryObject<Potion> NORMAL_RAPACITY = POTIONS.register("rapacity",
            () -> new Potion("rapacity", new MobEffectInstance(RAPACITY.get(), 3600)));
    public static final RegistryObject<Potion> LONG_RAPACITY = POTIONS.register("long_rapacity",
            () -> new Potion("rapacity", new MobEffectInstance(RAPACITY.get(), 9600)));

    public static void registerBrewingRecipes() {
        Item item = DMHUtils.getPotentialItem(DMHUtils.Constants.ZIRCONIA);
        if (item == null) {
            item = Items.SCULK;
        }

        DataUtil.addMix(Potions.AWKWARD, item, NORMAL_MIMING.get());
        DataUtil.addMix(NORMAL_MIMING.get(), Items.GLOWSTONE_DUST, MIMING_STRONG.get());

        DataUtil.addMix(NORMAL_MIMING.get(), Items.FERMENTED_SPIDER_EYE, NORMAL_GENEROSITY.get());
        DataUtil.addMix(MIMING_STRONG.get(), Items.FERMENTED_SPIDER_EYE, STRONG_GENEROSITY.get());

        DataUtil.addMix(Potions.AWKWARD, Items.ROTTEN_FLESH, NORMAL_RAPACITY.get());
        DataUtil.addMix(NORMAL_RAPACITY.get(), Items.REDSTONE, LONG_RAPACITY.get());
    }

}
