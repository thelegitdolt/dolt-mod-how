package com.dolthhaven.dolt_mod_how.common.effect;

import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.dolthhaven.dolt_mod_how.data.tag.DMHTags;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.ForgeRegistries;

public class MimingMobEffect extends InstantenousMobEffect {
    public MimingMobEffect(MobEffectCategory category) {
        super(category, 0x99f0c2);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        LivingEntity victim = DMHUtils.getClosestEntityTo(entity, living -> !living.getActiveEffects().isEmpty() && living != entity);
        if (victim == null) return;

        for (MobEffectInstance instance : victim.getActiveEffects()) {
            if (!ForgeRegistries.MOB_EFFECTS.tags().getTag(DMHTags.MIMING_CANNOT_COPY).contains(instance.getEffect()) && !instance.isInfiniteDuration()) {
                entity.addEffect(instance);

                if (amplifier == 0) break;
            }
        }
    }
}
