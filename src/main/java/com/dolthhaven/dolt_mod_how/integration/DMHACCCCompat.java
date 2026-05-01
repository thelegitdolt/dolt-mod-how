package com.dolthhaven.dolt_mod_how.integration;

import com.google.common.base.Suppliers;
import com.teamabnormals.caverns_and_chasms.core.registry.CCItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.List;
import java.util.function.Supplier;

public class DMHACCCCompat {
    public static final Supplier<List<Item>> COPPER_INGOTS = Suppliers.memoize(() ->
            List.of(Items.COPPER_INGOT, CCItems.EXPOSED_COPPER_INGOT.get(), CCItems.WEATHERED_COPPER_INGOT.get(), CCItems.OXIDIZED_COPPER_INGOT.get(),
                    CCItems.WAXED_COPPER_INGOT.get(), CCItems.WAXED_EXPOSED_COPPER_INGOT.get(), CCItems.WAXED_WEATHERED_COPPER_INGOT.get(), CCItems.WAXED_OXIDIZED_COPPER_INGOT.get()));

}
