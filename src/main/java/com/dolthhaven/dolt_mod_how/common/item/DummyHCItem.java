package com.dolthhaven.dolt_mod_how.common.item;

import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.dolthhaven.dolt_mod_how.integration.DMHHCCompat;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;

public class DummyHCItem extends Item {
    public DummyHCItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!ModList.get().isLoaded(DMHUtils.Constants.HEART_CRYSTALS)) return InteractionResultHolder.fail(player.getItemInHand(hand));
        return DMHHCCompat.useDummyHeartCrystal(level, player, hand);
    }
}
