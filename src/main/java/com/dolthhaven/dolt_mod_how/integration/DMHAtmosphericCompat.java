package com.dolthhaven.dolt_mod_how.integration;

import com.teamabnormals.atmospheric.core.registry.AtmosphericBlocks;
import com.teamabnormals.blueprint.core.util.PropertyUtil;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;

import java.util.function.Supplier;

public class DMHAtmosphericCompat {
    public static final Supplier<Block> POTTED_ARID_SPROUTS = () ->
            new FlowerPotBlock(AtmosphericBlocks.ARID_SPROUTS.get(), PropertyUtil.flowerPot());
}
