package com.dolthhaven.dolt_mod_how.core.other;

import com.dolthhaven.dolt_mod_how.core.util.Util;
import com.teamabnormals.blueprint.core.util.DataUtil;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.dolthhaven.dolt_mod_how.core.registry.DMHItems.GLOWSHROOM_COLONY;

public class DoltModHowDataUtil {
    public static final Object2FloatMap<EntityType<?>> COMPOSTABLE_ENTITIES = new Object2FloatOpenHashMap<>();

    public static void registerData() {
        registerCompostable();
    }

    private static void registerCompostable() {
        compost100(GLOWSHROOM_COLONY);

        COMPOSTABLE_ENTITIES.defaultReturnValue(-1.0f);

        if (ModList.get().isLoaded("neapolitan")) {
            EntityType<?> bananaPeel = ForgeRegistries.ENTITY_TYPES.getValue(Util.Constants.BANANA_PEEL);

            COMPOSTABLE_ENTITIES.put(bananaPeel, 0.5f);
        }
    }


    @SafeVarargs
    public static void compost100(RegistryObject<Item>... thing) {
        for (RegistryObject<Item> hello : thing) {
            DataUtil.registerCompostable(hello.get(), 1.0f);
        }
    }
}
