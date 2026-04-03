package com.dolthhaven.dolt_mod_how.core.mixin.farmersdelight;

import com.dolthhaven.dolt_mod_how.core.registry.DMHEnchants;
import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.dolthhaven.dolt_mod_how.integration.DMHDDCompat;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.common.item.KnifeItem;

@Mixin(Item.class)
public class KnifeCanUseBallisticMixins {
    @Inject(method = "getUseAnimation", at = @At("HEAD"), cancellable = true)
    private void hi(ItemStack stack, CallbackInfoReturnable<UseAnim> cir) {
        if (isBallisticKnife(stack)) {
            cir.setReturnValue(UseAnim.BOW);
        }
    }

    @Inject(method = "getUseDuration", at = @At("HEAD"), cancellable = true)
    private void DMH$BallisticUseDuration(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        if (isBallisticKnife(stack)) {
            cir.setReturnValue(72000);
        }
    }

    @Inject(method = "releaseUsing", at = @At("HEAD"))
    private void DMH$BallisticReleaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft, CallbackInfo ci) {
        if (isBallisticKnife(stack)) {
            throwCleaver(level, entity, stack, timeLeft);
        }
    }

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void DMH$UseBallisticKnife(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.getEnchantmentLevel(DMHEnchants.BALLISTIC.get()) > 0 && ModList.get().isLoaded(DMHUtils.Constants.DUNGEONS_DELIGHT)) {
            if (stack.getDamageValue() >= stack.getMaxDamage() - 1) {
                cir.setReturnValue(InteractionResultHolder.fail(stack));
            }
            player.startUsingItem(hand);
            cir.setReturnValue(InteractionResultHolder.consume(stack));
        }
    }

    @Unique
    private static boolean isBallisticKnife(ItemStack stack) {
        return stack.getItem() instanceof KnifeItem &&
                stack.getEnchantmentLevel(DMHEnchants.BALLISTIC.get()) > 0 &&
                ModList.get().isLoaded(DMHUtils.Constants.DUNGEONS_DELIGHT);
    }

    @Unique
    private void throwCleaver(Level level, LivingEntity entity, ItemStack stack, int timeLeft) {
        if (!ModList.get().isLoaded(DMHUtils.Constants.DUNGEONS_DELIGHT)) return;

        if (entity instanceof Player player && stack.getItem() instanceof KnifeItem knifeItem) {
            if (knifeItem.getUseDuration(stack) - timeLeft >= 6 && !player.getCooldowns().isOnCooldown(stack.getItem())) {
                if (!level.isClientSide) {
                    stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(entity.getUsedItemHand()));
                    DMHDDCompat.makeCleaverAndThrowIt(stack, player, level, knifeItem.getAttackDamage(), cleaver -> {});
                }
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            }
        }
    }
}
