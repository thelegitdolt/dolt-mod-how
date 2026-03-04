package com.dolthhaven.dolt_mod_how.core.mixin.caverns_and_chasms;

import com.dolthhaven.dolt_mod_how.core.registry.DMHCriteriaTriggers;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.teamabnormals.caverns_and_chasms.core.other.CCEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CCEvents.class)
public class CCEventsMixin {
    @Inject(method = "rewindTeleport", at = @At("HEAD"), remap = false)
    private static void sexers(LivingEntity entity, CallbackInfo ci, @Share("arg") LocalRef<Vec3> argRef) {
        argRef.set(entity.position());
    }

    @Inject(method = "rewindTeleport", at = @At(value = "TAIL"), remap = false)
    private static void sex(LivingEntity entity, CallbackInfo ci, @Share("arg") LocalRef<Vec3> argRef) {
        if (entity instanceof ServerPlayer player) {
            if (argRef.get().distanceTo(player.position()) > 1000) {
                DMHCriteriaTriggers.REWIND_GREAT_DISTANCES.trigger(player);
            }
        }
    }
}
