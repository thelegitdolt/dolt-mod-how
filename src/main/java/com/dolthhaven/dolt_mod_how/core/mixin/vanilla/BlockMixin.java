package com.dolthhaven.dolt_mod_how.core.mixin.vanilla;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.class)
public class BlockMixin {
    @Inject(method = "canBeReplaced*", at = @At("HEAD"), cancellable = true)
    private void replaceMossCarpet(BlockState state, BlockPlaceContext context, CallbackInfoReturnable<Boolean> cir) {
        if (state.is(Blocks.MOSS_CARPET)) {
            cir.setReturnValue(true);
        }
    }
}
