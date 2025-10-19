package com.dolthhaven.dolt_mod_how.core.mixin.supplementaries;

import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.dolthhaven.dolt_mod_how.integration.DMHCCCompat;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.mehvahdjukaar.supplementaries.common.utils.SoapWashableHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SoapWashableHelper.class)
public class SoapWashableHelperMixin {
    @WrapOperation(method = "tryUnoxidise", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"))
    private static boolean DoltModHow$SaveToolBoxState(Level level, BlockPos pos, BlockState state, int flag, Operation<Boolean> original) {
        if (!ModList.get().isLoaded(DMHUtils.Constants.CAVERNS_AND_CHASMS)) {
            return original.call(level, pos, state, flag);
        }
        int result = DMHCCCompat.tryClearToolBox(level, pos, state, flag);
        if (result == DMHUtils.NOT_TOOLBOX) {
            return original.call(level, pos, state, flag);
        } else {
            return result == DMHUtils.SUCCESSFUL_SETTING;
        }
    }
}
