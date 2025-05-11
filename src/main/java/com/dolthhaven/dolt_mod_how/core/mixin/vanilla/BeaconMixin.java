package com.dolthhaven.dolt_mod_how.core.mixin.vanilla;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(BeaconBlockEntity.class)
public class BeaconMixin {
    @ModifyArg(method = "applyEffects", at = @At(value = "INVOKE", target =
            "Lnet/minecraft/world/effect/MobEffectInstance;<init>(Lnet/minecraft/world/effect/MobEffect;IIZZ)V"), index = 4)
    private static boolean DoltModHow$HideBeaconParticles(boolean isVisible) {
        return DMHConfig.COMMON.hideBeaconParticles.get();
    }
}
