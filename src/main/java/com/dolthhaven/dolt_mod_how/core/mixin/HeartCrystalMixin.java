package com.dolthhaven.dolt_mod_how.core.mixin;

import com.dolthhaven.dolt_mod_how.integration.DMHHCCompat;
import com.rosemods.heart_crystals.common.item.HeartCrystalItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.slf4j.helpers.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HeartCrystalItem.class)
public class HeartCrystalMixin {
    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void cancelthemtehod(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        if (Util.getCallingClass() != DMHHCCompat.class) {
            cir.setReturnValue(InteractionResultHolder.fail(player.getItemInHand(hand)));
        }
    }
}
