package com.dolthhaven.dolt_mod_how.core.compat;

import com.github.alexmodguy.alexscaves.server.block.fluid.ACFluidRegistry;
import com.teamabnormals.caverns_and_chasms.common.item.GoldenBucketItem;
import com.teamabnormals.caverns_and_chasms.core.registry.CCItems;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class DMHOptionalItems {
    public static final Supplier<Item> GOLDEN_ACID_BUCKET = () ->
            new GoldenBucketItem(ACFluidRegistry.ACID_FLUID_SOURCE, new Item.Properties().craftRemainder(CCItems.GOLDEN_BUCKET.get()).stacksTo(1));

    public static final Supplier<Item> GOLDEN_PURPLE_SODA_BUCKET = () ->
            new GoldenBucketItem(ACFluidRegistry.PURPLE_SODA_FLUID_SOURCE, new Item.Properties().craftRemainder(CCItems.GOLDEN_BUCKET.get()).stacksTo(1));

}
