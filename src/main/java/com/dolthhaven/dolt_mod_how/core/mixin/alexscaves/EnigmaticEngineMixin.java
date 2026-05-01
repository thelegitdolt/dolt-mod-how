package com.dolthhaven.dolt_mod_how.core.mixin.alexscaves;

import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.github.alexmodguy.alexscaves.server.block.blockentity.EnigmaticEngineBlockEntity;
import com.github.alexmodguy.alexscaves.server.entity.item.SubmarineEntity;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
@Mixin(EnigmaticEngineBlockEntity.class)
public class EnigmaticEngineMixin {
    @WrapOperation(method = "attemptAssembly", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    private boolean sex(Level instance, BlockPos pos, BlockState state, Operation<Boolean> original, @Share("oxi")LocalRef<int[]> oxidationCount, @Share("waxed") LocalRef<int[]> waxedCount) {
        boolean ret = original.call(instance, pos, state);
        if (oxidationCount.get() == null) oxidationCount.set(new int[]{0, 0, 0, 0});
        if (waxedCount.get() == null) oxidationCount.set(new int[]{0, 0});
        String path = DMHUtils.getBlockId(state.getBlock()).getPath();
        waxedCount.get()[path.contains("waxed") ? 1 : 0] += 1;
        oxidationCount.get()[DMHUtils.toNumber(path)] += 1;

        return ret;
    }

    @WrapOperation(method = "attemptAssembly", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"))
    private boolean sex(Level instance, Entity entity, Operation<Boolean> original, @Share("oxi")LocalRef<int[]> oxidationCount, @Share("waxed") LocalRef<int[]> waxedCount) {
        int[] waxed = waxedCount.get();
        int[] oxy = oxidationCount.get();
        if (entity instanceof SubmarineEntity submarine) {
            if (waxed[1] > waxed[0]) submarine.setWaxed(false);
            submarine.setOxidizationLevel(DMHUtils.getLargestIndexInArray(oxy));
        }

        return original.call(instance, entity);
    }
}
