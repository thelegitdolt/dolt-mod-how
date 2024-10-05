package com.dolthhaven.dolt_mod_how.core.other;

import com.teamabnormals.blueprint.core.util.DataUtil;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import static com.dolthhaven.dolt_mod_how.core.registry.DMHItems.GLOWSHROOM_COLONY;

public class DoltModHowDataUtil {
    public static void registerData() {
        registerCompostable();
    }

    private static void registerCompostable() {
        compost100(GLOWSHROOM_COLONY);
    }


    @SafeVarargs
    public static void compost100(RegistryObject<Item>... thing) {
        for (RegistryObject<Item> hello : thing) {
            DataUtil.registerCompostable(hello.get(), 1.0f);
        }
    }
}
