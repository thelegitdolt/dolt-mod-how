package com.dolthhaven.dolt_mod_how.core.compat;

import com.dolthhaven.dolt_mod_how.core.other.DoltModHowTrackedData;
import com.dolthhaven.dolt_mod_how.core.registry.DMHBlocks;
import com.dolthhaven.dolt_mod_how.core.registry.DMHParticles;
import com.github.ilja615.fish_in_planks.FishBarrelBlock;
import com.uraneptus.sullysmod.core.registry.SMItems;

public class DoltModHowCommonSetup {

    public static void commonSetup() {
        DoltModHowTrackedData.registerTrackedData();
        registerFishBarrels();
    }

    private static void registerFishBarrels() {
        FishBarrelBlock.BLOCK_COOKED_FISH_ITEM_HASH_MAP.put(DMHBlocks.LANTERNFISH_BARREL.get(), SMItems.COOKED_LANTERNFISH.get());
        FishBarrelBlock.BLOCK_I_PARTICLE_DATA_HASH_MAP.put(DMHBlocks.LANTERNFISH_BARREL.get(), DMHParticles.LANTERNFISH_PARTICLE.get());
    }
}
