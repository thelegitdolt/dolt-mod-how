package com.dolthhaven.dolt_mod_how.integration;

import com.dolthhaven.dolt_mod_how.core.registry.DMHItems;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import umpaz.brewinandchewin.common.block.CheeseWheelBlock;

import java.util.function.Supplier;

public class DMHBCCompat {
    public static final Supplier<Block> WARDENZOLA = () ->
            new CheeseWheelBlock(DMHItems.WARDENZOLA_WEDGE, BlockBehaviour.Properties.copy(Blocks.CAKE));
}
