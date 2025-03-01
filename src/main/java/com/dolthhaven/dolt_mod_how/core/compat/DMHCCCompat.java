package com.dolthhaven.dolt_mod_how.core.compat;

import com.dolthhaven.dolt_mod_how.common.item.DMHGoldenBucketItem;
import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.teamabnormals.caverns_and_chasms.common.item.GoldenBucketItem;
import com.teamabnormals.caverns_and_chasms.core.registry.CCItems;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class DMHCCCompat {
    public static final Supplier<Item> GOLDEN_ACID_BUCKET = () ->
            new DMHGoldenBucketItem(() -> DMHUtils.getFluidOrWater(DMHUtils.Constants.ACID),
                    new Item.Properties().craftRemainder(CCItems.GOLDEN_BUCKET.get()).stacksTo(1));
    public static final Supplier<Item> GOLDEN_PURPLE_SODA_BUCKET = () ->
            new DMHGoldenBucketItem(() -> DMHUtils.getFluidOrWater(DMHUtils.Constants.PURPLE_SODA),
                    new Item.Properties().craftRemainder(CCItems.GOLDEN_BUCKET.get()).stacksTo(1));
    public static final Supplier<Item> GOLDEN_MOLTEN_LEAD_BUCKET = () ->
            new DMHGoldenBucketItem(() -> DMHUtils.getFluidOrWater(DMHUtils.Constants.MOLTEN_LEAD),
                    new Item.Properties().craftRemainder(CCItems.GOLDEN_BUCKET.get()).stacksTo(1));


}
