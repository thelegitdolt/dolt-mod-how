package com.dolthhaven.dolt_mod_how.integration;

import com.teamabnormals.blueprint.core.util.PropertyUtil;
import com.teamabnormals.neapolitan.core.NeapolitanConfig;
import com.teamabnormals.neapolitan.core.registry.NeapolitanBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;

import java.util.function.Supplier;

public class DMHNeapolitanCompat {
    public static final Supplier<Block> POTTED_WHITE_STRAWBERRIES = () ->
            new FlowerPotBlock(NeapolitanBlocks.STRAWBERRY_BUSH.get(), PropertyUtil.flowerPot());

    public static final Supplier<Block> POTTED_STRAWBERRIES = () ->
            new FlowerPotBlock(NeapolitanBlocks.STRAWBERRY_BUSH.get(), PropertyUtil.flowerPot());


    public static boolean isWhiteStrawberry(Level level, BlockPos pos) {
        return pos.getY() >= NeapolitanConfig.COMMON.whiteStrawberryMinHeight.get() && level.dimension() == Level.OVERWORLD || level.dimension() == Level.END;
    }
}
