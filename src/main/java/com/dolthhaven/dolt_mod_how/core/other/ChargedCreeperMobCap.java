package com.dolthhaven.dolt_mod_how.core.other;

import com.ninni.ftgu.server.entity.ChargedCreeperEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ChargedCreeperMobCap {
    public static final Set<UUID> CHARGED_CREEPERS = new HashSet<>();

    public static boolean add(ChargedCreeperEntity entity) {
        return CHARGED_CREEPERS.add(entity.getUUID());
    }

    public static void update(ServerLevel level) {
        CHARGED_CREEPERS.removeIf(id -> level.getEntity(id) == null);
    }

    public static boolean checkNewChargedCreeperSpawn(ServerLevel level, BlockPos pos) {
        for (UUID creeper : CHARGED_CREEPERS) {
            Entity entity = level.getEntity(creeper);
            if (entity != null) {
                if (entity.position().distanceTo(pos.getCenter()) < 200) {
                    return false;
                }
            }
        }
        return true;
    }
}
