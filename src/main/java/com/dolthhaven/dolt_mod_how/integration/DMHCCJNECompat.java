package com.dolthhaven.dolt_mod_how.integration;

import com.dolthhaven.dolt_mod_how.common.block.AncientBrazierBlock;
import com.teamabnormals.caverns_and_chasms.common.block.BrazierBlock;
import com.teamabnormals.caverns_and_chasms.core.registry.CCSoundEvents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Supplier;

public class DMHCCJNECompat {
    public static final Supplier<Block> ANCIENT_BRAZIER = () ->
            new AncientBrazierBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops()
                    .strength(3.5F)
                    .sound(SoundType.LANTERN).mapColor(MapColor.COLOR_RED)
                    .sound(CCSoundEvents.CCSoundTypes.SILVER)
                    .lightLevel(state -> state.getValue(BrazierBlock.LIT) ? 13 : 0).noOcclusion());
}
