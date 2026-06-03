package com.dolthhaven.dolt_mod_how.core;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import umpaz.brewinandchewin.common.block.CoasterBlock;
import umpaz.brewinandchewin.common.block.entity.CoasterBlockEntity;

public class Sex {
    public static void sex(CoasterBlockEntity entity, Font font, Component component, PoseStack poseStack, BlockEntityRenderDispatcher renderer, Vec3 position, MultiBufferSource buffer, int packedLight) {
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
