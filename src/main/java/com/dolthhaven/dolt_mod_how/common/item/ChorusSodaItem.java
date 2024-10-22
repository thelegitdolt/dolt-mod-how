package com.dolthhaven.dolt_mod_how.common.item;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.item.DrinkableItem;

public class ChorusSodaItem extends DrinkableItem {
    public ChorusSodaItem(Properties properties) {
        super(properties);
    }

    @Override
    public void affectConsumer(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity consumer) {
        double x = consumer.getX();
        double y = consumer.getY();
        double z = consumer.getZ();

        consumer.teleportRelative(0, 10, 0);
        level.broadcastEntityEvent(consumer, (byte) 46);


        if (consumer.isPassenger()) {
            consumer.stopRiding();
        }

        SoundEvent soundevent = consumer instanceof Fox ? SoundEvents.FOX_TELEPORT : SoundEvents.CHORUS_FRUIT_TELEPORT;

        level.playSound(null, x, y, z, soundevent, SoundSource.PLAYERS, 1.0F, 1.0F);
        consumer.playSound(soundevent, 1.0f, 1.0f);


        level.gameEvent(GameEvent.TELEPORT, consumer.position(), GameEvent.Context.of(consumer));
        if (consumer instanceof Player player) {
            player.getCooldowns().addCooldown(this, 40);
        }
    }
}
