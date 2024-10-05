package com.dolthhaven.dolt_mod_how.core.mixin.nethers_delight;

import com.dolthhaven.dolt_mod_how.core.DoltModHowConfig;
import com.dolthhaven.dolt_mod_how.core.registry.DMHBlocks;
import com.dolthhaven.dolt_mod_how.core.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.violetmoon.quark.content.world.module.GlimmeringWealdModule;
import vectorwing.farmersdelight.common.block.MushroomColonyBlock;
import vectorwing.farmersdelight.common.block.RichSoilBlock;

import java.util.HashMap;
import java.util.Map;

@Mixin(RichSoilBlock.class)
public class RichSoilMixin {
    @Inject(method = "randomTick",
            at = @At("HEAD"))
    private void DoltModHow$GrowCustomColonies(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand, CallbackInfo ci) {
        if (!level.isClientSide && ModList.get().isLoaded(Util.Constants.MY_NETHERS_DELIGHT) && DoltModHowConfig.COMMON.doRichSoilGrowFungusColony.get()) {
            BlockPos abovePos = pos.above();
            BlockState aboveState = level.getBlockState(abovePos);

            if (aboveState.is(Blocks.CRIMSON_FUNGUS)) {
                Block block = Util.getPotentialBlock(Util.Constants.MY_NETHERS_DELIGHT, "crimson_fungus_colony");
                if (block != null) {
                    level.setBlockAndUpdate(abovePos, block.defaultBlockState());
                }
            }
            else if (aboveState.is(Blocks.WARPED_FUNGUS)) {
                Block block = Util.getPotentialBlock(Util.Constants.MY_NETHERS_DELIGHT, "warped_fungus_colony");
                if (block != null) {
                    level.setBlockAndUpdate(abovePos, block.defaultBlockState());
                }
            }
            else if (aboveState.is(GlimmeringWealdModule.glow_shroom)) {
                level.setBlockAndUpdate(abovePos, DMHBlocks.GLOWSHROOM_COLONY.get().defaultBlockState());
            }
        }
    }
}
