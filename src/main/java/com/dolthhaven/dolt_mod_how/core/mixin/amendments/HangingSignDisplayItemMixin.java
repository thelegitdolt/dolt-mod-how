package com.dolthhaven.dolt_mod_how.core.mixin.amendments;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.mehvahdjukaar.amendments.events.behaviors.HangingSignDisplayItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HangingSignDisplayItem.class)
public class HangingSignDisplayItemMixin {
    @WrapOperation(method = "interactWithFace", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;setItemInHand(Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/item/ItemStack;)V"))
    private static void hi(Player instance, InteractionHand hand, ItemStack stack, Operation<Void> original) {
        if (DMHConfig.COMMON.lessAnnoyingItemReclaim.get()) {
            DMHUtils.addToInvOrDrop(instance, stack);
        }
        else {
            original.call(instance, hand, stack);
        }
    }
}
