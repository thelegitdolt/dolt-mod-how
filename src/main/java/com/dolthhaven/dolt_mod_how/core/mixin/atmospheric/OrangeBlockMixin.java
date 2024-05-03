package com.dolthhaven.dolt_mod_how.core.mixin.atmospheric;

import com.dolthhaven.dolt_mod_how.core.DoltModHowConfig;
import com.teamabnormals.atmospheric.common.block.OrangeBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OrangeBlock.class)
public class OrangeBlockMixin extends DirectionalBlock {
    protected OrangeBlockMixin(Properties p_52591_) {
        super(p_52591_);
    }

    @Inject(method = "fallOn", at = @At(value = "HEAD"), cancellable = true)
    private void DoltModHow$OrangeCantBeFallenOn(Level level, BlockState state, BlockPos pos, Entity entity, float distance, CallbackInfo ci) {
        if (DoltModHowConfig.COMMON.removeOrangeVapor.get()) {
            super.fallOn(level, state, pos, entity,distance);
            ci.cancel();
        }
    }

    @Inject(method = "createVaporCloud", at = @At(value = "HEAD"), cancellable = true)
    private static void DoltModHow$PistonsMateReally(Level level, Vec3 pos, boolean blood, CallbackInfo ci) {
        if (DoltModHowConfig.COMMON.removeOrangeVapor.get()) {
            ci.cancel();
        }
    }
}
