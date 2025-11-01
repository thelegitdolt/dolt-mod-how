package com.dolthhaven.dolt_mod_how.core.mixin.jne;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.jadenxgamer.netherexp.registry.block.custom.SorrowsquashBlock;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import vectorwing.farmersdelight.common.tag.ModTags;

@Pseudo
@Mixin(SorrowsquashBlock.class)
public class SorrowsquashBlockMixin {
    @ModifyExpressionValue(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"))
    private boolean hi(boolean original, @Local ItemStack stack) {
        return original || stack.is(ModTags.KNIVES);
    }
}
