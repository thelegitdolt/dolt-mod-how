package com.dolthhaven.dolt_mod_how.core.mixin;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.monster.CaveSpider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(CaveSpider.class)
public class CaveSpiderMixin {

    @ModifyArg(method = "doHurtTarget(Lnet/minecraft/world/entity/Entity;)Z",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z"))
    private MobEffectInstance DoltModHow$NerfCaveSpider(MobEffectInstance effect) {
        return new MobEffectInstance(effect.getEffect(), effect.getDuration() / 3 * 2, effect.getAmplifier());
    }
}
