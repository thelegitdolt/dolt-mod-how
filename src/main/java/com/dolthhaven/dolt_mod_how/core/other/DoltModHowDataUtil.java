package com.dolthhaven.dolt_mod_how.core.other;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.dolthhaven.dolt_mod_how.core.other.events.DMHRightClickEvent;
import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.teamabnormals.blueprint.core.util.DataUtil;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import static com.dolthhaven.dolt_mod_how.core.registry.DMHBlocks.*;
import static com.dolthhaven.dolt_mod_how.core.registry.DMHItems.GLOWSHROOM_COLONY;

public class DoltModHowDataUtil {
    public static final Object2FloatMap<EntityType<?>> COMPOSTABLE_ENTITIES = new Object2FloatOpenHashMap<>();

    public static void registerData() {
        registerCompostable();
        DMHRightClickEvent.registerHoeTills();
        DMHRightClickEvent.registerUnRust();
        DMHRightClickEvent.registerRakeables();
    }

    private static void registerCompostable() {
        DataUtil.registerCompostable(GLOWSHROOM_COLONY.get(), 1.0f);
        DataUtil.registerCompostable(TOADSTOOL_COLONY.get(), 1.0f);
        DataUtil.registerCompostable(BOP_GLOW_SHROOM_COLONY.get(), 1.0f);
        DataUtil.registerCompostable(PINE_NUTS_CRATE.get(), 1.0f);

        COMPOSTABLE_ENTITIES.defaultReturnValue(-1.0f);

        if (ModList.get().isLoaded(DMHUtils.Constants.NEAPOLITAN)) {
            EntityType<?> bananaPeel = ForgeRegistries.ENTITY_TYPES.getValue(DMHUtils.Constants.BANANA_PEEL);
            COMPOSTABLE_ENTITIES.put(bananaPeel, 0.5f);
        }
    }
}
