package com.dolthhaven.dolt_mod_how.core.mixin.nethers_delight;

import com.dolthhaven.dolt_mod_how.core.DoltModHowConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import umpaz.nethersdelight.common.registry.NDBlocks;
import vectorwing.farmersdelight.common.block.RichSoilBlock;

@Mixin(RichSoilBlock.class)
public class RichSoilMixin {
    @Inject(method = "Lvectorwing/farmersdelight/common/block/RichSoilBlock;randomTick(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;)V",
            at = @At("HEAD"))
    private void DoltModHow$GrowNetherShroomColony(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand, CallbackInfo ci) {
        if (!level.isClientSide && ModList.get().isLoaded("nethersdelight") && DoltModHowConfig.COMMON.doRichSoilGrowFungusColony.get()) {
            BlockPos abovePos = pos.above();
            BlockState aboveState = level.getBlockState(abovePos);

            if (aboveState.is(Blocks.CRIMSON_FUNGUS))
                level.setBlockAndUpdate(abovePos, (NDBlocks.CRIMSON_FUNGUS_COLONY.get()).defaultBlockState());
            else if (aboveState.is(Blocks.WARPED_FUNGUS))
                level.setBlockAndUpdate(abovePos, (NDBlocks.WARPED_FUNGUS_COLONY.get()).defaultBlockState());
        }
    }
}
