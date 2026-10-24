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
    }
}
