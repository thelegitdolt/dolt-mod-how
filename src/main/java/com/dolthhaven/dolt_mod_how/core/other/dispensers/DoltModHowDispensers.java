package com.dolthhaven.dolt_mod_how.core.other.dispensers;

import com.dolthhaven.dolt_mod_how.core.registry.DMHItems;
import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.dolthhaven.dolt_mod_how.integration.DMHCCCompat;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.fml.ModList;

public class DoltModHowDispensers {
    public static void registerDispenseBehavior() {
        FlintAndSteelExplodeCreepersDispenseBehavior.registerFlintAndSteelExplodeCreepers();
        DyeSheepDispenseBehavior.registerSheepDispensers();
        registerOtherDispensers();
    }

    private static void registerOtherDispensers() {
        if (ModList.get().isLoaded(DMHUtils.Constants.CAVERNS_AND_CHASMS)) {
            var buck = DMHCCCompat.getGoldenBucketDispenseBehavior();
            DispenserBlock.registerBehavior(DMHItems.GOLDEN_MOLTEN_LEAD_BUCKET.get(), buck);
            DispenserBlock.registerBehavior(DMHItems.GOLDEN_ACID_BUCKET.get(), buck);
            DispenserBlock.registerBehavior(DMHItems.GOLDEN_PURPLE_SODA_BUCKET.get(), buck);
        }
    }
}
