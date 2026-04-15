package com.dolthhaven.dolt_mod_how.integration;

import com.rosemods.heart_crystals.core.registry.HCBlocks;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DMHHCCompat {

    public static InteractionResultHolder<ItemStack> useDummyHeartCrystal(Level level, Player player, InteractionHand hand) {
        return HCBlocks.HEART_CRYSTAL.get().asItem().use(level, player, hand);
//        ItemStack stack = player.getItemInHand(hand);
//        Item thisItem = DMHItems.HEART_CRYSTAL_OWO.get();
//        Item otherItem = HCBlocks.HEART_CRYSTAL.get().asItem();
//        HCPlayerInfo.PlayerHealthInfo info = HCPlayerInfo.getPlayerHealthInfo(player);
//        if (info.heartCount < HCConfig.COMMON.maximum.get()) {
//            ++info.heartCount;
//            info.syncHealthInfo(player);
//            HCEvents.setMaxHealthAttribute(info.heartCount * 2, player);
//            stack.shrink(1);
//            player.heal(2.0F);
//            player.awardStat(Stats.ITEM_USED.get(thisItem));
//            player.getCooldowns().addCooldown(thisItem, 24);
//            level.playSound(player, player.blockPosition(), HCSoundEvents.HEART_CRYSTAL_USE.get(), SoundSource.PLAYERS, 0.65F, 1.0F + (level.random.nextFloat() - 0.5F) / 8.0F);
//
//            return InteractionResultHolder.success(stack);
//        } else {
//            player.displayClientMessage(Component.translatable(otherItem.getDescriptionId() + ".maximum"), true);
//            return InteractionResultHolder.fail(stack);
//        }

    }
}
