package com.dolthhaven.dolt_mod_how.core.mixin.quark;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.injection.At;
import org.violetmoon.quark.base.Quark;

@Mixin(value = Quark.class)
public class QuarkMixin {
    @WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lorg/spongepowered/asm/mixin/MixinEnvironment;audit()V"))
    private void pleaseShutUp(MixinEnvironment instance, Operation<Void> original) {}
}
