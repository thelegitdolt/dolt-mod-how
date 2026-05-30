package com.dolthhaven.dolt_mod_how.core.mixin.brewinandchewin;

import com.dolthhaven.dolt_mod_how.core.DoltModHow;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import umpaz.brewinandchewin.client.renderer.CoasterBlockEntityRenderer;
import umpaz.brewinandchewin.common.block.entity.CoasterBlockEntity;

import java.util.stream.Stream;

@Pseudo
@Mixin(CoasterBlockEntityRenderer.class)
public abstract class CoasterBlockEntityRendererMixin implements BlockEntityRenderer<CoasterBlockEntity> {
    // prevents the coaster from rendering if it is invisible even if there's no blocks on it
    @WrapOperation(method = "render(Lumpaz/brewinandchewin/common/block/entity/CoasterBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V", at = @At(value = "INVOKE", target = "Ljava/util/stream/Stream;count()J"), remap = false)
    private <T> long hi(Stream<T> instance, Operation<Long> original) {
        long hi = original.call(instance);
        if (hi == 0) return -1000;
        return hi;
    }

    @Inject(method = "render(Lumpaz/brewinandchewin/common/block/entity/CoasterBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V",
        at = @At("TAIL"), remap = false)
    private void DoltModHow$RenderCoasterNamePlate(CoasterBlockEntity entity, float tickDelta, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay, CallbackInfo ci) {
        if (Minecraft.getInstance().hitResult instanceof BlockHitResult blockHitResult) {
            if (entity.getBlockPos().equals(blockHitResult.getBlockPos())) {
                DoltModHow.LOGGER.info("CHAT GIPPITY. THANK YOU CHAT GIPPITY FOR GIVING ME THIS CODE. I LOVE CHAT GIPPITY.");
                DoltModHow.LOGGER.info(entity.getBlockPos().toString());
            }
        }
    }
}
