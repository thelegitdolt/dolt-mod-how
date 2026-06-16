package com.dolthhaven.dolt_mod_how.core.mixin.vanilla;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net.minecraft.world.inventory.BrewingStandMenu.FuelSlot")
public class BrewingStandMenuFuelSlotMixin {
    @ModifyReturnValue(method = "mayPlace", at = @At("RETURN"))
    private boolean hi(boolean original) {
        return !DMHConfig.COMMON.brewingUnbloating.get();
    }
}
