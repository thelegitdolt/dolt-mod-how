package com.dolthhaven.dolt_mod_how.core.other;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import com.dolthhaven.dolt_mod_how.core.other.events.DMHRightClickEvent;
import com.dolthhaven.dolt_mod_how.core.registry.DMHMobEffects;
import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.dolthhaven.dolt_mod_how.integration.DMHCCCompat;
import com.teamabnormals.blueprint.core.util.DataUtil;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import org.violetmoon.quark.addons.oddities.module.PipesModule;

import java.util.HashSet;
import java.util.ListIterator;
import java.util.Set;

import static com.dolthhaven.dolt_mod_how.core.registry.DMHBlocks.*;
import static com.dolthhaven.dolt_mod_how.core.registry.DMHItems.GLOWSHROOM_COLONY;

public class DoltModHowDataUtil {
    public static final Object2FloatMap<EntityType<?>> COMPOSTABLE_ENTITIES = new Object2FloatOpenHashMap<>();

    public static void registerData() {
        registerCompostable();
        DMHRightClickEvent.registerHoeTills();
        DMHRightClickEvent.registerUnRust();
        DMHRightClickEvent.registerBlockPlacing();
        DMHRightClickEvent.registerRakeables();
        DMHCauldrons.register();
        DMHMobEffects.registerBrewingRecipes();
        if (DMHUtils.cavernsChasmsLoaded()) {
            DMHCCCompat.registerGoldenBuckets();
        }
        if (DMHConfig.COMMON.musicDiscsStack.get()) {
            transformStackSizeMusicDisc();
        }
        registerPipes();
        if (DMHConfig.COMMON.hasRemovedHunger.get()) {
            transformItemProperties();
        }
        if (DMHConfig.COMMON.noMoreAwkwardPotions.get()) {
            try {
                changeAwkwardPotionsToWater();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void changeAwkwardPotionsToWater() {
        PotionBrewing.POTION_MIXES.removeIf(mix -> mix.to.get() == Potions.MUNDANE);
        DataUtil.addMix(Potions.WATER, Items.POISONOUS_POTATO, Potions.MUNDANE);


        for (ListIterator<PotionBrewing.Mix<Potion>> mixer = PotionBrewing.POTION_MIXES.listIterator(); mixer.hasNext();) {
            PotionBrewing.Mix<Potion> mix = mixer.next();
            if (mix.from.get() == Potions.AWKWARD) {
                mixer.remove();
                mixer.add(new PotionBrewing.Mix<>(ForgeRegistries.POTIONS, Potions.WATER, mix.ingredient, mix.to.get()));
            }
        }
    }

    public static void transformStackSizeMusicDisc() {
        for (Item item : ForgeRegistries.ITEMS.getValues()) {
            if (item instanceof RecordItem) {
                item.maxStackSize = 64;
            }
        }
    }

    public static void transformItemProperties() {
        int[] numbers = {1, 1, 1, 2, 3, 4, 4, 5, 6, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};

        for (Item item : ForgeRegistries.ITEMS.getValues()) {
            if (item.foodProperties != null) {
                int nut = item.foodProperties.nutrition;
                if (nut > 0 && nut <= 21) {
                    item.foodProperties.nutrition = numbers[nut - 1];
                }
            }
        }
    }

    private static void registerCompostable() {
        DataUtil.registerCompostable(GLOWSHROOM_COLONY.get(), 1.0f);
        DataUtil.registerCompostable(TOADSTOOL_COLONY.get(), 1.0f);
        DataUtil.registerCompostable(BOP_GLOW_SHROOM_COLONY.get(), 1.0f);
        DataUtil.registerCompostable(PINE_NUTS_CRATE.get(), 1.0f);
        DataUtil.registerCompostable(MULCH_BAG.get(), 1.0f);

        COMPOSTABLE_ENTITIES.defaultReturnValue(-1.0f);

        if (ModList.get().isLoaded(DMHUtils.Constants.NEAPOLITAN)) {
            EntityType<?> bananaPeel = ForgeRegistries.ENTITY_TYPES.getValue(DMHUtils.Constants.BANANA_PEEL);
            COMPOSTABLE_ENTITIES.put(bananaPeel, 0.5f);
        }
    }

    private static void registerPipes() {
        Set<Block> block = new HashSet<>(PipesModule.blockEntityType.validBlocks);
        block.add(WHITE_ENCASED_PIPE.get());
        block.add(BROWN_ENCASED_PIPE.get());
        block.add(GRAY_ENCASED_PIPE.get());
        block.add(LIGHT_GRAY_ENCASED_PIPE.get());
        block.add(RED_ENCASED_PIPE.get());
        block.add(ORANGE_ENCASED_PIPE.get());
        block.add(YELLOW_ENCASED_PIPE.get());
        block.add(LIME_ENCASED_PIPE.get());
        block.add(GREEN_ENCASED_PIPE.get());
        block.add(BLUE_ENCASED_PIPE.get());
        block.add(LIGHT_BLUE_ENCASED_PIPE.get());
        block.add(CYAN_ENCASED_PIPE.get());
        block.add(PURPLE_ENCASED_PIPE.get());
        block.add(MAGENTA_ENCASED_PIPE.get());
        block.add(PINK_ENCASED_PIPE.get());
        block.add(BLACK_ENCASED_PIPE.get());
        block.add(ROSE_ENCASED_PIPE.get());
        block.add(MAROON_ENCASED_PIPE.get());
        block.add(GINGER_ENCASED_PIPE.get());
        block.add(TAN_ENCASED_PIPE.get());
        block.add(BEIGE_ENCASED_PIPE.get());
        block.add(CORAL_ENCASED_PIPE.get());
        block.add(OLIVE_ENCASED_PIPE.get());
        block.add(FOREST_ENCASED_PIPE.get());
        block.add(VERDANT_ENCASED_PIPE.get());
        block.add(AMBER_ENCASED_PIPE.get());
        block.add(TEAL_ENCASED_PIPE.get());
        block.add(MINT_ENCASED_PIPE.get());
        block.add(AQUA_ENCASED_PIPE.get());
        block.add(SLATE_ENCASED_PIPE.get());
        block.add(NAVY_ENCASED_PIPE.get());
        block.add(INDIGO_ENCASED_PIPE.get());
        PipesModule.blockEntityType.validBlocks = block;
    }
}
