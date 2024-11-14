package com.dolthhaven.dolt_mod_how.core.mixin.nethers_delight;

import com.soytutta.mynethersdelight.common.entity.ia.EatMagmaCakeGoal;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EatMagmaCakeGoal.class)
public class FrogEatMagmaCakeGoalMixin {
    @Inject(method = "canUse", at = @At("HEAD"), cancellable = true)
    private void DIE(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }

    @Redirect(method = "handleBlockInteraction", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/EntityType;create(Lnet/minecraft/world/level/Level;)Lnet/minecraft/world/entity/Entity;"))
    private Entity DoltModHow$FrogsCANNOTEatFuckingMagmaCakesKYS(EntityType<?> instance, Level p_20616_) {
         return null;
    }

    @Redirect(method = "handleEntityInteraction", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/EntityType;create(Lnet/minecraft/world/level/Level;)Lnet/minecraft/world/entity/Entity;"))
    private Entity DoltModHow$aneurysm(EntityType<?> instance, Level p_20616_) {
        return null;
    }
}
