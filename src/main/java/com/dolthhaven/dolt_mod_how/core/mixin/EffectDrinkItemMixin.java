package com.dolthhaven.dolt_mod_how.core.mixin;

import com.teamabnormals.abnormals_delight.common.item.EffectDrinkItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EffectDrinkItem.class)
public class EffectDrinkItemMixin {
    @Inject(method = "getUseDuration(Lnet/minecraft/world/item/ItemStack;)I", at = @At("RETURN"), cancellable = true)
    private void DoltModHow$FastDrink(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(20);
    }
}
