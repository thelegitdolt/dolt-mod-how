package com.dolthhaven.dolt_mod_how.core.mixin.brewinandchewin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import umpaz.brewinandchewin.common.block.FieryFonduePotBlock;

@Mixin(FieryFonduePotBlock.class)
public class FieryFonduePotBlockMixin {
    @Definition(id = "LEVEL_CAULDRON", field = "Lnet/minecraft/world/level/block/state/properties/BlockStateProperties;LEVEL_CAULDRON:Lnet/minecraft/world/level/block/state/properties/IntegerProperty;")
    @Expression("LEVEL_CAULDRON")
    @ModifyExpressionValue(method = "<clinit>", at = @At("MIXINEXTRAS:EXPRESSION"))
    private static IntegerProperty DMH$CardinalSin(IntegerProperty original) {
        return IntegerProperty.create("level", 1, 4);
    }

    @Inject(method = "getContentHeight", at = @At("HEAD"), cancellable = true, remap = false)
    private void DMH$Fix(BlockState state, CallbackInfoReturnable<Double> cir) {
        cir.setReturnValue(7 + state.getValue(FieryFonduePotBlock.LEVEL) * 2 / 16.0d);
    }

    @WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;setValue(Lnet/minecraft/world/level/block/state/properties/Property;Ljava/lang/Comparable;)Ljava/lang/Object;"))
    private Object DMH$Fix(BlockState instance, Property<?> property, Comparable<?> comparable, Operation<Object> original) {
        if (property == FieryFonduePotBlock.LEVEL) return original.call(instance, property, 4);
        return original.call(instance, property, comparable);
    }
}
