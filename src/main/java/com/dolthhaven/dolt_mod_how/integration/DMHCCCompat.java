package com.dolthhaven.dolt_mod_how.integration;

import com.dolthhaven.dolt_mod_how.common.item.DMHGoldenBucketItem;
import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.teamabnormals.caverns_and_chasms.common.block.entity.ToolboxBlockEntity;
import com.teamabnormals.caverns_and_chasms.common.dispenser.FilledGoldenBucketDispenseBehavior;
import com.teamabnormals.caverns_and_chasms.common.entity.monster.Mime;
import com.teamabnormals.caverns_and_chasms.core.registry.CCItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

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

    public static DefaultDispenseItemBehavior getGoldenBucketDispenseBehavior() {
        return new FilledGoldenBucketDispenseBehavior();
    }

    // 1 if successfully set, 0 if unsuccessful set, 2 if not even toolbox
    public static int tryClearToolBox(Level level, BlockPos pos, BlockState state, int flag) {
        if (level.getBlockEntity(pos) instanceof ToolboxBlockEntity toolbox) {
            CompoundTag tag = toolbox.serializeNBT();
            boolean success = level.setBlock(pos, state, flag);
            if (success) {
                level.getBlockEntity(pos).deserializeNBT(tag);
            }

            return success ? DMHUtils.SUCCESSFUL_SETTING : DMHUtils.UNSUCCESSFUL_SETTING;
        }
        return DMHUtils.NOT_TOOLBOX;
    }

    public static boolean isMime(Entity entity) {
        return entity instanceof Mime;
    }
}
