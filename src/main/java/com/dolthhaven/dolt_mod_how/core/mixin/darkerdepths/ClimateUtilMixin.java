package com.dolthhaven.dolt_mod_how.core.mixin.darkerdepths;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.naterbobber.darkerdepths.worldgen.ClimateParamsUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Consumer;

@Mixin(ClimateParamsUtil.class)
public class ClimateUtilMixin {
    @WrapWithCondition(method = "createBiomes", at = @At(value = "INVOKE", target = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V"), remap = false)
    private static <T> boolean sex(Consumer<T> instance, T t) {
//        if (!DMHConfig.COMMON.removeDarkerDepthGlowStoneForest.get()) return true;
//        if (t instanceof Pair<?, ?> pair) {
//            if (pair.getSecond() instanceof ResourceKey<?> key) {
//                return !key.location().getPath().equals("glowshroom_forest");
//            }
//        }
        return true;
    }
}
