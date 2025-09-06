package com.dolthhaven.dolt_mod_how.core.mixin.jne;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import net.jadenxgamer.netherexp.registry.block.custom.BoneRodBlock;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoneRodBlock.class)
public class BoneRodsMixin {
    @Shadow @Final public static IntegerProperty BONES;

    @Inject(method = "canBeReplaced", at = @At("HEAD"), cancellable = true)
    private void hi(BlockState state, BlockPlaceContext context, CallbackInfoReturnable<Boolean> cir){
        if (DMHConfig.COMMON.shouldPlaceBonePilesWithNormalBones.get()) {

            if (!context.isSecondaryUseActive() && context.getItemInHand().getItem() == Items.BONE && state.getValue(BONES) < 4) {
                cir.setReturnValue(true);
            }
        }
    }
}
