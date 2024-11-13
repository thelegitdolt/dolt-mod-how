package com.dolthhaven.dolt_mod_how.core.mixin.nethers_delight;

import com.soytutta.mynethersdelight.common.entity.ia.EatMagmaCakeGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EatMagmaCakeGoal.class)
public class FrogEatMagmaCakeGoalMixin {
    @Inject(method = "canUse", at = @At("HEAD"), cancellable = true)
    private void DoltModHow$FrogsCANNOTEatFuckingMagmaCakesKYS(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }
}
