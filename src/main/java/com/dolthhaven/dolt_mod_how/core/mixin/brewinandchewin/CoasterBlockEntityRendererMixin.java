package com.dolthhaven.dolt_mod_how.core.mixin.brewinandchewin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import umpaz.brewinandchewin.client.renderer.CoasterBlockEntityRenderer;
import umpaz.brewinandchewin.common.block.CoasterBlock;
import umpaz.brewinandchewin.common.block.entity.CoasterBlockEntity;

import java.util.stream.Stream;

@Pseudo
@Mixin(CoasterBlockEntityRenderer.class)
public abstract class CoasterBlockEntityRendererMixin implements BlockEntityRenderer<CoasterBlockEntity> {
    @Unique
    private BlockEntityRenderDispatcher renderer;
    @Unique
    private Font font;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void sex(BlockEntityRendererProvider.Context context, CallbackInfo ci) {
        this.renderer = context.getBlockEntityRenderDispatcher();
        this.font = context.getFont();
    }

    // prevents the coaster from rendering if it is invisible even if there's no blocks on it
    @WrapOperation(method = "render(Lumpaz/brewinandchewin/common/block/entity/CoasterBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V",
            at = @At(value = "INVOKE", target = "Ljava/util/stream/Stream;count()J"), remap = false)
    private <T> long hi(Stream<T> instance, Operation<Long> original) {
        long hi = original.call(instance);
        if (hi == 0) return -1000;
        return hi;
    }

    @Inject(method = "render(Lumpaz/brewinandchewin/common/block/entity/CoasterBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V",
        at = @At("TAIL"), remap = false)
    private void DoltModHow$RenderCoasterNamePlate(CoasterBlockEntity entity, float tickDelta, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay, CallbackInfo ci) {
        if (this.renderer.cameraHitResult instanceof BlockHitResult blockHitResult) {
            BlockPos pos = blockHitResult.getBlockPos();
            if (entity.getBlockPos().equals(pos)) {
                ItemStack stack = itemToRender(entity, blockHitResult.getLocation().toVector3f());
                if (stack.hasCustomHoverName()) {
                    renderNameTag(entity, this.font, stack.getHoverName(), poseStack, this.renderer, Vec3.atCenterOf(pos), buffer, combinedLight);
                }
            }
        }
    }

    @Unique
    private static Vector2f doMatrixMultiplication(CoasterBlockEntity entity, Vector2f lookVec) {
        // normalizes vector, truncates nondecimal bits, make transpose origin to the center of the block
        float x = lookVec.x - (int) lookVec.x + (lookVec.x < 0 ? 1 : 0) - 0.5f;
        float z = lookVec.y - (int) lookVec.y + (lookVec.y < 0 ? 1 : 0) - 0.5f;
        // Construct + apply (2pi * rotation angle) / 16 rotation matrix
        int rotation = entity.getBlockState().getValue(CoasterBlock.ROTATION);
        float sin = Mth.sin(Mth.TWO_PI * rotation / 16);
        float cos = org.joml.Math.cosFromSin(sin, Mth.TWO_PI * rotation / 16);
        float newX = cos * x + sin * z;
        float newY = -sin * x + cos * z;
        // + 0.5f is to make origin work again
        return new Vector2f(newX + 0.5f, newY + 0.5f);
    }

    @Unique
    private static ItemStack itemToRender(CoasterBlockEntity entity, Vector3f lookVector) {
        Vector2f vec = doMatrixMultiplication(entity, new Vector2f(lookVector.x, lookVector.z)) ;
        int x = (int) (16 * (vec.x));
        int z = (int) (16 * (vec.y));

        int count = 0;
        for (ItemStack stack : entity.getItems()) {
            if (!stack.isEmpty()) count++;
        }
        NonNullList<ItemStack> items = entity.getItems();
        if (count == 4) {
            int index = (z < 8 ? 2 : 0) + (x < 8 ? 1 : 0);
            return items.get(index);
        } else if (count == 3) {
            if (z > 8 && x >= 3 && x <= 13) return items.get(0);
            else if (z <= 8) {
                return items.get(1 + (x >= 8 ? 1 : 0));
            }
        } else if (count == 2) {
            int index = (z < 8 ? 2 : 0) + (x < 8 ? 1 : 0);
            if (index == 3) return items.get(0);
            if (index == 0) return items.get(1);
        } else if (count == 1) {
            return items.get(0);
        }

        return ItemStack.EMPTY;
    }

    @Unique
    private static void renderNameTag(CoasterBlockEntity entity, Font font, Component component, PoseStack poseStack, BlockEntityRenderDispatcher renderer, Vec3 position, MultiBufferSource buffer, int packedLight) {
        double distance = renderer.camera.getPosition().distanceToSqr(position);
        if (distance > 8) return;

        float nameOffset = 1.0f;

        poseStack.pushPose();
        float rotationAmount = entity.getBlockState().getValue(CoasterBlock.ROTATION) * Mth.PI / 8;

        poseStack.translate(0.5F, nameOffset, 0.5F);
        poseStack.mulPose(new Quaternionf().rotationXYZ(0, rotationAmount, 0));
        poseStack.mulPose(renderer.camera.rotation());
        poseStack.scale(-0.025F, -0.025F, 0.025F);

        Matrix4f matrix4f = poseStack.last().pose();
        float opacity = Minecraft.getInstance().options.getBackgroundOpacity(0.25F);
        int productionOpacity = (int) (opacity * 255.0F) << 24;

        float width = (float)(-font.width(component)) / 2f;
        font.drawInBatch(component, width, 0, 553648127, false, matrix4f, buffer, Font.DisplayMode.SEE_THROUGH, productionOpacity, packedLight);
        font.drawInBatch(component, width, 0, -1, false, matrix4f, buffer, Font.DisplayMode.NORMAL, 0, packedLight);

        poseStack.popPose();
    }
}
