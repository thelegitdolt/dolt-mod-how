package com.dolthhaven.dolt_mod_how.core.compat;

import com.dolthhaven.dolt_mod_how.core.util.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class DMHSpeciesCompat {
    private static final String GRASS = "block.alphacene_grass.";

    public static final SoundType ALPHACENE_GRASS = type(0.8F, 1,
            opSound(GRASS + "break"),
            opSound(GRASS + "step"),
            opSound(GRASS + "place"),
            opSound(GRASS + "hit"),
            opSound(GRASS + "fall")
    );

    private static SoundType type(float f, float f1, SoundEvent brk, SoundEvent step, SoundEvent place, SoundEvent hit, SoundEvent fall) {
        if (List.of(brk, step, place, hit, fall).contains(null)) {
            return null;
        }

        return new SoundType(f, f1, brk, step, place, hit, fall);
    }

    private static SoundEvent opSound(String namespace) {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(Util.Constants.SPECIES, namespace));
    }
}
