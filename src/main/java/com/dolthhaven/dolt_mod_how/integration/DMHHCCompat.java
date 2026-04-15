package com.dolthhaven.dolt_mod_how.integration;

import com.dolthhaven.dolt_mod_how.core.registry.DMHItems;
import com.rosemods.heart_crystals.core.HCConfig;
import com.rosemods.heart_crystals.core.other.HCEvents;
import com.rosemods.heart_crystals.core.other.HCPlayerInfo;
import com.rosemods.heart_crystals.core.registry.HCBlocks;
import com.rosemods.heart_crystals.core.registry.HCSoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class DMHHCCompat {

    public static InteractionResultHolder<ItemStack> useDummyHeartCrystal(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        Item thisItem = DMHItems.HEART_CRYSTAL_OWO.get();
        Item otherItem = HCBlocks.HEART_CRYSTAL.get().asItem();
        HCPlayerInfo.PlayerHealthInfo info = HCPlayerInfo.getPlayerHealthInfo(player);
        if (info.heartCount < HCConfig.COMMON.maximum.get()) {
            ++info.heartCount;
            info.syncHealthInfo(player);
            HCEvents.setMaxHealthAttribute(info.heartCount * 2, player);
            stack.shrink(1);
            player.heal(2.0F);
            player.awardStat(Stats.ITEM_USED.get(thisItem));
            player.getCooldowns().addCooldown(thisItem, 24);
            level.playSound(player, player.blockPosition(), HCSoundEvents.HEART_CRYSTAL_USE.get(), SoundSource.PLAYERS, 0.65F, 1.0F + (level.random.nextFloat() - 0.5F) / 8.0F);

            return InteractionResultHolder.success(stack);
        } else {
            player.displayClientMessage(Component.translatable(otherItem.getDescriptionId() + ".maximum"), true);
            return InteractionResultHolder.fail(stack);
        }
    }
    public static void appendDummyHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag tooltipFlag){
        Item otherItem = HCBlocks.HEART_CRYSTAL.get().asItem();
        tooltip.add(Component.translatable(otherItem.getDescriptionId() + ".desc", "" + HCConfig.COMMON.maximum.get()).withStyle(ChatFormatting.DARK_PURPLE));
    }
}
