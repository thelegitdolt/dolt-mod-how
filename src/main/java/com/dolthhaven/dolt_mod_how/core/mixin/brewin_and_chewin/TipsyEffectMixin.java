package com.dolthhaven.dolt_mod_how.core.mixin.brewin_and_chewin;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import umpaz.brewinandchewin.common.effect.TipsyEffect;

@Mixin(TipsyEffect.class)
public class TipsyEffectMixin {
    @Redirect(method = "Lumpaz/brewinandchewin/common/effect/TipsyEffect;applyEffectTick(Lnet/minecraft/world/entity/LivingEntity;I)V",
    at= @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;forceAddEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)V"))
    private void DoltCompat$NoMoreTipsyGivingNausea(LivingEntity instance, MobEffectInstance superInstance, Entity entity) {
        if (!(superInstance.getEffect() == MobEffects.CONFUSION)) {
            instance.forceAddEffect(superInstance, entity);
        }
    }

}
