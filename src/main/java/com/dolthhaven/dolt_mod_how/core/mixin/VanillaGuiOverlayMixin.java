package com.dolthhaven.dolt_mod_how.core.mixin;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import umpaz.brewinandchewin.common.registry.BCEffects;

@Mixin(VanillaGuiOverlay.class)
public class VanillaGuiOverlayMixin {
    @Redirect(method = "lambda$static$4(Lnet/minecraftforge/client/gui/overlay/ForgeGui;Lcom/mojang/blaze3d/vertex/PoseStack;FII)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;hasEffect(Lnet/minecraft/world/effect/MobEffect;)Z"))
    private static boolean DoltCompat$HasEffectOrTipsy(LocalPlayer instance, MobEffect effect) {
        return instance.hasEffect(effect) || instance.hasEffect(BCEffects.TIPSY.get());
    }

}
