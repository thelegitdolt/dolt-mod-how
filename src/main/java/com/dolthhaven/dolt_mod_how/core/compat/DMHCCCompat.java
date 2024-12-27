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

public class DMHCCCompat {
    private static Supplier<? extends Fluid> getFluidAC(String path) {
        return getFluidOrWater(new ResourceLocation(Util.Constants.ALEXS_CAVES, path));
    }

    private static Supplier<? extends Fluid> getFluidOrWater(ResourceLocation loc) {
        Fluid fluid = ForgeRegistries.FLUIDS.getValue(loc);
        return fluid == null ? () -> Fluids.WATER : () -> fluid;
    }

    public static final Supplier<Item> GOLDEN_ACID_BUCKET = () ->
            new GoldenBucketItem(getFluidAC("acid"), new Item.Properties().craftRemainder(CCItems.GOLDEN_BUCKET.get()).stacksTo(1));
    public static final Supplier<Item> GOLDEN_PURPLE_SODA_BUCKET = () ->
            new GoldenBucketItem(getFluidAC("purple_soda"), new Item.Properties().craftRemainder(CCItems.GOLDEN_BUCKET.get()).stacksTo(1));
}
