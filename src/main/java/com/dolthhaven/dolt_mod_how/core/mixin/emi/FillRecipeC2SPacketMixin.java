package com.dolthhaven.dolt_mod_how.core.mixin.emi;

import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.violetmoon.quark.addons.oddities.inventory.slot.CachedItemHandlerSlot;

@Pseudo
@Mixin(targets = "dev.emi.emi.network.FillRecipeC2SPacket")
public class FillRecipeC2SPacketMixin {
    @Inject(method = "apply", at = @At("HEAD"))
    private void hi(Player player, CallbackInfo ci) {
        CachedItemHandlerSlot.cache(player.containerMenu);
    }

    @Inject(method = "apply", at = @At("TAIL"))
    private void bye(Player player, CallbackInfo ci) {
        CachedItemHandlerSlot.applyCache(player.containerMenu);
    }
}
