package com.dolthhaven.dolt_mod_how.integration;

import com.bobmowzie.mowziesmobs.server.block.RakedSandBlock;
import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public class DMHMowziesMobsCompat {
    public static final Supplier<Block> RAKED_ARID_SAND = rakedSand(0xDBD3A0, DMHUtils.Constants.ARID_SAND);
    public static final Supplier<Block> RAKED_RED_ARID_SAND = rakedSand(0xF7D3A0, DMHUtils.Constants.RED_ARID_SAND);
    public static final Supplier<Block> RAKED_ASHEN_SAND = rakedSand(0x565E5D, DMHUtils.Constants.ASHEN_SAND);

    private static Supplier<Block> rakedSand(int color, ResourceLocation sandLoc) {
        Block sand = getModdedSand(sandLoc);
        return () -> new RakedSandBlock(color, BlockBehaviour.Properties.copy(sand), sand.defaultBlockState());
    }

    private static Block getModdedSand(ResourceLocation location) {
        Block block = DMHUtils.getPotentialBlock(location);
        return block == null ? Blocks.SAND : block;
    }
}
