package com.dolthhaven.dolt_mod_how.core.mixin.brewinandchewin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import umpaz.brewinandchewin.common.block.CoasterBlock;
import umpaz.brewinandchewin.common.block.entity.CoasterBlockEntity;
import umpaz.brewinandchewin.common.registry.BnCBlocks;
import vectorwing.farmersdelight.common.block.entity.SyncedBlockEntity;

@Mixin(CoasterBlockEntity.class)
public class CoasterEntityMixin extends SyncedBlockEntity {
    public CoasterEntityMixin(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }

    @Definition(id = "AIR", field = "Lnet/minecraft/world/level/block/Blocks;AIR:Lnet/minecraft/world/level/block/Block;")
    @Definition(id = "defaultBlockState", method = "Lnet/minecraft/world/level/block/Block;defaultBlockState()Lnet/minecraft/world/level/block/state/BlockState;")
    @Expression("AIR.defaultBlockState()")
    @ModifyExpressionValue(method = "onUse", at = @At("MIXINEXTRAS:EXPRESSION"), remap = false)
    private BlockState thing(BlockState original, @Share("isReal") LocalBooleanRef isReal) {
        isReal.set(true);
        return BnCBlocks.COASTER.get().defaultBlockState().setValue(CoasterBlock.INVISIBLE, true).setValue(CoasterBlock.SIZE, 0);
    }

    @WrapOperation(method = "onUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    private boolean real(Level instance, BlockPos pos, BlockState state, Operation<Boolean> original, @Share("isReal") LocalBooleanRef isReal) {
        if (isReal.get()) {
            instance.scheduleTick(pos, BnCBlocks.COASTER.get(), 100);
        }
        return original.call(instance, pos, state);
    }
}
