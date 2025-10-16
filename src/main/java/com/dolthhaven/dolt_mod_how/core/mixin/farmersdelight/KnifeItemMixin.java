package com.dolthhaven.dolt_mod_how.core.mixin.farmersdelight;

import com.dolthhaven.dolt_mod_how.core.DMHConfig;
import com.dolthhaven.dolt_mod_how.core.registry.DMHEnchants;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.yirmiri.dungeonsdelight.common.entity.misc.CleaverEntity;
import net.yirmiri.dungeonsdelight.common.item.CleaverItem;
import net.yirmiri.dungeonsdelight.core.registry.DDEntities;
import net.yirmiri.dungeonsdelight.core.registry.DDItems;
import net.yirmiri.dungeonsdelight.core.registry.DDSounds;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.common.item.KnifeItem;

@Mixin(KnifeItem.class)
public class KnifeItemMixin extends DiggerItem {
    public KnifeItemMixin(float p_204108_, float p_204109_, Tier p_204110_, TagKey<Block> p_204111_, Properties p_204112_) {
        super(p_204108_, p_204109_, p_204110_, p_204111_, p_204112_);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return stack.getEnchantmentLevel(DMHEnchants.CONQUERING_STAR.get()) > 0 ? UseAnim.BOW : super.getUseAnimation(stack);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return stack.getEnchantmentLevel(DMHEnchants.CONQUERING_STAR.get()) > 0 ? 72000 : super.getUseDuration(stack);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        if (stack.getEnchantmentLevel(DMHEnchants.CONQUERING_STAR.get()) > 0) {
            throwCleaver(level, entity, stack, timeLeft);
        } else {
            super.releaseUsing(stack, level, entity, timeLeft);
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.getEnchantmentLevel(DMHEnchants.CONQUERING_STAR.get()) > 0) {
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
        if (entity instanceof Player player) {
            if (this.getUseDuration(stack) - timeLeft >= 6 && !player.getCooldowns().isOnCooldown(this)) {
                if (!level.isClientSide) {
                    stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(entity.getUsedItemHand()));
                    CleaverEntity cleaver = new CleaverEntity(DDEntities.CLEAVER.get(), level, player, stack.copy());
                    cleaver.setItem(stack.copy());
                    this.applyEffects(stack, cleaver);
                    cleaver.setBaseDamage(cleaver.getBaseDamage() + (double)this.getAttackDamage());
                    cleaver.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, ((CleaverItem) DDItems.NETHERITE_CLEAVER.get()).range, 1.0F);
                    if (player.getAbilities().instabuild) {
                        cleaver.pickup = AbstractArrow.Pickup.DISALLOWED;
                    }

                    level.addFreshEntity(cleaver);
                    cleaver.setOwner(player);
                    level.playSound(null, cleaver, DDSounds.CLEAVER_THROW.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
                }

                player.awardStat(Stats.ITEM_USED.get(this));
            }
        }
    }

    @Unique
    private void applyEffects(ItemStack stack, CleaverEntity cleaver) {
        int sharpness = stack.getEnchantmentLevel(Enchantments.SHARPNESS);
        int fireAspect = stack.getEnchantmentLevel(Enchantments.FIRE_ASPECT);
        int conqueringStar = stack.getEnchantmentLevel(DMHEnchants.CONQUERING_STAR.get());

        if (sharpness > 0) {
            cleaver.setBaseDamage(cleaver.getBaseDamage() + (double)sharpness * (double)0.5F + (double)0.5F);
        }

        if (fireAspect > 0) {
            cleaver.setRemainingFireTicks(fireAspect * 40 + cleaver.getRemainingFireTicks());
        }

        if (conqueringStar > 1) {
            cleaver.ricochetsLeft += 2;
        }

        if (conqueringStar > 2) {
            cleaver.setSerratedLevel(2);
        }
    }

    /**
     * mixins knives so they can no longer receive the efficiency enchantment.
     */
    @Inject(method = "canApplyAtEnchantingTable",
    at = @At(value = "RETURN"), cancellable = true, remap = false)
    private void DoltModHow$NoEfficiencyOnKnifeEnchantmentTable(ItemStack stack, Enchantment enchantment, CallbackInfoReturnable<Boolean> cir) {
        if ((enchantment.equals(Enchantments.SILK_TOUCH) || enchantment.equals(Enchantments.BLOCK_EFFICIENCY)) && DMHConfig.COMMON.doUnbloatKnifeEnchants.get())
            cir.setReturnValue(false);
    }
}
