package com.dolthhaven.dolt_mod_how.integration;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.registries.ForgeRegistries;

import java.lang.reflect.InvocationTargetException;

public class DMHSpawnCompat {
    public static boolean hasOctopus(BlockEntity entity) {
        try {
            Class<?> octoClass = Class.forName("com.ninni.spawn.server.entity.accessor.ChestBlockEntityAccessor");
            Object a = octoClass.cast(entity);
            if (octoClass.getMethod("getOctopusOwner").invoke(a) != null) return true;
        } catch (ClassNotFoundException | ClassCastException | NoSuchMethodException | InvocationTargetException | IllegalAccessException ignored) {
        }
        return false;
    }

    public static SoundEvent octopusSquirtSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("spawn", "entity.octopus.squirt"));
    }
}
