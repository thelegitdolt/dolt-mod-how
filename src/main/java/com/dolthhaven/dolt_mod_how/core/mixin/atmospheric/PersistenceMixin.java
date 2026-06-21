package com.dolthhaven.dolt_mod_how.core.mixin.atmospheric;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.teamabnormals.atmospheric.common.effect.PersistenceEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PersistenceEffect.class)
public class PersistenceMixin {
    @WrapOperation(method = "addAttributeModifiers", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/food/FoodData;getFoodLevel()I"))
    private int sex(FoodData instance, Operation<Integer> original, @Local(argsOnly = true) LivingEntity player) {
        return DMHConfig.COMMON.hasRemovedHunger.get() ? (int) player.getHealth() : original.call(instance);
    }
}
