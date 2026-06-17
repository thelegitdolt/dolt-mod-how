package com.dolthhaven.dolt_mod_how.core.mixin.opposingforces;

import com.unusualmodding.opposing_force.blocks.FruitfulLeavesBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(FruitfulLeavesBlock.class)
public class AppleLeavesMixin {
    @ModifyArgs(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;<init>(Lnet/minecraft/world/level/ItemLike;I)V"))
    private void DoltModHow$LeavesDropOneApple(Args args) {
        args.set(1, 1);
    }
}
