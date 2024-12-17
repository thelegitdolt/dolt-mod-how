package com.dolthhaven.dolt_mod_how.core.mixin.nethers_delight;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
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
        if (DMHConfig.COMMON.frogsAreNotStupid.get()) cir.setReturnValue(false);
    }

    @WrapWithCondition(method = "handleBlockInteraction", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/EntityType;create(Lnet/minecraft/world/level/Level;)Lnet/minecraft/world/entity/Entity;"))
    private boolean DoltModHow$FrogsCANNOTEatFuckingMagmaCakesKYS(EntityType<?> instance, Level p_20616_) {
         return DMHConfig.COMMON.frogsAreNotStupid.get();
    }

    @WrapWithCondition(method = "handleEntityInteraction", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/EntityType;create(Lnet/minecraft/world/level/Level;)Lnet/minecraft/world/entity/Entity;"))
    private boolean DoltModHow$aneurysm(EntityType<?> instance, Level p_20616_) {
        return DMHConfig.COMMON.frogsAreNotStupid.get();
    }
}
