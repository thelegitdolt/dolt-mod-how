package com.dolthhaven.dolt_mod_how.core.mixin.brewinandchewin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import umpaz.brewinandchewin.client.renderer.CoasterBlockEntityRenderer;

import java.util.stream.Stream;

@Pseudo
@Mixin(CoasterBlockEntityRenderer.class)
public class CoasterBlockEntityRendererMixin {
    // prevents the coaster from rendering if it is invisible even if there's no blocks on it
    @WrapOperation(method = "render(Lumpaz/brewinandchewin/common/block/entity/CoasterBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V", at = @At(value = "INVOKE", target = "Ljava/util/stream/Stream;count()J"), remap = false)
    private <T> long hi(Stream<T> instance, Operation<Long> original) {
        long hi = original.call(instance);
        if (hi == 0) return -1000;
        return hi;
    }
}
