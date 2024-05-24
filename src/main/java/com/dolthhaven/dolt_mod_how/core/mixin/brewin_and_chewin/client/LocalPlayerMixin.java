package com.dolthhaven.dolt_mod_how.core.mixin.brewin_and_chewin.client;

import com.dolthhaven.dolt_mod_how.core.DoltModHowConfig;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.ProfilePublicKey;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import umpaz.brewinandchewin.common.registry.BCEffects;

import java.util.Objects;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin extends AbstractClientPlayer {
    @Shadow
    public float portalTime;
    @Shadow public float oPortalTime;

    public LocalPlayerMixin(ClientLevel p_234112_, GameProfile p_234113_, @Nullable ProfilePublicKey p_234114_) {
        super(p_234112_, p_234113_, p_234114_);
    }

    @Inject(method = "handleNetherPortalClient",
            at = @At(value = "HEAD"), cancellable = true)
    private void DoltModHow$ThrowTipsyOverlayPacket(CallbackInfo ci) {
        if (!DoltModHowConfig.CLIENT.overhaulTipsyOverlay.get()) {
            return;
        }

        if (this.isInsidePortal || this.hasEffect(MobEffects.CONFUSION)) {
            return;
        }

        if (this.hasEffect(BCEffects.TIPSY.get()) && Objects.requireNonNull(this.getEffect(BCEffects.TIPSY.get())).getDuration() > 60) {
            this.oPortalTime = this.portalTime;
            float amp = (float) Objects.requireNonNull(this.getEffect(BCEffects.TIPSY.get())).getAmplifier() + 1;
            portalTime += 0.005667f;
            if (portalTime > amp / 11) {
                portalTime = amp / 11;
            }
            this.processPortalCooldown();
            ci.cancel();
        }
    }

    @Inject(method = "Lnet/minecraft/client/player/LocalPlayer;removeEffectNoUpdate(Lnet/minecraft/world/effect/MobEffect;)Lnet/minecraft/world/effect/MobEffectInstance;",
            at = @At(value = "HEAD"))
    private void DoltModHow$RemoveTipsyNoUpdate(MobEffect p_108720_, CallbackInfoReturnable<MobEffectInstance> cir) {
        if (!DoltModHowConfig.CLIENT.overhaulTipsyOverlay.get()) {
            return;
        }

        if (p_108720_ == BCEffects.TIPSY.get()) {
            this.oPortalTime = 0.0F;
            this.portalTime = 0.0F;
        }
    }

}
