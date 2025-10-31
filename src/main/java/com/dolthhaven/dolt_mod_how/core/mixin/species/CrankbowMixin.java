package com.dolthhaven.dolt_mod_how.core.mixin.species;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import com.dolthhaven.dolt_mod_how.integration.DMHSpeciesCompat;
import com.ninni.species.server.item.CrankbowItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(CrankbowItem.class)
public class CrankbowMixin {
    @Inject(method = "getMaxWeight", at = @At("HEAD"), cancellable = true, remap = false)
    private static void stackers(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        int stackSize = DMHConfig.COMMON.stackSizeForSpeciesCrankbow.get();
        if (stackSize == DMHConfig.COMMON.stackSizeForSpeciesCrankbow.getDefault()) {
            return;
        }

        cir.setReturnValue(DMHSpeciesCompat.calculateCrankbowStack(stack, stackSize));
    }
}
