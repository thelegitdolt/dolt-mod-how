package com.dolthhaven.dolt_mod_how.core.compat;

import com.teamabnormals.atmospheric.core.registry.AtmosphericBlocks;
import com.teamabnormals.blueprint.core.util.PropertyUtil;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.teamabnormals.upgrade_aquatic.core.registry.UABlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;

import java.util.function.Supplier;

public class DMHOptionalBlocks {
    // ATMOSPHERIC BEGIN
    public static final Supplier<Block> POTTED_ARID_SPROUTS = () ->
            new FlowerPotBlock(AtmosphericBlocks.ARID_SPROUTS.get(), PropertyUtil.flowerPot());

    // UPGRADE AQUATIC BEGIN
    public static final Supplier<Block> POTTED_BEACHGRASS = () ->
            new FlowerPotBlock(UABlocks.BEACHGRASS.get(), PropertyUtil.flowerPot());
    public static final Supplier<Block> POTTED_TALL_BEACHGRASS = () ->
            new FlowerPotBlock(UABlocks.TALL_BEACHGRASS.get(), PropertyUtil.flowerPot());


    // ENVIRONMENTAL BEGIN
    public static final Supplier<Block> POTTED_MYCELIUM_SPROUTS = () ->
            new FlowerPotBlock(EnvironmentalBlocks.MYCELIUM_SPROUTS.get(), PropertyUtil.flowerPot());


}
