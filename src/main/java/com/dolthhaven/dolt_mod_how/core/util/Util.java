package com.dolthhaven.dolt_mod_how.core.util;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public class Util {
    public static void printItem(Item item) {
        DoltModHow.LOGGER.info(ForgeRegistries.ITEMS.getKey(item).getNamespace() + ForgeRegistries.ITEMS.getKey(item).getPath());
    }
}
