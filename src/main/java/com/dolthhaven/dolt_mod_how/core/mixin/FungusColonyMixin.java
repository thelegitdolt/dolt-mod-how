package com.dolthhaven.dolt_mod_how.core.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import umpaz.nethersdelight.common.block.FungusColonyBlock;
import vectorwing.farmersdelight.common.registry.ModBlocks;

@Mixin(FungusColonyBlock.class)
public class FungusColonyMixin {
    @Inject(method = "isValidBonemealTarget(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Z)Z",
    at = @At("RETURN"), cancellable = true)
    private void DoltModHow$BonemealableColonies(BlockGetter worldIn, BlockPos pos, BlockState state, boolean isClient, CallbackInfoReturnable<Boolean> cir) {
        if (state.getValue(FungusColonyBlock.COLONY_AGE) < 3) cir.setReturnValue(true);
    }

    @Inject(method = "isBonemealSuccess(Lnet/minecraft/world/level/Level;Lnet/minecraft/util/RandomSource;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z",
            at = @At("RETURN"), cancellable = true)
    private void DoltModHow$BoneMealableColonies(Level worldIn, RandomSource random, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (state.getValue(FungusColonyBlock.COLONY_AGE) < 3) cir.setReturnValue(true);
    }

    @Inject(method = "mayPlaceOn(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Z",
    at = @At("RETURN"), cancellable = true)
    private void DoltModHow$PlaceableOnRichSoil(BlockState state, BlockGetter worldIn, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (state.is(ModBlocks.RICH_SOIL.get()) || state.is(ModBlocks.RICH_SOIL_FARMLAND.get())) {
            cir.setReturnValue(true);
        }
    }
}
