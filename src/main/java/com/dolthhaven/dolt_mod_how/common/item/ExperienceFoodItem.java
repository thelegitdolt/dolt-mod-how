package com.dolthhaven.dolt_mod_how.common.item;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import vectorwing.farmersdelight.common.item.ConsumableItem;

public class ExperienceFoodItem extends ConsumableItem {
    public ExperienceFoodItem(Properties properties) {
        super(properties);
    }

    @Override
    public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
        if (consumer instanceof ServerPlayer && level instanceof ServerLevel serverLevel) {
            ExperienceOrb.award(serverLevel, consumer.position(), UniformInt.of(1, 3).sample(consumer.getRandom()));
        }
    }
}
