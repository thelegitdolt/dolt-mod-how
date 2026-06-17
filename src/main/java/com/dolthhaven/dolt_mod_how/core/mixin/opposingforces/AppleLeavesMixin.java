package com.dolthhaven.dolt_mod_how.core.mixin.opposingforces;

import com.unusualmodding.opposing_force.blocks.FruitfulLeavesBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(FruitfulLeavesBlock.class)
public class AppleLeavesMixin {
    @ModifyArg(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;<init>(Lnet/minecraft/world/level/ItemLike;I)V", ordinal = 1))
    private int DoltModHow$LeavesDropOneApple(int old) {
        return 1;
    }
}
