package com.dolthhaven.dolt_mod_how.core.mixin.vanilla;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ElytraItem.class)
public class ElytraItemMixin {
    @Inject(method = "isFlyEnabled", at = @At("HEAD"), cancellable = true)
    private static void DoltModHow$UnbreakableElytra(ItemStack p_41141_, CallbackInfoReturnable<Boolean> cir) {
        if (DMHConfig.COMMON.elytraInfiniteDurability.get()) cir.setReturnValue(true);
    }
}
