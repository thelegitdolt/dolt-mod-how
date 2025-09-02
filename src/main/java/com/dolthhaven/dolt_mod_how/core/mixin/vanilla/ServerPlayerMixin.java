package com.dolthhaven.dolt_mod_how.core.mixin.vanilla;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.teamabnormals.environmental.common.entity.animal.slabfish.SlabfishOverlay;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.function.Predicate;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin {
    @Inject(method = "bedInRange",
            at = @At("RETURN"), cancellable = true)
    private void DoltCompat$NoMoreTooFarAwayBS(BlockPos pos, Direction dir, CallbackInfoReturnable<Boolean> cir) {
        if (DMHConfig.COMMON.lessAnnoyingSleep.get()) {
            cir.setReturnValue(true);
        }
    }

    @WrapOperation(method = "startSleepInBed", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/level/Level;getEntitiesOfClass(Ljava/lang/Class;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;)Ljava/util/List;"))
    private <T extends Entity> List<T> DoltCompat$NoMoreMonsterNearbyBS(Level instance, Class<T> aClass, AABB aabb, Predicate<LivingEntity> predicate, Operation<List<T>> original) {
        if (DMHConfig.COMMON.lessAnnoyingSleep.get() && aClass == Monster.class) {
            return List.of();
        }

        return original.call(instance, aClass, aabb, predicate);
    }
}