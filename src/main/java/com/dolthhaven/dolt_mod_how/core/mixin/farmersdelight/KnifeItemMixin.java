package com.dolthhaven.dolt_mod_how.core.mixin.farmersdelight;

import com.dolthhaven.dolt_mod_how.core.DoltModHowConfig;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.common.item.KnifeItem;

@Mixin(KnifeItem.class)
public class KnifeItemMixin {

    /**
     * mixins knives so they can no longer receive the efficiency enchantment.
     */
    @Inject(method = "canApplyAtEnchantingTable(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/enchantment/Enchantment;)Z",
    at = @At(value = "RETURN"), cancellable = true, remap = false)
    private void DoltModHow$NoEfficiencyOnKnifeEnchantmentTable(ItemStack stack, Enchantment enchantment, CallbackInfoReturnable<Boolean> cir) {
        if ((enchantment.equals(Enchantments.SILK_TOUCH) || enchantment.equals(Enchantments.BLOCK_EFFICIENCY)) && DoltModHowConfig.COMMON.doUnbloatKnifeEnchants.get())
            cir.setReturnValue(false);
    }
}
