package com.dolthhaven.dolt_mod_how.core.mixin.upgradeaquatic;

import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.teamabnormals.upgrade_aquatic.core.other.UAEvents;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(UAEvents.class)
public class UAEventsMixin {
    @Inject(method = "onRightClickBlock", at = @At("HEAD"), cancellable = true, remap = false)
    private static void thisFeatureSucksAndIWIN_MWAHAHAHAHAHA(PlayerInteractEvent.RightClickBlock event, CallbackInfo ci) {
        if (DMHUtils.alexCavesLoaded()) ci.cancel();
    }
}
