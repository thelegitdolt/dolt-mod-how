package com.dolthhaven.dolt_mod_how.common.effect;

import com.dolthhaven.dolt_mod_how.core.registry.DMHMobEffects;
import com.dolthhaven.dolt_mod_how.data.tag.DMHTags;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class MimingMobEffect extends InstantenousMobEffect {
    public MimingMobEffect(MobEffectCategory category) {
        super(category, 0x99f0c2);
    }

    public void applyEffectTick(LivingEntity entity, int amplifier) {
        Vec3 pos = entity.position();
        List<LivingEntity> effectiveEntities = entity.level().getEntitiesOfClass(LivingEntity.class, new AABB(pos, pos.add(1, 1, 1)).inflate(2, 10, 2),
                        living -> !living.getActiveEffects().isEmpty());
        LivingEntity victim = entity.level().getNearestEntity(effectiveEntities, TargetingConditions.DEFAULT,null,  pos.x, pos.y, pos.z);
        if (victim == null) return;

        for (MobEffectInstance instance : victim.getActiveEffects()) {
            if (!ForgeRegistries.MOB_EFFECTS.tags().getTag(DMHTags.MIMING_CANNOT_COPY).contains(instance.getEffect())) {
                entity.addEffect(instance);

                if (amplifier == 0) break;
            }
        }
    }
}
