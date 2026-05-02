package com.dolthhaven.dolt_mod_how.integration;

import com.ninni.spawn.registry.SpawnSoundEvents;
import com.ninni.spawn.server.entity.accessor.ChestBlockEntityAccessor;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.entity.BlockEntity;

public class DMHSpawnCompat {
    public static boolean hasOctopus(BlockEntity entity) {
        return entity instanceof ChestBlockEntityAccessor accessor && accessor.getOctopusOwner() != null;
    }

    public static SoundEvent octopusSquirtSound() {
        return SpawnSoundEvents.OCTOPUS_SQUIRT.get();
    }
}
