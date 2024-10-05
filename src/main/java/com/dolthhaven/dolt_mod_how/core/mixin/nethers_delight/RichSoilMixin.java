package com.dolthhaven.dolt_mod_how.core.mixin.nethers_delight;

import com.dolthhaven.dolt_mod_how.core.DoltModHowConfig;
import com.dolthhaven.dolt_mod_how.core.util.Util;
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
import vectorwing.farmersdelight.common.block.MushroomColonyBlock;
import vectorwing.farmersdelight.common.block.RichSoilBlock;

@Mixin(RichSoilBlock.class)
public class RichSoilMixin {
    @Inject(method = "randomTick",
            at = @At("HEAD"))
    private void DoltModHow$GrowNetherShroomColony(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand, CallbackInfo ci) {
        if (!level.isClientSide && ModList.get().isLoaded(Util.Constants.MY_NETHERS_DELIGHT) && DoltModHowConfig.COMMON.doRichSoilGrowFungusColony.get()) {
            BlockPos abovePos = pos.above();
            BlockState aboveState = level.getBlockState(abovePos);

            if (aboveState.is(Blocks.CRIMSON_FUNGUS)) {
                MushroomColonyBlock crimsonFungusColony = (MushroomColonyBlock) Util.getPotentialBlock(Util.Constants.MY_NETHERS_DELIGHT, "crimson_fungus_colony");
                if (crimsonFungusColony == null) return;
                level.setBlockAndUpdate(abovePos, crimsonFungusColony.defaultBlockState());
            }
            else if (aboveState.is(Blocks.WARPED_FUNGUS)) {
                MushroomColonyBlock warpedFungusColony = (MushroomColonyBlock) Util.getPotentialBlock(Util.Constants.MY_NETHERS_DELIGHT, "warped_fungus_colony");
                if (warpedFungusColony == null) return;

                level.setBlockAndUpdate(abovePos, (warpedFungusColony).defaultBlockState());
            }
        }
    }
}
