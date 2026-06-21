package com.dolthhaven.dolt_mod_how.integration;

import com.dolthhaven.dolt_mod_how.core.registry.DMHCriteriaTriggers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.registries.ForgeRegistries;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

public class DMHSpawnCompat {
    public static boolean hasOctopus(Level level, BlockEntity entity) {
        try {
            Class<?> octoClass = Class.forName("com.ninni.spawn.server.entity.accessor.ChestBlockEntityAccessor");
            Object a = octoClass.cast(entity);
            UUID playerUUID = (UUID) octoClass.getMethod("getOctopusOwner").invoke(a);
            if (playerUUID != null) {
                if (level.getPlayerByUUID(playerUUID) instanceof ServerPlayer serverPlayer) {
                    DMHCriteriaTriggers.USE_OCTOPUS_TO_PREVENT_VALLUMRAPTOR_TOMFOOLERY.trigger(serverPlayer);
                }
                return true;
            }
        } catch (ClassNotFoundException | ClassCastException | NoSuchMethodException | InvocationTargetException | IllegalAccessException ignored) {
        }
        return false;
    }

    public static SoundEvent octopusSquirtSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("spawn", "entity.octopus.squirt"));
    }
}
