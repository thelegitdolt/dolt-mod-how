package com.dolthhaven.dolt_mod_how.integration;

import com.dolthhaven.dolt_mod_how.core.other.ChargedCreeperMobCap;
import com.ninni.ftgu.registry.FTGUStatusEffects;
import com.ninni.ftgu.server.entity.ChargedCreeperEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class DMHFTGUCompat {
    public static boolean shouldSpawnChargedCreeper(ServerLevel level, BlockPos pos, LivingEntity entity) {
        if (entity instanceof ChargedCreeperEntity creeper) {
            BlockPos belowPos = pos.below();
            boolean shouldSpawn = level.isThundering() && level.getDifficulty() != Difficulty.PEACEFUL && level.canSeeSky(pos) &&
                    level.getBlockState(belowPos).isValidSpawn(level, belowPos, creeper.getType()) && pos.getY() > 50
                    && ChargedCreeperMobCap.checkNewChargedCreeperSpawn(level, pos);

            if (shouldSpawn) {
                ChargedCreeperMobCap.add(creeper);
                return true;
            }
        }

        return false;
    }

    public static boolean isDoomedEffect(MobEffectInstance instance) {
        return instance.getEffect() == FTGUStatusEffects.DOOMED.get();
    }
}
