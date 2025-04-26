package com.dolthhaven.dolt_mod_how.core.mixin.farmersdelight;

import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.common.registry.ModEnchantments;

@Mixin(ModEnchantments.class)
public class ModEnchantmentMixin {
    @Inject(method = "vectorwing/farmersdelight/common/registry/ModEnchantments.lambda$static$0(Lnet/minecraft/world/item/Item;)Z", at = @At("HEAD"), cancellable = true)
    private static void DoltModHow$KnifeStuff(Item item, CallbackInfoReturnable<Boolean> cir) {
        if (item.builtInRegistryHolder().is(DMHUtils.Constants.DESOLATE_DAGGER)) {
            cir.setReturnValue(true);
        }
    }
}
