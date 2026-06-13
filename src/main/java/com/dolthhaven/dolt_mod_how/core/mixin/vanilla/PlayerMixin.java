package com.dolthhaven.dolt_mod_how.core.mixin.vanilla;

import com.dolthhaven.dolt_mod_how.core.registry.DMHMobEffects;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerMixin {
    @Inject(method = "canEat", at = @At("HEAD"), cancellable = true)
    private void DoltModHow$RapaciousAlwaysEat(boolean p_36392_, CallbackInfoReturnable<Boolean> cir) {
        if (((Player)(Object) this).hasEffect(DMHMobEffects.RAPACITY.get())) {
            cir.setReturnValue(true);
        }
    }

//    @Inject(method = "aiStep", at = @At())
}
