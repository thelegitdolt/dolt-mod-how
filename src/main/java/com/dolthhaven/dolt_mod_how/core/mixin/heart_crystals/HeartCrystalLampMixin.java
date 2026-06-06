package com.dolthhaven.dolt_mod_how.core.mixin.heart_crystals;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import com.rosemods.heart_crystals.common.block_entity.HeartLanternBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeartLanternBlockEntity.class)
public class HeartCrystalLampMixin {
    @Inject(method = "tick(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lcom/rosemods/heart_crystals/common/block_entity/HeartLanternBlockEntity;)V",
            at = @At("HEAD"), cancellable = true, remap = false)
    private void sex(Level level, BlockPos pos, BlockState state, HeartLanternBlockEntity blockEntity, CallbackInfo ci) {
        if (DMHConfig.COMMON.hasRemovedHunger.get()) ci.cancel();
    }
}
