package com.dolthhaven.dolt_mod_how.core.mixin.nethers_delight;

import biomesoplenty.api.block.BOPBlocks;
import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import com.dolthhaven.dolt_mod_how.core.registry.DMHBlocks;
import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.violetmoon.quark.content.world.module.GlimmeringWealdModule;
import vectorwing.farmersdelight.common.block.RichSoilBlock;

@Mixin(RichSoilBlock.class)
public class RichSoilMixin {
    @Inject(method = "randomTick",
            at = @At("HEAD"))
    private void DoltModHow$GrowCustomColonies(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand, CallbackInfo ci) {
        if (!level.isClientSide &&  DMHConfig.COMMON.doRichSoilGrowFungusColony.get()) {
            BlockPos abovePos = pos.above();
            BlockState aboveState = level.getBlockState(abovePos);

            if (ModList.get().isLoaded(DMHUtils.Constants.MY_NETHERS_DELIGHT)) {
                if (aboveState.is(Blocks.CRIMSON_FUNGUS)) {
                    Block block = DMHUtils.getPotentialBlock(DMHUtils.Constants.MY_NETHERS_DELIGHT, "crimson_fungus_colony");
                    if (block != null) {
                        level.setBlockAndUpdate(abovePos, block.defaultBlockState());
                    }
                }
                else if (aboveState.is(Blocks.WARPED_FUNGUS)) {
                    Block block = DMHUtils.getPotentialBlock(DMHUtils.Constants.MY_NETHERS_DELIGHT, "warped_fungus_colony");
                    if (block != null) {
                        level.setBlockAndUpdate(abovePos, block.defaultBlockState());
                    }
                }
            }

            else if (aboveState.is(GlimmeringWealdModule.glow_shroom)) {
                level.setBlockAndUpdate(abovePos, DMHBlocks.GLOWSHROOM_COLONY.get().defaultBlockState());
            }
            else if (ModList.get().isLoaded(DMHUtils.Constants.BOP)) {
                Block bopGlowshroom = DMHUtils.getPotentialBlock(DMHUtils.Constants.BOP, "glowshroom");
                Block toadStool = DMHUtils.getPotentialBlock(DMHUtils.Constants.BOP, "toadstool");

                if (aboveState.is(bopGlowshroom)) {
                    level.setBlockAndUpdate(abovePos, DMHBlocks.BOP_GLOW_SHROOM_COLONY.get().defaultBlockState());
                }
                else if (aboveState.is(toadStool)) {
                    level.setBlockAndUpdate(abovePos, DMHBlocks.TOADSTOOL_COLONY.get().defaultBlockState());
                }
            }
        }
    }
}
