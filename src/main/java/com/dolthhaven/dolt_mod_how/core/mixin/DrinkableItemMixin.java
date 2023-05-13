package com.dolthhaven.dolt_mod_how.core.mixin;

import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.common.item.DrinkableItem;

@Mixin(DrinkableItem.class)
public class DrinkableItemMixin {
    @Inject(method = "getUseDuration(Lnet/minecraft/world/item/ItemStack;)I",
    at = @At("RETURN"), cancellable = true)
    private void DoltModHow$FastDrinks(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(20);
    }
}
