package com.dolthhaven.dolt_mod_how.core.mixin.supplementaries;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.teamabnormals.caverns_and_chasms.common.block.entity.ToolboxBlockEntity;
import net.mehvahdjukaar.supplementaries.common.utils.SoapWashableHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SoapWashableHelper.class)
public class SoapWashableHelperMixin {
    @WrapOperation(method = "tryUnoxidise", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"))
    private static boolean DoltModHow$SaveToolBoxState(Level level, BlockPos pos, BlockState state, int flag, Operation<Boolean> original) {
        if (level.getBlockEntity(pos) instanceof ToolboxBlockEntity toolbox) {
            CompoundTag tag = toolbox.serializeNBT();
            boolean success = level.setBlock(pos, state, flag);
            if (success) {
                level.getBlockEntity(pos).deserializeNBT(tag);
            }

            return success;
        } else {
            return original.call(level, pos, state, flag);
        }
    }
}
