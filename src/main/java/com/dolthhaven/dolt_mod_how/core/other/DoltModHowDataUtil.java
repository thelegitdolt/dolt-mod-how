package com.dolthhaven.dolt_mod_how.core.other;

import com.dolthhaven.dolt_mod_how.core.util.Util;
import com.teamabnormals.blueprint.core.util.DataUtil;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import net.mehvahdjukaar.supplementaries.reg.ModRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import vectorwing.farmersdelight.common.registry.ModBlocks;

import static com.dolthhaven.dolt_mod_how.core.other.DoltModHowEvent.TILL_MAP;
import static com.dolthhaven.dolt_mod_how.core.registry.DMHBlocks.PINE_NUTS_CRATE;
import static com.dolthhaven.dolt_mod_how.core.registry.DMHItems.GLOWSHROOM_COLONY;

public class DoltModHowDataUtil {
    public static final Object2FloatMap<EntityType<?>> COMPOSTABLE_ENTITIES = new Object2FloatOpenHashMap<>();

    public static void registerData() {
        registerCompostable();
        registerHoeTills();
    }

    private static void registerCompostable() {
        DataUtil.registerCompostable(GLOWSHROOM_COLONY.get(), 1.0f);
        DataUtil.registerCompostable(PINE_NUTS_CRATE.get(), 1.0f);

        COMPOSTABLE_ENTITIES.defaultReturnValue(-1.0f);

        if (ModList.get().isLoaded(Util.Constants.NEAPOLITAN)) {
            EntityType<?> bananaPeel = ForgeRegistries.ENTITY_TYPES.getValue(Util.Constants.BANANA_PEEL);

            COMPOSTABLE_ENTITIES.put(bananaPeel, 0.5f);
        }
    }

    private static void registerHoeTills() {
        TILL_MAP.put(Blocks.FARMLAND, Blocks.DIRT);
        TILL_MAP.put(ModBlocks.RICH_SOIL_FARMLAND.get(), ModBlocks.RICH_SOIL.get());
        TILL_MAP.put(ModRegistry.RAKED_GRAVEL.get(), Blocks.GRAVEL);
    }
}
