package com.dolthhaven.dolt_mod_how.core.mixin.vanilla;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.PowderSnowBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PowderSnowBlock.class)
public class PowderSnowBlockMixin {
    @Inject(at = @At("RETURN"), method = "canEntityWalkOnPowderSnow", cancellable = true)
    private static void DoltCompat$canEntityWalkOnPowderSnow(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof Horse horse) {
            ItemStack chestSlot = horse.getItemBySlot(EquipmentSlot.CHEST);
            if (chestSlot.is(Items.LEATHER_HORSE_ARMOR)) {
                cir.setReturnValue(true);
            }
        }
    }
}