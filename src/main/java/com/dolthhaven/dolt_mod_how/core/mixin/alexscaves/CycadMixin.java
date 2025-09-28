package com.dolthhaven.dolt_mod_how.core.mixin.alexscaves;

import com.github.alexmodguy.alexscaves.server.block.CycadBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CycadBlock.class)
public class CycadMixin {
    @Inject(method = "mayPlaceOn", at = @At("RETURN"), cancellable = true)
    private void hi(BlockState state, BlockGetter level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        boolean result = cir.getReturnValue();
        cir.setReturnValue(result || state.is(Tags.Blocks.SAND));
    }
}
