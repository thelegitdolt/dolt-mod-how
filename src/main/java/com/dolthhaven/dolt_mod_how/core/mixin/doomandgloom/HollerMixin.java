package com.dolthhaven.dolt_mod_how.core.mixin.doomandgloom;

import com.dolthhaven.dolt_mod_how.core.registry.DMHCriteriaTriggers;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import galena.doom_and_gloom.content.entity.holler.Holler;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(Holler.class)
public class HollerMixin {
    @WrapOperation(method = "panicFinish", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;getBlockEntity(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/entity/BlockEntityType;)Ljava/util/Optional;"))
    private <T extends BlockEntity> Optional<T> attachAdvancement(ServerLevel level, BlockPos pos, BlockEntityType<T> blockEntityType, Operation<Optional<T>> original) {
        level.getEntitiesOfClass(ServerPlayer.class, new AABB(pos).inflate(35))
                .forEach(DMHCriteriaTriggers.WITNESS_HOLLER_POSSESS_JUKEBOX::trigger);
        return original.call(level, pos, blockEntityType);
    }
}
