package com.dolthhaven.dolt_mod_how.core.mixin.vanilla;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BrewingStandBlockEntity.class)
public class BrewingStandBlockEntityMixin {
    @Definition(id = "fuel", field = "Lnet/minecraft/world/level/block/entity/BrewingStandBlockEntity;fuel:I")
    @Expression("?.fuel")
    @ModifyExpressionValue(method = "serverTick", at = @At("MIXINEXTRAS:EXPRESSION"))
    private static int brewingStandAlwaysHasFuel(int original) {
        return DMHConfig.COMMON.brewingUnbloating.get() ? 1000000 : original;
    }
}
