package com.dolthhaven.dolt_mod_how.integration;

import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.fml.ModList;

import java.util.function.Supplier;

public class DMHBopCompat {
    public static Supplier<Item> toadstool() {
        return () -> ModList.get().isLoaded(DMHUtils.Constants.BOP) ? DMHUtils.getPotentialItem(DMHUtils.Constants.BOP, "toadstool") : Items.RED_MUSHROOM;
    }

    public static Supplier<Item> glowshroom() {
        return () -> ModList.get().isLoaded(DMHUtils.Constants.BOP) ? DMHUtils.getPotentialItem(DMHUtils.Constants.BOP, "glowshroom") : Items.RED_MUSHROOM;
    }
}
