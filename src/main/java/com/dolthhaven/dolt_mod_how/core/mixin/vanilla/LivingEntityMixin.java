package com.dolthhaven.dolt_mod_how.core.mixin.vanilla;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @ModifyReturnValue(method = "calculateFallDamage", at = @At("RETURN"))
    private int sex(int original) {
        return DMHConfig.COMMON.fallDamageNerf.get() ? Mth.floor(original * 0.67d) : original;
    }
}
