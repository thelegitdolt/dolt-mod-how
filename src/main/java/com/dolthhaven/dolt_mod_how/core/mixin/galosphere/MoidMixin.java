package com.dolthhaven.dolt_mod_how.core.mixin.galosphere;

import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.dolthhaven.dolt_mod_how.integration.DMHCCCompat;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.orcinus.galosphere.entities.Mole;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
@Mixin(Mole.class)
public class MoidMixin {
    @WrapOperation(method = "isTuffDirt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z"))
    private static boolean sex(BlockState instance, Block block, Operation<Boolean> original) {
        return original.call(instance, block) || (DMHUtils.cavernsChasmsLoaded() && DMHCCCompat.isRockyDirt(instance));
    }
}
