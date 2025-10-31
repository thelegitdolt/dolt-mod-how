package com.dolthhaven.dolt_mod_how.core.mixin.atmospheric;

import com.teamabnormals.atmospheric.core.other.AtmosphericEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(AtmosphericEvents.class)
public class AtmosphericEventsMixin {
    @Inject(method = "isAprilFools", at = @At("HEAD"), cancellable = true, remap = false)
    private static void DoltModHow$DoltGrinchArc(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }
}
