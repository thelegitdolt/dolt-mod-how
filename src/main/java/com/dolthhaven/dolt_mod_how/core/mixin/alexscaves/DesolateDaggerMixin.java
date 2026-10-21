package com.dolthhaven.dolt_mod_how.core.mixin.alexscaves;

import com.github.alexmodguy.alexscaves.server.item.DesolateDaggerItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;

@Pseudo
@Mixin(DesolateDaggerItem.class)
public class DesolateDaggerMixin {
//    public DesolateDaggerMixin(Tier p_43269_, int p_43270_, float p_43271_, Properties p_43272_) {
//        super(p_43269_, p_43270_, p_43271_, p_43272_);
//    }
//
//    @Override
//    public UseAnim getUseAnimation(ItemStack stack) {
//        return stack.getEnchantmentLevel(DMHEnchants.BALLISTIC.get()) > 0 ? UseAnim.BOW : super.getUseAnimation(stack);
//    }
//
//    @Override
//    public int getUseDuration(ItemStack stack) {
//        return stack.getEnchantmentLevel(DMHEnchants.BALLISTIC.get()) > 0 ? 72000 : super.getUseDuration(stack);
//    }
//
//    @Override
//    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
//        if (stack.getEnchantmentLevel(DMHEnchants.BALLISTIC.get()) > 0) {
//            throwCleaver(level, entity, stack, timeLeft);
//        } else {
//            super.releaseUsing(stack, level, entity, timeLeft);
//        }
//    }
//
//    @Override
//    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
//        ItemStack stack = player.getItemInHand(hand);
//        if (stack.getEnchantmentLevel(DMHEnchants.BALLISTIC.get()) > 0) {
//            if (stack.getDamageValue() >= stack.getMaxDamage() - 1) {
//                return InteractionResultHolder.fail(stack);
//            }
//            player.startUsingItem(hand);
//            return InteractionResultHolder.consume(stack);
//        }
//        return super.use(level, player, hand);
//    }
//
//    @Unique
//    private void throwCleaver(Level level, LivingEntity entity, ItemStack stack, int timeLeft) {
//        if (!ModList.get().isLoaded(DMHUtils.Constants.DUNGEONS_DELIGHT)) return;
//
//        if (entity instanceof Player player) {
//            if (this.getUseDuration(stack) - timeLeft >= 6 && !player.getCooldowns().isOnCooldown(this)) {
//                if (!level.isClientSide) {
//                    stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(entity.getUsedItemHand()));
//                    DMHDDCompat.makeCleaverAndThrowIt(stack, player, level, this.getDamage(),
//                            cleaver -> DMHACCompat.saveToCleaver(cleaver, stack));
//                }
//                player.awardStat(Stats.ITEM_USED.get(this));
//            }
//        }
//    }
}
