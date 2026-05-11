package com.dolthhaven.dolt_mod_how.core.mixin.quark;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.violetmoon.quark.content.building.block.HedgeBlock;
import org.violetmoon.quark.content.building.block.WoodPostBlock;

@Mixin(WoodPostBlock.PostSideType.class)
public class WoodPostBlockMixin {
    @ModifyReturnValue(method = "get", at = @At("RETURN"), remap = false)
    private static WoodPostBlock.PostSideType sex(WoodPostBlock.PostSideType original, @Local(argsOnly = true) Direction direction, @Local BlockState relState) {
        if (original == WoodPostBlock.PostSideType.CHAIN) return original;

        if (direction == Direction.UP && relState.getBlock() instanceof HedgeBlock) {
            return WoodPostBlock.PostSideType.OTHER_POST;
        } return original;
     }
}
