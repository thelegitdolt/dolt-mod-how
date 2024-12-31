package com.dolthhaven.dolt_mod_how.core.compat;

import com.dolthhaven.dolt_mod_how.core.util.Util;
import com.teamabnormals.caverns_and_chasms.common.item.GoldenBucketItem;
import com.teamabnormals.caverns_and_chasms.core.registry.CCItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class DMHCCAndACCompat {
    public static final Supplier<Item> GOLDEN_ACID_BUCKET = () ->
            new GoldenBucketItem(getLiquid(Util.Constants.ACID), new Item.Properties().craftRemainder(CCItems.GOLDEN_BUCKET.get()).stacksTo(1));
    public static final Supplier<Item> GOLDEN_PURPLE_SODA_BUCKET = () ->
            new GoldenBucketItem(getLiquid(Util.Constants.PURPLE_SODA), new Item.Properties().craftRemainder(CCItems.GOLDEN_BUCKET.get()).stacksTo(1));


    private static Supplier<Fluid> getLiquid(ResourceLocation location) {
        Fluid fluid = ForgeRegistries.FLUIDS.getValue(location);
        return fluid != null ? () -> fluid: () -> Fluids.WATER;
    }
}
