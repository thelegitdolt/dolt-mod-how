package com.dolthhaven.dolt_mod_how.core.mixin.vanilla;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Slime.class)
public class SlimeMixin {
    @Inject(method = "checkSlimeSpawnRules", at = @At("RETURN"), cancellable = true)
    private static void DoltCompat$RemoveSlimeChunks(EntityType<Slime> slime, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource source, CallbackInfoReturnable<Boolean> cir) {
        if (pos.getY() < 40) {
            cir.setReturnValue(false);
        }
    }
}