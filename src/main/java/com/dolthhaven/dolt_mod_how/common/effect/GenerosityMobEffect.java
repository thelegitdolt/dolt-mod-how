package com.dolthhaven.dolt_mod_how.common.effect;

import com.dolthhaven.dolt_mod_how.core.util.DMHUtils;
import com.dolthhaven.dolt_mod_how.data.tag.DMHTags;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class GenerosityMobEffect extends InstantenousMobEffect {
    public GenerosityMobEffect(MobEffectCategory category) {
        super(category, 0xff6969);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        LivingEntity victim = DMHUtils.getClosestEntityTo(entity, e -> e != entity);
        if (victim == null) return;

        Set<MobEffect> toRemove = new HashSet<>();


        for (MobEffectInstance instance : entity.getActiveEffects()) {
            if (!ForgeRegistries.MOB_EFFECTS.tags().getTag(DMHTags.GENEROSITY_CANNOT_SHARE).contains(instance.getEffect()) && instance.getDuration() != -1) {
                victim.addEffect(instance);
                toRemove.add(instance.getEffect());

                if (amplifier == 0) break;
            }
        }

        toRemove.forEach(entity::removeEffect);
    }
}
