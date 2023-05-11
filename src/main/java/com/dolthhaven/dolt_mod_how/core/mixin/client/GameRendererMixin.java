package com.dolthhaven.dolt_mod_how.core.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import umpaz.brewinandchewin.common.registry.BCEffects;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    protected abstract void renderConfusionOverlay(float f);


    @Inject(method = "Lnet/minecraft/client/renderer/GameRenderer;render(FJZ)V",
            at = @At(value = "INVOKE_ASSIGN", target = "Ljava/lang/Double;floatValue()F"))
    private void DoltModHow$HandleTipsyOverlay(float partialTicks, long tall, boolean lean, CallbackInfo ci) {
        if (this.minecraft.player != null) {
            float f = Mth.lerp(partialTicks, this.minecraft.player.oPortalTime, this.minecraft.player.portalTime);
            float f1 = this.minecraft.options.screenEffectScale().get().floatValue();
            if (f > 0.0F && this.minecraft.player.hasEffect(BCEffects.TIPSY.get()) && f1 < 1.0F) {
                this.renderConfusionOverlay(f * (1.0F - f1));
            }
        }
    }

    @Redirect(method = "renderLevel(FJLcom/mojang/blaze3d/vertex/PoseStack;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;hasEffect(Lnet/minecraft/world/effect/MobEffect;)Z"))
    public boolean DoltMowHow$HasEffectOrTipsy(LocalPlayer instance, MobEffect effect) {
        return instance.hasEffect(effect) || instance.hasEffect(BCEffects.TIPSY.get());
    }
}
