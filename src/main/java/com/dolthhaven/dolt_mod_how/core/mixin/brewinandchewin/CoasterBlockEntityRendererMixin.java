package com.dolthhaven.dolt_mod_how.core.mixin.brewinandchewin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import umpaz.brewinandchewin.client.renderer.CoasterBlockEntityRenderer;

import java.util.stream.Stream;

@Mixin(CoasterBlockEntityRenderer.class)
public class CoasterBlockEntityRendererMixin {
    @WrapOperation(method = "render(Lumpaz/brewinandchewin/common/block/entity/CoasterBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V", at = @At(value = "INVOKE", target = "Ljava/util/stream/Stream;count()J"), remap = false)
    private <T> long hi(Stream<T> instance, Operation<Long> original) {
        long hi = original.call(instance);
        if (hi == 0) return -1000;
        return hi;
    }
}
