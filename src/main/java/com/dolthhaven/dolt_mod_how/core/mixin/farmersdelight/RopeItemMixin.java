package com.dolthhaven.dolt_mod_how.core.mixin.farmersdelight;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import vectorwing.farmersdelight.common.item.RopeItem;

@Mixin(RopeItem.class)
public class RopeItemMixin {
    @ModifyExpressionValue(method = "updatePlacementContext",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/material/FluidState;is(Lnet/minecraft/tags/TagKey;)Z"))
    private boolean DoltModHow$WhoIsLavaNOWVector(boolean original) {
        return true;
    }
}
