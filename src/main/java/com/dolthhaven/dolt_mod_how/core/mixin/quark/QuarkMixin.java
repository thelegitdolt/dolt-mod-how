package com.dolthhaven.dolt_mod_how.core.mixin.quark;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.injection.At;
import org.violetmoon.quark.base.Quark;

@Mixin(Quark.class)
public class QuarkMixin {
    @WrapWithCondition(method = "<init>", at = @At(value = "INVOKE", target = "Lorg/spongepowered/asm/mixin/MixinEnvironment;audit()V"))
    private boolean pleaseShutUp(MixinEnvironment instance) {
        return false;
    }
}
