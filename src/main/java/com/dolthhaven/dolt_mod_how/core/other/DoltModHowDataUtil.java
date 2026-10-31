package com.dolthhaven.dolt_mod_how.core.other;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import com.dolthhaven.dolt_mod_how.core.other.events.DMHRightClickEvent;
import com.dolthhaven.dolt_mod_how.core.registry.DMHMobEffects;
import com.teamabnormals.blueprint.core.util.DataUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ListIterator;


public class DoltModHowDataUtil {
    public static void registerData() {
        DMHRightClickEvent.registerHoeTills();
        DMHMobEffects.registerBrewingRecipes();
        if (DMHConfig.COMMON.musicDiscsStack.get()) {
            transformStackSizeMusicDisc();
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
}
