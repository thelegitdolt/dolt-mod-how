package com.dolthhaven.dolt_mod_how.core.mixin;

import com.lodestar.aileron.CloudSkipperEnchantment;
import com.lodestar.aileron.SmokeStackEnchantment;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {

    @Inject(at = @At("RETURN"), method = "getAvailableEnchantmentResults")
    private static void getPossibleEntries(int power, ItemStack stack, boolean treasureAllowed, CallbackInfoReturnable<List<EnchantmentInstance>> ci) {
        if (!(stack.is(Items.ELYTRA) || stack.getItem() instanceof ElytraItem)) {
            ci.getReturnValue().removeIf(ele -> ele != null && (ele.enchantment instanceof CloudSkipperEnchantment || ele.enchantment instanceof SmokeStackEnchantment));
        }
    }
}
