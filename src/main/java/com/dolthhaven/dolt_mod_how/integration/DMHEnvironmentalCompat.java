package com.dolthhaven.dolt_mod_how.integration;

import com.teamabnormals.blueprint.core.util.PropertyUtil;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;

import java.util.function.Supplier;

public class DMHEnvironmentalCompat {
    public static final Supplier<Block> POTTED_MYCELIUM_SPROUTS = () ->
            new FlowerPotBlock(EnvironmentalBlocks.MYCELIUM_SPROUTS.get(), PropertyUtil.flowerPot());
}
