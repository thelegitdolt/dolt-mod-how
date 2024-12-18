package com.dolthhaven.dolt_mod_how.core.mixin.environmental;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.Map;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Shadow @Final private Map<MobEffect, MobEffectInstance> activeEffects;

    @Inject(method = "getActiveEffects", at = @At("HEAD"), cancellable = true)
    private void DoltModHow$IFUCKINGFIXEDSLABFISHESHOLYSHIT(CallbackInfoReturnable<Collection<MobEffectInstance>> cir) {
        if (this.activeEffects == null) {
            cir.setReturnValue(null);
        }
    }
}
