package com.dolthhaven.dolt_mod_how.core.mixin;

import com.rosemods.heart_crystals.common.item.HeartCrystalItem;
import com.rosemods.heart_crystals.core.registry.HCSoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(HeartCrystalItem.class)
public class HeartCrystalMixin extends Item {
    public HeartCrystalMixin(Properties p_41383_) {
        super(p_41383_);
    }

    @Unique
    private static final List<MobEffectInstance> BENEFITS = List
            .of(new MobEffectInstance(MobEffects.HEALTH_BOOST, 60 * 8 * 20, 1), new MobEffectInstance(MobEffects.REGENERATION, 15 * 20, 1));

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void doEffectsInstead(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        ItemStack stack = player.getItemInHand(hand);

        player.awardStat(Stats.ITEM_USED.get(this));
        player.getCooldowns().addCooldown(this, 5 * 20);
        level.playSound(player, player.blockPosition(), HCSoundEvents.HEART_CRYSTAL_USE.get(), SoundSource.PLAYERS, 0.65F, 1.0F + (level.random.nextFloat() - 0.5F) / 8.0F);
        stack.shrink(1);

        BENEFITS.forEach(player::addEffect);
        player.heal(4.0F);

        cir.setReturnValue(InteractionResultHolder.sidedSuccess(stack, level.isClientSide()));
    }

    @Inject(method = "appendHoverText", at = @At("HEAD"), cancellable = true)
    private void yeahDie(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag tooltipFlag, CallbackInfo ci) {
        Component component = Component.translatable("dolt_mod_how.item.whenUsed").withStyle(ChatFormatting.DARK_PURPLE);
        tooltip.add(component);

        for (MobEffectInstance instance : BENEFITS) {
            MutableComponent effectText = Component.translatable(instance.getDescriptionId());
            if (instance.getAmplifier() > 0) {
                effectText = Component.translatable("potion.withAmplifier", effectText,
                        Component.translatable("potion.potency." + instance.getAmplifier()));
            }

            if (!instance.endsWithin(20)) {
                effectText = Component.translatable("potion.withDuration", effectText, MobEffectUtil.formatDuration(instance, 1.0f));
            }



            tooltip.add(effectText.withStyle(instance.getEffect().getCategory().getTooltipFormatting()));
        }
        ci.cancel();
    }
}
