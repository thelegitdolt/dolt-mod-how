package com.dolthhaven.dolt_mod_how.core.mixin.vanilla;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {
    @Inject(method = "canStartSprinting", at = @At(value = "HEAD"), cancellable = true)
    private void DoltModHow$HungerStopsSprinting(CallbackInfoReturnable<Boolean> cir) {
        if (!DMHConfig.COMMON.hasRemovedHunger.get()) return;
        if (((LocalPlayer) (Object)this).hasEffect(MobEffects.HUNGER)) cir.setReturnValue(false);
    }
}
