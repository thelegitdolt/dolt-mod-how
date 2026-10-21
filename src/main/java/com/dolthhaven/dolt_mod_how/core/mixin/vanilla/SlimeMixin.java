package com.dolthhaven.dolt_mod_how.core.mixin.vanilla;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.monster.Slime;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Slime.class)
public class SlimeMixin {
    @Definition(id = "seedSlimeChunk", method = "Lnet/minecraft/world/level/levelgen/WorldgenRandom;seedSlimeChunk(IIJJ)Lnet/minecraft/util/RandomSource;")
    @Definition(id = "nextInt", method = "Lnet/minecraft/util/RandomSource;nextInt(I)I")
    @Expression("seedSlimeChunk(?, ?, ?, ?).nextInt(10)")
    @ModifyExpressionValue(method = "checkSlimeSpawnRules", at = @At(value = "MIXINEXTRAS:EXPRESSION"))
    private static int DoltCompat$RemoveSlimeChunks(int original) {
        return 0;
    }
}