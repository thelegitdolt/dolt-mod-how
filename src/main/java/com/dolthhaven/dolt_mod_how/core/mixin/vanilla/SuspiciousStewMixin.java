package com.dolthhaven.dolt_mod_how.core.mixin.vanilla;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SuspiciousStewItem;
import net.minecraftforge.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SuspiciousStewItem.class)
public class SuspiciousStewMixin {
    @ModifyReturnValue(method = "finishUsingItem", at = @At("RETURN"))
    private ItemStack hi(ItemStack bowlStack, @Local(argsOnly = true) ItemStack originalStew) {
        if (ModList.get().isLoaded("bowling_bulb")) return bowlStack;

        if (bowlStack.is(Items.BOWL)) {
            return originalStew.split(1);
        } return bowlStack;
    }
}
