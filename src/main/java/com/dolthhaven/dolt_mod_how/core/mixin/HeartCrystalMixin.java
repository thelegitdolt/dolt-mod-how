package com.dolthhaven.dolt_mod_how.core.mixin;

import com.rosemods.heart_crystals.common.item.HeartCrystalItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(HeartCrystalItem.class)
public class HeartCrystalMixin {
    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void cancelthemtehod(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        cir.setReturnValue(InteractionResultHolder.fail(player.getItemInHand(hand)));
    }

    @Inject(method = "appendHoverText", at = @At("HEAD"), cancellable = true)
    private void yeahDie(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag tooltipFlag, CallbackInfo ci) {
        ci.cancel();
    }
}
