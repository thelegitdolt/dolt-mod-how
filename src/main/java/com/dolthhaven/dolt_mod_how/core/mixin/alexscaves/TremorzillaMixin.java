package com.dolthhaven.dolt_mod_how.core.mixin.alexscaves;

import com.github.alexmodguy.alexscaves.server.entity.living.TremorzillaEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TremorzillaEntity.class)
public class TremorzillaMixin {
    @Inject(method = "breakBlocksInBoundingBox", at = @At("HEAD"), cancellable = true)
    private void DoltModHow$NOTVANILLAPLUS(float dropChance, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }
}
