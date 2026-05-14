package com.dolthhaven.dolt_mod_how.core.mixin.quark;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import org.violetmoon.quark.content.client.tooltip.FoodTooltips;

@Mixin(FoodTooltips.FoodComponent.class)
public class FoodTooltipMixin {
    @ModifyArgs(method = "renderImage", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIFFIIII)V"))
    private void sex(Args args) {
        if (DMHConfig.COMMON.hasRemovedHunger.get()){
            args.set(4, 0f);
        }
    }
}
