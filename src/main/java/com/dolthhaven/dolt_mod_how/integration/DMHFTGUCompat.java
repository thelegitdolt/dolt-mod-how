package com.dolthhaven.dolt_mod_how.integration;

import com.ninni.ftgu.server.entity.ChargedCreeperEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.AABB;

public class DMHFTGUCompat {
    public static boolean areaHasChargedCreeper(ServerLevel level, BlockPos pos) {
        return level.getEntitiesOfClass(ChargedCreeperEntity.class, new AABB(pos).inflate(50, 8, 50)).isEmpty();
    }
}
