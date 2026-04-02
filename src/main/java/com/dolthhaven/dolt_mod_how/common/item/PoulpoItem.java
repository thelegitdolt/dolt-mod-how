package com.dolthhaven.dolt_mod_how.common.item;

import com.dolthhaven.dolt_mod_how.core.registry.DMHSounds;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class PoulpoItem extends Item {
    public PoulpoItem(Properties p_41383_) {
        super(p_41383_);
    }

    public static final String[] SEX = {
        "sex sex sex sex sex sex sex sex sex sex sex sex sex sex sex sex sex sex sex sex",
        "I hate my chud Poulpo item",
        "PoulpSMP 10000 Years",
        "Invite everyone you know",
        "Chunk culling soon",
        "Server reset tomorrow",
        "Dolt made this mod? But how....",
        "Poulpolis 5 trillion years",
        "Rainbow reef confirmed in Dolt Modpack How",
        "Everybody loves me",
        "I really hope they add diorite blockset",
        ":trollwave: :trollwave: :TrollSurfing: :trollwave: :trollwave:",
        "You may not poulp now, there are monsters nearby"
    };

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        boolean success = false;
        if (level.random.nextInt(4) == 0) {
            player.displayClientMessage(Component.literal(Util.getRandom(SEX, level.random)), true);
            success = true;
        }
        if (level.random.nextInt(15) == 0) {
            player.playSound(DMHSounds.POULPO.get(), 1f + level.getRandom().nextFloat(), 1.0f);
            success = true;
        }

        return success ? InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide) : super.use(level, player, hand);
    }
}

