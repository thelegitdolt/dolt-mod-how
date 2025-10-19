package com.dolthhaven.dolt_mod_how.core.mixin.farmersdelight;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import com.dolthhaven.dolt_mod_how.core.registry.DMHEnchants;
import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.dolthhaven.dolt_mod_how.integration.DMHDDCompat;
import net.minecraft.stats.Stats;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.common.item.KnifeItem;

@Mixin(KnifeItem.class)
public class KnifeItemMixin extends DiggerItem {
    /**
     * mixins knives so they can no longer receive the efficiency enchantment.
     */
    @Inject(method = "canApplyAtEnchantingTable",
            at = @At(value = "RETURN"), cancellable = true, remap = false)
    private void DoltModHow$NoEfficiencyOnKnifeEnchantmentTable(ItemStack stack, Enchantment enchantment, CallbackInfoReturnable<Boolean> cir) {
        if ((enchantment.equals(Enchantments.SILK_TOUCH) || enchantment.equals(Enchantments.BLOCK_EFFICIENCY)) && DMHConfig.COMMON.doUnbloatKnifeEnchants.get())
            cir.setReturnValue(false);
    }

    public KnifeItemMixin(float p_204108_, float p_204109_, Tier p_204110_, TagKey<Block> p_204111_, Properties p_204112_) {
        super(p_204108_, p_204109_, p_204110_, p_204111_, p_204112_);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return stack.getEnchantmentLevel(DMHEnchants.BALLISTIC.get()) > 0 ? UseAnim.BOW : super.getUseAnimation(stack);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return stack.getEnchantmentLevel(DMHEnchants.BALLISTIC.get()) > 0 ? 72000 : super.getUseDuration(stack);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        if (stack.getEnchantmentLevel(DMHEnchants.BALLISTIC.get()) > 0 && ModList.get().isLoaded(DMHUtils.Constants.DUNGEONS_DELIGHT)) {
            throwCleaver(level, entity, stack, timeLeft);
        } else {
            super.releaseUsing(stack, level, entity, timeLeft);
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.getEnchantmentLevel(DMHEnchants.BALLISTIC.get()) > 0 && ModList.get().isLoaded(DMHUtils.Constants.DUNGEONS_DELIGHT)) {
            if (stack.getDamageValue() >= stack.getMaxDamage() - 1) {
                return InteractionResultHolder.fail(stack);
            }
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(stack);
        }
        return super.use(level, player, hand);
    }

    @Unique
    private void throwCleaver(Level level, LivingEntity entity, ItemStack stack, int timeLeft) {
        if (!ModList.get().isLoaded(DMHUtils.Constants.DUNGEONS_DELIGHT)) return;

        if (entity instanceof Player player) {
            if (this.getUseDuration(stack) - timeLeft >= 6 && !player.getCooldowns().isOnCooldown(this)) {
                if (!level.isClientSide) {
                    stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(entity.getUsedItemHand()));
                    DMHDDCompat.makeCleaverAndThrowIt(stack, player, level, this.getAttackDamage(), cleaver -> {});
                }
                player.awardStat(Stats.ITEM_USED.get(this));
            }
        }
    }
}
