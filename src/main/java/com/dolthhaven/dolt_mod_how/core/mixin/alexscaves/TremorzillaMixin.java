package com.dolthhaven.dolt_mod_how.core.mixin.alexscaves;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import com.github.alexmodguy.alexscaves.server.entity.living.TremorzillaEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(TremorzillaEntity.class)
public class TremorzillaMixin {
    @Inject(method = "breakBlocksInBoundingBox", at = @At("HEAD"), cancellable = true, remap = false)
    private void DoltModHow$NOTVANILLAPLUS(float dropChance, CallbackInfoReturnable<Boolean> cir) {
        if (DMHConfig.COMMON.removeTremorzillaGrief.get()) {
            cir.setReturnValue(false);
        }
    }
}
